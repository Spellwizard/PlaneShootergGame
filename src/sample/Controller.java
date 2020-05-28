package sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.lang.Math.abs;

public class Controller {
/**
    public static void main(String[] args) {
        JFrame frame = new JFrame("Train Demo");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.add(new TrainCanvas());
        frame.setVisible(true);
    }
**/
}

class TrainCanvas extends JComponent {


    private int debugCounter = 0;

    public TrainCanvas() {

        initKeyListener();

        Thread animationThread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    repaint();
                    try {Thread.sleep(10);} catch (Exception ex) {}
                }
            }
        });

        animationThread.start();
    }

    public void initKeyListener(){

        this.addKeyListener(InputTracker);
        this.setFocusable(true);
    }


    int trainW = 80; //the size of the train width
    int trainH = 25; // the size of the train height

    //default speed of the plane
    int defaultHSpeed = 4;
    int defaultVSpeed = 4;

    //these speed values change to track the current speed of the player / plane
    int planeHorizontalSpeed = defaultHSpeed/2; //the HORIZONTAL OR THE LEFT / RIGHT MOTION OF THE CRAFT
    int planeVerticalSpeed = defaultVSpeed/2; //THE VERTICAL OR THE UP / DOWN MOTION OF THE CRAFT


    private int lastX = 0; // used to track the previous position of the x value of the plane
    private int lastY = 0;

    public void paintComponent(Graphics g) {

        Graphics2D gg = (Graphics2D) g;

        int w = getWidth(); //gets the window width
        int h = getHeight(); // gets the window height


        int x = lastX + planeHorizontalSpeed; // updates the train's x position based on the planes horizontal movement
        int y = lastY + planeVerticalSpeed;


        //Stop Horizontal exceeding of borders by moving the player to the opposing border
        if (x > w + trainW) { // resets the object position if it exceeds the right border
            x = 0+1; // move the player to the left border
        }
        else if (x < 0 -trainW-1) { // resets the object position if it exceeds the left border
            x = w-trainW;
        }

        //Stop Vertical exceeding of borders by moving the player to the opposing border

        if (y > h + trainH) { // resets the object position if it exceeds the bottom border
            y = 0;
        }
        else if (y < 0 - trainH) { // resets the object position if it exceeds the top border
            y= h-trainH;
        }

        gg.setColor(Color.BLACK);
        gg.fillRect(x, y + trainH, trainW, trainH);

        lastX = x;
        lastY = y;
    }

    KeyListener InputTracker = new KeyListener() {


        public void keyPressed(KeyEvent e) {
            //whenever a key is pressed this will run


            /**
             * 38 - up
             * 40 - down
             * 37 - left
             * 39 - right
             * */

            //if the player preses the LEFT key the player object speed will be set to negative
            if(e.getKeyCode()==37){
                // use absolute value to get a positive speed then set it to negative / left movement
                planeHorizontalSpeed = -abs(defaultHSpeed);
            }
            //if the player preses the RIGHT key the player object speed will be set to negative
            else if(e.getKeyCode()==39){
                // use absolute value to get a positive speed then set it to negative / left movement
                planeHorizontalSpeed = abs(defaultHSpeed);
            }

            //VERTICAL MOTION

            //if the player preses the UP key the player object speed will be set to negative
            else if(e.getKeyCode()==40){
                // use absolute value to get a positive speed then set it to negative / left movement
                planeVerticalSpeed = abs(defaultVSpeed);
            }
            //if the player preses the DOWN key the player object speed will be set to negative
            else if(e.getKeyCode()==38){
                // use absolute value to get a positive speed then set it to negative / left movement
                planeVerticalSpeed = -abs(defaultVSpeed);
            }

            //'P' key
            //stop player motion
            else if(e.getKeyCode() ==19){
                planeVerticalSpeed = 0;
                planeHorizontalSpeed = 0;
            }
        }

        //these two are needed but will be unused
        public void keyTyped(KeyEvent e){}

        public void keyReleased(KeyEvent e) {
            //whenever a movment key is released the plane will stop moving in that direction

            if(e.getKeyCode()==37){ //LEFT
                planeHorizontalSpeed = 0;
            }
            else if(e.getKeyCode()==39){ //RIGHT
                planeHorizontalSpeed = 0;
            }

            //VERTICAL

             if(e.getKeyCode()==40){ //DOWN
                planeVerticalSpeed = 0;}

            else if(e.getKeyCode()==38){ //UP
                planeVerticalSpeed = 0;
            }


        }

    };


}

