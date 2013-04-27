package kmod.client.input;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.EnumSet;

import kmod.client.gui.StatsGUI;
import kmod.main.KModMain;
import kmod.player.control.KClientPlayerBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.TickType;

public class KeyBindingHandler extends KeyBindingRegistry.KeyHandler
{
	//test
	KeyBinding keyStats = new KeyBinding("Key_StatsScreen", 49);
	KeyBinding hadoken = new KeyBinding("FireBall", 45);
	KeyBinding apokalypse = new KeyBinding("Apokalypse", 46);

	private long keyTime = 0;

	public KeyBindingHandler() 
	{
		super(KeyBindings.gatherKeyBindings(), KeyBindings.gatherIsRepeating());
	}

	@Override
	public String getLabel() {
		return "Kunos Mod KeyBindingHandler";
	}
	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding bind,
			boolean tickEnd, boolean isRepeat) {


	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding bind, boolean tickEnd) {
		if(tickEnd)
		{
			EntityClientPlayerMP player = FMLClientHandler.instance().getClient().thePlayer;
			if (bind.keyDescription == keyStats.keyDescription && FMLClientHandler.instance().getClient().inGameHasFocus && FMLClientHandler.instance().getClient().currentScreen == null)
			{
				player.openGui(KModMain.instance, 20, player.worldObj, 0, 0, 0);
			}
			else if (bind.keyDescription == keyStats.keyDescription && FMLClientHandler.instance().getClient().currentScreen instanceof StatsGUI)
			{
				player.closeScreen();
			}
			else if (bind.keyDescription == hadoken.keyDescription && FMLClientHandler.instance().getClient().inGameHasFocus)
			{
				KClientPlayerBase kplayer = (KClientPlayerBase) player.getPlayerBase("Kunos Mod Client");
				if(kplayer.getMana() >= 5){
					ByteArrayOutputStream bos = new ByteArrayOutputStream(12);
					DataOutputStream outputStream = new DataOutputStream(bos);
					try {
							outputStream.writeByte(1);
					}
					catch (Exception ex) {
					        ex.printStackTrace();
					}
					Packet250CustomPayload packet = new Packet250CustomPayload();
					packet.channel = "kMod_skills";
					packet.data = bos.toByteArray();
					packet.length = bos.size();
					player.sendQueue.addToSendQueue(packet);
					kplayer.hadouken();
				}
			}
			else if (bind.keyDescription == apokalypse.keyDescription && FMLClientHandler.instance().getClient().inGameHasFocus){
				KClientPlayerBase kplayer = (KClientPlayerBase) player.getPlayerBase("Kunos Mod Client");
				if(kplayer.getMana() >= 50){
					ByteArrayOutputStream bos = new ByteArrayOutputStream(12);
					DataOutputStream outputStream = new DataOutputStream(bos);
					try {
							outputStream.writeByte(2);
					}
					catch (Exception ex) {
					        ex.printStackTrace();
					}
					Packet250CustomPayload packet = new Packet250CustomPayload();
					packet.channel = "kMod_skills";
					packet.data = bos.toByteArray();
					packet.length = bos.size();
					player.sendQueue.addToSendQueue(packet);
					kplayer.todUndVernichtung();
				}
			}
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		// TODO Auto-generated method stub
		return EnumSet.of(TickType.WORLD, TickType.WORLDLOAD, TickType.CLIENT, TickType.PLAYER);
	}
}
