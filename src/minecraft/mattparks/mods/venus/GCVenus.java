package mattparks.mods.venus;

import mattparks.mods.MattparksCore.ConfigManager;
import mattparks.mods.MattparksCore.MattCore;
import mattparks.mods.venus.blocks.GCVenusBlock;
import mattparks.mods.venus.blocks.GCVenusBrick;
import mattparks.mods.venus.blocks.GCVenusEgg;
import mattparks.mods.venus.blocks.GCVenusGrass;
import mattparks.mods.venus.blocks.GCVenusOre;
import mattparks.mods.venus.blocks.GCVenusVurnBerryBush;
import mattparks.mods.venus.blocks.VenusBlocks;
import mattparks.mods.venus.dimension.GCVenusTeleportType;
import mattparks.mods.venus.dimension.GCVenusWorldProvider;
import mattparks.mods.venus.entities.GCVenusEntityEvolvedBlaze;
import mattparks.mods.venus.entities.GCVenusEntityVenusianVillager;
import mattparks.mods.venus.event.GCVenusEvents;
import mattparks.mods.venus.items.GCVenusItems;
import mattparks.mods.venus.network.GCVenusPacketHandlerServer;
import mattparks.mods.venus.recipe.GCVenusRecipeManager;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.network.GCCoreConnectionHandler;
import micdoodle8.mods.galacticraft.core.network.GCCorePacketManager;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(name = GCVenus.NAME, version = MattCore.LOCALMAJVERSION + "." + MattCore.LOCALMINVERSION + "." + MattCore.LOCALBUILDVERSION, useMetadata = true, modid = GCVenus.MODID, dependencies = "required-after:" + GalacticraftCore.MODID + ";")
@NetworkMod(channels = { GCVenus.CHANNEL }, clientSideRequired = true, serverSideRequired = false, connectionHandler = GCCoreConnectionHandler.class, packetHandler = GCCorePacketManager.class)
public class GCVenus
{
	public static final String NAME = "Galacticraft Venus";
	public static final String MODID = "GCVenus";
	public static final String CHANNEL = "GCVenus";
	public static final String CHANNELENTITIES = "GCVenusEntities";

	public static final String LANGUAGE_PATH = "/assets/galacticraftvenus/lang/";

	@SidedProxy(clientSide = "mattparks.mods.venus.client.ClientProxyVenus", serverSide = "mattparks.mods.venus.CommonProxyVenus")
	public static CommonProxyVenus proxy;

    public static long tick;
    public static long slowTick;
    
	@Instance(GCVenus.MODID)
	public static GCVenus instance;

	public static CreativeTabs galacticraftVenusTab = new CreativeTabs("galacticraftVenusTab") {
		@Override
		public ItemStack getIconItemStack() {
			return new ItemStack(VenusBlocks.EvolvedBlazeEgg, 1, 0);
		}
	};

	public static final String TEXTURE_DOMAIN = "galacticraftvenus";
	public static final String TEXTURE_PREFIX = GCVenus.TEXTURE_DOMAIN + ":";

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new GCVenusEvents());
		
		GCVenusItems.initItems();
		
		VenusBlocks.initBlocks();
		VenusBlocks.registerBlocks();
		VenusBlocks.setHarvestLevels();

		GCVenus.proxy.preInit(event);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		NetworkRegistry.instance().registerChannel(new GCVenusPacketHandlerServer(), GCVenus.CHANNEL, Side.SERVER);
	}

	public void registerTileEntities()
	{
		;
	}

	public void registerCreatures()
	{
	        GCCoreUtil.registerGalacticraftCreature(GCVenusEntityVenusianVillager.class, "VenusianVillager", ConfigManager.idEntityVenusianVillager, GCCoreUtil.convertTo32BitColor(255, 103, 181, 145), 12422002);
	        GCCoreUtil.registerGalacticraftCreature(GCVenusEntityEvolvedBlaze.class, "EvolvedBlaze", ConfigManager.idEntityEvolvedBlaze, 44975, 7969893);
	}

	public void registerOtherEntities()
	{
		;
	}

	@EventHandler
	public void postLoad(FMLPostInitializationEvent event)
	{
		GCVenus.proxy.postInit(event);
		GCVenus.proxy.registerRenderInformation();
		GCVenusRecipeManager.loadRecipes();
	}

	public void registerGalacticraftCreature(Class<? extends Entity> var0, String var1, int id, int back, int fore)
	{
		EntityRegistry.registerGlobalEntityID(var0, var1, id, back, fore);
		EntityRegistry.registerModEntity(var0, var1, id, GCVenus.instance, 80, 3, true);
	}

	public void registerGalacticraftNonMobEntity(Class<? extends Entity> var0, String var1, int id, int trackingDistance, int updateFreq, boolean sendVel)
	{
		EntityList.addMapping(var0, var1, id);
		EntityRegistry.registerModEntity(var0, var1, id, this, trackingDistance, updateFreq, sendVel);
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		this.registerTileEntities();
		this.registerCreatures();
		this.registerOtherEntities();
		
		
		GCVenus.proxy.init(event);
		
        GalacticraftRegistry.registerTeleportType(GCVenusWorldProvider.class, new GCVenusTeleportType());
        GalacticraftRegistry.registerCelestialBody(new GCVenusPlanet());
        GalacticraftRegistry.registerRocketGui(GCVenusWorldProvider.class, new ResourceLocation(GCVenus.TEXTURE_DOMAIN, "textures/gui/venusRocketGui.png"));
	}
}