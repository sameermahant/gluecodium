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

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import com.here.ivi.api.generator.cbridge.TypeConverter;
import com.here.ivi.api.model.common.Include;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class CFunction extends CElement {

  public final List<? extends CParameter> parameters;
  public final List<TypeConverter.TypeConversion> conversions;
  public final CType returnType;
  public final TypeConverter.TypeConversion returnConversion;
  public final String delegateCall;
  public final Set<Include> delegateCallInclude;
  public boolean internalOnlyFunction;
  public String functionName;
  public boolean interfaceMethod;
  public String definitionTemplate;

  public enum FunctionMember {
    STATIC("cbridge/FunctionDefinition"),
    INSTANCE("cbridge/FunctionInstanceDefinition"),
    POINTER("cbridge/getPointer_impl");

    private final String value;

    FunctionMember(final String value) {
      this.value = value;
    }

    public String toString() {
      return value;
    }
  }

  public CFunction(
      String name,
      List<? extends CParameter> parameters,
      List<TypeConverter.TypeConversion> conversions,
      CType returnType,
      TypeConverter.TypeConversion returnConversion,
      String delegateCall,
      Set<Include> delegateCallInclude,
      String definitionTemplate,
      boolean internalOnlyFunction) {
    super(name);
    this.parameters = parameters;
    this.conversions = conversions;
    this.returnType = returnType;
    this.returnConversion = returnConversion;
    this.delegateCall = delegateCall;
    this.delegateCallInclude = delegateCallInclude;
    this.internalOnlyFunction = internalOnlyFunction;
    this.definitionTemplate = definitionTemplate;
  }

  @SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
  public static class Builder {
    private final String name;
    private List<? extends CParameter> parameters = emptyList();
    private List<TypeConverter.TypeConversion> conversions = emptyList();
    private CType returnType = CType.VOID;
    private TypeConverter.TypeConversion returnConversion;
    private String delegateCall = "";
    private Set<Include> delegateCallInclude = new LinkedHashSet<>();
    private boolean internalOnlyFunction;
    private String definitionTemplate;

    public Builder(String name) {
      this.name = name;
    }

    public CFunction.Builder parameters(List<? extends CParameter> params) {
      this.parameters = params;
      return this;
    }

    public CFunction.Builder returnType(CType returnType) {
      this.returnType = returnType;
      return this;
    }

    public CFunction.Builder returnConversion(TypeConverter.TypeConversion returnConversion) {
      this.returnConversion = returnConversion;
      return this;
    }

    public CFunction.Builder delegateCallTemplate(String template) {
      this.delegateCall = template;
      return this;
    }

    public CFunction.Builder delegateCallIncludes(Set<Include> includes) {
      this.delegateCallInclude = includes;
      return this;
    }

    public CFunction.Builder markAsInternalOnly() {
      this.internalOnlyFunction = true;
      return this;
    }

    public CFunction.Builder functionMember(FunctionMember member) {
      this.definitionTemplate = member.toString();
      return this;
    }

    public CFunction build() {
      if (!parameters.isEmpty()) {
        conversions =
            parameters
                .stream()
                .map(param -> param.conversion)
                .filter(Objects::nonNull)
                .collect(toList());
      }

      if (conversions.isEmpty() && !parameters.isEmpty()) {
        conversions =
            parameters
                .stream()
                .filter(CParameter::filterInstanceParam)
                .map(TypeConverter::identity)
                .collect(toList());
      }
      if (!conversions.isEmpty() && !delegateCall.isEmpty()) {
        delegateCall =
            String.format(delegateCall, conversions.stream().map(it -> it.name).toArray());
      }
      if (returnType != CType.VOID && returnConversion == null) {
        returnConversion = new TypeConverter.TypeConversion("result");
      }
      if (this.definitionTemplate == null) {
        this.definitionTemplate = FunctionMember.STATIC.toString();
      }
      return new CFunction(
          this.name,
          this.parameters,
          this.conversions,
          this.returnType,
          this.returnConversion,
          this.delegateCall,
          this.delegateCallInclude,
          this.definitionTemplate,
          this.internalOnlyFunction);
    }
  }
}
