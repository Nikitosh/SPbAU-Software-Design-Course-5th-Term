package com.nikitosh.spbau.ui;

import com.nikitosh.spbau.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChooseModeFrame extends JFrame {
    private static final String FRAME_NAME = "Choose mode";
    private static final int FRAME_WIDTH = 320;
    private static final int FRAME_HEIGHT = 240;

    public ChooseModeFrame() {
        super(FRAME_NAME);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setJMenuBar(new MenuBar());
        JPanel panel = buildChooseButtonsPanel();
        getContentPane().add(panel);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
    }

    private JPanel buildChooseButtonsPanel() {
        JPanel panel = new JPanel();
        JButton serverModeButton = new JButton("Server");
        serverModeButton.addActionListener((ActionEvent actionEvent) -> {
            Controller controller = new Controller();
            Server server = new ServerImpl(controller, Settings.getInstance().getServerPort());
            new ChatFrame(controller, server::stop, server::start).setVisible(true);
        });
        JButton clientModeButton = new JButton("Client");
        clientModeButton.addActionListener((ActionEvent actionEvent) -> {
            Controller controller = new Controller();
            Client client = new ClientImpl(controller);
            new ChatFrame(controller, client::disconnect, () -> {
                client.connect(Settings.getInstance().getServerIp(), Settings.getInstance().getPortToConnect());
            }).setVisible(true);
        });
        panel.setLayout(new GridLayout(0, 1));
        panel.add(serverModeButton);
        panel.add(clientModeButton);
        return panel;
    }
}
