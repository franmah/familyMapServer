package handler;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UrlParser {

    private int DEFAULT_NUM_GENERATION = 4;

    public UrlParser(){}

    public String[] parseFillUrl(String url_path) throws Exception {

        System.out.println(LocalTime.now() + " UrlParser.parseFillUrl(): URI url_path: " + url_path);

        // Parse the URL (first command is always the name of the service/handler)
        url_path = url_path.substring(1); // Remove the first "/"

        String[] commands = url_path.split("/");
        System.out.println(LocalTime.now() + " UrlParser.parseFillUrl():  commands: " + Arrays.toString(commands));

        if (commands.length != 3) {
            System.out.println(LocalTime.now() + " UrlParser.parseFillUrl: too many arguments");
            throw new Exception("Wrong number of arguments");

        }
        // Check if num_generation is an Int
        try{
            int num_generation = Integer.parseInt(commands[2]);
        }
        catch (Exception e){
            System.out.println(LocalTime.now() + " UrlParser.parseFillUrl: error: " + e.toString());
            e.printStackTrace();
            throw new Exception("second argument (number of generations) should be a number");
        }

        // Return commands[] as a list:
       /* List<String> final_list = new ArrayList<>();
        for(String str : commands){
            final_list.add(str);
        }*/

        return commands;
    }
}
