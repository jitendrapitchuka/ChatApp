package com.jitendra.chatApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat")
                .setAllowedOrigins("http://localhost:3000")
                .withSockJS();
        //This means that clients (usually web browsers or other WebSocket-enabled applications) will be able to connect to this URL endpoint using WebSocket.
        //For example, a client can connect to the server using ws://<server-domain>/chat
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
        /*
        The enableSimpleBroker("/topic") method registers a simple in-memory message
         broker that will handle publish-subscribe messaging (i.e., messages are broadcast to all
         clients subscribed to a specific destination.*/
        /*
        registry.setApplicationDestinationPrefixes("/app")
        This method sets a prefix for application-specific destinations,
        which are typically used for point-to-point messaging (i.e., sending messages from the client to the server).
         */
    }
}
