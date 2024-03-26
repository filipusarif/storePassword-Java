/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author lenovo
 */
//import PV.evolution.util.Encryptor;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordStore {
    public String name, username;
    private String password, hashkey;
    private double score;
    private int category;
    public static final int UNCATEGORIZED = 0;
    public static final int CAT_WEBAPP = 1;
    public static final int CAT_MOBILEAPP = 2;
    public static final int CAT_OTHER = 3;
    
    public PasswordStore(String name, String username, String plainPass) throws Exception {
        hashkey = Encryptor.generateKey();
        this.name = name;
        this.username = username;
        setPassword(plainPass);
        setCategory(UNCATEGORIZED);
    }
    
    public PasswordStore(String name, String username, String plainPass, int category) throws NoSuchAlgorithmException, Exception{
        hashkey = Encryptor.generateKey();
        this.name = name;
        this.username = username;
        setPassword(plainPass);
        setCategory(category);
    }
    
    public void setPassword(String plainPass) throws Exception{
        this.password = Encryptor.encrypt(plainPass, this.hashkey);
        calculateScore(this.password);
    }
    
    public String getPassword() throws Exception{
        return Encryptor.decrypt(this.password, this.hashkey);
    }
    
    public void setCategory(int category){
        if (category >= 0 && category <= 3 ) {
            this.category = category;
        } else {
            this.category = 0;
        }
    }
    
    public String getCategory(){
        switch(this.category){
            case 0:
                return "Belum terkategori";
            case 1:
                return "Aplikasi web";
            case 2:
                return "Aplikasi mobile";
            case 3:
                return "Akun lainnya";
            default:
                return "";
        }
    }
    
    private void calculateScore(String plainPass){
        if (plainPass.length() > 15) {
            this.score = 10;
        } else {
            this.score = (plainPass.length() / 15) * 10;
        }
    }
    
    @Override
    public String toString() {
        return "Username: " + this.username + "\n" +
               "Password: " + this.password + "\n" +
               "Hashkey: " + this.hashkey + "\n" +
               "Category: " + getCategory() + "\n" +
               "Score: " + this.score;
    }
}

