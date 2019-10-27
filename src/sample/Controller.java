package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import java.io.*;

public class Controller {
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailField;
    @FXML
    private Button signUp;
    @FXML
    private Button signIn;
    @FXML
    private GridPane gridpane;

    public void initialize(){
        signIn.setDisable(true);
        signUp.setDisable(true);
    }

    @FXML
    public void validateInput(){
        String emailInput = emailField.getText().trim();
        String passwordInput = passwordField.getText().trim();
        boolean isDisable = emailInput.isEmpty() || passwordInput.isEmpty();
        signIn.setDisable(isDisable);
        signUp.setDisable(isDisable);

    }

    public boolean validateFile(String fileURL) {
        File file = new File(fileURL);
        if(!file.exists()){
            signIn.setDisable(true);
            return false;
        }
        return true;
    }

    public void clearInput(){
        emailField.clear();
        passwordField.clear();
    }

    @FXML
    public void colorButton(MouseEvent event){
        if(event.getSource().equals(signUp)){
            signUp.setStyle("-fx-background-color:#23e124");
        }else if(event.getSource().equals(signIn)){
            signIn.setStyle("-fx-background-color:#23e124");
        }
    }
    @FXML
    public void uncolorButton(MouseEvent event){
        if(event.getSource().equals(signUp)){
            signUp.setStyle("-fx-background-color:#80334d");
        }else if(event.getSource().equals(signIn)){
            signIn.setStyle("-fx-background-color:#80334d");
        }
    }

    @FXML
    public void setSignUp() throws IOException {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        File file = new File("sign_up_records.txt");
        FileWriter fileWriter = new FileWriter(file,true);
        PrintWriter printWriter  = new PrintWriter(fileWriter);
        printWriter.println(email+" "+password);
        printWriter.close();
        clearInput();
        validateInput();
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"SignUp successfully", ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    public void setSignIn() throws IOException {
        String fileURL = "sign_up_records.txt";
        if(!validateFile(fileURL)){
            Alert alert = new Alert(Alert.AlertType.ERROR,"No records exists\nSignUP first!", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        File file = new File("sign_up_records.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        while((line=bufferedReader.readLine())!=null){

            String [] vals = line.split(" ");

            if((vals[0].equals(email)) && (vals[1].equals(password))){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Logged In successfully", ButtonType.OK);
                alert.showAndWait();
                bufferedReader.close();
                clearInput();
                validateInput();
                return;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR,"wrong email address or password", ButtonType.OK);
        alert.showAndWait();
        bufferedReader.close();
        clearInput();
        validateInput();
    }
}