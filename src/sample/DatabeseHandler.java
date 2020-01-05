package sample;

import controler.LoginController;
import model.Messages;
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
                + "," + Const.USER_SECOND_NAME + "," + Const.USER_PASSWORD + "," + Const.USER_COUNTRY + "," + Const.USER_GENDER + ")"
                + "VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnectiont().prepareStatement(insert);
            prSt.setString(1, user.getNickName());
            prSt.setString(2, user.getFirstName());
            prSt.setString(3, user.getSecondName());
            prSt.setString(4, user.getPassword());
            prSt.setString(5, user.getCountry());
            prSt.setString(6, user.getGender());

            prSt.executeUpdate();
            isAddedUser = true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("----------");
            isAddedUser = false;
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

    public ResultSet getProduct() {
        ResultSet resultSet = null;
        String sql = "SELECT * FROM cursor.products";
        try {
            PreparedStatement prSt = getDbConnectiont().prepareStatement(sql);
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
            prSt.setString(1, message.getAuthor());
            prSt.setString(2, message.getMessages());
            prSt.setString(3, LoginController.NAME_USER);

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
}