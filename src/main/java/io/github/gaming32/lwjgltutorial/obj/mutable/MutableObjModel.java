package io.github.gaming32.lwjgltutorial.obj.mutable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.gaming32.lwjgltutorial.obj.ObjModel;
import io.github.gaming32.lwjgltutorial.obj.Triangle;
import io.github.gaming32.lwjgltutorial.obj.Vertex;

public final class MutableObjModel implements ObjModel {
    private final List<Vertex> vertices;
    private final List<Triangle> tris;

    public MutableObjModel(Vertex[] vertices, Triangle[] tris) {
        this.vertices = new ArrayList<>(Arrays.asList(vertices));
        this.tris = new ArrayList<>(Arrays.asList(tris));
    }

    public MutableObjModel(List<Vertex> vertices, List<Triangle> tris) {
        this.vertices = vertices;
        this.tris = tris;
    }

    public static MutableObjModel parse(String file) throws IOException {
        return ObjModel.parse(file, MutableObjModel::new);
    }

    public static MutableObjModel parse(File file) throws IOException {
        return ObjModel.parse(file, MutableObjModel::new);
    }

    public static MutableObjModel parse(InputStream input) throws IOException {
        return ObjModel.parse(input, MutableObjModel::new);
    }

    public static MutableObjModel parse(Reader input) throws IOException {
        return ObjModel.parse(input, MutableObjModel::new);
    }

    public Vertex[] getVertices() {
        return vertices.toArray(new Vertex[vertices.size()]);
    }

    public Triangle[] getTris() {
        return tris.toArray(new Triangle[tris.size()]);
    }

    public List<Vertex> getVerticesList() {
        return vertices;
    }

    public List<Triangle> getTrisList() {
        return tris;
    }

    @Override
    public int bufferLength() {
        return tris.size() * 18;
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
        return "MutableObjModel[" + tris.size() + " triangles]";
    }
}
