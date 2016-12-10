package com.nikitosh.spbau.model;

import com.nikitosh.spbau.proto.*;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.*;

import java.io.IOException;

public class ChatServerImpl implements ChatServer {
    private static final Logger LOGGER = LogManager.getLogger(ChatServerImpl.class);

    private Server server;
    private Controller controller;

    /**
     * Creates new server.
     *
     * @param controller controller through which communication with client happens.
     * @param port port for creating server socket.
     */
    public ChatServerImpl(Controller controller, int port) {
        this.controller = controller;
        server = ServerBuilder.forPort(port).addService(new ChatService()).build();
    }

    @Override
    public void start() {
        try {
            server.start();
            LOGGER.info("Server started");
            server.awaitTermination();
        } catch (InterruptedException | IOException exception) {
            LOGGER.error("Exception during receiving messages: " + exception.getMessage());
        }
    }

    @Override
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private class ChatService extends ChatGrpc.ChatImplBase {
        @Override
        public StreamObserver<Message> chat(StreamObserver<Message> responseObserver) {
            return new StreamObserver<Message>() {
                @Override
                public void onNext(Message message) {
                    ChatServerImpl.this.controller.receiveMessage(
                            new ChatMessage(message.getName(), message.getText()));
                    ChatServerImpl.this.controller.setOutputStreamObserver(responseObserver);
                }

                @Override
                public void onError(Throwable throwable) {
                    LOGGER.warn("Exception in ChatService: " + throwable.getMessage());
                }

                @Override
                public void onCompleted() {
                    responseObserver.onCompleted();
                }
            };
        }

        @Override
        public StreamObserver<TypingNotification> typing(StreamObserver<TypingNotification> responseObserver) {
            return new StreamObserver<TypingNotification>() {
                @Override
                public void onNext(TypingNotification typingNotification) {
                    ChatServerImpl.this.controller.receiveTypingNotification(typingNotification.getName());
                    ChatServerImpl.this.controller.setTypingNotificationStreamObserver(responseObserver);
                }

                @Override
                public void onError(Throwable throwable) {
                    LOGGER.warn("Exception in ChatService: " + throwable.getMessage());
                }

                @Override
                public void onCompleted() {
                    responseObserver.onCompleted();
                }
            };
        }
    }
}
