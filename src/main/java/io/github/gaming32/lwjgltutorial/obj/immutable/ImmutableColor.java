package io.github.gaming32.lwjgltutorial.obj.immutable;

import io.github.gaming32.lwjgltutorial.obj.Color;

public final class ImmutableColor implements Color {
    private final float r, g, b;

    public ImmutableColor(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public static ImmutableColor immutableCopy(Color color) {
        if (color instanceof ImmutableColor) {
            return (ImmutableColor)color;
        }
        return new ImmutableColor(color.getR(), color.getG(), color.getB());
    }

    @Override
    public float getR() {
        return r;
    }

    @Override
    public float getG() {
        return g;
    }

    @Override
    public float getB() {
        return b;
    }

    @Override
    public String toString() {
        return "ImmutableColor[r=" + r + ", g=" + g + ", b=" + b + "]";
    }
}
