package mindustry.world.blocks.payloads;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Nullable;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.world.Block;
import mindustry.world.blocks.ItemSelection;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.Stat;

import static mindustry.Vars.*;

/**
 * Configurable BlockProducer variant.
 */
public class Constructor extends BlockProducer {
    /**
     * Empty seq for no filter.
     */
    public Seq<Block> filter = new Seq<>();

    public int minBlockSize = 1, maxBlockSize = 2;

    public Constructor(String name) {
        super(name);

        size = 3;
        configurable = true;
        clearOnDoubleTap = true;

        configClear((ConstructorBuild tile) -> tile.recipe = null);
        config(
                Block.class,
                (ConstructorBuild tile, Block block) -> {
                    if (tile.recipe != block) tile.progress = 0f;
                    if (canProduce(block)) {
                        tile.recipe = block;
                    }
                });
        configClear((ConstructorBuild tile) -> tile.recipe = null);
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.add(Stat.output, "@x@ ~ @x@", minBlockSize, minBlockSize, maxBlockSize, maxBlockSize);
    }

    public boolean canProduce(Block b) {
        return b.isVisible()
                && b.size >= minBlockSize
                && b.size <= maxBlockSize
                && !(b instanceof CoreBlock)
                && !state.rules.bannedBlocks.contains(b)
                && b.environmentBuildable()
                && (filter.isEmpty() || filter.contains(b));
    }

    public class ConstructorBuild extends BlockProducerBuild {
        public @Nullable Block recipe;

        @Override
        public @Nullable Block recipe() {
            return recipe;
        }

        @Override
        public void buildConfiguration(Table table) {
            ItemSelection.buildTable(
                    Constructor.this,
                    table,
                    filter.isEmpty()
                            ? content.blocks().select(Constructor.this::canProduce)
                            : filter,
                    () -> recipe,
                    this::configure);
        }

        @Override
        public Object config() {
            return recipe;
        }

        @Override
        public void drawSelect() {
            if (recipe != null) {
                float dx = x - size * tilesize / 2f, dy = y + size * tilesize / 2f;
                TextureRegion icon = recipe.uiIcon;
                Draw.mixcol(Color.darkGray, 1f);
                // Fixes size because modded content icons are not scaled
                Draw.rect(
                        icon,
                        dx - 0.7f,
                        dy - 1f,
                        Draw.scl * Draw.xscl * 24f,
                        Draw.scl * Draw.yscl * 24f);
                Draw.reset();
                Draw.rect(icon, dx, dy, Draw.scl * Draw.xscl * 24f, Draw.scl * Draw.yscl * 24f);
            }
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.s(recipe == null ? -1 : recipe.id);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            recipe = Vars.content.block(read.s());
        }
    }
}
