precision mediump float;

struct PhongMaterial {
    vec4 ambient;
    vec4 diffuse;
    vec4 specular;
    vec4 emissive;
    float shininess;
};

struct DirectionalLight {
    vec3 position;
    vec3 direction;
    bool isOn;
    int type;
    vec4 ambient;
    vec4 diffuse;
    vec4 specular;
};

uniform PhongMaterial u_PhongMaterial;
uniform DirectionalLight u_DirectionalLight;
uniform mat4 u_ProjectionMatrix;

varying vec4 ecPosition;
varying vec3 ecNormal;

/*
 Calculate surface color based on Phong illumination model.
 - pos:  position of point on surface, in eye coordinates
 - n:    surface normal at pos
 - v:    direction pointing towards the viewer, in eye coordinates
 + assuming directional light
 */
vec3 phong(vec3 pos, vec3 n, vec3 v,  DirectionalLight light, PhongMaterial material) {

    // ambient part
    vec3 ambient = vec4(material.ambient * light.ambient).rgb;

    // back face towards viewer?
    float ndotv = dot(n,v);
    if(ndotv<0.0)
        return vec3(0,0,0);

    // vector from light to current point
    vec3 l = normalize(light.direction);

    // cos of angle between light and surface. 0 = light behind surface
    float ndotl = dot(n,-l);
    if(ndotl<=0.0)
        return ambient;

    // diffuse contribution
    vec3 diffuse = vec4(material.diffuse * light.diffuse).rgb * ndotl;

     // reflected light direction = perfect reflection direction
    vec3 r = reflect(l,n);

    // angle between reflection dir and viewing dir
    float rdotv = max( dot(r,v), 0.0);

    // specular contribution
    vec3 specular = vec4(material.specular * light.specular).rgb * pow(rdotv, material.shininess);

    // return sum of all contributions
    return ambient + diffuse + specular;

}

void main() {

    // normalize normal after projection
    vec3 normalEC = normalize(ecNormal);

    // do we use a perspective or an orthogonal projection matrix?
    bool usePerspective = u_ProjectionMatrix[2][3] != 0.0;

    // for perspective mode, the viewing direction (in eye coords) points
    // from the vertex to the origin (0,0,0) --> use -ecPosition as direction.
    // for orthogonal mode, the viewing direction is simply (0,0,1)
    vec3 viewdirEC = usePerspective? normalize(-ecPosition.xyz) : vec3(0,0,1);

    // calculate color using phong illumination
    vec3 color = phong( ecPosition.xyz, normalEC, viewdirEC, u_DirectionalLight, u_PhongMaterial );

    // set fragment color
    gl_FragColor = vec4(color, 1.0);
}