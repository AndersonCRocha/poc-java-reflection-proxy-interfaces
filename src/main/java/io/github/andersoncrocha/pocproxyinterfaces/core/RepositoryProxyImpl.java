package io.github.andersoncrocha.pocproxyinterfaces.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

public record RepositoryProxyImpl(Class<?> repositoryType) implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        System.out.printf("Proxied method invocation: %s%n", method.getName());
        if (Objects.equals(method.getName(), "findAll")) {
            return InMemoryDatabase.getByType(repositoryType);
        }

        if (Objects.equals(method.getName(), "save")) {
            return InMemoryDatabase.save(args[0]);
        }

        return null;
    }

}
