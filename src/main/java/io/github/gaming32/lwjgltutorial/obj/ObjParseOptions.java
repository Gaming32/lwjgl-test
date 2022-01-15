package io.github.gaming32.lwjgltutorial.obj;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public final class ObjParseOptions {
    public final Map<String, ImmutableMaterialLibrary> loadedMaterialLibraries;

    public ObjParseOptions() {
        loadedMaterialLibraries = new HashMap<>();
    }

    public ObjParseOptions loadMaterialLibrary(String name) throws IOException {
        try (FileReader reader = new FileReader(name)) {
            return loadMaterialLibrary(name, reader);
        }
    }

    public ObjParseOptions loadMaterialLibrary(String name, Reader reader) throws IOException {
        ImmutableMaterialLibrary matlib = ImmutableMaterialLibrary.parse(reader, this);
        loadedMaterialLibraries.put(name, matlib);
        return this;
    }
}
