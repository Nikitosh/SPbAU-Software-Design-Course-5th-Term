package com.nikitosh.spbau.model;

import com.nikitosh.spbau.proto.*;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.*;

import java.util.*;
import java.util.function.Consumer;

/**
 * Class which provides ability to communicate with another user (send and receive messages)
 * and which is used for interaction between Model and UI.
 */

public class Controller {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);
    private static final int DELAY = 1000;
    private static final int TIMEOUT = 3000;

    private StreamObserver<Message> outputStreamObserver;
    private StreamObserver<TypingNotification> typingNotificationStreamObserver;

    private String userName;
    private long userTypingTime;

    public Controller() {
        /**
         * Timer task for updating typing notification info.
         */
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (System.currentTimeMillis() > userTypingTime + TIMEOUT) {
                    userName = "";
                }
                onReceiveTypingNotification.accept(userName);
            }
        }, DELAY, TIMEOUT);
    }

    /**
     * Callback for receiving message.
     */
    private Consumer<ChatMessage> onReceiveMessage = (ChatMessage message) -> {};
    /**
     * Callback for receiving message.
     */
    private Consumer<String> onReceiveTypingNotification = (String name) -> {};
    /**
     * Callback for connecting to server.
     */
    private Runnable onConnectToServer = () -> {};

    /**
     * Receives given message.
     *
     * @param message received message.
     */
    public void receiveMessage(ChatMessage message) {
        LOGGER.info("Message was received");
        onReceiveMessage.accept(message);
    }

    /**
     * Receives given typing notification.
     *
     * @param name name of sender.
     */
    public void receiveTypingNotification(String name) {
        LOGGER.info("Typing Notification was received");
        userName = name;
        userTypingTime = System.currentTimeMillis();
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

    /**
     * Tries to send typing notification to another user.
     *
     * @param name name of current user.
     * @return if notification was sent or not.
     */
    public boolean sendTypingNotification(String name) {
        if (typingNotificationStreamObserver == null) {
            return false;
        }
        typingNotificationStreamObserver.onNext(TypingNotification.newBuilder().setName(name).build());
        return true;
    }

    public void setOnReceiveMessage(Consumer<ChatMessage> onReceiveMessage) {
        this.onReceiveMessage = onReceiveMessage;
    }

    public void setOnReceiveTypingNotification(Consumer<String> onReceiveTypingNotification) {
        this.onReceiveTypingNotification = onReceiveTypingNotification;
    }

    public void setOnConnectToServer(Runnable onConnectToServer) {
        this.onConnectToServer = onConnectToServer;
    }

    public void runOnConnectToServer() {
        onConnectToServer.run();
    }

    public void setOutputStreamObserver(StreamObserver<Message> outputStreamObserver) {
        this.outputStreamObserver = outputStreamObserver;
    }

    public void setTypingNotificationStreamObserver(
            StreamObserver<TypingNotification> typingNotificationStreamObserver) {
        this.typingNotificationStreamObserver = typingNotificationStreamObserver;
    }
}
