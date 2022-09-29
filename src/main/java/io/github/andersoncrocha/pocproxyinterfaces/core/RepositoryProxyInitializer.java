package io.github.andersoncrocha.pocproxyinterfaces.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class RepositoryProxyInitializer {

    private RepositoryProxyInitializer() {
        throw new IllegalStateException("Utility class");
    }

    @SuppressWarnings("unchecked")
    public static void initialize(Class<?> baseProjectClass) {
        Set<Class<?>> repositories = ReflectionUtils.getClassesInPackage(baseProjectClass.getPackageName())
                .stream()
                .filter(Repository.class::isAssignableFrom)
                .filter(clazz -> clazz != Repository.class)
                .collect(Collectors.toSet());

        repositories.forEach(repository -> {
            Type genericRepositoryInterface = Arrays.stream(repository.getGenericInterfaces())
                    .filter(genericInterface -> {
                        String genericInterfaceFullName = genericInterface.getTypeName();
                        int indexOfFirstGenericType = genericInterfaceFullName.indexOf("<");
                        String genericInterfaceRawName = genericInterfaceFullName.substring(0, indexOfFirstGenericType);
                        return Objects.equals(genericInterfaceRawName, Repository.class.getCanonicalName());
                    })
                    .findFirst()
                    .orElseThrow();

            Class<?> repositoryGenericType =
                    (Class<?>) ((ParameterizedType) genericRepositoryInterface).getActualTypeArguments()[0];
            Object proxiedRepository =
                    Proxy.newProxyInstance(RepositoryContainer.class.getClassLoader(), new Class[]{repository},
                            new RepositoryProxyImpl(repositoryGenericType));

            RepositoryContainer.register((Class<? extends Repository<?>>) repository, proxiedRepository);
        });
    }

}
