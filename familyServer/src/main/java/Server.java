import java.io.*;
import java.net.*;
import java.time.LocalTime;

import com.sun.net.httpserver.*;

import handler.ClearHandler;
import handler.EventHandler;
import handler.FileHandler;
import handler.LoadHandler;
import handler.PersonHandler;
import handler.RegisterHandler;

public class Server {

    private static final int MAX_WAITING_CONNECTIONS = 12;

    private HttpServer server;

    public static void main(String[] args){
        String port_number = args[0];
        new Server().run(port_number);

    }

    private void run(String port_number){
        System.out.println(LocalTime.now() + " Server.run(): Initializing HTTP server...");

        try{
            server = HttpServer.create(new InetSocketAddress(Integer.parseInt(port_number)),
                                        MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e){
            System.out.println(LocalTime.now() + " Server.run(): Error while creating server: " + e.toString());
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);

        System.out.println(LocalTime.now() + " Server.run(): server initialized, now creating context(s)...");

        /*** CONTEXT ***/

        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoadHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/fill", new FileHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/event", new EventHandler()); // use getRequestURI
        server.createContext("/person", new PersonHandler());
        server.createContext("/", new FileHandler());

        // Files.Coppy

        System.out.println(LocalTime.now() + " Server.run(): context(s) created, now starting server...");

        server.start();

        System.out.println(LocalTime.now() + " Server.run(): server started");

    }
}
