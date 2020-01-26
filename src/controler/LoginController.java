package controler;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.AlertWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import dao.Const;
import dao.DatabaseHandler;
import model.User;

public class LoginController {
    private DatabaseHandler handler = new DatabaseHandler();
    private AlertWindow window = new AlertWindow();
    public static String NAME_USER;

    @FXML
    private TextField loginText;

    @FXML
    private TextField passwordText;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink registrationHyperlink;

    @FXML
    void initialize() {
        loginButton.setOnAction(actionEvent -> {
            String loginText = this.loginText.getText().trim();
            String passwordText = this.passwordText.getText().trim();
            if (!loginText.equals("") && !passwordText.equals("")) {
                loginUser(loginText, passwordText);
            } else {
                window.showErrorWindow("Password or login is empty");
            }
        });

        registrationHyperlink.setOnAction(actionEvent -> {
            registrationHyperlink.getScene().getWindow().hide();
            goToThePage("/view/registration.fxml");
        });
    }

    private void loginUser(String loginText, String passwordText) {
        User user = new User();
        user.setNickName(this.loginText.getText());
        user.setPassword(this.passwordText.getText());
        String nickName = null;
        String password = null;
        int statusUser = 0;
        ResultSet resultSet = handler.getUser(user);
        try {
            while (resultSet.next()) {
                nickName = resultSet.getString(Const.USER_NICK_NAME);
                password = resultSet.getString(Const.USER_PASSWORD);
                statusUser = resultSet.getInt(Const.USER_STATUS_USER);
            }
        } catch (SQLException t) {
            t.getStackTrace();
        }

        if (loginText.equals(nickName) && passwordText.equals(password) && statusUser!=0) {
            NAME_USER = nickName;
            goToTheMainPage();
        }else if(loginText.equals("admin") && passwordText.equals("admin")){
            NAME_USER = "admin";
            goToTheAdminPage();
        }else {
            window.showErrorWindow("Incorrect login/password or Admin blocked you");
        }
    }

    private void goToTheAdminPage() {
        loginButton.getScene().getWindow().hide();
        goToThePage("/view/adminPage.fxml");
    }

    private void goToTheMainPage() {
        loginButton.getScene().getWindow().hide();
        goToThePage("/view/mainPage.fxml");
    }

    private void goToThePage(String way){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(way));
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

