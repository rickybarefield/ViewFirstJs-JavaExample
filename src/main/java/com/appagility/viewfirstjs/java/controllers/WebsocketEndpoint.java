package com.appagility.viewfirstjs.java.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket")
public class WebsocketEndpoint {

    private static Logger logger = LoggerFactory.getLogger(WebsocketEndpoint.class);

    @OnMessage
    public String onMessage(String message, Session session) {

        System.out.println("onMessage");
        return message;
    }

    @OnOpen
    public void onOpen(Session session) {

        System.out.println("onOpen");
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {

        System.out.println("onClose");
    }
}