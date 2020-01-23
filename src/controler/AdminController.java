package controler;

import com.mysql.cj.protocol.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Messages;
import model.Product;
import model.User;
import dao.Const;
import dao.DatabeseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminController {

    public static Integer idEdit;

    @FXML
    private ListView<Product> allListView;

    @FXML
    private Button blockButton;

    @FXML
    private Button unblockButton;

    @FXML
    private Label userMoney;

    @FXML
    private Label totalMoney;

    @FXML
    private ComboBox<User> choseUserComboBox;

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
    private TextArea writeMessage;

    @FXML
    private Button sendButton;

    @FXML
    private Button addButton;

    @FXML
    private Button allProductsButton;

    @FXML
    void initialize() {
        fillCommonBoxWithUsers();
        showAllProducts();
        System.out.println(LoginController.NAME_USER);
        totalMoney.setText(calculatePrice().toString());

        unconfirmButton.setOnAction(actionEvent -> {
            DatabeseHandler dbHendler = new DatabeseHandler();
            dbHendler.unconfirmUser(String.valueOf(choseUserComboBox.getValue()));
            unconfirmButton.setStyle("-fx-background-color: #FF0000");
            confirmButton.setStyle("-fx-background-color: #DCDCDC");
            showButtonColor();
        });

        confirmButton.setOnAction(actionEvent -> {
            DatabeseHandler dbHendler = new DatabeseHandler();
            dbHendler.confirmUser(String.valueOf(choseUserComboBox.getValue()));
            confirmButton.setStyle("-fx-background-color: #32CD32");
            unconfirmButton.setStyle("-fx-background-color: #DCDCDC");
            showButtonColor();
        });

        blockButton.setOnAction(actionEvent -> {
            DatabeseHandler dbHendler = new DatabeseHandler();
            dbHendler.blockUser(String.valueOf(choseUserComboBox.getValue()));
            blockButton.setStyle("-fx-background-color: #FF0000");
            unblockButton.setStyle("-fx-background-color: #DCDCDC");
            showButtonColor();
        });

        unblockButton.setOnAction(actionEvent -> {
            DatabeseHandler dbHendler = new DatabeseHandler();
            dbHendler.unblockUser(String.valueOf(choseUserComboBox.getValue()));
            unblockButton.setStyle("-fx-background-color: #32CD32");
            blockButton.setStyle("-fx-background-color: #DCDCDC");
            showButtonColor();
        });

        sendButton.setOnAction(actionEvent -> {
            sendMessages();
            showMessages(String.valueOf(choseUserComboBox.getValue()));
        });

        choseUserComboBox.setOnAction(actionEvent -> {
            showButtonColor();
            showMessages(String.valueOf(choseUserComboBox.getValue()));
            printOrder(String.valueOf(choseUserComboBox.getValue()));
            userMoney.setText(String.valueOf(getUserMoney()));
            totalMoney.setText(String.valueOf(calculatePrice()));
        });

        addButton.setOnAction(actionEvent -> {
            DatabeseHandler handler = new DatabeseHandler();
            Product product = new Product();
            product.setName(brandText.getText());
            product.setModel(modelText.getText());
            product.setPrice(Double.valueOf(priceText.getText()));
            handler.addProductsIntoDatabase(product);
            clearBrandModelPrice();
            showAllProducts();
        });

        saveButton.setOnAction(actionEvent -> {
            DatabeseHandler handler = new DatabeseHandler();
            Product product = new Product();
            product.setId(idEdit);
            product.setName(brandText.getText());
            product.setModel(modelText.getText());
            product.setPrice(Double.valueOf(priceText.getText()));
            handler.changeProductInDatabase(product);
            clearBrandModelPrice();
            showAllProducts();
        });

        allProductsButton.setOnAction(actionEvent -> showAllProducts());

        allListView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                ObservableList<Product> list = allListView.getSelectionModel().getSelectedItems();
                Product product = list.get(0);
                idEdit = product.getId();
                brandText.setText(product.getName());
                modelText.setText(product.getModel());
                priceText.setText(String.valueOf(product.getPrice()));
            }
        });
    }

    private void showButtonColor() {
        DatabeseHandler dbHendler = new DatabeseHandler();
        int statusUser = 0;
        int statusOrder = 0;
        ResultSet resultSet = dbHendler.getUserAndOrderStatus(String.valueOf(choseUserComboBox.getValue()));
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
        return allListView.getItems().stream().mapToDouble(Product::getPrice).sum();
    }

    private Double getUserMoney(){
        DatabeseHandler handler = new DatabeseHandler();
        handler.getUserAndOrderStatus(String.valueOf(choseUserComboBox.getValue()));
        double userMoney = 0.0d;
        ResultSet resultSet = handler.getUserAndOrderStatus(String.valueOf(choseUserComboBox.getValue()));
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
        DatabeseHandler handler = new DatabeseHandler();
        ResultSet resultSet = handler.getProductOrder(buyer);
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
        allListView.getItems().clear();
        allListView.getItems().addAll(list);
    }

    private void showAllProducts() {
        DatabeseHandler handler = new DatabeseHandler();
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
        allListView.getItems().clear();
        allListView.getItems().addAll(list);
    }

    private void clearBrandModelPrice() {
        brandText.clear();
        priceText.clear();
        modelText.clear();
    }

    private void fillCommonBoxWithUsers() {
        DatabeseHandler handler = new DatabeseHandler();
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
        choseUserComboBox.setItems(usersList);
    }

    private void showMessages(String nickName){
        DatabeseHandler handler = new DatabeseHandler();
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
        DatabeseHandler handler = new DatabeseHandler();
        Messages message = new Messages();
        message.setAuthor(LoginController.NAME_USER);
        message.setMessages(writeMessage.getText());
        message.setKeyUser(String.valueOf(choseUserComboBox.getValue()));
        handler.sendMessagesIntoDatabase(message);
    }
}

