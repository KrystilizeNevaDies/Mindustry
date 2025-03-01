package mindustry.world.blocks.defense.turrets;

import arc.struct.ObjectMap;
import mindustry.content.Fx;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.*;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.world.consumers.ConsumeLiquidFilter;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;
import mindustry.world.meta.Stats;

public class ContinuousLiquidTurret extends ContinuousTurret {
    public ObjectMap<Liquid, BulletType> ammoTypes = new ObjectMap<>();
    public float liquidConsumed = 1f / 60f;

    public ContinuousLiquidTurret(String name) {
        super(name);
        hasLiquids = true;
        // TODO
        loopSound = Sounds.minebeam;
        shootSound = Sounds.none;
        smokeEffect = Fx.none;
        shootEffect = Fx.none;
    }

    /**
     * Initializes accepted ammo map. Format: [liquid1, bullet1, liquid2, bullet2...]
     */
    public void ammo(Object... objects) {
        ammoTypes = ObjectMap.of(objects);
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.remove(Stat.ammo);
        // TODO looks bad
        stats.add(Stat.ammo, StatValues.number(liquidConsumed * 60f, StatUnit.perSecond, true));
        stats.add(Stat.ammo, StatValues.ammo(ammoTypes));
    }

    @Override
    public void init() {
        // TODO display ammoMultiplier.
        consume(
                new ConsumeLiquidFilter(i -> ammoTypes.containsKey(i), liquidConsumed) {

                    @Override
                    public void display(Stats stats) {
                    }

                    // TODO
                    // @Override
                    // protected float use(Building entity){
                    //    BulletType type = ammoTypes.get(entity.liquids.current());
                    //    return Math.min(amount * entity.edelta(), entity.block.liquidCapacity) /
                    // (type ==
                    // null ? 1f : type.ammoMultiplier);
                    // }
                });

        super.init();
    }

    public class ContinuousLiquidTurretBuild extends ContinuousTurretBuild {

        @Override
        public boolean shouldActiveSound() {
            return wasShooting && enabled;
        }

        @Override
        public void updateTile() {
            unit.ammo(unit.type().ammoCapacity * liquids.currentAmount() / liquidCapacity);

            super.updateTile();
        }

        @Override
        public BulletType useAmmo() {
            // does not consume ammo upon firing
            return peekAmmo();
        }

        @Override
        public BulletType peekAmmo() {
            return ammoTypes.get(liquids.current());
        }

        @Override
        public boolean hasAmmo() {
            return ammoTypes.get(liquids.current()) != null
                    && liquids.currentAmount()
                    >= 1f / ammoTypes.get(liquids.current()).ammoMultiplier;
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            return false;
        }

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid) {
            return ammoTypes.get(liquid) != null
                    && (liquids.current() == liquid
                    || ((!ammoTypes.containsKey(liquids.current())
                    || liquids.get(liquids.current())
                    <= 1f / ammoTypes.get(liquids.current()).ammoMultiplier
                    + 0.001f)));
        }
    }
}
