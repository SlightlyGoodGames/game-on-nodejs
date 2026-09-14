package net.gameonline.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main{
    private static final int PORT = Integer.parseInt(System.getenv("PORT"));
    private static GameServer SERVER;

    public static void main(String[] args){
        ExecutorService threadPool = Executors.newFixedThreadPool(16);

        SERVER = new GameServer(PORT);
    }

    public static void handleNewClient(Socket client){
        try{
            InputStream infoIn = client.getInputStream();
            OutputStream infoOut = client.getOutputStream();
        } catch (IOException e){
            System.exit(1);
        }
    }

    public static void handleClientMessage(Socket client,String message){

    }
}