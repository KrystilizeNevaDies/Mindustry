package mindustry.world.blocks.defense.turrets;

import arc.math.Mathf;
import arc.struct.EnumSet;
import arc.util.Nullable;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.TargetPriority;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.logic.Ranged;
import mindustry.world.Block;
import mindustry.world.consumers.ConsumeCoolant;
import mindustry.world.consumers.ConsumeLiquidBase;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.tilesize;

public class BaseTurret extends Block {
    public float range = 80f;
    public float placeOverlapMargin = 8 * 7f;
    public float rotateSpeed = 5;

    /**
     * Effect displayed when coolant is used.
     */
    public Effect coolEffect = Fx.fuelburn;
    /**
     * How much reload is lowered by for each unit of liquid of heat capacity.
     */
    public float coolantMultiplier = 5f;
    /**
     * If not null, this consumer will be used for coolant.
     */
    public @Nullable ConsumeLiquidBase coolant;

    public BaseTurret(String name) {
        super(name);

        update = true;
        solid = true;
        outlineIcon = true;
        attacks = true;
        priority = TargetPriority.turret;
        group = BlockGroup.turrets;
        flags = EnumSet.of(BlockFlag.turret);
    }

    @Override
    public void init() {
        if (coolant == null) {
            coolant = findConsumer(c -> c instanceof ConsumeCoolant);
        }

        // just makes things a little more convenient
        if (coolant != null) {
            // TODO coolant fix
            coolant.update = false;
            coolant.booster = true;
            coolant.optional = true;
        }

        placeOverlapRange = Math.max(placeOverlapRange, range + placeOverlapMargin);
        fogRadius = Math.max(Mathf.round(range / tilesize), fogRadius);
        super.init();
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);

        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, range, Pal.placing);
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.add(Stat.shootRange, range / tilesize, StatUnit.blocks);
    }

    public class BaseTurretBuild extends Building implements Ranged {
        public float rotation = 90;

        @Override
        public float range() {
            return range;
        }

        @Override
        public void drawSelect() {
            Drawf.dashCircle(x, y, range(), team.color);
        }

        public float estimateDps() {
            return 0f;
        }
    }
}
