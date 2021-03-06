package io.github.gaming32.lwjgltutorial;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindFragDataLocation;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import io.github.gaming32.lwjgltutorial.obj.immutable.ImmutableObjModel;

public class App {
    private static final int FLOAT_SIZE = 4;
    private static final Matrix4f IDENTITY_MATRIX = new Matrix4f();

    private static final float FLOAT_PI = (float)Math.PI;
    private static final float DEGREES_180 = FLOAT_PI,
                               DEGREES_90  = FLOAT_PI / 2,
                               DEGREES_360 = FLOAT_PI * 2;

    private static final float MOVE_SPEED = 6;
    private static final float TURN_SPEED = 0.5f;
    private static final float GRAVITY = -1f;
    private static final float JUMP_SPEED = 15;

    private long window;
    private int shaderProgram;
    private Vector2i screenSize;
    private GLFWErrorCallback errorCallback = GLFWErrorCallback.createPrint(System.err);
    private final ImmutableObjModel model;

    private FloatBuffer mainProjectionBuffer;
    private FloatBuffer hudProjectionBuffer;

    private App() throws IOException {
        model = ImmutableObjModel.parse(getClass().getResourceAsStream("/model.obj"));
    }

    private void run() {
        glfwSetErrorCallback(errorCallback);

        if (!glfwInit()) {
            throw new RuntimeException("Failed to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_SAMPLES, 4);

        window = glfwCreateWindow(640, 480, "LWJGL Tutorial", NULL, NULL);
        if (window == NULL) {
            glfwTerminate();
            throw new RuntimeException("Failed to create GLFW window");
        }

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        int vbo;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer vertices = stack.mallocFloat(model.bufferLength());
            model.getInto(vertices);
            vertices.flip();

            vbo = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        }

        int vertexShader = getShader(GL_VERTEX_SHADER, "/shader.vert");
        int fragmentShader = getShader(GL_FRAGMENT_SHADER, "/vertexcolor.frag");

        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glBindFragDataLocation(shaderProgram, 0, "fragColor");
        glLinkProgram(shaderProgram);

        int status = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if (status != GL_TRUE) {
            throw new RuntimeException(glGetProgramInfoLog(shaderProgram));
        }
        glUseProgram(shaderProgram);

        int posAttrib = glGetAttribLocation(shaderProgram, "position");
        glEnableVertexAttribArray(posAttrib);
        glVertexAttribPointer(posAttrib, 3, GL_FLOAT, false, 6 * FLOAT_SIZE, 0);

        int colAttrib = glGetAttribLocation(shaderProgram, "color");
        glEnableVertexAttribArray(colAttrib);
        glVertexAttribPointer(colAttrib, 3, GL_FLOAT, false, 6 * FLOAT_SIZE, 3 * FLOAT_SIZE);

        int uniModel = glGetUniformLocation(shaderProgram, "model");
        FloatBuffer modelMatrixBuffer = MemoryUtil.memAllocFloat(16);
        Matrix4f modelMatrix = new Matrix4f();
        modelMatrix.get(modelMatrixBuffer);
        glUniformMatrix4fv(uniModel, false, modelMatrixBuffer);

        int uniView = glGetUniformLocation(shaderProgram, "view");
        FloatBuffer viewMatrixBuffer = MemoryUtil.memAllocFloat(16);
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.translate(0, 0, -5);
        viewMatrix.get(viewMatrixBuffer);
        glUniformMatrix4fv(uniView, false, viewMatrixBuffer);

        int uniProjection = glGetUniformLocation(shaderProgram, "projection");
        mainProjectionBuffer = MemoryUtil.memAllocFloat(16);
        hudProjectionBuffer = MemoryUtil.memAllocFloat(16);

        glfwSetWindowSizeCallback(window, (window2, width, height) -> {
            resizeView(width, height);
        });
        resizeView(640, 480);

        Vector2d mousePos = getMousePos();
        Vector2f rotation = new Vector2f();

        Vector3f velocity = new Vector3f();
        Vector3f position = new Vector3f(0, 0, -5);
        glfwSetKeyCallback(window, (window2, key, scancode, action, mods) -> {
            if (action == GLFW_PRESS) {
                if (key == GLFW_KEY_W) {
                    velocity.z = MOVE_SPEED;
                } else if (key == GLFW_KEY_S) {
                    velocity.z = -MOVE_SPEED;
                } else if (key == GLFW_KEY_A) {
                    velocity.x = MOVE_SPEED;
                } else if (key == GLFW_KEY_D) {
                    velocity.x = -MOVE_SPEED;
                } else if (key == GLFW_KEY_SPACE) {
                    velocity.y = JUMP_SPEED;
                }
            } else if (action == GLFW_RELEASE) {
                if (key == GLFW_KEY_W || key == GLFW_KEY_S) {
                    velocity.z = 0;
                } else if (key == GLFW_KEY_A || key == GLFW_KEY_D) {
                    velocity.x = 0;
                } else if (key == GLFW_KEY_ESCAPE) {
                    glfwSetWindowShouldClose(window, true);
                }
            }
        });

        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        glEnable(GL_MULTISAMPLE);

        double lastTime = glfwGetTime();
        while (!glfwWindowShouldClose(window)) {
            double time = glfwGetTime();
            double delta = time - lastTime;
            lastTime = time;
            // System.out.print(1 / delta + " FPS                      \r");

            Vector2d relMousePos = getMousePos().sub(mousePos);
            glfwSetCursorPos(window, screenSize.x / 2, screenSize.y / 2);
            mousePos.set(screenSize.x / 2, screenSize.y / 2);

            rotation.x += (float)(relMousePos.y * delta * TURN_SPEED);
            rotation.y -= (float)(relMousePos.x * delta * TURN_SPEED);
            if (rotation.x > DEGREES_90) {
                rotation.x = DEGREES_90;
            } else if (rotation.x < -DEGREES_90) {
                rotation.x = -DEGREES_90;
            }
            velocity.y += GRAVITY;
            position.add(
                new Vector3f()
                    .set(velocity)
                    .rotateY(rotation.y)
                    .mul((float)delta)
            );
            if (position.y < 0) {
                position.y = 0;
                velocity.y = 0;
            }

            viewMatrix.set(IDENTITY_MATRIX);
            viewMatrix.rotateX(rotation.x);
            viewMatrix.rotateY(-rotation.y);
            viewMatrix.translate(position.x, -position.y - 1.8f, position.z);
            // System.out.println(position);
            // viewMatrix.rotateLocalX((float)(relMousePos.y * delta * TURN_SPEED));
            // viewMatrix.rotateLocalY((float)(relMousePos.x * delta * TURN_SPEED));
            // viewMatrix.translateLocal(new Vector3f().set(velocity).mul((float)delta));
            viewMatrix.get(viewMatrixBuffer);
            glUniformMatrix4fv(uniView, false, viewMatrixBuffer);

            // model.rotate((float)(delta * Math.PI * 0.25), 0, 0, 1);
            // model.get(modelBuffer);
            // glUniformMatrix4fv(uniModel, false, modelMatrixBuffer);

            // Start rendering
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glUniformMatrix4fv(uniProjection, false, mainProjectionBuffer);

            // glDepthMask(false);
            // glDisable(GL_DEPTH_TEST);

            glDepthMask(true);
            glEnable(GL_DEPTH_TEST);

            glDrawArrays(GL_TRIANGLES, 0, 6);

            // glUniformMatrix4fv(uniProjection, false, hudProjectionBuffer);
            // glDepthMask(false);
            // glDisable(GL_DEPTH_TEST);
            // Draw HUD


            glfwSwapBuffers(window);
            glfwPollEvents();
        }

        MemoryUtil.memFree(modelMatrixBuffer);
        glfwSetKeyCallback(window, null).free();

        glfwDestroyWindow(window);
        glDeleteVertexArrays(vao);
        glDeleteBuffers(vbo);
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
        glDeleteProgram(shaderProgram);
        glfwTerminate();
        errorCallback.free();
    }

    private void resizeView(int width, int height) {
        screenSize = new Vector2i(width, height);
        glViewport(0, 0, width, height);
        float ratio = (float)width / (float)height;
        Matrix4f projection = new Matrix4f();
        projection.setPerspective((float)Math.toRadians(80), ratio, 0.01f, 1000).get(mainProjectionBuffer);
        projection.setOrtho2D(0, 1, 0, 1).get(hudProjectionBuffer);
    }

    private Vector2d getMousePos() {
        final double[] xPos = new double[1];
        final double[] yPos = new double[1];
        glfwGetCursorPos(window, xPos, yPos);
        return new Vector2d(xPos[0], yPos[0]);
    }

    private int getShader(int type, String file) {
        int shader = glCreateShader(type);
        try {
            glShaderSource(shader, getFileContents(file));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read " + file, e);
        }
        glCompileShader(shader);
        if (glGetShaderi(shader, GL_COMPILE_STATUS) != GL_TRUE) {
            throw new RuntimeException(glGetShaderInfoLog(shader));
        }
        return shader;
    }

    private String getFileContents(String inputFile) throws IOException {
        StringBuilder result = new StringBuilder(8192);
        char[] buffer = new char[8192];
        try (InputStreamReader is = new InputStreamReader(
            getClass().getResourceAsStream(inputFile)
        )) {
            int len;
            while ((len = is.read(buffer)) != -1) {
                result.append(buffer, 0, len);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) throws Exception {
        new App().run();
    }
}
