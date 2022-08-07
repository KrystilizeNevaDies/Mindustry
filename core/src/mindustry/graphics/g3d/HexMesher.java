package mindustry.graphics.g3d;

import arc.graphics.Color;
import arc.math.geom.Vec3;

/**
 * Defines color and height for a planet mesh.
 */
public interface HexMesher {
    float getHeight(Vec3 position);

    Color getColor(Vec3 position);

    default boolean skip(Vec3 position) {
        return false;
    }
}
