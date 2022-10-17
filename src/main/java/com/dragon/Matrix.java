package com.dragon;

import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class Matrix {
    private int width;
    private int height;
    private Dragon dragon;
    private Sword sword;
    public static Matrix game;
    private final String[] drawAtMatrix = {"❎", " x ", "\uD83D\uDC32", "\uD83D\uDDE1", "\uD83D\uDC80"};
    private final int DRAGON_BODY =  1;
    private final int DRAGON_HEAD = 2;
    private final int SWORD = 3;
    private final int RIP = 4;
    private final int INITIAL_DELAY = 520;
    private final int BASE_DELAY = 200;
    private final int DELAY_SLEEP = 20;
    private final int LEVEL_15 = 15;

    public static void main(String[] args) {
        Dragon dragon = new Dragon(10, 10);
        game = new Matrix(15, 15, dragon);
        game.dragon.setDirection(DragonDirection.DOWN);

        game.putSwordAtField();
        game.run();
    }

    public Matrix(int width, int height, Dragon dragon) {
        this.width = width;
        this.height = height;
        this.dragon = dragon;
        game = this;
    }

    public Dragon getDragon() {
        return dragon;
    }

    public Sword getSword() {
        return sword;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setDragon(Dragon dragon) {
        this.dragon = dragon;
    }

    public void setSword(Sword sword) {
        this.sword = sword;
    }

    public void run() {
        //Создаем объект "наблюдатель за клавиатурой" и стартуем его.
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        while (dragon.isAlive()) {
            //"наблюдатель" содержит события о нажатии клавиш?
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                //Если равно символу 'q' - выйти из игры.
                if (event.getKeyChar() == 'q') return;

                //Если "стрелка влево" - сдвинуть фигурку влево
                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    dragon.setDirection(DragonDirection.LEFT);
                    //Если "стрелка вправо" - сдвинуть фигурку вправо
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    dragon.setDirection(DragonDirection.RIGHT);
                    //Если "стрелка вверх" - сдвинуть фигурку вверх
                else if (event.getKeyCode() == KeyEvent.VK_UP)
                    dragon.setDirection(DragonDirection.UP);
                    //Если "стрелка вниз" - сдвинуть фигурку вниз
                else if (event.getKeyCode() == KeyEvent.VK_DOWN)
                    dragon.setDirection(DragonDirection.DOWN);
            }

            dragon.move();
            print();
            sleep();
        }

        System.out.println("Game Over!");
    }

    public void print() {
        int[][] field = new int[height][width];
        ArrayList<DragonSection> sections = new ArrayList<>(dragon.getSections());

        for (DragonSection dragonSection : sections) {
            field[dragonSection.getY()][dragonSection.getX()] = DRAGON_BODY;
        }

        field[dragon.getY()][dragon.getX()] = dragon.isAlive() ? DRAGON_HEAD : RIP;
        field[sword.getY()][sword.getX()] = SWORD;

        //Выводим все это на экран
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(drawAtMatrix[field[y][x]]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    public void killDragon() {
        putSwordAtField();
    }

    public void putSwordAtField() {
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);

        sword = new Sword(x, y);
    }

    public void sleep() {
        try {
            int level = dragon.getSections().size();
            int delay = level < LEVEL_15 ? (INITIAL_DELAY - DELAY_SLEEP * level) : BASE_DELAY;

            Thread.sleep(delay);
        } catch (InterruptedException exception) {
            System.out.println("Interrupted has been occurred " + exception);
        }
    }
}
