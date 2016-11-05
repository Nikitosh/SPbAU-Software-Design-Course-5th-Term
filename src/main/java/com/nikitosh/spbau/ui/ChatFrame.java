package com.nikitosh.spbau.ui;

import com.nikitosh.spbau.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatFrame extends JFrame {
    private static final String FRAME_NAME = "Chat";
    private static final int FRAME_WIDTH = 640;
    private static final int FRAME_HEIGHT = 480;

    private Conversation conversation;

    public ChatFrame(Conversation conversation) {
        super(FRAME_NAME);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setJMenuBar(new MenuBar());
        buildChatInterface();
    }

    public ChatFrame(Conversation conversation, Runnable onWindowClosed) {
        this(conversation);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                onWindowClosed.run();
            }
        });
    }

    private void buildChatInterface() {
        JTextArea chatHistoryTextArea = new JTextArea();
        chatHistoryTextArea.setEditable(false);
        chatHistoryTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JTextArea messageTextArea = new JTextArea();
        messageTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener((ActionEvent actionEvent) -> {

        });

        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.add(new JScrollPane(messageTextArea));
        horizontalBox.add(sendButton);
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(new JScrollPane(chatHistoryTextArea));
        verticalBox.add(horizontalBox);
        add(verticalBox);
    }
}
