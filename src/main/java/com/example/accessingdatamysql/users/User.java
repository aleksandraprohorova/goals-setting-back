package com.example.accessingdatamysql.users;

import com.example.accessingdatamysql.sprints.Sprint;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private long id;

    @Column (name = "login")
    private String login;

    public User(){
    }

    public User(String login) {
        this.login = login;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }
    void setName(String login)
    {
        this.login = login;
    }
}
