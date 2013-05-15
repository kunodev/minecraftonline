package minecraftonline.kmod.player.model;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerRules {
	
	
	//foo
	//penis
	
	public int getExperienceNeeded(PlayerModel stats, short[] incr, EntityPlayer player){
		int neededExperience = 0;
		
		for(int i = 0; i< incr.length; i++){
			short currentStat = stats.getStat(i);
			for(int j=currentStat+1;j<=currentStat+incr[i];j++){
				neededExperience+= 1 << (j/BalancingConstants.levelHardship);
			}
		}
		if(player.experienceLevel<neededExperience){
			//error code
			return -1;
		}
		return neededExperience;
	}
	
	public boolean doIncrement(PlayerModel stats, short[] incr, EntityPlayer player){
		int needExp = getExperienceNeeded(stats,incr,player);
		if(needExp != -1){
			player.experienceLevel-=needExp;
			modifyStats(stats,incr);
			return true;
		} else{
			return false;
		}
		
		
	}
	
	public void modifyStats(PlayerModel stats, short[] incr){
		for(int i=0; i<incr.length;i++){
			stats.incrementStat(i, (byte)incr[i]);
		}
	}
	
	
}
