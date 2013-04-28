package minecraftonline.kmod.client.gui;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import minecraftonline.kmod.net.PacketBuilder;
import minecraftonline.kmod.player.control.KClientPlayerBase;
import minecraftonline.kmod.player.control.KServerPlayerBase;
import minecraftonline.kmod.player.model.PlayerModel;
import minecraftonline.kmod.player.model.PlayerRules;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
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
import net.minecraft.src.ModLoader;
import net.minecraft.src.PlayerBase;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;

public class StatsGUI extends GuiScreen
{
	//test
    World world;
    int x;
    int z;
    EntityPlayer player;
    short[] buffer;
    PacketBuilder pbuild = new PacketBuilder();
    EntityClientPlayerMP playermp;
    KClientPlayerBase kplayer;
    PlayerModel stats;
    PlayerRules rules = new PlayerRules();
	int tempExperience = 0;
    
    /** The X size of the inventory window in pixels. */
    protected int xSize = 176;

    /** The Y size of the inventory window in pixels. */
    protected int ySize = 184;
    /**
     * Starting X position for the Gui. Inconsistent use for Gui backgrounds.
     */
    protected int guiLeft;

    /**
     * Starting Y position for the Gui. Inconsistent use for Gui backgrounds.
     */
    protected int guiTop;
    

    public StatsGUI(EntityPlayer p, World world, int i, int j, int k)
    {
        this.world = world;
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        x = i;
        z = k;
        player = p;
		buffer = new short[6];
		tempExperience = 0;
        //if(player instanceof EntityPlayerMP){
        //	EntityPlayerMP playermp = (EntityPlayerMP)player;
        //	KServerPlayerBase base = (KServerPlayerBase) ((EntityPlayerMP) player).getServerPlayerBase("kmod.player.mod.KServerPlayerBase");
        //
        //}
        if(player instanceof EntityClientPlayerMP){
        	playermp = (EntityClientPlayerMP) player;
        	//System.out.println(playermp.playerAPI);
        	kplayer = (KClientPlayerBase) playermp.getPlayerBase("Kunos Mod Client");
        	//System.out.println(kplayer);
        	stats = kplayer.getStats();
        }
//        else if(player instanceof EntityClientPlayerSP){
//        	playersp = (EntityClientPlayerSP);
//        	kplayer = KClientPlayerBase playersp.getPlayerBase("Kunos Mod Client");
//        	stats = kplayer.stats;
//        }
    }

	public void onGuiClosed()
    {
		
        super.onGuiClosed();
    }

    public void initGui()
    {
        super.initGui();
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        
        this.buttonList.add(new GuiButton(0, l+20, i1 + 118, 66, 20, "Str+"));
        this.buttonList.add(new GuiButton(1, l+20, i1 + 137, 66, 20, "Vit+"));
        this.buttonList.add(new GuiButton(2, l+20, i1 + 156, 66, 20, "Agi+"));
        this.buttonList.add(new GuiButton(3, l+85, i1 + 118, 66, 20, "Dex+"));
        this.buttonList.add(new GuiButton(4, l+85, i1 + 137, 66, 20, "Wip+"));
        this.buttonList.add(new GuiButton(5, l+85, i1 + 156, 66, 20, "Int+"));
        this.buttonList.add(new GuiButton(6, l+20, i1 + 175, 66, 20, "Done"));
        this.buttonList.add(new GuiButton(7, l+85, i1 + 175, 66, 20, "Cancel"));
    }

    @Override
    public void drawScreen(int par1, int par2, float par3)
    {
        int k = mc.renderEngine.getTexture("/minecraftonline/kmod/resources/gui_stats.png");

        int var4 = this.guiLeft;
        int var5 = this.guiTop;
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(k);
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
        drawCenteredString(fontRenderer,player.username, l+87, i1+16, 0xFFFFFF);
        drawCenteredString(fontRenderer,"Strength : "+ stats.getStat(0) + "+" + buffer[0] , l + 87, i1+26, 0x000000);	
        drawCenteredString(fontRenderer,"Vitality : "+ stats.getStat(1) + "+" + buffer[1], l + 87, i1+36, 0x000000);	
        drawCenteredString(fontRenderer,"Agility : "+ stats.getStat(2) + "+" + buffer[2] , l + 87, i1+46, 0x000000);	
        drawCenteredString(fontRenderer,"Dexterity : "+ stats.getStat(3) + "+" + buffer[3] , l + 87, i1+56, 0x000000);
        drawCenteredString(fontRenderer,"Willpower : "+ stats.getStat(4) + "+" + buffer[4], l + 87, i1+66, 0x000000);	
        drawCenteredString(fontRenderer,"Intelligence : "+ stats.getStat(5) + "+" + buffer[5], l + 87, i1+76, 0x000000);		
        //drawCenteredString(fontRenderer,"Day : " + TFC_Time.Days[TFC_Time.getDayOfWeek()], l + 87, i1+36, 0x000000);
        //int dom = TFC_Time.getDayOfMonth();
        //int month = TFC_Time.currentMonth;
        
        //if(dom == 7 && month == 4)
        //    drawCenteredString(fontRenderer,"Date : Bioxx's Birthday!, " +(1000+TFC_Time.getYear()), l + 87, i1+46, 0x000000);
        //else
        //    drawCenteredString(fontRenderer,"Date : " + dom + " " + TFC_Time.months[month] + ", " +(1000+TFC_Time.getYear()), l + 87, i1+46, 0x000000);

        //float temp = Math.round((TFC_Climate.getHeightAdjustedTemp((int) player.posX, (int) player.posY, (int) player.posZ)));

        //drawCenteredString(fontRenderer,"Temperature : " + (int)temp + "C", l + 87, i1+56, 0x000000);
        //drawCenteredString(fontRenderer,"Month : " + , l + 87, i1+36, 0x000000);


        //long h = TFC_Time.getHour();
        String hour = "";
        //if(h == 0)
        //    hour = "The Witching Hour";
        //else
        //    hour+=h;
        //drawCenteredString(fontRenderer,"Hour : " + hour, l + 87, i1+56, 0x000000);
        //drawCenteredString(fontRenderer,"EVT : " + ((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt((int) player.posX, (int) player.posZ).floatdata1, l + 87, i1+76, 0x000000);
        
        //int rain = (int) TFC_Climate.getRainfall((int) player.posX,(int) player.posY, (int) player.posZ);
        //drawCenteredString(fontRenderer,"Rain : " + rain, l + 87, i1+86, 0x000000);
        
        for (int var6 = 0; var6 < this.buttonList.size(); ++var6)
        {
            GuiButton var7 = (GuiButton)this.buttonList.get(var6);
            var7.drawButton(this.mc, par1, par2);
        }


    }
    
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
    {
        fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
    }

    protected void actionPerformed(GuiButton guibutton)
    {
    	if(guibutton.id <= 5){
    		int stat = guibutton.id;
    		buffer[stat]++;
    		int need = rules.getExperienceNeeded(stats,buffer,player);
    		if(!(need != -1 && stats.getStat(stat) + (buffer[stat]) < 127)){
    			buffer[stat]--;
    		}
    	}else if(guibutton.id==6){
    		finishPerformed();
    		player.closeScreen();
    	}else{
    		player.closeScreen();
    	}
    	
	}
    
    protected void finishPerformed(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream(12);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			for(int i=0;i<buffer.length;i++){
				outputStream.writeShort(buffer[i]);
			}
		} catch (Exception ex) {
		        ex.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "kMod";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		playermp.sendQueue.addToSendQueue(packet);
		kplayer.getRules().doIncrement(stats, buffer, player);
		kplayer.actuateStats();
    }
    
    
    
}
