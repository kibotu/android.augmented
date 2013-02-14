package net.kibotu.android.albert.stl;

import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import org.jetbrains.annotations.NotNull;

/**
 * Author: Jan Rabe
 * E-Mail: cloudgazer3d@gmail.com
 * Date: 14/02/13
 * Time: 14:30
 */
public class STLMaterial extends Material {

    public float shininess;
    public static final String PHONG_MATERIAL = "u_PhongMaterial";

    /**
     * Constructs a new material.
     *
     * @param name The material's name.
     */
    public STLMaterial(String name) {
        super(name);
    }

    public void apply(@NotNull ShaderProgram program) {
        program.begin();
        program.setUniformf(PHONG_MATERIAL + ".ambient", Ambient.r, Ambient.g, Ambient.b, Ambient.a);
        program.setUniformf(PHONG_MATERIAL + ".diffuse", Diffuse.r, Diffuse.g, Diffuse.b, Diffuse.a);
        program.setUniformf(PHONG_MATERIAL + ".specular", Specular.r, Specular.g, Specular.b, Specular.a);
        program.setUniformf(PHONG_MATERIAL + ".emissive", Emissive.r, Emissive.g, Emissive.b, Emissive.a);
        program.setUniformf(PHONG_MATERIAL + ".shininess", shininess);
        program.end();
    }
}
