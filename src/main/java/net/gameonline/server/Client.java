package net.gameonline.server;

import org.java_websocket.WebSocket;

public record Client(WebSocket webSocket){}