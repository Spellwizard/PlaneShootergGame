package sample;

import java.awt.*;
import java.util.ArrayList;

public class Plane extends  movingObjects {


    //the keyboard value associated with each of the following movment / input types
    private int buttonUp; //UP
    private int buttonDown; //DOWN
    private int buttonLeft; //LEFT
    private int buttonRight; //RIGHT

    private int buttonFire; //the fire button

    //these track the players cooldown to when they can next fire
    private int fireCooldown =0;
    private int FIRECOOLDOWN = 0;

    private int buttonBomb;//either the secondary fire or the bomb button

    private int bombCooldown =0;
    private int BOMBCOOLDOWN = 0;



    private int defaultProjectileHeight=25;
    private int getDefaultProjectileWidth=15;

    private int health = 100;

    private String name;



    //Full constructor that on construction sets all values for the player
    public Plane(int playerWidth, int playerHeight, int defaultHSpeed, int defaultVSpeed, int playerHSpeed,
                 int playerVSpeed, int lastX, int lastY, int posX, int posY, int buttonUp, int buttonDown,
                 int buttonLeft, int buttonRight) {

        super(posX,posY, playerWidth, playerHeight, playerHSpeed, playerVSpeed);
        this.buttonUp = buttonUp;
        this.buttonDown = buttonDown;
        this.buttonLeft = buttonLeft;
        this.buttonRight = buttonRight;
    }

    //More logical constructor only asking for some values
    public Plane(int posX, int posY, int playerWidth, int playerHeight,
                 int defaultHSpeed, int defaultVSpeed,
                 int buttonUp, int buttonDown,
                 int buttonLeft, int buttonRight) {

        super(posX,posY, playerWidth, playerHeight, defaultHSpeed, defaultVSpeed);
        this.buttonUp = buttonUp;
        this.buttonDown = buttonDown;
        this.buttonLeft = buttonLeft;
        this.buttonRight = buttonRight;
    }

    //More logical constructor only asking for some values
    public Plane(int posX, int posY, int playerWidth, int playerHeight,
                 int defaultHSpeed, int defaultVSpeed,
                 int buttonUp, int buttonDown,
                 int buttonLeft, int buttonRight,
                 int fireCooldown, int bombCooldown
    ) {


        super(posX,posY, playerWidth, playerHeight, defaultHSpeed, defaultVSpeed);
        this.buttonUp = buttonUp;
        this.buttonDown = buttonDown;
        this.buttonLeft = buttonLeft;
        this.buttonRight = buttonRight;

        this.FIRECOOLDOWN = fireCooldown;
        this.BOMBCOOLDOWN = bombCooldown;
    }

    //Full constructor that on construction sets all values for the player

    /**
     *
     * @param posX
     * @param posY
     * @param playerWidth
     * @param playerHeight
     * @param defaultHSpeed
     * @param defaultVSpeed
     * @param buttonUp
     * @param buttonDown
     * @param buttonLeft
     * @param buttonRight
     * @param buttonFire
     * @param buttonBomb
     */
    public Plane(int posX, int posY, int playerWidth, int playerHeight,
                 int defaultHSpeed, int defaultVSpeed,
                 Color c, String name,
                 int buttonUp, int buttonDown,
                 int buttonLeft, int buttonRight,
                 int buttonFire, int buttonBomb

    ) {

        super(posX,posY, playerWidth, playerHeight, defaultHSpeed, defaultVSpeed);
        super.setObjColour(c);
        this.buttonUp = buttonUp;
        this.buttonDown = buttonDown;
        this.buttonLeft = buttonLeft;
        this.buttonRight = buttonRight;

        this.buttonFire = buttonFire;
        this.buttonBomb = buttonBomb;

        this.name = name;
    }

    //Lazy constructor with some default values
    public Plane(int posX, int posY, int buttonUp, int buttonDown,
                 int buttonLeft, int buttonRight) {

        super(50,50, 1, 1, 0, 0);

        this.buttonUp = buttonUp;
        this.buttonDown = buttonDown;
        this.buttonLeft = buttonLeft;
        this.buttonRight = buttonRight;
    }

    /**
     * this is called when this player's fire button is pressed
     * @return a created projectilee that the player fired
     */
    public Projectile createProjectile(){

        int proX = getPosX()+ this.getObjWidth()/2;
        int proY = (getPosY() +(this.getObjHeight()/2));

        int speedH = getDefaultHSpeed()*2;
        int speedV = getDefaultVSpeed()/4;



        //to ensure the projectile will always shoot in relative to the fastest speed of the player check to see what direction and set speed accordingly
        if(getObjHSpeed() < 0){
            speedH = -speedH;
        }
        if(getObjVSpeed() <0){
            speedV = -speedV;
        }

        if(getObjHSpeed() == 0){
            speedH = 0;
        }
        if(getObjVSpeed() ==0){
            speedV = 0;
        }
//System.out.println(getDefaultVSpeed()+" : "+speedV);

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
     *
     * @return
     */
    public Projectile createBomb(){

        int proX = getPosX()+ this.getObjWidth()/2;
        int proY = (getPosY() +(this.getObjHeight()/2));

        int speedH = getDefaultHSpeed()/2;
        int speedV = getDefaultVSpeed();

        //to ensure the projectile will always shoot in relative to the fastest speed of the player check to see what direction and set speed accordingly
        if(getObjHSpeed() < 0){
            speedH = -speedH;
        }

        if(getObjHSpeed() == 0){
            speedH = 0;
        }

//System.out.println(getDefaultVSpeed()+" : "+speedV);

        Projectile ouchy = new Projectile(
                proX,proY,  (getDefaultProjectileWidth*10),defaultProjectileHeight*6, speedH, speedV
        );
        ouchy.setObjColour(getObjColour());
        ouchy.addFriendly(this);
        this.addFriendly(ouchy);

        ouchy.setExplode(true);
        return ouchy;

    }



    //given a 2d rectangle draw the object on the current value
    @Override
    public void drawobj(Graphics2D gg){
        gg.fillRect(this.getPosX(), this.getPosY(), this.getObjWidth(), this.getObjHeight());
    }


    /**
     *  This function given alpha will save the current movement to the default H and V and then stop the H & V movement
     * @param alpha arraylist of the parent type movingObjects
     */
    public void freezePlayerArray(ArrayList<Plane> alpha){
        for(Plane e: alpha) {
            e.setDefaultHSpeed(e.getObjHSpeed());
            e.setDefaultVSpeed(e.getObjVSpeed());
            e.setObjHSpeed(0);
            e.setObjVSpeed(0);
        }
    }

    //This is used to move the cooldowns slowly to 0
    public void calcCooldowns(){
        if(fireCooldown>0)fireCooldown --;
        if(bombCooldown>0)bombCooldown --;
    }

    //start the fire button cooldown
    public void fireCooldownStart(){
        fireCooldown = FIRECOOLDOWN;
    }
    //start the bomb button cooldown
    public void bombCooldownStart(){
        bombCooldown = BOMBCOOLDOWN;
    }


    public int getButtonUp() {
        return buttonUp;
    }

    public void setButtonUp(int buttonUp) {
        this.buttonUp = buttonUp;
    }

    public int getButtonDown() {
        return buttonDown;
    }

    public void setButtonDown(int buttonDown) {
        this.buttonDown = buttonDown;
    }

    public int getButtonLeft() {
        return buttonLeft;
    }

    public void setButtonLeft(int buttonLeft) {
        this.buttonLeft = buttonLeft;
    }

    public int getButtonRight() {
        return buttonRight;
    }

    public void setButtonRight(int buttonRight) {
        this.buttonRight = buttonRight;
    }

    public int getButtonFire() {
        return buttonFire;
    }

    public void setButtonFire(int buttonFire) {
        this.buttonFire = buttonFire;
    }

    public int getButtonBomb() {
        return buttonBomb;
    }

    public void setButtonBomb(int buttonBomb) {
        this.buttonBomb = buttonBomb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }



    public int getFireCooldown() {
        return fireCooldown;
    }

    public void setFireCooldown(int fireCooldown) {
        this.fireCooldown = fireCooldown;
    }

    public void setFIRECOOLDOWN(int FIRECOOLDOWN) {
        this.FIRECOOLDOWN = FIRECOOLDOWN;
    }

    public int getBombCooldown() {
        return bombCooldown;
    }

    public void setBombCooldown(int bombCooldown) {
        this.bombCooldown = bombCooldown;
    }


    public void setBOMBCOOLDOWN(int BOMBCOOLDOWN) {
        this.BOMBCOOLDOWN = BOMBCOOLDOWN;
    }
}
