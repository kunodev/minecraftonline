package kmod.net;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import kmod.player.control.KClientPlayerBase;
import kmod.player.control.KServerPlayerBase;
import kmod.player.model.PlayerModel;
import kmod.player.model.PlayerRules;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ClientPacketHandler implements IPacketHandler {

	
	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
			if(packet.channel.equals("kMod")){
			DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
			EntityPlayerSP sender = (EntityPlayerSP) player;
			KClientPlayerBase kplayer = (KClientPlayerBase) sender.getPlayerBase("Kunos Mod Client");
			PlayerModel newModel = new PlayerModel();
			for(int i=0;i<6;i++){
				try{
					newModel.setStat(i,(byte) data.readShort());	
				}catch(IOException e){
					e.printStackTrace();
					System.err.println("Packet read error on kunos mod");
				}
			}
	
			kplayer.setStats(newModel);
			kplayer.actuateStats();
			System.out.println("In soviet Russia Server hacks you!");
			}
			else if(packet.channel.equals("kMod_skills")){
				System.out.println("Heil dein Mana!");
				EntityPlayerSP sender = (EntityPlayerSP) player;
				KClientPlayerBase kplayer = (KClientPlayerBase) sender.getPlayerBase("Kunos Mod Client");
				kplayer.healMana();
			}
	}

}
