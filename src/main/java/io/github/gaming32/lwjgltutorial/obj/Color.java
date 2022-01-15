package io.github.gaming32.lwjgltutorial.obj;

import java.nio.FloatBuffer;

public final class Color implements FloatBufferable {
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color WHITE = new Color(1, 1, 1);
    public static final Color RED   = new Color(1, 0, 0);
    public static final Color GREEN = new Color(0, 1, 0);
    public static final Color BLUE  = new Color(0, 0, 1);

    private final float r, g, b;

    public Color(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
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

    @Override
    public FloatBuffer getInto(FloatBuffer buffer) {
        return buffer.put(r).put(g).put(b);
    }

    @Override
    public String toString() {
        return "Color[r=" + r + ", g=" + g + ", b=" + b + "]";
    }
}
