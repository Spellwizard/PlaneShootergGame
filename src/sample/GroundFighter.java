package sample;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * This is a simple object of a ground fighter that moves across the X axis on a set rate and does not move up / down
 */
public class GroundFighter extends movingObjects {
    private static int number;

    private int defaultProjectileHeight=5;
    private int getDefaultProjectileWidth=15;

    /**
     *
     * @param posX
     * @param posY
     * @param objWidth
     * @param objHeight
     * @param defaultHSpeed
     * @param defaultVSpeed
     */

    private int shootRange = 600;
    Random random = new Random();
    private int shootNumber = random.nextInt(shootRange);




    public GroundFighter(int posX, int posY, int objWidth, int objHeight, int defaultHSpeed, int defaultVSpeed) {
        super(posX, posY, objWidth, objHeight, defaultHSpeed, defaultVSpeed);
    }


    /**
     *  This function given alpha will save the current movement to the default H and V and then stop the H & V movement
     * @param alpha arraylist of the parent type movingObjects
     */
    public void freezeGroundFighterArray(ArrayList<GroundFighter> alpha){
        for(GroundFighter e: alpha) {
            e.setDefaultHSpeed(e.getObjHSpeed());
            e.setDefaultVSpeed(e.getObjVSpeed());
            e.setObjHSpeed(0);
            e.setObjVSpeed(0);
        }
    }

    public Projectile shoot(boolean isHProjectile){


        if(shootNumber<0){

            shootNumber = random.nextInt(shootRange);

            Projectile bullet = null;

            if(isHProjectile)bullet = createHProjectile();
            else{
                bullet = createProjectile();
            }

            bullet.setObjColour(getObjColour());

            bullet.setObjVSpeed(-bullet.getObjVSpeed());

            return bullet;

        }
        else{

            shootNumber--;

        }



        return null;
    }

    /**
     * Create and return a projectile only moving in the same H direction as the object firing it
     * @return
     */
    public Projectile createHProjectile(){


        int proX = getPosX()+ this.getObjWidth()/2;
        int proY = (getPosY() +(this.getObjHeight()/2));

        int speedV = 0;
        int speedH = (1+getDefaultHSpeed())*3;


        Projectile ouchy = new Projectile(
                proX,proY, defaultProjectileHeight, getDefaultProjectileWidth, speedH, speedV
        );

        ouchy.setObjColour(getObjColour());


        //fish are friends not food
        ouchy.addFriendly(this);

        this.addFriendly(ouchy);


        return ouchy;

    }

    /**
     * this is called when this player's fire button is pressed
     * @return a created projectilee that the player fired
     */
    public Projectile createProjectile(){

        int proX = getPosX()+ this.getObjWidth()/2;
        int proY = (getPosY() +(this.getObjHeight()/2));

        int speedH = 0;
        int speedV = (1+getDefaultVSpeed())*3;


        Projectile ouchy = new Projectile(
                proX,proY, defaultProjectileHeight, getDefaultProjectileWidth, speedH, speedV
        );

        ouchy.setObjColour(getObjColour());


        //fish are friends not food
        ouchy.addFriendly(this);

        this.addFriendly(ouchy);


        return ouchy;

    }

}
