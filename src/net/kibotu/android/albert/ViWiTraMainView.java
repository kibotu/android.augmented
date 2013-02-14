package net.kibotu.android.albert;

import android.util.Log;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.loaders.obj.ObjLoader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import net.kibotu.android.albert.stl.STLFileObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Apu
 * Date: 07.02.13
 * Time: 17:56
 * To change this template use File | Settings | File Templates.
 */
public class ViWiTraMainView implements ApplicationListener {

    public static final String TAG = ViWiTraMainView.class.getSimpleName();
    Perspective3DCamera perspective3DCamera = null;
    ShaderProgram shader = null;
    Mesh[] meshes = null;
    Mesh mesh1 = null;

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
                        + "   gl_Position = u_ProjectionView * vec4( "+ position +", 1.0);  \n"
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

    public static Mesh createMesh() {

        InputStream stream;
        Mesh mesh = null;
        try {
            stream = Gdx.files.internal("data/heart.obj").read();
            mesh = ObjLoader.loadObj(stream, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mesh;
    }

    @Override
    public void create() {
        Log.d(TAG, TAG + ": create");

        shader = createShader();
        perspective3DCamera = new Perspective3DCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), shader);
        perspective3DCamera.position.set(0, 0, 100);
        perspective3DCamera.direction.set(0, 0, -1);
        perspective3DCamera.near = 0.5f;
        perspective3DCamera.far = 300;

        meshes = new Mesh[ViWiTraMain.stlFileObjects.size()];

        for(int i = 0; i < ViWiTraMain.stlFileObjects.size(); ++i) {
            STLFileObject stlFileObject = ViWiTraMain.stlFileObjects.get(i);
            float[] vertices = stlFileObject.getVertices();

            ArrayList<VertexAttribute> attributes = new ArrayList<VertexAttribute>();
            attributes.add(new VertexAttribute(VertexAttributes.Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE));
            attributes.add(new VertexAttribute(VertexAttributes.Usage.Normal, 3, ShaderProgram.NORMAL_ATTRIBUTE));

            meshes[i] = new Mesh(true, vertices.length / 2 / 3, 0, attributes.toArray(new VertexAttribute[attributes.size()]));
            meshes[i].setVertices(vertices);
        }

        mesh1 = createMesh();

        initGL();

    }

    private void initGL() {
        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);

        shader.begin();
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
        Gdx.graphics.getGL20().glClearColor(0.3f, 0.3f, 0.3f, 1f);
        Gdx.graphics.getGL20().glClear(GL20.GL_DEPTH_BUFFER_BIT | GL20.GL_COLOR_BUFFER_BIT | GL20.GL_STENCIL_BUFFER_BIT);

        Gdx.graphics.getGL20().glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        perspective3DCamera.update();
        perspective3DCamera.applyShader(Gdx.graphics.getGL20());

        if (meshes != null) {
            for (Mesh mesh : meshes) {
                mesh.render(shader, GL20.GL_TRIANGLES);
            }
            mesh1.render(shader, GL20.GL_TRIANGLES);
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


}