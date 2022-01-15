package io.github.gaming32.lwjgltutorial.obj.mutable;

import io.github.gaming32.lwjgltutorial.obj.Color;
import io.github.gaming32.lwjgltutorial.obj.Vertex;

public final class MutableVertex implements Vertex {
    private float x, y, z;
    private Color color;

    public MutableVertex(float x, float y, float z, Color color) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = color;
    }

    public MutableVertex(float x, float y, float z, float r, float g, float b) {
        this(x, y, z, new MutableColor(r, g, b));
    }

    public MutableVertex(float x, float y, float z) {
        this(x, y, z, Color.WHITE);
    }

    public static MutableVertex mutableCopy(Vertex vert) {
        if (vert instanceof MutableVertex) {
            return (MutableVertex)vert;
        }
        return new MutableVertex(vert.getX(), vert.getY(), vert.getZ(), vert.getColor());
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

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "MutableVertex[x=" + x + ", y=" + y + ", z=" + z + ", color=" + color + "]";
    }
}
