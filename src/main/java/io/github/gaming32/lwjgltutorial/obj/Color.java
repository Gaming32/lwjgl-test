package io.github.gaming32.lwjgltutorial.obj;

import java.nio.FloatBuffer;

import io.github.gaming32.lwjgltutorial.obj.immutable.ImmutableColor;

public interface Color extends FloatBufferable {
    public static final Color BLACK = new ImmutableColor(0, 0, 0);
    public static final Color WHITE = new ImmutableColor(1, 1, 1);
    public static final Color RED   = new ImmutableColor(1, 0, 0);
    public static final Color GREEN = new ImmutableColor(0, 1, 0);
    public static final Color BLUE  = new ImmutableColor(0, 0, 1);

    public float getR();
    public float getG();
    public float getB();

    @Override
    default public FloatBuffer getInto(FloatBuffer buffer) {
        return buffer.put(getR()).put(getG()).put(getB());
    }

    @Override
    default public int bufferLength() {
        return 3;
    }
}
