package io.github.gaming32.lwjgltutorial.obj;

import java.nio.FloatBuffer;

public interface FloatBufferable {
    public FloatBuffer getInto(FloatBuffer buffer);
    public int bufferLength();
}
