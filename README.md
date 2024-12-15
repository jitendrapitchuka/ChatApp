## New Learnings and About the project

### 1. **HTTP Protocol**
- The **HTTP** protocol is a simple request-response communication model between a client and server.

### 2. **WebSocket**
- **WebSocket** is built on top of HTTP and provides a full-duplex communication channel over a single, long-lived connection. This is ideal for real-time applications like messaging, live updates, etc.

### 3. **STOMP (Simple Text-Oriented Messaging Protocol)**
- **STOMP** is a simple and widely used messaging protocol that works over WebSocket. It allows clients to communicate with servers by sending and receiving messages in a simple text format, making it a great option for real-time messaging.

### 4. **SockJS**
- **SockJS** is a JavaScript library that provides a WebSocket-like object in browsers that do not support native WebSocket connections. It offers fallback options, ensuring your application still works on all browsers by using alternative protocols like XHR polling, etc.

---

## Spring Boot WebSocket Setup

In a Spring Boot application, we configure WebSocket messaging using the following annotations and configuration.

### 1. **`@EnableWebSocketMessageBroker`**
- This annotation enables WebSocket message handling, backed by a message broker in your Spring Boot application.
- It sets up the WebSocket infrastructure necessary for the application to send and receive messages.

```java
  
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
```

# Chat Controller in Spring Boot

This is the WebSocket controller that handles sending and receiving messages between clients in a chat room. The controller leverages **STOMP** over WebSocket for real-time communication.

```java
@RestController
public class ChatController {

    @Autowired
    private RoomService roomService;

    /**
     * This method is triggered when a message is sent to the '/app/sendMessage/{roomId}' endpoint.
     * The message is then broadcast to all subscribed clients at '/topic/room/{roomId}'.
     * 
     * @param roomId   The ID of the room where the message will be sent.
     * @param request  The message content to be sent.
     * @return         The processed message to be sent to all room subscribers.
     */
    @MessageMapping("/sendMessage/{roomId}")  // Maps to /app/sendMessage/{roomId}
    @SendTo("/topic/room/{roomId}")  // Broadcasts to the topic /topic/room/{roomId}
    public Message sendMessage(@DestinationVariable String roomId, @RequestBody MessageRequest request) {
        // Sends the message using the roomService
        return roomService.sendMessage(roomId, request);
    }
}
