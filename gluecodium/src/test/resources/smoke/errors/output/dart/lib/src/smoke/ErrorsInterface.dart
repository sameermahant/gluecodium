import 'package:library/src/BuiltInTypes__conversion.dart';
import 'package:library/src/_token_cache.dart' as __lib;
import 'package:library/src/_type_repository.dart' as __lib;
import 'package:library/src/smoke/Payload.dart';
import 'package:library/src/smoke/WithPayloadException.dart';
import 'dart:ffi';
import 'package:ffi/ffi.dart';
import 'package:meta/meta.dart';
import 'package:library/src/_library_context.dart' as __lib;
import 'package:library/src/_library_init.dart' as __lib;
abstract class ErrorsInterface {
  void release() {}
  methodWithErrors();
  methodWithExternalErrors();
  String methodWithErrorsAndReturnValue();
  static methodWithPayloadError();
  static String methodWithPayloadErrorAndReturnValue();
}
enum ErrorsInterface_InternalError {
    errorNone,
    errorFatal
}
// ErrorsInterface_InternalError "private" section, not exported.
int smoke_ErrorsInterface_InternalError_toFfi(ErrorsInterface_InternalError value) {
  switch (value) {
  case ErrorsInterface_InternalError.errorNone:
    return 0;
  break;
  case ErrorsInterface_InternalError.errorFatal:
    return 1;
  break;
  default:
    throw StateError("Invalid enum value $value for ErrorsInterface_InternalError enum.");
  }
}
ErrorsInterface_InternalError smoke_ErrorsInterface_InternalError_fromFfi(int handle) {
  switch (handle) {
  case 0:
    return ErrorsInterface_InternalError.errorNone;
  break;
  case 1:
    return ErrorsInterface_InternalError.errorFatal;
  break;
  default:
    throw StateError("Invalid numeric value $handle for ErrorsInterface_InternalError enum.");
  }
}
void smoke_ErrorsInterface_InternalError_releaseFfiHandle(int handle) {}
final _smoke_ErrorsInterface_InternalError_create_handle_nullable = __lib.nativeLibrary.lookupFunction<
    Pointer<Void> Function(Uint32),
    Pointer<Void> Function(int)
  >('library_smoke_ErrorsInterface_InternalError_create_handle_nullable');
final _smoke_ErrorsInterface_InternalError_release_handle_nullable = __lib.nativeLibrary.lookupFunction<
    Void Function(Pointer<Void>),
    void Function(Pointer<Void>)
  >('library_smoke_ErrorsInterface_InternalError_release_handle_nullable');
final _smoke_ErrorsInterface_InternalError_get_value_nullable = __lib.nativeLibrary.lookupFunction<
    Uint32 Function(Pointer<Void>),
    int Function(Pointer<Void>)
  >('library_smoke_ErrorsInterface_InternalError_get_value_nullable');
Pointer<Void> smoke_ErrorsInterface_InternalError_toFfi_nullable(ErrorsInterface_InternalError value) {
  if (value == null) return Pointer<Void>.fromAddress(0);
  final _handle = smoke_ErrorsInterface_InternalError_toFfi(value);
  final result = _smoke_ErrorsInterface_InternalError_create_handle_nullable(_handle);
  smoke_ErrorsInterface_InternalError_releaseFfiHandle(_handle);
  return result;
}
ErrorsInterface_InternalError smoke_ErrorsInterface_InternalError_fromFfi_nullable(Pointer<Void> handle) {
  if (handle.address == 0) return null;
  final _handle = _smoke_ErrorsInterface_InternalError_get_value_nullable(handle);
  final result = smoke_ErrorsInterface_InternalError_fromFfi(_handle);
  smoke_ErrorsInterface_InternalError_releaseFfiHandle(_handle);
  return result;
}
void smoke_ErrorsInterface_InternalError_releaseFfiHandle_nullable(Pointer<Void> handle) =>
  _smoke_ErrorsInterface_InternalError_release_handle_nullable(handle);
// End of ErrorsInterface_InternalError "private" section.
enum ErrorsInterface_ExternalErrors {
    none,
    boom,
    bust
}
// ErrorsInterface_ExternalErrors "private" section, not exported.
int smoke_ErrorsInterface_ExternalErrors_toFfi(ErrorsInterface_ExternalErrors value) {
  switch (value) {
  case ErrorsInterface_ExternalErrors.none:
    return 0;
  break;
  case ErrorsInterface_ExternalErrors.boom:
    return 1;
  break;
  case ErrorsInterface_ExternalErrors.bust:
    return 2;
  break;
  default:
    throw StateError("Invalid enum value $value for ErrorsInterface_ExternalErrors enum.");
  }
}
ErrorsInterface_ExternalErrors smoke_ErrorsInterface_ExternalErrors_fromFfi(int handle) {
  switch (handle) {
  case 0:
    return ErrorsInterface_ExternalErrors.none;
  break;
  case 1:
    return ErrorsInterface_ExternalErrors.boom;
  break;
  case 2:
    return ErrorsInterface_ExternalErrors.bust;
  break;
  default:
    throw StateError("Invalid numeric value $handle for ErrorsInterface_ExternalErrors enum.");
  }
}
void smoke_ErrorsInterface_ExternalErrors_releaseFfiHandle(int handle) {}
final _smoke_ErrorsInterface_ExternalErrors_create_handle_nullable = __lib.nativeLibrary.lookupFunction<
    Pointer<Void> Function(Uint32),
    Pointer<Void> Function(int)
  >('library_smoke_ErrorsInterface_ExternalErrors_create_handle_nullable');
final _smoke_ErrorsInterface_ExternalErrors_release_handle_nullable = __lib.nativeLibrary.lookupFunction<
    Void Function(Pointer<Void>),
    void Function(Pointer<Void>)
  >('library_smoke_ErrorsInterface_ExternalErrors_release_handle_nullable');
final _smoke_ErrorsInterface_ExternalErrors_get_value_nullable = __lib.nativeLibrary.lookupFunction<
    Uint32 Function(Pointer<Void>),
    int Function(Pointer<Void>)
  >('library_smoke_ErrorsInterface_ExternalErrors_get_value_nullable');
Pointer<Void> smoke_ErrorsInterface_ExternalErrors_toFfi_nullable(ErrorsInterface_ExternalErrors value) {
  if (value == null) return Pointer<Void>.fromAddress(0);
  final _handle = smoke_ErrorsInterface_ExternalErrors_toFfi(value);
  final result = _smoke_ErrorsInterface_ExternalErrors_create_handle_nullable(_handle);
  smoke_ErrorsInterface_ExternalErrors_releaseFfiHandle(_handle);
  return result;
}
ErrorsInterface_ExternalErrors smoke_ErrorsInterface_ExternalErrors_fromFfi_nullable(Pointer<Void> handle) {
  if (handle.address == 0) return null;
  final _handle = _smoke_ErrorsInterface_ExternalErrors_get_value_nullable(handle);
  final result = smoke_ErrorsInterface_ExternalErrors_fromFfi(_handle);
  smoke_ErrorsInterface_ExternalErrors_releaseFfiHandle(_handle);
  return result;
}
void smoke_ErrorsInterface_ExternalErrors_releaseFfiHandle_nullable(Pointer<Void> handle) =>
  _smoke_ErrorsInterface_ExternalErrors_release_handle_nullable(handle);
// End of ErrorsInterface_ExternalErrors "private" section.
class ErrorsInterface_InternalException implements Exception {
  final ErrorsInterface_InternalError error;
  ErrorsInterface_InternalException(this.error);
}
class ErrorsInterface_ExternalException implements Exception {
  final ErrorsInterface_ExternalErrors error;
  ErrorsInterface_ExternalException(this.error);
}
// ErrorsInterface "private" section, not exported.
final _smoke_ErrorsInterface_copy_handle = __lib.nativeLibrary.lookupFunction<
    Pointer<Void> Function(Pointer<Void>),
    Pointer<Void> Function(Pointer<Void>)
  >('library_smoke_ErrorsInterface_copy_handle');
final _smoke_ErrorsInterface_release_handle = __lib.nativeLibrary.lookupFunction<
    Void Function(Pointer<Void>),
    void Function(Pointer<Void>)
  >('library_smoke_ErrorsInterface_release_handle');
final _smoke_ErrorsInterface_create_proxy = __lib.nativeLibrary.lookupFunction<
    Pointer<Void> Function(Uint64, Int32, Pointer, Pointer, Pointer, Pointer, Pointer, Pointer),
    Pointer<Void> Function(int, int, Pointer, Pointer, Pointer, Pointer, Pointer, Pointer)
  >('library_smoke_ErrorsInterface_create_proxy');
final _smoke_ErrorsInterface_get_raw_pointer = __lib.nativeLibrary.lookupFunction<
      Pointer<Void> Function(Pointer<Void>),
      Pointer<Void> Function(Pointer<Void>)
    >('library_smoke_ErrorsInterface_get_raw_pointer');
final _smoke_ErrorsInterface_get_type_id = __lib.nativeLibrary.lookupFunction<
    Pointer<Void> Function(Pointer<Void>),
    Pointer<Void> Function(Pointer<Void>)
  >('library_smoke_ErrorsInterface_get_type_id');
final _methodWithErrors_return_release_handle = __lib.nativeLibrary.lookupFunction<
    Void Function(Pointer<Void>),
    void Function(Pointer<Void>)
  >('library_smoke_ErrorsInterface_methodWithErrors_return_release_handle');
final _methodWithErrors_return_get_result = (Pointer) {};
final _methodWithErrors_return_get_error = __lib.nativeLibrary.lookupFunction<
    Uint32 Function(Pointer<Void>),
    int Function(Pointer<Void>)
  >('library_smoke_ErrorsInterface_methodWithErrors_return_get_error');
final _methodWithErrors_return_has_error = __lib.nativeLibrary.lookupFunction<
    Uint8 Function(Pointer<Void>),
    int Function(Pointer<Void>)
  >('library_smoke_ErrorsInterface_methodWithErrors_return_has_error');
final _methodWithExternalErrors_return_release_handle = __lib.nativeLibrary.lookupFunction<
    Void Function(Pointer<Void>),
    void Function(Pointer<Void>)
  >('library_smoke_ErrorsInterface_methodWithExternalErrors_return_release_handle');
final _methodWithExternalErrors_return_get_result = (Pointer) {};
final _methodWithExternalErrors_return_get_error = __lib.nativeLibrary.lookupFunction<
    Uint32 Function(Pointer<Void>),
    int Function(Pointer<Void>)
  >('library_smoke_ErrorsInterface_methodWithExternalErrors_return_get_error');
final _methodWithExternalErrors_return_has_error = __lib.nativeLibrary.lookupFunction<
    Uint8 Function(Pointer<Void>),
    int Function(Pointer<Void>)
  >('library_smoke_ErrorsInterface_methodWithExternalErrors_return_has_error');
final _methodWithErrorsAndReturnValue_return_release_handle = __lib.nativeLibrary.lookupFunction<
    Void Function(Pointer<Void>),
    void Function(Pointer<Void>)
  >('library_smoke_ErrorsInterface_methodWithErrorsAndReturnValue_return_release_handle');
final _methodWithErrorsAndReturnValue_return_get_result = __lib.nativeLibrary.lookupFunction<
    Pointer<Void> Function(Pointer<Void>),
    Pointer<Void> Function(Pointer<Void>)
  >('library_smoke_ErrorsInterface_methodWithErrorsAndReturnValue_return_get_result');
final _methodWithErrorsAndReturnValue_return_get_error = __lib.nativeLibrary.lookupFunction<
    Uint32 Function(Pointer<Void>),
    int Function(Pointer<Void>)
  >('library_smoke_ErrorsInterface_methodWithErrorsAndReturnValue_return_get_error');
final _methodWithErrorsAndReturnValue_return_has_error = __lib.nativeLibrary.lookupFunction<
    Uint8 Function(Pointer<Void>),
    int Function(Pointer<Void>)
  >('library_smoke_ErrorsInterface_methodWithErrorsAndReturnValue_return_has_error');
final _methodWithPayloadError_return_release_handle = __lib.nativeLibrary.lookupFunction<
    Void Function(Pointer<Void>),
    void Function(Pointer<Void>)
  >('library_smoke_ErrorsInterface_methodWithPayloadError_return_release_handle');
final _methodWithPayloadError_return_get_result = (Pointer) {};
final _methodWithPayloadError_return_get_error = __lib.nativeLibrary.lookupFunction<
    Pointer<Void> Function(Pointer<Void>),
    Pointer<Void> Function(Pointer<Void>)
  >('library_smoke_ErrorsInterface_methodWithPayloadError_return_get_error');
final _methodWithPayloadError_return_has_error = __lib.nativeLibrary.lookupFunction<
    Uint8 Function(Pointer<Void>),
    int Function(Pointer<Void>)
  >('library_smoke_ErrorsInterface_methodWithPayloadError_return_has_error');
final _methodWithPayloadErrorAndReturnValue_return_release_handle = __lib.nativeLibrary.lookupFunction<
    Void Function(Pointer<Void>),
    void Function(Pointer<Void>)
  >('library_smoke_ErrorsInterface_methodWithPayloadErrorAndReturnValue_return_release_handle');
final _methodWithPayloadErrorAndReturnValue_return_get_result = __lib.nativeLibrary.lookupFunction<
    Pointer<Void> Function(Pointer<Void>),
    Pointer<Void> Function(Pointer<Void>)
  >('library_smoke_ErrorsInterface_methodWithPayloadErrorAndReturnValue_return_get_result');
final _methodWithPayloadErrorAndReturnValue_return_get_error = __lib.nativeLibrary.lookupFunction<
    Pointer<Void> Function(Pointer<Void>),
    Pointer<Void> Function(Pointer<Void>)
  >('library_smoke_ErrorsInterface_methodWithPayloadErrorAndReturnValue_return_get_error');
final _methodWithPayloadErrorAndReturnValue_return_has_error = __lib.nativeLibrary.lookupFunction<
    Uint8 Function(Pointer<Void>),
    int Function(Pointer<Void>)
  >('library_smoke_ErrorsInterface_methodWithPayloadErrorAndReturnValue_return_has_error');
class ErrorsInterface$Impl implements ErrorsInterface {
  final Pointer<Void> handle;
  ErrorsInterface$Impl(this.handle);
  @override
  void release() => _smoke_ErrorsInterface_release_handle(handle);
  @override
  methodWithErrors() {
    final _methodWithErrors_ffi = __lib.nativeLibrary.lookupFunction<Pointer<Void> Function(Pointer<Void>, Int32), Pointer<Void> Function(Pointer<Void>, int)>('library_smoke_ErrorsInterface_methodWithErrors');
    final _handle = this.handle;
    final __call_result_handle = _methodWithErrors_ffi(_handle, __lib.LibraryContext.isolateId);
    if (_methodWithErrors_return_has_error(__call_result_handle) != 0) {
        final __error_handle = _methodWithErrors_return_get_error(__call_result_handle);
        _methodWithErrors_return_release_handle(__call_result_handle);
        final _error_value = smoke_ErrorsInterface_InternalError_fromFfi(__error_handle);
        smoke_ErrorsInterface_InternalError_releaseFfiHandle(__error_handle);
        throw ErrorsInterface_InternalException(_error_value);
    }
    final __result_handle = _methodWithErrors_return_get_result(__call_result_handle);
    _methodWithErrors_return_release_handle(__call_result_handle);
    final _result = (__result_handle);
    (__result_handle);
    return _result;
  }
  @override
  methodWithExternalErrors() {
    final _methodWithExternalErrors_ffi = __lib.nativeLibrary.lookupFunction<Pointer<Void> Function(Pointer<Void>, Int32), Pointer<Void> Function(Pointer<Void>, int)>('library_smoke_ErrorsInterface_methodWithExternalErrors');
    final _handle = this.handle;
    final __call_result_handle = _methodWithExternalErrors_ffi(_handle, __lib.LibraryContext.isolateId);
    if (_methodWithExternalErrors_return_has_error(__call_result_handle) != 0) {
        final __error_handle = _methodWithExternalErrors_return_get_error(__call_result_handle);
        _methodWithExternalErrors_return_release_handle(__call_result_handle);
        final _error_value = smoke_ErrorsInterface_ExternalErrors_fromFfi(__error_handle);
        smoke_ErrorsInterface_ExternalErrors_releaseFfiHandle(__error_handle);
        throw ErrorsInterface_ExternalException(_error_value);
    }
    final __result_handle = _methodWithExternalErrors_return_get_result(__call_result_handle);
    _methodWithExternalErrors_return_release_handle(__call_result_handle);
    final _result = (__result_handle);
    (__result_handle);
    return _result;
  }
  @override
  String methodWithErrorsAndReturnValue() {
    final _methodWithErrorsAndReturnValue_ffi = __lib.nativeLibrary.lookupFunction<Pointer<Void> Function(Pointer<Void>, Int32), Pointer<Void> Function(Pointer<Void>, int)>('library_smoke_ErrorsInterface_methodWithErrorsAndReturnValue');
    final _handle = this.handle;
    final __call_result_handle = _methodWithErrorsAndReturnValue_ffi(_handle, __lib.LibraryContext.isolateId);
    if (_methodWithErrorsAndReturnValue_return_has_error(__call_result_handle) != 0) {
        final __error_handle = _methodWithErrorsAndReturnValue_return_get_error(__call_result_handle);
        _methodWithErrorsAndReturnValue_return_release_handle(__call_result_handle);
        final _error_value = smoke_ErrorsInterface_InternalError_fromFfi(__error_handle);
        smoke_ErrorsInterface_InternalError_releaseFfiHandle(__error_handle);
        throw ErrorsInterface_InternalException(_error_value);
    }
    final __result_handle = _methodWithErrorsAndReturnValue_return_get_result(__call_result_handle);
    _methodWithErrorsAndReturnValue_return_release_handle(__call_result_handle);
    final _result = String_fromFfi(__result_handle);
    String_releaseFfiHandle(__result_handle);
    return _result;
  }
  @override
  static methodWithPayloadError() {
    final _methodWithPayloadError_ffi = __lib.nativeLibrary.lookupFunction<Pointer<Void> Function(Int32), Pointer<Void> Function(int)>('library_smoke_ErrorsInterface_methodWithPayloadError');
    final __call_result_handle = _methodWithPayloadError_ffi(__lib.LibraryContext.isolateId);
    if (_methodWithPayloadError_return_has_error(__call_result_handle) != 0) {
        final __error_handle = _methodWithPayloadError_return_get_error(__call_result_handle);
        _methodWithPayloadError_return_release_handle(__call_result_handle);
        final _error_value = smoke_Payload_fromFfi(__error_handle);
        smoke_Payload_releaseFfiHandle(__error_handle);
        throw WithPayloadException(_error_value);
    }
    final __result_handle = _methodWithPayloadError_return_get_result(__call_result_handle);
    _methodWithPayloadError_return_release_handle(__call_result_handle);
    final _result = (__result_handle);
    (__result_handle);
    return _result;
  }
  @override
  static String methodWithPayloadErrorAndReturnValue() {
    final _methodWithPayloadErrorAndReturnValue_ffi = __lib.nativeLibrary.lookupFunction<Pointer<Void> Function(Int32), Pointer<Void> Function(int)>('library_smoke_ErrorsInterface_methodWithPayloadErrorAndReturnValue');
    final __call_result_handle = _methodWithPayloadErrorAndReturnValue_ffi(__lib.LibraryContext.isolateId);
    if (_methodWithPayloadErrorAndReturnValue_return_has_error(__call_result_handle) != 0) {
        final __error_handle = _methodWithPayloadErrorAndReturnValue_return_get_error(__call_result_handle);
        _methodWithPayloadErrorAndReturnValue_return_release_handle(__call_result_handle);
        final _error_value = smoke_Payload_fromFfi(__error_handle);
        smoke_Payload_releaseFfiHandle(__error_handle);
        throw WithPayloadException(_error_value);
    }
    final __result_handle = _methodWithPayloadErrorAndReturnValue_return_get_result(__call_result_handle);
    _methodWithPayloadErrorAndReturnValue_return_release_handle(__call_result_handle);
    final _result = String_fromFfi(__result_handle);
    String_releaseFfiHandle(__result_handle);
    return _result;
  }
}
int _ErrorsInterface_methodWithErrors_static(int _token) {
  int _error = 0;
  try {
  (__lib.instanceCache[_token] as ErrorsInterface).methodWithErrors();
  } on ErrorsInterface_InternalException catch(e) {
    _error = smoke_ErrorsInterface_InternalError_toFfi(e.error);
  } finally {
  }
  return _error;
}
int _ErrorsInterface_methodWithExternalErrors_static(int _token) {
  int _error = 0;
  try {
  (__lib.instanceCache[_token] as ErrorsInterface).methodWithExternalErrors();
  } on ErrorsInterface_ExternalException catch(e) {
    _error = smoke_ErrorsInterface_ExternalErrors_toFfi(e.error);
  } finally {
  }
  return _error;
}
int _ErrorsInterface_methodWithErrorsAndReturnValue_static(int _token, Pointer<Pointer<Void>> _result) {
  int _error = 0;
  try {
  final _result_object = (__lib.instanceCache[_token] as ErrorsInterface).methodWithErrorsAndReturnValue();
  _result.value = String_toFfi(_result_object);
  } on ErrorsInterface_InternalException catch(e) {
    _error = smoke_ErrorsInterface_InternalError_toFfi(e.error);
  } finally {
  }
  return _error;
}
int _ErrorsInterface_methodWithPayloadError_static(int _token) {
  int _error = 0;
  try {
  (__lib.instanceCache[_token] as ErrorsInterface).methodWithPayloadError();
  } on WithPayloadException catch(e) {
  } finally {
  }
  return _error;
}
int _ErrorsInterface_methodWithPayloadErrorAndReturnValue_static(int _token, Pointer<Pointer<Void>> _result) {
  int _error = 0;
  try {
  final _result_object = (__lib.instanceCache[_token] as ErrorsInterface).methodWithPayloadErrorAndReturnValue();
  _result.value = String_toFfi(_result_object);
  } on WithPayloadException catch(e) {
  } finally {
  }
  return _error;
}
Pointer<Void> smoke_ErrorsInterface_toFfi(ErrorsInterface value) {
  if (value is ErrorsInterface$Impl) return _smoke_ErrorsInterface_copy_handle(value.handle);
  final result = _smoke_ErrorsInterface_create_proxy(
    __lib.cacheObject(value),
    __lib.LibraryContext.isolateId,
    __lib.uncacheObjectFfi,
    Pointer.fromFunction<Int64 Function(Uint64)>(_ErrorsInterface_methodWithErrors_static, __lib.unknownError),
    Pointer.fromFunction<Int64 Function(Uint64)>(_ErrorsInterface_methodWithExternalErrors_static, __lib.unknownError),
    Pointer.fromFunction<Int64 Function(Uint64, Pointer<Pointer<Void>>)>(_ErrorsInterface_methodWithErrorsAndReturnValue_static, __lib.unknownError),
    Pointer.fromFunction<Int64 Function(Uint64)>(_ErrorsInterface_methodWithPayloadError_static, __lib.unknownError),
    Pointer.fromFunction<Int64 Function(Uint64, Pointer<Pointer<Void>>)>(_ErrorsInterface_methodWithPayloadErrorAndReturnValue_static, __lib.unknownError)
  );
  __lib.reverseCache[_smoke_ErrorsInterface_get_raw_pointer(result)] = value;
  return result;
}
ErrorsInterface smoke_ErrorsInterface_fromFfi(Pointer<Void> handle) {
  final instance = __lib.reverseCache[_smoke_ErrorsInterface_get_raw_pointer(handle)] as ErrorsInterface;
  if (instance != null) return instance;
  final _copied_handle = _smoke_ErrorsInterface_copy_handle(handle);
  final _type_id_handle = _smoke_ErrorsInterface_get_type_id(handle);
  final _type_id = String_fromFfi(_type_id_handle);
  final result = _type_id.isEmpty
    ? ErrorsInterface$Impl(_copied_handle)
    : __lib.typeRepository[_type_id](_copied_handle);
  String_releaseFfiHandle(_type_id_handle);
  return result;
}
void smoke_ErrorsInterface_releaseFfiHandle(Pointer<Void> handle) =>
  _smoke_ErrorsInterface_release_handle(handle);
Pointer<Void> smoke_ErrorsInterface_toFfi_nullable(ErrorsInterface value) =>
  value != null ? smoke_ErrorsInterface_toFfi(value) : Pointer<Void>.fromAddress(0);
ErrorsInterface smoke_ErrorsInterface_fromFfi_nullable(Pointer<Void> handle) =>
  handle.address != 0 ? smoke_ErrorsInterface_fromFfi(handle) : null;
void smoke_ErrorsInterface_releaseFfiHandle_nullable(Pointer<Void> handle) =>
  _smoke_ErrorsInterface_release_handle(handle);
// End of ErrorsInterface "private" section.
