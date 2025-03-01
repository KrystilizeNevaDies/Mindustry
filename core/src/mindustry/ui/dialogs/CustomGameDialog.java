package mindustry.ui.dialogs;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.Image;
import arc.scene.ui.ImageButton;
import arc.scene.ui.ImageButton.ImageButtonStyle;
import arc.scene.ui.ScrollPane;
import arc.scene.ui.layout.Scl;
import arc.scene.ui.layout.Table;
import arc.util.Scaling;
import arc.util.Strings;
import mindustry.Vars;
import mindustry.game.Gamemode;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.maps.Map;
import mindustry.ui.BorderImage;
import mindustry.ui.Styles;

public class CustomGameDialog extends BaseDialog {
    private MapPlayDialog dialog = new MapPlayDialog();

    public CustomGameDialog() {
        super("@customgame");
        addCloseButton();
        shown(this::setup);
        onResize(this::setup);
    }

    void setup() {
        clearChildren();
        add(titleTable).growX().row();
        stack(cont, buttons).grow();
        buttons.bottom();
        cont.clear();

        Table maps = new Table();
        maps.marginBottom(55f).marginRight(-20f);
        ScrollPane pane = new ScrollPane(maps);
        pane.setFadeScrollBars(false);

        int maxwidth = Math.max((int) (Core.graphics.getWidth() / Scl.scl(210)), 1);
        float images = 146f;

        ImageButtonStyle style =
                new ImageButtonStyle() {
                    {
                        up = Styles.grayPanel;
                        down = Styles.flatOver;
                        over = Styles.flatOver;
                        disabled = Styles.none;
                    }
                };

        int i = 0;
        maps.defaults().width(170).fillY().top().pad(4f);
        for (Map map : Vars.maps.all()) {

            if (i % maxwidth == 0) {
                maps.row();
            }

            ImageButton image = new ImageButton(new TextureRegion(map.safeTexture()), style);
            image.margin(5);
            image.top();

            Image img = image.getImage();
            img.remove();

            image.row();
            image.table(
                            t -> {
                                t.left();
                                for (Gamemode mode : Gamemode.all) {
                                    TextureRegionDrawable icon =
                                            Vars.ui.getIcon(
                                                    "mode"
                                                            + Strings.capitalize(mode.name())
                                                            + "Small");
                                    if (mode.valid(map) && Core.atlas.isFound(icon.getRegion())) {
                                        t.image(icon).size(16f).pad(4f);
                                    }
                                }
                            })
                    .left();
            image.row();
            image.add(map.name()).pad(1f).growX().wrap().left().get().setEllipsis(true);
            image.row();
            image.image(Tex.whiteui, Pal.gray).growX().pad(3).height(4f);
            image.row();
            image.add(img).size(images);

            BorderImage border = new BorderImage(map.safeTexture(), 3f);
            border.setScaling(Scaling.fit);
            image.replaceImage(border);

            image.clicked(() -> dialog.show(map));

            maps.add(image);

            i++;
        }

        if (Vars.maps.all().size == 0) {
            maps.add("@maps.none").pad(50);
        }

        cont.add(pane).grow();
    }
}
