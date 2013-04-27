package kmod.net;

import kmod.player.model.PlayerModel;

public class PacketBuilder {


	public short[] buildStatsPacket(PlayerModel stats) {
		short[] packet = stats.getStats();
		return packet;
	}
	

}
