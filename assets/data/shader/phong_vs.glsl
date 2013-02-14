precision mediump float;
attribute vec3 a_position;
attribute vec3 a_normal;

uniform mat4 u_ProjectionView;

varying vec4 ecPosition;
varying vec3 ecNormal;

void main() {

    // transform vertex position and normal into eye coordinates
    // for lighting calculations
    ecPosition   = vec4(a_position,1.0);
    ecNormal     = normalize(a_normal);

    // set the fragment position in clip coordinates
    gl_Position  = u_ProjectionView * ecPosition;
}