package net.kibotu.android.albert;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * Created with IntelliJ IDEA.
 * User: Apu
 * Date: 13.02.13
 * Time: 14:58
 * To change this template use File | Settings | File Templates.
 */
public class Perspective3DCamera extends PerspectiveCamera {

    ShaderProgram shaderProgram;

    public Perspective3DCamera(float fieldOfView, float viewportWidth, float viewportHeight, ShaderProgram shaderProgram) {
        super(fieldOfView, viewportWidth, viewportHeight);
        this.shaderProgram = shaderProgram;
    }

    public void applyShader(GL20 gl) {
        shaderProgram.begin();
        shaderProgram.setUniformMatrix("u_ProjectionView", combined);
        shaderProgram.end();
    }
}
