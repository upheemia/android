package com.example.secondlab.db;

public class User {
    int id;
    String username;
    String password;

    public User(){
    }
    public User(int id, String login, String pass){
        this.id = id;
        this.username = login;
        this.password = pass;
    }
    public User(String login, String pass){
        this.username = login;
        this.password = pass;
    }

    public int getID(){
        return this.id;
    }
    public void setID(int id){
        this.id = id;
    }

    public String getLogin(){
        return this.username;
    }
    public void setLogin(String login){
        this.username = login;
    }

    public String getPass(){
        return this.password;
    }
    public void setPass(String pass){
        this.password = pass;
    }
}
