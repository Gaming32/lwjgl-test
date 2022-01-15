package io.github.gaming32.lwjgltutorial.obj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

import io.github.gaming32.lwjgltutorial.obj.immutable.ImmutableTriangle;
import io.github.gaming32.lwjgltutorial.obj.immutable.ImmutableVertex;

public interface ObjModel extends FloatBufferable {
    public Vertex[] getVertices();
    public Triangle[] getTris();

    @Override
    default public int bufferLength() {
        return getTris().length * 18;
    }

    @Override
    default public FloatBuffer getInto(FloatBuffer buffer) {
        for (Triangle tri : getTris()) {
            tri.getInto(buffer);
        }
        return buffer;
    }

    public static <T extends ObjModel> T parse(String file, BiFunction<List<Vertex>, List<Triangle>, T> constructor) throws IOException {
        try (FileReader reader = new FileReader(file)) {
            return parse(reader, constructor);
        }
    }

    public static <T extends ObjModel> T parse(File file, BiFunction<List<Vertex>, List<Triangle>, T> constructor) throws IOException {
        try (FileReader reader = new FileReader(file)) {
            return parse(reader, constructor);
        }
    }

    public static <T extends ObjModel> T parse(InputStream input, BiFunction<List<Vertex>, List<Triangle>, T> constructor) throws IOException {
        return parse(new InputStreamReader(input), constructor);
    }

    public static <T extends ObjModel> T parse(Reader input, BiFunction<List<Vertex>, List<Triangle>, T> constructor) throws IOException {
        BufferedReader reader = input instanceof BufferedReader ? (BufferedReader)input : new BufferedReader(input);
        List<Vertex> vertices = new ArrayList<>();
        List<Triangle> tris = new ArrayList<>();

        Pattern whitespaceSplitter = Pattern.compile(" +");

        String line;
        while ((line = reader.readLine()) != null) {
            int commentPos = line.indexOf('#');
            if (commentPos != -1) {
                line = line.substring(0, commentPos); // Remove comment
            }
            line = line.trim();
            int firstSpace = line.indexOf(' ');
            if (firstSpace == -1) {
                if (line.length() > 0) {
                    throw new ObjFormatException("Non-empty line without arguments");
                }
                continue;
            }
            String command = line.substring(0, firstSpace).trim().toLowerCase(Locale.ROOT);
            String commandArgs = line.substring(firstSpace + 1).trim();
            String[] splitArgs = whitespaceSplitter.split(commandArgs, 0);
            switch (command) {
                case "v": {
                    Vertex vert;
                    if (splitArgs.length == 3) {
                        vert = new ImmutableVertex(
                            Float.parseFloat(splitArgs[0]),
                            Float.parseFloat(splitArgs[1]),
                            Float.parseFloat(splitArgs[2])
                        );
                    } else if (splitArgs.length == 6) { // Vertex colors
                        vert = new ImmutableVertex(
                            Float.parseFloat(splitArgs[0]),
                            Float.parseFloat(splitArgs[1]),
                            Float.parseFloat(splitArgs[2]),
                            Float.parseFloat(splitArgs[3]),
                            Float.parseFloat(splitArgs[4]),
                            Float.parseFloat(splitArgs[5])
                        );
                    } else {
                        throw new ObjFormatException("v with " + splitArgs.length + " arguments not supported");
                    }
                    vertices.add(vert);
                    break;
                }
                case "f": {
                    Triangle tri;
                    if (splitArgs.length != 3) {
                        throw new ObjFormatException("f only supports tris");
                    }
                    Vertex[] verts = new Vertex[3];
                    for (int i = 0; i < 3; i++) {
                        String vertRef = splitArgs[i];
                        if (vertRef.indexOf('/') != -1) {
                            throw new ObjFormatException("Only vertices supported in triangles");
                        }
                        verts[i] = vertices.get(Integer.parseInt(vertRef) - 1); // One indexed
                    }
                    tri = new ImmutableTriangle(verts[0], verts[1], verts[2]);
                    tris.add(tri);
                    break;
                }
                case "g":
                    break;
                default:
                    throw new ObjFormatException("Unknown or unsupported command " + command);
            }
        }

        return constructor.apply(vertices, tris);
    }
}
