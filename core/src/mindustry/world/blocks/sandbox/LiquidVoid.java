package mindustry.world.blocks.sandbox;

import mindustry.gen.*;
import mindustry.type.Liquid;
import mindustry.world.Block;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Env;

public class LiquidVoid extends Block {

    public LiquidVoid(String name) {
        super(name);
        hasLiquids = true;
        solid = true;
        update = true;
        group = BlockGroup.liquids;
        envEnabled = Env.any;
        liquidCapacity = 10000f;
    }

    @Override
    public void setBars() {
        super.setBars();
        removeBar("liquid");
    }

    public class LiquidVoidBuild extends Building {
        @Override
        public boolean acceptLiquid(Building source, Liquid liquid) {
            return enabled;
        }

        @Override
        public void handleLiquid(Building source, Liquid liquid, float amount) {
            liquids.handleFlow(liquid, amount);
        }
    }
}
