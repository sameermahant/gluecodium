/*
 * Copyright (C) 2016-2019 HERE Europe B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 * License-Filename: LICENSE
 */

package com.here.gluecodium.generator.jni

import com.here.gluecodium.common.ModelBuilderContextStack
import com.here.gluecodium.generator.common.modelbuilder.AbstractLimeBasedModelBuilder
import com.here.gluecodium.generator.cpp.CppIncludeResolver
import com.here.gluecodium.generator.cpp.CppModelBuilder
import com.here.gluecodium.generator.java.JavaModelBuilder
import com.here.gluecodium.generator.java.JavaSignatureResolver
import com.here.gluecodium.generator.jni.JniNameRules.Companion.getMangledName
import com.here.gluecodium.model.cpp.CppClass
import com.here.gluecodium.model.cpp.CppEnum
import com.here.gluecodium.model.cpp.CppEnumItem
import com.here.gluecodium.model.cpp.CppField
import com.here.gluecodium.model.cpp.CppFunctionTypeRef
import com.here.gluecodium.model.cpp.CppMethod
import com.here.gluecodium.model.cpp.CppParameter
import com.here.gluecodium.model.cpp.CppStruct
import com.here.gluecodium.model.cpp.CppTypeRef
import com.here.gluecodium.model.cpp.CppUsing
import com.here.gluecodium.model.java.JavaClass
import com.here.gluecodium.model.java.JavaCustomType
import com.here.gluecodium.model.java.JavaEnum
import com.here.gluecodium.model.java.JavaEnumItem
import com.here.gluecodium.model.java.JavaField
import com.here.gluecodium.model.java.JavaInterface
import com.here.gluecodium.model.java.JavaMethod
import com.here.gluecodium.model.java.JavaPackage
import com.here.gluecodium.model.java.JavaParameter
import com.here.gluecodium.model.java.JavaPrimitiveType
import com.here.gluecodium.model.java.JavaTopLevelElement
import com.here.gluecodium.model.java.JavaType
import com.here.gluecodium.model.jni.JniContainer
import com.here.gluecodium.model.jni.JniElement
import com.here.gluecodium.model.jni.JniEnum
import com.here.gluecodium.model.jni.JniEnumerator
import com.here.gluecodium.model.jni.JniException
import com.here.gluecodium.model.jni.JniField
import com.here.gluecodium.model.jni.JniMethod
import com.here.gluecodium.model.jni.JniParameter
import com.here.gluecodium.model.jni.JniStruct
import com.here.gluecodium.model.jni.JniTopLevelElement
import com.here.gluecodium.model.jni.JniType
import com.here.gluecodium.model.lime.LimeAttributeType
import com.here.gluecodium.model.lime.LimeContainerWithInheritance
import com.here.gluecodium.model.lime.LimeEnumeration
import com.here.gluecodium.model.lime.LimeEnumerator
import com.here.gluecodium.model.lime.LimeField
import com.here.gluecodium.model.lime.LimeFunction
import com.here.gluecodium.model.lime.LimeInterface
import com.here.gluecodium.model.lime.LimeLambda
import com.here.gluecodium.model.lime.LimeNamedElement
import com.here.gluecodium.model.lime.LimeParameter
import com.here.gluecodium.model.lime.LimeProperty
import com.here.gluecodium.model.lime.LimeSet
import com.here.gluecodium.model.lime.LimeStruct
import com.here.gluecodium.model.lime.LimeTypeAlias
import com.here.gluecodium.model.lime.LimeTypeHelper
import com.here.gluecodium.model.lime.LimeTypeRef
import com.here.gluecodium.model.lime.LimeTypesCollection

/**
 * This class builds a correspondence-tree containing correspondences between Java and C++ model
 * elements. Currently only corresponding classes and theirs corresponding methods are calculated.
 *
 * Preconditions:
 *
 * For correspondence calculation it is assumed to have one to one mapping, i.e. one LIME
 * element is mapped to at least one target model element.
 *
 * It is assumed that Java- and CppModelBuilder's finishBuilding methods are called in advance of
 * calling finishBuilding on JniModelBuilder (constructed Java and C++ elements need to be
 * accessible via getResults() ).
 */
class JniModelBuilder(
    contextStack: ModelBuilderContextStack<JniElement> = ModelBuilderContextStack(),
    private val javaBuilder: JavaModelBuilder,
    private val javaSignatureResolver: JavaSignatureResolver,
    private val cppBuilder: CppModelBuilder,
    private val cppIncludeResolver: CppIncludeResolver,
    private val internalNamespace: List<String>,
    private val buildTransientModel: (LimeNamedElement) -> List<JniContainer>
) : AbstractLimeBasedModelBuilder<JniElement>(contextStack) {

    val setsCollector = mutableMapOf<String, JniType>()

    override fun finishBuilding(limeContainer: LimeContainerWithInheritance) {
        val cppClass = cppBuilder.getFinalResult(CppClass::class.java)
        val javaTopLevelElement = javaBuilder.getFinalResult(JavaTopLevelElement::class.java)
        val javaClass = javaBuilder.getFinalResult(JavaClass::class.java)

        val jniContainer = JniContainer(
            javaPackage = javaTopLevelElement.javaPackage,
            cppNameSpaces = limeContainer.path.head,
            javaNames = javaClass.classNames,
            javaInterfaceName = javaTopLevelElement.name,
            cppName = cppClass.name,
            cppFullyQualifiedName = cppClass.fullyQualifiedName,
            containerType = when (limeContainer) {
                is LimeInterface -> JniContainer.ContainerType.INTERFACE
                else -> JniContainer.ContainerType.CLASS
            },
            internalNamespace = internalNamespace,
            isEquatable = limeContainer.attributes.have(LimeAttributeType.EQUATABLE),
            isPointerEquatable = limeContainer.attributes.have(LimeAttributeType.POINTER_EQUATABLE),
            hasTypeRepository = cppClass.isInheritable || cppClass.inheritances.isNotEmpty()
        )

        limeContainer.parent?.type?.let {
            val transientParent = buildTransientModel(it).first()
            jniContainer.parentMethods += transientParent.parentMethods
            jniContainer.parentMethods += transientParent.methods
        }

        jniContainer.methods += getPreviousResults(JniMethod::class.java)
        jniContainer.structs += getPreviousResults(JniStruct::class.java)
        jniContainer.enums += getPreviousResults(JniEnum::class.java)

        val types = listOf(limeContainer) + limeContainer.structs + limeContainer.enumerations +
                limeContainer.typeAliases
        jniContainer.includes += types.flatMap { cppIncludeResolver.resolveIncludes(it) }.sorted()

        getPreviousResults(JniContainer::class.java).forEach { storeResult(it) }

        storeResult(jniContainer)
        closeContext()
    }

    override fun finishBuilding(limeTypes: LimeTypesCollection) {
        val childElement = getPreviousResultOrNull(JniTopLevelElement::class.java)

        val cppNameSpace = limeTypes.path.head
        val jniContainer = JniContainer(
            javaPackage = childElement?.javaPackage ?: JavaPackage(emptyList()),
            cppNameSpaces = cppNameSpace,
            containerType = JniContainer.ContainerType.TYPE_COLLECTION,
            internalNamespace = internalNamespace
        )
        jniContainer.structs += getPreviousResults(JniStruct::class.java)
        jniContainer.enums += getPreviousResults(JniEnum::class.java)

        val types = limeTypes.structs + limeTypes.enumerations + limeTypes.typeAliases
        jniContainer.includes += types.flatMap { cppIncludeResolver.resolveIncludes(it) }.sorted()

        storeResult(jniContainer)
        closeContext()
    }

    override fun finishBuilding(limeMethod: LimeFunction) {
        val javaMethod = javaBuilder.getFinalResult(JavaMethod::class.java)
        val cppMethod = cppBuilder.getFinalResult(CppMethod::class.java)

        val jniException = javaMethod.exception?.let {
            JniException(JniNameRules.getFullClassName(it), getPreviousResult(JniType::class.java))
        }

        val jniMethod = JniMethod(
            javaMethodName = getMangledName(javaMethod.name),
            cppMethodName = cppMethod.name,
            returnType = JniType(javaMethod.returnType, cppMethod.returnType),
            isStatic = cppMethod.specifiers.contains(CppMethod.Specifier.STATIC),
            isConst = cppMethod.qualifiers.contains(CppMethod.Qualifier.CONST),
            isOverloaded = javaSignatureResolver.isOverloaded(limeMethod),
            returnsOpaqueHandle = javaMethod.isConstructor && javaMethod.returnType == JavaPrimitiveType.LONG,
            exception = jniException
        )
        jniMethod.parameters.addAll(getPreviousResults(JniParameter::class.java))

        storeResult(jniMethod)
        closeContext()
    }

    override fun finishBuilding(limeParameter: LimeParameter) {
        val javaParameter = javaBuilder.getFinalResult(JavaParameter::class.java)
        val cppParameter = cppBuilder.getFinalResult(CppParameter::class.java)
        val jniType = JniType(javaParameter.type, cppParameter.type)

        storeResult(JniParameter(javaParameter.name, jniType))
        closeContext()
    }

    override fun finishBuilding(limeStruct: LimeStruct) {
        val javaClass = javaBuilder.getFinalResult(JavaClass::class.java)
        val cppStruct = cppBuilder.getFinalResult(CppStruct::class.java)
        val jniStruct = JniStruct(
            javaName = javaClass.classNames.joinToString("$"),
            cppFullyQualifiedName = cppStruct.fullyQualifiedName,
            javaPackage = javaClass.javaPackage,
            fields = getPreviousResults(JniField::class.java),
            methods = getPreviousResults(JniMethod::class.java),
            hasImmutableFields = cppStruct.hasImmutableFields
        )

        storeNamedResult(limeStruct, jniStruct)
        closeContext()
    }

    override fun finishBuilding(limeField: LimeField) {
        val javaField = javaBuilder.getFinalResult(JavaField::class.java)
        val cppField = cppBuilder.getFinalResult(CppField::class.java)

        val jniField = JniField(
            javaName = javaField.name,
            javaCustomType =
                (javaField.type as? JavaCustomType)?.let { JniNameRules.getFullClassName(it) },
            cppField = cppField,
            cppGetterName = cppField.getterName,
            cppSetterName = cppField.setterName
        )

        storeResult(jniField)
        closeContext()
    }

    override fun finishBuilding(limeEnumeration: LimeEnumeration) {
        val javaEnum = javaBuilder.getFinalResult(JavaEnum::class.java)
        val cppEnum = cppBuilder.getFinalResult(CppEnum::class.java)
        val jniEnum = JniEnum(
            javaName = javaEnum.classNames.joinToString("$"),
            cppFullyQualifiedName = cppEnum.fullyQualifiedName,
            javaPackage = javaEnum.javaPackage,
            enumerators = getPreviousResults(JniEnumerator::class.java)
        )

        storeNamedResult(limeEnumeration, jniEnum)
        closeContext()
    }

    override fun finishBuilding(limeEnumerator: LimeEnumerator) {
        val javaEnumItem = javaBuilder.getFinalResult(JavaEnumItem::class.java)
        val cppEnumItem = cppBuilder.getFinalResult(CppEnumItem::class.java)
        storeResult(JniEnumerator(javaEnumItem.name, cppEnumItem.name))
        closeContext()
    }

    override fun finishBuilding(limeProperty: LimeProperty) {
        val javaMethods = javaBuilder.finalResults.filterIsInstance<JavaMethod>()
        val cppMethods = cppBuilder.finalResults.filterIsInstance<CppMethod>()

        val javaGetter = javaMethods.first()
        val cppGetter = cppMethods.first()
        val jniType = JniType(javaGetter.returnType, cppGetter.returnType)
        storeResult(
            JniMethod(
                javaMethodName = getMangledName(javaGetter.name),
                cppMethodName = cppGetter.name,
                returnType = jniType,
                isConst = true,
                isStatic = limeProperty.isStatic
            )
        )
        if (limeProperty.setter != null) {
            val javaSetter = javaMethods.last()
            val cppSetter = cppMethods.last()
            val jniSetter = JniMethod(
                javaMethodName = getMangledName(javaSetter.name),
                cppMethodName = cppSetter.name,
                returnType = JniType.VOID,
                isStatic = limeProperty.isStatic
            )

            val javaParameter = javaSetter.parameters.first()
            val cppParameter = cppSetter.parameters.first()
            val parameterType = JniType(javaParameter.type, cppParameter.type)
            jniSetter.parameters.add(JniParameter(javaParameter.name, parameterType))

            storeResult(jniSetter)
        }

        closeContext()
    }

    override fun finishBuilding(limeLambda: LimeLambda) {
        val cppUsing = cppBuilder.getFinalResult(CppUsing::class.java)
        val javaInterface = javaBuilder.getFinalResult(JavaInterface::class.java)
        val javaClass = javaBuilder.getFinalResult(JavaClass::class.java)

        val jniContainer = JniContainer(
            javaPackage = javaInterface.javaPackage,
            cppNameSpaces = limeLambda.path.head,
            javaNames = javaClass.classNames,
            javaInterfaceName = javaInterface.name,
            cppName = cppUsing.name,
            cppFullyQualifiedName = cppUsing.fullyQualifiedName,
            containerType = JniContainer.ContainerType.INTERFACE,
            internalNamespace = internalNamespace,
            isFunctionalInterface = true
        )
        jniContainer.includes += cppIncludeResolver.resolveIncludes(limeLambda)

        val javaMethod = javaInterface.methods.first()
        val cppFunctionRef = cppUsing.definition as CppFunctionTypeRef
        jniContainer.methods += JniMethod(
            javaMethodName = getMangledName(javaMethod.name),
            cppMethodName = "operator()",
            returnType = JniType(javaMethod.returnType, cppFunctionRef.returnType),
            parameters = javaMethod.parameters.mapIndexed { index, it ->
                JniParameter(it.name, JniType(it.type, cppFunctionRef.parameters[index]))
            }.toMutableList()
        )

        storeResult(jniContainer)
        closeContext()
    }

    override fun finishBuilding(limeTypeRef: LimeTypeRef) {
        val jniType = JniType(
            javaBuilder.getFinalResult(JavaType::class.java),
            cppBuilder.getFinalResult(CppTypeRef::class.java)
        )

        storeResult(jniType)
        closeContext()
    }

    override fun finishBuilding(limeTypeDef: LimeTypeAlias) {
        val actualType = LimeTypeHelper.getActualType(limeTypeDef)
        if (actualType is LimeSet && actualType.elementType.type is LimeEnumeration) {
            setsCollector[actualType.elementType.elementFullName] =
                getPreviousResult(JniType::class.java)
        }

        closeContext()
    }
}
