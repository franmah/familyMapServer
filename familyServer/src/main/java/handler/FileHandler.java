package handler;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.time.LocalTime;
import java.lang.*;

import com.sun.jndi.toolkit.url.Uri;
import com.sun.net.httpserver.*;


public class FileHandler implements HttpHandler{

    public FileHandler(){}

    private final String WEBSITE_PATH = "C:\\Users\\Francois\\AndroidStudioProjects\\fma\\familyServer\\src\\web";

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        boolean success = false;

        System.out.println(LocalTime.now() + " FileHandler: context \"/\" called ");

        try{
            if(exchange.getRequestMethod().toLowerCase().equals("get")){

                // Get the path of the url
                String path = exchange.getRequestURI().getPath();
                System.out.println(LocalTime.now() + " FileHandler: URI path: " + path);

                if("/".equals(path)){
                    path = "\\index.html";
                }
                path.replace('/', '\\');
                path = WEBSITE_PATH + path;

                System.out.println(LocalTime.now() + " FileHandler: final path: " + path);

                File file_request = new File(path);
                if(file_request.exists()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream http_response = exchange.getResponseBody();
                    Files.copy(file_request.toPath(), http_response);

                }

            }
        }
        catch (Exception e){
            System.out.println(LocalTime.now() + " FileHandler: Error: " + e.toString());
            e.printStackTrace();

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }
    }
}
