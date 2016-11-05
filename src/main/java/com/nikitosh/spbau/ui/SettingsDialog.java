package com.nikitosh.spbau.ui;

import com.nikitosh.spbau.model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.net.*;

public class SettingsDialog extends JDialog {
    public SettingsDialog(Settings settings) {
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameTextField = new JTextField(Settings.getInstance().getName());
        JLabel serverIpLabel = new JLabel("Server IP:");
        JTextField serverIpTextField;
        try {
            serverIpTextField = new JTextField(
                    InetAddress.getByAddress(Settings.getInstance().getServerIp()).getHostAddress());
        } catch (UnknownHostException exception) {
            return;
        }
        JLabel portLabel = new JLabel("Port:");
        JTextField portTextField = new JTextField(String.valueOf(Settings.getInstance().getPort()));
        JButton okButton = new JButton("Ok");
        okButton.addActionListener((ActionEvent actionEvent) -> {
            try {
                settings.setServerIp(InetAddress.getByName(serverIpTextField.getText()).getAddress());
                settings.setName(nameTextField.getText());
                settings.setPort(Integer.parseInt(portTextField.getText()));
                dispose();
            } catch (UnknownHostException exception) {
                JOptionPane.showMessageDialog(this, "Wrong IP address", "Warning", JOptionPane.WARNING_MESSAGE);
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(this, "Wrong port", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(getLabelTextFieldBox(nameLabel, nameTextField));
        verticalBox.add(getLabelTextFieldBox(serverIpLabel, serverIpTextField));
        verticalBox.add(getLabelTextFieldBox(portLabel, portTextField));
        verticalBox.add(okButton);
        add(verticalBox);
        pack();
    }

    private Box getLabelTextFieldBox(JLabel label, JTextField textField) {
        Box box = Box.createHorizontalBox();
        box.add(label);
        box.add(textField);
        return box;
    }
}
