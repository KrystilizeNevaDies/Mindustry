package mindustry.type;

import arc.func.Cons;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Rules;
import mindustry.gen.*;
import mindustry.maps.generators.FileMapGenerator;

public class SectorPreset extends UnlockableContent {
    public FileMapGenerator generator;
    public Planet planet;
    public Sector sector;

    public int captureWave = 0;
    public Cons<Rules> rules = rules -> rules.winWave = captureWave;
    /**
     * Difficulty, 0-10.
     */
    public float difficulty;

    public float startWaveTimeMultiplier = 2f;
    public boolean addStartingItems = false;
    public boolean showSectorLandInfo = true;
    /**
     * If true, switches to attack mode after waves end.
     */
    public boolean attackAfterWaves = false;

    public SectorPreset(String name, Planet planet, int sector) {
        super(name);
        this.generator = new FileMapGenerator(name, this);
        this.planet = planet;
        sector %= planet.sectors.size;
        this.sector = planet.sectors.get(sector);
        inlineDescription = false;

        planet.preset(sector, this);
    }

    @Override
    public void loadIcon() {
        if (Icon.terrain != null) {
            uiIcon = fullIcon = Icon.terrain.getRegion();
        }
    }

    @Override
    public boolean isHidden() {
        return description == null;
    }

    @Override
    public ContentType getContentType() {
        return ContentType.sector;
    }
}
