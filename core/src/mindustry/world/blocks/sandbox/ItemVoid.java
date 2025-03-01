package mindustry.world.blocks.sandbox;

import mindustry.gen.*;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Env;
import mindustry.world.modules.ItemModule;

public class ItemVoid extends Block {

    public ItemVoid(String name) {
        super(name);
        group = BlockGroup.transportation;
        update = solid = acceptsItems = true;
        envEnabled = Env.any;
    }

    public class ItemVoidBuild extends Building {
        // I need a fake item module, because items can't be added to older blocks (breaks saves)
        public ItemModule flowItems = new ItemModule();

        @Override
        public ItemModule flowItems() {
            return flowItems;
        }

        @Override
        public void handleItem(Building source, Item item) {
            flowItems.handleFlow(item, 1);
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            return enabled;
        }
    }
}
