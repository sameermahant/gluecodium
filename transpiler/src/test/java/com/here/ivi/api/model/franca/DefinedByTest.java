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

package com.here.ivi.api.model.franca;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.here.ivi.api.TranspilerExecutionException;
import org.eclipse.emf.ecore.EObject;
import org.franca.core.franca.FTypeCollection;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

@SuppressWarnings("MethodName")
@RunWith(JUnit4.class)
public class DefinedByTest {

  @Rule public final ExpectedException expectedException = ExpectedException.none();

  @Mock private EObject eObject;
  @Mock private FTypeCollection fTypeCollection;

  @Before
  public void setUp() {
    initMocks(this);
  }

  @Test
  public void findDefiningTypeCollectionReturnsTypeCollection() {
    // Arrange
    when(fTypeCollection.getName()).thenReturn("MyFTypeCollection");

    // Act
    FTypeCollection definingTypeCollection = DefinedBy.findDefiningTypeCollection(fTypeCollection);

    // Assert
    assertEquals("MyFTypeCollection", definingTypeCollection.getName());
  }

  @Test
  public void findDefiningTypeCollection_returnsParentTypeCollection() {

    // Arrange
    when(fTypeCollection.getName()).thenReturn("MyFTypeCollection");
    when(eObject.eContainer()).thenReturn(fTypeCollection);

    // Act
    FTypeCollection definingTypeCollection = DefinedBy.findDefiningTypeCollection(eObject);

    // Assert
    assertEquals("MyFTypeCollection", definingTypeCollection.getName());
  }

  @Test
  public void findDefiningTypeCollection_throwsException() {
    // Arrange
    when(eObject.eContainer()).thenReturn(null);

    // Expect, Act
    expectedException.expect(TranspilerExecutionException.class);
    DefinedBy.findDefiningTypeCollection(eObject);
  }
}
