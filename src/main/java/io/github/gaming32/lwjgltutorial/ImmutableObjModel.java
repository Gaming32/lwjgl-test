package io.github.gaming32.lwjgltutorial;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public final class ImmutableObjModel {
    public static final class Vertex {
        private final float x, y, z, r, g, b;

        public Vertex(float x, float y, float z, float r, float g, float b) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public Vertex(float x, float y, float z) {
            this(x, y, z, 1, 1, 1);
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public float getZ() {
            return z;
        }

        public float getR() {
            return r;
        }

        public float getG() {
            return g;
        }

        public float getB() {
            return b;
        }

        public FloatBuffer getInto(FloatBuffer buffer) {
            return buffer.put(x).put(y).put(z).put(r).put(g).put(b);
        }

        @Override
        public String toString() {
            return "Vertex[x=" + x + ", y=" + y + ", z=" + z + ", r=" + r + ", g=" + g + ", b=" + b + "]";
        }
    }

    public static final class Triangle {
        private final Vertex v1, v2, v3;

        public Triangle(Vertex v1, Vertex v2, Vertex v3) {
            this.v1 = v1;
            this.v2 = v2;
            this.v3 = v3;
        }

        public Triangle(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3) {
            this(
                new Vertex(x1, y1, z1),
                new Vertex(x2, y2, z2),
                new Vertex(x3, y3, z3)
            );
        }

        public Vertex getV1() {
            return v1;
        }

        public Vertex getV2() {
            return v2;
        }

        public Vertex getV3() {
            return v3;
        }

        public FloatBuffer getInto(FloatBuffer buffer) {
            v1.getInto(buffer);
            v2.getInto(buffer);
            v3.getInto(buffer);
            return buffer;
        }

        @Override
        public String toString() {
            return "Triangle[v1=" + v1 + ", v2=" + v2 + ", v3=" + v3 + "]";
        }
    }

    public static class ObjFormatException extends RuntimeException {
        public ObjFormatException(String s) {
            super(s);
        }
    }

    private final Vertex[] vertices;
    private final Triangle[] tris;

    public ImmutableObjModel(Vertex[] vertices, Triangle[] tris) {
        // Defensive copies
        this.vertices = Arrays.copyOf(Objects.requireNonNull(vertices), vertices.length);
        this.tris = Arrays.copyOf(Objects.requireNonNull(tris), tris.length);
    }

    private ImmutableObjModel(List<Vertex> vertices, List<Triangle> tris) {
        this.vertices = vertices.toArray(new Vertex[vertices.size()]);
        this.tris = tris.toArray(new Triangle[tris.size()]);
    }

    public static ImmutableObjModel parse(String file) throws IOException {
        return parse(new FileReader(file));
    }

    public static ImmutableObjModel parse(File file) throws IOException {
        return parse(new FileReader(file));
    }

    public static ImmutableObjModel parse(InputStream input) throws IOException {
        return parse(new InputStreamReader(input));
    }

    public static ImmutableObjModel parse(Reader input) throws IOException {
        BufferedReader reader = input instanceof BufferedReader ? (BufferedReader)input : new BufferedReader(input);
        List<Vertex> vertices = new ArrayList<>();
        List<Triangle> tris = new ArrayList<>();

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
            String[] splitArgs = commandArgs.split("\\s+");
            switch (command) {
                case "v": {
                    Vertex vert;
                    if (splitArgs.length == 3) {
                        vert = new Vertex(
                            Float.parseFloat(splitArgs[0]),
                            Float.parseFloat(splitArgs[1]),
                            Float.parseFloat(splitArgs[2])
                        );
                    } else if (splitArgs.length == 6) { // Vertex colors
                        vert = new Vertex(
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
                    tri = new Triangle(verts[0], verts[1], verts[2]);
                    tris.add(tri);
                    break;
                }
                default:
                    throw new ObjFormatException("Unknown or unsupported command " + command);
            }
        }

        return new ImmutableObjModel(vertices, tris);
    }

    public Vertex[] getVertices() {
        return Arrays.copyOf(vertices, vertices.length); // Defensive copy
    }

    public Triangle[] getTris() {
        return Arrays.copyOf(tris, tris.length); // Defensive copy
    }

    public int bufferLength() {
        return tris.length * 6;
    }

    public FloatBuffer getInto(FloatBuffer buffer) {
        for (Triangle tri : tris) {
            tri.getInto(buffer);
        }
        return buffer;
    }

    @Override
    public String toString() {
        return "ImmutableObjModel[" + tris.length + " triangles]";
    }
}
