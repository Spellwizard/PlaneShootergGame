import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

public class AI {
    /**
     * this is used to calculate what an AI should do given data
     */

    private ArrayList<Food> foodList; //this is the complete list of food on the table

    private ArrayList<Frog> competitors;//this is the complete list of the other ai on the board

    private boolean isDebug;//used to toggle if printing to console

    public AI(ArrayList<Food> foodList, ArrayList<Frog> competitors, boolean isDebug) {
        this.foodList = foodList;
        this.competitors = competitors;
        this.isDebug = isDebug;
    }

    /**
     * @return
     */

    public Food nearestFood(
            Frog john){

        int sighty = john.getSight();

        Food output = null;

        if(foodList==null)return null;

        /**
         * start at the largest sight field and check for collisions, if at any point no collisions with food then break
         */
        for (int i = sighty; i > 0; i--) {

            //calculate the simulated size of sight form the frog's current x,y and size
            int sightX = john.getPosX() - sighty;
            int sightY = john.getPosY() - sighty;
            int sightWidth = john.getObjWidth() + (sighty * 2);
            int sightHeight = john.getObjHeight() + (sighty * 2);

            //now using these coordinates find a collision

            for (Food yum : foodList) {
                if (yum.isCollision(sightX, sightY,
                        sightWidth, sightHeight)) {
                    output = yum;
                }
                else{
                    break;
                }
            }

        }
        return output;
    }

    /**
     * Nearestfood when given a plane will use the arraylist foodList to find the nearest food relative to the plane / self and return it
     *
     * Version 2.0
     * @return
     */

    public Food nearestFoodV2(
            Frog john){

        if(foodList==null)return null; //always do a safety check.. ;)

        Food output = null;


        /**
         * start at the largest sight field and check for collisions, if at any point no collisions with food then break
         */
        for (int i = john.getSight(); i > 0; i--) {

            //now using these coordinates find a collision

            /**
             * simulate a larger rectangle using smaller functions
             * getSightPosX, getSightPosY, getSightWidth, getSightHeight
             * and work through each food in the food list and determine if any on the list 'collide' with this sight line
             *
             * if multiple pellets do collide then it will save as the output the one lowest matching food pellet on the foodlist arraylist
             */

            for (Food yum : foodList) {
                if (yum.isCollision(
                        getSightPosX(john)
                        , getSightPosY(john),
                        getSightWidth(john),
                        getSightHeight(john)
                )) {
                    //once found a 'colliding pellet' then save the pellet as the output
                    output = yum;
                }
            }
        }
        return output;
    }

    /**
     *
     * @param john Frog / Frog then use it's sight to calculate the X coordinate for the sight rectangle where it can see
     * @return
     */
    public int getSightPosX(Frog john){
        return john.getPosX() - john.getSight();
    }

    /**
     * @param john Frog / Frog then use it's sight to calculate the Y coordinate for the sight rectangle where it can see
     * @return
     */
    public int getSightPosY(Frog john){
        return john.getPosY() - john.getSight();
    }
    /**
     *
     */
    public int getSightWidth(Frog john){
        return john.getObjWidth() + (john.getSight()*2);
    }
    /**
     *
     */
    public int getSightHeight(Frog john){
        return john.getObjHeight() + (john.getSight()*2);
    }



    /**
     * ThinkActions 1.0 is supposed to use the function nearestFood to find the nearest food
     * and then move the object to that said food pellet
     * it will use the existing target if it stil exists to prevent a 'shiny object' syndrome
     * @param self
     */
    public void thinkActions(Frog self) {
        /**
         * Calculate 'self''s best course of action using various points of values
         */

        /**
         * Step 1 - Find nearest food
         */
        Food yummy = null;

        if(self.getTargetPellet()!=null){
            boolean TargetExists = false;

            yummy = self.getTargetPellet();

            //ensure that the target is still in the target list
            for(Food pellet: foodList){
                if(yummy == pellet )TargetExists=true;
            }

            if(!TargetExists)yummy=nearestFood(self);
        }

        else{
            yummy = nearestFood(self);
        }

        if (yummy != null) {

            self.setTargetPellet(yummy);

            //set the food to a different colour
            yummy.setObjColour(Color.orange);
            /**
             * we found food and we should move towards the food
             */

            /**
             * is the food above us?
             */
            if (yummy.isAbove(self)) {

                //System.out.print("Food within sight above and to the");
                self.setObjVSpeed(  //reverse the planes vertical motion
                        abs(self.getDefaultVSpeed())
                );

                //UPPER LEFT
                if (yummy.isLeft(self)) {
                    //System.out.println(" left of us");

                    self.setObjHSpeed(  //reverse the planes horizontal motion
                            abs(self.getDefaultHSpeed())
                    );
                }
                //UPPER RIGHT
                if (yummy.isRight(self)) {
                    //System.out.println(" Right of us");
                    self.setObjHSpeed(  //reverse the planes horizontal motion
                            -abs(self.getDefaultHSpeed())
                    );
                }
            }
            /**
             * we know the food is below us
             */
            else {
                //System.out.print("Food within sight below and to the");

                self.setObjVSpeed(  //reverse the planes vertical motion
                        -abs(self.getDefaultVSpeed())
                );
                //UPPER LEFT
                if (yummy.isLeft(self)) {
                    //System.out.println(" left of us");

                    self.setObjHSpeed(  //reverse the planes horizontal motion
                            abs(self.getDefaultHSpeed())
                    );
                }
                //UPPER RIGHT
                if (yummy.isRight(self)) {
                    //System.out.println(" Right of us");
                    self.setObjHSpeed(  //reverse the planes horizontal motion
                            -abs(self.getDefaultHSpeed())
                    );
                }
            }

        }}


    /**
     * This is Thinkactions 2.0
     * this is supposed to set the plane/ frog towards the targeted (nearest food)
     * the program should ignore any searching for nearer food if it already has a target
      * @param self
     */
    public void thinkActionsV2(Frog self) {
        /**
         * Calculate 'self''s best course of action using various points of values
         */

        /**
         * Step 1 - Find nearest food
         */
        Food yummy = null;

        if(self.getTargetPellet()!=null){
            boolean TargetExists = false;

            yummy = self.getTargetPellet();

            //ensure that the target is still in the target list
            for(Food pellet: foodList){
                if(yummy == pellet )TargetExists=true;
            }

            if(!TargetExists) {
                self.setObjColour(Color.orange); //set the colour of planes/ frogs that found a new object to target
                yummy = nearestFoodV2(self);
            }
        }

        else{
            yummy = nearestFood(self);
        }

        /**
         * Once a target pellet is found it must be safety checked against being null
         *
         * Then move the plane/ frog towards the target food pellet
         */

        if(
                yummy!=null
                &&
                !moveTowardsPellet(self,yummy)){

            //This if statement is two fold, firstly it sets the object towards the target pellet but in the event no
            //food pellet is found it then will run the following lines

            //In here there is no nearby food and therefore the self should make a choice on which way to go.

            //If the following lines are left empty then the plane / frog will continue in the last direction it was going
            self.setObjColour(Color.white);

        }
        else if (
                //this is to check if there is a target
                yummy!=null

        ){
            //this is run if a target exists
            self.setObjColour(Color.red);
        }
        else{
            self.setObjColour(Color.white);
        }

        }


    /**
     * Move the Frog / frog towards the pellet by detecting the two objects relative positions
     * @param self
     * @param pellet
     * @return
     */
    public boolean moveTowardsPellet(Frog self, Food pellet) {
        if (self != null && pellet!=null) {

            self.setTargetPellet(pellet);

            //set the food to a different colour
            pellet.setObjColour(Color.orange);
            /**
             * we found food and we should move towards the food
             */

            /**
             * is the food above us?
             */
            if (pellet.isAbove(self)) {

                if(isDebug)System.out.print("Food within sight above and to the");
                self.setObjVSpeed(  //reverse the planes vertical motion
                        abs(self.getDefaultVSpeed())
                );

                //UPPER LEFT
                if (pellet.isLeft(self)) {
                    if(isDebug)System.out.println(" left of us");

                    self.setObjHSpeed(  //reverse the planes horizontal motion
                            abs(self.getDefaultHSpeed())
                    );
                }
                //UPPER RIGHT
                if (pellet.isRight(self)) {
                    if(isDebug)System.out.println(" Right of us");
                    self.setObjHSpeed(  //reverse the planes horizontal motion
                            -abs(self.getDefaultHSpeed())
                    );
                }
            }
            /**
             * we know the food is below us
             */
            else {
                if(isDebug)System.out.print("Food within sight below and to the");

                //move the object down towards the food pellet
                self.setObjVSpeed(
                        -abs(self.getDefaultVSpeed())
                );
                //UPPER LEFT
                if (pellet.isLeft(self)) {
                    if(isDebug)System.out.println(" left of us");

                    self.setObjHSpeed(  //reverse the planes horizontal motion
                            abs(self.getDefaultHSpeed())
                    );
                }
                //UPPER RIGHT
                if (pellet.isRight(self)) {
                    if(isDebug)System.out.println(" Right of us");
                    self.setObjHSpeed(  //reverse the planes horizontal motion
                            -abs(self.getDefaultHSpeed())
                    );
                }
            }
            return true; // return that the pellet is not null and that the movement directional occured correctly

    }

        return false; // this will return false when the pellet / Food item was Null
        }

    /**
     * using parameters create a new version and return it of the plane/ frog class
     * @param parent
     * @return
     */
    public Frog reproduceFrog(Frog parent){

        Random ran = new Random();

        Frog output = parent;

        int changeSize = 10;//when changes occur, they will occur in this increment - or +

        //make some random changes
        //*******
        //These is where as i understand any 'AI' (linear algebra) would be calculated to better guess at more desireable numbers

        int randomNum01 = ran.nextInt(1);
        int randomNum02 = ran.nextInt(1);


            if(randomNum02==0){
                output.setSight(output.getSight() + changeSize);
            }
            else if (randomNum01==1){
                output.setSight(output.getSight() + changeSize);
            }

            if(randomNum02==0){
                output.setDefaultHSpeed(output.getDefaultHSpeed() + changeSize);
            }
            else if(randomNum02==1){
                output.setDefaultVSpeed(output.getDefaultVSpeed() + changeSize);
            }


        return output;
        }

    }
