package model;

public class User {
    public String userName;
    public String password;
    public int rank=1;
    public int rate;
    public int score;

    public User(String userName,String password){
        this.userName=userName;
        this.password=password;
    }
    public User(){};
    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
