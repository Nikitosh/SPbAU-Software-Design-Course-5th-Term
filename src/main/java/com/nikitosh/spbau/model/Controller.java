package com.nikitosh.spbau.model;

import com.nikitosh.spbau.proto.Message;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.*;

import java.util.function.Consumer;

/**
 * Class which provides ability to communicate with another user (send and receive messages)
 * and which is used for interaction between Model and UI.
 */

public class Controller {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    private StreamObserver<Message> outputStreamObserver;

    /**
     * Callback for receiving message.
     */
    private Consumer<ChatMessage> onReceiveMessage = (ChatMessage message) -> {};
    /**
     * Callback for connecting to server.
     */
    private Runnable onConnectToServer = () -> {};
    /**
     * Callback for client connected.
     */
    private Runnable onClientConnected = () -> {};

    public ChatMessage receiveMessage(ChatMessage message) {
        LOGGER.info("ChatMessage was received");
        onReceiveMessage.accept(message);
        return message;
    }

    /**
     * Tries to send message to another user.
     *
     * @param message message should be sent.
     * @return if message was sent or not.
     */
    public boolean sendMessage(ChatMessage message) {
        if (outputStreamObserver == null) {
            return false;
        }
        outputStreamObserver.onNext(Message.newBuilder().setName(message.getName()).setText(message.getText()).build());
        return true;
    }

    public void setOnReceiveMessage(Consumer<ChatMessage> onReceiveMessage) {
        this.onReceiveMessage = onReceiveMessage;
    }

    public void setOnConnectToServer(Runnable onConnectToServer) {
        this.onConnectToServer = onConnectToServer;
    }

    public void runOnConnectToServer() {
        onConnectToServer.run();
    }

    public void setOutputStreamObserver(StreamObserver<Message> streamObserver) {
        this.outputStreamObserver = streamObserver;
    }
}
