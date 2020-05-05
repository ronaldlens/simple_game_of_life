package org.rfl.life;

import java.awt.*;
import java.util.Random;

public class Game {

    private final boolean[][] board;

    /**
     * Main method, creates the game
     * @param args command line arguments (ignored)
     */
    public static void main(String[] args) {
        new Game();
    }

    /**
     * Constructor for Game
     */
    public Game() {
        // create the board
        board = new boolean[80][60];
        Random random = new Random();

        // iterate over all cells
        for(int y=0; y<60; y++) {
            for(int x=0; x<80; x++) {
                // there is a 45% chance a cell is alive
                if (random.nextFloat() < 0.45) {
                    board[x][y] = true;
                }
            }
        }

        // initiate the engine
        new GameEngine("Game of Life", this);


    }

    /**
     * draw the scene, it has already been cleared by the @see GameEngine#render
     */
    public void render(Graphics g) {
        // we draw the live cells in white
        g.setColor(Color.white);

        // iterate over all cells
        for(int y=0; y<60; y++) {
            for(int x=0; x<80; x++) {
                // if the cell is alive (true), draw an 8x8 square
                if (board[x][y]) {
                    g.fillRect(x*8, y*8, 8, 8);
                }
            }
        }
    }

    /**
     * Check whether a cell is alive, does wrap around
     * @param x x coordinate
     * @param y y coordinate
     * @return true is cell is alive
     */
    boolean isAlive(int x, int y) {
        if (x == -1) x = 79;
        if (x == 80) x =  0;
        if (y == -1) y = 59;
        if (y == 60) y =  0;
        return board[x][y];
    }

    /**
     * Counts the live neighbors of a cell
     * @param x x coordinate
     * @param y y coordinate
     * @return int containing the number of neighbors
     */
    int getNeighbors(int x, int y) {
        int n = 0;
        if (isAlive(x-1,y-1)) n++;
        if (isAlive(x,y-1)) n++;
        if (isAlive(x+1,y-1)) n++;
        if (isAlive(x+1, y)) n++;
        if (isAlive(x+1, y+1)) n++;
        if (isAlive(x,y+1)) n++;
        if (isAlive(x-1, y+1)) n++;
        if (isAlive(x-1, y)) n++;
        return n;
    }

    /**
     * advance a generation
     */
    public void tick() {

        // storage for the board containing the new generation
        boolean[][] newBoard = new boolean[80][60];

        // iterate over all cells
        for(int y=0; y<60; y++) {
            for(int x=0; x<80; x++) {

                // get the number of neighbors for this cell
                int neighbors = getNeighbors(x,y);

                // if the cell is alive and has 2 or 3 neighbors, it stays alive, otherwise it dies
                // if the cell is dead but has 3 neighbors, it becomes alive
                if (board[x][y]) {
                    newBoard[x][y] = neighbors == 2 || neighbors == 3;
                } else {
                    newBoard[x][y] = neighbors == 3;
                }
            }
        }

        // iterate over all cells
        // and write the new board to the main board
        for(int y=0; y<60; y++) {
            for (int x = 0; x < 80; x++) {
                board[x][y] = newBoard[x][y];
            }
        }
    }
}
