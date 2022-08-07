package mindustry.world.blocks.heat;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import arc.struct.IntSet;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.draw.DrawBlock;
import mindustry.world.draw.DrawDefault;

public class HeatConductor extends Block {
    public float visualMaxHeat = 15f;
    public DrawBlock drawer = new DrawDefault();

    public HeatConductor(String name) {
        super(name);
        update = solid = rotate = true;
        rotateDraw = false;
        size = 3;
    }

    @Override
    public void setBars() {
        super.setBars();

        // TODO show number
        addBar(
                "heat",
                (HeatConductorBuild entity) ->
                        new Bar(
                                () -> Core.bundle.format("bar.heatamount", (int) entity.heat),
                                () -> Pal.lightOrange,
                                () -> entity.heat / visualMaxHeat));
    }

    @Override
    public void load() {
        super.load();

        drawer.load(this);
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        drawer.drawPlan(this, plan, list);
    }

    @Override
    public TextureRegion[] icons() {
        return drawer.finalIcons(this);
    }

    public class HeatConductorBuild extends Building implements HeatBlock, HeatConsumer {
        public float heat = 0f;
        public float[] sideHeat = new float[4];
        public IntSet cameFrom = new IntSet();

        @Override
        public void draw() {
            drawer.draw(this);
        }

        @Override
        public void drawLight() {
            super.drawLight();
            drawer.drawLight(this);
        }

        @Override
        public float[] sideHeat() {
            return sideHeat;
        }

        @Override
        public float heatRequirement() {
            return visualMaxHeat;
        }

        @Override
        public void updateTile() {
            heat = calculateHeat(sideHeat, cameFrom);
        }

        @Override
        public float warmup() {
            return heat;
        }

        @Override
        public float heat() {
            return heat;
        }

        @Override
        public float heatFrac() {
            return heat / visualMaxHeat;
        }
    }
}
