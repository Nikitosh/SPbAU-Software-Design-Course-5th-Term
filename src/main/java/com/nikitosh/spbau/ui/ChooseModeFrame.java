package com.nikitosh.spbau.ui;

import com.nikitosh.spbau.model.*;

import javax.swing.*;
import java.awt.event.*;

public class ChooseModeFrame extends JFrame {
    private static final String FRAME_NAME = "Choose mode";
    private static final int FRAME_WIDTH = 640;
    private static final int FRAME_HEIGHT = 480;

    public ChooseModeFrame() {
        super(FRAME_NAME);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setJMenuBar(new MenuBar());
        buildChooseButtons();
    }

    private void buildChooseButtons() {
        JButton serverModeButton = new JButton("Server");
        serverModeButton.addActionListener((ActionEvent actionEvent) -> {
            Conversation conversation = new Conversation();
            Server server = new ChatServer(conversation, Settings.getInstance().getPort());
            new ChatFrame(conversation, server::stop).setVisible(true);
        });
        JButton clientModeButton = new JButton("Client");
        clientModeButton.addActionListener((ActionEvent actionEvent) -> {
            Conversation conversation = new Conversation();
            Client client = new ChatClient(conversation);
            new ChatFrame(conversation, client::disconnect).setVisible(true);
        });
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(serverModeButton);
        verticalBox.add(clientModeButton);
        add(verticalBox);
    }
}
