package kmod.net;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import kmod.main.KModMain;
import kmod.player.control.KServerPlayerBase;
import kmod.player.model.PlayerRules;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class ServerPacketHandler implements IPacketHandler, IConnectionHandler {

	//trolololol penis penis penis
	@Override
	public void clientLoggedIn(NetHandler clientHandler,
			INetworkManager manager, Packet1Login login) 
	{
		//PlayerManagerTFC.getInstance().Players.add(new PlayerInfo(clientHandler.getPlayer().username, manager));
		KModMain.proxy.onClientLogin();
	}
	
	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player){
		if(packet.channel.equals("kMod")){
			DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
			EntityPlayerMP sender = (EntityPlayerMP) player;
			KServerPlayerBase kplayer = (KServerPlayerBase) sender.getServerPlayerBase("Kunos Mod Server");
			short[] increment = new short[6];
			for(int i=0;i<6;i++){
				try{
					increment[i] = data.readShort();
				}catch(IOException e){
					e.printStackTrace();
					System.err.println("Packet read error on kunos mod");
				}
			}
			PlayerRules rules = kplayer.getRules();
			
			if(rules.doIncrement(kplayer.getStats(), increment, sender)){
				kplayer.actuateStats();
			}else{
				System.err.println("Fuck you!");
			}
		}else if(packet.channel.equals("kMod_skills")){
			DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
			EntityPlayerMP sender = (EntityPlayerMP) player;
			KServerPlayerBase kplayer = (KServerPlayerBase) sender.getServerPlayerBase("Kunos Mod Server");
			byte usedSkill=0;
			try{
				usedSkill = data.readByte();
			} catch(IOException e){
				e.printStackTrace();
				System.err.println("Packet Skill read error in Kunos Mod");
			}
			switch(usedSkill){
			case 1:
				kplayer.hadouken();
				break;
			case 2:
				kplayer.todUndVernichtung();
				break;
			default:
				;
			}
			
			
		}
	}

	@Override
	public void playerLoggedIn(Player player, NetHandler netHandler,
			INetworkManager manager) {
		if(player instanceof EntityPlayerMP){
			EntityPlayerMP playerMP = (EntityPlayerMP) player;
			KServerPlayerBase kplayer = (KServerPlayerBase) playerMP.getServerPlayerBase("Kunos Mod Server");
			short[] stats = kplayer.getStats().getStats();
			ByteArrayOutputStream bos = new ByteArrayOutputStream(12);
			DataOutputStream outputStream = new DataOutputStream(bos);
			try {
				for(int i=0;i<stats.length;i++){
					outputStream.writeShort(stats[i]);
				}
			} catch (Exception ex) {
			        ex.printStackTrace();
			}
			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = "kMod";
			packet.data = bos.toByteArray();
			packet.length = bos.size();
			//playermp.sendQueue.addToSendQueue(packet)
			PacketDispatcher.sendPacketToPlayer(packet, player);
		}
		
	}

	@Override
	public String connectionReceived(NetLoginHandler netHandler,
			INetworkManager manager) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, String server,
			int port, INetworkManager manager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler,
			MinecraftServer server, INetworkManager manager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectionClosed(INetworkManager manager) {
		
		
	}

}
