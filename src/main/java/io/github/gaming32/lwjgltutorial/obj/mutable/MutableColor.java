package io.github.gaming32.lwjgltutorial.obj.mutable;

import io.github.gaming32.lwjgltutorial.obj.Color;

public final class MutableColor implements Color {
    private float r, g, b;

    public MutableColor(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public static MutableColor mutableCopy(Color color) {
        if (color instanceof MutableColor) {
            return (MutableColor)color;
        }
        return new MutableColor(color.getR(), color.getG(), color.getB());
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

    public void setR(float r) {
        this.r = r;
    }

    public void setG(float g) {
        this.g = g;
    }

    public void setB(float b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "MutableColor[r=" + r + ", g=" + g + ", b=" + b + "]";
    }
}
