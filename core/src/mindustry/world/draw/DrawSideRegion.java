package mindustry.world.draw;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.*;
import mindustry.world.Block;

public class DrawSideRegion extends DrawBlock {
    public TextureRegion top1, top2;

    @Override
    public void draw(Building build) {
        Draw.rect(build.rotation > 1 ? top2 : top1, build.x, build.y, build.rotdeg());
    }

    @Override
    public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
        Draw.rect(plan.rotation > 1 ? top2 : top1, plan.drawx(), plan.drawy(), plan.rotation * 90);
    }

    @Override
    public void load(Block block) {
        top1 = Core.atlas.find(block.name + "-top1");
        top2 = Core.atlas.find(block.name + "-top2");
    }

    @Override
    public TextureRegion[] icons(Block block) {
        return new TextureRegion[]{top1};
    }
}
