import 'dart:ffi';
import 'package:library/src/_library_context.dart' as __lib;
import 'package:library/src/_native_base.dart' as __lib;
import 'package:library/src/_token_cache.dart' as __lib;
import 'package:library/src/builtin_types__conversion.dart';
/// This is some very useful interface.
///
/// There is a lot to say about this interface.
/// at least it has multiline comments.
///
/// I am a heading
/// --------------
///
/// And now comes a list:
/// * asterisk
/// * needs
/// * escaping
///
/// ```Some example code;```
abstract class MultiLineComments {
  /// Destroys the underlying native object.
  ///
  /// Call this to free memory when you no longer need this instance.
  /// Note that setting the instance to null will not destroy the underlying native object.
  void release();
  /// This is very important method.
  ///
  /// It has very important parameters.
  /// It has side effects.
  ///
  /// [input] Very useful input parameter.
  /// You must not confuse it with the second parameter.
  /// But they are similar.
  ///
  /// [ratio] Not as useful as the first parameter.
  /// But still useful.
  /// use a positive value for more happiness.
  ///
  /// Returns [double]. If you provide a useful input,
  /// and a useful ratio you can expect a useful output.
  /// Just kidding do not expect anything from a method until
  /// you see its body.
  ///
  double someMethodWithLongComment(String input, double ratio);
}
// MultiLineComments "private" section, not exported.
final _smokeMultilinecommentsCopyHandle = __lib.catchArgumentError(() => __lib.nativeLibrary.lookupFunction<
    Pointer<Void> Function(Pointer<Void>),
    Pointer<Void> Function(Pointer<Void>)
  >('library_smoke_MultiLineComments_copy_handle'));
final _smokeMultilinecommentsReleaseHandle = __lib.catchArgumentError(() => __lib.nativeLibrary.lookupFunction<
    Void Function(Pointer<Void>),
    void Function(Pointer<Void>)
  >('library_smoke_MultiLineComments_release_handle'));
class MultiLineComments$Impl extends __lib.NativeBase implements MultiLineComments {
  MultiLineComments$Impl(Pointer<Void> handle) : super(handle);
  @override
  void release() {
    if (handle == null) return;
    __lib.uncacheObject(this);
    __lib.ffiUncacheToken(handle, __lib.LibraryContext.isolateId);
    _smokeMultilinecommentsReleaseHandle(handle);
    handle = null;
  }
  @override
  double someMethodWithLongComment(String input, double ratio) {
    final _someMethodWithLongCommentFfi = __lib.catchArgumentError(() => __lib.nativeLibrary.lookupFunction<Float Function(Pointer<Void>, Int32, Pointer<Void>, Double), double Function(Pointer<Void>, int, Pointer<Void>, double)>('library_smoke_MultiLineComments_someMethodWithLongComment__String_Double'));
    final _inputHandle = stringToFfi(input);
    final _ratioHandle = (ratio);
    final _handle = this.handle;
    final __resultHandle = _someMethodWithLongCommentFfi(_handle, __lib.LibraryContext.isolateId, _inputHandle, _ratioHandle);
    stringReleaseFfiHandle(_inputHandle);
    try {
      return (__resultHandle);
    } finally {
    }
  }
}
Pointer<Void> smokeMultilinecommentsToFfi(MultiLineComments value) =>
  _smokeMultilinecommentsCopyHandle((value as __lib.NativeBase).handle);
MultiLineComments smokeMultilinecommentsFromFfi(Pointer<Void> handle) {
  final isolateId = __lib.LibraryContext.isolateId;
  final token = __lib.ffiGetCachedToken(handle, isolateId);
  final instance = __lib.instanceCache[token] as MultiLineComments;
  if (instance != null) return instance;
  final _copiedHandle = _smokeMultilinecommentsCopyHandle(handle);
  final result = MultiLineComments$Impl(_copiedHandle);
  __lib.ffiCacheToken(_copiedHandle, isolateId, __lib.cacheObject(result));
  return result;
}
void smokeMultilinecommentsReleaseFfiHandle(Pointer<Void> handle) =>
  _smokeMultilinecommentsReleaseHandle(handle);
Pointer<Void> smokeMultilinecommentsToFfiNullable(MultiLineComments value) =>
  value != null ? smokeMultilinecommentsToFfi(value) : Pointer<Void>.fromAddress(0);
MultiLineComments smokeMultilinecommentsFromFfiNullable(Pointer<Void> handle) =>
  handle.address != 0 ? smokeMultilinecommentsFromFfi(handle) : null;
void smokeMultilinecommentsReleaseFfiHandleNullable(Pointer<Void> handle) =>
  _smokeMultilinecommentsReleaseHandle(handle);
// End of MultiLineComments "private" section.
