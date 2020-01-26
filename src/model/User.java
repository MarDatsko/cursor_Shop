package model;

public class User {
    private String firstName;
    private String secondName;
    private String nickName;
    private String password;
    private String country;
    private String gender;
    private Double money;
    private Boolean statusUser;
    private Boolean statusOrder;

    @Override
    public String toString() {
        return nickName;
    }

    public User() {
    }

    public User(String firstName, String secondName, String nickName, String password, String country,
                String gender, Double money) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.nickName = nickName;
        this.password = password;
        this.country = country;
        this.gender = gender;
        this.money = money;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Boolean getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(Boolean statusUser) {
        this.statusUser = statusUser;
    }

    public Boolean getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(Boolean statusOrder) {
        this.statusOrder = statusOrder;
    }
}
