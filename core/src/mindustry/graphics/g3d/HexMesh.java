package mindustry.graphics.g3d;

import arc.graphics.gl.Shader;
import arc.math.geom.Vec3;
import mindustry.graphics.Shaders;
import mindustry.type.Planet;

public class HexMesh extends PlanetMesh {

    public HexMesh(Planet planet, int divisions) {
        super(
                planet,
                MeshBuilder.buildHex(planet.generator, divisions, false, planet.radius, 0.2f),
                Shaders.planet);
    }

    public HexMesh(Planet planet, HexMesher mesher, int divisions, Shader shader) {
        super(planet, MeshBuilder.buildHex(mesher, divisions, false, planet.radius, 0.2f), shader);
    }

    public HexMesh() {
    }

    @Override
    public void preRender(PlanetParams params) {
        Shaders.planet.planet = planet;
        Shaders.planet
                .lightDir
                .set(planet.solarSystem.position)
                .sub(planet.position)
                .rotate(Vec3.Y, planet.getRotation())
                .nor();
        Shaders.planet.ambientColor.set(planet.solarSystem.lightColor);
    }
}
