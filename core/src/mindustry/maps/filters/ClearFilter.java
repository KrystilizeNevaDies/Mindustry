package mindustry.maps.filters;

import mindustry.content.Blocks;
import mindustry.gen.*;
import mindustry.world.Block;

import static mindustry.maps.filters.FilterOption.BlockOption;
import static mindustry.maps.filters.FilterOption.anyOptional;

public class ClearFilter extends GenerateFilter {
    public Block target = Blocks.stone;
    public Block replace = Blocks.air;

    @Override
    public FilterOption[] options() {
        return new FilterOption[]{
                new BlockOption("target", () -> target, b -> target = b, anyOptional),
                new BlockOption("replacement", () -> replace, b -> replace = b, anyOptional)
        };
    }

    @Override
    public char icon() {
        return Iconc.blockSnow;
    }

    @Override
    public void apply(GenerateInput in) {

        if (in.block == target
                || in.floor == target
                || (target.isOverlay() && in.overlay == target)) {
            // special case: when air is the result, replace only the overlay or wall
            if (replace == Blocks.air) {
                if (in.overlay == target) {
                    in.overlay = Blocks.air;
                } else {
                    in.block = Blocks.air;
                }
            } else if (replace.isOverlay()) { // replace the best match based on type
                in.overlay = replace;
            } else if (replace.isFloor()) {
                in.floor = replace;
            } else {
                in.block = replace;
            }
        }
    }
}
