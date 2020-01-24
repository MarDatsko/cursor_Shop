package controler;

import java.io.IOException;

import dao.AlertWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import dao.DatabaseHandler;
import model.User;

public class RegistrationController {
    private DatabaseHandler handler = new DatabaseHandler();
    private AlertWindow window = new AlertWindow();

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField secondNameTextField;

    @FXML
    private TextField moneyTextField;

    @FXML
    private TextField nickNameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private TextField countryTextField;

    @FXML
    private RadioButton femaleRadioButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button exitButton;

    @FXML
    void initialize() {
        saveButton.setOnAction(actionEvent -> {
            if (singUpNewUser()) {
                returnToLoginPage();
            }
        });

        exitButton.setOnAction(actionEvent -> {
            exitButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/sample.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
    }

    private void returnToLoginPage() {
        saveButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/sample.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private boolean singUpNewUser() {
        boolean isSingUp = false;
        if (!isTextFieldNotEmpty()) {
            String firstName = firstNameTextField.getText();
            String secondName = secondNameTextField.getText();
            String nickName = nickNameTextField.getText();
            String password = passwordTextField.getText().trim();
            String country = countryTextField.getText();
            String gender;
            if (femaleRadioButton.isPressed()) {
                gender = femaleRadioButton.getText();
            } else {
                gender = maleRadioButton.getText();
            }
            Double money= Double.valueOf(moneyTextField.getText());
            User newUser = new User(firstName, secondName, nickName, password, country, gender, money);
            isSingUp = handler.singUpUser(newUser);
        }
        return isSingUp;
    }

    private boolean isTextFieldNotEmpty(){
        boolean isEmpty = false;
        if(firstNameTextField.getText().isEmpty() || firstNameTextField.getText().isBlank()){
            isEmpty = true;
            window.showErrorWindow("Text view is empty");
        }else if(secondNameTextField.getText().isEmpty() || secondNameTextField.getText().isBlank()){
            isEmpty = true;
            window.showErrorWindow("Text view is empty");
        }else if(nickNameTextField.getText().isEmpty() || nickNameTextField.getText().isBlank()){
            isEmpty = true;
            window.showErrorWindow("Text view is empty");
        }else if(passwordTextField.getText().isEmpty() || passwordTextField.getText().isBlank()){
            isEmpty = true;
            window.showErrorWindow("Text view is empty");
        }else if(countryTextField.getText().isEmpty() || countryTextField.getText().isBlank()){
            isEmpty = true;
            window.showErrorWindow("Text view is empty");
        }else if(femaleRadioButton.isPressed()|| maleRadioButton.isPressed()){
            isEmpty = true;
            window.showErrorWindow("Text view is empty");
        }else if(moneyTextField.getText().isEmpty() || moneyTextField.getText().isBlank()){
            isEmpty = true;
            window.showErrorWindow("Text view is empty");
        }else if(!moneyTextField.getText().isEmpty() || !moneyTextField.getText().isBlank()){
            try {
                Double money = Double.valueOf(moneyTextField.getText());
                money.hashCode();
            }catch (NumberFormatException e){
                window.showErrorWindow("Change field Money!\nIn this field you must write only number");
                isEmpty = true;
            }
        }
        return isEmpty;
    }
}

