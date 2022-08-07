package mindustry.world.draw;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.world.Block;

public class DrawPlasma extends DrawFlame {
    public TextureRegion[] regions;
    public String suffix = "-plasma-";
    public int plasmas = 4;

    public Color plasma1 = Color.valueOf("ffd06b"), plasma2 = Color.valueOf("ff361b");

    @Override
    public void load(Block block) {
        regions = new TextureRegion[plasmas];
        for (int i = 0; i < regions.length; i++) {
            regions[i] = Core.atlas.find(block.name + suffix + i);
        }
    }

    @Override
    public void drawLight(Building build) {
        Drawf.light(
                build.x,
                build.y,
                (110f + Mathf.absin(5, 5f)) * build.warmup(),
                Tmp.c1.set(plasma2).lerp(plasma1, Mathf.absin(7f, 0.2f)),
                0.8f * build.warmup());
    }

    @Override
    public void draw(Building build) {
        Draw.blend(Blending.additive);
        for (int i = 0; i < regions.length; i++) {
            float r =
                    ((float) regions[i].width * Draw.scl
                            - 3f
                            + Mathf.absin(Time.time, 2f + i * 1f, 5f - i * 0.5f));

            Draw.color(plasma1, plasma2, (float) i / regions.length);
            Draw.alpha(
                    (0.3f + Mathf.absin(Time.time, 2f + i * 2f, 0.3f + i * 0.05f))
                            * build.warmup());
            Draw.rect(regions[i], build.x, build.y, r, r, build.totalProgress() * (12 + i * 6f));
        }
        Draw.color();
        Draw.blend();
    }
}
