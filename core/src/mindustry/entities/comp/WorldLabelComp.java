package mindustry.entities.comp;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Font;
import arc.graphics.g2d.GlyphLayout;
import arc.scene.ui.layout.Scl;
import arc.util.Align;
import arc.util.pooling.Pools;
import mindustry.annotations.Annotations.Component;
import mindustry.annotations.Annotations.EntityDef;
import mindustry.annotations.Annotations.Import;
import mindustry.annotations.Annotations.Replace;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.ui.Fonts;

/**
 * Component/entity for labels in world space. Useful for servers. Does not save in files - create
 * only on world load.
 */
@EntityDef(
        value = {WorldLabelc.class},
        serialize = false)
@Component(base = true)
public abstract class WorldLabelComp implements Posc, Drawc, Syncc {
    @Import
    int id;
    @Import
    float x, y;
    public static final byte flagBackground = 1, flagOutline = 2;

    public String text = "sample text";
    public float fontSize = 1f, z = Layer.playerName + 1;
    /**
     * Flags are packed into a byte for sync efficiency; see the flag static values.
     */
    public byte flags = flagBackground | flagOutline;

    @Replace
    public float clipSize() {
        return text.length() * 10f * fontSize;
    }

    @Override
    public void draw() {
        drawAt(text, x, y, z, flags, fontSize);
    }

    public static void drawAt(
            String text, float x, float y, float layer, int flags, float fontSize) {
        Draw.z(layer);
        float z = Drawf.text();

        Font font = (flags & flagOutline) != 0 ? Fonts.outline : Fonts.def;
        GlyphLayout layout = Pools.obtain(GlyphLayout.class, GlyphLayout::new);

        boolean ints = font.usesIntegerPositions();
        font.setUseIntegerPositions(false);
        font.getData().setScale(0.25f / Scl.scl(1f) * fontSize);
        layout.setText(font, text);

        if ((flags & flagBackground) != 0) {
            Draw.color(0f, 0f, 0f, 0.3f);
            Fill.rect(x, y - layout.height / 2, layout.width + 2, layout.height + 3);
            Draw.color();
        }

        font.setColor(Color.white);
        font.draw(text, x, y, 0, Align.center, false);

        Draw.reset();
        Pools.free(layout);
        font.getData().setScale(1f);
        font.setColor(Color.white);
        font.setUseIntegerPositions(ints);

        Draw.z(z);
    }

    /**
     * This MUST be called instead of remove()!
     */
    public void hide() {
        remove();
        Call.removeWorldLabel(id);
    }
}
