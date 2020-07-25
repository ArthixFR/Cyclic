package com.lothrazar.cyclic.registry;

import com.lothrazar.cyclic.ConfigManager;
import com.lothrazar.cyclic.ModCyclic;
import com.lothrazar.cyclic.block.battery.ItemBlockBattery;
import com.lothrazar.cyclic.block.cable.CableWrench;
import com.lothrazar.cyclic.block.expcollect.ExpItemGain;
import com.lothrazar.cyclic.block.scaffolding.ItemScaffolding;
import com.lothrazar.cyclic.block.tank.ItemBlockTank;
import com.lothrazar.cyclic.item.CarbonPaperItem;
import com.lothrazar.cyclic.item.EnderBagItem;
import com.lothrazar.cyclic.item.EnderWingItem;
import com.lothrazar.cyclic.item.EnderWingSp;
import com.lothrazar.cyclic.item.EvokerFangItem;
import com.lothrazar.cyclic.item.FireScepter;
import com.lothrazar.cyclic.item.GemstoneItem;
import com.lothrazar.cyclic.item.HeartItem;
import com.lothrazar.cyclic.item.HeartToxicItem;
import com.lothrazar.cyclic.item.IceWand;
import com.lothrazar.cyclic.item.LeverRemote;
import com.lothrazar.cyclic.item.LightningScepter;
import com.lothrazar.cyclic.item.LocationGpsItem;
import com.lothrazar.cyclic.item.MattockItem;
import com.lothrazar.cyclic.item.PeatItem;
import com.lothrazar.cyclic.item.ShearsMaterial;
import com.lothrazar.cyclic.item.SleepingMatItem;
import com.lothrazar.cyclic.item.SnowScepter;
import com.lothrazar.cyclic.item.SpelunkerCaveFinder;
import com.lothrazar.cyclic.item.StirrupsItem;
import com.lothrazar.cyclic.item.WaterSpreaderItem;
import com.lothrazar.cyclic.item.WrenchItem;
import com.lothrazar.cyclic.item.bauble.AirAntiGravity;
import com.lothrazar.cyclic.item.bauble.AutoTorchItem;
import com.lothrazar.cyclic.item.bauble.CharmAntidote;
import com.lothrazar.cyclic.item.bauble.CharmFire;
import com.lothrazar.cyclic.item.bauble.CharmOverpowered;
import com.lothrazar.cyclic.item.bauble.CharmVoid;
import com.lothrazar.cyclic.item.bauble.CharmWither;
import com.lothrazar.cyclic.item.bauble.GloveItem;
import com.lothrazar.cyclic.item.boomerang.BoomerangItem;
import com.lothrazar.cyclic.item.boomerang.BoomerangItem.Boomer;
import com.lothrazar.cyclic.item.builder.BuildStyle;
import com.lothrazar.cyclic.item.builder.BuilderItem;
import com.lothrazar.cyclic.item.endereye.ItemEnderEyeReuse;
import com.lothrazar.cyclic.item.enderpearl.EnderPearlMount;
import com.lothrazar.cyclic.item.enderpearl.EnderPearlReuse;
import com.lothrazar.cyclic.item.findspawner.ItemProjectileDungeon;
import com.lothrazar.cyclic.item.horse.ItemHorseEmeraldJump;
import com.lothrazar.cyclic.item.horse.ItemHorseHealthDiamondCarrot;
import com.lothrazar.cyclic.item.horse.ItemHorseLapisVariant;
import com.lothrazar.cyclic.item.horse.ItemHorseRedstoneSpeed;
import com.lothrazar.cyclic.item.horse.ItemHorseToxic;
import com.lothrazar.cyclic.item.magicnet.ItemMagicNet;
import com.lothrazar.cyclic.item.magicnet.ItemMobContainer;
import com.lothrazar.cyclic.item.random.RandomizerItem;
import com.lothrazar.cyclic.item.scythe.ScytheBrush;
import com.lothrazar.cyclic.item.scythe.ScytheForage;
import com.lothrazar.cyclic.item.scythe.ScytheLeaves;
import com.lothrazar.cyclic.item.torchthrow.ItemTorchThrower;
import com.lothrazar.cyclic.item.transporter.TileTransporterEmptyItem;
import com.lothrazar.cyclic.item.transporter.TileTransporterItem;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegistry {

  @ObjectHolder(ModCyclic.MODID + ":gem_amber")
  public static Item gem_amber;
  @ObjectHolder(ModCyclic.MODID + ":biomass")
  public static Item biomass;
  @ObjectHolder(ModCyclic.MODID + ":cable_wrench")
  public static CableWrench cable_wrench;
  @ObjectHolder(ModCyclic.MODID + ":antigravity")
  public static Item antigravity;
  @ObjectHolder(ModCyclic.MODID + ":build_scepter")
  public static Item build_scepter;
  @ObjectHolder(ModCyclic.MODID + ":spawner_seeker")
  public static Item spawner_seeker;
  @ObjectHolder(ModCyclic.MODID + ":gem_obsidian")
  public static Item gem_obsidian;
  @ObjectHolder(ModCyclic.MODID + ":lapis_carrot_variant")
  public static Item lapis_carrot_variant;
  @ObjectHolder(ModCyclic.MODID + ":boomerang_damage")
  public static Item boomerang_damage;
  @ObjectHolder(ModCyclic.MODID + ":boomerang_carry")
  public static Item boomerang_carry;
  @ObjectHolder(ModCyclic.MODID + ":boomerang_stun")
  public static Item boomerang_stun;
  @ObjectHolder(ModCyclic.MODID + ":emerald_carrot_jump")
  public static Item emerald_carrot_jump;
  @ObjectHolder(ModCyclic.MODID + ":redstone_carrot_speed")
  public static Item redstone_carrot_speed;
  @ObjectHolder(ModCyclic.MODID + ":diamond_carrot_health")
  public static Item diamond_carrot_health;
  @ObjectHolder(ModCyclic.MODID + ":torch_launcher")
  public static ItemTorchThrower torch_launcher;
  @ObjectHolder(ModCyclic.MODID + ":scaffold_replace")
  public static ItemScaffolding item_scaffold_replace;
  @ObjectHolder(ModCyclic.MODID + ":scaffold_fragile")
  public static ItemScaffolding item_scaffold_fragile;
  @ObjectHolder(ModCyclic.MODID + ":scaffold_responsive")
  public static ItemScaffolding item_scaffold_responsive;
  @ObjectHolder(ModCyclic.MODID + ":mob_container")
  public static ItemMobContainer mob_container;
  @ObjectHolder(ModCyclic.MODID + ":wrench")
  public static WrenchItem wrench;
  @ObjectHolder(ModCyclic.MODID + ":peat_fuel")
  public static PeatItem peat_fuel;
  @ObjectHolder(ModCyclic.MODID + ":peat_fuel_enriched")
  public static PeatItem peat_fuel_enriched;
  @ObjectHolder(ModCyclic.MODID + ":experience_food")
  public static Item experience_food;
  @ObjectHolder(ModCyclic.MODID + ":magic_net")
  public static Item magic_net;
  @ObjectHolder(ModCyclic.MODID + ":tile_transporter")
  public static Item tile_transporter;
  @ObjectHolder(ModCyclic.MODID + ":tile_transporter_empty")
  public static Item tile_transporterempty;
  @ObjectHolder(ModCyclic.MODID + ":toxic_carrot")
  public static Item toxic_carrot;
  @ObjectHolder(ModCyclic.MODID + ":xpjuice_bucket")
  public static Item bucket_expjuice;
  @ObjectHolder(ModCyclic.MODID + ":pattern_reader")
  public static Item pattern_reader;
  @ObjectHolder(ModCyclic.MODID + ":structure_disk")
  public static Item structure_disk;

  @SubscribeEvent
  public static void onItemsRegistry(RegistryEvent.Register<Item> event) {
    IForgeRegistry<Item> r = event.getRegistry();
    r.register(new BlockItem(BlockRegistry.placer_fluid, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("placer_fluid"));
    //    r.register(new StructureDiskItem(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("structure_disk"));
    //    r.register(new BlockItem(BlockRegistry.structure_writer, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("structure_writer"));
    //    r.register(new BlockItem(BlockRegistry.structure_reader, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("structure_reader"));
    //    r.register(new BlockItem(BlockRegistry.structure_copy, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("structure_copy"));
    r.register(new BlockItem(BlockRegistry.flower_cyan, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("flower_cyan"));
    //redstone
    r.register(new BlockItem(BlockRegistry.cask, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("cask"));
    r.register(new BlockItem(BlockRegistry.crate, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("crate"));
    r.register(new BlockItem(BlockRegistry.clock, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("clock"));
    r.register(new BlockItem(BlockRegistry.wireless_transmitter, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("wireless_transmitter"));
    r.register(new BlockItem(BlockRegistry.wireless_receiver, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("wireless_receiver"));
    //fun
    r.register(new BlockItem(BlockRegistry.plate_launch_redstone, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("plate_launch_redstone"));
    r.register(new BlockItem(BlockRegistry.plate_launch, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("plate_launch"));
    // machine blocks   
    r.register(new BlockItem(BlockRegistry.detector_item, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("detector_item"));
    r.register(new BlockItem(BlockRegistry.detector_entity, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("detector_entity"));
    r.register(new BlockItem(BlockRegistry.fisher, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("fisher"));
    r.register(new BlockItem(BlockRegistry.user, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("user"));
    r.register(new BlockItem(BlockRegistry.disenchanter, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("disenchanter"));
    r.register(new BlockItem(BlockRegistry.collector_fluid, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("collector_fluid"));
    r.register(new BlockItem(BlockRegistry.fan, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("fan"));
    r.register(new BlockItem(BlockRegistry.soundproofing, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("soundproofing"));
    r.register(new BlockItem(BlockRegistry.solidifier, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("solidifier"));
    r.register(new BlockItem(BlockRegistry.melter, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("melter"));
    r.register(new BlockItem(BlockRegistry.structure, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("structure"));
    r.register(new BlockItem(BlockRegistry.placer, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("placer"));
    r.register(new BlockItem(BlockRegistry.anvil, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("anvil"));
    r.register(new ItemBlockTank(BlockRegistry.tank, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("tank"));
    r.register(new BlockItem(BlockRegistry.breaker, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("breaker"));
    r.register(new BlockItem(BlockRegistry.collector, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("collector"));
    r.register(new BlockItem(BlockRegistry.dark_glass, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("dark_glass"));
    r.register(new BlockItem(BlockRegistry.harvester, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("harvester"));
    r.register(new BlockItem(BlockRegistry.trash, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("trash"));
    // exp machines
    r.register(new BlockItem(BlockRegistry.experience_pylon, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("experience_pylon"));
    r.register(new ExpItemGain(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("experience_food"));
    // resources
    r.register(new GemstoneItem(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("gem_obsidian"));
    r.register(new GemstoneItem(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("gem_amber"));
    //energy 
    r.register(new ItemBlockBattery(BlockRegistry.battery, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("battery"));
    r.register(new BlockItem(BlockRegistry.peat_generator, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("peat_generator"));
    r.register(new BlockItem(BlockRegistry.peat_unbaked, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("peat_unbaked"));
    r.register(new BlockItem(BlockRegistry.peat_baked, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("peat_baked"));
    r.register(new PeatItem(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("peat_fuel"));
    r.register(new PeatItem(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("peat_fuel_enriched"));
    r.register(new PeatItem(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("biomass"));
    // basic tools
    r.register(new LocationGpsItem(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("location"));
    r.register(new MattockItem(new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(9000)).setRegistryName("mattock"));
    r.register(new SleepingMatItem(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("sleeping_mat"));
    r.register(new ShearsMaterial(new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(1024 * 1024)).setRegistryName("shears_obsidian"));
    r.register(new ShearsMaterial(new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(64)).setRegistryName("shears_flint"));
    r.register(new WrenchItem(new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(256)).setRegistryName("wrench"));
    r.register(new ScytheBrush(new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(256)).setRegistryName("scythe_brush"));
    r.register(new ScytheForage(new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(256)).setRegistryName("scythe_forage"));
    r.register(new ScytheLeaves(new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(256)).setRegistryName("scythe_leaves"));
    r.register(new StirrupsItem(new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(256)).setRegistryName("stirrups"));
    r.register(new LeverRemote(new Item.Properties().group(MaterialRegistry.itemGroup).maxStackSize(1)).setRegistryName("lever_remote"));
    r.register(new BuilderItem(new Item.Properties().group(MaterialRegistry.itemGroup), BuildStyle.NORMAL).setRegistryName("build_scepter"));
    r.register(new BuilderItem(new Item.Properties().group(MaterialRegistry.itemGroup), BuildStyle.REPLACE).setRegistryName("replace_scepter"));
    r.register(new BuilderItem(new Item.Properties().group(MaterialRegistry.itemGroup), BuildStyle.OFFSET).setRegistryName("offset_scepter"));
    r.register(new RandomizerItem(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("randomize_scepter"));
    // magic tools
    r.register(new CarbonPaperItem(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("carbon_paper"));
    r.register(new SnowScepter(new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(256)).setRegistryName("ice_scepter"));
    r.register(new FireScepter(new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(256)).setRegistryName("fire_scepter"));
    r.register(new LightningScepter(new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(256)).setRegistryName("lightning_scepter"));
    r.register(new EnderBagItem(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("ender_bag"));
    r.register(new WaterSpreaderItem(new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(256)).setRegistryName("spell_water"));
    r.register(new IceWand(new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(256)).setRegistryName("spell_ice"));
    r.register(new ItemTorchThrower(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("torch_launcher"));
    r.register(new AutoTorchItem(new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(256 * 4)).setRegistryName("charm_torch"));
    r.register(new EnderWingItem(new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(256)).setRegistryName("charm_home"));
    r.register(new EnderWingSp(new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(256)).setRegistryName("charm_world"));
    r.register(new EvokerFangItem(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("evoker_fang"));
    r.register(new ItemEnderEyeReuse(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("ender_eye_reuse"));
    r.register(new EnderPearlReuse(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("ender_pearl_reuse"));
    r.register(new EnderPearlMount(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("ender_pearl_mounted"));
    r.register(new ItemProjectileDungeon(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("spawner_seeker"));
    r.register(new SpelunkerCaveFinder(new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(256)).setRegistryName("spelunker"));
    r.register(new ItemMagicNet(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("magic_net"));
    r.register(new ItemMobContainer(new Item.Properties().maxStackSize(1)).setRegistryName("mob_container"));
    r.register(new TileTransporterEmptyItem(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("tile_transporter_empty"));
    r.register(new TileTransporterItem(new Item.Properties()).setRegistryName("tile_transporter"));
    if (ConfigManager.BOOMERANGS.get()) {
      r.register(new BoomerangItem(Boomer.STUN, new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(256)).setRegistryName("boomerang_stun"));
      r.register(new BoomerangItem(Boomer.CARRY, new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(256)).setRegistryName("boomerang_carry"));
      r.register(new BoomerangItem(Boomer.DAMAGE, new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(256)).setRegistryName("boomerang_damage"));
    }
    if (ConfigManager.SCAFFOLD.get()) {
      r.register(new ItemScaffolding(BlockRegistry.scaffold_replace,
          new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("scaffold_replace"));
      r.register(new ItemScaffolding(BlockRegistry.scaffold_fragile,
          new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("scaffold_fragile"));
      r.register(new ItemScaffolding(BlockRegistry.scaffold_responsive,
          new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("scaffold_responsive"));
    }
    if (ConfigManager.SPIKES.get()) {
      r.register(new BlockItem(BlockRegistry.spikes_iron, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("spikes_iron"));
      r.register(new BlockItem(BlockRegistry.spikes_curse, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("spikes_curse"));
      r.register(new BlockItem(BlockRegistry.spikes_fire, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("spikes_fire"));
    }
    if (ConfigManager.CABLES.get()) {
      r.register(new BlockItem(BlockRegistry.energy_pipe, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("energy_pipe"));
      r.register(new BlockItem(BlockRegistry.item_pipe, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("item_pipe"));
      r.register(new BlockItem(BlockRegistry.fluid_pipe, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("fluid_pipe"));
      r.register(new CableWrench(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("cable_wrench"));
    }
    if (ConfigManager.GLOVE.get()) {
      r.register(new GloveItem(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("glove_climb"));
    }
    if (ConfigManager.CHARMS.get()) {
      r.register(new AirAntiGravity(new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(1024 * 4)).setRegistryName("antigravity"));
      r.register(new CharmVoid(new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(64)).setRegistryName("charm_void"));
      r.register(new CharmAntidote(new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(64)).setRegistryName("charm_antidote"));
      r.register(new CharmFire(new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(64)).setRegistryName("charm_fire"));
      r.register(new CharmWither(new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(64)).setRegistryName("charm_wither"));
      r.register(new CharmOverpowered(new Item.Properties().group(MaterialRegistry.itemGroup).maxDamage(256)).setRegistryName("charm_ultimate"));
    }
    if (ConfigManager.CARROTS.get()) {
      r.register(new ItemHorseHealthDiamondCarrot(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("diamond_carrot_health"));
      r.register(new ItemHorseRedstoneSpeed(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("redstone_carrot_speed"));
      r.register(new ItemHorseEmeraldJump(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("emerald_carrot_jump"));
      r.register(new ItemHorseLapisVariant(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("lapis_carrot_variant"));
      r.register(new ItemHorseToxic(new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("toxic_carrot"));
    }
    if (ConfigManager.GEMGEAR.get()) {
      r.register(new SwordItem(MaterialRegistry.ToolMats.GEMOBSIDIAN, 3, -2.4F, (new Item.Properties()).group(MaterialRegistry.itemGroup)).setRegistryName("crystal_sword"));
      r.register(new PickaxeItem(MaterialRegistry.ToolMats.GEMOBSIDIAN, 1, -2.8F, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("crystal_pickaxe"));
      r.register(new AxeItem(MaterialRegistry.ToolMats.GEMOBSIDIAN, 5.0F, -3.0F, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("crystal_axe"));
      r.register(new HoeItem(MaterialRegistry.ToolMats.GEMOBSIDIAN, 0.0F, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("crystal_hoe"));
      r.register(new ShovelItem(MaterialRegistry.ToolMats.GEMOBSIDIAN, 1.5F, -3.0F, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("crystal_shovel"));
      r.register(new ArmorItem(MaterialRegistry.ArmorMats.GEMOBSIDIAN, EquipmentSlotType.FEET, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("crystal_boots"));
      r.register(new ArmorItem(MaterialRegistry.ArmorMats.GEMOBSIDIAN, EquipmentSlotType.HEAD, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("crystal_helmet"));
      r.register(new ArmorItem(MaterialRegistry.ArmorMats.GEMOBSIDIAN, EquipmentSlotType.CHEST, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("crystal_chestplate"));
      r.register(new ArmorItem(MaterialRegistry.ArmorMats.GEMOBSIDIAN, EquipmentSlotType.LEGS, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("crystal_leggings"));
    }
    if (ConfigManager.EMERALD.get()) {
      r.register(new SwordItem(MaterialRegistry.ToolMats.EMERALD, 3, -2.4F, (new Item.Properties()).group(MaterialRegistry.itemGroup)).setRegistryName("emerald_sword"));
      r.register(new PickaxeItem(MaterialRegistry.ToolMats.EMERALD, 1, -2.8F, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("emerald_pickaxe"));
      r.register(new AxeItem(MaterialRegistry.ToolMats.EMERALD, 5.0F, -3.0F, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("emerald_axe"));
      r.register(new HoeItem(MaterialRegistry.ToolMats.EMERALD, 0.0F, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("emerald_hoe"));
      r.register(new ShovelItem(MaterialRegistry.ToolMats.EMERALD, 1.5F, -3.0F, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("emerald_shovel"));
      r.register(new ArmorItem(MaterialRegistry.ArmorMats.EMERALD, EquipmentSlotType.FEET, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("emerald_boots"));
      r.register(new ArmorItem(MaterialRegistry.ArmorMats.EMERALD, EquipmentSlotType.HEAD, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("emerald_helmet"));
      r.register(new ArmorItem(MaterialRegistry.ArmorMats.EMERALD, EquipmentSlotType.CHEST, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("emerald_chestplate"));
      r.register(new ArmorItem(MaterialRegistry.ArmorMats.EMERALD, EquipmentSlotType.LEGS, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("emerald_leggings"));
    }
    if (ConfigManager.SANDSTONE.get()) {
      r.register(new SwordItem(MaterialRegistry.ToolMats.SANDSTONE, 3, -2.4F, (new Item.Properties()).group(MaterialRegistry.itemGroup)).setRegistryName("sandstone_sword"));
      r.register(new PickaxeItem(MaterialRegistry.ToolMats.SANDSTONE, 1, -2.8F, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("sandstone_pickaxe"));
      r.register(new AxeItem(MaterialRegistry.ToolMats.SANDSTONE, 5.0F, -3.0F, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("sandstone_axe"));
      r.register(new HoeItem(MaterialRegistry.ToolMats.SANDSTONE, 0.0F, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("sandstone_hoe"));
      r.register(new ShovelItem(MaterialRegistry.ToolMats.SANDSTONE, 1.5F, -3.0F, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("sandstone_shovel"));
    }
    if (ConfigManager.NETHERBRICK.get()) {
      r.register(new SwordItem(MaterialRegistry.ToolMats.NETHERBRICK, 3, -2.4F, (new Item.Properties()).group(MaterialRegistry.itemGroup)).setRegistryName("netherbrick_sword"));
      r.register(new PickaxeItem(MaterialRegistry.ToolMats.NETHERBRICK, 1, -2.8F, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("netherbrick_pickaxe"));
      r.register(new AxeItem(MaterialRegistry.ToolMats.NETHERBRICK, 5.0F, -3.0F, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("netherbrick_axe"));
      r.register(new HoeItem(MaterialRegistry.ToolMats.NETHERBRICK, 0.0F, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("netherbrick_hoe"));
      r.register(new ShovelItem(MaterialRegistry.ToolMats.NETHERBRICK, 1.5F, -3.0F, new Item.Properties().group(MaterialRegistry.itemGroup)).setRegistryName("netherbrick_shovel"));
    }
    if (ConfigManager.HEARTS.get()) {
      r.register(new HeartItem(new Item.Properties().group(MaterialRegistry.itemGroup).maxStackSize(16)).setRegistryName("heart"));
      r.register(new HeartToxicItem(new Item.Properties().group(MaterialRegistry.itemGroup).maxStackSize(16)).setRegistryName("heart_empty"));
    }
  }
}