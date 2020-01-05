package controler;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Const;
import sample.DatabeseHandler;
import model.User;

public class LoginController {
    public static String NAME_USER;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField textFieldLogin;

    @FXML
    private TextField textFieldPassword;

    @FXML
    private Button buttonLogin;

    @FXML
    private Hyperlink hyperlinkRegistration;

    @FXML
    void initialize() {
        buttonLogin.setOnAction(actionEvent -> {
            String loginText = textFieldLogin.getText().trim();
            String passwordText = textFieldPassword.getText().trim();
            if (!loginText.equals("") && !passwordText.equals("")) {
                loginUser(loginText, passwordText);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Results: ");
                alert.setContentText("Password or login is empty");
                alert.showAndWait();
            }
        });

        hyperlinkRegistration.setOnAction(actionEvent -> {
            hyperlinkRegistration.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/registration.fxml"));
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

    private void loginUser(String loginText, String passwordText) {
        DatabeseHandler dbHendler = new DatabeseHandler();
        User user = new User();
        user.setNickName(textFieldLogin.getText());
        user.setPassword(textFieldPassword.getText());
        String nickName = null;
        String password = null;
        ResultSet resultSet = dbHendler.getUser(user);
        System.out.println(resultSet);

        try {
            while (resultSet.next()) {
                nickName = resultSet.getString(Const.USER_NICK_NAME);
                password = resultSet.getString(Const.USER_PASSWORD);
            }
        } catch (SQLException t) {
            t.getStackTrace();
        }

        if (loginText.equals(nickName) && passwordText.equals(password)) {
            NAME_USER = nickName;
            goToTheMainPage();
        }else if(loginText.equals("admin") && passwordText.equals("admin")){
            goToTheAdminPage();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Results: ");
            alert.setContentText("Incorrect login or password");
            alert.showAndWait();
        }
    }

    private void goToTheAdminPage() {
        buttonLogin.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/adminPage.fxml"));
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

    private void goToTheMainPage() {
        buttonLogin.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/mainPage.fxml"));
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
}

