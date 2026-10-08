package net.gameonline.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

public class GameServer extends WebSocketServer{
    public GameServer(int port){
        super(new InetSocketAddress("0.0.0.0",port));
    }

    @Override
    public void onOpen(WebSocket client,ClientHandshake handshake){
        Main.handleClientJoin(client);
        Main.findClientLobby(client).handleClientJoin(client);
    }

    @Override
    public void onClose(WebSocket client,int code,String reason,boolean remote){
        Main.findClientLobby(client).handleClientDisconnect(client);
        Main.handleClientDisconnect(client);
    }

    @Override
    public void onMessage(WebSocket client,String message){
        System.out.println("Client sent message " + message);
        Main.findClientLobby(client).handleClientMessage(client,Main.splitArgs(message));
    }

    @Override
    public void onError(WebSocket client,Exception exception){
        System.out.println("Client error: " + client.getRemoteSocketAddress() + "\nError: " + exception.toString());
    }

    @Override
    public void onStart(){
        System.out.println("Server started!");
    }
}