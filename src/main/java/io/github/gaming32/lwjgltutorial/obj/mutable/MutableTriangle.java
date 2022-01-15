package io.github.gaming32.lwjgltutorial.obj.mutable;

import io.github.gaming32.lwjgltutorial.obj.Triangle;
import io.github.gaming32.lwjgltutorial.obj.Vertex;

public final class MutableTriangle implements Triangle {
    private final Vertex v1, v2, v3;

    public MutableTriangle(Vertex v1, Vertex v2, Vertex v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    public MutableTriangle(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3) {
        this(
            new MutableVertex(x1, y1, z1),
            new MutableVertex(x2, y2, z2),
            new MutableVertex(x3, y3, z3)
        );
    }

    public static MutableTriangle mutableCopy(Triangle tri) {
        if (tri instanceof MutableTriangle) {
            return (MutableTriangle)tri;
        }
        return new MutableTriangle(tri.getV1(), tri.getV2(), tri.getV3());
    }

    @Override
    public Vertex getV1() {
        return v1;
    }

    @Override
    public Vertex getV2() {
        return v2;
    }

    @Override
    public Vertex getV3() {
        return v3;
    }

    @Override
    public String toString() {
        return "MutableTriangle[v1=" + v1 + ", v2=" + v2 + ", v3=" + v3 + "]";
    }
}
