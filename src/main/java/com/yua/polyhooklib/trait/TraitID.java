package com.yua.polyhooklib.trait;

import java.util.Arrays;

public class TraitID {
    private final String id;

    private TraitID(String id) {
        this.id = id;
    }

    public static TraitID of(String localName) {
        String namespace = extractShallowPackage(getCallerClass().getPackageName());
        return new TraitID(namespace + ":" + localName);
    }

    public String id() {
        return id;
    }

    private static Class<?> getCallerClass() {
        return StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
                .walk(frames -> frames.skip(2).findFirst().get().getDeclaringClass());
    }

    private static String extractShallowPackage(String fullPackage) {
        // example: "com.yua.polyhooklib.trait.internal" → "com.yua.polyhooklib"
        String[] parts = fullPackage.split("\\.");
        int len = Math.min(parts.length, 3);
        return String.join(".", Arrays.copyOf(parts, len));
    }

    @Override public boolean equals(Object o) { return o instanceof TraitID tid && tid.id.equals(this.id); }
    @Override public int hashCode() { return id.hashCode(); }
    @Override public String toString() { return "TraitID(" + id + ")"; }
}

