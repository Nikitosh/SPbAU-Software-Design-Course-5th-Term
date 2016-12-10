package com.nikitosh.spbau;

import com.nikitosh.spbau.model.*;
import org.junit.*;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class ChatTest {
    public static final int SERVER_PORT = 12345;
    public static final int DELAY = 1000;
    public static final byte[] LOCALHOST = new byte[]{127, 0, 0, 1};

    @Test
    public void testConnect() throws InterruptedException {
        Controller serverController = new Controller();
        ChatServer server = createStartedServer(serverController);

        Controller clientController = new Controller();
        Runnable onConnectToServerCallback = mock(Runnable.class);
        clientController.setOnConnectToServer(onConnectToServerCallback);
        ChatClient client = createConnectedClient(clientController);

        client.disconnect();
        server.stop();

        verify(onConnectToServerCallback).run();
    }

    @Test
    public void testSendMessage() throws InterruptedException {
        Controller serverController = new Controller();
        ChatServer server = createStartedServer(serverController);

        Controller clientController = new Controller();
        ChatClient client = createConnectedClient(clientController);

        final List<ChatMessage> serverMessages = new ArrayList<>();
        serverController.setOnReceiveMessage(serverMessages::add);
        final List<ChatMessage> clientMessages = new ArrayList<>();
        clientController.setOnReceiveMessage(clientMessages::add);

        ChatMessage serverMessage1 = new ChatMessage("Username1", "message1");
        ChatMessage clientMessage1 = new ChatMessage("Username2", "message2");
        ChatMessage clientMessage2 = new ChatMessage("Username2", "message3");
        serverController.sendMessage(serverMessage1);
        clientController.sendMessage(clientMessage1);
        clientController.sendMessage(clientMessage2);

        client.disconnect();
        server.stop();

        assertEquals(serverMessages, Arrays.asList(clientMessage1, clientMessage2));
        assertEquals(clientMessages, Arrays.asList(serverMessage1));
    }

    private ChatServer createStartedServer(Controller controller) throws InterruptedException {
        ChatServer server = new ChatServerImpl(controller, SERVER_PORT);
        new Thread(server::start).start();
        Thread.sleep(DELAY);
        return server;
    }

    private ChatClient createConnectedClient(Controller controller) throws InterruptedException {
        ChatClient client = new ChatClientImpl(controller);
        new Thread(() -> {
            client.connect(LOCALHOST, SERVER_PORT);
        }).start();
        Thread.sleep(DELAY);
        return client;
    }
}
