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

package com.here.ivi.api.generator.common;

import com.here.ivi.api.common.FrancaTypeHelper;
import org.eclipse.emf.common.util.EList;
import org.franca.core.franca.*;

/**
 * Helper class with predicates for filtering out features that are not supported by Java generator
 * yet.
 */
public final class PlatformUnsupportedFeatures {

  public static final String SWIFT_PLATFORM = "swift";
  public static final String ANDROID_PLATFORM = "android";

  // TODO: remove when APIGEN-735 and APIGEN-729 are implemented
  public static boolean hasUnsupportedParameters(final FMethod francaMethod) {
    return hasUnsupportedParameters(francaMethod, SWIFT_PLATFORM);
  }

  public static boolean hasUnsupportedParameters(
      final FMethod francaMethod, final String platform) {
    EList<FArgument> outArgs = francaMethod.getOutArgs();
    return hasUnsupportedElements(francaMethod.getInArgs())
        || hasUnsupportedElements(outArgs)
        // TODO: remove line below when APIGEN-702 is done
        || (!ANDROID_PLATFORM.equals(platform) && FrancaTypeHelper.hasErrorType(francaMethod))
        || (outArgs != null && outArgs.size() > 1);
  }

  // TODO: remove when APIGEN-735 and APIGEN-729 are implemented
  private static boolean hasUnsupportedElements(
      final EList<? extends FTypedElement> francaTypedElements) {
    return francaTypedElements != null
        && francaTypedElements
            .stream()
            .map(FTypedElement::getType)
            .anyMatch(PlatformUnsupportedFeatures::isUnsupportedType);
  }

  // TODO: remove when APIGEN-735 and APIGEN-729 are implemented
  public static boolean isUnsupportedType(final FTypeRef francaTypeRef) {
    return francaTypeRef != null
        && francaTypeRef.getDerived() != null
        && isUnsupportedType(francaTypeRef.getDerived());
  }

  // TODO: remove when APIGEN-735 and APIGEN-729 are implemented
  public static boolean isUnsupportedType(final FType francaType) {

    if (francaType instanceof FUnionType) {
      return true;
    }

    if (francaType instanceof FStructType) {
      FStructType francaStructType = (FStructType) francaType;
      return hasUnsupportedElements(francaStructType.getElements());
    }

    if (francaType instanceof FTypeDef) {
      return isUnsupportedType(((FTypeDef) francaType).getActualType());
    }

    return false;
  }
}
