package dao;

import javafx.scene.control.Alert;

public class AlertWindow {
    public void showInformationWindow(String context){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
        alert.setHeaderText("Result :");
        alert.setContentText(context);
        alert.showAndWait();
    }

    public void showErrorWindow(String context){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("Result :");
        alert.setContentText(context);
        alert.showAndWait();
    }
}
