package mindustry.ai.types;

import arc.math.Angles;
import arc.math.geom.Vec2;
import mindustry.entities.units.AIController;

public class AssemblerAI extends AIController {
    public Vec2 targetPos = new Vec2();
    public float targetAngle;

    @Override
    public void updateMovement() {
        if (!targetPos.isZero()) {
            moveTo(targetPos, 1f, 3f);
        }

        if (unit.within(targetPos, 5f)) {
            unit.lookAt(targetAngle);
        }
    }

    public boolean inPosition() {
        return unit.within(targetPos, 10f) && Angles.within(unit.rotation, targetAngle, 15f);
    }
}
