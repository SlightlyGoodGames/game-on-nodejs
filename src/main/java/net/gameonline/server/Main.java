package net.gameonline.server;

import org.java_websocket.WebSocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Main{
    private static final int PORT = Integer.parseInt(System.getenv().getOrDefault("PORT","8080"));
    private static GameServer SERVER;
    private static final Object lock = new Object();
    private static final ArrayList<WebSocket> connectedPlayers = new ArrayList<>();
    private static final ArrayList<String> chatLog = new ArrayList<>();

    public static void main(String[] args){
        System.out.println("Started successfully!");

        SERVER = new GameServer(PORT);

        SERVER.start();

        System.out.println(splitArgs("tictac a:1 b:2").get("a"));
        System.out.println(splitArgs("tictac \"a:1\" \"b:2\"").get("command"));

        try{
            synchronized(lock){
                while(true){
                    lock.wait();
                }
            }
        } catch(InterruptedException e){
            System.out.println("idk bro i dunno why this happened.");
            e.printStackTrace();
            System.out.println("good luck haha");
        }
    }

    public static void handleNewClient(WebSocket client){
        client.send("Welcome");
        connectedPlayers.add(client);
    }

    public static void handleClientMessage(WebSocket client,Map<String,String> message){
        switch(message.get("command")){
            case "chat-message":{
                chatLog.add(message.get("message"));
                notifyNewMessage(message.get("message"),"Other");
                break;
            }
        }
    }

    public static void handleClientDisconnect(WebSocket client){
        connectedPlayers.remove(client);
    }

    public static Map<String,String> splitArgs(String message){
        try {
            ArrayList<String> allArgs = new ArrayList<>();

            Pattern regexPattern = Pattern.compile("(?<=\")(?:[^\"\\\\]|\\\\.)*(?=\")");
            Matcher regexMatcher = regexPattern.matcher(message);
            while (regexMatcher.find()) {
                allArgs.add(regexMatcher.group());
            }

            Map<String, String> toReturn = allArgs.stream().limit(allArgs.size() - 2).map(s -> s.split(":", 2))
                    .collect(Collectors.toMap(tokenisedVer -> tokenisedVer[0], tokenisedVer -> tokenisedVer[1]));
            toReturn.put("command", allArgs.getFirst());

            return toReturn;
        } catch (Exception e){
            System.out.println("Client has returned a malformed request: "+message);
            Map<String,String> toReturn = new HashMap<>();
            toReturn.put("malformed","true");

            return toReturn;
        }
    }

    public static void notifyNewMessage(String message,String clientName){
        for(WebSocket client : connectedPlayers){
            client.send(String.format("chat-message message:%s client:%s",message,clientName));
        }
    }
}