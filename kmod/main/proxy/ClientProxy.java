package minecraftonline.kmod.main.proxy;


import minecraftonline.kmod.client.input.*;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minecraftonline.kmod.client.gui.GUIHandler;
import minecraftonline.kmod.client.gui.HUDGUI;
import minecraftonline.kmod.client.gui.StatsGUI;
import minecraftonline.kmod.client.input.KeyBindings;
import minecraftonline.kmod.player.control.KClientPlayerBase;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.PlayerAPI;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {
	@SideOnly(Side.CLIENT)
	private GUIHandler guiHandler = new GUIHandler();
	
	
	public void init(){
		
	}
	
	public void onClientLogin()
	{
		//NetworkRegistry.instance().registerGuiHandler(this, guiHandler);
		//ModLoader.getMinecraftInstance().ingameGUI = new HUDGUI(ModLoader.getMinecraftInstance());
	}
	
	@Override
	public void RegisterPlayerApiClasses(){
		super.RegisterPlayerApiClasses();
		PlayerAPI.register("Kunos Mod Client", KClientPlayerBase.class);
	}
	
	@Override
	public void registerKeys()
	{
		KeyBindings.addKeyBinding("Key_StatsScreen", 49);
		KeyBindings.addKeyBinding("FireBall", 45);
		KeyBindings.addKeyBinding("Apokalypse", 46);
		KeyBindings.addIsRepeating(false);
		KeyBindings.addIsRepeating(false);
		KeyBindings.addIsRepeating(false);
	}
	
	@Override
	public void registerKeyBindingHandler()
	{
		registerKeys();
		KeyBindingRegistry.registerKeyBinding(new KeyBindingHandler());
	}
	
	@Override
	public void registerTranslations() 
	{
		LanguageRegistry LR = LanguageRegistry.instance();
		LR.addStringLocalization("Key_StatsScreen", "Level up Screen");
		LR.addStringLocalization("Key_ToolMode", "Cycle Tool Mode");
		LR.addStringLocalization("Key_LockTool", "Lock Tool Location");
	}

}
