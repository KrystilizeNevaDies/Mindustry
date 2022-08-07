package mindustry.world.consumers;

import arc.scene.ui.layout.Table;
import mindustry.gen.*;
import mindustry.type.Liquid;
import mindustry.ui.ReqImage;
import mindustry.world.Block;
import mindustry.world.meta.Stat;
import mindustry.world.meta.Stats;

import static mindustry.Vars.iconMed;

// TODO replace with ConsumeLiquids?
public class ConsumeLiquid extends ConsumeLiquidBase {
    public final Liquid liquid;

    public ConsumeLiquid(Liquid liquid, float amount) {
        super(amount);
        this.liquid = liquid;
    }

    protected ConsumeLiquid() {
        this(null, 0f);
    }

    @Override
    public void apply(Block block) {
        super.apply(block);
        block.liquidFilter[liquid.id] = true;
    }

    @Override
    public void build(Building build, Table table) {
        table.add(new ReqImage(liquid.uiIcon, () -> build.liquids.get(liquid) > 0))
                .size(iconMed)
                .top()
                .left();
    }

    @Override
    public void update(Building build) {
        build.liquids.remove(liquid, amount * build.edelta());
    }

    @Override
    public float efficiency(Building build) {
        float ed = build.edelta();
        if (ed <= 0.00000001f) return 0f;
        // there can be more liquid than necessary, so cap at 1
        return Math.min(build.liquids.get(liquid) / (amount * ed), 1f);
    }

    @Override
    public void display(Stats stats) {
        stats.add(booster ? Stat.booster : Stat.input, liquid, amount * 60f, true);
    }
}
