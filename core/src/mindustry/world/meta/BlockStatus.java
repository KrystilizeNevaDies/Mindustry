package mindustry.world.meta;

import arc.graphics.Color;
import mindustry.graphics.Pal;

public enum BlockStatus {
    active(Color.valueOf("5ce677")),
    noOutput(Color.orange),
    noInput(Pal.remove),
    logicDisable(Color.valueOf("8a73c6"));

    public final Color color;

    BlockStatus(Color color) {
        this.color = color;
    }
}
