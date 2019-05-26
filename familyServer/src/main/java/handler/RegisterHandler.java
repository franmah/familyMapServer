package handler;

import java.io.*;
import java.net.*;
import java.time.LocalTime;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;

import request.RegisterRequest;
import response.Response;
import services.RegisterService;


public class RegisterHandler implements HttpHandler {

    public RegisterHandler(){}

    @Override
    public void handle(HttpExchange exchange) throws IOException{

        boolean success = false;

        System.out.println(LocalTime.now() + " RegisterHandler: context \"/user/register/\" called ");

        try{
            if(exchange.getRequestMethod().toLowerCase().equals("post")) {
                // Get inputStream from request body
                InputStream request_body = exchange.getRequestBody();
                String data_json = readInputStream(request_body);
                //System.out.println(LocalTime.now() + " RegisterHandler: Json string retrieved: " + data_json);

                // Process JSON
                Gson gson = new Gson();
                RegisterRequest register_request = gson.fromJson(data_json, RegisterRequest.class);
                //System.out.println(LocalTime.now() + " Json request received: " + register_request.toString());

                // Proceed to RegisterService
                System.out.println(LocalTime.now() + " RegisterHandler: calling RegisterService...");
                RegisterService register_service = new RegisterService();
                Response response = register_service.registerNewUser(register_request);

                // Turn response into Json
                String response_json = gson.toJson(response);

                // Return response
                System.out.println(LocalTime.now() + " RegisterHandler: sending response...");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                OutputStream response_body = exchange.getResponseBody();
                writeToOutputStream(response_json, response_body);
                response_body.close();

                success = true;
                System.out.println(LocalTime.now() + " RegisterHandler: response successfully sent!");
            }

            if(!success){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (Exception e){
            System.out.println(LocalTime.now() + " FileHandler: Error: " + e.toString());
            e.printStackTrace();

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }

    }

    private String readInputStream(InputStream is) throws  IOException{
        StringBuilder final_string = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while((len = reader.read(buf)) > 0){
            final_string.append(buf, 0, len);
        }
        return final_string.toString();
    }

    private void writeToOutputStream(String str, OutputStream os) throws IOException{
        OutputStreamWriter writer = new OutputStreamWriter(os);
        writer.write(str);
        writer.flush();
    }

}
