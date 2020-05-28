
import java.awt.*;

public class Frog extends  movingObjects {


    private int energy = 100;

    private int defaultEnergy;

    private Food targetPellet;




    public Frog(int posX, int posY, int objWidth, int objHeight, int defaultHSpeed, int defaultVSpeed, int sight) {
        super(posX, posY, objWidth, objHeight, defaultHSpeed, defaultVSpeed);
        Sight = sight;
        defaultEnergy=energy;
    }

    private int Sight;

    private int foodSupply;

    private String name;

    public Frog(int posX, int posY, int width, int height, int Hspeed, int Vspeed){
        super(posX,posY, width, height, Hspeed, Vspeed);
        defaultEnergy=energy;
    }

    //given a 2d rectangle draw the object on the current value
    @Override
    public void drawobj(Graphics2D gg){
        gg.setColor(super.getObjColour());
        gg.fillRect(this.getPosX(), this.getPosY(), this.getObjWidth(), this.getObjHeight());
    }




    public void increaseFoodSupply(int additionalFood){
        foodSupply+=additionalFood;
    }

    public String getName() {
        return name;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getFoodSupply() {
        return foodSupply;
    }

    public void setFoodSupply(int foodSupply) {
        this.foodSupply = foodSupply;
    }


    public int getSight() {
        return Sight;
    }

    public void setSight(int sight) {
        Sight = sight;
    }


    public Food getTargetPellet() {
        return targetPellet;
    }

    public void setTargetPellet(Food targetPellet) {
        this.targetPellet = targetPellet;
    }

    public int getDefaultEnergy() {
        return defaultEnergy;
    }

    public void setDefaultEnergy(int defaultEnergy) {
        this.defaultEnergy = defaultEnergy;
    }
}
