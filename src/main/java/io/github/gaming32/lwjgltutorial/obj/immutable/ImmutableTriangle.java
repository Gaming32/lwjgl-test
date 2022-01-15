package io.github.gaming32.lwjgltutorial.obj.immutable;

import io.github.gaming32.lwjgltutorial.obj.Triangle;
import io.github.gaming32.lwjgltutorial.obj.Vertex;

public final class ImmutableTriangle implements Triangle {
    private final ImmutableVertex v1, v2, v3;

    public ImmutableTriangle(Vertex v1, Vertex v2, Vertex v3) {
        this.v1 = ImmutableVertex.immutableCopy(v1);
        this.v2 = ImmutableVertex.immutableCopy(v2);
        this.v3 = ImmutableVertex.immutableCopy(v3);
    }

    public ImmutableTriangle(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3) {
        this(
            new ImmutableVertex(x1, y1, z1),
            new ImmutableVertex(x2, y2, z2),
            new ImmutableVertex(x3, y3, z3)
        );
    }

    public static ImmutableTriangle immutableCopy(Triangle tri) {
        if (tri instanceof ImmutableTriangle) {
            return (ImmutableTriangle)tri;
        }
        return new ImmutableTriangle(tri.getV1(), tri.getV2(), tri.getV3());
    }

    @Override
    public ImmutableVertex getV1() {
        return v1;
    }

    @Override
    public ImmutableVertex getV2() {
        return v2;
    }

    @Override
    public ImmutableVertex getV3() {
        return v3;
    }

    @Override
    public String toString() {
        return "ImmutableTriangle[v1=" + v1 + ", v2=" + v2 + ", v3=" + v3 + "]";
    }
}
