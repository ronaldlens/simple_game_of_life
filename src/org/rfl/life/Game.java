package org.rfl.life;

import java.awt.*;
import java.util.Random;

public class Game {

    private GameEngine engine;
    int i;
    boolean board[][];

    public static void main(String[] args) {
        Game ame = new Game();
    }

    public Game() {
        board = new boolean[80][60];
        Random random = new Random();
        for(int y=0; y<60; y++) {
            for(int x=0; x<80; x++) {
                if (random.nextFloat() < 0.45) {
                    board[x][y] = true;
                }
            }
        }

        engine = new GameEngine("Game of Life", this);


    }

    public void render(Graphics g) {
        g.setColor(Color.white);
        for(int y=0; y<60; y++) {
            for(int x=0; x<80; x++) {
                if (board[x][y]) {
                    g.fillRect(x*8, y*8, 8, 8);
                }
            }
        }
    }

    boolean isAlive(int x, int y) {
        if (x==-1) x = 79;
        if (x==80) x = 0;
        if (y==-1) y = 59;
        if (y==60) y = 0;
        return board[x][y];
    }

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

    public void tick() {

        boolean newBoard[][] = new boolean[80][60];

        for(int y=0; y<60; y++) {
            for(int x=0; x<80; x++) {

                int neighbors = getNeighbors(x,y);

                if (board[x][y]) {
                    if (neighbors==2 || neighbors==3) {
                        newBoard[x][y] = true;
                    } else {
                        newBoard[x][y] = false;
                    }
                } else {
                    if (neighbors == 3) {
                        newBoard[x][y] = true;
                    } else {
                        newBoard[x][y] = false;
                    }
                }
            }
        }

        for(int y=0; y<60; y++) {
            for (int x = 0; x < 80; x++) {
                board[x][y] = newBoard[x][y];
            }
        }
    }
}
