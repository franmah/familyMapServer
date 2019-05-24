package services;

import java.io.File;
import java.io.FileReader;
import java.time.LocalTime;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import response.Response;
import response.SuccessResponse;
import response.ErrorResponse;
import models.*;

/**
 * Generate a user's family tree for a given number of generation
 * Handle DataBaseException.
 */
public class FillService{

    private final String location_path = ".." + File.separator + "jsonFiles" + File.separator + "locations.json" ;
    private final String fnames_path = ".." + File.separator + "jsonFiles" + File.separator + "fnames.json";
    private final String mnames_path = ".." + File.separator + "jsonFiles" + File.separator + "mnames.json";
    private final String snames_path = ".." + File.separator + "jsonFiles" + File.separator + "snames.json";

    private Locations locations = null;
    private FemaleNames female_names = null;
    private MalesNames males_names = null;
    private FamilyNames family_names = null;

    /***** JSON CLASSES ****/
    private class Locations {
        private Location[] data;

        public Locations(Location[] data) {
            this.data = data;
        }

        public Location[] getData() {
            return data;
        }

        private class Location {
            private String country;
            private String city;
            private float latitude;
            private float longitude;

            public Location(String country, String city, float latitude, float longitude) {
                this.country = country;
                this.city = city;
                this.latitude = latitude;
                this.longitude = longitude;
            }

            public String getCountry() {
                return country;
            }

            public String getCity() {
                return city;
            }

            public float getLatitude() {
                return latitude;
            }

            public float getLongitude() {
                return longitude;
            }
        }
    }
    private class FemaleNames{
        private String[] data = null;

        public FemaleNames(String[] data) {
            this.data = data;
        }

        public String[] getData() {
            return data;
        }
    }
    private class MalesNames{
        private String[] data = null;

        public String[] getData() {
            return data;
        }

        public MalesNames(String[] data) {
            this.data = data;
        }
    }
    private class FamilyNames{
        private String[] data = null;

        public String[] getData() {
            return data;
        }

        public FamilyNames(String[] data) {
            this.data = data;
        }
    }

    public FillService(){}


    public boolean fillGeneratorObjects(){
        File location_file = new File(location_path);
        File fnames_file = new File(fnames_path);
        File mnames_file = new File(mnames_path);
        File snames_file = new File(snames_path);

        if(!location_file.exists() || !fnames_file.exists() ||
            !mnames_file.exists() || !snames_file.exists()){
            System.out.println(LocalTime.now() + " FillService.fillGeneratorObjects: Error json files not found");
            return false;
        }

        try {
            Gson gson = new Gson();
            JsonReader jsonReader = new JsonReader(new FileReader(location_file));
            locations = gson.fromJson(jsonReader, Locations.class);

            jsonReader = new JsonReader(new FileReader(fnames_file));
            female_names = gson.fromJson(jsonReader, FemaleNames.class);

            jsonReader = new JsonReader(new FileReader(mnames_file));
            males_names = gson.fromJson(jsonReader, MalesNames.class);

            jsonReader = new JsonReader(new FileReader(snames_file));
            family_names = gson.fromJson(jsonReader, FamilyNames.class);


            return true;
        }
        catch (Exception e){
            System.out.println(LocalTime.now() + " FillService.fillGeneratorObjects: Error: " + e.toString());
            e.printStackTrace();
            return false;
        }

    }
    
    /**
     * Method called by other classes. 
     * Find a user, it's person and generate his family tree.
     * 
     * @param   user_name: the user.
     * @param   num_generations: number of generations to create.
     * @return  SuccessResponse or ErrorResponse with an explanation message.
     */
    public Response fillUserTree(String user_name, int num_generations){
        if(user_name == null || num_generations < 0){
            System.out.println(LocalTime.now() + " FillService.fillUserTree(): Error: user_name null or num_generation < 0.");
            return new ErrorResponse("Wrong information");
        }



        
        return null;
    }
    
    /**
     * fillUserTree helper.
     * Recursively generate random family tree.
     * 
     * @param   child: person who's parents will be generated and then attached to.
     * @param   num_generations: number of generations to generate.
     * @param   current_generation: number giving the generation it the method is creating.
     */
    private void generateTree(Person child, int num_generations, int current_generation){
        
    }
    
}