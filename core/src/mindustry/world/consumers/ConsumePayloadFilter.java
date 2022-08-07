package mindustry.world.consumers;

import arc.func.Boolf;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.ctype.UnlockableContent;
import mindustry.gen.*;
import mindustry.ui.ItemImage;
import mindustry.ui.MultiReqImage;
import mindustry.ui.ReqImage;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatValues;
import mindustry.world.meta.Stats;

import static mindustry.Vars.content;

public class ConsumePayloadFilter extends Consume {
    // cache fitting blocks to prevent search over all blocks later
    protected final UnlockableContent[] fitting;

    public Boolf<UnlockableContent> filter;

    public ConsumePayloadFilter(Boolf<UnlockableContent> filter) {
        this.filter = filter;
        this.fitting =
                Vars.content
                        .blocks()
                        .copy()
                        .<UnlockableContent>as()
                        .add(content.units().as())
                        .select(filter)
                        .toArray(UnlockableContent.class);
    }

    @Override
    public float efficiency(Building build) {
        var payloads = build.getPayloads();
        for (var block : fitting) {
            if (payloads.contains(block, 1)) {
                return 1f;
            }
        }
        return 0f;
    }

    @Override
    public void trigger(Building build) {
        var payloads = build.getPayloads();
        for (var block : fitting) {
            if (payloads.contains(block, 1)) {
                payloads.remove(block);
                return;
            }
        }
    }

    @Override
    public void display(Stats stats) {
        stats.add(booster ? Stat.booster : Stat.input, StatValues.content(new Seq<>(fitting)));
    }

    @Override
    public void build(Building build, Table table) {
        var inv = build.getPayloads();

        MultiReqImage image = new MultiReqImage();

        content.blocks()
                .each(
                        i -> filter.get(i) && i.unlockedNow(),
                        block ->
                                image.add(
                                        new ReqImage(
                                                new ItemImage(block.uiIcon, 1),
                                                () -> inv.contains(block, 1))));

        table.add(image).size(8 * 4);
    }
}
