package mindustry.graphics;

import arc.Core;
import arc.Events;
import arc.graphics.Color;
import arc.graphics.Texture;
import arc.graphics.Texture.TextureFilter;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.ScissorStack;
import arc.graphics.gl.FrameBuffer;
import arc.math.geom.Rect;
import arc.struct.LongSeq;
import arc.util.Nullable;
import mindustry.game.EventType.WorldLoadEvent;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.world.Tile;
import mindustry.world.meta.BlockFlag;

import static mindustry.Vars.*;

/**
 * Highly experimental fog-of-war renderer.
 */
public final class FogRenderer {
    private FrameBuffer staticFog = new FrameBuffer(), dynamicFog = new FrameBuffer();
    private LongSeq events = new LongSeq();
    private Rect rect = new Rect();
    private @Nullable Team lastTeam;

    public FogRenderer() {
        Events.on(
                WorldLoadEvent.class,
                event -> {
                    lastTeam = null;
                    events.clear();
                });
    }

    public void handleEvent(long event) {
        events.add(event);
    }

    public Texture getStaticTexture() {
        return staticFog.getTexture();
    }

    public Texture getDynamicTexture() {
        return dynamicFog.getTexture();
    }

    public void drawFog() {
        // there is no fog.
        if (fogControl.getDiscovered(player.team()) == null) return;

        // resize if world size changes
        boolean clearStatic = staticFog.resizeCheck(world.width(), world.height());

        dynamicFog.resize(world.width(), world.height());

        if (state.rules.staticFog && player.team() != lastTeam) {
            copyFromCpu();
            lastTeam = player.team();
            clearStatic = false;
        }

        // draw dynamic fog every frame
        {
            Draw.proj(0, 0, staticFog.getWidth() * tilesize, staticFog.getHeight() * tilesize);
            dynamicFog.begin(Color.black);
            ScissorStack.push(rect.set(1, 1, staticFog.getWidth() - 2, staticFog.getHeight() - 2));

            Team team = player.team();

            for (var build : indexer.getFlagged(team, BlockFlag.hasFogRadius)) {
                poly(build.x, build.y, build.fogRadius() * tilesize);
            }

            for (var unit : team.data().units) {
                poly(unit.x, unit.y, unit.type.fogRadius * tilesize);
            }

            dynamicFog.end();
            ScissorStack.pop();
            Draw.proj(Core.camera);
        }

        // grab static events
        if (state.rules.staticFog && (clearStatic || events.size > 0)) {
            // set projection to whole map
            Draw.proj(0, 0, staticFog.getWidth(), staticFog.getHeight());

            // if the buffer resized, it contains garbage now, clearStatic it.
            if (clearStatic) {
                staticFog.begin(Color.black);
            } else {
                staticFog.begin();
            }

            ScissorStack.push(rect.set(1, 1, staticFog.getWidth() - 2, staticFog.getHeight() - 2));

            Draw.color(Color.white);

            // process new static fog events
            for (int i = 0; i < events.size; i++) {
                renderEvent(events.items[i]);
            }
            events.clear();

            staticFog.end();
            ScissorStack.pop();
            Draw.proj(Core.camera);
        }

        if (state.rules.staticFog) {
            staticFog.getTexture().setFilter(TextureFilter.linear);
        }
        dynamicFog.getTexture().setFilter(TextureFilter.linear);

        Draw.shader(Shaders.fog);
        Draw.color(state.rules.dynamicColor);
        Draw.fbo(dynamicFog.getTexture(), world.width(), world.height(), tilesize);
        // TODO ai check?
        if (state.rules.staticFog) {
            // TODO why does this require a half-tile offset while dynamic does not
            Draw.color(state.rules.staticColor);
            Draw.fbo(
                    staticFog.getTexture(), world.width(), world.height(), tilesize, tilesize / 2f);
        }
        Draw.shader();
    }

    void poly(float x, float y, float rad) {
        Fill.poly(x, y, 20, rad);
    }

    void renderEvent(long e) {
        Tile tile = world.tile(FogEvent.x(e), FogEvent.y(e));
        float o = 0f;
        // visual offset for uneven blocks; this is not reflected on the CPU, but it doesn't really
        // matter
        if (tile != null && tile.block().size % 2 == 0 && tile.isCenter()) {
            o = 0.5f;
        }
        Fill.poly(
                FogEvent.x(e) + 0.5f + o, FogEvent.y(e) + 0.5f + o, 20, FogEvent.radius(e) + 0.3f);
    }

    public void copyFromCpu() {
        staticFog.resize(world.width(), world.height());
        staticFog.begin(Color.black);
        Draw.proj(0, 0, staticFog.getWidth(), staticFog.getHeight());
        Draw.color();
        int ww = world.width(), wh = world.height();

        var data = fogControl.getDiscovered(player.team());
        int len = world.width() * world.height();
        if (data != null) {
            for (int i = 0; i < len; i++) {
                if (data.get(i)) {
                    // TODO slow, could do scanlines instead at the very least.
                    int x = i % ww, y = i / ww;

                    // manually clip with 1 pixel of padding so the borders are never fully revealed
                    if (x > 0 && y > 0 && x < ww - 1 && y < wh - 1) {
                        Fill.rect(x + 0.5f, y + 0.5f, 1f, 1f);
                    }
                }
            }
        }

        staticFog.end();
        Draw.proj(Core.camera);
    }
}
