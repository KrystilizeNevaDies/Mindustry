package mindustry.world.blocks.power;

import arc.math.Mathf;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.heat.HeatBlock;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawHeatOutput;
import mindustry.world.draw.DrawMulti;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

public class HeaterGenerator extends ConsumeGenerator {
    public float heatOutput = 10f;
    public float warmupRate = 0.15f;

    public HeaterGenerator(String name) {
        super(name);

        drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput());
        rotateDraw = false;
        rotate = true;
        canOverdrive = false;
        drawArrow = true;
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.add(Stat.output, heatOutput, StatUnit.heatUnits);
    }

    @Override
    public boolean rotatedOutput(int x, int y) {
        return false;
    }

    @Override
    public void setBars() {
        super.setBars();

        addBar(
                "heat",
                (HeaterGeneratorBuild entity) ->
                        new Bar("bar.heat", Pal.lightOrange, () -> entity.heat / heatOutput));
    }

    public class HeaterGeneratorBuild extends ConsumeGeneratorBuild implements HeatBlock {
        public float heat;

        @Override
        public void updateTile() {
            super.updateTile();

            // heat approaches target at the same speed regardless of efficiency
            heat = Mathf.approachDelta(heat, heatOutput * efficiency, warmupRate * delta());
        }

        @Override
        public float heatFrac() {
            return heat / heatOutput;
        }

        @Override
        public float heat() {
            return heat;
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.f(heat);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            heat = read.f();
        }
    }
}
