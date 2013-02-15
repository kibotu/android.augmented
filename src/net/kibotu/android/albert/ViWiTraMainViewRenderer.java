package net.kibotu.android.albert;

import android.content.Context;
import android.util.Log;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.loaders.obj.ObjLoader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import net.kibotu.android.albert.stl.STLFileObject;
import net.kibotu.android.albert.stl.STLMaterial;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Apu
 * Date: 07.02.13
 * Time: 17:56
 */
public class ViWiTraMainViewRenderer implements ApplicationListener, InputProcessor {

    public static final String TAG = ViWiTraMainViewRenderer.class.getSimpleName();
    private Mesh[] meshes;
    private ShaderProgram shader;
    private Perspective3DCamera camera;
    private Light light;
    private List<STLMaterial> materials;
    private Mesh heart;
    private Context context;
    private Matrix4 u_ModelMatrix;

    public ViWiTraMainViewRenderer(@NotNull Context context) {
        super();
        // TODO better initialization
        this.shader = null;
        this.camera = null;
        this.light = null;
        this.materials = new ArrayList<STLMaterial>();
        this.meshes = null;
        this.heart = null;
        this.context = context;
        this.u_ModelMatrix = new Matrix4();
    }

    private static ShaderProgram loadAndCreateShader(@NotNull String vertex, @NotNull String fragment) {
        ShaderProgram meshShader = new ShaderProgram(loadFile(vertex), loadFile(fragment));
        if (meshShader.isCompiled() == false)
            throw new IllegalStateException(meshShader.getLog());
        return meshShader;
    }

    private static String loadFile(@NotNull String filepath) {
        String file = null;
        try {
            file = Gdx.files.internal(filepath).readString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static STLMaterial createRedMaterial() {
        STLMaterial material = new STLMaterial("red");
        material.Ambient = new Color(0, 0, 0, 255);
        material.Diffuse = new Color(255, 0, 0, 255);
        material.Specular = new Color(128, 128, 128, 255);
        material.Emissive = new Color(0, 0, 0, 255);
        material.shininess = 32;
        return material;
    }

    public static STLMaterial createGreenMaterial() {
        STLMaterial material = new STLMaterial("green");
        material.Ambient = new Color(0, 0, 0, 255);
        material.Diffuse = new Color(0, 255, 0, 255);
        material.Specular = new Color(128, 128, 128, 255);
        material.Emissive = new Color(0, 0, 0, 255);
        material.shininess = 32;
        return material;
    }

    public static Mesh createMesh() {

        InputStream stream;
        Mesh mesh = null;
        try {
            stream = Gdx.files.internal("data/models/heart.obj").read();
            mesh = ObjLoader.loadObj(stream, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mesh;
    }

    @Override
    public void create() {
        Log.d(TAG, TAG + ": create");

        // shader
//        shader = loadAndCreateShader("data/shader/default_vs.glsl", "data/shader/default_ps.glsl");
        shader = loadAndCreateShader("data/shader/phong_vs.glsl", "data/shader/phong_ps.glsl");

        // camera
        camera = new Perspective3DCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0, 0, 30);
        camera.direction.set(0, 0, -1);
        camera.near = 0.001f;
        camera.far = 1000f;
        camera.setBackground(new Color(0.3f, 0.3f, 0.3f, 1f));

        // light source
        light = new Light(Light.LIGHT_DIRECTIONAL_UNIFORM, 0, 0, 30, 0, 0, -1);

        // mesh group
        meshes = new Mesh[ViWiTraMain.stlFileObjects.size()];

        // materials
        materials.add(createRedMaterial());
        materials.add(createGreenMaterial());

        // stl files
        for (int i = 0; i < ViWiTraMain.stlFileObjects.size(); ++i) {
            STLFileObject stlFileObject = ViWiTraMain.stlFileObjects.get(i);
            float[] vertices = stlFileObject.getVertices();

            ArrayList<VertexAttribute> attributes = new ArrayList<VertexAttribute>();
            attributes.add(new VertexAttribute(VertexAttributes.Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE));
            attributes.add(new VertexAttribute(VertexAttributes.Usage.Normal, 3, ShaderProgram.NORMAL_ATTRIBUTE));

            meshes[i] = new Mesh(true, vertices.length / 2 / 3, 0, attributes.toArray(new VertexAttribute[attributes.size()]));
            meshes[i].setVertices(vertices);
        }

        // random obj file
        heart = createMesh();

        // initial gl settings
        initGL();

        // register listener
        Gdx.input.setInputProcessor(this);
    }

    private void initGL() {
        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        Gdx.graphics.getGL20().glEnable(GL20.GL_DITHER);
        Gdx.graphics.getGL20().glDisable(GL20.GL_DEPTH_TEST);
        Gdx.graphics.getGL20().glEnable(GL20.GL_CULL_FACE);
        shader.begin();
    }

    @Override
    public void resize(int width, int height) {
        Log.d(TAG, ": resize");
        Gdx.graphics.getGL20().glViewport(0, 0, width, height);
    }

    @Override
    public void render() {
        // clear screen
        camera.clearScreen(Gdx.graphics.getGL20());

        // camera
        camera.update();
        camera.apply(shader);

        // light
        light.position.set(camera.position);
        light.direction.set(camera.direction);
        light.apply(shader);

        // material
        materials.get(0).apply(shader);

        // model matrix TODO scene graph
        shader.setUniformMatrix("u_ModelView", u_ModelMatrix);

        if (meshes != null) {
            for (Mesh mesh : meshes) {
                mesh.render(shader, GL20.GL_TRIANGLES);
            }
            heart.render(shader, GL20.GL_TRIANGLES);
        }
    }

    @Override
    public void pause() {
        Log.d(TAG, TAG + ": pause");
        // TODO remove input listener somehow (otherwise possibly unpredicted behaviour of multiple inputs)
    }

    @Override
    public void resume() {
        Log.d(TAG, TAG + ": resume");
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void dispose() {
        Log.d(TAG, TAG + ": dispose");
        shader.end();
        shader.dispose();
        for (Mesh mesh : meshes) {
            mesh.dispose();
        }
        heart.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        camera.rotate((float) (Gdx.input.getDeltaX()*Math.PI/180f), 0,1,0);
        camera.rotate((float) (Gdx.input.getDeltaY()*Math.PI/180f), 1,0,0);
        return true;
    }

    @Override
    public boolean touchMoved(int x, int y) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}