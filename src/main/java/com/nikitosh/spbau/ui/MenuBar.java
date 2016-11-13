package com.nikitosh.spbau.ui;

import com.nikitosh.spbau.model.*;

import javax.swing.*;
import java.awt.event.*;

/**
 * Menu bar which is used in two other frames.
 * Allows to change settings.
 */

public class MenuBar extends JMenuBar {
    MenuBar() {
        super();
        JMenu settingsMenu = new JMenu("Settings");
        JMenuItem settingsItem = new JMenuItem("Change Settings");
        settingsItem.addActionListener((ActionEvent actionEvent) ->
                new SettingsDialog(Settings.getInstance()).setVisible(true));
        settingsMenu.add(settingsItem);
        add(settingsMenu);
    }
}
