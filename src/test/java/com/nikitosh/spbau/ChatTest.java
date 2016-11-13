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
        Runnable onClientConnectedCallback = mock(Runnable.class);
        serverController.setOnClientConnected(onClientConnectedCallback);
        Server server = createStartedServer(serverController);

        Controller clientController = new Controller();
        Runnable onConnectToServerCallback = mock(Runnable.class);
        clientController.setOnConnectToServer(onConnectToServerCallback);
        Client client = createConnectedClient(clientController);

        client.disconnect();
        server.stop();

        verify(onClientConnectedCallback).run();
        verify(onConnectToServerCallback).run();
    }

    @Test
    public void testSendMessage() throws InterruptedException {
        Controller serverController = new Controller();
        Server server = createStartedServer(serverController);

        Controller clientController = new Controller();
        Client client = createConnectedClient(clientController);

        final List<Message> serverMessages = new ArrayList<>();
        serverController.setOnReceiveMessage(serverMessages::add);
        final List<Message> clientMessages = new ArrayList<>();
        clientController.setOnReceiveMessage(clientMessages::add);

        Message serverMessage1 = new Message("Username1", "message1");
        Message clientMessage1 = new Message("Username2", "message2");
        Message clientMessage2 = new Message("Username2", "message3");
        serverController.sendMessage(serverMessage1);
        clientController.sendMessage(clientMessage1);
        clientController.sendMessage(clientMessage2);

        client.disconnect();
        server.stop();

        assertEquals(serverMessages, Arrays.asList(clientMessage1, clientMessage2));
        assertEquals(clientMessages, Arrays.asList(serverMessage1));
    }

    private Server createStartedServer(Controller controller) throws InterruptedException {
        Server server = new ServerImpl(controller, SERVER_PORT);
        new Thread(server::start).start();
        Thread.sleep(DELAY);
        return server;
    }

    private Client createConnectedClient(Controller controller) throws InterruptedException {
        Client client = new ClientImpl(controller);
        new Thread(() -> {
            client.connect(LOCALHOST, SERVER_PORT);
        }).start();
        Thread.sleep(DELAY);
        return client;
    }
}
