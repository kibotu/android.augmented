package net.kibotu.android.albert;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import org.jetbrains.annotations.Nullable;

/**
 * Created with IntelliJ IDEA.
 * User: Apu
 * Date: 13.02.13
 * Time: 14:58
 * To change this template use File | Settings | File Templates.
 */
public class Perspective3DCamera extends PerspectiveCamera {

    protected Matrix3 normalMatrix;

    public Perspective3DCamera(float fieldOfView, float viewportWidth, float viewportHeight) {
        super(fieldOfView, viewportWidth, viewportHeight);
        normalMatrix = new Matrix3();
    }

    public void apply(ShaderProgram program) {
        program.begin();
        program.setUniformMatrix("u_ProjectionView", combined);
//        program.setUniformMatrix("u_NormalMatrix", normalMatrix);
        program.end();
    }

    public void update(@Nullable Matrix4 modelViewMatrix) {
        super.update();
//        if(modelViewMatrix == null) normalMatrix.mul(modelViewMatrix);
//        normalMatrix.mul(modelViewMatrix);

    }
}
