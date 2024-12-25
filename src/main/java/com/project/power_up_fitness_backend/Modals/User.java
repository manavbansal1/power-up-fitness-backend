package com.project.power_up_fitness_backend.Modals;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("User-data")
public class User {

    @Id
    String id;
    @Field("name")
    String name;
    @Field("userName")
    String userName;
    @Field("emailID")
    String emailID;
    @Field("imageURL")
    String imageURL;
    @Field("passphrase")
    String passphrase;

    public User(String id,String name,String userName,String emailID,String imageURL,String passphrase)
    {
        this.id=id;
        this.name=name;
        this.userName=userName;
        this.emailID=emailID;
        this.imageURL=imageURL;
        this.passphrase=passphrase;

    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getuserName() {
        return userName;
    }
    public void setuserName(String userName) {
        this.userName = userName;
    }
    public String getemailID() {
        return emailID;
    }
    public void setemailID(String emailID) {
        this.emailID = emailID;
    }
    public String getimageURL() {
        return imageURL;
    }
    public void setimageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    public String getPassphrase() {
        return passphrase;
    }
    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }
    
}
