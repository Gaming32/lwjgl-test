package io.github.gaming32.lwjgltutorial.obj.immutable;

import io.github.gaming32.lwjgltutorial.obj.Color;
import io.github.gaming32.lwjgltutorial.obj.Vertex;

public final class ImmutableVertex implements Vertex {
    private final float x, y, z;
    private final ImmutableColor color;

    public ImmutableVertex(float x, float y, float z, Color color) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = ImmutableColor.immutableCopy(color);
    }

    public ImmutableVertex(float x, float y, float z, float r, float g, float b) {
        this(x, y, z, new ImmutableColor(r, g, b));
    }

    public ImmutableVertex(float x, float y, float z) {
        this(x, y, z, Color.WHITE);
    }

    public static ImmutableVertex immutableCopy(Vertex vert) {
        if (vert instanceof ImmutableVertex) {
            return (ImmutableVertex)vert;
        }
        return new ImmutableVertex(vert.getX(), vert.getY(), vert.getZ(), vert.getColor());
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

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "ImmutableVertex[x=" + x + ", y=" + y + ", z=" + z + ", color=" + color + "]";
    }
}
