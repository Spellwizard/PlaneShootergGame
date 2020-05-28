package sample;

import java.util.ArrayList;

public class Explosion extends movingObjects {

    int lifecycles; //the amount of time that the

    public Explosion(int posX, int posY, int objHeight, int objWidth, int defaultHSpeed, int defaultVSpeed, int lifecycles) {
        super(posX - (objWidth),
                posY - (objHeight), //the x and y use half of the size and width to allow for more central explosions

                objWidth, objHeight, defaultHSpeed, defaultVSpeed);
        this.lifecycles = lifecycles;
    }


    @Override
    public void calcMovement() {

        super.setPosX(
                (super.getPosX()   -   ( super.getObjHSpeed() /2) )
                //+(super.getPosX()/43)
        );

        super.setPosY(
                (super.getPosY()   -   ( super.getObjVSpeed() /2))
                     +   (super.getPosY()/50)
        );


        super.setObjHeight(super.getObjHeight()+super.getObjHSpeed());

        super.setObjWidth(super.getObjWidth()+super.getObjVSpeed());

        lifecycles-=10;


    }


    /**
     *  This function given alpha will save the current movement to the default H and V and then stop the H & V movement
     * @param alpha arraylist of the parent type movingObjects
     */
    public void freezeExplosionArray(ArrayList<Explosion> alpha){
        for(Explosion e: alpha) {
            e.setDefaultHSpeed(e.getObjHSpeed());
            e.setDefaultVSpeed(e.getObjVSpeed());
            e.setObjHSpeed(0);
            e.setObjVSpeed(0);
        }
    }
}
