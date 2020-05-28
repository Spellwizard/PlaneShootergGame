

import javax.imageio.ImageIO;
import javax.swing.*;


        Container c = frame.getContentPane();
        c.setBackground(Color.blue);
        frame.setLocationRelativeTo(null);
        GameCanvas program = new GameCanvas();
        frame.add(program);
        frame.setVisible(true);
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.*;


public class Main {

    public static JFrame frame = new JFrame("Frog Version: July 31, 2019");


    public Main(){

               Container c = frame.getContentPane();
        c.setBackground(Color.blue);
         frame.setLocationRelativeTo(null);
         GameCanvas program = new GameCanvas();
        frame.add(program);
         frame.setVisible(true);

        //On Close of game window go back to the menu window
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Closing Window");

                FileReader ouputData = null;
                try {
                    ouputData = new FileReader("DataOutput.txt");
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }

                for(String line: program.getDateOutput()){
                    ouputData.addLine(line);
                    ouputData.addLine("FUnnny");
                }

                Menu.makeVisible();
            }
        });
    }
}
    class GameCanvas extends JComponent {

        public int WINDOWWIDTH = 700;
        public int WINDOWHEIGHT = 900;

        private int callsToAIClass = 0;//this is used as a counter to track the number of times that the AI has been consulted for decisions

        //General World Settings
        private boolean gamePaused = false; // this is a toggle for the 'p' button to pause all movement players and arrows at the time of creation but potentially enemies

        private boolean graphicsOn = true;

        private int initPopulationSize;//used as the beginning population of the frogs
        private int frogEnergy;

        //Frog beginning values
        private int frogWidth;
        private int frogHeight;
        private int frogdefaultHSpeed;
        private int frogdefaultVSpeed;

        private int frogSight;

        //User Input keyboard button values
        private int Keycmd_PauseGame; //pause / unpause the round
        private int Keycmd_StepRound; //allow for 1 run of the game and then pause before next round
        private int Keycmd_IncreaseSpeed;//increase round speed
        private int Keycmd_DecreaseSpeed;//decrease round speed

        private int Keycmd_ToggleGraphics;//used to toggle if the graphics are on

        private int Keycmd_repopulateFood;//resets food

        private int GameSpeed = 1;//each increase is in every loop is how many times it is calculated

        private boolean isDebug; //toggles a window of values for the program

        private int pelletCount = 1; // the number of pellets added per round

        //framecount using maths can sorta be used to get seconds / minutes ect but can be out of sync due to program / hardware lag
        private int framecount=1; //the total count of all frames for the duration of the program running

        private int roundDuration= 2;//this is in minutes
        private int roundCount = 0;//this is the current round count

        private int tempRoundCount = 0;

        private Random random = new Random(); // called in various places; mostly used to get a random nuber in a range using nextInt()

        //use a variable size player list to allow for more players later on / to allow for some to die
        private  ArrayList<Frog> frogList;

        //this is the list of the 'food' items
        private ArrayList<Food> pelletList;

        public ArrayList<String> getDateOutput() {
            return dateOutput;
        }

        public void setDateOutput(ArrayList<String> dateOutput) {
            this.dateOutput = dateOutput;
        }

        public ArrayList<String> dateOutput = new ArrayList<String>();

        //IMAGES FILE PATHS

        //The background images
        private  String backgroundFilePath;

        private BufferedImage BACKGROUNDIMAGE;

        //THE ACTUAL IMAGE OBJECT

        //THE BLUE PLAYER FILE PATH
        private BufferedImage blueJetImage;
        private BufferedImage blueFlipImage;

        private BufferedImage currentBackground;


        private void InitializeDefaultValues() {
            //THE BLUE PLAYER FILE PATH
           // blueJetFilePath = "BlueJet.png";
            //blueJetFlipPath = "BlueJetFlipped.png";

            //The background image
            backgroundFilePath = "";

            //General World Settings
            gamePaused = false; // this is a toggle for the 'p' button to pause all movement players and arrows at the time of creation but potentially enemies

            initPopulationSize = 1;
            frogEnergy = 150;

            frogWidth = 10;
            frogHeight = 10;
            frogdefaultHSpeed = 20;
            frogdefaultVSpeed = 20;

            frogSight = 100;

            //the following must have additional lines for additional built players

            //Program Command default keyboard inputs
            Keycmd_PauseGame = 80;
            Keycmd_StepRound = 75;
            Keycmd_IncreaseSpeed = 74;
            Keycmd_DecreaseSpeed = 76;

            Keycmd_repopulateFood = 0;

            isDebug = false;

            //GROUND TROOP VALUES
            //use a variable size player list to allow for more players later on / to allow for some to die
            frogList = new ArrayList<>();

            graphicsOn = true;

            int roundDuration= 2;//this is in minutes
        }

        public GameCanvas() {



            this.setSize(WINDOWWIDTH,WINDOWHEIGHT);
            this.setPreferredSize(new Dimension(WINDOWWIDTH,WINDOWHEIGHT));
            gamePaused = false;
            InitializeDefaultValues();
            firstTimeinitialization();
        }

        private void overrideGameValues(String fileName) {
            FileReader file = new FileReader(fileName);


            //if there is a new relavent parameter value then overrride otherwise move on

            String temp = "";

            //Interager parameters likely to provide errors

            //OVERRIDE PLAYER VALUES
            OverridePlayerValues(file);

            //BOOLEAN OVERRIDE VALUES

            //gamePaused
            temp = file.findValue("gamePaused");
            //check to make sure that there is a value
            if (!temp.equals("")) {

                //check to see if the value is true
                if (temp.equals("true")) {
                    gamePaused = true;
                }

                //check to see if the value is false
                if (temp.equals("false")) {
                    gamePaused = false;
                }
            }

            //isdebug
            temp = file.findValue("isdebug");
            //check to make sure that there is a value
            if (!temp.equals("")) {

                //check to see if the value is true
                if (temp.equals("true")) {
                    isDebug = true;
                }

                //check to see if the value is false
                if (temp.equals("false")) {
                    isDebug = false;
                }
            }

            temp=file.findValue("graphicsOn");;
            //check to make sure that there is a value
            if (!temp.equals("")) {

                //check to see if the value is true
                if (temp.equals("true")) {
                    graphicsOn = true;
                }

                //check to see if the value is false
                if (temp.equals("false")) {
                    graphicsOn = false;
                }
            }

            //pelletCount
            if(!file.findValue("pelletCount").equals(""))
                pelletCount = convertStringToInt(file.findValue("pelletCount"));
        }

        private void OverridePlayerValues(FileReader file) {
            //PLAYER COUNT
            String temp = "initPopulationSize";
            //confirm that the array contains the desired value and isn't an empty value
            if (
                    !file.findValue(temp).equals("")
                            &&
                            convertStringToInt(file.findValue(temp)) != -1)

                initPopulationSize = convertStringToInt(file.findValue(temp));



            //PLAYER HEALTH
            temp = "frogEnergy";
            if (!file.findValue(temp).equals("")
                    && convertStringToInt(file.findValue(temp)) != -1)
                frogEnergy = convertStringToInt(file.findValue(temp));

            //PLAYER WIDTH
            temp = "frogWidth";
            if (!file.findValue(temp).equals("")
                    && convertStringToInt(file.findValue(temp)) != -1)
                frogWidth = convertStringToInt(file.findValue(temp));


            //PLAYER HEIGHT
            temp = "frogHeight";
            if (!file.findValue(temp).equals("")
                    && convertStringToInt(file.findValue(temp)) != -1)
                frogHeight = convertStringToInt(file.findValue(temp));


            //PLAYER H SPEED
            temp = "frogdefaultHSpeed";
            if (!file.findValue(temp).equals("")
                    && convertStringToInt(file.findValue(temp)) != -1)
                frogdefaultHSpeed = convertStringToInt(file.findValue(temp));

            //PLAYER V SPEED
            temp = "frogdefaultVSpeed";
            if (!file.findValue(temp).equals("")
                    && convertStringToInt(file.findValue(temp)) != -1)
                frogdefaultVSpeed = convertStringToInt(file.findValue(temp));

            //frogSight
            temp = "frogSight";
            if (!file.findValue(temp).equals("")
                    && convertStringToInt(file.findValue(temp)) != -1)
                frogSight = convertStringToInt(file.findValue(temp));

                    //Keycmd_ToggleGraphics
                    temp = "Keycmd_ToggleGraphics";
            if (!file.findValue(temp).equals("")
                    && convertStringToInt(file.findValue(temp)) != -1)
                Keycmd_ToggleGraphics = convertStringToInt(file.findValue(temp));


            //roundDuration
            temp = "roundDuration";
            if (!file.findValue(temp).equals("")
                    && convertStringToInt(file.findValue(temp)) != -1)
                roundDuration = convertStringToInt(file.findValue(temp));

            //FIRST PLAYER KEYBOARD VALUES
            if (initPopulationSize > 0) OverrideFirstPlayerInputs(file);

        }

        private void OverrideFirstPlayerInputs(FileReader file) {
            String temp = "";

            /**
             * Override first player values safely
             *
             * int firstPlayer_buttonUp
             *  int Keycmd_StepRound
             *  int Keycmd_IncreaseSpeed
             *  int Keycmd_DecreaseSpeed
             *  int firstPlayer_buttonFire
             *  int firstPlayer_buttonBomb
             */
            //  int firstPlayer_buttonUp

            temp = "Keycmd_PauseGame";
            if (!file.findValue(temp).equals("")
                    && convertStringToInt(file.findValue(temp)) != -1)
                Keycmd_PauseGame = convertStringToInt(file.findValue(temp));

            // int Keycmd_StepRound
            temp = "Keycmd_StepRound";
            if (!file.findValue(temp).equals("")
                    && convertStringToInt(file.findValue(temp)) != -1)
                Keycmd_StepRound = convertStringToInt(file.findValue(temp));

            // int Keycmd_IncreaseSpeed
            temp = "Keycmd_IncreaseSpeed";
            if (!file.findValue(temp).equals("")
                    && convertStringToInt(file.findValue(temp)) != -1)
                Keycmd_IncreaseSpeed = convertStringToInt(file.findValue(temp));

            // int Keycmd_DecreaseSpeed
            temp = "Keycmd_DecreaseSpeed";
            if (!file.findValue(temp).equals("")
                    && convertStringToInt(file.findValue(temp)) != -1)
                Keycmd_DecreaseSpeed = convertStringToInt(file.findValue(temp));

            //Keycmd_repopulateFood
            temp = "Keycmd_repopulateFood";
            if (!file.findValue(temp).equals("")
                    && convertStringToInt(file.findValue(temp)) != -1)
                Keycmd_repopulateFood = convertStringToInt(file.findValue(temp));

        }

        /**
         * will attempt to convert a string to int but if fails will return -1
         *
         * @param num
         * @return
         */
        private int convertStringToInt(String num) {


            try {
                return (Integer.valueOf(num));
            } catch (Exception e) {
                return -1;
            }
        }

        private void intializeImages() {

            blueJetImage = null;
            blueFlipImage = null;

            BACKGROUNDIMAGE = null;
            currentBackground = null;
        }

        private BufferedImage imageGetter(String filePathName) {
            try {

                return ImageIO.read(new File(filePathName));
            } catch (IOException e) {


                System.out.println(e.toString());
            }
            return null;
        }

        private void firstTimeinitialization() {

            overrideGameValues("GameSettings.txt");

            intializeImages();

            //make sure that the window will actually listen for keyboard inputs
            initKeyListener();

            //use prebuilt values, make players and put them into the frogList arrayList
            populateFrogs();

            Thread animationThread = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        repaint();
                        try {
                            Thread.sleep(10);
                        } catch (Exception ex) {
                        }
                    }
                }
            });
            animationThread.start();
        }

        public void initKeyListener() {
            this.addKeyListener(InputTracker);
            this.setFocusable(true);
        }
        /**
         * stop all motion of the game and on second trigger reactive using default speeds
         */
        public void freezeAll() {

            //This line runs if the game is not yet paused
            if (!gamePaused) {

                for (movingObjects i : frogList) {
                    i.setObjHSpeed(0);
                    i.setObjVSpeed(0);
                }
                //Toggle this boolean to track if the game is actively paused or not
                gamePaused = !gamePaused;
            }

            //This line runs if the game is paused to unpause the game
            else {
            }
        }

        /**
         * empty and make new amounts of food in a random area as per guidlines
         */
        public void randomlyPopulateFood(){
            pelletList= new ArrayList<Food>();






            int xoffset = (frogWidth*2)
                    +
                    random.nextInt(getWidth()-frogWidth*2);


            int yoffest = (frogHeight*2)
                    +
                    random.nextInt(
                    getHeight() - (
                    frogHeight*4
                    )
            );




            Color fColor = Color.black;
            int pelletSize = 15;

            for(int i = 0; i<pelletCount;i++)//for the amount of pellet counts, add a new claim
            {
                pelletList.add(new Food(xoffset, yoffest
                        , pelletSize, pelletSize, fColor));

                 xoffset = random.nextInt(getWidth()-(frogWidth*2))+frogWidth*2;

                 yoffest = random.nextInt(getHeight()-(frogHeight*2))+frogHeight;

            }

}
        public void paintComponent(Graphics g) {



            int frameSeconds =0; //calculates the seconds per the frame count
            int frameMinutes = 0; // calculates the minutes based on the seconds which come from the frame count


            Graphics2D gg = (Graphics2D) g;

            //stop the program doing anything when the program is paused

            if(!gamePaused){

                //draw the 'field of play'
                //the field of play is where food is but also where at round end where the player's will die
                gg.setColor(Color.GREEN);

                int xoffset = frogWidth*2;

                int yoffest = frogHeight*2;
                if(graphicsOn)
                gg.fillRect(
                        xoffset,
                        yoffest,
                        getWidth()- xoffset*2,
                        getHeight()-yoffest*2


                );



                //loop the game per regular cycle timers the game speed which means that there is a functional fast forward button
                for(int i = 0;i<GameSpeed;i++){

                    framecount++;

                    frameSeconds = (framecount/ 60); //calculates the seconds per the frame count
                    frameMinutes = frameSeconds/60; // calculates the minutes based on the seconds which come from the frame count


                    //NEW ROUND

                    /**
                     * Given RoundDuration calculate when a new round occurs
                     *
                     * eg: round duration = 2 //it's in minutes
                     * everytime the frame minutes is evenly divisble into it then start a roun
                     */
                    try {

                        boolean noMovesLeft = true;
                        for(Frog frog: frogList)if(frog.getEnergy()>0)noMovesLeft=false;

                        if(roundCount<frameMinutes/roundDuration
                        //|| noMovesLeft
                        ){
                            roundCount++;

                            calcNewRound();

                        }


                                            }
                    catch(Exception e){}


            WINDOWHEIGHT = getHeight();
            WINDOWWIDTH = getWidth();

                    if(graphicsOn)gg.drawImage(currentBackground, 0, 0, WINDOWWIDTH, WINDOWHEIGHT, null);

            gg.setColor(Color.white);

            //this function along with subfunctions handles all collisions for the game
            calcCollisions();

            gg.setColor(Color.white);

        //remove dead players
            for(
                    int john = 0; john< frogList.size(); john++)
        {
            Frog a = frogList.get(john);

            if (a.getEnergy() < 0) {
                frogList.remove(a);
                john--;
                if (john < 0) break;
            }
        }

                    if(pelletList!=null&&graphicsOn) {
                        drawFood(gg, pelletList);

                    }

            //if(pelletList!=null)for(Food TT: pelletList) TT.setObjColour(Color.white);

            drawPlayers(gg,frogList,getWidth(),getHeight());

            repaint();
    }
            }
        else {
                /**
                 * game paused
                 */

                gg.setColor(Color.GREEN);

                int xoffset = frogWidth * 2;

                int yoffest = frogHeight * 2;

                if(graphicsOn)gg.fillRect(
                        xoffset,
                        yoffest,
                        getWidth() - xoffset * 2,
                        getHeight() - yoffest * 2


                );
                //Draw the food pellets
                if(pelletList!=null)for (Food yum : pelletList) {
                    gg.setColor(yum.getObjColour());
                    if(graphicsOn)gg.fillRect(yum.getPosX(), yum.getPosY(), yum.getObjWidth(), yum.getObjHeight());
                }
                //draw the ai
                for (Frog yum : frogList) {
                    gg.setColor(yum.getObjColour());
                    if(graphicsOn)gg.fillRect(yum.getPosX(), yum.getPosY(), yum.getObjWidth(), yum.getObjHeight());
                }
            }
            //calculate movement and draw the ground fighter


            //  SCOREBOARD

            gg.setColor(Color.black);

            int pos = 60;

            int posy = 35;

            if (frogList != null)
                gg.setColor(Color.white);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 25));

            //If the debug is on then use the following commands to get on screen output to help with diagnosing problem
            if (isDebug) {

                gg.setColor(Color.white);
                gg.setFont(new Font("TimesRoman", Font.PLAIN, 12));

                gg.drawString("Frame Count: " + framecount +
                                " Second count: " +  frameSeconds+
                                " Minute count: " +  frameMinutes
                                +" Game Speed: "+GameSpeed
                                +" Round: "+roundCount
                                +" tempRoundcount: "+tempRoundCount
                        +" graphicsOn: "+graphicsOn
                        , 50, 30);



                for(Frog john: frogList) {
                    gg.setColor(john.getObjColour());
                    if(john.getTargetPellet()!=null) {
                        gg.drawString("Frog Speed: H: " + john.getObjHSpeed() + " V: " + john.getObjVSpeed()
                                        + " Size: " + (john.getObjHeight() + john.getObjWidth()) / 2 +
                                        " Sight: "+john.getSight()+
                                        " Energy: " + john.getEnergy()
                                        +"\tFrog X, Y Values: (" + john.getPosX() + ", " + john.getPosY() + ") "
                                        + " Food Supply: " + john.getFoodSupply() +
                                        " Target Food: (" + john.getTargetPellet().getPosX() + ", " +
                                        john.getTargetPellet().getPosY() + ")"
                                , 50, (posy += gg.getFont().getSize() + 5));

                    }
                    else{

                        gg.drawString("\tFrog X, Y Values: (" + john.getPosX() + ", " + john.getPosY() + ") " +
                                        "Speed: H: " + john.getObjHSpeed() + " V: " + john.getObjVSpeed()
                                        + " Size: " + (john.getObjHeight() + john.getObjWidth()) / 2 +
                                        " Energy: " + john.getEnergy() + " Food Supply: " + john.getFoodSupply()
                                , 50, (posy += gg.getFont().getSize() + 5));
                    }

                }
                posy=50;

                gg.setColor(Color.black);

                ArrayList<Food> targettedFoods = null;

                //loop through each food
                if(frogList!=null)for(Frog john: frogList){
                    //ignore frogs without a target pellet

                    Food target = john.getTargetPellet();

                    if(target!=null){

                        //then compare it to the targettedFoods array to ignore duplicates
                        boolean isNewTarget = true;

                        if(targettedFoods!=null) {

                            for (Food pellet : targettedFoods) {
                                //finally check for duplicates
                                if (
                                        pellet.getPosX()==target.getPosX()&&
                                                pellet.getPosY()==target.getPosY()

                                ) {
                                    isNewTarget = false;
                                }
                            }

                            if (isNewTarget) targettedFoods.add(target);

                        }
                    }
                }

                int targetCount=0;
                if(targettedFoods!=null)targetCount=targettedFoods.size();
                if(!graphicsOn)gg.setColor(Color.white);
                if(pelletList!=null )gg.drawString("FoodCount: "+pelletList.size()
                        +" Targeted Foods: "+ targetCount

                            , getWidth()-350, (posy += gg.getFont().getSize()+5));


                if(!graphicsOn)gg.setColor(Color.white);
                gg.drawString( " GreatThoughts: "+ (callsToAIClass/framecount)
                                ,getWidth()-250,
                        (posy+=gg.getFont().getSize()+5)
                );


                if(pelletList!=null){
                    for(Food pellet: pelletList){
                        gg.drawString("Pellet: "
                                        +"Frog X, Y Values: (" + pellet.getPosX() + ", " + pellet.getPosY() + ") "
                                , getWidth()-450, (posy += gg.getFont().getSize() + 5));

                    }
                    }
                }

            }


        public void calcNewRound(){
            /**
             * This is run everyNew round
             */

            //Save all the data to the arraylist dateOutput
            dateOutput.add("New Data Set: "+dateOutput.size());//add a 'header for each new data set'
            for(Frog frogy: frogList){
                if(frogy!=null) {//always safety check ;)
                    String input =
                            frogy.getSight() + "\t" +
                                    frogy.getDefaultEnergy() + "\t"
                                    + frogy.getDefaultHSpeed() + "\t"
                                    + frogy.getDefaultVSpeed();
                    dateOutput.add(input);
                }
            }

            AI greatThought = new AI(pelletList,frogList,!isDebug);

            randomlyPopulateFood();

            ArrayList<Frog> newFrogs = new ArrayList<>();


            //Cull the population by those who didn't find any food
            //those who found more than 1 food get to live to see another day
            for(int i = frogList.size()-1;    i>=0;   i--){

                //remove frogs / planes that didn't find food
                if(frogList.get(i).getFoodSupply()<1){

                    frogList.remove(i);

                    i--;

                    if(i<0){
                        break;//safety catch to stop nullpointerexeption errors in looping
                    }

                }
                if(frogList.get(i).getFoodSupply()>1){
                    //reproduce/ make another one
                    newFrogs.add(   ( greatThought.reproduceFrog(frogList.get(i))));

                    frogList.get(i).setFoodSupply(0);//reset the food supply
                }

                for(Frog frog: frogList){
                    frog.setEnergy(frog.getDefaultEnergy());//reset frog energy
                }

            }

            //finally add any potential new frogs to the frog roster
            if(newFrogs!=null){//safety check ;)

                for(Frog frog: newFrogs){
                    if(frog!=null)frogList.add(frog);
                }

            }

            //why not do a safety check?
            for(int i = 0; i<frogList.size();i++){

                if(frogList.get(i)==null){
                    frogList.remove(i);
                    i++;
                    if(i>frogList.size())
                        break;
                }

            }
        }



        /**
         * This function calculates and handles the                    collisions for the following arrayLists:
         *
         * This function should be called before any objects are drwawn
         * Explosions: explosionList
                            * Frog: frogList
                            * GroundFighters: groundFighters
                            * Projectiles: projectileList
                            */
                    public void calcCollisions(){
                        //calcPlaneOnPlaneCollision(frogList, frogList);
                        if(pelletList!=null&& frogList!=null)calcPlaneOnFoodCollision(frogList,pelletList);
                    }

                    public void calcPlaneOnFoodCollision(ArrayList<Frog> list, ArrayList<Food> gList) {

                        //loop through list safely
                        for (int a = 0; a < list.size(); a++) {

                            //loop through gList safely to allow for deletions in looping
                            for (int b = 0; b < gList.size(); b++) {

                    //Food enlargedPellet = gList.get(b);
                    /**
                    enlargedPellet.setPosX(enlargedPellet.getPosX() -
                            (enlargedPellet.getObjWidth()/4)
                    );
                    enlargedPellet.setPosY(enlargedPellet.getPosY() -
                            (enlargedPellet.getObjHeight()/4)
                    );


                    enlargedPellet.setObjWidth(enlargedPellet.getObjWidth() -
                            ((enlargedPellet.getObjWidth()/4)
                            +enlargedPellet.getObjWidth())
                    );
                    enlargedPellet.setObjHeight(enlargedPellet.getObjHeight() -
                            ((enlargedPellet.getObjHeight()/4)
                                    +enlargedPellet.getObjHeight())
                    );
                     */


                    //use the isCollision function to find if these two objects in the two array list are colliding
                    if (
                                    list.get(a).isCollision(gList.get(b))) {

                        //Handle when a collision is found with list.get(a) & gList.get(b)

                        list.get(a).increaseFoodSupply(1);//add a food counter to the frog

                        gList.remove(b);//remove the food pellet
                        b--; //offset for the removal

                        //finally safety check to break looping if the marker has exceeded the usable positions
                        if (b < 0 || b > gList.size()) break;
                    }
                }
            }}

        public void calcPlaneOnPlaneCollision(ArrayList<Frog> list, ArrayList<Frog> gList) {

            //loop through list safely
            for (int a = 0; a < list.size(); a++) {

                //loop through gList safely to allow for deletions in looping
                for (int b = 0; b < gList.size(); b++) {

                    //use the isCollision function to find if these two objects in the two array list are colliding
                    if (
                            list.get(a)!=gList.get(b)//ensure that collision isn't detected against the same object
                            &&
                            list.get(a).isCollision(gList.get(b))) {

                        //The following lines are when there is a collision between list.get(a) & glist.get(b)

                        //have them reverse directions

                        list.get(a).setObjHSpeed(
                                -    list.get(a).getObjHSpeed()
                                );

                        gList.get(b).setObjHSpeed(
                                -    gList.get(b).getObjHSpeed()
                        );
                        /**
                        list.get(a).setObjVSpeed(
                                -    abs(list.get(a).getObjVSpeed())
                        );
                         gList.get(b).setObjVSpeed(
                         -    abs(gList.get(b).getObjVSpeed())
                         );
                         */




                    }
                }
            }}

        public void drawPlayers(Graphics2D gg, ArrayList<Frog> list, int w, int h){

            AI greatThought = new AI(pelletList, list, isDebug);

            //Draw the planes/ frog's sight rectangles
            for(Frog john: list){
                callsToAIClass++;

                gg.setColor(john.getObjColour());
                if(graphicsOn &&isDebug)gg.drawRect(greatThought.getSightPosX(john),
                        greatThought.getSightPosY(john),
                        greatThought.getSightWidth(john),
                        greatThought.getSightHeight(john)

                        );
            }

            //loop through each player and populate the player movement
            //and loop the player position to the far side if they exceed the boundries of the window
            for (Frog john: list) {

                //make the frog 'think'
                greatThought.thinkActionsV2(john);

                //calculate the player's movement and update that player's values
                if(john.getEnergy()>0){
                    john.calcMovement();//only calc movement if the plane has energy to move
                    john.setEnergy(john.getEnergy()-1);//now that the frog has moved now lower it's energy count
                }

                //then if they exceeded any border then loop them around the value
                PlaneBorderTest(w, h, john);

                //use the object's own fucntion to allow for modular design in drawing the object later on
                if(graphicsOn)john.drawobj(gg);

            }
        }

        public void drawFood(Graphics2D gg, ArrayList<Food> list){

            //loop through each player and populate the player movement
            //and loop the player position to the far side if they exceed the boundries of the window
            if(true) {

                for (Food john : list) {
                    //use the object's own fucntion to allow for modular design in drawing the object later on
                    john.drawobj(gg);
                }
            }
        }

        KeyListener InputTracker = new KeyListener() {

            public void keyPressed(KeyEvent e) {
                //whenever a key is pressed this will run

                //and the key that is pressed will trigger whatever action is associated with it

                int key = e.getKeyCode();

                //'P' key
                //stop ALL motion until the 'p' key is hit again
                //all sorts of problems with running the freeze code.... just getting mvp
                if (key == Keycmd_PauseGame) {
                    //toggle the game paused variable
                    gamePaused =!gamePaused;
                }
                else if (key == Keycmd_IncreaseSpeed) {
                    GameSpeed++;
                }
                else if (key == Keycmd_DecreaseSpeed) {

                    GameSpeed--;

                    //safety to stop the game going below
                    if(GameSpeed<1){

                        GameSpeed=1;

                    }

                    if(GameSpeed>1)gamePaused=false;
                }

                else if(key==Keycmd_repopulateFood){
                    randomlyPopulateFood();
                }
                else if(key==Keycmd_ToggleGraphics){
                    graphicsOn=!graphicsOn;
                }

            }
            public void keyTyped(KeyEvent e){}

            public void keyReleased(KeyEvent e) {
            }

        };

        //use the given player object and calculate and adjust if the player is going to exceed the borders and then move the player to the
        //oppisite side of the window

        /**
         * @param WindowHeight the height/ length of the given window
         * @param WindowWidth the width of the given window
         */
        public boolean PlaneBorderTest(int WindowWidth, int WindowHeight, Frog john){
            if(john==null)return false;
            boolean isBorderReached = false;

            //Stop Horizontal exceeding of borders by moving the player to the opposing border
            if (john.getPosX() > WindowWidth) { // resets the object position if it exceeds the right border
                int XSpeed = abs(john.getDefaultHSpeed());

                john.setObjHSpeed(-XSpeed);
                john.setPosX(
                        john.getPosX()
                        -
                                (john.getObjWidth())

                );
                isBorderReached = true;
            }
            else if (john.getPosX() < 0) { // resets the object position if it exceeds the left border
                int XSpeed = abs(john.getDefaultHSpeed());

                john.setObjHSpeed(XSpeed);
                john.setPosX(
                        john.getPosX()
                                +
                                (john.getObjWidth())
                );
                isBorderReached = true;
            }
            //if the player exceeds the top of the window then turn it back downwards
            if(topBorderTest(john)){

                int VSpeed = abs(john.getDefaultVSpeed());

                john.setObjVSpeed(VSpeed);
                john.setPosY(
                        john.getPosY()
                                +
                                (john.getObjHeight())
                );
                isBorderReached = true;
            }
            else if(
                    bottomBorderTest(john))
            {
                int VSpeed = -abs(john.getDefaultVSpeed());

                john.setObjVSpeed(VSpeed);
                john.setPosY(
                        john.getPosY()
                                -
                                (john.getObjHeight())
                );
                isBorderReached = true;
            }
            return isBorderReached;
        }

        /**
         * @param john the objct that is being tested for exceeding the border
         * @return if the given object has exceeded the top border
         */
        public boolean topBorderTest(movingObjects john){
            if(john==null)return false;
            //check if the object has exceeded the top window position
            if (john.getPosY() < 0) {

                return true;
            }

            return false;
        }

        /**
         * @param john tpyically a frog
         * @return if the given object has exceeded the bottom border
         */
        public boolean bottomBorderTest(movingObjects john){
            if(john==null)return false;
            //check if the object has exceeded the top window position
            if (john.getPosY() > this.getHeight()-john.getObjHeight()) {

                return true;
            }
            return false;
        }

        //create the desired number of players and give them values
        public void populateFrogs(){

            for(int i = 0; i<initPopulationSize;i++) {

                //add a frog

                //Randomize the X, Y values using the size of the window as values
                int xVal = random.nextInt(getWidth()) + 1;
                int yVal = random.nextInt(getHeight()) + 1;

                int speed = random.nextInt((frogdefaultHSpeed + frogdefaultVSpeed) / 2 - 1) + 1;

                frogList.add(
                        new Frog(xVal, yVal,
                                frogWidth, frogHeight,
                                speed, speed
                                , frogSight
                        ));

                frogList.get(i).setObjVSpeed(speed);
                frogList.get(i).setObjHSpeed(speed);

                frogList.get(i).setR_Image(blueJetImage);
                frogList.get(i).setL_Image(blueFlipImage);

                frogList.get(i).setEnergy(frogEnergy);

                frogList.get(i).setDefaultEnergy(frogEnergy);

                frogList.get(i).setObjColour(Color.white);
            }

        }

    }