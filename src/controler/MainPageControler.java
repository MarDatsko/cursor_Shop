package controler;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import model.Messages;
import model.Product;
import sample.Const;
import sample.DatabeseHandler;

public class MainPageControler {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<?> productsList;

    @FXML
    private TextField searchTextField;

    @FXML
    private ListView<?> orderList;

    @FXML
    private Button search;

    @FXML
    private TextArea messagesText;

    @FXML
    private Button sendButton;

    @FXML
    private ListView<?> messagesList;

    @FXML
    void initialize() {
        printProductsList();
        showAllMessages();

        sendButton.setOnAction(actionEvent -> {
            sendMessages();
            showAllMessages();
            //sendButton.setStyle("-fx-background-color: red");
        });

        search.setOnAction(actionEvent -> {
            DatabeseHandler handler = new DatabeseHandler();
            String txt = searchTextField.getText();
            System.out.println(txt);
            ResultSet resultSet =  handler.getProductByName(txt);
            ObservableList list = FXCollections.observableArrayList();
            try {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setId(resultSet.getInt(Const.PRODUCTS_ID));
                    product.setName(resultSet.getString(Const.PRODUCTS_NAME));
                    product.setPrice(resultSet.getDouble(Const.PRODUCTS_PRICE));
                    product.setModel(resultSet.getString(Const.PRODUCTS_MODEL));

                    list.addAll(product);
                }
            } catch (SQLException t) {
                t.getStackTrace();
            }
            productsList.getItems().clear();
            productsList.getItems().addAll(list);
        });

        productsList.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                ObservableList list = productsList.getSelectionModel().getSelectedItems();
                orderList.getItems().addAll(list);
            }
        });

        orderList.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                int i = orderList.getSelectionModel().getSelectedIndex();      // int vs Integer ?????
                orderList.getItems().remove(i);
            }
        });
    }

    private void sendMessages() {
        DatabeseHandler handler = new DatabeseHandler();
        Messages message = new Messages();
        message.setAuthor(LoginController.NAME_USER);
        message.setMessages(messagesText.getText());
        handler.sendMessagesIntoDatabase(message);
    }

    private void showAllMessages() {
        DatabeseHandler handler = new DatabeseHandler();
        ResultSet resultSet = handler.getMessages();
        ObservableList list = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                Messages product = new Messages();
                product.setId(resultSet.getInt(Const.MESSAGES_ID));
                product.setAuthor(resultSet.getString(Const.MESSAGES_AUTHOR));
                product.setMessages(resultSet.getString(Const.MESSAGES_MESSAGE));

                list.addAll(product);
            }
        } catch (SQLException t) {
            t.getStackTrace();
        }
        messagesList.getItems().clear();
        messagesList.getItems().addAll(list);
    }

    private void printProductsList() {
        DatabeseHandler handler = new DatabeseHandler();
        ResultSet resultSet = handler.getProduct();
        ObservableList list = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt(Const.PRODUCTS_ID));
                product.setName(resultSet.getString(Const.PRODUCTS_NAME));
                product.setPrice(resultSet.getDouble(Const.PRODUCTS_PRICE));
                product.setModel(resultSet.getString(Const.PRODUCTS_MODEL));

                list.addAll(product);
            }
        } catch (SQLException t) {
            t.getStackTrace();
        }
        productsList.getItems().addAll(list);
    }
}

