package io.github.gaming32.lwjgltutorial.obj;

import java.nio.FloatBuffer;

public interface Triangle extends FloatBufferable {
    public Vertex getV1();
    public Vertex getV2();
    public Vertex getV3();

    @Override
    default public FloatBuffer getInto(FloatBuffer buffer) {
        getV1().getInto(buffer);
        getV2().getInto(buffer);
        getV3().getInto(buffer);
        return buffer;
    }

    @Override
    default public int bufferLength() {
        return 18;
    }
}
