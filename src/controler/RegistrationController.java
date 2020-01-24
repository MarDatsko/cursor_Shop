package controler;

import java.io.IOException;

import dao.AlertWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import dao.DatabeseHandler;
import model.User;

public class RegistrationController {
    private DatabeseHandler handler = new DatabeseHandler();
    private AlertWindow window = new AlertWindow();

    @FXML
    private TextField textFieldFirstName;

    @FXML
    private TextField textFieldSecondName;

    @FXML
    private TextField moneyTextField;

    @FXML
    private TextField textFieldNickName;

    @FXML
    private TextField textFieldPassword;

    @FXML
    private RadioButton radioButtonMale;

    @FXML
    private TextField countryTextField;

    @FXML
    private ToggleGroup gender;

    @FXML
    private RadioButton radioButtonFemale;

    @FXML
    private Button buttonSave;

    @FXML
    private Button exitButton;

    @FXML
    void initialize() {
        buttonSave.setOnAction(actionEvent -> {
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
        buttonSave.getScene().getWindow().hide();
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
            String firstName = textFieldFirstName.getText();
            String secondName = textFieldSecondName.getText();
            String nickName = textFieldNickName.getText();
            String password = textFieldPassword.getText().trim();
            String country = countryTextField.getText();
            String gender;
            if (radioButtonFemale.isPressed()) {
                gender = radioButtonFemale.getText();
            } else {
                gender = radioButtonMale.getText();
            }
            Double money= Double.valueOf(moneyTextField.getText());
            User newUser = new User(firstName, secondName, nickName, password, country, gender, money);
            isSingUp = handler.singUpUser(newUser);
        }
        return isSingUp;
    }

    private boolean isTextFieldNotEmpty(){
        boolean isEmpty = false;
        if(textFieldFirstName.getText().isEmpty() || textFieldFirstName.getText().isBlank()){
            isEmpty = true;
            window.showErrorWindow("Text view is empty");
        }else if(textFieldSecondName.getText().isEmpty() || textFieldSecondName.getText().isBlank()){
            isEmpty = true;
            window.showErrorWindow("Text view is empty");
        }else if(textFieldNickName.getText().isEmpty() || textFieldNickName.getText().isBlank()){
            isEmpty = true;
            window.showErrorWindow("Text view is empty");
        }else if(textFieldPassword.getText().isEmpty() || textFieldPassword.getText().isBlank()){
            isEmpty = true;
            window.showErrorWindow("Text view is empty");
        }else if(countryTextField.getText().isEmpty() || countryTextField.getText().isBlank()){
            isEmpty = true;
            window.showErrorWindow("Text view is empty");
        }else if(radioButtonFemale.isPressed()|| radioButtonMale.isPressed()){
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

