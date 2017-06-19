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

package com.here.ivi.api.model.cppmodel;

import java.util.stream.Stream;

public class CppConstant extends CppElement {

  public final CppType type;
  public final CppValue value;

  public CppConstant(final String name, final CppType type, final CppValue value) {
    super(name);
    this.type = type;
    this.value = value;
  }

  public boolean isValid() {
    return type != null && value != null && type.isValid() && value.isValid();
  }

  @Override
  public Stream<CppElement> stream() {
    return Stream.of(type, value);
  }
}
