package com.nikitosh.spbau.ui;

import com.nikitosh.spbau.model.*;

import javax.swing.*;
import java.awt.event.*;

public class MenuBar extends JMenuBar {
    MenuBar() {
        super();

        JMenu settingsMenu = new JMenu("Settings");
        JMenuItem settingsItem = new JMenuItem("Change Settings");
        settingsItem.addActionListener((ActionEvent actionEvent) -> changeSettings());
        settingsMenu.add(settingsItem);
        add(settingsMenu);
    }

    private void changeSettings() {
        new SettingsDialog(Settings.getInstance()).setVisible(true);
        repaint();
    }
}
