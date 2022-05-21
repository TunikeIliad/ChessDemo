package model;

public class User {
    public String userName;
    public String password;

    public User(String userName,String password){
        this.userName=userName;
        this.password=password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
