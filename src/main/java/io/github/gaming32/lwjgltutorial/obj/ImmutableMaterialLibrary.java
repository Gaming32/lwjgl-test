package io.github.gaming32.lwjgltutorial.obj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * NO-OP does nothing
 * DO NOT USE
 */
public final class ImmutableMaterialLibrary {
    public static class Material {

    }

    public static ImmutableMaterialLibrary parse(String file) throws IOException {
        try (FileReader reader = new FileReader(file)) {
            return parse(reader);
        }
    }

    public static ImmutableMaterialLibrary parse(File file) throws IOException {
        try (FileReader reader = new FileReader(file)) {
            return parse(reader);
        }
    }

    public static ImmutableMaterialLibrary parse(InputStream input) throws IOException {
        return parse(new InputStreamReader(input));
    }

    public static ImmutableMaterialLibrary parse(Reader input) throws IOException {
        return parse(input, new ObjParseOptions());
    }

    public static ImmutableMaterialLibrary parse(Reader input, ObjParseOptions options) throws IOException {
        BufferedReader reader = input instanceof BufferedReader ? (BufferedReader)input : new BufferedReader(input);

        return new ImmutableMaterialLibrary();
    }
}
