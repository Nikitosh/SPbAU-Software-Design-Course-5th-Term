package com.nikitosh.spbau.model;

import com.nikitosh.spbau.proto.*;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.*;

import java.net.*;
import java.util.concurrent.TimeUnit;

public class ChatClientImpl implements ChatClient {
    private static final Logger LOGGER = LogManager.getLogger(ChatClientImpl.class);
    private static final int TIMEOUT = 5;

    private Controller controller;
    private ManagedChannel channel;

    /**
     * Creates new client.
     *
     * @param controller controller through which communication with server happens.
     */
    public ChatClientImpl(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void connect(byte[] ip, int port) {
        try {
            ManagedChannelBuilder channelBuilder = ManagedChannelBuilder.forAddress(
                InetAddress.getByAddress(ip).getHostAddress(), port).usePlaintext(true);
            channel = channelBuilder.build();
            ChatGrpc.ChatStub asyncStub = ChatGrpc.newStub(channel);

            StreamObserver<Message> outputStreamObserver = asyncStub.chat(new StreamObserver<Message>() {
                @Override
                public void onNext(Message message) {
                    controller.receiveMessage(new ChatMessage(message.getName(), message.getText()));
                }

                @Override
                public void onError(Throwable throwable) {
                    LOGGER.warn("Exception in ChatService: " + throwable.getMessage());
                }

                @Override
                public void onCompleted() {}

            });
            controller.setOutputStreamObserver(outputStreamObserver);

            StreamObserver<TypingNotification> typingNotificationStreamObserver = asyncStub.typing(
                    new StreamObserver<TypingNotification>() {
                @Override
                public void onNext(TypingNotification typingNotification) {
                    controller.receiveTypingNotification(typingNotification.getName());
                }

                @Override
                public void onError(Throwable throwable) {
                    LOGGER.warn("Exception in ChatService: " + throwable.getMessage());
                }

                @Override
                public void onCompleted() {}

            });
            controller.setTypingNotificationStreamObserver(typingNotificationStreamObserver);

            controller.runOnConnectToServer();
        } catch (UnknownHostException exception) {
            LOGGER.error("Could not determine server with given ip " + ip + ": " + exception.getMessage());
            return;
        }
        LOGGER.info("Connected to server with ip " + ip);
    }

    @Override
    public void disconnect() {
        if (channel == null) {
            return;
        }
        try {
            channel.shutdown().awaitTermination(TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException exception) {
            LOGGER.warn("Exception during disconnecting from server: " + exception.getMessage());
        }
        LOGGER.info("Disconnected from server");
    }
}
