package com.nikitosh.spbau.ui;

import com.nikitosh.spbau.model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.net.*;

public class SettingsDialog extends JDialog {
    public SettingsDialog(Settings settings) {
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameTextField = new JTextField("Name");
        JLabel serverIpLabel = new JLabel("Server IP:");
        JTextField serverIpTextField = new JTextField("0.0.0.0");
        JButton okButton = new JButton("Ok");
        okButton.addActionListener((ActionEvent actionEvent) -> {
            try {
                settings.setIp(InetAddress.getByName(serverIpTextField.getText()).getAddress());
                settings.setName(nameTextField.getText());
                dispose();
            } catch (UnknownHostException e) {
                JOptionPane.showMessageDialog(this, "Wrong IP address", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(getLabelTextFieldBox(nameLabel, nameTextField));
        verticalBox.add(getLabelTextFieldBox(serverIpLabel, serverIpTextField));
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
