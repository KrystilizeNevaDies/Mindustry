package mindustry.world.draw;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.world.Block;

public class DrawRegion extends DrawBlock {
    public TextureRegion region;
    public String suffix = "";
    public boolean spinSprite = false;
    public boolean drawPlan = true;
    public float rotateSpeed, x, y, rotation;
    /**
     * Any number <=0 disables layer changes.
     */
    public float layer = -1;

    public DrawRegion(String suffix) {
        this.suffix = suffix;
    }

    public DrawRegion() {
    }

    @Override
    public void draw(Building build) {
        float z = Draw.z();
        if (layer > 0) Draw.z(layer);
        if (spinSprite) {
            Drawf.spinSprite(
                    region,
                    build.x + x,
                    build.y + y,
                    build.totalProgress() * rotateSpeed + rotation);
        } else {
            Draw.rect(
                    region,
                    build.x + x,
                    build.y + y,
                    build.totalProgress() * rotateSpeed + rotation);
        }
        Draw.z(z);
    }

    @Override
    public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
        if (!drawPlan) return;
        Draw.rect(region, plan.drawx(), plan.drawy());
    }

    @Override
    public TextureRegion[] icons(Block block) {
        return new TextureRegion[]{region};
    }

    @Override
    public void load(Block block) {
        region = Core.atlas.find(block.name + suffix);
    }
}
