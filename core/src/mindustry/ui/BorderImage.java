package mindustry.ui;

import arc.graphics.Color;
import arc.graphics.Texture;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.Image;
import arc.scene.ui.layout.Scl;
import mindustry.graphics.Pal;

public class BorderImage extends Image {
    public float thickness = 4f, pad = 0f;
    public Color borderColor = Pal.gray;

    public BorderImage() {
    }

    public BorderImage(Texture texture) {
        super(texture);
    }

    public BorderImage(Texture texture, float thick) {
        super(texture);
        thickness = thick;
    }

    public BorderImage(TextureRegion region, float thick) {
        super(region);
        thickness = thick;
    }

    public BorderImage border(Color color) {
        this.borderColor = color;
        return this;
    }

    @Override
    public void draw() {
        super.draw();

        Draw.color(borderColor);
        Draw.alpha(parentAlpha);
        Lines.stroke(Scl.scl(thickness));
        Lines.rect(
                x + imageX - pad,
                y + imageY - pad,
                imageWidth * scaleX + pad * 2,
                imageHeight * scaleY + pad * 2);
        Draw.reset();
    }
}
