package io.github.gaming32.lwjgltutorial.obj;

import java.nio.FloatBuffer;

public interface Vertex extends FloatBufferable {
    public float getX();
    public float getY();
    public float getZ();
    public Color getColor();

    @Override
    default public FloatBuffer getInto(FloatBuffer buffer) {
        buffer.put(getX()).put(getY()).put(getZ());
        getColor().getInto(buffer);
        return buffer;
    }

    @Override
    default public int bufferLength() {
        return 6;
    }
}
