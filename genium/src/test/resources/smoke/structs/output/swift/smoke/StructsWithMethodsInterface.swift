//
//
import Foundation
internal func getRef(_ ref: StructsWithMethodsInterface?, owning: Bool = true) -> RefHolder {
    guard let c_handle = ref?.c_instance else {
        return RefHolder(0)
    }
    let handle_copy = smoke_StructsWithMethodsInterface_copy_handle(c_handle)
    return owning
        ? RefHolder(ref: handle_copy, release: smoke_StructsWithMethodsInterface_release_handle)
        : RefHolder(handle_copy)
}
public class StructsWithMethodsInterface {
    let c_instance : _baseRef
    init(cStructsWithMethodsInterface: _baseRef) {
        guard cStructsWithMethodsInterface != 0 else {
            fatalError("Nullptr value is not supported for initializers")
        }
        c_instance = cStructsWithMethodsInterface
    }
    deinit {
        smoke_StructsWithMethodsInterface_release_handle(c_instance)
    }
    public struct Vector3 {
        public var x: Double
        public var y: Double
        public var z: Double
        internal init(cHandle: _baseRef) {
            x = moveFromCType(smoke_StructsWithMethodsInterface_Vector3_x_get(cHandle))
            y = moveFromCType(smoke_StructsWithMethodsInterface_Vector3_y_get(cHandle))
            z = moveFromCType(smoke_StructsWithMethodsInterface_Vector3_z_get(cHandle))
        }
        public init(input: String) {
            let _result_handle = StructsWithMethodsInterface.Vector3.create(input: input)
            guard _result_handle != 0 else {
                fatalError("Nullptr value is not supported for initializers")
            }
            let _result: StructsWithMethodsInterface.Vector3 = moveFromCType(_result_handle)
            self.x = _result.x
            self.y = _result.y
            self.z = _result.z
        }
        public init(other: StructsWithMethodsInterface.Vector3) throws {
            let _result_handle = try StructsWithMethodsInterface.Vector3.create(other: other)
            guard _result_handle != 0 else {
                fatalError("Nullptr value is not supported for initializers")
            }
            let _result: StructsWithMethodsInterface.Vector3 = moveFromCType(_result_handle)
            self.x = _result.x
            self.y = _result.y
            self.z = _result.z
        }
        public func distanceTo(other: StructsWithMethodsInterface.Vector3) -> Double {
            let c_self_handle = moveToCType(self)
            let c_other = moveToCType(other)
            return moveFromCType(smoke_StructsWithMethodsInterface_Vector3_distanceTo(c_self_handle.ref, c_other.ref))
        }
        public func add(other: StructsWithMethodsInterface.Vector3) -> StructsWithMethodsInterface.Vector3 {
            let c_self_handle = moveToCType(self)
            let c_other = moveToCType(other)
            return moveFromCType(smoke_StructsWithMethodsInterface_Vector3_add(c_self_handle.ref, c_other.ref))
        }
        public static func validate(x: Double, y: Double, z: Double) -> Bool {
            let c_x = moveToCType(x)
            let c_y = moveToCType(y)
            let c_z = moveToCType(z)
            return moveFromCType(smoke_StructsWithMethodsInterface_Vector3_validate(c_x.ref, c_y.ref, c_z.ref))
        }
        private static func create(input: String) -> _baseRef {
            let c_input = moveToCType(input)
            return moveFromCType(smoke_StructsWithMethodsInterface_Vector3_create_String(c_input.ref))
        }
        private static func create(other: StructsWithMethodsInterface.Vector3) throws -> _baseRef {
            let c_other = moveToCType(other)
            let RESULT = smoke_StructsWithMethodsInterface_Vector3_create_Vector3(c_other.ref)
            if (RESULT.has_value) {
                return moveFromCType(RESULT.returned_value)
            } else {
                throw ValidationError(rawValue: RESULT.error_code)!
            }
        }
    }
}
extension StructsWithMethodsInterface: NativeBase {
    var c_handle: _baseRef { return c_instance }
}
internal func StructsWithMethodsInterfacecopyFromCType(_ handle: _baseRef) -> StructsWithMethodsInterface {
    return StructsWithMethodsInterface(cStructsWithMethodsInterface: smoke_StructsWithMethodsInterface_copy_handle(handle))
}
internal func StructsWithMethodsInterfacemoveFromCType(_ handle: _baseRef) -> StructsWithMethodsInterface {
    return StructsWithMethodsInterface(cStructsWithMethodsInterface: handle)
}
internal func StructsWithMethodsInterfacecopyFromCType(_ handle: _baseRef) -> StructsWithMethodsInterface? {
    guard handle != 0 else {
        return nil
    }
    return StructsWithMethodsInterfacemoveFromCType(handle) as StructsWithMethodsInterface
}
internal func StructsWithMethodsInterfacemoveFromCType(_ handle: _baseRef) -> StructsWithMethodsInterface? {
    guard handle != 0 else {
        return nil
    }
    return StructsWithMethodsInterfacemoveFromCType(handle) as StructsWithMethodsInterface
}
internal func copyToCType(_ swiftClass: StructsWithMethodsInterface) -> RefHolder {
    return getRef(swiftClass, owning: false)
}
internal func moveToCType(_ swiftClass: StructsWithMethodsInterface) -> RefHolder {
    return getRef(swiftClass, owning: true)
}
internal func copyToCType(_ swiftClass: StructsWithMethodsInterface?) -> RefHolder {
    return getRef(swiftClass, owning: false)
}
internal func moveToCType(_ swiftClass: StructsWithMethodsInterface?) -> RefHolder {
    return getRef(swiftClass, owning: true)
}
internal func copyFromCType(_ handle: _baseRef) -> StructsWithMethodsInterface.Vector3 {
    return StructsWithMethodsInterface.Vector3(cHandle: handle)
}
internal func moveFromCType(_ handle: _baseRef) -> StructsWithMethodsInterface.Vector3 {
    defer {
        smoke_StructsWithMethodsInterface_Vector3_release_handle(handle)
    }
    return copyFromCType(handle)
}
internal func copyToCType(_ swiftType: StructsWithMethodsInterface.Vector3) -> RefHolder {
    let c_x = moveToCType(swiftType.x)
    let c_y = moveToCType(swiftType.y)
    let c_z = moveToCType(swiftType.z)
    return RefHolder(smoke_StructsWithMethodsInterface_Vector3_create_handle(c_x.ref, c_y.ref, c_z.ref))
}
internal func moveToCType(_ swiftType: StructsWithMethodsInterface.Vector3) -> RefHolder {
    return RefHolder(ref: copyToCType(swiftType).ref, release: smoke_StructsWithMethodsInterface_Vector3_release_handle)
}
internal func copyFromCType(_ handle: _baseRef) -> StructsWithMethodsInterface.Vector3? {
    guard handle != 0 else {
        return nil
    }
    let unwrappedHandle = smoke_StructsWithMethodsInterface_Vector3_unwrap_optional_handle(handle)
    return StructsWithMethodsInterface.Vector3(cHandle: unwrappedHandle) as StructsWithMethodsInterface.Vector3
}
internal func moveFromCType(_ handle: _baseRef) -> StructsWithMethodsInterface.Vector3? {
    defer {
        smoke_StructsWithMethodsInterface_Vector3_release_optional_handle(handle)
    }
    return copyFromCType(handle)
}
internal func copyToCType(_ swiftType: StructsWithMethodsInterface.Vector3?) -> RefHolder {
    guard let swiftType = swiftType else {
        return RefHolder(0)
    }
    let c_x = moveToCType(swiftType.x)
    let c_y = moveToCType(swiftType.y)
    let c_z = moveToCType(swiftType.z)
    return RefHolder(smoke_StructsWithMethodsInterface_Vector3_create_optional_handle(c_x.ref, c_y.ref, c_z.ref))
}
internal func moveToCType(_ swiftType: StructsWithMethodsInterface.Vector3?) -> RefHolder {
    return RefHolder(ref: copyToCType(swiftType).ref, release: smoke_StructsWithMethodsInterface_Vector3_release_optional_handle)
}
