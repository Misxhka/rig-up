package rig.up.Model;


public class User {
    private String  email;
    private String password;
    private String name;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String Password) {
        password = Password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String Email) {
        email = Email;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name;
    }
}