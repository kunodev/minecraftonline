package minecraftonline.kmod.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler {
	//test
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) 
	{
		TileEntity te;
		try
		{
			te= world.getBlockTileEntity(x, y, z);
		}
		catch(Exception e)
		{
			te = null;
		}

		switch(ID)
		{
			case 20:
			{
				return new StatsGUI(player,world, x, y , z);
			}
			case 21:
			{
				return new HUDGUI(ModLoader.getMinecraftInstance());
			}
		}
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity te;
		try
		{
			te= world.getBlockTileEntity(x, y, z);
		}
		catch(Exception e)
		{
			te = null;
		}

		switch(ID)
		{
			case 20:
			{
				return null;
			}
		}
		
		return null;
	}

}
