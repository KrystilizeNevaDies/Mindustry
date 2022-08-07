package mindustry.graphics.g3d;

import arc.math.geom.Mat3D;

public interface GenericMesh {
    void render(PlanetParams params, Mat3D projection, Mat3D transform);
}
