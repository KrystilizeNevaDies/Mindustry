package mindustry.mod;

import arc.struct.ObjectMap;

/**
 * Generated class. Maps simple class names to concrete classes. For use in JSON mods.
 */
@SuppressWarnings("deprecation")
public class ClassMap {
    public static final ObjectMap<String, Class<?>> classes = new ObjectMap<>();

    static {
        classes.put("AssemblerAI", mindustry.ai.types.AssemblerAI.class);
        classes.put("BuilderAI", mindustry.ai.types.BuilderAI.class);
        classes.put("CargoAI", mindustry.ai.types.CargoAI.class);
        classes.put("CommandAI", mindustry.ai.types.CommandAI.class);
        classes.put("DefenderAI", mindustry.ai.types.DefenderAI.class);
        classes.put("FlyingAI", mindustry.ai.types.FlyingAI.class);
        classes.put("FlyingFollowAI", mindustry.ai.types.FlyingFollowAI.class);
        classes.put("GroundAI", mindustry.ai.types.GroundAI.class);
        classes.put("HugAI", mindustry.ai.types.HugAI.class);
        classes.put("LogicAI", mindustry.ai.types.LogicAI.class);
        classes.put("MinerAI", mindustry.ai.types.MinerAI.class);
        classes.put("MissileAI", mindustry.ai.types.MissileAI.class);
        classes.put("RepairAI", mindustry.ai.types.RepairAI.class);
        classes.put("SuicideAI", mindustry.ai.types.SuicideAI.class);
        classes.put("Ability", mindustry.entities.abilities.Ability.class);
        classes.put("ArmorPlateAbility", mindustry.entities.abilities.ArmorPlateAbility.class);
        classes.put("EnergyFieldAbility", mindustry.entities.abilities.EnergyFieldAbility.class);
        classes.put("ForceFieldAbility", mindustry.entities.abilities.ForceFieldAbility.class);
        classes.put(
                "LiquidExplodeAbility", mindustry.entities.abilities.LiquidExplodeAbility.class);
        classes.put("LiquidRegenAbility", mindustry.entities.abilities.LiquidRegenAbility.class);
        classes.put("MoveEffectAbility", mindustry.entities.abilities.MoveEffectAbility.class);
        classes.put(
                "MoveLightningAbility", mindustry.entities.abilities.MoveLightningAbility.class);
        classes.put("RegenAbility", mindustry.entities.abilities.RegenAbility.class);
        classes.put("RepairFieldAbility", mindustry.entities.abilities.RepairFieldAbility.class);
        classes.put("ShieldArcAbility", mindustry.entities.abilities.ShieldArcAbility.class);
        classes.put(
                "ShieldRegenFieldAbility",
                mindustry.entities.abilities.ShieldRegenFieldAbility.class);
        classes.put("SpawnDeathAbility", mindustry.entities.abilities.SpawnDeathAbility.class);
        classes.put("StatusFieldAbility", mindustry.entities.abilities.StatusFieldAbility.class);
        classes.put(
                "SuppressionFieldAbility",
                mindustry.entities.abilities.SuppressionFieldAbility.class);
        classes.put("UnitSpawnAbility", mindustry.entities.abilities.UnitSpawnAbility.class);
        classes.put("ArtilleryBulletType", mindustry.entities.bullet.ArtilleryBulletType.class);
        classes.put("BasicBulletType", mindustry.entities.bullet.BasicBulletType.class);
        classes.put("BombBulletType", mindustry.entities.bullet.BombBulletType.class);
        classes.put("BulletType", mindustry.entities.bullet.BulletType.class);
        classes.put("ContinuousBulletType", mindustry.entities.bullet.ContinuousBulletType.class);
        classes.put(
                "ContinuousFlameBulletType",
                mindustry.entities.bullet.ContinuousFlameBulletType.class);
        classes.put(
                "ContinuousLaserBulletType",
                mindustry.entities.bullet.ContinuousLaserBulletType.class);
        classes.put("EmpBulletType", mindustry.entities.bullet.EmpBulletType.class);
        classes.put("ExplosionBulletType", mindustry.entities.bullet.ExplosionBulletType.class);
        classes.put("FireBulletType", mindustry.entities.bullet.FireBulletType.class);
        classes.put("FlakBulletType", mindustry.entities.bullet.FlakBulletType.class);
        classes.put("LaserBoltBulletType", mindustry.entities.bullet.LaserBoltBulletType.class);
        classes.put("LaserBulletType", mindustry.entities.bullet.LaserBulletType.class);
        classes.put("LightningBulletType", mindustry.entities.bullet.LightningBulletType.class);
        classes.put("LiquidBulletType", mindustry.entities.bullet.LiquidBulletType.class);
        classes.put("MassDriverBolt", mindustry.entities.bullet.MassDriverBolt.class);
        classes.put("MissileBulletType", mindustry.entities.bullet.MissileBulletType.class);
        classes.put("PointBulletType", mindustry.entities.bullet.PointBulletType.class);
        classes.put("PointLaserBulletType", mindustry.entities.bullet.PointLaserBulletType.class);
        classes.put("RailBulletType", mindustry.entities.bullet.RailBulletType.class);
        classes.put("SapBulletType", mindustry.entities.bullet.SapBulletType.class);
        classes.put("ShrapnelBulletType", mindustry.entities.bullet.ShrapnelBulletType.class);
        classes.put("SpaceLiquidBulletType", mindustry.entities.bullet.SpaceLiquidBulletType.class);
        classes.put("ExplosionEffect", mindustry.entities.effect.ExplosionEffect.class);
        classes.put("MultiEffect", mindustry.entities.effect.MultiEffect.class);
        classes.put("ParticleEffect", mindustry.entities.effect.ParticleEffect.class);
        classes.put("RadialEffect", mindustry.entities.effect.RadialEffect.class);
        classes.put("SeqEffect", mindustry.entities.effect.SeqEffect.class);
        classes.put("WaveEffect", mindustry.entities.effect.WaveEffect.class);
        classes.put("WrapEffect", mindustry.entities.effect.WrapEffect.class);
        classes.put("DrawPart", mindustry.entities.part.DrawPart.class);
        classes.put("PartFunc", mindustry.entities.part.DrawPart.PartFunc.class);
        classes.put("PartMove", mindustry.entities.part.DrawPart.PartMove.class);
        classes.put("PartParams", mindustry.entities.part.DrawPart.PartParams.class);
        classes.put("PartProgress", mindustry.entities.part.DrawPart.PartProgress.class);
        classes.put("FlarePart", mindustry.entities.part.FlarePart.class);
        classes.put("HaloPart", mindustry.entities.part.HaloPart.class);
        classes.put("HoverPart", mindustry.entities.part.HoverPart.class);
        classes.put("RegionPart", mindustry.entities.part.RegionPart.class);
        classes.put("ShapePart", mindustry.entities.part.ShapePart.class);
        classes.put("ShootAlternate", mindustry.entities.pattern.ShootAlternate.class);
        classes.put("ShootBarrel", mindustry.entities.pattern.ShootBarrel.class);
        classes.put("ShootHelix", mindustry.entities.pattern.ShootHelix.class);
        classes.put("ShootMulti", mindustry.entities.pattern.ShootMulti.class);
        classes.put("ShootPattern", mindustry.entities.pattern.ShootPattern.class);
        classes.put("BulletHandler", mindustry.entities.pattern.ShootPattern.BulletHandler.class);
        classes.put("ShootSine", mindustry.entities.pattern.ShootSine.class);
        classes.put("ShootSpread", mindustry.entities.pattern.ShootSpread.class);
        classes.put("Objectives", mindustry.game.Objectives.class);
        classes.put("Objective", mindustry.game.Objectives.Objective.class);
        classes.put("OnPlanet", mindustry.game.Objectives.OnPlanet.class);
        classes.put("OnSector", mindustry.game.Objectives.OnSector.class);
        classes.put("Produce", mindustry.game.Objectives.Produce.class);
        classes.put("Research", mindustry.game.Objectives.Research.class);
        classes.put("SectorComplete", mindustry.game.Objectives.SectorComplete.class);
        classes.put("AmmoType", mindustry.type.AmmoType.class);
        classes.put("Category", mindustry.type.Category.class);
        classes.put("CellLiquid", mindustry.type.CellLiquid.class);
        classes.put("ErrorContent", mindustry.type.ErrorContent.class);
        classes.put("Item", mindustry.type.Item.class);
        classes.put("ItemSeq", mindustry.type.ItemSeq.class);
        classes.put("ItemStack", mindustry.type.ItemStack.class);
        classes.put("Liquid", mindustry.type.Liquid.class);
        classes.put("LiquidStack", mindustry.type.LiquidStack.class);
        classes.put("PayloadSeq", mindustry.type.PayloadSeq.class);
        classes.put("PayloadStack", mindustry.type.PayloadStack.class);
        classes.put("Planet", mindustry.type.Planet.class);
        classes.put("Publishable", mindustry.type.Publishable.class);
        classes.put("Sector", mindustry.type.Sector.class);
        classes.put("SectorRect", mindustry.type.Sector.SectorRect.class);
        classes.put("SectorPreset", mindustry.type.SectorPreset.class);
        classes.put("StatusEffect", mindustry.type.StatusEffect.class);
        classes.put("TransitionHandler", mindustry.type.StatusEffect.TransitionHandler.class);
        classes.put("TeamEntry", mindustry.type.TeamEntry.class);
        classes.put("UnitType", mindustry.type.UnitType.class);
        classes.put("UnitEngine", mindustry.type.UnitType.UnitEngine.class);
        classes.put("Weapon", mindustry.type.Weapon.class);
        classes.put("Weather", mindustry.type.Weather.class);
        classes.put("WeatherEntry", mindustry.type.Weather.WeatherEntry.class);
        classes.put("ItemAmmoType", mindustry.type.ammo.ItemAmmoType.class);
        classes.put("PowerAmmoType", mindustry.type.ammo.PowerAmmoType.class);
        classes.put("ErekirUnitType", mindustry.type.unit.ErekirUnitType.class);
        classes.put("MissileUnitType", mindustry.type.unit.MissileUnitType.class);
        classes.put("NeoplasmUnitType", mindustry.type.unit.NeoplasmUnitType.class);
        classes.put("TankUnitType", mindustry.type.unit.TankUnitType.class);
        classes.put("BuildWeapon", mindustry.type.weapons.BuildWeapon.class);
        classes.put("PointDefenseWeapon", mindustry.type.weapons.PointDefenseWeapon.class);
        classes.put("RepairBeamWeapon", mindustry.type.weapons.RepairBeamWeapon.class);
        classes.put("HealBeamMount", mindustry.type.weapons.RepairBeamWeapon.HealBeamMount.class);
        classes.put("MagneticStorm", mindustry.type.weather.MagneticStorm.class);
        classes.put("ParticleWeather", mindustry.type.weather.ParticleWeather.class);
        classes.put("RainWeather", mindustry.type.weather.RainWeather.class);
        classes.put("SolarFlare", mindustry.type.weather.SolarFlare.class);
        classes.put("Attributes", mindustry.world.blocks.Attributes.class);
        classes.put("Autotiler", mindustry.world.blocks.Autotiler.class);
        classes.put("AutotilerHolder", mindustry.world.blocks.Autotiler.AutotilerHolder.class);
        classes.put("SliceMode", mindustry.world.blocks.Autotiler.SliceMode.class);
        classes.put("ConstructBlock", mindustry.world.blocks.ConstructBlock.class);
        classes.put("ConstructBuild", mindustry.world.blocks.ConstructBlock.ConstructBuild.class);
        classes.put("ControlBlock", mindustry.world.blocks.ControlBlock.class);
        classes.put("ItemSelection", mindustry.world.blocks.ItemSelection.class);
        classes.put("Accelerator", mindustry.world.blocks.campaign.Accelerator.class);
        classes.put(
                "AcceleratorBuild",
                mindustry.world.blocks.campaign.Accelerator.AcceleratorBuild.class);
        classes.put("LaunchPad", mindustry.world.blocks.campaign.LaunchPad.class);
        classes.put(
                "LaunchPadBuild", mindustry.world.blocks.campaign.LaunchPad.LaunchPadBuild.class);
        classes.put("AutoDoor", mindustry.world.blocks.defense.AutoDoor.class);
        classes.put("AutoDoorBuild", mindustry.world.blocks.defense.AutoDoor.AutoDoorBuild.class);
        classes.put("BaseShield", mindustry.world.blocks.defense.BaseShield.class);
        classes.put(
                "BaseShieldBuild", mindustry.world.blocks.defense.BaseShield.BaseShieldBuild.class);
        classes.put("BuildTurret", mindustry.world.blocks.defense.BuildTurret.class);
        classes.put(
                "BuildTurretBuild",
                mindustry.world.blocks.defense.BuildTurret.BuildTurretBuild.class);
        classes.put(
                "DirectionalForceProjector",
                mindustry.world.blocks.defense.DirectionalForceProjector.class);
        classes.put(
                "DirectionalForceProjectorBuild",
                mindustry.world.blocks.defense.DirectionalForceProjector
                        .DirectionalForceProjectorBuild.class);
        classes.put("Door", mindustry.world.blocks.defense.Door.class);
        classes.put("DoorBuild", mindustry.world.blocks.defense.Door.DoorBuild.class);
        classes.put("ForceProjector", mindustry.world.blocks.defense.ForceProjector.class);
        classes.put("ForceBuild", mindustry.world.blocks.defense.ForceProjector.ForceBuild.class);
        classes.put("MendProjector", mindustry.world.blocks.defense.MendProjector.class);
        classes.put("MendBuild", mindustry.world.blocks.defense.MendProjector.MendBuild.class);
        classes.put("OverdriveProjector", mindustry.world.blocks.defense.OverdriveProjector.class);
        classes.put(
                "OverdriveBuild",
                mindustry.world.blocks.defense.OverdriveProjector.OverdriveBuild.class);
        classes.put("Radar", mindustry.world.blocks.defense.Radar.class);
        classes.put("RadarBuild", mindustry.world.blocks.defense.Radar.RadarBuild.class);
        classes.put("RegenProjector", mindustry.world.blocks.defense.RegenProjector.class);
        classes.put(
                "RegenProjectorBuild",
                mindustry.world.blocks.defense.RegenProjector.RegenProjectorBuild.class);
        classes.put("ShieldBreaker", mindustry.world.blocks.defense.ShieldBreaker.class);
        classes.put(
                "ShieldBreakerBuild",
                mindustry.world.blocks.defense.ShieldBreaker.ShieldBreakerBuild.class);
        classes.put("ShieldWall", mindustry.world.blocks.defense.ShieldWall.class);
        classes.put(
                "ShieldWallBuild", mindustry.world.blocks.defense.ShieldWall.ShieldWallBuild.class);
        classes.put("ShockMine", mindustry.world.blocks.defense.ShockMine.class);
        classes.put(
                "ShockMineBuild", mindustry.world.blocks.defense.ShockMine.ShockMineBuild.class);
        classes.put("Thruster", mindustry.world.blocks.defense.Thruster.class);
        classes.put("ThrusterBuild", mindustry.world.blocks.defense.Thruster.ThrusterBuild.class);
        classes.put("Wall", mindustry.world.blocks.defense.Wall.class);
        classes.put("WallBuild", mindustry.world.blocks.defense.Wall.WallBuild.class);
        classes.put("BaseTurret", mindustry.world.blocks.defense.turrets.BaseTurret.class);
        classes.put(
                "BaseTurretBuild",
                mindustry.world.blocks.defense.turrets.BaseTurret.BaseTurretBuild.class);
        classes.put(
                "ContinuousLiquidTurret",
                mindustry.world.blocks.defense.turrets.ContinuousLiquidTurret.class);
        classes.put(
                "ContinuousLiquidTurretBuild",
                mindustry.world.blocks.defense.turrets.ContinuousLiquidTurret
                        .ContinuousLiquidTurretBuild.class);
        classes.put(
                "ContinuousTurret", mindustry.world.blocks.defense.turrets.ContinuousTurret.class);
        classes.put(
                "ContinuousTurretBuild",
                mindustry.world.blocks.defense.turrets.ContinuousTurret.ContinuousTurretBuild
                        .class);
        classes.put("ItemTurret", mindustry.world.blocks.defense.turrets.ItemTurret.class);
        classes.put("ItemEntry", mindustry.world.blocks.defense.turrets.ItemTurret.ItemEntry.class);
        classes.put(
                "ItemTurretBuild",
                mindustry.world.blocks.defense.turrets.ItemTurret.ItemTurretBuild.class);
        classes.put("LaserTurret", mindustry.world.blocks.defense.turrets.LaserTurret.class);
        classes.put(
                "LaserTurretBuild",
                mindustry.world.blocks.defense.turrets.LaserTurret.LaserTurretBuild.class);
        classes.put("LiquidTurret", mindustry.world.blocks.defense.turrets.LiquidTurret.class);
        classes.put(
                "LiquidTurretBuild",
                mindustry.world.blocks.defense.turrets.LiquidTurret.LiquidTurretBuild.class);
        classes.put(
                "PayloadAmmoTurret",
                mindustry.world.blocks.defense.turrets.PayloadAmmoTurret.class);
        classes.put(
                "PayloadTurretBuild",
                mindustry.world.blocks.defense.turrets.PayloadAmmoTurret.PayloadTurretBuild.class);
        classes.put(
                "PointDefenseTurret",
                mindustry.world.blocks.defense.turrets.PointDefenseTurret.class);
        classes.put(
                "PointDefenseBuild",
                mindustry.world.blocks.defense.turrets.PointDefenseTurret.PointDefenseBuild.class);
        classes.put("PowerTurret", mindustry.world.blocks.defense.turrets.PowerTurret.class);
        classes.put(
                "PowerTurretBuild",
                mindustry.world.blocks.defense.turrets.PowerTurret.PowerTurretBuild.class);
        classes.put("ReloadTurret", mindustry.world.blocks.defense.turrets.ReloadTurret.class);
        classes.put(
                "ReloadTurretBuild",
                mindustry.world.blocks.defense.turrets.ReloadTurret.ReloadTurretBuild.class);
        classes.put(
                "TractorBeamTurret",
                mindustry.world.blocks.defense.turrets.TractorBeamTurret.class);
        classes.put(
                "TractorBeamBuild",
                mindustry.world.blocks.defense.turrets.TractorBeamTurret.TractorBeamBuild.class);
        classes.put("Turret", mindustry.world.blocks.defense.turrets.Turret.class);
        classes.put("AmmoEntry", mindustry.world.blocks.defense.turrets.Turret.AmmoEntry.class);
        classes.put("BulletEntry", mindustry.world.blocks.defense.turrets.Turret.BulletEntry.class);
        classes.put("TurretBuild", mindustry.world.blocks.defense.turrets.Turret.TurretBuild.class);
        classes.put("ArmoredConveyor", mindustry.world.blocks.distribution.ArmoredConveyor.class);
        classes.put(
                "ArmoredConveyorBuild",
                mindustry.world.blocks.distribution.ArmoredConveyor.ArmoredConveyorBuild.class);
        classes.put(
                "BufferedItemBridge", mindustry.world.blocks.distribution.BufferedItemBridge.class);
        classes.put(
                "BufferedItemBridgeBuild",
                mindustry.world.blocks.distribution.BufferedItemBridge.BufferedItemBridgeBuild
                        .class);
        classes.put("ChainedBuilding", mindustry.world.blocks.distribution.ChainedBuilding.class);
        classes.put("Conveyor", mindustry.world.blocks.distribution.Conveyor.class);
        classes.put(
                "ConveyorBuild", mindustry.world.blocks.distribution.Conveyor.ConveyorBuild.class);
        classes.put("DirectionBridge", mindustry.world.blocks.distribution.DirectionBridge.class);
        classes.put(
                "DirectionBridgeBuild",
                mindustry.world.blocks.distribution.DirectionBridge.DirectionBridgeBuild.class);
        classes.put(
                "DirectionLiquidBridge",
                mindustry.world.blocks.distribution.DirectionLiquidBridge.class);
        classes.put(
                "DuctBridgeBuild",
                mindustry.world.blocks.distribution.DirectionLiquidBridge.DuctBridgeBuild.class);
        classes.put(
                "DirectionalUnloader",
                mindustry.world.blocks.distribution.DirectionalUnloader.class);
        classes.put(
                "DirectionalUnloaderBuild",
                mindustry.world.blocks.distribution.DirectionalUnloader.DirectionalUnloaderBuild
                        .class);
        classes.put("Duct", mindustry.world.blocks.distribution.Duct.class);
        classes.put("DuctBuild", mindustry.world.blocks.distribution.Duct.DuctBuild.class);
        classes.put("DuctBridge", mindustry.world.blocks.distribution.DuctBridge.class);
        classes.put(
                "DuctBridgeBuild",
                mindustry.world.blocks.distribution.DuctBridge.DuctBridgeBuild.class);
        classes.put("DuctRouter", mindustry.world.blocks.distribution.DuctRouter.class);
        classes.put(
                "DuctRouterBuild",
                mindustry.world.blocks.distribution.DuctRouter.DuctRouterBuild.class);
        classes.put("ItemBridge", mindustry.world.blocks.distribution.ItemBridge.class);
        classes.put(
                "ItemBridgeBuild",
                mindustry.world.blocks.distribution.ItemBridge.ItemBridgeBuild.class);
        classes.put("Junction", mindustry.world.blocks.distribution.Junction.class);
        classes.put(
                "JunctionBuild", mindustry.world.blocks.distribution.Junction.JunctionBuild.class);
        classes.put("MassDriver", mindustry.world.blocks.distribution.MassDriver.class);
        classes.put(
                "DriverBulletData",
                mindustry.world.blocks.distribution.MassDriver.DriverBulletData.class);
        classes.put(
                "DriverState", mindustry.world.blocks.distribution.MassDriver.DriverState.class);
        classes.put(
                "MassDriverBuild",
                mindustry.world.blocks.distribution.MassDriver.MassDriverBuild.class);
        classes.put("OverflowDuct", mindustry.world.blocks.distribution.OverflowDuct.class);
        classes.put(
                "OverflowDuctBuild",
                mindustry.world.blocks.distribution.OverflowDuct.OverflowDuctBuild.class);
        classes.put("OverflowGate", mindustry.world.blocks.distribution.OverflowGate.class);
        classes.put(
                "OverflowGateBuild",
                mindustry.world.blocks.distribution.OverflowGate.OverflowGateBuild.class);
        classes.put("Router", mindustry.world.blocks.distribution.Router.class);
        classes.put("RouterBuild", mindustry.world.blocks.distribution.Router.RouterBuild.class);
        classes.put("Sorter", mindustry.world.blocks.distribution.Sorter.class);
        classes.put("SorterBuild", mindustry.world.blocks.distribution.Sorter.SorterBuild.class);
        classes.put("StackConveyor", mindustry.world.blocks.distribution.StackConveyor.class);
        classes.put(
                "StackConveyorBuild",
                mindustry.world.blocks.distribution.StackConveyor.StackConveyorBuild.class);
        classes.put("StackRouter", mindustry.world.blocks.distribution.StackRouter.class);
        classes.put(
                "StackRouterBuild",
                mindustry.world.blocks.distribution.StackRouter.StackRouterBuild.class);
        classes.put("AirBlock", mindustry.world.blocks.environment.AirBlock.class);
        classes.put("Cliff", mindustry.world.blocks.environment.Cliff.class);
        classes.put("EmptyFloor", mindustry.world.blocks.environment.EmptyFloor.class);
        classes.put("Floor", mindustry.world.blocks.environment.Floor.class);
        classes.put(
                "UpdateRenderState",
                mindustry.world.blocks.environment.Floor.UpdateRenderState.class);
        classes.put("OreBlock", mindustry.world.blocks.environment.OreBlock.class);
        classes.put("OverlayFloor", mindustry.world.blocks.environment.OverlayFloor.class);
        classes.put("Prop", mindustry.world.blocks.environment.Prop.class);
        classes.put("SeaBush", mindustry.world.blocks.environment.SeaBush.class);
        classes.put("Seaweed", mindustry.world.blocks.environment.Seaweed.class);
        classes.put("ShallowLiquid", mindustry.world.blocks.environment.ShallowLiquid.class);
        classes.put("SpawnBlock", mindustry.world.blocks.environment.SpawnBlock.class);
        classes.put("StaticTree", mindustry.world.blocks.environment.StaticTree.class);
        classes.put("StaticWall", mindustry.world.blocks.environment.StaticWall.class);
        classes.put("SteamVent", mindustry.world.blocks.environment.SteamVent.class);
        classes.put("TallBlock", mindustry.world.blocks.environment.TallBlock.class);
        classes.put("TreeBlock", mindustry.world.blocks.environment.TreeBlock.class);
        classes.put("WobbleProp", mindustry.world.blocks.environment.WobbleProp.class);
        classes.put("HeatBlock", mindustry.world.blocks.heat.HeatBlock.class);
        classes.put("HeatConductor", mindustry.world.blocks.heat.HeatConductor.class);
        classes.put(
                "HeatConductorBuild",
                mindustry.world.blocks.heat.HeatConductor.HeatConductorBuild.class);
        classes.put("HeatConsumer", mindustry.world.blocks.heat.HeatConsumer.class);
        classes.put("HeatProducer", mindustry.world.blocks.heat.HeatProducer.class);
        classes.put(
                "HeatProducerBuild",
                mindustry.world.blocks.heat.HeatProducer.HeatProducerBuild.class);
        classes.put("LegacyBlock", mindustry.world.blocks.legacy.LegacyBlock.class);
        classes.put("LegacyCommandCenter", mindustry.world.blocks.legacy.LegacyCommandCenter.class);
        classes.put(
                "CommandBuild",
                mindustry.world.blocks.legacy.LegacyCommandCenter.CommandBuild.class);
        classes.put("LegacyMechPad", mindustry.world.blocks.legacy.LegacyMechPad.class);
        classes.put(
                "LegacyMechPadBuild",
                mindustry.world.blocks.legacy.LegacyMechPad.LegacyMechPadBuild.class);
        classes.put("LegacyUnitFactory", mindustry.world.blocks.legacy.LegacyUnitFactory.class);
        classes.put(
                "LegacyUnitFactoryBuild",
                mindustry.world.blocks.legacy.LegacyUnitFactory.LegacyUnitFactoryBuild.class);
        classes.put("ArmoredConduit", mindustry.world.blocks.liquid.ArmoredConduit.class);
        classes.put(
                "ArmoredConduitBuild",
                mindustry.world.blocks.liquid.ArmoredConduit.ArmoredConduitBuild.class);
        classes.put("Conduit", mindustry.world.blocks.liquid.Conduit.class);
        classes.put("ConduitBuild", mindustry.world.blocks.liquid.Conduit.ConduitBuild.class);
        classes.put("LiquidBlock", mindustry.world.blocks.liquid.LiquidBlock.class);
        classes.put("LiquidBuild", mindustry.world.blocks.liquid.LiquidBlock.LiquidBuild.class);
        classes.put("LiquidBridge", mindustry.world.blocks.liquid.LiquidBridge.class);
        classes.put(
                "LiquidBridgeBuild",
                mindustry.world.blocks.liquid.LiquidBridge.LiquidBridgeBuild.class);
        classes.put("LiquidJunction", mindustry.world.blocks.liquid.LiquidJunction.class);
        classes.put(
                "LiquidJunctionBuild",
                mindustry.world.blocks.liquid.LiquidJunction.LiquidJunctionBuild.class);
        classes.put("LiquidRouter", mindustry.world.blocks.liquid.LiquidRouter.class);
        classes.put(
                "LiquidRouterBuild",
                mindustry.world.blocks.liquid.LiquidRouter.LiquidRouterBuild.class);
        classes.put("CanvasBlock", mindustry.world.blocks.logic.CanvasBlock.class);
        classes.put("CanvasBuild", mindustry.world.blocks.logic.CanvasBlock.CanvasBuild.class);
        classes.put("LogicBlock", mindustry.world.blocks.logic.LogicBlock.class);
        classes.put("LogicBuild", mindustry.world.blocks.logic.LogicBlock.LogicBuild.class);
        classes.put("LogicLink", mindustry.world.blocks.logic.LogicBlock.LogicLink.class);
        classes.put("LogicDisplay", mindustry.world.blocks.logic.LogicDisplay.class);
        classes.put("GraphicsType", mindustry.world.blocks.logic.LogicDisplay.GraphicsType.class);
        classes.put(
                "LogicDisplayBuild",
                mindustry.world.blocks.logic.LogicDisplay.LogicDisplayBuild.class);
        classes.put("MemoryBlock", mindustry.world.blocks.logic.MemoryBlock.class);
        classes.put("MemoryBuild", mindustry.world.blocks.logic.MemoryBlock.MemoryBuild.class);
        classes.put("MessageBlock", mindustry.world.blocks.logic.MessageBlock.class);
        classes.put("MessageBuild", mindustry.world.blocks.logic.MessageBlock.MessageBuild.class);
        classes.put("SwitchBlock", mindustry.world.blocks.logic.SwitchBlock.class);
        classes.put("SwitchBuild", mindustry.world.blocks.logic.SwitchBlock.SwitchBuild.class);
        classes.put("BlockProducer", mindustry.world.blocks.payloads.BlockProducer.class);
        classes.put(
                "BlockProducerBuild",
                mindustry.world.blocks.payloads.BlockProducer.BlockProducerBuild.class);
        classes.put("BuildPayload", mindustry.world.blocks.payloads.BuildPayload.class);
        classes.put("Constructor", mindustry.world.blocks.payloads.Constructor.class);
        classes.put(
                "ConstructorBuild",
                mindustry.world.blocks.payloads.Constructor.ConstructorBuild.class);
        classes.put("Payload", mindustry.world.blocks.payloads.Payload.class);
        classes.put("PayloadBlock", mindustry.world.blocks.payloads.PayloadBlock.class);
        classes.put(
                "PayloadBlockBuild",
                mindustry.world.blocks.payloads.PayloadBlock.PayloadBlockBuild.class);
        classes.put("PayloadConveyor", mindustry.world.blocks.payloads.PayloadConveyor.class);
        classes.put(
                "PayloadConveyorBuild",
                mindustry.world.blocks.payloads.PayloadConveyor.PayloadConveyorBuild.class);
        classes.put(
                "PayloadDeconstructor", mindustry.world.blocks.payloads.PayloadDeconstructor.class);
        classes.put(
                "PayloadDeconstructorBuild",
                mindustry.world.blocks.payloads.PayloadDeconstructor.PayloadDeconstructorBuild
                        .class);
        classes.put("PayloadLoader", mindustry.world.blocks.payloads.PayloadLoader.class);
        classes.put(
                "PayloadLoaderBuild",
                mindustry.world.blocks.payloads.PayloadLoader.PayloadLoaderBuild.class);
        classes.put("PayloadMassDriver", mindustry.world.blocks.payloads.PayloadMassDriver.class);
        classes.put(
                "PayloadDriverBuild",
                mindustry.world.blocks.payloads.PayloadMassDriver.PayloadDriverBuild.class);
        classes.put(
                "PayloadDriverState",
                mindustry.world.blocks.payloads.PayloadMassDriver.PayloadDriverState.class);
        classes.put(
                "PayloadMassDriverData",
                mindustry.world.blocks.payloads.PayloadMassDriver.PayloadMassDriverData.class);
        classes.put("PayloadRouter", mindustry.world.blocks.payloads.PayloadRouter.class);
        classes.put(
                "PayloadRouterBuild",
                mindustry.world.blocks.payloads.PayloadRouter.PayloadRouterBuild.class);
        classes.put("PayloadSource", mindustry.world.blocks.payloads.PayloadSource.class);
        classes.put(
                "PayloadSourceBuild",
                mindustry.world.blocks.payloads.PayloadSource.PayloadSourceBuild.class);
        classes.put("PayloadUnloader", mindustry.world.blocks.payloads.PayloadUnloader.class);
        classes.put(
                "PayloadUnloaderBuild",
                mindustry.world.blocks.payloads.PayloadUnloader.PayloadUnloaderBuild.class);
        classes.put("PayloadVoid", mindustry.world.blocks.payloads.PayloadVoid.class);
        classes.put(
                "PayloadVoidBuild",
                mindustry.world.blocks.payloads.PayloadVoid.PayloadVoidBuild.class);
        classes.put("UnitPayload", mindustry.world.blocks.payloads.UnitPayload.class);
        classes.put("Battery", mindustry.world.blocks.power.Battery.class);
        classes.put("BatteryBuild", mindustry.world.blocks.power.Battery.BatteryBuild.class);
        classes.put("BeamNode", mindustry.world.blocks.power.BeamNode.class);
        classes.put("BeamNodeBuild", mindustry.world.blocks.power.BeamNode.BeamNodeBuild.class);
        classes.put("ConsumeGenerator", mindustry.world.blocks.power.ConsumeGenerator.class);
        classes.put(
                "ConsumeGeneratorBuild",
                mindustry.world.blocks.power.ConsumeGenerator.ConsumeGeneratorBuild.class);
        classes.put("ImpactReactor", mindustry.world.blocks.power.ImpactReactor.class);
        classes.put(
                "ImpactReactorBuild",
                mindustry.world.blocks.power.ImpactReactor.ImpactReactorBuild.class);
        classes.put("LightBlock", mindustry.world.blocks.power.LightBlock.class);
        classes.put("LightBuild", mindustry.world.blocks.power.LightBlock.LightBuild.class);
        classes.put("LongPowerNode", mindustry.world.blocks.power.LongPowerNode.class);
        classes.put(
                "LongPowerNodeBuild",
                mindustry.world.blocks.power.LongPowerNode.LongPowerNodeBuild.class);
        classes.put("NuclearReactor", mindustry.world.blocks.power.NuclearReactor.class);
        classes.put(
                "NuclearReactorBuild",
                mindustry.world.blocks.power.NuclearReactor.NuclearReactorBuild.class);
        classes.put("PowerBlock", mindustry.world.blocks.power.PowerBlock.class);
        classes.put("PowerDiode", mindustry.world.blocks.power.PowerDiode.class);
        classes.put(
                "PowerDiodeBuild", mindustry.world.blocks.power.PowerDiode.PowerDiodeBuild.class);
        classes.put("PowerDistributor", mindustry.world.blocks.power.PowerDistributor.class);
        classes.put("PowerGenerator", mindustry.world.blocks.power.PowerGenerator.class);
        classes.put(
                "GeneratorBuild", mindustry.world.blocks.power.PowerGenerator.GeneratorBuild.class);
        classes.put("PowerGraph", mindustry.world.blocks.power.PowerGraph.class);
        classes.put("PowerNode", mindustry.world.blocks.power.PowerNode.class);
        classes.put("PowerNodeBuild", mindustry.world.blocks.power.PowerNode.PowerNodeBuild.class);
        classes.put("SolarGenerator", mindustry.world.blocks.power.SolarGenerator.class);
        classes.put(
                "SolarGeneratorBuild",
                mindustry.world.blocks.power.SolarGenerator.SolarGeneratorBuild.class);
        classes.put("ThermalGenerator", mindustry.world.blocks.power.ThermalGenerator.class);
        classes.put(
                "ThermalGeneratorBuild",
                mindustry.world.blocks.power.ThermalGenerator.ThermalGeneratorBuild.class);
        classes.put("AttributeCrafter", mindustry.world.blocks.production.AttributeCrafter.class);
        classes.put(
                "AttributeCrafterBuild",
                mindustry.world.blocks.production.AttributeCrafter.AttributeCrafterBuild.class);
        classes.put("BeamDrill", mindustry.world.blocks.production.BeamDrill.class);
        classes.put(
                "BeamDrillBuild", mindustry.world.blocks.production.BeamDrill.BeamDrillBuild.class);
        classes.put("BurstDrill", mindustry.world.blocks.production.BurstDrill.class);
        classes.put(
                "BurstDrillBuild",
                mindustry.world.blocks.production.BurstDrill.BurstDrillBuild.class);
        classes.put("Drill", mindustry.world.blocks.production.Drill.class);
        classes.put("DrillBuild", mindustry.world.blocks.production.Drill.DrillBuild.class);
        classes.put("Fracker", mindustry.world.blocks.production.Fracker.class);
        classes.put("FrackerBuild", mindustry.world.blocks.production.Fracker.FrackerBuild.class);
        classes.put("GenericCrafter", mindustry.world.blocks.production.GenericCrafter.class);
        classes.put(
                "GenericCrafterBuild",
                mindustry.world.blocks.production.GenericCrafter.GenericCrafterBuild.class);
        classes.put("HeatCrafter", mindustry.world.blocks.production.HeatCrafter.class);
        classes.put(
                "HeatCrafterBuild",
                mindustry.world.blocks.production.HeatCrafter.HeatCrafterBuild.class);
        classes.put("Incinerator", mindustry.world.blocks.production.Incinerator.class);
        classes.put(
                "IncineratorBuild",
                mindustry.world.blocks.production.Incinerator.IncineratorBuild.class);
        classes.put("ItemIncinerator", mindustry.world.blocks.production.ItemIncinerator.class);
        classes.put(
                "ItemIncineratorBuild",
                mindustry.world.blocks.production.ItemIncinerator.ItemIncineratorBuild.class);
        classes.put("Pump", mindustry.world.blocks.production.Pump.class);
        classes.put("PumpBuild", mindustry.world.blocks.production.Pump.PumpBuild.class);
        classes.put("Separator", mindustry.world.blocks.production.Separator.class);
        classes.put(
                "SeparatorBuild", mindustry.world.blocks.production.Separator.SeparatorBuild.class);
        classes.put(
                "SingleBlockProducer", mindustry.world.blocks.production.SingleBlockProducer.class);
        classes.put(
                "SingleBlockProducerBuild",
                mindustry.world.blocks.production.SingleBlockProducer.SingleBlockProducerBuild
                        .class);
        classes.put("SolidPump", mindustry.world.blocks.production.SolidPump.class);
        classes.put(
                "SolidPumpBuild", mindustry.world.blocks.production.SolidPump.SolidPumpBuild.class);
        classes.put("WallCrafter", mindustry.world.blocks.production.WallCrafter.class);
        classes.put(
                "WallCrafterBuild",
                mindustry.world.blocks.production.WallCrafter.WallCrafterBuild.class);
        classes.put("ItemSource", mindustry.world.blocks.sandbox.ItemSource.class);
        classes.put(
                "ItemSourceBuild", mindustry.world.blocks.sandbox.ItemSource.ItemSourceBuild.class);
        classes.put("ItemVoid", mindustry.world.blocks.sandbox.ItemVoid.class);
        classes.put("ItemVoidBuild", mindustry.world.blocks.sandbox.ItemVoid.ItemVoidBuild.class);
        classes.put("LiquidSource", mindustry.world.blocks.sandbox.LiquidSource.class);
        classes.put(
                "LiquidSourceBuild",
                mindustry.world.blocks.sandbox.LiquidSource.LiquidSourceBuild.class);
        classes.put("LiquidVoid", mindustry.world.blocks.sandbox.LiquidVoid.class);
        classes.put(
                "LiquidVoidBuild", mindustry.world.blocks.sandbox.LiquidVoid.LiquidVoidBuild.class);
        classes.put("PowerSource", mindustry.world.blocks.sandbox.PowerSource.class);
        classes.put(
                "PowerSourceBuild",
                mindustry.world.blocks.sandbox.PowerSource.PowerSourceBuild.class);
        classes.put("PowerVoid", mindustry.world.blocks.sandbox.PowerVoid.class);
        classes.put("CoreBlock", mindustry.world.blocks.storage.CoreBlock.class);
        classes.put("CoreBuild", mindustry.world.blocks.storage.CoreBlock.CoreBuild.class);
        classes.put("StorageBlock", mindustry.world.blocks.storage.StorageBlock.class);
        classes.put("StorageBuild", mindustry.world.blocks.storage.StorageBlock.StorageBuild.class);
        classes.put("Unloader", mindustry.world.blocks.storage.Unloader.class);
        classes.put("ContainerStat", mindustry.world.blocks.storage.Unloader.ContainerStat.class);
        classes.put("UnloaderBuild", mindustry.world.blocks.storage.Unloader.UnloaderBuild.class);
        classes.put("DroneCenter", mindustry.world.blocks.units.DroneCenter.class);
        classes.put(
                "DroneCenterBuild",
                mindustry.world.blocks.units.DroneCenter.DroneCenterBuild.class);
        classes.put("EffectDroneAI", mindustry.world.blocks.units.DroneCenter.EffectDroneAI.class);
        classes.put("Reconstructor", mindustry.world.blocks.units.Reconstructor.class);
        classes.put(
                "ReconstructorBuild",
                mindustry.world.blocks.units.Reconstructor.ReconstructorBuild.class);
        classes.put("RepairTower", mindustry.world.blocks.units.RepairTower.class);
        classes.put(
                "RepairTowerBuild",
                mindustry.world.blocks.units.RepairTower.RepairTowerBuild.class);
        classes.put("RepairTurret", mindustry.world.blocks.units.RepairTurret.class);
        classes.put(
                "RepairPointBuild",
                mindustry.world.blocks.units.RepairTurret.RepairPointBuild.class);
        classes.put("UnitAssembler", mindustry.world.blocks.units.UnitAssembler.class);
        classes.put(
                "AssemblerUnitPlan",
                mindustry.world.blocks.units.UnitAssembler.AssemblerUnitPlan.class);
        classes.put(
                "UnitAssemblerBuild",
                mindustry.world.blocks.units.UnitAssembler.UnitAssemblerBuild.class);
        classes.put("YeetData", mindustry.world.blocks.units.UnitAssembler.YeetData.class);
        classes.put("UnitAssemblerModule", mindustry.world.blocks.units.UnitAssemblerModule.class);
        classes.put(
                "UnitAssemblerModuleBuild",
                mindustry.world.blocks.units.UnitAssemblerModule.UnitAssemblerModuleBuild.class);
        classes.put("UnitBlock", mindustry.world.blocks.units.UnitBlock.class);
        classes.put("UnitBuild", mindustry.world.blocks.units.UnitBlock.UnitBuild.class);
        classes.put("UnitCargoLoader", mindustry.world.blocks.units.UnitCargoLoader.class);
        classes.put(
                "UnitTransportSourceBuild",
                mindustry.world.blocks.units.UnitCargoLoader.UnitTransportSourceBuild.class);
        classes.put(
                "UnitCargoUnloadPoint", mindustry.world.blocks.units.UnitCargoUnloadPoint.class);
        classes.put(
                "UnitCargoUnloadPointBuild",
                mindustry.world.blocks.units.UnitCargoUnloadPoint.UnitCargoUnloadPointBuild.class);
        classes.put("UnitFactory", mindustry.world.blocks.units.UnitFactory.class);
        classes.put(
                "UnitFactoryBuild",
                mindustry.world.blocks.units.UnitFactory.UnitFactoryBuild.class);
        classes.put("UnitPlan", mindustry.world.blocks.units.UnitFactory.UnitPlan.class);
        classes.put("DrawArcSmelt", mindustry.world.draw.DrawArcSmelt.class);
        classes.put("DrawBlock", mindustry.world.draw.DrawBlock.class);
        classes.put("DrawBlurSpin", mindustry.world.draw.DrawBlurSpin.class);
        classes.put("DrawBubbles", mindustry.world.draw.DrawBubbles.class);
        classes.put("DrawCells", mindustry.world.draw.DrawCells.class);
        classes.put("DrawCircles", mindustry.world.draw.DrawCircles.class);
        classes.put("DrawCrucibleFlame", mindustry.world.draw.DrawCrucibleFlame.class);
        classes.put("DrawCultivator", mindustry.world.draw.DrawCultivator.class);
        classes.put("DrawDefault", mindustry.world.draw.DrawDefault.class);
        classes.put("DrawFade", mindustry.world.draw.DrawFade.class);
        classes.put("DrawFlame", mindustry.world.draw.DrawFlame.class);
        classes.put("DrawFrames", mindustry.world.draw.DrawFrames.class);
        classes.put("DrawGlowRegion", mindustry.world.draw.DrawGlowRegion.class);
        classes.put("DrawHeatInput", mindustry.world.draw.DrawHeatInput.class);
        classes.put("DrawHeatOutput", mindustry.world.draw.DrawHeatOutput.class);
        classes.put("DrawHeatRegion", mindustry.world.draw.DrawHeatRegion.class);
        classes.put("DrawLiquidOutputs", mindustry.world.draw.DrawLiquidOutputs.class);
        classes.put("DrawLiquidRegion", mindustry.world.draw.DrawLiquidRegion.class);
        classes.put("DrawLiquidTile", mindustry.world.draw.DrawLiquidTile.class);
        classes.put("DrawMulti", mindustry.world.draw.DrawMulti.class);
        classes.put("DrawMultiWeave", mindustry.world.draw.DrawMultiWeave.class);
        classes.put("DrawParticles", mindustry.world.draw.DrawParticles.class);
        classes.put("DrawPistons", mindustry.world.draw.DrawPistons.class);
        classes.put("DrawPulseShape", mindustry.world.draw.DrawPulseShape.class);
        classes.put("DrawPumpLiquid", mindustry.world.draw.DrawPumpLiquid.class);
        classes.put("DrawRegion", mindustry.world.draw.DrawRegion.class);
        classes.put("DrawShape", mindustry.world.draw.DrawShape.class);
        classes.put("DrawSideRegion", mindustry.world.draw.DrawSideRegion.class);
        classes.put("DrawSpikes", mindustry.world.draw.DrawSpikes.class);
        classes.put("DrawTurret", mindustry.world.draw.DrawTurret.class);
        classes.put("DrawWarmupRegion", mindustry.world.draw.DrawWarmupRegion.class);
        classes.put("DrawWeave", mindustry.world.draw.DrawWeave.class);
        classes.put("Block", mindustry.world.Block.class);
    }
}
