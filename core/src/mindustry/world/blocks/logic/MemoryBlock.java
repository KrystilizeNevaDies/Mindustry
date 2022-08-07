package mindustry.world.blocks.logic;

import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.gen.*;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Env;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.state;

public class MemoryBlock extends Block {
    public int memoryCapacity = 32;

    public MemoryBlock(String name) {
        super(name);
        destructible = true;
        solid = true;
        group = BlockGroup.logic;
        drawDisabled = false;
        envEnabled = Env.any;
        canOverdrive = false;
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.add(Stat.memoryCapacity, memoryCapacity, StatUnit.none);
    }

    public boolean accessible() {
        return !privileged || state.rules.editor;
    }

    @Override
    public boolean canBreak(Tile tile) {
        return accessible();
    }

    public class MemoryBuild extends Building {
        public double[] memory = new double[memoryCapacity];

        // massive byte size means picking up causes sync issues
        @Override
        public boolean canPickup() {
            return false;
        }

        @Override
        public boolean collide(Bullet other) {
            return !privileged;
        }

        @Override
        public boolean displayable() {
            return accessible();
        }

        @Override
        public void damage(float damage) {
            if (privileged) return;
            super.damage(damage);
        }

        @Override
        public void write(Writes write) {
            super.write(write);

            write.i(memory.length);
            for (double v : memory) {
                write.d(v);
            }
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);

            int amount = read.i();
            for (int i = 0; i < amount; i++) {
                double val = read.d();
                if (i < memory.length) memory[i] = val;
            }
        }
    }
}
