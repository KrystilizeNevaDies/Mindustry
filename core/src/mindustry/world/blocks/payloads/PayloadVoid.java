package mindustry.world.blocks.payloads;

import arc.audio.Sound;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.*;
import mindustry.graphics.Layer;

public class PayloadVoid extends PayloadBlock {
    public Effect incinerateEffect = Fx.blastExplosion;
    public Sound incinerateSound = Sounds.bang;

    public PayloadVoid(String name) {
        super(name);

        outputsPayload = false;
        acceptsPayload = true;
        update = true;
        rotate = false;
        size = 3;
        payloadSpeed = 1.2f;
        // make sure to display large units.
        clipSize = 120;
    }

    @Override
    public TextureRegion[] icons() {
        return new TextureRegion[]{region, topRegion};
    }

    public class PayloadVoidBuild extends PayloadBlockBuild<Payload> {

        @Override
        public void draw() {
            Draw.rect(region, x, y);

            // draw input
            for (int i = 0; i < 4; i++) {
                if (blends(i)) {
                    Draw.rect(inRegion, x, y, (i * 90) - 180);
                }
            }

            Draw.rect(topRegion, x, y);

            Draw.z(Layer.blockOver);
            drawPayload();
        }

        @Override
        public boolean acceptUnitPayload(Unit unit) {
            return true;
        }

        @Override
        public void updateTile() {
            super.updateTile();
            if (moveInPayload(false) && efficiency > 0) {
                payload = null;
                incinerateEffect.at(this);
                incinerateSound.at(this);
            }
        }
    }
}
