package io.github.andersoncrocha.pocproxyinterfaces.core;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ReflectionUtils {

    private ReflectionUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Set<Class<?>> getClassesInPackage(String packageName) {
        Enumeration<URL> resources = null;
        try {
            resources = ClassLoader.getSystemClassLoader()
                    .getResources(packageName.replaceAll("[.]", "/"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Objects.isNull(resources)) {
            return Collections.emptySet();
        }

        List<File> filesOrDirectories = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            filesOrDirectories.add(new File(resource.getFile()));
        }

        return filesOrDirectories.stream()
                .map(fileOrDirectory -> findClasses(fileOrDirectory, packageName))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private static Set<Class<?>> findClasses(File directory, String packageName) {
        if (!directory.exists()) {
            return Collections.emptySet();
        }

        File[] files = directory.listFiles();

        if (Objects.isNull(files)) {
            return Collections.emptySet();
        }

        return Arrays.stream(files)
                .map(fileOrDirectory -> {
                    String fileOrDirectoryName = fileOrDirectory.getName();
                    if (fileOrDirectory.isDirectory()) {
                        return findClasses(fileOrDirectory, packageName + "." + fileOrDirectoryName);
                    }

                    if (fileOrDirectoryName.endsWith(".class")) {
                        return Collections.singletonList(getClassByCanonicalName(packageName, fileOrDirectoryName));
                    }

                    return new ArrayList<Class<?>>();
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private static Class<?> getClassByCanonicalName(String packageName, String className) {
        try {
            String classNameWithoutExtension = className.substring(0, className.lastIndexOf('.'));
            return Class.forName("%s.%s".formatted(packageName, classNameWithoutExtension));
        } catch (ClassNotFoundException e) {
            System.err.printf("Class not found for name %s in package %s%n", className, packageName);
            return null;
        }
    }

}
