package mindustry.ui.fragments;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import arc.scene.Element;
import arc.scene.Group;
import arc.scene.event.Touchable;

/**
 * Fades in a black overlay.
 */
public class FadeInFragment {
    private static final float duration = 40f;
    float time = 0f;

    public void build(Group parent) {
        parent.addChild(
                new Element() {
                    {
                        setFillParent(true);
                        this.touchable = Touchable.disabled;
                    }

                    @Override
                    public void draw() {
                        Draw.color(0f, 0f, 0f, Mathf.clamp(1f - time));
                        Fill.crect(0, 0, Core.graphics.getWidth(), Core.graphics.getHeight());
                        Draw.color();
                    }

                    @Override
                    public void act(float delta) {
                        super.act(delta);
                        time += 1f / duration;
                        if (time > 1) {
                            remove();
                        }
                    }
                });
    }
}
