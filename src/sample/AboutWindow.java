
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.KeyListener;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class AboutWindow extends JFrame implements ActionListener {

        FileReader fRead = new FileReader("README.txt");


        KeyListener InputTracker = new KeyListener() {


            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("KeyPressed: "+e.getKeyCode());
            }
            //these two are needed but will be unused
            @Override
            public void keyTyped(KeyEvent e){}
            @Override
            public void keyReleased(KeyEvent e) {}

        };


        public AboutWindow(String windowName){

            initKeyListener();

            //make sure that on closure the options window is hidden in favour of the menu window being made visible
            //On Close of game window go back to the menu window
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.out.println("Closing OptionsWindow");

                    Menu.makeVisible();
                }
            });

            System.out.println("New Sheet made: "+windowName);

            this.setTitle(windowName);

            onlineEXadaptations();
        }


        public void initKeyListener() {

            this.addKeyListener(InputTracker);
            this.setFocusable(true);
        }

        public JTextField createText(String description, int column){

            JTextField i = new JTextField(description);

            i.setColumns(column);

            return i;
        }

        public JTextField createText(int column, int row, String description){

            JTextField i = new JTextField(description);

            i.setColumns(column);


            return i;
        }

        public void onlineEXadaptations(){

            Color backColor = Color.black;
            Color foreColor = Color.white;

            JPanel panel = new JPanel(new GridBagLayout());

            //panel.setBackground(backColor);

            GridBagConstraints cst = new GridBagConstraints();

            //set where the next component will be added on the row and column
            cst.gridx = 0; //column
            cst.gridy = 0; // row

            //add a label
            Label libel = new Label("ABOUT");
            //libel.setForeground(foreColor);
            //libel.setBackground(backColor);
            panel.add(libel, cst);

            //somehow this is important for the layout control
            cst.fill = GridBagConstraints.HORIZONTAL;



            ArrayList<String> StringRead = fRead.readFileArray();


            for(int i =0;i<StringRead.size();i++) {
                //set where the next component will be added on the row and column
                cst.gridx = 0; //column
                cst.gridy = i + 2; // row

                //add a label
                Label lineLabel = new Label(StringRead.get(i));
                //lineLabel.setForeground(foreColor);
                //lineLabel.setBackground(backColor);
                panel.add(lineLabel, cst);


                //somehow this is important for the layout control
                cst.fill = GridBagConstraints.HORIZONTAL;
            }


            //somehow this is important for the layout control
            cst.fill = GridBagConstraints.HORIZONTAL;

            panel.setVisible(true);

            this.getContentPane().add(panel);

            this.pack();
            this.setVisible(true);

            this.setSize(1000,1250);


            repaint();

        }
        /**
         * Given Parameters this will make a button and add it to the given container
         * @param posX given x position
         * @param posY given y position
         * @param width the width of the button
         * @param length the length of the button (should be called height)
         * @param name the text the button will have
         * @param actionCommand the command string code that when the button is pressed will trigger for any event listeners
         * @param colour the colour of the button
         * @param container and finally what container the button will be in
         */
        public void CreateButton(int posX, int posY, int width, int length,String name, String actionCommand, Color colour, Container container){

            Button output = new Button(name);
            //set a code to check when any buttons are clicked
            output.setActionCommand(actionCommand);
            output.addActionListener(this);
            output.setVisible(true);
            output.setBackground(colour);

            output.setBounds(posX,posY, width, length);

            container.add(output);

        }


        public void CreateLabel(int posX, int posY, int width, int length,String name, Color colour, Container container){

            Label output = new Label(name);
            //set a code to check when any buttons are clicked
            output.setVisible(true);
            output.setBackground(colour);
            output.setBounds(posX,posY, width, length);

            container.add(output);

        }

        public void paint(Graphics g){
            super.paint(g);
            g.setColor(Color.red);
            g.fillRect(10,10,10,10);
        }

        //this can be used to check if a button, check box or the like is used
        public void actionPerformed(ActionEvent ae) {
            String action = ae.getActionCommand();

            //check to see what button was pressed and execute relevant actions
            if (action.equals("BQuit")) {


            }
        }

    }
