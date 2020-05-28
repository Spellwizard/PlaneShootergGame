import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {

    //This is the file name and type eg 'name.txt'
    String fileName = "";

    //This is the character to denote comment lines in the notepad
    String commentSymbol ="*";

    //this is the character to denote the change of the name to the value eg "file name = 9" in this example the '=' is the specified character
    String equalsSymbol = "=";

    //used to store the cleaned read of the arraylist
    ArrayList<String> cleanArrayRead = new ArrayList<>();

    //this is used for conserving the commented out lines when over writing system
    ArrayList<String> rawArrayRead = new ArrayList<>();

    /**
     * Values used for reading values and to allow other programs to read these easily in other classes without context
     */
    public final int ERRORRESULT = -1;
    public final int STRINGRESULT = 0;
    public final int INTERGERRESULT = 1;
    public final int BOOLEANRESULT = 2;


    public FileReader(String fileName) {

        this.fileName = fileName;

        updateArray();
    }

    /**
     * this is used internally to help reduce work as an internal
     * array to help track the variables and cooresponding values in an easily looped way
     */
    private void updateArray(){
        cleanArrayRead = this.cleanRead();
        rawArrayRead = this.readFileArray();
    }

    /**
     * Using the FileName read the entire file
     * @return a string containing the entire file string
     */
    public String readFileString(){
        String contents = "";

        Scanner inputStream = null;

        try {

            inputStream = new Scanner(new FileInputStream(fileName));

            while(inputStream.hasNextLine())
            contents+=inputStream.nextLine();

            inputStream.close();
        }
        catch(Exception e){
        }



    return contents;
}


    /**
     * Using the FileName read the entire file
     * @return an array where each line is a line in the wordpad
     */
    public ArrayList<String> readFileArray(){

        ArrayList<String> contents = new ArrayList<>();

        Scanner inputStream = null;

        try {

            inputStream = new Scanner(new FileInputStream(fileName));

            while(inputStream.hasNextLine())
                contents.add(inputStream.nextLine());

            inputStream.close();
        }
        catch(Exception e){
        }

        rawArrayRead = contents;

        return contents;
    }

    /**
     * Using the FileName Overwrite the contents of the file using the given parameter
     * @return
     */
    public boolean addLine(String FileContents){

        boolean goodRun = false;

        PrintWriter outputStream = null;

        try {

            outputStream = new PrintWriter(new FileOutputStream(fileName,true));

            outputStream.println("\n"+FileContents);

            //safely close the accessing of the file
            outputStream.close();

            goodRun = true;
        }
        catch(Exception e){
        }

        updateArray();

        return goodRun;
}

    public boolean OverrWriteFile(ArrayList<String> arrayContents){

        String FileContents = "";

        //convert the arraylist to a string using \n to seperate each string of the array
        for(String i: arrayContents){
            FileContents+=i+"\n";
        }

        boolean goodRun = false;

        PrintWriter outputStream = null;

        try {

            outputStream = new PrintWriter(new FileOutputStream(fileName,false));

            outputStream.println(FileContents);

            //safely close the accessing of the file
            outputStream.close();

            goodRun = true;
        }
        catch(Exception e){
        }

        updateArray();

        return goodRun;
    }

    /**
     *
     * @return a 'cleaned' 2d array of the file taking off lines that are not relevant and removing spaces
     * such a 2d array will contain the name and the cooresponding value eg: name - 38 however such a value is not neccessarily a number.
     */
    public ArrayList<String> cleanRead(){

        //check the raw read of the file
        ArrayList<String> output = readFileArray();


        //loop through each line of the file and allow for lines to be removed
        for(int i = 0 ;i < output.size();i++){

            //check to see if the current iterated line is empty

            if(output.get(i).equals("") || output.get(i).equals(" ")
                    ||output.get(i).equals("\n")|| output.get(i).equals("\t")){
                output.remove(i);
                i--;
                //safety check to stop index out of bounds errors



                //safety check to stop index out of bounds errors
                if(i>output.size())break;
                if(i<0)i=0;

            }

            //check to see if the current iterated line contains any of the characters that are to indicate as not relevant

            if(output.get(i).contains(commentSymbol)){

                output.remove(i);

                i--;

                //safety check to stop index out of bounds errors
                if(i>output.size())break;
                if(i<0)i=0;

            }

            //safety check to stop index out of bounds errors
            if(i>output.size())break;
            if(i<0)i=0;
            //finally now we know that this line is a desirable line we can remove all spaces in the line

            String line = output.get(i).replace(" ","");

            line = line.replace(";","");

            char symbol = '"';

            line = line.replace(Character.toString(symbol),"");

            //then actually swap the line
            output.set(i,line);

        }

        cleanArrayRead = output;

        return output;
    }

    /**
     * limitations is that if the provided array has multiple answers then the code will provide the last applicable item value
     *
     * @param name of desired value
     * @return "" is the desired value is not found otherwise return the desired coorespdonding value.
     */
    public String findValue(String name){
        String output = "";

        //loop through the array
        for(String line: cleanArrayRead){
           //determine if the value is found
            if(line.contains(name)){
                //finally update the output to the correct value
                output = line.replaceAll(equalsSymbol, "");
                output= output.replace(name,"");
            }
        }


        return output;
    }

    /**
     * limitations is that if the provided array has multiple answers then the code will provide the last applicable item value
     *
     * @param name of desired value
     * @return "" is the desired value is not found otherwise return the desired coorespdonding value.
     */
    public String findActualValue(String name){
        String output = "";

        //loop through the array
        for(String line: cleanArrayRead){
            //determine if the value is found
            if(line.contains(name)){
                output=line;
            }
        }

        //remove all spaces
        output.replaceAll(" ","");

        int num = output.indexOf("=");

        output = output.substring(num+1);

        return output;
    }

    /**
     * Given a variable name to overwrite and  the new value
     * find and update given value
     * if the variable is found then return true
     * if line not found then return false and add the new value to the bottom of the text file
     * any errors that cause aborting returns false
     */
    public boolean overWriteLine(String VariableName, String newValue) {

        //get the current value
        String currentValue = findValue(VariableName);
        //determine that the current variable type is the same as the new value
        if(currentValue.equals("")||
                variableType(currentValue)!=
                        variableType(newValue))
            return false;

        int numLine = -1;

        //loop through the existing arraylist to find it
        for (String line : rawArrayRead) {
            //remove all spaces in the lines
            String i = line.replaceAll(" ", "");
            //remove any ';'
            line = line.replaceAll(";", "");

            //remove the quotation's char from the line
            char symbol = '"';

            line = line.replace(Character.toString(symbol), "");


            if (
                //safety checks to ignore the lines that should be ignored
                //empty lines - "" , " ", "\n", "\t"
                    !(i.equals("") || i.equals(" ")
                            || i.equals("\n") || i.equals("\t")) &&

                            !(i.contains(commentSymbol)) &&
                            //finally check to ensure that the given variable name
                            i.contains(VariableName)) {

                //finally we know that the line is found and ready to be overwritten

                numLine = rawArrayRead.indexOf(line);
            }
        }

        String addnewLine = (VariableName + equalsSymbol + newValue);

            //if the line was not found then add the variable name and the value at the bottom of the textfile
            if (numLine == -1) {

                addLine(addnewLine);
                updateArray();
                return false;
            }
            else{

                rawArrayRead.set(numLine, addnewLine);

                OverrWriteFile(rawArrayRead);
                updateArray();

                return true;
            }


    }

        /**
         *
         * @param i String value
         * @return a number based on the following table for what the variable type is
         *
         * ERRORRESULT
         * STRINGRESULT
         * INTERGERRESULT
         * BOOLEANRESULT
         */
        public int variableType (String i){

            try {
                //convert the string into a seperate string for interation and detection
                String input = i.toLowerCase();
                //remove all spaces in the string
                input.replaceAll(" ", "");

                //BOOLEAN DETECTION
                if (input.equals("true") || input.equals("false")) return BOOLEANRESULT;

                //INTERGER DETECTION
                if (convertStringToInt(i) != -1) return INTERGERRESULT;

                //all else fails it is by definition a string
                return STRINGRESULT;
            } catch (Exception e) {
                //Literally shouldn't / can't return error but sometimes people do stupid stuff
            }
            return ERRORRESULT;
        }

    /**
     * will attempt to convert a string to int but if fails will return -1
     * @param num
     * @return
     */
    private int convertStringToInt(String num){


        try{
            return (Integer.valueOf(num));
        }
        catch (Exception e){
            return -1;
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
