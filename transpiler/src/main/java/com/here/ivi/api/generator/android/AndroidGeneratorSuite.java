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

package com.here.ivi.api.generator.android;

import com.here.ivi.api.OptionReader;
import com.here.ivi.api.generator.baseapi.BaseApiGeneratorSuite;
import com.here.ivi.api.generator.common.GeneratedFile;
import com.here.ivi.api.generator.common.GeneratorSuite;
import com.here.ivi.api.loader.FrancaModelLoader;
import com.here.ivi.api.model.franca.FrancaModel;
import com.here.ivi.api.model.franca.ModelHelper;
import com.here.ivi.api.model.jni.JniContainer;
import com.here.ivi.api.validator.android.AndroidValidator;
import com.here.ivi.api.validator.common.ResourceValidator;
import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.emf.ecore.resource.ResourceSet;

public final class AndroidGeneratorSuite implements GeneratorSuite {

  public static final String GENERATOR_NAMESPACE = "android";
  private static final String CONVERSION_UTILS_HEADER = "android/jni/JniCppConversionUtils.h";
  private static final String CONVERSION_UTILS_CPP = "android/jni/JniCppConversionUtils.cpp";
  public static final String FIELD_ACCESS_UTILS_HEADER = "android/jni/FieldAccessMethods.h";

  private static final String CONVERSION_UTILS_TARGET_DIR = "";

  private final OptionReader.TranspilerOptions transpilerOptions;
  private FrancaModel model;
  private final FrancaModelLoader francaModelLoader;
  private Collection<File> currentFiles;
  private final AndroidValidator validator;

  public AndroidGeneratorSuite(OptionReader.TranspilerOptions transpilerOptions) {
    this.validator = new AndroidValidator();
    this.francaModelLoader = new FrancaModelLoader();
    this.transpilerOptions = transpilerOptions;
  }

  @Override
  public String getName() {
    return "com.here.AndroidGenerator";
  }

  @Override
  public boolean validate() {
    ResourceSet resources = francaModelLoader.getResourceSetProvider().get();
    return ResourceValidator.validate(resources, currentFiles) && validator.validate(model);
  }

  @Override
  public void buildModel(String inputPath) {
    ModelHelper.getFdeplInjector().injectMembers(francaModelLoader);
    currentFiles = FrancaModelLoader.listFilesRecursively(new File(inputPath));
    model = francaModelLoader.load(GeneratorSuite.getSpecPath(), currentFiles);
  }

  @Override
  public List<GeneratedFile> generate() {

    // Generate Java files
    JavaGenerator javaGenerator = new JavaGenerator(transpilerOptions.getJavaPackageList());
    Stream<List<GeneratedFile>> javaFilesStream =
        Stream.concat(
            model.getInterfaces().stream().map(javaGenerator::generateFiles),
            model.getTypeCollections().stream().map(javaGenerator::generateFiles));

    // Generate JNI files
    JavaNativeInterfacesGenerator jniGenerator =
        new JavaNativeInterfacesGenerator(
            transpilerOptions.getJavaPackageList(),
            Collections.singletonList(CONVERSION_UTILS_HEADER));

    //jni models need to be built first as they are required to generate conversion util file
    List<JniContainer> jniContainers =
        model
            .getInterfaces()
            .stream()
            .map(jniGenerator::generateModel)
            .collect(Collectors.toList());

    jniContainers.addAll(
        model
            .getTypeCollections()
            .stream()
            .map(jniGenerator::generateModel)
            .collect(Collectors.toList()));

    Stream<List<GeneratedFile>> jniFilesStream =
        Stream.concat(
            jniContainers
                .stream()
                .filter(jniContainer -> jniContainer.isInterface)
                .map(jniGenerator::generateFiles),
            Stream.of(jniGenerator.generateConversionFiles(jniContainers)));

    // This generator is special in that it generates only one file
    // At the moment it does not need to iterate over all interfaces
    AndroidManifestGenerator androidManifestGenerator =
        new AndroidManifestGenerator(transpilerOptions.getJavaPackageList());

    List<GeneratedFile> results = androidManifestGenerator.generate();
    results.add(
        BaseApiGeneratorSuite.copyTarget(CONVERSION_UTILS_HEADER, CONVERSION_UTILS_TARGET_DIR));
    results.add(
        BaseApiGeneratorSuite.copyTarget(CONVERSION_UTILS_CPP, CONVERSION_UTILS_TARGET_DIR));
    results.add(
        BaseApiGeneratorSuite.copyTarget(FIELD_ACCESS_UTILS_HEADER, CONVERSION_UTILS_TARGET_DIR));
    results.addAll(
        Stream.concat(javaFilesStream, jniFilesStream)
            .flatMap(Collection::stream)
            .collect(Collectors.toList()));

    return results;
  }
}
