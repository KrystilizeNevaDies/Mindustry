package mindustry.maps.filters;

import arc.struct.IntSeq;
import mindustry.gen.*;
import mindustry.world.Tile;
import mindustry.world.Tiles;
import mindustry.world.blocks.storage.CoreBlock;

import static mindustry.Vars.state;

/**
 * Selects X spawns from the core spawn pool.
 */
public class CoreSpawnFilter extends GenerateFilter {
    public int amount = 1;

    @Override
    public FilterOption[] options() {
        // disabled until necessary
        // SliderOption("amount", () -> amount, f -> amount = (int)f, 1, 10).display()
        return new FilterOption[]{};
    }

    @Override
    public char icon() {
        return Iconc.blockCoreShard;
    }

    @Override
    public void apply(Tiles tiles, GenerateInput in) {
        IntSeq spawns = new IntSeq();
        for (Tile tile : tiles) {
            if (tile.team() == state.rules.defaultTeam
                    && tile.block() instanceof CoreBlock
                    && tile.isCenter()) {
                spawns.add(tile.pos());
            }
        }

        spawns.shuffle();

        int used = Math.min(spawns.size, amount);
        for (int i = used; i < spawns.size; i++) {
            tiles.getp(spawns.get(i)).remove();
        }
    }

    @Override
    public boolean isPost() {
        return true;
    }
}
