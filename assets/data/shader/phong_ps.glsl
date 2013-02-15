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
uniform mat4 u_ProjectionWorldView;
uniform mat4 u_WorldView;

varying vec3 v_VertexToLight;
varying vec3 v_ViewSpacePosition;
varying vec3 v_Normal;

vec3 phong(vec3 n, vec3 v, DirectionalLight light, PhongMaterial material) {

    // ambient part
    vec3 ambient = vec4(material.ambient * light.ambient).rgb;

    // back face towards viewer?
    float ndotv = dot(n,v);
    if(ndotv<0.0)
        return vec3(0,0,0);

    // light enabled?
    if(!light.isOn)
        return ambient;

    // vector from light to current point
    vec3 l = normalize(light.direction);

    // cos of angle between light and surface. 0 = light behind surface
    float ndotl = dot(n,-l);

    // diffuse contribution
    vec3 diffuse = vec4(material.diffuse * light.diffuse).rgb * ndotl;

    // reflected light direction = perfect reflection direction
    vec3 r = reflect(l,n);

    // angle between reflection dir and viewing dir
    float rdotv = max( dot(r,v), 0.0);

    // specular contribution
    vec3 specular = vec4(material.specular * light.specular).rgb * pow(rdotv,material.shininess);

    // return sum of all contributions
    return ambient + diffuse + specular;
}

void main() {

    // normalize normal after projection
    vec3 normalEC = normalize(v_Normal);

    // Required for phong lighting
    vec3 v_VertexToLight = normalize((u_WorldView * vec4(u_DirectionalLight.direction, 0.0)).xyz );

    // do we use a perspective or an orthogonal projection matrix?
    bool usePerspective = u_ProjectionWorldView[2][3] != 0.0;

    // for perspective mode, the viewing direction (in eye coords) points
    // from the vertex to the origin (0,0,0) --> use -ecPosition as direction.
    // for orthogonal mode, the viewing direction is simply (0,0,1)
    vec3 viewdirEC = usePerspective? normalize(-v_ViewSpacePosition.xyz) : vec3(0,0,1);

    // do something fancy with light type
    //int lightType = light.type;

    // calculate color using phong illumination
    vec3 phongColor = phong(normalEC, viewdirEC, u_DirectionalLight, u_PhongMaterial);

    gl_FragColor = vec4(phongColor,1.0);
}
