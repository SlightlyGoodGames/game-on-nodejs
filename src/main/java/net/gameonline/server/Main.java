package net.gameonline.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main{
    private static final int PORT = Integer.parseInt(System.getenv().getOrDefault("PORT","8080"));
    private static GameServer SERVER;
    private static final Object lock = new Object();

    public static void main(String[] args){
        System.out.println("Started successfully!");

        ExecutorService threadPool = Executors.newFixedThreadPool(16);

        SERVER = new GameServer(PORT);

        SERVER.start();

        try{
            synchronized (lock){
                while (true){
                    lock.wait();
                }
            }
        } catch(InterruptedException e){
            System.out.println("idk bro i dunno why this happened.");
            e.printStackTrace();
            System.out.println("good luck haha");
        }
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