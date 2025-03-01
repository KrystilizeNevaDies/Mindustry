package mindustry.world.blocks.defense.turrets;

import arc.Events;
import arc.scene.ui.Image;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.content.Items;
import mindustry.entities.bullet.BulletType;
import mindustry.game.EventType.Trigger;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.ui.Bar;
import mindustry.ui.MultiReqImage;
import mindustry.ui.ReqImage;
import mindustry.world.consumers.ConsumeItemFilter;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatValues;
import mindustry.world.meta.Stats;

import static mindustry.Vars.content;

public class ItemTurret extends Turret {
    public ObjectMap<Item, BulletType> ammoTypes = new ObjectMap<>();

    public ItemTurret(String name) {
        super(name);
        hasItems = true;
    }

    /**
     * Initializes accepted ammo map. Format: [item1, bullet1, item2, bullet2...]
     */
    public void ammo(Object... objects) {
        ammoTypes = ObjectMap.of(objects);
    }

    /**
     * Limits bullet range to this turret's range value.
     */
    public void limitRange() {
        limitRange(9f);
    }

    /**
     * Limits bullet range to this turret's range value.
     */
    public void limitRange(float margin) {
        for (var entry : ammoTypes.entries()) {
            limitRange(entry.value, margin);
        }
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.remove(Stat.itemCapacity);
        stats.add(Stat.ammo, StatValues.ammo(ammoTypes));
    }

    @Override
    public void init() {
        consume(new ConsumeItemFilter(i -> ammoTypes.containsKey(i)) {
            @Override
            public void build(Building build, Table table) {
                MultiReqImage image = new MultiReqImage();
                content.items().each(i -> filter.get(i) && i.unlockedNow(),
                        item -> image.add(new ReqImage(new Image(item.uiIcon),
                                () -> build instanceof ItemTurretBuild it && !it.ammo.isEmpty() && ((ItemEntry) it.ammo.peek()).item == item)));

                table.add(image).size(8 * 4);
            }

            @Override
            public float efficiency(Building build) {
                //valid when there's any ammo in the turret
                return build instanceof ItemTurretBuild it && !it.ammo.isEmpty() ? 1f : 0f;
            }

            @Override
            public void display(Stats stats) {
                //don't display
            }
        });

        super.init();
    }

    public class ItemTurretBuild extends TurretBuild {

        @Override
        public void onProximityAdded() {
            super.onProximityAdded();

            //add first ammo item to cheaty blocks so they can shoot properly
            if (cheating() && ammo.size > 0) {
                handleItem(this, ammoTypes.entries().next().key);
            }
        }

        @Override
        public void updateTile() {
            unit.ammo((float) unit.type().ammoCapacity * totalAmmo / maxAmmo);

            super.updateTile();
        }

        @Override
        public void displayBars(Table bars) {
            super.displayBars(bars);

            bars.add(new Bar("stat.ammo", Pal.ammo, () -> (float) totalAmmo / maxAmmo)).growX();
            bars.row();
        }

        @Override
        public int acceptStack(Item item, int amount, Teamc source) {
            BulletType type = ammoTypes.get(item);

            if (type == null) return 0;

            return Math.min((int) ((maxAmmo - totalAmmo) / ammoTypes.get(item).ammoMultiplier), amount);
        }

        @Override
        public void handleStack(Item item, int amount, Teamc source) {
            for (int i = 0; i < amount; i++) {
                handleItem(null, item);
            }
        }

        //currently can't remove items from turrets.
        @Override
        public int removeStack(Item item, int amount) {
            return 0;
        }

        @Override
        public void handleItem(Building source, Item item) {
            //TODO instead of all this "entry" crap, turrets could just accept only one type of ammo at a time - simpler for both users and the code

            if (item == Items.pyratite) {
                Events.fire(Trigger.flameAmmo);
            }

            BulletType type = ammoTypes.get(item);
            if (type == null) return;
            totalAmmo += type.ammoMultiplier;

            //find ammo entry by type
            for (int i = 0; i < ammo.size; i++) {
                ItemEntry entry = (ItemEntry) ammo.get(i);

                //if found, put it to the right
                if (entry.item == item) {
                    entry.amount += type.ammoMultiplier;
                    ammo.swap(i, ammo.size - 1);
                    return;
                }
            }

            //must not be found
            ammo.add(new ItemEntry(item, (int) type.ammoMultiplier));
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            return ammoTypes.get(item) != null && totalAmmo + ammoTypes.get(item).ammoMultiplier <= maxAmmo;
        }

        @Override
        public byte version() {
            return 2;
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.b(ammo.size);
            for (AmmoEntry entry : ammo) {
                ItemEntry i = (ItemEntry) entry;
                write.s(i.item.id);
                write.s(i.amount);
            }
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            ammo.clear();
            totalAmmo = 0;
            int amount = read.ub();
            for (int i = 0; i < amount; i++) {
                Item item = Vars.content.item(revision < 2 ? read.ub() : read.s());
                short a = read.s();

                //only add ammo if this is a valid ammo type
                if (item != null && ammoTypes.containsKey(item)) {
                    totalAmmo += a;
                    ammo.add(new ItemEntry(item, a));
                }
            }
        }
    }

    public class ItemEntry extends AmmoEntry {
        public Item item;

        ItemEntry(Item item, int amount) {
            this.item = item;
            this.amount = amount;
        }

        @Override
        public BulletType type() {
            return ammoTypes.get(item);
        }

        @Override
        public String toString() {
            return "ItemEntry{" +
                    "item=" + item +
                    ", amount=" + amount +
                    '}';
        }
    }
}
