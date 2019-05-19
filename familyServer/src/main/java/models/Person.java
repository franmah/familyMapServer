package models;

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
    
}