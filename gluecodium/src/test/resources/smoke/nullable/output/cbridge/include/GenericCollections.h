//
//
#pragma once
#ifdef __cplusplus
extern "C" {
#endif
#include "cbridge/include/BaseHandle.h"
#include "cbridge/include/Export.h"
#include <stdbool.h>
#include <stdint.h>
_GLUECODIUM_C_EXPORT _baseRef foobar_ArrayOf__Date_create_handle();
_GLUECODIUM_C_EXPORT _baseRef foobar_ArrayOf__Date_copy_handle(_baseRef handle);
_GLUECODIUM_C_EXPORT void foobar_ArrayOf__Date_release_handle(_baseRef handle);
_GLUECODIUM_C_EXPORT uint64_t foobar_ArrayOf__Date_count(_baseRef handle);
_GLUECODIUM_C_EXPORT double foobar_ArrayOf__Date_get(_baseRef handle, uint64_t index);
_GLUECODIUM_C_EXPORT void foobar_ArrayOf__Date_append(_baseRef handle, double item);
_GLUECODIUM_C_EXPORT _baseRef foobar_ArrayOf__Date_create_optional_handle();
_GLUECODIUM_C_EXPORT void foobar_ArrayOf__Date_release_optional_handle(_baseRef handle);
_GLUECODIUM_C_EXPORT _baseRef foobar_ArrayOf__Date_unwrap_optional_handle(_baseRef handle);
_GLUECODIUM_C_EXPORT _baseRef foobar_ArrayOf__String_create_handle();
_GLUECODIUM_C_EXPORT _baseRef foobar_ArrayOf__String_copy_handle(_baseRef handle);
_GLUECODIUM_C_EXPORT void foobar_ArrayOf__String_release_handle(_baseRef handle);
_GLUECODIUM_C_EXPORT uint64_t foobar_ArrayOf__String_count(_baseRef handle);
_GLUECODIUM_C_EXPORT _baseRef foobar_ArrayOf__String_get(_baseRef handle, uint64_t index);
_GLUECODIUM_C_EXPORT void foobar_ArrayOf__String_append(_baseRef handle, _baseRef item);
_GLUECODIUM_C_EXPORT _baseRef foobar_ArrayOf__String_create_optional_handle();
_GLUECODIUM_C_EXPORT void foobar_ArrayOf__String_release_optional_handle(_baseRef handle);
_GLUECODIUM_C_EXPORT _baseRef foobar_ArrayOf__String_unwrap_optional_handle(_baseRef handle);
_GLUECODIUM_C_EXPORT _baseRef foobar_ArrayOf_nullable_Date_create_handle();
_GLUECODIUM_C_EXPORT _baseRef foobar_ArrayOf_nullable_Date_copy_handle(_baseRef handle);
_GLUECODIUM_C_EXPORT void foobar_ArrayOf_nullable_Date_release_handle(_baseRef handle);
_GLUECODIUM_C_EXPORT uint64_t foobar_ArrayOf_nullable_Date_count(_baseRef handle);
_GLUECODIUM_C_EXPORT _baseRef foobar_ArrayOf_nullable_Date_get(_baseRef handle, uint64_t index);
_GLUECODIUM_C_EXPORT void foobar_ArrayOf_nullable_Date_append(_baseRef handle, _baseRef item);
_GLUECODIUM_C_EXPORT _baseRef foobar_ArrayOf_nullable_Date_create_optional_handle();
_GLUECODIUM_C_EXPORT void foobar_ArrayOf_nullable_Date_release_optional_handle(_baseRef handle);
_GLUECODIUM_C_EXPORT _baseRef foobar_ArrayOf_nullable_Date_unwrap_optional_handle(_baseRef handle);
_GLUECODIUM_C_EXPORT _baseRef foobar_MapOf__Int_To_nullable_smoke_Nullable_SomeStruct_create_handle();
_GLUECODIUM_C_EXPORT void foobar_MapOf__Int_To_nullable_smoke_Nullable_SomeStruct_release_handle(_baseRef handle);
_GLUECODIUM_C_EXPORT _baseRef foobar_MapOf__Int_To_nullable_smoke_Nullable_SomeStruct_iterator(_baseRef handle);
_GLUECODIUM_C_EXPORT void foobar_MapOf__Int_To_nullable_smoke_Nullable_SomeStruct_iterator_release_handle(_baseRef iterator_handle);
_GLUECODIUM_C_EXPORT void foobar_MapOf__Int_To_nullable_smoke_Nullable_SomeStruct_put(_baseRef handle, int32_t key, _baseRef value);
_GLUECODIUM_C_EXPORT bool foobar_MapOf__Int_To_nullable_smoke_Nullable_SomeStruct_iterator_is_valid(_baseRef handle, _baseRef iterator_handle);
_GLUECODIUM_C_EXPORT void foobar_MapOf__Int_To_nullable_smoke_Nullable_SomeStruct_iterator_increment(_baseRef iterator_handle);
_GLUECODIUM_C_EXPORT int32_t foobar_MapOf__Int_To_nullable_smoke_Nullable_SomeStruct_iterator_key(_baseRef iterator_handle);
_GLUECODIUM_C_EXPORT _baseRef foobar_MapOf__Int_To_nullable_smoke_Nullable_SomeStruct_iterator_value(_baseRef iterator_handle);
_GLUECODIUM_C_EXPORT _baseRef foobar_MapOf__Int_To_nullable_smoke_Nullable_SomeStruct_create_optional_handle();
_GLUECODIUM_C_EXPORT void foobar_MapOf__Int_To_nullable_smoke_Nullable_SomeStruct_release_optional_handle(_baseRef handle);
_GLUECODIUM_C_EXPORT _baseRef foobar_MapOf__Int_To_nullable_smoke_Nullable_SomeStruct_unwrap_optional_handle(_baseRef handle);
_GLUECODIUM_C_EXPORT _baseRef foobar_MapOf__Long_To__String_create_handle();
_GLUECODIUM_C_EXPORT void foobar_MapOf__Long_To__String_release_handle(_baseRef handle);
_GLUECODIUM_C_EXPORT _baseRef foobar_MapOf__Long_To__String_iterator(_baseRef handle);
_GLUECODIUM_C_EXPORT void foobar_MapOf__Long_To__String_iterator_release_handle(_baseRef iterator_handle);
_GLUECODIUM_C_EXPORT void foobar_MapOf__Long_To__String_put(_baseRef handle, int64_t key, _baseRef value);
_GLUECODIUM_C_EXPORT bool foobar_MapOf__Long_To__String_iterator_is_valid(_baseRef handle, _baseRef iterator_handle);
_GLUECODIUM_C_EXPORT void foobar_MapOf__Long_To__String_iterator_increment(_baseRef iterator_handle);
_GLUECODIUM_C_EXPORT int64_t foobar_MapOf__Long_To__String_iterator_key(_baseRef iterator_handle);
_GLUECODIUM_C_EXPORT _baseRef foobar_MapOf__Long_To__String_iterator_value(_baseRef iterator_handle);
_GLUECODIUM_C_EXPORT _baseRef foobar_MapOf__Long_To__String_create_optional_handle();
_GLUECODIUM_C_EXPORT void foobar_MapOf__Long_To__String_release_optional_handle(_baseRef handle);
_GLUECODIUM_C_EXPORT _baseRef foobar_MapOf__Long_To__String_unwrap_optional_handle(_baseRef handle);
#ifdef __cplusplus
}
#endif
