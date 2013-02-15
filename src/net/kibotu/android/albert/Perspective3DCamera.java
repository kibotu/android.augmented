package net.kibotu.android.albert;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created with IntelliJ IDEA.
 * User: Apu
 * Date: 13.02.13
 * Time: 14:58
 */
public class Perspective3DCamera extends PerspectiveCamera {

    private Color background;

    public Perspective3DCamera(float fieldOfView, float viewportWidth, float viewportHeight) {
        super(fieldOfView, viewportWidth, viewportHeight);
        background = Color.BLACK;
    }

    public void apply(@NotNull ShaderProgram program) {
        program.setUniformMatrix("u_ProjectionWorldView", combined);
        program.setUniformMatrix("u_ProjectionView", projection);
        program.setUniformMatrix("u_WorldView", view);
    }

    public void clearScreen(@NotNull GL20 gl20) {
        gl20.glClearColor(background.r, background.g, background.b, background.a);
        gl20.glClear(GL20.GL_DEPTH_BUFFER_BIT | GL20.GL_COLOR_BUFFER_BIT | GL20.GL_STENCIL_BUFFER_BIT);
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(@NotNull Color background) {
        this.background = background;
    }
}
