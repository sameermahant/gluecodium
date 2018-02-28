#pragma once

#include <jni.h>
#include <memory>
#include <new>
#include "smoke/SimpleInstantiableOne.h"
#include "android/jni/com_example_smoke_SimpleInstantiableOne.h"
#include "android/jni/CppProxyBase.h"
#include "android/jni/FieldAccessMethods.h"
#include "android/jni/JniBase.h"
#include "android/jni/JniCppConversionUtils.h"
#include "smoke/SimpleInstantiableTwo.h"
#include "android/jni/com_example_smoke_SimpleInstantiableTwo.h"
#include "smoke/NestedInstantiableOne.h"
#include "android/jni/com_example_smoke_NestedInstantiableOne.h"
#include "smoke/NestedInstantiableTwo.h"
#include "android/jni/com_example_smoke_NestedInstantiableTwo.h"
#include "smoke/InstanceWithStruct.h"
#include "android/jni/com_example_smoke_InstanceWithStruct.h"
#include "ProxyConversion.h"

namespace transpiler {
namespace jni {

template < typename T >
void
convert_from_jni( JNIEnv* _env, jobject _jobj, ::std::shared_ptr< T >& _nresult ) {
    jclass nativeBaseClass = _env->FindClass("com/example/NativeBase");
    if (_env->IsInstanceOf(_jobj, nativeBaseClass)) {
        if (_jobj != nullptr) {
            auto long_ptr = transpiler::jni::get_long_field(_env, _env->GetObjectClass(_jobj), _jobj, "nativeHandle");
            _nresult = *reinterpret_cast<::std::shared_ptr< T >*> (long_ptr);
        }
    } else {
        ::createCppProxy<>( _env, _jobj, _nresult);
    }
    _env->DeleteLocalRef( nativeBaseClass );
}

jobject convert_to_jni(JNIEnv* _jenv, const ::std::shared_ptr<::smoke::SimpleInstantiableOne> & _ninput);
jobject convert_to_jni(JNIEnv* _jenv, const ::std::shared_ptr<::smoke::SimpleInstantiableTwo> & _ninput);
jobject convert_to_jni(JNIEnv* _jenv, const ::std::shared_ptr<::smoke::NestedInstantiableOne> & _ninput);
jobject convert_to_jni(JNIEnv* _jenv, const ::std::shared_ptr<::smoke::NestedInstantiableTwo> & _ninput);
jobject convert_to_jni(JNIEnv* _jenv, const ::std::shared_ptr<::smoke::InstanceWithStruct> & _ninput);

}
}