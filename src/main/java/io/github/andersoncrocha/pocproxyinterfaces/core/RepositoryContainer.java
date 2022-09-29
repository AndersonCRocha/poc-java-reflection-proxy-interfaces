package io.github.andersoncrocha.pocproxyinterfaces.core;

import java.lang.reflect.InvocationHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RepositoryContainer {

    private RepositoryContainer() {
        throw new IllegalStateException("Utility class");
    }

    private static final Map<Class<? extends Repository<?>>, Object> REPOSITORIES_PROXIES = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T get(Class<T> repositoryClass) {
        return (T) Objects.requireNonNull(REPOSITORIES_PROXIES.get(repositoryClass));
    }

    public static void register(Class<? extends Repository<?>> repositoryClass, Object proxy) {
        REPOSITORIES_PROXIES.put(repositoryClass, proxy);
    }

}
