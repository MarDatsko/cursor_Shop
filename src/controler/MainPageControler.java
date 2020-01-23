package controler;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Messages;
import model.Product;
import dao.Const;
import dao.DatabeseHandler;

public class MainPageControler {
    private final String byPrice = "Price";
    private final String byBrand = "Brand";
    private final String byId = "ID";
    private String sortChose = byId;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<Product> productsList;

    @FXML
    private TextField searchTextField;

    @FXML
    private ListView<Product> orderList;

    @FXML
    private Pagination pagination;

    @FXML
    private ChoiceBox<String> sortProducts;

    @FXML
    private Button exitButton;

    @FXML
    private Button buyButton;

    @FXML
    private Label moneyLabel;

    @FXML
    private Button search;

    @FXML
    private TextArea messagesText;

    @FXML
    private Button sendButton;

    @FXML
    private ListView<Messages> messagesList;

    @FXML
    void initialize() {
        printProductsList(sortChose);
        showAllMessages();
        printOrderList();
        fillChoseBox();

        sortProducts.setOnAction(actionEvent -> {
            sortChose = sortProducts.getValue();
            printProductsList(sortChose);
        });

        sendButton.setOnAction(actionEvent -> {
            sendMessages();
            showAllMessages();
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

        search.setOnAction(actionEvent -> {
            DatabeseHandler handler = new DatabeseHandler();
            String txt = searchTextField.getText();
            ResultSet resultSet = handler.getProductByName(txt);
            ObservableList<Product> list = writeResultInList(resultSet);

            productsList.getItems().clear();
            productsList.getItems().addAll(list);
        });

        productsList.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                ObservableList<Product> list = productsList.getSelectionModel().getSelectedItems();
                addBuyer(list);
                orderList.getItems().addAll(list);
                productsList.getItems().clear();
                printProductsList(sortChose);
            }
        });

        orderList.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                ObservableList<Product> list = orderList.getSelectionModel().getSelectedItems();
                int i = orderList.getSelectionModel().getSelectedIndex();      // int vs Integer ?????
                removeBuyer(list);
                orderList.getItems().remove(i);
                productsList.getItems().clear();
                printProductsList(sortChose);
            }
        });
    }

    private void printOrderList() {
        DatabeseHandler handler = new DatabeseHandler();
        ResultSet resultSet = handler.getProductOrder(LoginController.NAME_USER);
        ObservableList<Product> list = writeResultInList(resultSet);
        orderList.getItems().clear();
        orderList.getItems().addAll(list);

    }

    private void removeBuyer(ObservableList<Product> list) {
        DatabeseHandler handler = new DatabeseHandler();
        Product product = list.get(0);
        product.setId(product.getId());
        product.setBuyer(null);
        handler.setBuyer(product);
    }

    private void addBuyer(ObservableList<Product> list) {
        DatabeseHandler handler = new DatabeseHandler();
        Product product = list.get(0);
        product.setId(product.getId());
        product.setBuyer(LoginController.NAME_USER);
        handler.setBuyer(product);
    }

    private void sendMessages() {
        DatabeseHandler handler = new DatabeseHandler();
        Messages message = new Messages();
        message.setAuthor(LoginController.NAME_USER);
        message.setMessages(messagesText.getText());
        message.setKeyUser(LoginController.NAME_USER);
        handler.sendMessagesIntoDatabase(message);
    }

    private void showAllMessages() {
        DatabeseHandler handler = new DatabeseHandler();
        ResultSet resultSet = handler.getMessages();
        ObservableList<Messages> list = FXCollections.observableArrayList();
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

    private ObservableList<Product> writeResultInList(ResultSet resultSet) {
        ObservableList<Product> list = FXCollections.observableArrayList();
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
        return list;
    }

    private void fillChoseBox() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add(byId);
        list.add(byPrice);
        list.add(byBrand);
        sortProducts.setValue(byId);
        sortProducts.setItems(list);
    }

    private void printProductsList(String sort) {
        DatabeseHandler handler = new DatabeseHandler();
        ResultSet resultSet;
        if (sort.equals(byBrand)) {
            resultSet = handler.getProductSortProductsByBrand();
        } else if (sort.equals(byPrice)) {
            resultSet = handler.getProductSortProductsByPrice();
        } else {
            resultSet = handler.getProductSortProductsById();
        }
        ObservableList<Product> list = writeResultInList(resultSet);
        productsList.getItems().clear();
        productsList.getItems().addAll(list);
    }
/*
    private void sortByPrice(){
        DatabeseHandler handler = new DatabeseHandler();
        ResultSet resultSet = handler.getProductSortProductsByPrice();
        ObservableList<Product> list = writeResultInList(resultSet);
        productsList.getItems().clear();
        productsList.getItems().addAll(list);
    }

    private void sortByBrand(){
        DatabeseHandler handler = new DatabeseHandler();
        ResultSet resultSet = handler.getProductSortProductsByBrand();
        ObservableList<Product> list = writeResultInList(resultSet);
        productsList.getItems().clear();
        productsList.getItems().addAll(list);
    }

    private void sortById(){
        DatabeseHandler handler = new DatabeseHandler();
        ResultSet resultSet = handler.getProductSortProductsById();
        ObservableList<Product> list = writeResultInList(resultSet);
        productsList.getItems().clear();
        productsList.getItems().addAll(list);
    }
    */
}

