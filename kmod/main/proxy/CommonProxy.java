package minecraftonline.kmod.main.proxy;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import minecraftonline.kmod.client.gui.GUIHandler;
import minecraftonline.kmod.item.ItemKunosBow;
import minecraftonline.kmod.player.control.KServerPlayerBase;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ServerPlayerAPI;

public class CommonProxy{


	private GUIHandler guiHandler = new GUIHandler();
	public static Item tutorialItem;
	
	public void RegisterPlayerApiClasses()
	{
		ServerPlayerAPI.register("Kunos Mod Server", KServerPlayerBase.class);
		//If i comment this one out, server seems to work
		//
		NetworkRegistry.instance().registerGuiHandler(this, guiHandler);
	}
	

	public void registerKeys() {
		// TODO Auto-generated method stub
		
	}

	public void registerKeyBindingHandler() {
		// TODO Auto-generated method stub
		
	}

	public void registerTranslations() {
		// TODO Auto-generated method stub
		
	}

	public void onClientLogin() {
		
	}
	
	public void registerItemsAndEntities(){
		tutorialItem = new ItemKunosBow(5000);
		ItemStack string = new ItemStack(Item.silk);
		ItemStack stick = new ItemStack(Item.stick);
		ItemStack plank = new ItemStack(Block.planks);
		GameRegistry.addRecipe(new ItemStack(tutorialItem,1), "xy ", "x z", "xy ",
		        'x', Item.silk, 'y', Item.stick, 'z', Block.planks);
		LanguageRegistry LR = LanguageRegistry.instance();
		LanguageRegistry.addName(tutorialItem, "Hunters Bow");
	}

}
