package mindustry.world.blocks.distribution;

import arc.math.Mathf;
import arc.util.Nullable;
import arc.util.io.Reads;
import mindustry.gen.*;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.DirectionalItemBuffer;
import mindustry.world.meta.BlockGroup;

public class OverflowGate extends Block {
    public float speed = 1f;
    public boolean invert = false;

    public OverflowGate(String name) {
        super(name);
        hasItems = true;
        underBullets = true;
        update = false;
        destructible = true;
        group = BlockGroup.transportation;
        instantTransfer = true;
        unloadable = false;
        canOverdrive = false;
        itemCapacity = 0;
    }

    @Override
    public boolean outputsItems() {
        return true;
    }

    public class OverflowGateBuild extends Building {

        @Override
        public boolean acceptItem(Building source, Item item) {
            Building to = getTileTarget(item, source, false);

            return to != null && to.acceptItem(this, item) && to.team == team;
        }

        @Override
        public void handleItem(Building source, Item item) {
            Building target = getTileTarget(item, source, true);

            if (target != null) target.handleItem(this, item);
        }

        public @Nullable Building getTileTarget(Item item, Building src, boolean flip) {
            int from = relativeToEdge(src.tile);
            if (from == -1) return null;
            Building to = nearby((from + 2) % 4);
            boolean fromInst = src.block.instantTransfer,
                    canForward =
                            to != null
                                    && to.team == team
                                    && !(fromInst && to.block.instantTransfer)
                                    && to.acceptItem(this, item),
                    inv = invert == enabled;

            if (!canForward || inv) {
                Building a = nearby(Mathf.mod(from - 1, 4));
                Building b = nearby(Mathf.mod(from + 1, 4));
                boolean ac =
                        a != null
                                && !(fromInst && a.block.instantTransfer)
                                && a.team == team
                                && a.acceptItem(this, item);
                boolean bc =
                        b != null
                                && !(fromInst && b.block.instantTransfer)
                                && b.team == team
                                && b.acceptItem(this, item);

                if (!ac && !bc) {
                    return inv && canForward ? to : null;
                }

                if (ac && !bc) {
                    to = a;
                } else if (bc && !ac) {
                    to = b;
                } else {
                    to = (rotation & (1 << from)) == 0 ? a : b;
                    if (flip) rotation ^= (1 << from);
                }
            }

            return to;
        }

        @Override
        public byte version() {
            return 4;
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);

            if (revision == 1) {
                new DirectionalItemBuffer(25).read(read);
            } else if (revision == 3) {
                read.i();
            }

            items.clear();
        }
    }
}
