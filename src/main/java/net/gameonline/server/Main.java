package net.gameonline.server;

import net.gameonline.server.lobby.*;
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
    private static final Map<WebSocket,Lobby> connectedPlayers = new HashMap<>();
    private static final ArrayList<Lobby> allLobbies = new ArrayList<>();
    private static final ArrayList<String> chatLog = new ArrayList<>();
    private static final Lobby waitingLobby = new Lobby();

    public static void main(String[] args){
        allLobbies.add(new ChatLobby());

        System.out.println("Started successfully!");

        SERVER = new GameServer(PORT);

        SERVER.start();

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

    public static Lobby findClientLobby(WebSocket client){
        return connectedPlayers.get(client);
    }

    public static void handleClientJoin(WebSocket client){
        connectedPlayers.put(client,allLobbies.getFirst());
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

            allArgs.removeIf(s -> s.equals(" "));

            Map<String, String> toReturn = allArgs.stream().skip(1).map(s -> s.split(":", 2))
                    .collect(Collectors.toMap(tokenisedVer -> tokenisedVer[0], tokenisedVer -> tokenisedVer[1]));
            toReturn.put("command", allArgs.getFirst());

            return toReturn;
        } catch (Exception e){
            System.out.println("Formatting error");
            e.printStackTrace();
            System.out.println("Client has returned a malformed request: "+message);
            Map<String,String> toReturn = new HashMap<>();
            toReturn.put("malformed","true");

            return toReturn;
        }
    }
}