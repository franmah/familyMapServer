package handler;

import java.io.*;
import java.net.*;
import java.time.LocalTime;
import java.util.Arrays;

import com.sun.net.httpserver.*;


public class FillHandler implements HttpHandler{

    public FillHandler(){}

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        System.out.println(LocalTime.now() + " FillHandler: context \"/fill/...\" called");

        // Get the path of the url
        String path = exchange.getRequestURI().getPath();
        System.out.println(LocalTime.now() + " FileHandler: URI path: " + path);

        // Parse the URL (first command is always the name of the service/handler)
        path = path.substring(1, path.length()); // Remove the first "/"
        String[] commands = path.split("/");
        System.out.println(LocalTime.now() + " FillHandler: commands: " + Arrays.toString(commands));

    }
}
