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
    public void onOpen(WebSocket client, ClientHandshake handshake){
        System.out.println("New client connected: " + client.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket client,int code,String reason,boolean remote){
        System.out.println("Client disconnected: " + client.getRemoteSocketAddress() + "\nFor reason: " + reason);
    }

    @Override
    public void onMessage(WebSocket client,String message){
        System.out.println("Client message: " + client.getRemoteSocketAddress() + "\nMessage: " + message);
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