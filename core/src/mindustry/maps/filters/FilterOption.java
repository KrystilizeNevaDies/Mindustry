package mindustry.maps.filters;

import arc.Core;
import arc.func.*;
import arc.scene.Element;
import arc.scene.event.Touchable;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.CheckBox;
import arc.scene.ui.Label;
import arc.scene.ui.Slider;
import arc.scene.ui.layout.Table;
import arc.util.Align;
import arc.util.Strings;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.gen.*;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.Block;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OverlayFloor;

import static mindustry.Vars.*;

public abstract class FilterOption {
    public static final Boolf<Block> floorsOnly =
            b ->
                    (b instanceof Floor && !(b instanceof OverlayFloor))
                            && !headless
                            && Core.atlas.isFound(b.fullIcon);
    public static final Boolf<Block> wallsOnly =
            b ->
                    (!b.synthetic() && !(b instanceof Floor))
                            && !headless
                            && Core.atlas.isFound(b.fullIcon)
                            && b.inEditor;
    public static final Boolf<Block> floorsOptional =
            b ->
                    b == Blocks.air
                            || ((b instanceof Floor && !(b instanceof OverlayFloor))
                            && !headless
                            && Core.atlas.isFound(b.fullIcon));
    public static final Boolf<Block> wallsOptional =
            b ->
                    (b == Blocks.air
                            || ((!b.synthetic() && !(b instanceof Floor))
                            && !headless
                            && Core.atlas.isFound(b.fullIcon)))
                            && b.inEditor;
    public static final Boolf<Block> wallsOresOptional =
            b ->
                    b == Blocks.air
                            || (((!b.synthetic() && !(b instanceof Floor))
                            || (b instanceof OverlayFloor))
                            && !headless
                            && Core.atlas.isFound(b.fullIcon))
                            && b.inEditor;
    public static final Boolf<Block> oresOnly =
            b -> b instanceof OverlayFloor && !headless && Core.atlas.isFound(b.fullIcon);
    public static final Boolf<Block> oresFloorsOptional =
            b -> (b instanceof Floor) && !headless && Core.atlas.isFound(b.fullIcon);
    public static final Boolf<Block> anyOptional =
            b ->
                    (floorsOnly.get(b) || wallsOnly.get(b) || oresOnly.get(b) || b == Blocks.air)
                            && b.inEditor;

    public abstract void build(Table table);

    public Runnable changed = () -> {
    };

    static class SliderOption extends FilterOption {
        final String name;
        final Floatp getter;
        final Floatc setter;
        final float min, max, step;

        boolean display = true;

        SliderOption(String name, Floatp getter, Floatc setter, float min, float max) {
            this(name, getter, setter, min, max, (max - min) / 200);
        }

        SliderOption(String name, Floatp getter, Floatc setter, float min, float max, float step) {
            this.name = name;
            this.getter = getter;
            this.setter = setter;
            this.min = min;
            this.max = max;
            this.step = step;
        }

        public SliderOption display() {
            display = true;
            return this;
        }

        @Override
        public void build(Table table) {
            Element base;
            if (!display) {
                Label l = new Label("@filter.option." + name);
                l.setWrap(true);
                l.setStyle(Styles.outlineLabel);
                base = l;
            } else {
                Table t = new Table().marginLeft(11f).marginRight(11f);
                base = t;
                t.add("@filter.option." + name).growX().wrap().style(Styles.outlineLabel);
                t.label(() -> Strings.autoFixed(getter.get(), 2))
                        .style(Styles.outlineLabel)
                        .right()
                        .labelAlign(Align.right)
                        .padLeft(6);
            }
            base.touchable = Touchable.disabled;

            Slider slider = new Slider(min, max, step, false);
            slider.moved(setter);
            slider.setValue(getter.get());
            if (updateEditorOnChange) {
                slider.changed(changed);
            } else {
                slider.released(changed);
            }

            table.stack(slider, base).colspan(2).pad(3).growX().row();
        }
    }

    static class BlockOption extends FilterOption {
        final String name;
        final Prov<Block> supplier;
        final Cons<Block> consumer;
        final Boolf<Block> filter;

        BlockOption(String name, Prov<Block> supplier, Cons<Block> consumer, Boolf<Block> filter) {
            this.name = name;
            this.supplier = supplier;
            this.consumer = consumer;
            this.filter = filter;
        }

        @Override
        public void build(Table table) {
            table.button(
                            b ->
                                    b.image(supplier.get().uiIcon)
                                            .update(
                                                    i ->
                                                            ((TextureRegionDrawable)
                                                                    i.getDrawable())
                                                                    .setRegion(
                                                                            supplier.get()
                                                                                    == Blocks
                                                                                    .air
                                                                                    ? Icon.none
                                                                                    .getRegion()
                                                                                    : supplier.get()
                                                                                    .uiIcon))
                                            .size(iconSmall),
                            () -> {
                                BaseDialog dialog = new BaseDialog("@filter.option." + name);
                                dialog.cont
                                        .pane(
                                                t -> {
                                                    int i = 0;
                                                    for (Block block : Vars.content.blocks()) {
                                                        if (!filter.get(block)) continue;

                                                        t.image(
                                                                        block == Blocks.air
                                                                                ? Icon.none
                                                                                .getRegion()
                                                                                : block.uiIcon)
                                                                .size(iconMed)
                                                                .pad(3)
                                                                .tooltip(
                                                                        block == Blocks.air
                                                                                ? "@none"
                                                                                : block.localizedName)
                                                                .get()
                                                                .clicked(
                                                                        () -> {
                                                                            consumer.get(block);
                                                                            dialog.hide();
                                                                            changed.run();
                                                                        });
                                                        if (++i % 10 == 0) t.row();
                                                    }
                                                    dialog.setFillParent(i > 100);
                                                })
                                        .padRight(8f)
                                        .scrollX(false);

                                dialog.addCloseButton();
                                dialog.show();
                            })
                    .pad(4)
                    .margin(12f);

            table.add("@filter.option." + name);
        }
    }

    static class ToggleOption extends FilterOption {
        final String name;
        final Boolp getter;
        final Boolc setter;

        ToggleOption(String name, Boolp getter, Boolc setter) {
            this.name = name;
            this.getter = getter;
            this.setter = setter;
        }

        @Override
        public void build(Table table) {
            table.row();
            CheckBox check =
                    table.check("@filter.option." + name, setter)
                            .growX()
                            .padBottom(5)
                            .padTop(5)
                            .center()
                            .get();
            check.setChecked(getter.get());
            check.changed(changed);
        }
    }
}
