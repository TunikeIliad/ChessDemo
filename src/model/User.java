package model;

public class User {
    public String userName;
    public String password;
    public int rank;
    public int rate;
    public int score=1;

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
