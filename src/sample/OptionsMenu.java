

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
//This is used to track keyboard inputs
import java.awt.event.KeyListener;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class OptionsMenu extends JFrame implements ActionListener {

    FileReader fRead = new FileReader("GameSettings.txt");

    Label ButtonInputer = null;

    ArrayList<String> aNames = new ArrayList<String>();

    ArrayList<Label> optionValue = new ArrayList<>();

    ArrayList<JButton> buttonArray = new ArrayList<>();

    ArrayList<JTextField> OptionSetting = new ArrayList<>();

    KeyListener InputTracker = new KeyListener() {


        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("KeyPressed: "+e.getKeyCode());
            ButtonInputer.setText("Button Pressed: "+e.getKeyCode());
        }
        //these two are needed but will be unused
        @Override
        public void keyTyped(KeyEvent e){}
        @Override
        public void keyReleased(KeyEvent e) {}

    };


    public OptionsMenu(String windowName){

        initKeyListener();

        aNames.add("isdebug");
        aNames.add("frogEnergy");
        aNames.add("frogWidth");
        aNames.add("frogHeight");
        aNames.add("blueJetFilePath");
        aNames.add("blueJetFlipPath");
        aNames.add("frogdefaultHSpeed");
        aNames.add("frogdefaultVSpeed");
        aNames.add("booleangamePaused");
        aNames.add("Keycmd_PauseGame");
        aNames.add("Keycmd_StepRound");
        aNames.add("Keycmd_DecreaseSpeed");
        aNames.add("Keycmd_IncreaseSpeed");
        aNames.add("pelletCount");
        aNames.add("initPopulationSize");
        aNames.add("Keycmd_repopulateFood");
        aNames.add("frogSight");
        aNames.add("graphicsOn");
        aNames.add("Keycmd_ToggleGraphics");
        aNames.add("roundDuration");


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


    public void initKeyListener(){
        this.addKeyListener(InputTracker);
        this.setFocusable(true);
    }

    /**
     *
     * @param name Text of the JLabel
     * @param x Pos on the x axis
     * @param y Pos on the y axis
     * @return a jlabel with the given values
     */
    public JLabel createLabel(String name, int x, int y){
        //create a J Label with the string name as given
        JLabel i = new JLabel(name);
        //set the position of the label as given x,y
        //i.setBounds(x,y);
        //i.setSize(width,height);
        i.setVisible(true);
        i.validate();
        //System.out.println("Built Label: "+name+", "+i.validate(););
        return i;
    }

    public JLabel createLabel(String name, int column){
        //create a J Label with the string name as given
        JLabel i = new JLabel(name, column);
        i.setVisible(true);
        i.validate();
        return i;
    }

    /**
     *
     * @param name Text of the JLabel
     * @param x Pos on the x axis
     * @param y Pos on the y axis
     * @return a JButton with the given values
     */
    public JButton createButton(String name, int x, int y, int width, int height, String command){
        //create a J Label with the string name as given
        JButton i = new JButton(name);
        //set the position of the label as given x,y
        i.setBounds(x,y,width,height);
        i.setActionCommand(command);
        i.addActionListener(this);
        i.setSize(width,height);
        i.setVisible(true);
        i.validate();
        //System.out.println("Built Label: "+name+", "+i.validate(););
        return i;
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

    public void onlineExample(){
       // JFrame f = new JFrame("Text Field Examples");

        JFrame f = this;

        f.getContentPane().setLayout(new FlowLayout());

        f.getContentPane().add(createText("Text field 1",10));
        f.getContentPane().add(createText("Text field 2",10));
        f.getContentPane().add(createText("Text field 3",10));

        f.pack();
        f.setVisible(true);
    }


    public void onlineEXadaptations(){

        Font fFont = new Font(Font.SERIF, Font.PLAIN,  25);


        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints cst = new GridBagConstraints();


        //Add the title to the program options

        //set where the next component will be added on the row and column
        cst.gridx = 0; //column
        cst.gridy = 0; // row

        //add a label
        Label titleLabel = new Label("PROGRAM OPTIONS");
        titleLabel.setBackground(Color.blue);
        titleLabel.setForeground(Color.white);
        titleLabel.setFont(fFont);
        panel.add(titleLabel, cst);

        //somehow this is important for the layout control
        cst.fill = GridBagConstraints.HORIZONTAL;

        Color backColor = Color.lightGray;
        Color textColor = Color.white;

        int rowHeight = 50;
        int columnWidth = 300;



        for(int i =0;i<aNames.size();i++) {

            if(i%2!=0){
                backColor=Color.lightGray;
                textColor = Color.black;
            }
            else{
                backColor = Color.darkGray;
                textColor = Color.white;
            }

            //try to find the string value from the saved options items

            String value = fRead.findActualValue(aNames.get(i));

            //set where the next component will be added on the row and column
            cst.gridx = 0; //column
            cst.gridy = i+1; // row

            //create a label
            Label libel = new Label(aNames.get(i));
            libel.setBackground(backColor);
            libel.setForeground(textColor);
            libel.setPreferredSize(new Dimension(columnWidth,rowHeight));
            libel.setFont(fFont);
            optionValue.add(libel);
            panel.add(libel, cst);

            //somehow this is important for the layout control
            cst.fill = GridBagConstraints.HORIZONTAL;

            //set where the next component will be added on the row and column
            cst.gridx = 1; //column
            cst.gridy = i+1; // row

            System.out.println(fRead.findValue(value));


            //This is an attempt to have radio buttons but i kinda gave up b/c it was really easy to do so
            if(false&&fRead.variableType(fRead.findActualValue(value)) == fRead.BOOLEANRESULT) {
                //
                JRadioButton boolTrue = new JRadioButton("True");
                JRadioButton boolFalse = new JRadioButton("False");

                panel.add(boolTrue);

                cst.gridx = 2; //column
                cst.gridy = i+1; // row

                panel.add(boolFalse);

            }
            else{
                //add a text field and populate with the value
                JTextField textbox = new JTextField(value);
                textbox.setBackground(backColor);
                textbox.setForeground(textColor);
                textbox.setPreferredSize(new Dimension(columnWidth, rowHeight));
                textbox.setFont(fFont);
                textbox.addKeyListener(InputTracker);
                OptionSetting.add(textbox);
                panel.add(textbox, cst);
            }




            //somehow this is important for the layout control
            cst.fill = GridBagConstraints.HORIZONTAL;

        }

        //add the save button to override the given values into the text file
        //set where the next component will be added on the row and column
        cst.gridx = 0; //column
        cst.gridy = aNames.size()+2; // row

        //add a save button that will update the new values into the text file
        ButtonInputer = new Label("Button Pressed: ");

        ButtonInputer.setBackground(new Color(40,40,40));
        ButtonInputer.setForeground(Color.white);
        ButtonInputer.setFont(fFont);
        panel.add(ButtonInputer, cst);

        //somehow this is important for the layout control
        cst.fill = GridBagConstraints.HORIZONTAL;

        //add the save button to override the given values into the text file
        //set where the next component will be added on the row and column
        cst.gridx = 1; //column
        cst.gridy = aNames.size()+2; // row

        //add a save button that will update the new values into the text file
        JButton BSave = new JButton("Save Changes");
        BSave.setActionCommand("BSave");
        BSave.addActionListener(this);

        BSave.setBackground(new Color(50,255,50));
        BSave.setFont(fFont);

        panel.add(BSave, cst);

        //somehow this is important for the layout control
        cst.fill = GridBagConstraints.HORIZONTAL;

        panel.addKeyListener(InputTracker);

        panel.setVisible(true);

        panel.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));

        panel.setBackground(new Color(30,30,175));

        this.getContentPane().add(panel);

        this.getContentPane().setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));

        this.pack();
        this.setVisible(true);

        this.setSize(600,800);


        repaint();

    }

    /**
     * I want to be able to give an arraylist of string names of variable names
     * for each string variable name, I want it to check for a valid copy in the options text box
     * and if it can find it then to make a jlabel to describe the name and orginal value and a text box with the new value
     */

    public void proofOfConceptInitialization(){
        FileReader fRead = new FileReader("GameSettings.txt");

        ArrayList<String> fileString = fRead.cleanRead();

        //Make the panel that contains the info on the screen
        Panel oPanel = new Panel();

        //oPanel.setLayout(new BoxLayout(oPanel, BoxLayout.Y_AXIS));
        oPanel.setLayout(null);
        //The Y position of the current iterated item
        int RowY = 50;

        //Used as the height of each row
        int RowHeight = 50;
        //Used as the width of each row
        int RowWidth = getWidth();

        //Overwritten as looped through variables to allow for lots of fun
        JButton iterated_Button = null;
        JLabel iterated_Label = null;
        //JPanel iterated_Panel = null;
        //subPanel.setLayout(new BoxLayout(oPanel, BoxLayout.X_AXIS));
        for(String name: aNames) {
            //Find the desired value
            String value = fRead.findValue(name);

            //iterated_Panel = new JPanel();
            //iterated_Panel.setLayout(new BoxLayout(iterated_Panel, BoxLayout.Y_AXIS));
            //iterated_Panel.setLayout(null);
            //iterated_Panel.setVisible(true);
            //Create a Button
            iterated_Button = createButton(name+" Button ", 50, RowY, RowWidth, RowHeight, name);
           // optionsButtons.add(iterated_Button);

            iterated_Label = createLabel(name+value, 50, RowY);


            iterated_Button.setVisible(true);
            oPanel.add(iterated_Button);

            iterated_Label.setVisible(true);
            oPanel.add(iterated_Label);
            oPanel.setLocation(50, RowY);

            RowY += RowHeight; // Move the Y Row value to the next position for the next row

            //iterated_Panel.setVisible(true);
        }

        oPanel.setVisible(true);
        this.add(oPanel);

        this.setSize(600, 800);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        System.out.println("WHY THIS NO WORK?");

        repaint();
    }

    public void setisVisible(boolean isVisible){
        this.setVisible(isVisible);
    }

    //used on making a new sheet class object
    private void init(){
        this.setBackground(Color.green);
        this.setSize(600,800);

        //Add a focus on the window to track for keyboard inputs
        //this.addKeyListener(InputTracker);

       //OptionsMenu();
        //ScrollMenu();

        repaint();

    }

    private void ScrollMenu(){

        testLabelList();

        Container cOptionsLabels = testLabelList();


        //**************************************//


        JPanel middlePanel = new JPanel ();

        middlePanel.setBorder ( new TitledBorder( new EtchedBorder(), "" ) );


        // create the middle panel components

        JTextArea display = new JTextArea ( 16, 58 );
        display.setEditable ( false ); // set textArea non-editable

        ArrayList<String> labeList = new ArrayList<>();

        populateArray(30, labeList);

        for(String alpha: labeList){
            display.setText(display.getText()+"\n"+alpha);
        }



        JScrollPane scroll = new JScrollPane ( display );

        scroll.add(cOptionsLabels);

        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );



        //Add Textarea in to middle panel

        middlePanel.add ( scroll );

        this.add ( middlePanel );
        //this.pack ();
        this.setLocationRelativeTo ( null );

        repaint();
    }

    private ArrayList<String> populateArray(int listSize, ArrayList<String> list){

        for(int i = 0; i <listSize;i++)
            list.add("Testing "+i);



        return list;
    }

    public Container testLabelList(){

        //used to contain any and all options names / short descriptions
        Container cOptionsLabels = new Container();

/**
        //temp to add basic items to the array to allow for testing

        populateArray(15, AoptionsList);

        //label size defaults
        int LabelXPos = 10;
        int  LabelWidth = 300;
        int LabelHeight = 25;

        Boolean isGray = false;

        int LabelYPos = 50;

        for(String a: AoptionsList) {
            if(isGray) {
                CreateLabel(LabelXPos, LabelYPos, LabelWidth, LabelHeight, a, Color.LIGHT_GRAY, cOptionsLabels);
                isGray = !isGray;
            }

            else{
                CreateLabel(LabelXPos, LabelYPos, LabelWidth, LabelHeight, a, Color.white, cOptionsLabels);

                isGray = !isGray;
            }

            LabelYPos+=LabelHeight;
        }
        cOptionsLabels.setVisible(true);
*/
        return cOptionsLabels;
    }

    //called when the mainmenu needs to be drawn
    //it should clear the window and set the mainmenu up with its buttons and items

    private void OptionsMenu(){
        System.out.println("OptionsMenu Function running");


        //temp to add basic items to the array to allow for testing




        //make a new container to have the primary buttons
        Container MenuButtonsContainer = testLabelList();


        //used to contain any and all options names / short descriptions
        //Container cOptionsLabels = testLabelList();

        //Primary buttons


        //BUTTONS

        int ButtonYPos = 450;
        int ButtonWidth = 100;
        int ButtonHeight = 50;

        int ButtonXPos = this.getWidth()-(ButtonWidth + (ButtonWidth/5));

        //QUIT BUTTON
        CreateButton(0, ButtonYPos, ButtonWidth, ButtonHeight, "Exit",
                "Bquit", Color.RED, MenuButtonsContainer);


        //SAVE BUTTON
        CreateButton(ButtonXPos, ButtonYPos, ButtonWidth, ButtonHeight, "Save",
                "Save", Color.GREEN, MenuButtonsContainer);

        //Make sure the containers will be visible
        MenuButtonsContainer.setVisible(true);
        //cOptionsLabels.setVisible(true);

        //add the containers to the frame
        this.add(MenuButtonsContainer);
       // this.add(cOptionsLabels);

        repaint();
    }

    /**
     * Given Parameters this will make a button and add it to the given container
     * @param posX -
     * @param posY-
     * @param width -
     * @param length -
     * @param name -
     * @param actionCommand -
     * @param colour -
     * @param container -
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
        if (action.equals("BSave")) {
           System.out.println("BSave");

           //cleanly read the file and get cooresponding values back
           ArrayList<String> aFile = fRead.cleanRead();

            /**
             * Loop through the aNames and values and find the cooresponding values in the textfile
             * then override the values as long as the exisiting value is of the same type ie boolean can't be a number
             */
            for( int i = 0;i< optionValue.size();i++){

                System.out.println(fRead.overWriteLine(optionValue.get(i).getText(),
                        OptionSetting.get(i).getText()));

                //update the cooresponding text box
                OptionSetting.get(i).setText(fRead.findValue(optionValue.get(i).getText()));
            }


        }
    }

}
