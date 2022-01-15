package io.github.gaming32.lwjgltutorial.obj.immutable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.github.gaming32.lwjgltutorial.obj.ObjModel;
import io.github.gaming32.lwjgltutorial.obj.Triangle;
import io.github.gaming32.lwjgltutorial.obj.Vertex;

public final class ImmutableObjModel implements ObjModel {
    private final ImmutableVertex[] vertices;
    private final ImmutableTriangle[] tris;

    public ImmutableObjModel(Vertex[] vertices, Triangle[] tris) {
        // Defensive copies
        if (vertices.getClass().getComponentType() == ImmutableVertex.class) {
            this.vertices = Arrays.copyOf(Objects.requireNonNull(vertices), vertices.length, ImmutableVertex[].class);
        } else {
            this.vertices = new ImmutableVertex[Objects.requireNonNull(vertices).length];
            for (int i = 0; i < vertices.length; i++) {
                this.vertices[i] = ImmutableVertex.immutableCopy(vertices[i]);
            }
        }

        if (tris.getClass().getComponentType() == ImmutableTriangle.class) {
            this.tris = Arrays.copyOf(Objects.requireNonNull(tris), tris.length, ImmutableTriangle[].class);
        } else {
            this.tris = new ImmutableTriangle[Objects.requireNonNull(tris).length];
            for (int i = 0; i < tris.length; i++) {
                this.tris[i] = ImmutableTriangle.immutableCopy(tris[i]);
            }
        }
    }

    public ImmutableObjModel(List<Vertex> vertices, List<Triangle> tris) {
        int vertexCount = Objects.requireNonNull(vertices).size();
        if (vertexCount == 0) {
            this.vertices = new ImmutableVertex[0];
        } else {
            if (vertices.get(0) instanceof ImmutableVertex) {
                this.vertices = vertices.toArray(new ImmutableVertex[vertexCount]);
            } else {
                this.vertices = new ImmutableVertex[vertexCount];
                for (int i = 0; i < vertexCount; i++) {
                    this.vertices[i] = ImmutableVertex.immutableCopy(vertices.get(i));
                }
            }
        }

        int triCount = Objects.requireNonNull(tris).size();
        if (triCount == 0) {
            this.tris = new ImmutableTriangle[0];
        } else {
            if (tris.get(0) instanceof ImmutableTriangle) {
                this.tris = tris.toArray(new ImmutableTriangle[triCount]);
            } else {
                this.tris = new ImmutableTriangle[triCount];
                for (int i = 0; i < vertexCount; i++) {
                    this.tris[i] = ImmutableTriangle.immutableCopy(tris.get(i));
                }
            }
        }
    }

    public static ImmutableObjModel parse(String file) throws IOException {
        return ObjModel.parse(file, ImmutableObjModel::new);
    }

    public static ImmutableObjModel parse(File file) throws IOException {
        return ObjModel.parse(file, ImmutableObjModel::new);
    }

    public static ImmutableObjModel parse(InputStream input) throws IOException {
        return ObjModel.parse(input, ImmutableObjModel::new);
    }

    public static ImmutableObjModel parse(Reader input) throws IOException {
        return ObjModel.parse(input, ImmutableObjModel::new);
    }

    public ImmutableVertex[] getVertices() {
        return Arrays.copyOf(vertices, vertices.length); // Defensive copy
    }

    public ImmutableTriangle[] getTris() {
        return Arrays.copyOf(tris, tris.length); // Defensive copy
    }

    @Override
    public int bufferLength() {
        return tris.length * 18;
    }

    @Override
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
