package kmod.player.control;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.HashMap;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

import kmod.entity.ApokalypseThread;
import kmod.player.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.item.ItemInWorldManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ServerPlayerAPI;
import net.minecraft.src.ServerPlayerBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class KServerPlayerBase extends ServerPlayerBase {
	
	private PlayerCapabilities capability;
	private PlayerModel stats = new PlayerModel();
	private PlayerRules rules = new PlayerRules();
	private float mana;

	public KServerPlayerBase(ServerPlayerAPI var1) {
		super(var1);
		this.mana = 0;
		PlayerModel stats = new PlayerModel();
	}
	
	

	public int getMaxHealth(){
		return 20+(stats.getStat(1)/BalancingConstants.maxhp);
	}
	
	public int getStartingMaxHealth(){
		return 20+(stats.getStat(1)/BalancingConstants.maxhp);
	}
	
	
	@Override
	public void heal(int var1)
    {
		int health = this.player.getHealthField();
		if (health > 0)
        {
            this.player.setHealthField(health + var1 + ((int)stats.getStat(4)/BalancingConstants.hpregen));

            if (this.player.getHealthField() > this.getMaxHealth())
            {
            	this.player.setHealthField(getMaxHealth());
            }

            this.player.hurtResistantTime = this.player.maxHurtResistantTime / 2;
        }
	}
	
	@Override
    public void afterAttackTargetEntityWithCurrentItem(Entity var1) {
		 var1.attackEntityFrom(DamageSource.causePlayerDamage(this.player), stats.getStat(0)/BalancingConstants.dmg);
	}
	//not useful to increase speed
	//@Override
	//public float getSpeedModifier(){
	//	return (float)stats.getAglity();
	//}
	@Override
	public void afterOnDeath(DamageSource var1) {
		//stats.reset();
		//actuateStats();
	}
	
	public void setStats(PlayerModel newStats) {
		this.stats = newStats;
	}
	
	
	public void actuateStats(){
		
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt){
		this.player.localWriteEntityToNBT(nbt);
		stats.writeToNBT(nbt);
		System.out.println("Writing, healing Mana");
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
		PacketDispatcher.sendPacketToPlayer(packet, (Player) player);
		this.healMana();
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt){
		this.player.localReadEntityFromNBT(nbt);
		stats.readFromNBT(nbt);
	}

	public PlayerRules getRules() {
		return this.rules;
	}

	public PlayerModel getStats() {
		return this.stats;
	}

	public int getMaxMana() {
		return (int) (5 + (stats.getStat(5)/BalancingConstants.maxMana));
	}
	
	public int getMana() {
		return (int) this.mana;
	}
	
	public void healMana() {
		if(this.mana < getMaxMana()){
			int healAmount = (int)stats.getStat(4)/BalancingConstants.manaregen;
			if(this.mana + healAmount > getMaxMana()){
				this.mana = getMaxMana();
			}
			else{
				this.mana += healAmount;
			}
		}
	}


	public void hadouken() {
		this.mana -= 5;
        Vec3 lookVec = player.getLook(5f);
        float xDir = (float)lookVec.xCoord;
        float zDir = (float)lookVec.zCoord;
        float yDir = (float)lookVec.yCoord;
		EntityLargeFireball fireball = new EntityLargeFireball(player.worldObj, player,xDir, yDir, zDir );
        fireball.posX = player.posX + xDir;
        fireball.posY = player.posY + 2;
        fireball.posZ = player.posZ + zDir;
        fireball.accelerationX = xDir * 0.1D;
        fireball.accelerationY = yDir * 0.1D;
        fireball.accelerationZ = zDir * 0.1D;        
		player.worldObj.spawnEntityInWorld(fireball);
	}



	public void todUndVernichtung() {
		this.mana -= 50;
		ApokalypseThread tod = new ApokalypseThread(player.posX,254d, player.posZ, player.worldObj);
		tod.start();
		
	}
	
}
