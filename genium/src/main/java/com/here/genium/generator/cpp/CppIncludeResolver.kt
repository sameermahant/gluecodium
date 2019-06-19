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

package com.here.genium.generator.cpp

import com.here.genium.model.common.Include
import com.here.genium.model.lime.LimeAttributeType
import com.here.genium.model.lime.LimeContainer
import com.here.genium.model.lime.LimeElement
import com.here.genium.model.lime.LimeNamedElement

class CppIncludeResolver(
    private val limeReferenceMap: Map<String, LimeElement>,
    private val nameRules: CppNameRules
) {
    private val resolvedIncludes = mutableMapOf<String, List<Include>>()

    fun resolveIncludes(limeNamedElement: LimeNamedElement): List<Include> =
        resolvedIncludes.getOrPut(limeNamedElement.fullName) {
            val externalType = inferExternalType(limeNamedElement)
            when {
                externalType != null ->
                    externalType.split(',').map { Include.createInternalInclude(it.trim()) }
                limeNamedElement is LimeContainer -> listOf(Include.createInternalInclude(
                    nameRules.getOutputFilePath(limeNamedElement) + CppGenerator.HEADER_FILE_SUFFIX
                ))
                else -> {
                    val parentElementKey = limeNamedElement.path.parent.toString()
                    resolveIncludes(limeReferenceMap[parentElementKey] as LimeNamedElement)
                }
            }
        }

    private fun inferExternalType(limeNamedElement: LimeNamedElement): String? {
        val externalType =
            limeNamedElement.attributes.get(LimeAttributeType.EXTERNAL_TYPE, String::class.java)
        if (externalType != null) {
            return externalType
        }

        val parentElementKey = limeNamedElement.path.parent.toString()
        val parentElement = limeReferenceMap[parentElementKey]
        return (parentElement as? LimeNamedElement)?.let { inferExternalType(it) }
    }
}
