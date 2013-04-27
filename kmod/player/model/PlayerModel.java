package kmod.player.model;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class PlayerModel {
	private short[] stats = new short[6];
	//foo
	//penis
	public PlayerModel(){
		reset();
	}

	public void reset() {
		for(int i=0;i<6;i++){
		stats[i] = 0;
		}
	}
	
	public short getStat(int stat){
		return stats[stat];
	}
	
	public void setStat(int stat, byte val ){
		stats[stat] = val; 
	}
	
	public void incrementStat(int stat, byte val){
		stats[stat] += val;
	}

	public short[] getStats() {
		return (short[])stats;
	}
	
	public void readFromNBT(NBTTagCompound nbt)
	{
	    stats[0] = nbt.getByte("str");
	    stats[1] = nbt.getByte("vit");
	    stats[2] = nbt.getByte("agi");
	    stats[3] = nbt.getByte("dex");
	    stats[4] = nbt.getByte("wip");
	    stats[5] = nbt.getByte("int");
	}
	
	public void writeToNBT(NBTTagCompound nbt)
	{
	    nbt.setByte("str", (byte) stats[0]);
	    nbt.setByte("vit", (byte) stats[1]);
	    nbt.setByte("agi", (byte) stats[2]);
	    nbt.setByte("dex", (byte) stats[3]);
	    nbt.setByte("wip", (byte) stats[4]);
	    nbt.setByte("int", (byte) stats[5]);
	}

}
