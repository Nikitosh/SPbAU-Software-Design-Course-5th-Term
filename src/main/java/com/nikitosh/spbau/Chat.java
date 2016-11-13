package com.nikitosh.spbau;

import com.nikitosh.spbau.ui.*;

/**
 * Main application class.
 */

public final class Chat {
    private Chat() {}

    /**
     * Creates new frame for mode choosing.
     * @param args
     */
    public static void main(String[] args) {
        ChooseModeFrame chooseModeFrame = new ChooseModeFrame();
        chooseModeFrame.setVisible(true);
    }

}
