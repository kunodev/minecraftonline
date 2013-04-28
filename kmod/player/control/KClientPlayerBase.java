package minecraftonline.kmod.player.control;

import minecraftonline.kmod.client.gui.HUDGUI;
import minecraftonline.kmod.main.KModMain;
import minecraftonline.kmod.player.model.BalancingConstants;
import minecraftonline.kmod.player.model.PlayerModel;
import minecraftonline.kmod.player.model.PlayerRules;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.item.ItemInWorldManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.PlayerAPI;
import net.minecraft.src.PlayerBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Session;
import net.minecraft.world.World;

public class KClientPlayerBase extends PlayerBase {

	private PlayerCapabilities capability;
	private PlayerModel stats = new PlayerModel();
	private PlayerRules  rules = new PlayerRules();
	private static final float defaultRunSpeed = 0.1F;
	private float mana;
	public KClientPlayerBase(PlayerAPI var1) {
		super(var1);
		capability = player.capabilities;
		this.mana = 0;
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void afterJump(){
		float bconst = BalancingConstants.jumpConstant;
		player.motionY+=bconst*(float)stats.getStat(2);
        float var1 = player.rotationYaw * 0.017453292F;
        if(this.player.localIsSprinting()){
        	player.motionX -= (double)(MathHelper.sin(var1) * bconst*(float)stats.getStat(2));
        	player.motionZ += (double)(MathHelper.cos(var1) * bconst*(float)stats.getStat(2));
        }
	}
	@Override
	public void afterLocalConstructing(Minecraft var1, World var2, Session var3, int var4) {
		ModLoader.getMinecraftInstance().ingameGUI = new HUDGUI(ModLoader.getMinecraftInstance());
	}

	public void setStats(PlayerModel newStats) {
		this.stats = newStats;
	}

	public PlayerRules getRules(){
		return rules;
	}

	public PlayerModel getStats() {
		return stats;
	}
	
	@Override
	public int getMaxHealth(){
		return 20+(stats.getStat(1)/BalancingConstants.maxhp);
	}
	
	public int getStartingMaxHealth(){
		return 20 +(stats.getStat(1)/BalancingConstants.maxhp);
	}
	
	
	public void actuateStats(){
		capability.setPlayerWalkSpeed((defaultRunSpeed + stats.getStat(2) * BalancingConstants.walk));
		capability.setFlySpeed(capability.getFlySpeed() + (stats.getStat(2) * BalancingConstants.walk/10));
		this.player.capabilities = capability;
	}


	public int getMaxMana() {
		return 5 + (stats.getStat(5)*2);
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
	
	public void hadouken(){
		this.mana -= 5;
	}


	public void todUndVernichtung() {
		this.mana -=50;
		
	}
	
	
	
	
	
}
