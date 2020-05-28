

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This is a class to be used as a parent class for sub classes such as
 * for a 'player', 'projectile', and any other moving objects in the game
 */
public class movingObjects extends SolidObject{

    private ArrayList<movingObjects> friendly; // list of friendly objects to this object stop friendly fire




    //default speed of the plane
    private int defaultHSpeed; //horizontally
    private int defaultVSpeed; //vertically

    //the current actual value of the object speed
    int objHSpeed; //the HORIZONTAL OR THE LEFT / RIGHT MOTION OF THE CRAFT
    int objVSpeed; //THE VERTICAL OR THE UP / DOWN MOTION OF THE CRAFT

    // used to track the previous position of the x value of the plane
    private int lastX;
    private int lastY;

    //This object's image as it moves in different directions
    private BufferedImage R_Image = null;
    private BufferedImage L_Image = null;
    private BufferedImage Up_Image = null;
    private BufferedImage Down_Image = null;

    public int getEnergy() {
        return health;
    }

    public void setEnergy(int energy) {
        this.health = energy;
    }

    private int health;

    public BufferedImage getR_Image() {
        return R_Image;
    }

    public BufferedImage getL_Image() {
        return L_Image;
    }

    public BufferedImage getUp_Image() {
        return Up_Image;
    }

    public BufferedImage getDown_Image() {
        return Down_Image;
    }





    //More logical constructor only asking for some values
    //Full constructor that on construction sets all values for the player

    /**
     *
     * @param posX the top left corner of the object's x value
     * @param posY the top left corner y value
     * @param objWidth the width of the object
     * @param objHeight the height of the object
     * @param defaultHSpeed sets the current H speed and stores the original H speed as default for calling later
     * @param defaultVSpeed sets the current V speed and stores the oringinal V speed as defualt for caling later
     */
    public movingObjects(int posX, int posY, int objWidth, int objHeight,
                         int defaultHSpeed, int defaultVSpeed) {
        super(posX, posY, objWidth, objHeight, Color.BLACK);



        this.defaultHSpeed = defaultHSpeed;
        this.defaultVSpeed = defaultVSpeed;

        this.objHSpeed = defaultHSpeed;
        this.objVSpeed = defaultVSpeed;

    }

    //Lazy constructor with some default values
    public movingObjects(int posX, int posY) {
        super(posX, posY, 50, 50, Color.BLACK);

        this.defaultHSpeed = 0;
        this.defaultVSpeed = 0;

        this.objHSpeed = 0;
        this.objVSpeed = 0;
        this.lastX = 0;
        this.lastY = 0;
    }

    //empty constructor
    public movingObjects(){

    }



    //based on current values of movement calculate the new values for the x,y
    //and save the old x, y to the lastX, lastY respecitvely
    public void calcMovement(){
        //save the x & y values in the lastX, last Y value spots
        lastX = super.getPosX();
        lastY = super.getPosY();

        //finally update the values of the x & y using the resepctive speeds in those directions
        super.setPosX(objHSpeed +super.getPosX());
        super.setPosY(objVSpeed + super.getPosY());
    }


    /**
     *  This function given alpha will save the current movement to the default H and V and then stop the H & V movement
     * @param alpha arraylist of the parent type movingObjects
     */
    public void freezeArray(ArrayList<movingObjects> alpha){
        for(movingObjects e: alpha) {
            e.setDefaultHSpeed(e.getObjHSpeed());
            e.setDefaultVSpeed(e.getObjVSpeed());
            e.setObjHSpeed(0);
            e.setObjVSpeed(0);
        }
    }



    public void drawImage(Graphics2D gg){
        gg.setColor(this.getObjColour());
        gg.fillRect(getPosX(),getPosY(),getObjWidth(),getObjHeight());
    }

    public void setR_Image(BufferedImage r_Image) {
        R_Image = r_Image;
    }

    public void setL_Image(BufferedImage l_Image) {
        L_Image = l_Image;
    }

    public void setUp_Image(BufferedImage up_Image) {
        Up_Image = up_Image;
    }

    public void setDown_Image(BufferedImage down_Image) {
        Down_Image = down_Image;
    }

    //Given a file path save the image to the respective directional movment
    public void setRightImage(String filePath) {
        {
            try {

                R_Image = ImageIO.read(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void setLeftImage(String filePath) {
        {
            try {

                L_Image = ImageIO.read(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void setUpImage(String filePath) {
        {
            try {

                Up_Image = ImageIO.read(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void setDownImage(String filePath) {
        {
            try {

                Down_Image = ImageIO.read(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //calulate given another object to see if collision has occured
    public boolean isCollision(SolidObject sam){

        //sam's lower right x, y
        int samLowY = sam.getPosY()+sam.getObjHeight();
        int samLowX = sam.getPosX()+sam.getObjWidth();

        //'this' / john's lower right x,y
        int johnLowY = this.getPosY()+this.getObjHeight();
        int johnLowX = this.getPosX()+this.getObjWidth();

        // System.out.println("SamLow: "+samLowX+", "+samLowY+" JohnLow: "+johnLowX+", "+johnLowY);


        /**
         //if the sam's lowest point is higher than 'this' highest point then no collision is detected
         if( samLowY  > this.getPosY()){

         System.out.print("Sam is lower than john\r");
         return false;
         }
         //if sam's highest point is lower than 'this' lowest point then no collision
         if(sam.getPosY() < johnLowY){
         System.out.print("Sam is higher than john\r");
         return false;
         }
         //if sams farthest point to the right is less than john's furthest left position then no collision
         if(samLowX < this.getPosX()){
         System.out.print("Sam is left of john\r");
         return false;
         }
         //idk what but this line seems to cause problems with actual positives

         //if sams furthest left position is greater than john's furtherest left position then no collision
         if(johnLowX < sam.getPosX()){
         System.out.print("Sam is right of john\r");
         return false;
         }
         */

        if(super.isAbove(sam)||super.isBelow(sam))
            return false;
        else{
            if(super.isLeft(sam)||isRight(sam))
                return false;
            else{return true;}
        }

    }

    //Basic Getter's / Setters that simply set and or get the variable


    public int getDefaultHSpeed() {
        int safe = defaultHSpeed;
        return safe;
    }

    public void setDefaultHSpeed(int defaultHSpeed) {
        this.defaultHSpeed = defaultHSpeed;
    }

    public int getDefaultVSpeed() {
        int safe = defaultVSpeed;
        return safe;
    }

    public void setDefaultVSpeed(int defaultVSpeed) {
        this.defaultVSpeed = defaultVSpeed;
    }

    public int getObjHSpeed() {
        int safe = objHSpeed;
        return safe;
    }

    public void setObjHSpeed(int objHSpeed) {
        this.objHSpeed = objHSpeed;
    }

    public int getObjVSpeed() {
        int safe = objVSpeed;
        return safe;
    }

    public void setObjVSpeed(int objVSpeed) {
        this.objVSpeed = objVSpeed;
    }

    public int getLastX() {
        return lastX;
    }

    public void setLastX(int lastX) {
        this.lastX = lastX;
    }

    public int getLastY() {
        return lastY;
    }

    public void setLastY(int lastY) {
        this.lastY = lastY;
    }



    public ArrayList<movingObjects> getFriendly() {
        return friendly;
    }

    public void setFriendly(ArrayList<movingObjects> friendly) {
        this.friendly = friendly;
    }

    /**
     *
     * @param newfriend add a movingObject as a friend to the arraylist
     */
    public void addFriendly(movingObjects newfriend){
        if(friendly == null){friendly = new ArrayList<movingObjects>();}
        this.friendly.add(newfriend);
    }

    /**
     * given a movingobject return true if it is on the friend list
     */
    public boolean isFriend(movingObjects sam){
        try{
            for(movingObjects item: friendly){
                if(item.equals(sam)) {
                    return true;

                }
            }
        }
        catch(Exception e){
            System.out.println("Error finding friends");
            return false;
        }

        return false;
    }
}
