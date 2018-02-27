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

package com.here.ivi.api.platform.android;

import com.here.ivi.api.cli.OptionReader;
import com.here.ivi.api.common.CollectionsHelper;
import com.here.ivi.api.generator.androidmanifest.AndroidManifestGenerator;
import com.here.ivi.api.generator.common.GeneratedFile;
import com.here.ivi.api.generator.java.JavaTemplates;
import com.here.ivi.api.generator.jni.JniGenerator;
import com.here.ivi.api.generator.jni.JniNameRules;
import com.here.ivi.api.generator.jni.JniTemplates;
import com.here.ivi.api.model.common.ModelElement;
import com.here.ivi.api.model.franca.FrancaDeploymentModel;
import com.here.ivi.api.model.java.JavaElement;
import com.here.ivi.api.model.java.JavaExceptionClass;
import com.here.ivi.api.model.java.JavaPackage;
import com.here.ivi.api.model.jni.JniContainer;
import com.here.ivi.api.platform.common.GeneratorSuite;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.franca.core.franca.FTypeCollection;

/**
 * Combines generators {@link JniGenerator} and {@link JavaTemplates} to generate Java code and
 * bindings to BaseAPI layer for Java.
 */
public class JavaGeneratorSuite extends GeneratorSuite {

  public static final String GENERATOR_NAME = "java";

  private static final String ARRAY_CONVERSION_UTILS = "ArrayConversionUtils";
  private static final String CPP_PROXY_BASE = "CppProxyBase";
  public static final String FIELD_ACCESS_UTILS = "FieldAccessMethods";
  private static final String JNI_BASE = "JniBase";
  private static final String JNI_CPP_CONVERSION_UTILS = "JniCppConversionUtils";

  private static final List<String> UTILS_IMPL_FILES =
      Arrays.asList(ARRAY_CONVERSION_UTILS, CPP_PROXY_BASE, JNI_BASE, JNI_CPP_CONVERSION_UTILS);
  private static final List<String> UTILS_HEADER_FILES =
      Arrays.asList(
          ARRAY_CONVERSION_UTILS,
          CPP_PROXY_BASE,
          FIELD_ACCESS_UTILS,
          JNI_BASE,
          JNI_CPP_CONVERSION_UTILS);
  private static final List<String> UTILS_HEADER_INCLUDES =
      Arrays.asList(CPP_PROXY_BASE, FIELD_ACCESS_UTILS, JNI_BASE, JNI_CPP_CONVERSION_UTILS);

  private static final String NATIVE_BASE_JAVA = "NativeBase.java";

  private final OptionReader.TranspilerOptions transpilerOptions;
  private final boolean enableAndroidFeatures;
  private final String internalNamespace;

  public JavaGeneratorSuite(final OptionReader.TranspilerOptions transpilerOptions) {
    this(transpilerOptions, false);
  }

  protected JavaGeneratorSuite(
      final OptionReader.TranspilerOptions transpilerOptions, final boolean enableAndroidFeatures) {
    super();
    this.transpilerOptions = transpilerOptions;
    this.enableAndroidFeatures = enableAndroidFeatures;
    internalNamespace =
        transpilerOptions != null ? transpilerOptions.getCppInternalNamespace() : null;
  }

  @Override
  public String getName() {
    return "com.here.JavaGeneratorSuite";
  }

  @Override
  public List<GeneratedFile> generate(
      final FrancaDeploymentModel deploymentModel, final List<FTypeCollection> typeCollections) {

    List<String> rootPackage = transpilerOptions.getJavaPackageList();
    List<String> javaPackageList =
        rootPackage != null && !rootPackage.isEmpty()
            ? rootPackage
            : JavaPackage.DEFAULT_PACKAGE_NAMES;

    Map<String, JavaExceptionClass> exceptionsCollector = new HashMap<>();
    JniGenerator jniGenerator =
        new JniGenerator(
            deploymentModel,
            javaPackageList,
            UTILS_HEADER_INCLUDES
                .stream()
                .map(JniNameRules::getHeaderFilePath)
                .collect(Collectors.toList()),
            enableAndroidFeatures,
            internalNamespace);

    Collection<ModelElement> model =
        typeCollections
            .stream()
            .map(typeCollection -> jniGenerator.generateModel(typeCollection, exceptionsCollector))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    List<JavaElement> javaModel = CollectionsHelper.getAllOfType(model, JavaElement.class);
    List<JniContainer> jniModel = CollectionsHelper.getAllOfType(model, JniContainer.class);

    List<GeneratedFile> javaFiles = JavaTemplates.generateFiles(javaModel);
    javaFiles.addAll(JavaTemplates.generateFilesForExceptions(exceptionsCollector.values()));

    List<String> nativeBasePath = new LinkedList<>();
    nativeBasePath.add(getGeneratorName());
    nativeBasePath.addAll(javaPackageList);
    nativeBasePath.add(NATIVE_BASE_JAVA);
    javaFiles.add(
        JavaTemplates.generateNativeBase(String.join("/", nativeBasePath), javaPackageList));

    JniTemplates jniTemplates = new JniTemplates(javaPackageList);
    Stream<List<GeneratedFile>> jniFilesStream =
        Stream.concat(
            jniModel
                .stream()
                .filter(jniContainer -> jniContainer.isFrancaInterface)
                .map(JniTemplates::generateFiles),
            Stream.of(jniTemplates.generateConversionFiles(jniModel)));

    List<GeneratedFile> results = new LinkedList<>();

    if (enableAndroidFeatures) {
      // This generator is special in that it generates only one file
      // At the moment it does not need to iterate over all interfaces
      AndroidManifestGenerator androidManifestGenerator =
          new AndroidManifestGenerator(javaPackageList);
      results.add(androidManifestGenerator.generate());
    }

    results.addAll(
        UTILS_HEADER_FILES
            .stream()
            .map(jniTemplates::generateConversionUtilsHeaderFile)
            .collect(Collectors.toList()));
    results.addAll(
        UTILS_IMPL_FILES
            .stream()
            .map(jniTemplates::generateConversionUtilsImplementationFile)
            .collect(Collectors.toList()));

    results.addAll(javaFiles);
    results.addAll(jniFilesStream.flatMap(Collection::stream).collect(Collectors.toList()));

    return results;
  }

  protected String getGeneratorName() {
    return GENERATOR_NAME;
  }
}
