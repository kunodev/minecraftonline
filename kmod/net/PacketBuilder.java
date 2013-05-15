package minecraftonline.kmod.net;

import minecraftonline.kmod.player.model.PlayerModel;

public class PacketBuilder {


	public short[] buildStatsPacket(PlayerModel stats) {
		short[] packet = stats.getStats();
		return packet;
	}
	

}
