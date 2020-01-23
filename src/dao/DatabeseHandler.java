package dao;

import controler.LoginController;
import model.Messages;
import model.Product;
import model.User;

import java.sql.*;

public class DatabeseHandler extends Configs {
    public Connection getDbConnectiont() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(connectionString, dbUser, dbPass);
    }

    public boolean singUpUser(User user) {
        boolean isAddedUser = false;
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" + Const.USER_NICK_NAME + "," + Const.USER_FIRST_NAME
                + "," + Const.USER_SECOND_NAME + "," + Const.USER_PASSWORD + "," + Const.USER_COUNTRY + "," + Const.USER_GENDER +"money"+ ")"
                + "VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnectiont().prepareStatement(insert);
            prSt.setString(1, user.getNickName());
            prSt.setString(2, user.getFirstName());
            prSt.setString(3, user.getSecondName());
            prSt.setString(4, user.getPassword());
            prSt.setString(5, user.getCountry());
            prSt.setString(6, user.getGender());
            prSt.setDouble(7,user.getMoney());

            prSt.executeUpdate();
            isAddedUser = true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return isAddedUser;
    }

    public ResultSet getUser(User user) {
        ResultSet resultSet = null;
        String sql = "SELECT * FROM cursor.users WHERE nickname =? AND password =?";
        try {
            PreparedStatement prSt = getDbConnectiont().prepareStatement(sql);
            prSt.setString(1, user.getNickName());
            prSt.setString(2, user.getPassword());

            resultSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getProductByName(String txt) {
        ResultSet resultSet = null;
        String sql = "SELECT * FROM cursor.products WHERE name =?";
        try {
            PreparedStatement prSt = getDbConnectiont().prepareStatement(sql);
            prSt.setString(1, txt);
            resultSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getMessages() {
        ResultSet resultSet = null;
        String sql = "SELECT * FROM cursor.messages WHERE author=? AND keyUser=? OR author=?" ;
        try {
            PreparedStatement prSt = getDbConnectiont().prepareStatement(sql);
            prSt.setString(1,"admin");
            prSt.setString(2, LoginController.NAME_USER);
            prSt.setString(3, LoginController.NAME_USER);
            resultSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void sendMessagesIntoDatabase(Messages message) {
        String sql = "INSERT INTO cursor.messages"+"("+Const.MESSAGES_AUTHOR+","+Const.MESSAGES_MESSAGE+","+Const.MESSAGES_KEY+")"
                + "VALUES (?,?,?)";
        try {
            PreparedStatement prSt = getDbConnectiont().prepareStatement(sql);
            prSt.setString(1,message.getAuthor());
            prSt.setString(2, message.getMessages());
            prSt.setString(3, message.getKeyUser());

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getUsersNickName() {
        ResultSet resultSet = null;
        String sql = "SELECT "+Const.USER_NICK_NAME+" FROM cursor.users";
        try {
            PreparedStatement prSt = getDbConnectiont().prepareStatement(sql);
            resultSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void addProductsIntoDatabase(Product product) {
        String sql = "INSERT INTO cursor.products"+"("+Const.PRODUCTS_NAME+","+Const.PRODUCTS_PRICE+","+Const.PRODUCTS_MODEL+")"
                +"VALUES (?,?,?)";
        try {
            PreparedStatement prSt = getDbConnectiont().prepareStatement(sql);
            prSt.setString(1, product.getName());
            prSt.setString(2, String.valueOf(product.getPrice()));
            prSt.setString(3, product.getModel());

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void changeProductInDatabase(Product product) {
        String sql = "UPDATE cursor.products SET name=?, price=?, model=? WHERE idproducts=?";
        try {
            PreparedStatement prSt = getDbConnectiont().prepareStatement(sql);
            prSt.setString(1, product.getName());
            prSt.setString(2, String.valueOf(product.getPrice()));
            prSt.setString(3, product.getModel());
            prSt.setString(4, String.valueOf(product.getId()));

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getMessagesNick(String nickName) {
        ResultSet resultSet = null;
        String sql = "SELECT * FROM cursor.messages WHERE author=? AND keyUser=? OR author=?" ;
        try {
            PreparedStatement prSt = getDbConnectiont().prepareStatement(sql);
            prSt.setString(1,"admin");
            prSt.setString(2, nickName);
            prSt.setString(3, nickName);
            resultSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void setBuyer(Product product) {
        String sql = "UPDATE cursor.products SET buyer=? WHERE idproducts=?";
        try {
            PreparedStatement prSt = getDbConnectiont().prepareStatement(sql);
            prSt.setString(1, product.getBuyer());
            prSt.setString(2, String.valueOf(product.getId()));

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getProductOrder(String buyer) {
        ResultSet resultSet = null;
        String sql = "SELECT * FROM cursor.products WHERE buyer=?";
        try {
            PreparedStatement prSt = getDbConnectiont().prepareStatement(sql);
            prSt.setString(1,buyer);
            resultSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getProductAll() {
        ResultSet resultSet = null;
        String sql = "SELECT * FROM cursor.products WHERE buyer IS NULL";
        try {
            PreparedStatement prSt = getDbConnectiont().prepareStatement(sql);
            resultSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getProductSortProductsByPrice() {
        ResultSet resultSet = null;
        String sql = "SELECT * FROM cursor.products WHERE buyer IS NULL ORDER BY price DESC";
        try {
            PreparedStatement prSt = getDbConnectiont().prepareStatement(sql);
            resultSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getProductSortProductsByBrand() {
        ResultSet resultSet = null;
        String sql = "SELECT * FROM cursor.products WHERE buyer IS NULL ORDER BY name";
        try {
            PreparedStatement prSt = getDbConnectiont().prepareStatement(sql);
            resultSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getProductSortProductsById() {
        ResultSet resultSet = null;
        String sql = "SELECT * FROM cursor.products WHERE buyer IS NULL ORDER BY idproducts";
        try {
            PreparedStatement prSt = getDbConnectiont().prepareStatement(sql);
            resultSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void blockUser(String nickName) {
        String sql = "UPDATE cursor.users SET statususer=? WHERE nickname=?";
        try {
            PreparedStatement prSt = getDbConnectiont().prepareStatement(sql);
            prSt.setInt(1, 0);
            prSt.setString(2, nickName);

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void unblockUser(String nickName) {
        String sql = "UPDATE cursor.users SET statususer=? WHERE nickname=?";
        try {
            PreparedStatement prSt = getDbConnectiont().prepareStatement(sql);
            prSt.setInt(1, 1);
            prSt.setString(2, nickName);

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getUserAndOrderStatus(String nickName) {
        ResultSet resultSet = null;
        String sql = "SELECT * FROM cursor.users WHERE nickname=?";
        try {
            PreparedStatement prSt = getDbConnectiont().prepareStatement(sql);
            prSt.setString(1,nickName);

            resultSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void unconfirmUser(String nickName) {
        String sql = "UPDATE cursor.users SET statusorder=? WHERE nickname=?";
        try {
            PreparedStatement prSt = getDbConnectiont().prepareStatement(sql);
            prSt.setInt(1, 0);
            prSt.setString(2, nickName);

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void confirmUser(String nickName) {
        String sql = "UPDATE cursor.users SET statusorder=? WHERE nickname=?";
        try {
            PreparedStatement prSt = getDbConnectiont().prepareStatement(sql);
            prSt.setInt(1, 1);
            prSt.setString(2, nickName);

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
