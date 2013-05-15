package minecraftonline.kmod.main;

import minecraftonline.kmod.client.gui.GUIHandler;
import minecraftonline.kmod.main.proxy.ClientProxy;
import minecraftonline.kmod.main.proxy.CommonProxy;
import minecraftonline.kmod.net.*;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.src.*;

//TODO: if i ever release the mod, set the version
@Mod(modid = "KunosMod", name = "Kunos Mod", version = "Testing")
@NetworkMod(clientSideRequired = true, serverSideRequired = true,
clientPacketHandlerSpec = @SidedPacketHandler(channels = {"kMod","kMod_skills" }, packetHandler = ClientPacketHandler.class),
serverPacketHandlerSpec = @SidedPacketHandler(channels = {"kMod","kMod_skills" }, packetHandler = ServerPacketHandler.class),
connectionHandler = ServerPacketHandler.class)



public class KModMain {
	//I dont hate you...
	@SidedProxy(clientSide = "minecraftonline.kmod.main.proxy.ClientProxy",serverSide = "minecraftonline.kmod.main.proxy.CommonProxy")

	//public static ClientProxy cproxy = new ClientProxy();
	//I guess this is a singleton now...
	//@Instance
	//public static KModMain instance;
	//@Instance
	//public static KModMain instance = new KModMain();
	
	public static CommonProxy proxy = new CommonProxy();
	
	
	//private GuiHandler guiHandler = new GuiHandler();
	@Instance
	public static KModMain instance;


	@Init
	public void load(FMLInitializationEvent evt)
	{
		this.proxy.registerItemsAndEntities();
		this.proxy.registerKeyBindingHandler();
	}
	@PostInit
	public void modsLoaded(FMLPostInitializationEvent evt) 
	{		
		this.proxy.RegisterPlayerApiClasses();
	}
	
	public void foo(){
		
	}
	

}
