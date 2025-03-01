package mindustry.world.blocks.units;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.util.Nullable;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.annotations.Annotations.Loc;
import mindustry.annotations.Annotations.Remote;
import mindustry.content.Fx;
import mindustry.content.UnitTypes;
import mindustry.entities.Units;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.type.UnitType;
import mindustry.ui.Bar;
import mindustry.ui.Fonts;
import mindustry.world.Block;
import mindustry.world.Tile;

import static mindustry.Vars.net;

public class UnitCargoLoader extends Block {
    public UnitType unitType = UnitTypes.manifold;
    public float buildTime = 60f * 8f;

    public float polyStroke = 1.8f, polyRadius = 8f;
    public int polySides = 6;
    public float polyRotateSpeed = 1f;
    public Color polyColor = Pal.accent;

    public UnitCargoLoader(String name) {
        super(name);

        solid = true;
        update = true;
        hasItems = true;
        itemCapacity = 200;
    }

    @Override
    public void setBars() {
        super.setBars();

        addBar("units", (UnitTransportSourceBuild e) ->
                new Bar(
                        () ->
                                Core.bundle.format("bar.unitcap",
                                        Fonts.getUnicodeStr(unitType.name),
                                        e.team.data().countType(unitType),
                                        Units.getStringCap(e.team)
                                ),
                        () -> Pal.power,
                        () -> (float) e.team.data().countType(unitType) / Units.getCap(e.team)
                ));
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        return super.canPlaceOn(tile, team, rotation) && Units.canCreate(team, unitType);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);

        if (!Units.canCreate(Vars.player.team(), unitType)) {
            drawPlaceText(Core.bundle.get("bar.cargounitcap"), x, y, valid);
        }
    }

    @Remote(called = Loc.server)
    public static void cargoLoaderDroneSpawned(Tile tile, int id) {
        if (tile == null || !(tile.build instanceof UnitTransportSourceBuild build)) return;
        build.spawned(id);
    }

    public class UnitTransportSourceBuild extends Building {
        //needs to be "unboxed" after reading, since units are read after buildings.
        public int readUnitId = -1;
        public float buildProgress, totalProgress;
        public float warmup, readyness;
        public @Nullable Unit unit;

        @Override
        public void updateTile() {
            //unit was lost/destroyed
            if (unit != null && (unit.dead || !unit.isAdded())) {
                unit = null;
            }

            if (readUnitId != -1) {
                unit = Groups.unit.getByID(readUnitId);
                readUnitId = -1;
            }

            warmup = Mathf.approachDelta(warmup, efficiency, 1f / 60f);
            readyness = Mathf.approachDelta(readyness, unit != null ? 1f : 0f, 1f / 60f);

            if (unit == null && Units.canCreate(team, unitType)) {
                buildProgress += edelta() / buildTime;
                totalProgress += edelta();

                if (buildProgress >= 1f) {
                    if (!net.client()) {
                        unit = unitType.create(team);
                        if (unit instanceof BuildingTetherc bt) {
                            bt.building(this);
                        }
                        unit.set(x, y);
                        unit.rotation = 90f;
                        unit.add();
                        Call.cargoLoaderDroneSpawned(tile, unit.id);
                    }
                }
            }
        }

        public void spawned(int id) {
            Fx.spawn.at(x, y);
            buildProgress = 0f;
            if (net.client()) {
                readUnitId = id;
            }
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            return items.total() < itemCapacity;
        }

        @Override
        public boolean shouldConsume() {
            return unit == null;
        }

        @Override
        public void draw() {
            Draw.rect(block.region, x, y);
            if (unit == null) {
                Draw.draw(Layer.blockOver, () -> {
                    //TODO make sure it looks proper
                    Drawf.construct(this, unitType.fullIcon, 0f, buildProgress, warmup, totalProgress);
                });
            } else {
                Draw.z(Layer.bullet - 0.01f);
                Draw.color(polyColor);
                Lines.stroke(polyStroke * readyness);
                Lines.poly(x, y, polySides, polyRadius, Time.time * polyRotateSpeed);
                Draw.reset();
                Draw.z(Layer.block);
            }
        }

        @Override
        public float totalProgress() {
            return totalProgress;
        }

        @Override
        public float progress() {
            return buildProgress;
        }

        @Override
        public void write(Writes write) {
            super.write(write);

            write.i(unit == null ? -1 : unit.id);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);

            readUnitId = read.i();
        }
    }
}
