precision mediump float;
attribute vec3 a_position;
attribute vec3 a_normal;
uniform mat4 u_ProjectionView;
void main()
{
    gl_Position = u_ProjectionView * vec4(a_position, 1.0);
}