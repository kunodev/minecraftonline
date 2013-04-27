package kmod.entity;

import java.math.*;

import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ApokalypseThread extends Thread {

	
	private double x;
	private double y;
	private double z;
	private World worldObj;
	
	public ApokalypseThread(double x, double y, double z,World worldObj){
		this.x=x;
		this.y=y;
		this.z=z;
		this.worldObj = worldObj;
	}
	
	
	@Override
	public void run() {
		int numberOfFireballs = (int) (Math.random()*1024d); 
		System.out.println("I shall spawn " + numberOfFireballs + " now!!! ");
		for(int i=0;i<numberOfFireballs;i++)
		{
	        float xDir = 0f;
	        float zDir = 0f;
	        float yDir = -10f;
	        
	        double xPosNeg = Math.random();
	        double zPosNeg = Math.random();
	        double xOffSet = Math.random()*30;
	        double zOffSet = Math.random()*30;
	        double tempX;
	        double tempZ;
	        if(xPosNeg >= 0.5){
	        	tempX = x - xOffSet;
	        }else{
	        	tempX = x + xOffSet;
	        }
	        if(zPosNeg >= 0.5){
	        	tempZ = z -zOffSet;
	        }else{
	        	tempZ = z + zOffSet;
	        }
	        
	        
			EntityLargeFireball fireball = new EntityLargeFireball(worldObj, tempX, y ,tempZ, xDir, yDir, zDir );
			
			
			worldObj.spawnEntityInWorld(fireball);
			try{
				this.sleep(250);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}

	
	

}
