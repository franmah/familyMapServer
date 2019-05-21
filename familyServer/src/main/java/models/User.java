package models;

import java.util.Objects;

/**
 * Client user. 
 * Users use their user_name and password to connect to the server.
 */
public class User{
    
    public String user_name = null;
    public String password = null;
    public String email = null;
    public String first_name = null;
    public String last_name = null;
    public String gender = null;
    public String person_id = null;
    
    public User(){}



    public User(String user_name, String password, String email,
                String first_name, String last_name,
                String gender, String person_id){
    
        this.user_name = user_name;
        this.password = password;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.person_id = person_id;
    }


    public String getUserName() {
        return user_name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getGender() {
        return gender;
    }

    public String getPersonId() {
        return person_id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(user_name, user.user_name) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(first_name, user.first_name) &&
                Objects.equals(last_name, user.last_name) &&
                Objects.equals(getGender(), user.getGender()) &&
                Objects.equals(person_id, user.person_id);
    }

}