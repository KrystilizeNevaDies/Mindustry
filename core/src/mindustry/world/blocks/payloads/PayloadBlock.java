package mindustry.world.blocks.payloads;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Vec2;
import arc.util.Nullable;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Env;

import static mindustry.Vars.tilesize;

public class PayloadBlock extends Block {
    public float payloadSpeed = 0.7f, payloadRotateSpeed = 5f;

    public String regionSuffix = "";
    public TextureRegion topRegion, outRegion, inRegion;

    public PayloadBlock(String name) {
        super(name);

        update = true;
        sync = true;
        group = BlockGroup.payloads;
        envEnabled |= Env.space | Env.underwater;
    }

    @Override
    public void load() {
        super.load();

        topRegion = Core.atlas.find(name + "-top", "factory-top-" + size + regionSuffix);
        outRegion = Core.atlas.find(name + "-out", "factory-out-" + size + regionSuffix);
        inRegion = Core.atlas.find(name + "-in", "factory-in-" + size + regionSuffix);
    }

    public static boolean blends(Building build, int direction) {
        int size = build.block.size;
        int trns = build.block.size / 2 + 1;
        Building accept = build.nearby(Geometry.d4(direction).x * trns, Geometry.d4(direction).y * trns);
        return accept != null &&
                accept.block.outputsPayload &&

                //if size is the same, block must either be facing this one, or not be rotating
                ((accept.block.size == size
                        && Math.abs(accept.tileX() - build.tileX()) % size == 0 //check alignment
                        && Math.abs(accept.tileY() - build.tileY()) % size == 0
                        && ((accept.block.rotate && accept.tileX() + Geometry.d4(accept.rotation).x * size == build.tileX() && accept.tileY() + Geometry.d4(accept.rotation).y * size == build.tileY())
                        || !accept.block.rotate
                        || !accept.block.outputFacing)) ||

                        //if the other block is smaller, check alignment
                        (accept.block.size != size &&
                                (accept.rotation % 2 == 0 ? //check orientation; make sure it's aligned properly with this block.
                                        Math.abs(accept.y - build.y) <= Math.abs(size * tilesize - accept.block.size * tilesize) / 2f : //check Y alignment
                                        Math.abs(accept.x - build.x) <= Math.abs(size * tilesize - accept.block.size * tilesize) / 2f   //check X alignment
                                )) && (!accept.block.rotate || accept.front() == build || !accept.block.outputFacing) //make sure it's facing this block
                );
    }

    public static void pushOutput(Payload payload, float progress) {
        float thresh = 0.55f;
        if (progress >= thresh) {
            boolean legStep = payload instanceof UnitPayload u && u.unit.type.allowLegStep;
            float size = payload.size(), radius = size / 2f, x = payload.x(), y = payload.y(), scl = Mathf.clamp(((progress - thresh) / (1f - thresh)) * 1.1f);

            Groups.unit.intersect(x - size / 2f, y - size / 2f, size, size, u -> {
                float dst = u.dst(payload);
                float rs = radius + u.hitSize / 2f;
                if (u.isGrounded() && u.type.allowLegStep == legStep && dst < rs) {
                    u.vel.add(Tmp.v1.set(u.x - x, u.y - y).setLength(Math.min(rs - dst, 1f)).scl(scl));
                }
            });
        }
    }

    public class PayloadBlockBuild<T extends Payload> extends Building {
        public @Nullable T payload;
        //TODO redundant; already stored in payload?
        public Vec2 payVector = new Vec2();
        public float payRotation;
        public boolean carried;

        public boolean acceptUnitPayload(Unit unit) {
            return false;
        }

        @Override
        public boolean canControlSelect(Unit unit) {
            return !unit.spawnedByCore && unit.type.allowedInPayloads && this.payload == null && acceptUnitPayload(unit) && unit.tileOn() != null && unit.tileOn().build == this;
        }

        @Override
        public void onControlSelect(Unit player) {
            float x = player.x, y = player.y;
            handleUnitPayload(player, p -> payload = (T) p);
            this.payVector.set(x, y).sub(this).clamp(-size * tilesize / 2f, -size * tilesize / 2f, size * tilesize / 2f, size * tilesize / 2f);
            this.payRotation = player.rotation;
        }

        @Override
        public boolean acceptPayload(Building source, Payload payload) {
            return this.payload == null;
        }

        @Override
        public void handlePayload(Building source, Payload payload) {
            this.payload = (T) payload;
            this.payVector.set(source).sub(this).clamp(-size * tilesize / 2f, -size * tilesize / 2f, size * tilesize / 2f, size * tilesize / 2f);
            this.payRotation = payload.rotation();

            updatePayload();
        }

        @Override
        public Payload getPayload() {
            return payload;
        }

        @Override
        public void pickedUp() {
            carried = true;
        }

        @Override
        public void drawTeamTop() {
            carried = false;
        }

        @Override
        public Payload takePayload() {
            T t = payload;
            payload = null;
            return t;
        }

        @Override
        public void onRemoved() {
            super.onRemoved();
            if (payload != null && !carried) payload.dump();
        }

        @Override
        public void updateTile() {
            if (payload != null) {
                payload.update(null, this);
            }
        }

        public boolean blends(int direction) {
            return PayloadBlock.blends(this, direction);
        }

        public void updatePayload() {
            if (payload != null) {
                payload.set(x + payVector.x, y + payVector.y, payRotation);
            }
        }

        /**
         * @return true if the payload is in position.
         */
        public boolean moveInPayload() {
            return moveInPayload(true);
        }

        /**
         * @return true if the payload is in position.
         */
        public boolean moveInPayload(boolean rotate) {
            if (payload == null) return false;

            updatePayload();

            if (rotate) {
                payRotation = Angles.moveToward(payRotation, block.rotate ? rotdeg() : 90f, payloadRotateSpeed * delta());
            }
            payVector.approach(Vec2.ZERO, payloadSpeed * delta());

            return hasArrived();
        }

        public void moveOutPayload() {
            if (payload == null) return;

            updatePayload();

            Vec2 dest = Tmp.v1.trns(rotdeg(), size * tilesize / 2f);

            payRotation = Angles.moveToward(payRotation, rotdeg(), payloadRotateSpeed * delta());
            payVector.approach(dest, payloadSpeed * delta());

            Building front = front();
            boolean canDump = front == null || !front.tile().solid();
            boolean canMove = front != null && (front.block.outputsPayload || front.block.acceptsPayload);

            if (canDump && !canMove) {
                pushOutput(payload, 1f - (payVector.dst(dest) / (size * tilesize / 2f)));
            }

            if (payVector.within(dest, 0.001f)) {
                payVector.clamp(-size * tilesize / 2f, -size * tilesize / 2f, size * tilesize / 2f, size * tilesize / 2f);

                if (canMove) {
                    if (movePayload(payload)) {
                        payload = null;
                    }
                } else if (canDump) {
                    dumpPayload();
                }
            }
        }

        public void dumpPayload() {
            //translate payload forward slightly
            float tx = Angles.trnsx(payload.rotation(), 0.1f), ty = Angles.trnsy(payload.rotation(), 0.1f);
            payload.set(payload.x() + tx, payload.y() + ty, payload.rotation());

            if (payload.dump()) {
                payload = null;
            } else {
                payload.set(payload.x() - tx, payload.y() - ty, payload.rotation());
            }
        }

        public boolean hasArrived() {
            return payVector.isZero(0.01f);
        }

        public void drawPayload() {
            if (payload != null) {
                updatePayload();

                Draw.z(Layer.blockOver);
                payload.draw();
            }
        }

        @Override
        public void write(Writes write) {
            super.write(write);

            write.f(payVector.x);
            write.f(payVector.y);
            write.f(payRotation);
            Payload.write(payload, write);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);

            payVector.set(read.f(), read.f());
            payRotation = read.f();
            payload = Payload.read(read);
        }
    }
}
