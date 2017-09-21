/*
 * Copyright (C) 2017 HERE Global B.V. and its affiliate(s). All rights reserved.
 *
 * This software, including documentation, is protected by copyright controlled by
 * HERE Global B.V. All rights are reserved. Copying, including reproducing, storing,
 * adapting or translating, any or all of this material requires the prior written
 * consent of HERE Global B.V. This material also contains confidential information,
 * which may not be disclosed to others without prior written consent of HERE Global B.V.
 *
 */

package com.here.ivi.api.generator.cbridge;

import com.here.ivi.api.model.cmodel.*;
import com.here.ivi.api.model.common.Include;
import java.util.*;

public class CBridgeComponents {
  public static Set<Include> collectImplementationIncludes(CInterface cInterface) {
    Set<Include> includes = new LinkedHashSet<>();
    for (CFunction function : cInterface.functions) {
      includes.addAll(collectFunctionBodyIncludes(function));
    }
    for (CStruct struct : cInterface.structs) {
      includes.addAll(struct.mappedType.conversionFromCppIncludes);
      includes.addAll(struct.mappedType.conversionToCppIncludes);
      for (CField field : struct.fields) {
        includes.addAll(field.type.conversionFromCppIncludes);
        includes.addAll(field.type.conversionToCppIncludes);
      }
    }
    return includes;
  }

  public static Set<Include> collectPrivateHeaderIncludes(CInterface cInterface) {
    Set<Include> includes = new LinkedHashSet<>();
    for (CStruct struct : cInterface.structs) {
      includes.addAll(struct.mappedType.conversionFromCppIncludes);
    }
    return includes;
  }

  public static Set<Include> collectHeaderIncludes(CInterface cInterface) {
    Set<Include> includes = new LinkedHashSet<>();
    for (CFunction function : cInterface.functions) {
      includes.addAll(collectFunctionSignatureIncludes(function));
    }
    for (CStruct struct : cInterface.structs) {
      for (CField field : struct.fields) {
        includes.addAll(field.type.functionReturnType.includes);
        for (CType type : field.type.cTypesNeededByConstructor) {
          includes.addAll(type.includes);
        }
      }
    }
    return includes;
  }

  public static Set<Include> collectFunctionSignatureIncludes(CFunction function) {
    Set<Include> includes = new LinkedHashSet<>();
    for (CParameter parameter : function.parameters) {
      includes.addAll(parameter.getSignatureIncludes());
    }
    includes.addAll(function.returnType.functionReturnType.includes);
    return includes;
  }

  public static Set<Include> collectFunctionBodyIncludes(CFunction function) {
    Set<Include> includes = new LinkedHashSet<>();
    for (CParameter parameter : function.parameters) {
      includes.addAll(parameter.mappedType.conversionToCppIncludes);
    }
    includes.addAll(function.returnType.conversionFromCppIncludes);
    includes.addAll(function.delegateCallInclude);
    return includes;
  }
}
