package controler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Product;
import model.User;
import sample.Const;
import sample.DatabeseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminController {

    public static Integer idEdit;

    @FXML
    private ListView<?> allListView;

    @FXML
    private ComboBox<?> choseUserComboBox;

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
    private ListView<?> messageList;

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

        //addButton

        //save get ID, Update in database, refresh;
        
        allProductsButton.setOnAction(actionEvent -> {
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
            allListView.getItems().clear();
            allListView.getItems().addAll(list);
        });

        allListView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                String brand = null;
                String model = null;
                Double price = 0.0d;
                ObservableList list = allListView.getSelectionModel().getSelectedItems();
                Object object = list.get(0);
                if(object instanceof Product){
                    Product product = (Product) object;
                    idEdit = product.getId();
                    brand = product.getName();
                    model = product.getModel();
                    price = product.getPrice();
                }
                brandText.setText(brand);
                modelText.setText(model);
                priceText.setText(price.toString());
            }
        });
    }

    private void fillCommonBoxWithUsers() {
        DatabeseHandler handler = new DatabeseHandler();
        ResultSet resultSet = handler.getUsersNickName();
        ObservableList usersList = FXCollections.observableArrayList();
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

}

