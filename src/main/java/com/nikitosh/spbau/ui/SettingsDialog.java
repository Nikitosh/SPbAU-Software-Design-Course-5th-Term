package com.nikitosh.spbau.ui;

import com.nikitosh.spbau.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.*;

/**
 * Frame for changing settings like name, server's IP and ports.
 */

public class SettingsDialog extends JDialog {
    public SettingsDialog(Settings settings) {
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setHorizontalAlignment(JLabel.RIGHT);
        JTextField nameTextField = new JTextField(settings.getName());

        JLabel serverIpLabel = new JLabel("Server IP:");
        serverIpLabel.setHorizontalAlignment(JLabel.RIGHT);
        JTextField serverIpTextField;
        try {
            serverIpTextField = new JTextField(
                    InetAddress.getByAddress(settings.getServerIp()).getHostAddress());
        } catch (UnknownHostException exception) {
            return;
        }

        JLabel serverPortLabel = new JLabel("Server port:");
        serverPortLabel.setHorizontalAlignment(JLabel.RIGHT);
        JTextField serverPortTextField = new JTextField(String.valueOf(settings.getServerPort()));

        JLabel portToConnectLabel = new JLabel("Port to connect:");
        portToConnectLabel.setHorizontalAlignment(JLabel.RIGHT);
        JTextField portToConnectTextField = new JTextField(String.valueOf(settings.getPortToConnect()));

        JButton okButton = new JButton("Ok");
        okButton.addActionListener((ActionEvent actionEvent) -> {
            try {
                settings.setServerIp(InetAddress.getByName(serverIpTextField.getText()).getAddress());
                settings.setName(nameTextField.getText());
                settings.setServerPort(Integer.parseInt(serverPortTextField.getText()));
                settings.setPortToConnect(Integer.parseInt(portToConnectTextField.getText()));
                dispose();
            } catch (UnknownHostException exception) {
                JOptionPane.showMessageDialog(this, "Wrong IP address", "Warning", JOptionPane.WARNING_MESSAGE);
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(this, "Wrong port", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel labelTextFieldPanel = new JPanel();
        labelTextFieldPanel.setLayout(new GridLayout(0, 2));
        addComponentsToPanel(labelTextFieldPanel,
                nameLabel, nameTextField,
                serverIpLabel, serverIpTextField,
                serverPortLabel, serverPortTextField,
                portToConnectLabel, portToConnectTextField);

        JPanel okButtonPanel = new JPanel();
        okButtonPanel.add(okButton);

        Container pane = getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(labelTextFieldPanel);
        pane.add(okButtonPanel);
        pack();
        setLocationRelativeTo(null); //set JDialog to appear in center
    }

    private void addComponentsToPanel(JPanel panel, Component... components) {
        for (Component component : components) {
            panel.add(component);
        }
    }
}
