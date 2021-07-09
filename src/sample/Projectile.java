package sample;

import java.awt.*;
import java.util.ArrayList;

public class Projectile extends sample.movingObjects{

    //projectiles should move fast, this multiples whatever direction the player is going by this interger to set the projectile speed
    int multiplier = 2;

    public int getMultiplier() {
        return multiplier;
    }

    public boolean isExplode() {
        return isExplode;
    }

    public void setExplode(boolean explode) {
        isExplode = explode;
    }

    boolean isExplode = false;

    /**
     *
     * @param posX the top left corner of the object's x value
     * @param posY the top left corner y value
     * @param objWidth the width of the object
     * @param objHeight the height of the object
     * @param defaultHSpeed sets the current H speed and stores the original H speed as default for calling later
     * @param defaultVSpeed sets the current V speed and stores the oringinal V speed as defualt for caling later
     */

    public Projectile(int objWidth, int objHeight, int defaultHSpeed, int defaultVSpeed, int objHSpeed,
                      int objVSpeed, int lastX, int lastY, int posX, int posY) {

        super(posX,posY,objWidth, objHeight, defaultHSpeed, defaultVSpeed);


        this.setObjHSpeed(objHSpeed*multiplier);
        this.setObjVSpeed(objVSpeed*multiplier);
    }

    public Projectile(int posX, int posY, int objWidth, int objLength, int defaultHSpeed, int defaultVSpeed) {
        super(posX, posY, objWidth, objLength, defaultHSpeed, defaultVSpeed);
        super.setObjHSpeed(objHSpeed*multiplier);
        super.setObjVSpeed(objVSpeed*multiplier);
    }

    public Projectile(int posX, int posY) {
        super(posX, posY);
    }


    /**
     *  This function given alpha will save the current movement to the default H and V and then stop the H & V movement
     * @param alpha arraylist of the parent type movingObjects
     */
    public void freezeProjectileArray(ArrayList<Projectile> alpha){
        for(Projectile e: alpha) {
            e.setDefaultHSpeed(e.getObjHSpeed());
            e.setDefaultVSpeed(e.getObjVSpeed());
            e.setObjHSpeed(0);
            e.setObjVSpeed(0);
        }
    }





}
