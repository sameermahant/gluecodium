#pragma once

#include <jni.h>
#include "smoke/Structs.h"
#include "android/jni/com_example_smoke_Structs.h"
#include "android/jni/CppProxyBase.h"
#include "android/jni/FieldAccessMethods.h"
#include "android/jni/JniBase.h"
#include "android/jni/JniCppConversionUtils.h"
#include "smoke/StructsInheritance.h"
#include "android/jni/com_example_smoke_StructsInheritance.h"
#include "smoke/StructsFromTypeCollection.h"
#include "android/jni/com_example_smoke_StructsFromTypeCollection.h"
#include "smoke/StructsInstance.h"
#include "android/jni/com_example_smoke_StructsInstance.h"
#include "smoke/InheritedStructsOrder.h"
#include "fire/StructsQualifiedType.h"
#include "android/jni/com_example_fire_StructsQualifiedType.h"
#include "smoke/TypeCollection.h"

namespace transpiler {
namespace jni {

    void convert_from_jni( JNIEnv* _jenv, const jobject _jinput, ::smoke::Structs::Point& _nout );
    jobject convert_to_jni(JNIEnv* _jenv, const ::smoke::Structs::Point& _ninput);
    void convert_from_jni( JNIEnv* _jenv, const jobject _jinput, ::smoke::Structs::Color& _nout );
    jobject convert_to_jni(JNIEnv* _jenv, const ::smoke::Structs::Color& _ninput);
    void convert_from_jni( JNIEnv* _jenv, const jobject _jinput, ::smoke::Structs::Line& _nout );
    jobject convert_to_jni(JNIEnv* _jenv, const ::smoke::Structs::Line& _ninput);
    void convert_from_jni( JNIEnv* _jenv, const jobject _jinput, ::smoke::Structs::ColoredLine& _nout );
    jobject convert_to_jni(JNIEnv* _jenv, const ::smoke::Structs::ColoredLine& _ninput);
    void convert_from_jni( JNIEnv* _jenv, const jobject _jinput, ::smoke::Structs::AllTypesStruct& _nout );
    jobject convert_to_jni(JNIEnv* _jenv, const ::smoke::Structs::AllTypesStruct& _ninput);
    void convert_from_jni( JNIEnv* _jenv, const jobject _jinput, ::smoke::StructsInheritance::ColoredLineInherited& _nout );
    jobject convert_to_jni(JNIEnv* _jenv, const ::smoke::StructsInheritance::ColoredLineInherited& _ninput);
    void convert_from_jni( JNIEnv* _jenv, const jobject _jinput, ::smoke::GrandChildStruct& _nout );
    jobject convert_to_jni(JNIEnv* _jenv, const ::smoke::GrandChildStruct& _ninput);
    void convert_from_jni( JNIEnv* _jenv, const jobject _jinput, ::smoke::ChildStruct& _nout );
    jobject convert_to_jni(JNIEnv* _jenv, const ::smoke::ChildStruct& _ninput);
    void convert_from_jni( JNIEnv* _jenv, const jobject _jinput, ::smoke::ParentStruct& _nout );
    jobject convert_to_jni(JNIEnv* _jenv, const ::smoke::ParentStruct& _ninput);
    void convert_from_jni( JNIEnv* _jenv, const jobject _jinput, ::fire::StructsQualifiedType::QualifiedType& _nout );
    jobject convert_to_jni(JNIEnv* _jenv, const ::fire::StructsQualifiedType::QualifiedType& _ninput);
    void convert_from_jni( JNIEnv* _jenv, const jobject _jinput, ::smoke::Point& _nout );
    jobject convert_to_jni(JNIEnv* _jenv, const ::smoke::Point& _ninput);
    void convert_from_jni( JNIEnv* _jenv, const jobject _jinput, ::smoke::Color& _nout );
    jobject convert_to_jni(JNIEnv* _jenv, const ::smoke::Color& _ninput);
    void convert_from_jni( JNIEnv* _jenv, const jobject _jinput, ::smoke::Line& _nout );
    jobject convert_to_jni(JNIEnv* _jenv, const ::smoke::Line& _ninput);
    void convert_from_jni( JNIEnv* _jenv, const jobject _jinput, ::smoke::ColoredLine& _nout );
    jobject convert_to_jni(JNIEnv* _jenv, const ::smoke::ColoredLine& _ninput);
    void convert_from_jni( JNIEnv* _jenv, const jobject _jinput, ::smoke::AllTypesStruct& _nout );
    jobject convert_to_jni(JNIEnv* _jenv, const ::smoke::AllTypesStruct& _ninput);

}
}