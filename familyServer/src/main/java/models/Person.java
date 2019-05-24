package models;

import java.util.Objects;
import java.util.UUID;

/**
 * Person object. 
 * Each object is linked to a user.
 */
public class Person{
    
    private String person_id = null;
    private String user_name = null;
    private String first_name = null;
    private String last_name = null;
    private String gender = null;
    private String father_id = null;
    private String mother_id = null;
    private String spouse_id = null;
    
    
    public Person(){}
    
    public Person(String user_name, String first_name, String last_name,
        String gender){
            this.person_id = UUID.randomUUID().toString();
            this.user_name = user_name;
            this.first_name = first_name;
            this.last_name = last_name;
            this.gender = gender;
    }

    public Person(String person_id, String user_name, String first_name, String last_name,
                  String gender){
        this.person_id = person_id;
        this.user_name = user_name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
    }
        
    public void setFatherId(String father_id){
        this.father_id = father_id;
    }
    public void setMotherId(String mother_id){
        this.mother_id = mother_id;
    }
    public void setSpouseId(String spouse_id){
        this.spouse_id = spouse_id;
    }
    
    public String getPersonId(){return person_id;}
    public String getUserName(){return user_name;}
    public String getFirstName(){return first_name;}
    public String getLastName(){return last_name;}
    public String getGender(){return gender;}
    public String getFatherId(){return father_id;}
    public String getMotherId(){return mother_id;}
    public String getSpouseId(){return spouse_id;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(person_id, person.person_id) &&
                Objects.equals(user_name, person.user_name) &&
                Objects.equals(first_name, person.first_name) &&
                Objects.equals(last_name, person.last_name) &&
                Objects.equals(getGender(), person.getGender()) &&
                Objects.equals(father_id, person.father_id) &&
                Objects.equals(mother_id, person.mother_id) &&
                Objects.equals(spouse_id, person.spouse_id);
    }

    @Override
    public String toString(){
        StringBuilder final_string = new StringBuilder(person_id + " " + user_name + " " + first_name + " " + last_name + " " + gender);

        if(father_id != null) { final_string.append(" " + father_id); }
        if(mother_id != null) { final_string.append(" " + mother_id); }
        if(spouse_id != null) { final_string.append(" " + spouse_id); }

        return final_string.toString();
    }
}