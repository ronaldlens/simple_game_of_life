package org.rfl.life;

import javax.swing.*;
import java.awt.*;

/**
 * Provides a frame and adds a GameEngine in it. Sets the window to a fixed size
 */
public class Window extends Canvas {

    /**
     * Creates a new Window
     * @param width The width of the window
     * @param height The height of the window
     * @param title The title in the titlebar of the window
     * @param gameEngine The engine used to run the game
     *
     * @see GameEngine
     */
    public Window(int width, int height, String title, GameEngine gameEngine) {
        // create the frame
        JFrame frame = new JFrame(title);

        // set the size and don't allow it to be changed
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setResizable(false);

        // place it not related to anything else
        frame.setLocationRelativeTo(null);

        // when the close ball is clicked, exit the app
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // add the gameEngine canvas
        frame.add(gameEngine);

        // show it
        frame.setVisible(true);

        // start the actual game engine
        gameEngine.start();

    }

}
