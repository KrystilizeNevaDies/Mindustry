package mindustry.entities.comp;

import mindustry.annotations.Annotations.Component;
import mindustry.gen.*;

@Component
abstract class DrawComp implements Posc {

    float clipSize() {
        return Float.MAX_VALUE;
    }

    void draw() {
    }
}
