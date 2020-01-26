package controler;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import dao.AlertWindow;
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
import dao.DatabaseHandler;

public class MainPageControler {
    private DatabaseHandler handler = new DatabaseHandler();
    private AlertWindow window = new AlertWindow();
    private final String byPrice = "Price";
    private final String byBrand = "Brand";
    private final String byId = "ID";
    private String sortChoose = byId;

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
    private Button searchButton;

    @FXML
    private TextArea messagesText;

    @FXML
    private Button sendButton;

    @FXML
    private ListView<Messages> messagesList;

    @FXML
    private Label yourMoney;

    @FXML
    void initialize() {
        printProductsList(sortChoose);
        printOrderList();
        showAllMessages();
        fillChooseBox();
        moneyLabel.setText(String.valueOf(calculatePrice()));
        yourMoney.setText(String.valueOf(getUserMoney()));

        buyButton.setOnAction(actionEvent -> {
            int orderStatus = getOrderStatus();
            if (orderList.getItems().stream().count() == 0){
                window.showInformationWindow("You didn't choose any products");
            }else if(orderStatus==0){
                window.showInformationWindow("Please, wait admin should check the order");
            } else {
                List<Integer> listId = orderList.getItems().stream().map(Product::getId).collect(Collectors.toList());
                handler.removeItemList(listId);
                window.showInformationWindow("You have successfully purchased the product");
                handler.unconfirmUser(LoginController.NAME_USER);
                printProductsList(sortChoose);
                printOrderList();
            }
        });

        sortProducts.setOnAction(actionEvent -> {
            sortChoose = sortProducts.getValue();
            printProductsList(sortChoose);
        });

        sendButton.setOnAction(actionEvent -> {
            if(messagesText.getText().isEmpty() || messagesText.getText().isBlank()){
                window.showInformationWindow("Please, write message");
            }else {
                sendMessages();
                showAllMessages();
                messagesText.clear();
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

        searchButton.setOnAction(actionEvent -> {
            if(searchTextField.getText().isBlank() || searchTextField.getText().isEmpty()){
                window.showInformationWindow("Please, write brand product in field");
                printProductsList(sortChoose);
            }else {
                String txt = searchTextField.getText();
                ResultSet resultSet = handler.getProductByName(txt);
                ObservableList<Product> list = writeResultInList(resultSet);

                productsList.getItems().clear();
                productsList.getItems().addAll(list);
            }
        });

        productsList.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                ObservableList<Product> list = productsList.getSelectionModel().getSelectedItems();
                addBuyer(list);
                orderList.getItems().addAll(list);
                productsList.getItems().clear();
                printProductsList(sortChoose);
                moneyLabel.setText(String.valueOf(calculatePrice()));
            }
        });

        orderList.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                ObservableList<Product> list = orderList.getSelectionModel().getSelectedItems();
                int i = orderList.getSelectionModel().getSelectedIndex();      // int vs Integer ?????
                removeBuyer(list);
                orderList.getItems().remove(i);
                productsList.getItems().clear();
                printProductsList(sortChoose);
                moneyLabel.setText(String.valueOf(calculatePrice()));
            }
        });
    }

    private int getOrderStatus() {
        handler.getUserAndOrderStatus(LoginController.NAME_USER);
        int orderStatus = 0;
        ResultSet resultSet = handler.getUserAndOrderStatus(LoginController.NAME_USER);
        try {
            while (resultSet.next()) {
                orderStatus = resultSet.getInt(Const.USER_STATUS_ORDER);
            }
        } catch (SQLException t) {
            t.getStackTrace();
        }
        return orderStatus;
    }

    private Double calculatePrice() {
        return orderList.getItems().stream().mapToDouble(Product::getPrice).sum();
    }

    private Double getUserMoney(){
        handler.getUserAndOrderStatus(LoginController.NAME_USER);
        double userMoney = 0.0d;
        ResultSet resultSet = handler.getUserAndOrderStatus(LoginController.NAME_USER);
        try {
            while (resultSet.next()) {
                userMoney = resultSet.getDouble(Const.USER_MONEY);
            }
        } catch (SQLException t) {
            t.getStackTrace();
        }
        return userMoney;
    }

    private void printOrderList() {
        ResultSet resultSet = handler.getProductOrder(LoginController.NAME_USER);
        ObservableList<Product> list = writeResultInList(resultSet);
        orderList.getItems().clear();
        orderList.getItems().addAll(list);

    }

    private void removeBuyer(ObservableList<Product> list) {
        Product product = list.get(0);
        product.setId(product.getId());
        product.setBuyer(null);
        handler.setBuyer(product);
    }

    private void addBuyer(ObservableList<Product> list) {
        Product product = list.get(0);
        product.setId(product.getId());
        product.setBuyer(LoginController.NAME_USER);
        handler.setBuyer(product);
    }

    private void sendMessages() {
        Messages message = new Messages();
        message.setAuthor(LoginController.NAME_USER);
        message.setMessages(messagesText.getText());
        message.setKeyUser(LoginController.NAME_USER);
        handler.sendMessagesIntoDatabase(message);
    }

    private void showAllMessages() {
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

    private void fillChooseBox() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add(byId);
        list.add(byPrice);
        list.add(byBrand);
        sortProducts.setValue(byId);
        sortProducts.setItems(list);
    }

    private void printProductsList(String sort) {
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
}

