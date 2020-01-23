package controler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import dao.DatabeseHandler;
import model.User;

public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    void initialize() {
        buttonSave.setOnAction(actionEvent -> {
            if (singUpNewUser()) {
                returnToLoginPage();
            }
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
        DatabeseHandler dbHendler = new DatabeseHandler();
        String firstName = textFieldFirstName.getText();
        String secondName = textFieldSecondName.getText();
        String nickName = textFieldNickName.getText();
        String password = textFieldPassword.getText().trim();
        String country = countryTextField.getText();
        String gender;
        if (radioButtonFemale.isPressed()){
            gender = radioButtonFemale.getText();
        }else {
            gender = radioButtonMale.getText();
        }
        Double money = Double.valueOf(moneyTextField.getText());

        User newUser = new User(firstName,secondName,nickName,password,country,gender,money);
        if (newUser.isUserHaveEmptyLine(newUser)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION");
            alert.setHeaderText("Results: ");
            alert.setContentText("Text view is empty");
            alert.showAndWait();
        }
            return dbHendler.singUpUser(newUser);
    }
}

