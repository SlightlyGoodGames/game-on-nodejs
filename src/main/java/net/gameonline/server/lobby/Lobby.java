package net.gameonline.server.lobby;

import org.java_websocket.WebSocket;

import java.util.ArrayList;
import java.util.Map;

public class Lobby{
    protected ArrayList<WebSocket> allPlayers = new ArrayList<>();

    public Lobby(){}
    public Lobby(Lobby l){
        this.allPlayers = new ArrayList<>(l.getAllPlayers());
    }

    public void add(WebSocket client){
        allPlayers.add(client);
    }
    public void add(ArrayList<WebSocket> clients){
        allPlayers.addAll(clients);
    }
    public Lobby getGame(String game){
        return switch(game){
            case "NOUGHTS" -> new NoughtsLobby(this);
            default -> this;
        };
    }
    public boolean containsPlayer(WebSocket client){
        return allPlayers.contains(client);
    }
    public ArrayList<WebSocket> getAllPlayers(){
        return allPlayers;
    }

    public void handleClientMessage(WebSocket client, Map<String,String> message){}
    public void handleClientJoin(WebSocket client){
        allPlayers.add(client);
    }
    public void handleClientDisconnect(WebSocket client){
        allPlayers.remove(client);
    }
}