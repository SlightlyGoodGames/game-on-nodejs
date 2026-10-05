package net.gameonline.server.lobby;

import org.java_websocket.WebSocket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class NoughtsLobby extends Lobby{
    private final ArrayList<Byte> board = new ArrayList<>(9);

    public NoughtsLobby(Lobby l){
        super(l);
        Collections.fill(board,(byte)0);
    }

    @Override
    public void handleClientMessage(WebSocket client, Map<String,String> message){

    }
}