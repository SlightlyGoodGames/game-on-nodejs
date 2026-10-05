package net.gameonline.server.lobby;

import org.java_websocket.WebSocket;

import java.util.Map;

public class ChatLobby extends Lobby{
    @Override
    public void handleClientMessage(WebSocket client,Map<String,String> message){
        if(message.get("command").equals("chat-message")){
            for(WebSocket iClient : allPlayers){
                if(!iClient.equals(client)){
                    iClient.send("\"chat-message\" \"data:" + message.get("data") + "\" \"client:Other\"");
                }
            }
        }
    }
}