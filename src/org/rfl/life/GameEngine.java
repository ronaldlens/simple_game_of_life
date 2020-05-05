package org.rfl.life;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameEngine extends Canvas implements Runnable {


    private Thread thread;
    private boolean running;
    private final Game game;

    /**
     * Creates a new engine
     * @param title String to put in top of the window
     * @param game the actual Game object
     */
    public GameEngine(String title, Game game) {
        new Window(640, 480, title, this);
        this.game = game;
    }

    /**
     * Start the game thread. This starts the actual game and starts the
     * tick method to be called.
     */
    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    /**
     * Stops the game thread. Waits for it to join the main thread and then returns.
     */
    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Runner of the game thread
     */
    @Override
    public void run() {
        // set the timestamp it was last run to now
        long lastNanos = System.nanoTime();

        // we use 5 ticks per second, less ticks means slower running.
        double ticksPerSecond = 5.0;
        double nanosPerTick = 1000000000 / ticksPerSecond;

        // delay to measure if a new tick should be run
        double deltaNanos = 0.0;

        // while we're running
        while (running) {
            // the the current time and calculate the difference divided by nanosPerTick
            // this will tell us if a new tick() should be run
            long nowNanos = System.nanoTime();
            deltaNanos += (nowNanos - lastNanos) / nanosPerTick;
            // set lastNanos to calculate the next delta
            lastNanos = nowNanos;

            // while we have ticks to run
            while(deltaNanos >= 1) {
                // run the tick
                // decrease the delta
                tick();
                deltaNanos--;
            }

            // if we're still running, draw the scene
            if (running) {
                render();
            }
        }
        // stop the thread
        stop();
    }

    /**
     * Draw the scene
     */
    private void render() {
        // get the buffering strategy used
        // if it's not there yet
        // create a triple buffer model and return
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        // get a handle to draw
        Graphics g = bs.getDrawGraphics();

        // fill the whole frame with black (clearing it)
        g.setColor(Color.black);
        g.fillRect(0,0, getWidth(), getHeight());

        // get the game to draw its stuff
        game.render(g);

        // get rid of the handle
        // and show the updated frame
        g.dispose();
        bs.show();
    }

    /**
     * move the game forward 1 tick
     */
    private void tick() {
        // move the game forward 1 tick
        game.tick();
    }


}
