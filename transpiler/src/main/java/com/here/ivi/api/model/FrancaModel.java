package com.here.ivi.api.model;

import com.google.common.collect.ImmutableList;
import com.here.ivi.api.loader.SpecAccessorFactory;
import navigation.CppStubSpec;
import org.eclipse.xtext.util.Strings;
import org.franca.core.franca.FInterface;
import org.franca.core.franca.FModel;
import org.franca.core.franca.FTypeCollection;
import org.franca.deploymodel.dsl.fDeploy.FDModel;
import org.franca.deploymodel.dsl.fDeploy.FDSpecification;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FrancaModel<InterfaceAccessor extends CppStubSpec.InterfacePropertyAccessor,
                         TypeCollectionAccessor extends CppStubSpec.TypeCollectionPropertyAccessor> {

    // creates a FrancaModel from the given FModel & FDModel,
    // ensuring that there are PropertyAccessors for each element
    public static <IA extends CppStubSpec.InterfacePropertyAccessor,
                   TA extends CppStubSpec.TypeCollectionPropertyAccessor>
    FrancaModel<IA, TA> create(SpecAccessorFactory<IA, TA> factory, FDSpecification spec, FModel fm, FDModel fdm) {

        ModelInfo info = new ModelInfo(fm);

        // create interface helpers
        ImmutableList<Interface<IA>> interfaces = fm.getInterfaces()
                .parallelStream()
                .map(fi -> Interface.create(factory, spec, info, fi, fdm))
                .collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));

        // create type collection helpers
        ImmutableList<TypeCollection<TA>> typeCollections = fm.getTypeCollections()
                .parallelStream()
                .map(fi -> TypeCollection.create(factory, spec, info, fi, fdm))
                .collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));

        return new FrancaModel<>(interfaces, typeCollections);
    }

    public static <IA extends CppStubSpec.InterfacePropertyAccessor,
                   TA extends CppStubSpec.TypeCollectionPropertyAccessor>
    FrancaModel<IA, TA> joinModels(List<FrancaModel<IA, TA>> models) {
        ImmutableList.Builder<Interface<IA>> interfaces = new ImmutableList.Builder<>();
        ImmutableList.Builder<TypeCollection<TA>> typeCollections = new ImmutableList.Builder<>();
        models.forEach(m -> {
            interfaces.addAll(m.getInterfaces());
            typeCollections.addAll(m.getTypeCollections());
        });
        return new FrancaModel<>(interfaces.build(), typeCollections.build());
    }

    public boolean isEmpty() {
        return getInterfaces().isEmpty() && getTypeCollections().isEmpty();
    }

    public FrancaModel(ImmutableList<Interface<InterfaceAccessor>> interfaces,
                       ImmutableList<TypeCollection<TypeCollectionAccessor>> typeCollections) {
        this.interfaces = interfaces;
        this.typeCollections = typeCollections;
    }

    public ImmutableList<Interface<InterfaceAccessor>> getInterfaces() {
        return interfaces;
    }

    public ImmutableList<TypeCollection<TypeCollectionAccessor>> getTypeCollections() {
        return typeCollections;
    }

    public Optional<? extends FrancaElement> find(DefinedBy definer) {
        return find(definer.model, definer.type);
    }

    public Optional<? extends FrancaElement> find(FModel model, FTypeCollection needle) {
        return needle instanceof FInterface ? findInterface(model, (FInterface)needle) :
                                              findTypeCollection(model, needle);
    }

    private Optional<Interface<InterfaceAccessor>> findInterface(FModel model, FInterface needle) {
        return getInterfaces().stream()
                .filter(i -> i.getName().equals(needle.getName()) && i.getModel().getName().equals(model.getName()))
                .findFirst();
    }

    private Optional<TypeCollection<TypeCollectionAccessor>> findTypeCollection(FModel model, FTypeCollection needle) {
        return getTypeCollections().stream()
                .filter(i -> i.getName().equals(needle.getName()) && i.getModel().getName().equals(model.getName()))
                .findFirst();
    }

    private final ImmutableList<Interface<InterfaceAccessor>> interfaces;
    private final ImmutableList<TypeCollection<TypeCollectionAccessor>> typeCollections;
}
