precision mediump float;

uniform mat4 u_ProjectionView;
uniform mat4 u_WorldView;
uniform mat4 u_ModelView;

attribute vec3 a_position;
attribute vec3 a_normal;

varying vec3 v_ViewSpacePosition;
varying vec3 v_Normal;

void main() {

    // The normal graphic's pipeline transform.
    gl_Position = u_ProjectionView * u_WorldView * u_ModelView * vec4(a_position, 1.0);

    v_ViewSpacePosition = (u_WorldView * vec4(a_position, 1.0)).xyz;

    // Only take to view space
    v_Normal = (normalize(u_WorldView * vec4(a_normal, 0.0))).xyz;
}