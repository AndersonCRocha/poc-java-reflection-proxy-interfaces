package io.github.andersoncrocha.pocproxyinterfaces.core;

import java.util.List;

public interface Repository<T> {

    T save(T t);

    List<T> findAll();

}
