package net.kibotu.android.albert;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import org.jetbrains.annotations.NotNull;

/**
 * Author: Jan Rabe
 * E-Mail: cloudgazer3d@gmail.com
 * Date: 14/02/13
 * Time: 14:16
 */
public class Light {

    public static final int LIGHT_DIRECTIONAL = 0;
    public static final int LIGHT_POINT = 1;
    public static final int LIGHT_SPOT = 2;
    public static final String LIGHT_DIRECTIONAL_UNIFORM = "u_DirectionalLight";
    public static final String LIGHT_POINT_UNIFORM = "u_PointLight";
    public static final String LIGHT_SPOT_UNIFORM = "u_SpotLight";
    public Vector3 position;
    public Vector3 direction;
    public int type;
    public boolean isOn;
    public Color ambient;
    public Color diffuse;
    public Color specular;
    protected String name;

    public Light(String name, Vector3 position, Vector3 direction, int type, boolean isOn, Color ambient, Color diffuse, Color specular) {
        this.name = name;
        this.position = position;
        this.direction = direction;
        this.type = type;
        this.isOn = isOn;
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
    }

    public Light(String name, float posX, float posY, float posZ, float dirX, float dirY, float dirZ) {
        this(name, new Vector3(posX, posY, posZ), new Vector3(dirX, dirY, dirZ), LIGHT_DIRECTIONAL, true, Color.WHITE, Color.WHITE, Color.WHITE);
    }

    public void apply(@NotNull ShaderProgram program) {
        program.begin();
        program.setUniformi(name + ".type", LIGHT_DIRECTIONAL);
        program.setUniformi(name + ".isOn", isOn ? 1 : 0);
        program.setUniformf(name + ".position", position.x, position.y, position.z);
        program.setUniformf(name + ".ambient", ambient.r, ambient.g, ambient.b, ambient.a);
        program.setUniformf(name + ".diffuse", diffuse.r, diffuse.g, diffuse.b, diffuse.a);
        program.setUniformf(name + ".specular", specular.r, specular.g, specular.b, specular.a);

        switch (type) {
            case 0:
                program.setUniformf(name + ".direction", direction.x, direction.y, direction.z);
                break;
            case 1:
                break; // TODO implement me
            case 2:
                break; // TODO implement me
        }

        program.end();
    }
}
