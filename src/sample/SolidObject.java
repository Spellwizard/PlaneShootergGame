import java.awt.*;

/**
 * Parent function for all solid objects
 */
public class SolidObject {
    private int objWidth; //the actual 'hitbox' or the width of the object in pixels
    private int objHeight; //the actual 'hitbox' or the height of the object in pixels


    //The current position of the plane
    private int posX;
    private int posY;

    private Color objColour = Color.BLACK; // the object colour


    public SolidObject(int posX, int posY, int objWidth, int objHeight, Color objColour) {
        this.objWidth = objWidth;
        this.objHeight = objHeight;
        this.posX = posX;
        this.posY = posY;
        this.objColour = objColour;
    }

    public SolidObject(){}

    //given a 2d rectangle draw the object on the current value
    public void drawobj(Graphics2D gg){
        gg.setColor(getObjColour());

        gg.fillRect(posX, posY, objWidth, objHeight);
    }


    //calulate given another object to see if collision has occured
    public boolean isCollision(int samX, int samY, int samWidth, int samHeight){

        SolidObject sam = new SolidObject(samX,samY,samWidth,samHeight,Color.black);

        //sam's lower right x, y
        int samLowY = sam.getPosY()+sam.getObjHeight();
        int samLowX = sam.getPosX()+sam.getObjWidth();

        //'this' / john's lower right x,y
        int johnLowY = this.getPosY()+this.getObjHeight();
        int johnLowX = this.getPosX()+this.getObjWidth();


        if(isAbove(sam)
                ||
                isBelow(sam))
            return false;
        else{
            if( isLeft(sam)
                    ||
                    isRight(sam))
                return false;
            else{return true;}
        }

    }

    public boolean isBelow(SolidObject sam){
        /**
         * this is true when 'this' object is entirely above sam
         */
        if((this.getPosY()+this.getObjHeight()<sam.getPosY())){
            return true;
        }
        return false;
    }
    public boolean isAbove(SolidObject sam){
        /**
         * this is true when 'this' object is entirely below sam
         */
        if(this.getPosY()>sam.getPosY()+sam.getObjHeight()){
            return true;
        }
        return false;
    }

    public boolean isRight(SolidObject sam){
        /**
         * this is true when 'this' object is entirely above sam
         */
        if((this.getPosX()+this.getObjHeight()<sam.getPosX())){
            return true;
        }
        return false;
    }
    public boolean isLeft(SolidObject sam){
        /**
         * this is true when 'this' object is entirely below sam
         */
        if(this.getPosX()>sam.getPosX()+sam.getObjWidth()){
            return true;
        }
        return false;

    }


    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }


    public Color getObjColour() {
        return objColour;
    }

    public void setObjColour(Color objColour) {
        this.objColour = objColour;
    }


    public int getObjWidth() {
        int safe = objWidth;
        return safe;
    }

    public void setObjWidth(int objWidth) {
        this.objWidth = objWidth;
    }

    public int getObjHeight() {
        return objHeight;
    }

    public void setObjHeight(int objHeight) {
        this.objHeight = objHeight;
    }


}
