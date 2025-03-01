package mindustry.maps.filters;

import mindustry.content.Blocks;
import mindustry.gen.*;
import mindustry.world.Block;
import mindustry.world.Tile;

import static mindustry.maps.filters.FilterOption.*;

public class BlendFilter extends GenerateFilter {
    public float radius = 2f;
    public Block block = Blocks.sand, floor = Blocks.sandWater, ignore = Blocks.air;

    @Override
    public FilterOption[] options() {
        return new FilterOption[]{
                new SliderOption("radius", () -> radius, f -> radius = f, 1f, 10f),
                new BlockOption("block", () -> block, b -> block = b, anyOptional),
                new BlockOption("floor", () -> floor, b -> floor = b, anyOptional),
                new BlockOption("ignore", () -> ignore, b -> ignore = b, anyOptional)
        };
    }

    @Override
    public boolean isBuffered() {
        return true;
    }

    @Override
    public char icon() {
        return Iconc.blockSand;
    }

    @Override
    public void apply(GenerateInput in) {
        if (in.floor == block
                || block == Blocks.air
                || in.floor == ignore
                || (!floor.isFloor() && (in.block == block || in.block == ignore))) return;

        int rad = (int) radius;
        boolean found = false;

        outer:
        for (int x = -rad; x <= rad; x++) {
            for (int y = -rad; y <= rad; y++) {
                if (x * x + y * y > rad * rad) continue;
                Tile tile = in.tile(in.x + x, in.y + y);

                if (tile.floor() == block || tile.block() == block || tile.overlay() == block) {
                    found = true;
                    break outer;
                }
            }
        }

        if (found) {
            if (!floor.isFloor()) {
                in.block = floor;
            } else {
                in.floor = floor;
            }
        }
    }
}
