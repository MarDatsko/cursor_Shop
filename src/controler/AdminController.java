package controler;


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
import model.User;
import dao.Const;
import dao.DatabaseHandler;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminController {
    private DatabaseHandler handler = new DatabaseHandler();
    private AlertWindow window = new AlertWindow();
    public static Integer idEdit;

    @FXML
    private ListView<Product> allProductsList;

    @FXML
    private Button blockButton;

    @FXML
    private Button unblockButton;

    @FXML
    private Label userMoney;

    @FXML
    private Label totalMoney;

    @FXML
    private ComboBox<User> chooseUserComboBox;

    @FXML
    private TextField brandText;

    @FXML
    private TextField priceText;

    @FXML
    private TextField modelText;

    @FXML
    private Button saveButton;

    @FXML
    private Button confirmButton;

    @FXML
    private Button unconfirmButton;

    @FXML
    private ListView<Messages> messageList;

    @FXML
    private TextArea messageText;

    @FXML
    private Button sendButton;

    @FXML
    private Button addButton;

    @FXML
    private Button allProductsButton;

    @FXML
    private Button exitButton;

    @FXML
    void initialize() {
        fillCommonBoxWithUsers();
        showAllProducts();
        totalMoney.setText(calculatePrice().toString());
        chooseUserComboBox.setPromptText("Choose user");

        unconfirmButton.setOnAction(actionEvent -> {
            handler.unconfirmUser(String.valueOf(chooseUserComboBox.getValue()));
            unconfirmButton.setStyle("-fx-background-color: #FF0000");
            confirmButton.setStyle("-fx-background-color: #DCDCDC");
            showButtonColor();
        });

        confirmButton.setOnAction(actionEvent -> {
            handler.confirmUser(String.valueOf(chooseUserComboBox.getValue()));
            confirmButton.setStyle("-fx-background-color: #32CD32");
            unconfirmButton.setStyle("-fx-background-color: #DCDCDC");
            showButtonColor();
        });

        blockButton.setOnAction(actionEvent -> {
            handler.blockUser(String.valueOf(chooseUserComboBox.getValue()));
            blockButton.setStyle("-fx-background-color: #FF0000");
            unblockButton.setStyle("-fx-background-color: #DCDCDC");
            showButtonColor();
        });

        unblockButton.setOnAction(actionEvent -> {
            handler.unblockUser(String.valueOf(chooseUserComboBox.getValue()));
            unblockButton.setStyle("-fx-background-color: #32CD32");
            blockButton.setStyle("-fx-background-color: #DCDCDC");
            showButtonColor();
        });

        sendButton.setOnAction(actionEvent -> {
            if(messageText.getText().isEmpty() || messageText.getText().isBlank()){
                window.showInformationWindow("Please, write message");
            }else if (chooseUserComboBox.getValue() == null){
                window.showInformationWindow("Choose user what you want write a message");
            } else {
                sendMessages();
                showMessages(String.valueOf(chooseUserComboBox.getValue()));
                messageText.clear();
            }
        });

        chooseUserComboBox.setOnAction(actionEvent -> {
            showButtonColor();
            showMessages(String.valueOf(chooseUserComboBox.getValue()));
            printOrder(String.valueOf(chooseUserComboBox.getValue()));
            userMoney.setText(String.valueOf(getUserMoney()));
            totalMoney.setText(String.valueOf(calculatePrice()));
            clearBrandModelPrice();
        });

        addButton.setOnAction(actionEvent -> {
            if(brandText.getText().isEmpty() || modelText.getText().isEmpty() || priceText.getText().isEmpty()){
                window.showInformationWindow("You have empty field");
            }else {
                Product product = new Product();
                product.setName(brandText.getText());
                product.setModel(modelText.getText());
                product.setPrice(Double.valueOf(priceText.getText()));
                handler.addProductsIntoDatabase(product);
                clearBrandModelPrice();
                showAllProducts();
            }
        });

        saveButton.setOnAction(actionEvent -> {
            if(brandText.getText().isEmpty() || modelText.getText().isEmpty() || priceText.getText().isEmpty()){
                window.showInformationWindow("Choose product to change!");
            }else if(idEdit == null){
                window.showInformationWindow("Choose product to change!\nIf you want add new product press ADD");
            } else {
                Product product = new Product();
                product.setId(idEdit);
                product.setName(brandText.getText());
                product.setModel(modelText.getText());
                product.setPrice(Double.valueOf(priceText.getText()));
                handler.changeProductInDatabase(product);
                clearBrandModelPrice();
                showAllProducts();
                idEdit=null;
            }
        });

        allProductsButton.setOnAction(actionEvent -> {
            showAllProducts();
            chooseUserComboBox.getSelectionModel().clearSelection();
        });

        allProductsList.setOnMouseClicked(mouseEvent -> {

            if (mouseEvent.getClickCount() == 2 & !isProductHasBuyer()) {
                clearBrandModelPrice();
                window.showInformationWindow("You don't change data when\nuser add product to order");
            } else if (mouseEvent.getClickCount() == 2) {
                ObservableList<Product> list = allProductsList.getSelectionModel().getSelectedItems();
                Product product = list.get(0);
                idEdit = product.getId();
                brandText.setText(product.getName());
                modelText.setText(product.getModel());
                priceText.setText(String.valueOf(product.getPrice()));
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

    private boolean isProductHasBuyer() {
        ObservableList<Product> list = allProductsList.getSelectionModel().getSelectedItems();
        Product product = list.get(0);
        System.out.println(product.getBuyer());
        return product.getBuyer() == null;
    }

    private void showButtonColor() {
        int statusUser = 0;
        int statusOrder = 0;
        ResultSet resultSet = handler.getUserAndOrderStatus(String.valueOf(chooseUserComboBox.getValue()));
        try {
            while (resultSet.next()) {
                statusUser = resultSet.getInt(Const.USER_STATUS_USER);
                statusOrder = resultSet.getInt(Const.USER_STATUS_ORDER);
            }
        } catch (SQLException t) {
            t.getStackTrace();
        }

        if(statusUser==0 & statusOrder==0){
            blockButton.setStyle("-fx-background-color: #FF0000");
            unblockButton.setStyle("-fx-background-color: #DCDCDC");
            unconfirmButton.setStyle("-fx-background-color: #FF0000");
            confirmButton.setStyle("-fx-background-color: #DCDCDC");
        }else if (statusUser!=0 & statusOrder!=0){
            unblockButton.setStyle("-fx-background-color: #32CD32");
            blockButton.setStyle("-fx-background-color: #DCDCDC");
            unconfirmButton.setStyle("-fx-background-color: #DCDCDC");
            confirmButton.setStyle("-fx-background-color: #32CD32");
        }else if (statusUser==0 & statusOrder!=0){
            unblockButton.setStyle("-fx-background-color: #DCDCDC");
            blockButton.setStyle("-fx-background-color: #FF0000");
            unconfirmButton.setStyle("-fx-background-color: #DCDCDC");
            confirmButton.setStyle("-fx-background-color: #32CD32");
        }else if (statusUser!=0 & statusOrder==0){
            unblockButton.setStyle("-fx-background-color: #32CD32");
            blockButton.setStyle("-fx-background-color: #DCDCDC");
            unconfirmButton.setStyle("-fx-background-color: #FF0000");
            confirmButton.setStyle("-fx-background-color: #DCDCDC");
        }
    }

    private Double calculatePrice() {
        return allProductsList.getItems().stream().mapToDouble(Product::getPrice).sum();
    }

    private Double getUserMoney(){
        handler.getUserAndOrderStatus(String.valueOf(chooseUserComboBox.getValue()));
        double userMoney = 0.0d;
        ResultSet resultSet = handler.getUserAndOrderStatus(String.valueOf(chooseUserComboBox.getValue()));
        try {
            while (resultSet.next()) {
                userMoney = resultSet.getDouble(Const.USER_MONEY);
            }
        } catch (SQLException t) {
            t.getStackTrace();
        }
        return userMoney;
    }

    private void printOrder(String buyer) {
        ResultSet resultSet = handler.getProductOrder(buyer);
        ObservableList<Product> list = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt(Const.PRODUCTS_ID));
                product.setName(resultSet.getString(Const.PRODUCTS_NAME));
                product.setPrice(resultSet.getDouble(Const.PRODUCTS_PRICE));
                product.setModel(resultSet.getString(Const.PRODUCTS_MODEL));
                product.setBuyer(resultSet.getString(Const.PRODUCTS_BUYER));

                list.addAll(product);
            }
        } catch (SQLException t) {
            t.getStackTrace();
        }
        allProductsList.getItems().clear();
        allProductsList.getItems().addAll(list);
    }

    private void showAllProducts() {
        ResultSet resultSet = handler.getProductAll();
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
        allProductsList.getItems().clear();
        allProductsList.getItems().addAll(list);
    }

    private void clearBrandModelPrice() {
        brandText.clear();
        priceText.clear();
        modelText.clear();
    }

    private void fillCommonBoxWithUsers() {
        ResultSet resultSet = handler.getUsersNickName();
        ObservableList<User> usersList = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                User user = new User();
                user.setNickName(resultSet.getString(Const.USER_NICK_NAME));
                usersList.addAll(user);
            }
        } catch (SQLException t) {
            t.getStackTrace();
        }
        chooseUserComboBox.setItems(usersList);
    }

    private void showMessages(String nickName){
        ResultSet resultSet = handler.getMessagesNick(nickName);
        ObservableList<Messages> list = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                Messages messages = new Messages();
                messages.setId(resultSet.getInt(Const.MESSAGES_ID));
                messages.setAuthor(resultSet.getString(Const.MESSAGES_AUTHOR));
                messages.setMessages(resultSet.getString(Const.MESSAGES_MESSAGE));

                list.addAll(messages);
            }
        } catch (SQLException t) {
            t.getStackTrace();
        }
        messageList.getItems().clear();
        messageList.getItems().addAll(list);
    }

    private void sendMessages(){
        Messages message = new Messages();
        message.setAuthor(LoginController.NAME_USER);
        message.setMessages(messageText.getText());
        message.setKeyUser(String.valueOf(chooseUserComboBox.getValue()));
        handler.sendMessagesIntoDatabase(message);
    }
}

