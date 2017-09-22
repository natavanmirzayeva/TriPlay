package com.ndp.triplay;

/**
 * Created by Murat Kaçmaz on 27.08.2017.
 */


public class User {
    private int id;
    private String UserName;
    private String email;
    private String password; //classdaki verilere direk erişilmemesi amacı ile encapsulation uygulandı

    public User(String userName,String email, String password,int id)
    {
        this.UserName = userName;
        this.email = email;
        this.password = password;
        this.id = id;

    }

    public User() {

    }

    public int getId() {
        return id;
    }




    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
