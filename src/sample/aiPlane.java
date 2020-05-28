package sample;

import java.awt.*;

import java.util.ArrayList; //used for input

import java.util.Random;

import static java.lang.Math.abs;

public class aiPlane extends Plane {

    private int lowYline; // the lowest y range the plane should fly in
    private int highYline; // the highst y range the plane should fly

    private int lowXline; // the lowest X range the plane should fly in
    private int highXline; // the highst X range the plane should fly in

    Random random = new Random(); // used to get a random number

    private int desiredY=500;;

    private int shootNumber = random.nextInt(50);

    private int yTargetLine;

    private int change = random.nextInt(50);

    private int changeX = random.nextInt(150);

    public int getLowXline() {
        return lowXline;
    }

    public int getHighXline() {
        return highXline;
    }

    public int getDesiredY() {
        return desiredY;
    }

    public void setDesiredY(int desiredY) {
        this.desiredY = desiredY;
    }

    public int getDesiredX() {
        return desiredX;
    }

    public void setDesiredX(int desiredX) {
        this.desiredX = desiredX;
    }

    private int desiredX=random.nextInt(100);



    public aiPlane(int playerWidth, int playerHeight, int defaultHSpeed, int defaultVSpeed, int playerHSpeed, int playerVSpeed, int lastX, int lastY, int posX, int posY, int buttonUp, int buttonDown, int buttonLeft, int buttonRight) {
        super(playerWidth, playerHeight, defaultHSpeed, defaultVSpeed, playerHSpeed, playerVSpeed, lastX, lastY, posX, posY, buttonUp, buttonDown, buttonLeft, buttonRight);
    }

    public aiPlane(int posX, int posY, int playerWidth, int playerHeight, int defaultHSpeed, int defaultVSpeed, int buttonUp, int buttonDown, int buttonLeft, int buttonRight) {
        super(posX, posY, playerWidth, playerHeight, defaultHSpeed, defaultVSpeed, buttonUp, buttonDown, buttonLeft, buttonRight);
    }

    public aiPlane(int posX, int posY, int playerWidth, int playerHeight, int defaultHSpeed, int defaultVSpeed, Color c, String name, int buttonUp, int buttonDown, int buttonLeft, int buttonRight, int buttonFire, int buttonBomb) {
        super(posX, posY, playerWidth, playerHeight, defaultHSpeed, defaultVSpeed, c, name, buttonUp, buttonDown, buttonLeft, buttonRight, buttonFire, buttonBomb);
    }

    public aiPlane(int posX, int posY, int buttonUp, int buttonDown, int buttonLeft, int buttonRight) {
        super(0, 0, 0, 0, 0, 0);
    }

    @Override
    public void calcMovement() {
        super.calcMovement();


    }

    private int shootRange = 150;

    public Projectile calcFire(){
        if(shootNumber<0){

            shootNumber = random.nextInt(shootRange);
            Projectile bullet = super.createProjectile();

            bullet.setObjVSpeed(-bullet.getObjVSpeed());
            return bullet;

        }
        else{

            shootNumber--;

        }

        return null;
    }


    /**
     * This is a simple AI setup to allow for some decision making given player positions
     *
     * @param targetsList
     */
    public Projectile calcAIPriorities(ArrayList<Plane> targetsList) {

        /**
         * Actions for AI -
         *
         * Fire if the number to fire has been reached and
         *
         * move the ai left and within a given range set the ai's field in which movment is legal (eg too high / low)
*/


        calcMovement();

        int yHigh = 700;
        int yLow = 50;

        if(super.getPosY()<yLow){
            super.setObjVSpeed(super.getDefaultVSpeed());

        }
        if(super.getPosY()>yHigh){
            super.setObjVSpeed(-super.getDefaultVSpeed());
        }


        if (random.nextInt(10) % 2 == 0 && random.nextInt(100) == 50) {
            if (super.getObjHSpeed() < 10)
                super.setObjHSpeed(super.getObjHSpeed() + 1);
            super.setObjHSpeed(-super.getObjHSpeed());
        }


        System.out.print("changeY "+ change+" change X: "+changeX+"\r");

        return calcFire();
    }




    public int getLowYline() {
        return lowYline;
    }

    public void setLowYline(int lowYline) {
        this.lowYline = lowYline;
    }

    public int getHighYline() {
        return highYline;
    }

    public void setHighYline(int highYline) {
        this.highYline = highYline;
    }

    public void setLowXline(int lowXline) {
        this.lowXline = lowXline;
    }

    public void setHighXline(int highXline) {
        this.highXline = highXline;
    }
}
