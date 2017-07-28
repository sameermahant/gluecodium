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

package com.here.ivi.api.model.cmodel;

import com.here.ivi.api.generator.cbridge.CppTypeInfo;
import java.util.LinkedList;
import java.util.List;

public class CStruct extends CType {

  public CStruct(String name, String baseName, String baseApiName, CppTypeInfo mappedType) {
    super(name);
    this.baseApiName = baseApiName;
    createFunctionName = baseName + "_create";
    releaseFunctionName = baseName + "_release";
    fieldSetterNameTemplate = baseName + "_" + "%s" + "_set";
    fieldGetterNameTemplate = baseName + "_" + "%s" + "_get";
    this.mappedType = mappedType;
    includes.addAll(mappedType.baseTypeIncludes);
  }

  public String getNameOfFieldSetter(String fieldName) {
    return String.format(fieldSetterNameTemplate, fieldName);
  }

  public String getNameOfFieldGetter(String fieldName) {
    return String.format(fieldGetterNameTemplate, fieldName);
  }

  public final String baseApiName;
  public final String createFunctionName;
  public final String releaseFunctionName;
  public final CppTypeInfo mappedType;
  public List<CField> fields = new LinkedList<>();

  private final String fieldSetterNameTemplate;
  private final String fieldGetterNameTemplate;
}
