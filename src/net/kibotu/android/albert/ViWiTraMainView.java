package net.kibotu.android.albert;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.loaders.obj.ObjLoader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import net.kibotu.android.albert.stl.STLFileObject;
import net.kibotu.android.albert.stl.STLMaterial;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Apu
 * Date: 07.02.13
 * Time: 17:56
 * To change this template use File | Settings | File Templates.
 */
public class ViWiTraMainView implements ApplicationListener, View.OnTouchListener, View.OnKeyListener {

    public static final String TAG = ViWiTraMainView.class.getSimpleName();
    private ShaderProgram shader;
    private Perspective3DCamera perspective3DCamera;
    private Light light;
    private List<STLMaterial> materials;
    private Mesh mesh;
    private Mesh mesh1;
    private Context context;

    public ViWiTraMainView(@NotNull Context context) {
        super();
        // TODO better initialization
        this.shader = null;
        this.perspective3DCamera = null;
        this.light = null;
        this.materials = new ArrayList<STLMaterial>();
        this.mesh = null;
        this.mesh1 = null;
        this.context = context;

        // add input
//        view.setOnTouchListener(InputEngineController.INSTANCE);
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

    @Deprecated // use createAndLoadShader instead
    private static ShaderProgram createShader() {
        // this shader tells opengl where to put things

        String position = ShaderProgram.POSITION_ATTRIBUTE;
        String normal = ShaderProgram.NORMAL_ATTRIBUTE;

        String vertexShader =
                "precision mediump float;      \n"
                        + "attribute vec3 " + position + ";    \n"
                        + "attribute vec3 " + normal + ";    \n"
                        + "uniform mat4 u_ProjectionView;   \n"
                        + "void main()                   \n"
                        + "{                             \n"
                        + "   gl_Position = u_ProjectionView * vec4( " + position + ", 1.0);  \n"
                        + "}                             \n";

        // this one tells it what goes in between the points (i.e
        // colour/texture)
        String fragmentShader =
                "precision mediump float;    \n"
                        + "void main()                 \n"
                        + "{                           \n"
                        + "  gl_FragColor = vec4(1.0,0.0,0.0,1.0);	\n"
                        + "}";

        // make an actual shader from our strings
        ShaderProgram meshShader = new ShaderProgram(vertexShader, fragmentShader);

        // check there's no shader compile errors
        if (meshShader.isCompiled() == false)
            throw new IllegalStateException(meshShader.getLog());

        return meshShader;
    }

    public static STLMaterial createRedMaterial() {
        STLMaterial material = new STLMaterial("red");
        material.Ambient = new Color(0, 0, 0, 255);
        material.Diffuse = new Color(255, 0, 0, 255);
        material.Specular = new Color(128, 128, 128, 255);
        material.Emissive = new Color(0, 0, 0, 255);
        material.shininess = 80;
        return material;
    }

    public static STLMaterial createGreenMaterial() {

        STLMaterial material = new STLMaterial("green");
        material.Ambient = new Color(0, 0, 0, 255);
        material.Diffuse = new Color(0, 255, 0, 255);
        material.Specular = new Color(128, 128, 128, 255);
        material.Emissive = new Color(0, 0, 0, 255);
        material.shininess = 80;
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
//        shader = createShader();
//        shader = loadAndCreateShader("data/shader/default_vs.glsl", "data/shader/default_ps.glsl");
        shader = loadAndCreateShader("data/shader/phong_vs.glsl", "data/shader/phong_ps.glsl");

        // camera
        perspective3DCamera = new Perspective3DCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        perspective3DCamera.position.set(0, 0, 30);
        perspective3DCamera.direction.set(0, 0, -1);
        perspective3DCamera.near = 0.5f;
        perspective3DCamera.far = 300;

        // light source
        light = new Light(Light.LIGHT_DIRECTIONAL_UNIFORM, 0, 0, 30, 0, 0, -1);

        // materials
        materials.add(createRedMaterial());
        materials.add(createGreenMaterial());

        // stl files
        STLFileObject stlFileObject = ViWiTraMain.stlFileObjects.get(0);
        float[] vertices = stlFileObject.getVertices();

        ArrayList<VertexAttribute> attributes = new ArrayList<VertexAttribute>();
        attributes.add(new VertexAttribute(VertexAttributes.Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE));
        attributes.add(new VertexAttribute(VertexAttributes.Usage.Normal, 3, ShaderProgram.NORMAL_ATTRIBUTE));

        // mesh TODO mesh list/scenegraph
        mesh = new Mesh(true, vertices.length / 2 / 3, 0, attributes.toArray(new VertexAttribute[attributes.size()]));
        mesh.setVertices(vertices);

        // random obj file
        mesh1 = createMesh();

        // initial gl settings
        initGL();
    }

    private void initGL() {
        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
//        gl.glDisable(GL10.GL_DITHER);
//        gl.glEnable(GL10.GL_DEPTH_TEST);
//        gl.glEnable(GL10.GL_CULL_FACE);
    }

    @Override
    public void resize(int width, int height) {
        Log.d(TAG, ": resize");
        Gdx.graphics.getGL20().glViewport(0, 0, width, height);
    }

    @Override
    public void render() {
//        Log.d(TAG, TAG + ": render");

        // clear screen
        Gdx.graphics.getGL20().glClearColor(0.3f, 0.3f, 0.3f, 1f);
        // clear framebuffer
        Gdx.graphics.getGL20().glClear(GL20.GL_DEPTH_BUFFER_BIT | GL20.GL_COLOR_BUFFER_BIT | GL20.GL_STENCIL_BUFFER_BIT);

        // camera
        perspective3DCamera.update();
        perspective3DCamera.apply(shader);

        // light
        light.apply(shader);

        // material
        materials.get(0).apply(shader);

        // draw mesh
        if (mesh != null) {
            shader.begin();
            mesh.render(shader, GL20.GL_TRIANGLES);
            mesh1.render(shader, GL20.GL_TRIANGLES);
            shader.end();
        }
    }

    @Override
    public void pause() {
        Log.d(TAG, TAG + ": pause");
    }

    @Override
    public void resume() {
        Log.d(TAG, TAG + ": resume");
    }

    @Override
    public void dispose() {
        Log.d(TAG, TAG + ": dispose");
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        float px = event.getX() / Gdx.graphics.getWidth();
        float py = event.getY() / Gdx.graphics.getHeight();

        return false;
    }
}