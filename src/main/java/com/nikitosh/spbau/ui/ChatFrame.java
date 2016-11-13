package com.nikitosh.spbau.ui;

import com.nikitosh.spbau.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatFrame extends JFrame {
    private static final String FRAME_NAME = "Chat";
    private static final int CHAT_HISTORY_ROW_NUMBER = 30;
    private static final int CHAT_HISTORY_COLUMN_NUMBER = 50;
    private static final int MESSAGE_TEXT_ROW_NUMBER = 6;

    private JTextArea chatHistoryTextArea;
    private JTextArea messageTextArea;
    private JButton sendButton;

    /**
     * Creates new frame with chat.
     *
     * @param controller controller through which all interaction with model happens.
     * @param onWindowClosed callback on window closed.
     * @param onFrameCreated callback on frame created.
     */
    public ChatFrame(Controller controller, Runnable onWindowClosed, Runnable onFrameCreated) {
        super(FRAME_NAME);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setJMenuBar(new MenuBar());
        add(buildChatInterfacePanel());
        pack();
        setLocationRelativeTo(null); //set JFrame to appear in center
        setCallbacks(controller);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                onWindowClosed.run();
            }
        });
        new Thread(onFrameCreated).start();
    }

    /**
     * Builds main interface.
     *
     * @return JPanel with interface
     */
    private JPanel buildChatInterfacePanel() {
        JPanel panel = new JPanel();
        chatHistoryTextArea = new JTextArea(CHAT_HISTORY_ROW_NUMBER, CHAT_HISTORY_COLUMN_NUMBER);
        chatHistoryTextArea.setEditable(false);
        chatHistoryTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        messageTextArea = new JTextArea(MESSAGE_TEXT_ROW_NUMBER, CHAT_HISTORY_COLUMN_NUMBER);
        messageTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        sendButton = new JButton("Send");

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.add(new JScrollPane(messageTextArea));
        horizontalBox.add(sendButton);
        panel.add(new JScrollPane(chatHistoryTextArea));
        panel.add(horizontalBox);
        return panel;
    }

    private void setCallbacks(Controller controller) {
        controller.setOnReceiveMessage(this::appendMessageToChatHistory);
        controller.setOnConnectToServer(() -> {
            chatHistoryTextArea.append("You connected to other user\n");
        });
        controller.setOnClientConnected(() -> {
            chatHistoryTextArea.append("New user connected to you\n");
        });
        sendButton.addActionListener((ActionEvent actionEvent) -> {
            if (messageTextArea.getText().isEmpty()) { //doesn't allow to send empty messages
                return;
            }
            Message message = new Message(Settings.getInstance().getName(), messageTextArea.getText() + "\n");
            if (controller.sendMessage(message)) { //message was sent successfully
                appendMessageToChatHistory(message);
                messageTextArea.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "You're not connected to anybody", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void appendMessageToChatHistory(Message message) {
        chatHistoryTextArea.append(message.getName() + ": " + message.getText());
    }
}
