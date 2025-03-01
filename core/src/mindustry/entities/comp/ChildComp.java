package mindustry.entities.comp;

import arc.math.Angles;
import arc.util.Nullable;
import mindustry.annotations.Annotations.Component;
import mindustry.annotations.Annotations.Import;
import mindustry.gen.*;

@Component
abstract class ChildComp implements Posc, Rotc {
    @Import
    float x, y, rotation;

    @Nullable
    Posc parent;
    boolean rotWithParent;
    float offsetX, offsetY, offsetPos, offsetRot;

    @Override
    public void add() {
        if (parent != null) {
            offsetX = x - parent.getX();
            offsetY = y - parent.getY();
            if (rotWithParent && parent instanceof Rotc r) {
                offsetPos = -r.rotation();
                offsetRot = rotation - r.rotation();
            }
        }
    }

    @Override
    public void update() {
        if (parent != null) {
            if (rotWithParent && parent instanceof Rotc r) {
                x = parent.getX() + Angles.trnsx(r.rotation() + offsetPos, offsetX, offsetY);
                y = parent.getY() + Angles.trnsy(r.rotation() + offsetPos, offsetX, offsetY);
                rotation = r.rotation() + offsetRot;
            } else {
                x = parent.getX() + offsetX;
                y = parent.getY() + offsetY;
            }
        }
    }
}
