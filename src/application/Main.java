/*
 * Author: Saeed Mosaffer
 * ID No. 1202254
 * */
package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Main extends Application {

    private Button btUploadData;
    private TextArea textAreaForData;
    private TextField txtDataSize;
    private TextField txtData;
    private Button btFind;
    private Button btTryAnotherData;
    private TextArea textAreaForResult;
    private TextArea textAreaDetails;
    private Button btLIS;
    private TextArea textAreaForLIS;


    private int[] LEDs;
    private int size = 0;
    private int index = 0;
    private String strRes = "";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Longest Common Subsequence & Longest Increasing Subsequence App");

        // Create main layout
        AnchorPane root = new AnchorPane();
        root.setPrefHeight(565.0);
        root.setPrefWidth(1500.0);
        root.setStyle("-fx-background-color: #ffffff;");

        // Create VBox for controls
        HBox controlsVBox = new HBox();
        controlsVBox.setAlignment(Pos.TOP_CENTER);
        controlsVBox.setLayoutX(600.0);
        controlsVBox.setLayoutY(30.0);
        controlsVBox.setPrefHeight(338.0);
        controlsVBox.setPrefWidth(275.0);
        controlsVBox.setSpacing(30.0);
        controlsVBox.setStyle("-fx-background-color: #ffffff;");

       
        btUploadData = createButton("Upload Data");
        btFind = createButton("Find the Best Pairs");
        

        controlsVBox.getChildren().addAll(btUploadData, btFind);
        
        Label labelResults = createLabel(10.0, 77.0, 540.0, "Results");
        textAreaForResult = createTextArea(10.0, 120.0, 540.0, 350.0, "Results");

        Label labelDetails = createLabel(580.0, 77.0, 546.0, "Details");
        textAreaDetails = createTextArea(580.0, 120.0, 546.0, 350.0, "Details");
        
        Label labelLEDsPower = createLabel(1150.0, 77.0, 350.0, "LEDs & Source Power");
        textAreaForData = createTextArea(1150.0, 120.0, 350.0, 350.0, "Data");
        
        Label relation = new Label("The LCS Relation:\n\n"
        		+ "Using the dynamic programming technique, which has the O(N * M) time complexity, \r\n"
        		+ "find the longest common subsequence between two sequences.\r\n"
        		+ "The code is represented by the following relation.\r\n"
        		+ "                                |\r\n"
        		+ "                                |  0                                                        if ( i = 0 ) || ( j = 0 )\r\n"
        		+ "              LCS[i,j]  =  |\r\n"
        		+ "                                |  c [ i-1, j-1 ] + 1                                if xi = yj\r\n"
        		+ "                                |\r\n"
        		+ "                                |  max ( c [ i-1, j ], c [ i , j-1 ] )          if xi ≠ yj\r\n"
        		+ "                                |");
        relation.setLayoutX(14.0);
        relation.setLayoutY(511.0);
        relation.setStyle("-fx-font-weight: bold; -fx-font-style: italic;");
        
        Label LISrelation = new Label("The LIS Relation:\n\n"
        		+ "Initial Value LIS[i] = 1 \r\n"
        		+ "which has the O(N^2) time complexity \r\n\n"
        		+ "                                |\r\n"
        		+ "                                |  max(LIS[j]+1, LIS[i])                          ,arr[j] < arr[i] \r\n"
				+ "                                |  j+1                                 \r\n"
        		+ "                 LIS[i]  =  |\r\n"
        		+ "                                |\r\n"
        		+ "                                |    j+1                                                    ,arr[j] >= arr[i] \r\n"
				+ "                                |  i+1, j=0                                              ,j+1=i \r\n"
        		+ "                                |");
        LISrelation.setLayoutX(600.0);
        LISrelation.setLayoutY(511.0);
        LISrelation.setStyle("-fx-font-weight: bold; -fx-font-style: italic;");
        

        // Create "Try another data" button
        btTryAnotherData = createButton("Try another data");
        btTryAnotherData.setLayoutX(1350.0);
        btTryAnotherData.setLayoutY(750.0);
        
        btLIS = createButton("Calculate LIS");
        btLIS.setLayoutX(1000.0);
        btLIS.setLayoutY(500.0);

        // Create TextArea for LIS results
        textAreaForLIS = createTextArea(1150.0, 500.0, 350.0, 200.0, "LIS Results");

        
        
        // Add all components to the root layout
        root.getChildren().addAll(controlsVBox, textAreaForResult, textAreaDetails, textAreaForData,
                labelLEDsPower, labelResults, btTryAnotherData, labelDetails, relation,LISrelation, btLIS, textAreaForLIS);

        setEventHandlers();
        Scene scene = new Scene(root, 1500, 800);
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createButton(String buttonText) {
        Button button = new Button(buttonText);
        button.setAlignment(Pos.CENTER);
        button.setContentDisplay(ContentDisplay.CENTER);
        button.setMnemonicParsing(false);
        button.setStyle("-fx-background-color: #63cdda;");
        button.setFont(new Font("System Bold", 14.0));
        return button;
    }

    private TextArea createTextArea(double layoutX, double layoutY, double prefWidth, double prefHeight, String promptText) {
        TextArea textArea = new TextArea();
        textArea.setLayoutX(layoutX);
        textArea.setLayoutY(layoutY);
        textArea.setPrefWidth(prefWidth);
        textArea.setPrefHeight(prefHeight);
        textArea.setStyle("-fx-background-color: #3dc1d3; -fx-border-color: #3dc1d3; -fx-border-width: 1.5;");
        textArea.setEditable(false);
        textArea.setPromptText(promptText);
        textArea.setFont(new Font("Microsoft Sans Serif", 14.0));
        return textArea;
    }

    private Label createLabel(double layoutX, double layoutY, double prefWidth, String labelText) {
        Label label = new Label(labelText);
        label.setAlignment(Pos.CENTER);
        label.setLayoutX(layoutX);
        label.setLayoutY(layoutY);
        label.setPrefHeight(35.0);
        label.setPrefWidth(prefWidth);
        label.setStyle("-fx-background-color: #3dc1d3; -fx-text-fill: #ffffff;");
        label.setFont(new Font("System Bold", 16.0));
        return label;
    }

    private void setEventHandlers() {
        btUploadData.setOnAction(event -> uploadFiles());
        btFind.setOnAction(event -> handleFind());
        btTryAnotherData.setOnAction(event ->handleBtTryAnotherData());
        btLIS.setOnAction(event -> calculateLIS());
    }

  /*--------------------------------------------------------------------------------------------------------------------------------*/  

    // Handle set button
    public void handleBtSet() {
        if (this.txtDataSize.getText().trim().isEmpty()) { // To check the text Field if contains value
            displayErrorMessage("Warning! Please Enter the data size");
            this.txtDataSize.clear();
        } else if (isNumber(this.txtDataSize.getText().trim())) {// To check value if it is a number

            // Creating matrix with a size that was entered
            this.size = Integer.parseInt(this.txtDataSize.getText().trim());
            this.LEDs = new int[this.size];

            this.textAreaForData.setText("Data size is: " + this.size + ", and they are:\n");
            
        } else {
            displayErrorMessage("Warning! Please enter a valid size");
            this.txtDataSize.clear();
        }
    }

    // Handle add button
    public void handleBtAdd() {
        String data = this.txtData.getText().trim();
        if (data.isEmpty()) { // To check the text Field if contains value
            displayErrorMessage("Warning! Please Enter the data");

            this.txtData.clear();
            return;
        } else if (isNumber(data)) { // To check value if it is a number

            if (Integer.parseInt(data) <= this.size) { // To validate value

                this.LEDs[this.index] = Integer.parseInt(data);
                this.textAreaForData.appendText(" \nLed # " + this.LEDs[this.index] + "\t\tPower- " + (this.index + 1) + "\n");
                this.index++;

            } else
            displayErrorMessage("Warning! The value must be less than or equal" + this.size);


        } else {
            displayErrorMessage("Warning! Please enter a valid data");

        }

        this.txtData.clear(); // to receive another value

        if (this.index == this.size) { // All data was entered
            this.btFind.setDisable(false);
        }
    }

    private void uploadFiles() {
        File file = new File("TestData.txt");

        if (file != null) {
            this.readDataFromFile(file);
        }
    }
    
    private void displayErrorMessage(String errorMessage) {
        Stage errorStage = new Stage();
        errorStage.setTitle("Error");

        Label errorLabel = new Label(errorMessage);
        errorLabel.setAlignment(Pos.CENTER);
        errorLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        
        VBox errorLayout = new VBox();
        errorLayout.setAlignment(Pos.CENTER);
        errorLayout.setSpacing(10.0);
        errorLayout.getChildren().add(errorLabel);

        Scene errorScene = new Scene(errorLayout, 400, 150);

        errorStage.setScene(errorScene);
        errorStage.show();
    }


    // Method to read data from file
    private void readDataFromFile(File fileName) {
        try {
            Scanner input = new Scanner(fileName); // instance of scanner for read data from file
            if (fileName.length() == 0) {// no data in file
                displayErrorMessage("Warning! There are no data in the file" + fileName);

            } else {
                boolean firstValue = true; // represent the size of the data
                while (input.hasNext()) { // Read file value by value

                    if (input.hasNextInt()) { // File have contains a data
                        if (firstValue) { // To check the size of the data if it is specified or not
                            this.size = input.nextInt();
                            this.LEDs = new int[this.size];
                            this.textAreaForData.setText("Data size is: " + this.size + ", and they are:\n");
                            firstValue = false;
                        } else { // The size of the data was specified, thus starting read them
                            int data = input.nextInt();
                            if(index < this.size){
                                if (data <= this.size) { // To validate value
                                   if(!isFound(this.LEDs, data)){
                                       this.LEDs[this.index] = data;
                                       this.textAreaForData.appendText(" \nLed #" + this.LEDs[this.index] + "\t\tPower-" + (this.index + 1) + "\n");
                                       this.index++;
                                   }else{
                                       displayErrorMessage("Warning!" + data+ " Is already exit");

                                   }

                                } else {
                                    displayErrorMessage("Warning!" + data + " is invalid");

                                }
                            }else{
                                displayErrorMessage("Warning! The number leds larger than it's size\nSo we take the first "+index+" LEds");
                            }

                        }
                    } else {
                        input.next(); // To ignore anything not an integer
                    }
                }
                input.close(); // prevent(close) scanner to read data
                this.btFind.setDisable(false);
                this.btUploadData.setDisable(true);
            }
        } catch (FileNotFoundException e) {
            // The specific file for reading data does not exist
            displayErrorMessage("Error! The system can NOT find the file " + fileName + "  ");

        }
    }

    // Handle find pairs button
    public void handleFind() {
        this.longestCommonSubsequence();
        this.btTryAnotherData.setDisable(false);
        this.btFind.setDisable(true);
    }


    // Handle try another data button
    public void handleBtTryAnotherData() {
        this.returnControlsDefault();
    }

    // To check the value of the entered numberOfShares if contain only digits or not
    public static boolean isNumber(String number) {
        try {
            return number.matches("\\d+") && Integer.parseInt(number) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Handle try another data button
    private void returnControlsDefault() {
       
        this.btUploadData.setDisable(false);
        this.btFind.setDisable(false);
        this.btTryAnotherData.setDisable(false);
        this.size = 0;
        this.index = 0;
        this.textAreaDetails.clear();
        this.textAreaForData.clear();
        this.textAreaForResult.clear();
        this.textAreaForResult.clear();
        this.textAreaForLIS.clear();
    }

   
    public void longestCommonSubsequence() {

        int[][] costForLCS = new int[this.size + 1][this.size + 1];
        byte[][] tempArrayForDisplayResult = new byte[this.size + 1][this.size + 1];

        for (int i = 1; i <= this.size; i++) {

            for (int j = 1; j <= this.size; j++) {

                if (i == this.LEDs[j - 1]) { // Second case from the above relation
                    costForLCS[i][j] = costForLCS[i - 1][j - 1] + 1;
                    tempArrayForDisplayResult[i][j] = 1;

                } else {// Third case from the above relation
                    if (costForLCS[i][j - 1] > costForLCS[i - 1][j]) {
                        costForLCS[i][j] = costForLCS[i][j - 1];
                        tempArrayForDisplayResult[i][j] = 0;
                    } else {
                        costForLCS[i][j] = costForLCS[i - 1][j];
                        tempArrayForDisplayResult[i][j] = 2;
                    }
                }
            }
        }

        displayAllLCS(tempArrayForDisplayResult, this.size, this.size, costForLCS);

        // View details of the results
        this.textAreaDetails.appendText("\t");
        this.textAreaForResult.appendText("\t");
        for (int i = 0; i < this.size + 1; i++) {

            for (int j = 0; j < this.size + 1; j++) {

                /* The first row and first column which was zero value will be skipped
                    and instead of them will be filled with Led word in the row,
                    and Power word in the column
                */
                if (i == 0 && j < this.size) {
                    textAreaDetails.appendText("\t\t" + "Led-" + this.LEDs[j]);
                    textAreaForResult.appendText("\t\t" + "Led-" + this.LEDs[j]);
                } else if (j == 0) {
                    textAreaDetails.appendText("Power-" + i + "\t\t");
                    textAreaForResult.appendText("Power-" + i + "\t\t");
                }

                if ((j > 0) && (i > 0)) {// Display values after fill first row and first column
                    char arrow = '←';
                    if (tempArrayForDisplayResult[i][j] == 1) {
                        arrow = '↖';
                    } else if (tempArrayForDisplayResult[i][j] == 2) {
                        arrow = '↑';
                    }
                    textAreaDetails.appendText(" " + arrow + " \t  \t  \t");
                    textAreaForResult.appendText(" " + costForLCS[i][j] + " \t  \t  \t");
                }

            }

            this.textAreaDetails.appendText("\n");
            this.textAreaForResult.appendText("\n");
        }


    }

    // Display all longest common subsequence
    public void displayAllLCS(byte[][] b, int i, int j, int[][] costForLCS) {

        int row = i; // to looping for all row have the LCS number(costForLCS[size][size])
        int column = j;// to looping for all column have the LCS number(costForLCS[size][size])

        // To store all unique LCS pairs
        LinkedList<String> allLCS = new LinkedList<>();
        // To visit through column rows that contain LCS number
        while (costForLCS[row][column] == costForLCS[i][j]) { // To check if the current number in the costForLCS matrix equal the LCS length

            // To visit through column that contain LCS number
            while (costForLCS[row][column] == costForLCS[i][j]) { // To check if the current number in the costForLCS matrix equal the LCS length

                int tempRow = row;
                int tempColumn = column;
                /* We used the pathways using both of those variables,
                but the initial value for them start from
                the current row and column, so will be changed
                in every row and column */

                String pairs = ""; // To store the LCS LEDs

                while (tempRow != 0 && tempColumn != 0) { // To visit the path that represent LCS pairs
                    if (b[tempRow][tempColumn] == 1) {
                        if(strRes.length()<costForLCS[costForLCS.length-1][costForLCS.length-1]){
                            this.strRes = tempRow + strRes;
                        }
                        pairs = "[Power-" + tempRow + ", Led-" + tempRow + "]\n" + pairs;
                        tempRow--; // to move the previous row
                        tempColumn--; // to move the previous column
                    } else if (b[tempRow][tempColumn] == 0) tempColumn--;
                    else tempRow--;
                }


                if (!allLCS.contains(pairs)) {
                    allLCS.addFirst(pairs);
                    this.textAreaForResult.appendText("The Best Pairs to be connected are:\n");
                    this.textAreaForResult.appendText(pairs);
                    this.textAreaForResult.appendText("\nThis " + costForLCS[row][column] + " LEDs will be lighted");
                    this.textAreaForResult.appendText("\n\n======================OR======================\n\n");
                }

                column--;
            }
            row--;
            column = j; // To start from last column in the new row(previous row of the current row)
        }

    }

    // Another DP Algorithm the longest increasing substance
    private String lengthOfLIS(int[] leds) {

        if (leds.length == 0) return null;

        // for the longest increasing
        int[] lis = new int[leds.length];

        // used to print the all LIS
        int[] indices = new int[leds.length];

        // longest increasing sub-sequence having just one element has length 1
        Arrays.fill(lis, 1);
        Arrays.fill(indices, -1);

        // start from second element in the array
        for (int i = 1; i < leds.length; i++) {

            // do for each element in sub-array LEDs[0..i-1]
            for (int j = 0; j < i; j++) {
                if (leds[i] > leds[j] && lis[i] < lis[j] + 1) {
                    lis[i] = lis[j] + 1;
                    // store what index helped to extend lis[i]
                    indices[i] = j;
                }
            }
        }
        // find the maximum from lis array and it's index
        int max = lis[0], maxIndex = 0;
        for (int i = 1; i < leds.length; i++) {
            if (max <= lis[i]) {
                max = lis[i];
                maxIndex = i;
            }
        }

        //starting from index of max-length LIS traverse back
        //using indices array populated earlier
        String str = "";
        while (maxIndex >= 0) {
            str = "[Power-" + leds[maxIndex] + ", Led-" + leds[maxIndex] + "]\n" + str;

            maxIndex = indices[maxIndex];
        }
        return str;
    }

    private static boolean isFound(int [] array, int num){
        for (int j : array) {
            if (j == num) return true;
        }
        return false;
    }
    
    private void calculateLIS() {
        if (LEDs != null && LEDs.length > 0) {
            String lisResult = lengthOfLIS(LEDs);
            if (lisResult != null) {
                textAreaForLIS.setText("Longest Increasing Subsequence (LIS) Results:\n" + lisResult);
            } else {
                textAreaForLIS.setText("No LIS found for the given LED data.");
            }
        } else {
            displayErrorMessage("Please upload LED data first.");
        }
    }
}
