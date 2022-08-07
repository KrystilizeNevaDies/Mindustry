package mindustry.world.blocks.logic;

import arc.audio.Sound;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.annotations.Annotations.Load;
import mindustry.gen.*;
import mindustry.world.Block;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Env;

public class SwitchBlock extends Block {
    public Sound clickSound = Sounds.click;

    public @Load("@-on") TextureRegion onRegion;

    public SwitchBlock(String name) {
        super(name);
        configurable = true;
        update = true;
        drawDisabled = false;
        autoResetEnabled = false;
        group = BlockGroup.logic;
        envEnabled = Env.any;

        config(Boolean.class, (SwitchBuild entity, Boolean b) -> entity.enabled = b);
    }

    public class SwitchBuild extends Building {

        @Override
        public boolean configTapped() {
            configure(!enabled);
            clickSound.at(this);
            return false;
        }

        @Override
        public void draw() {
            super.draw();

            if (enabled) {
                Draw.rect(onRegion, x, y);
            }
        }

        @Override
        public Boolean config() {
            return enabled;
        }

        @Override
        public byte version() {
            return 1;
        }

        @Override
        public void readAll(Reads read, byte revision) {
            super.readAll(read, revision);

            if (revision == 1) {
                enabled = read.bool();
            }
        }

        @Override
        public void write(Writes write) {
            super.write(write);

            write.bool(enabled);
        }
    }
}
