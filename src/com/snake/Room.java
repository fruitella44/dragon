package com.snake;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Основной класс программы.
 */
public class Room {
    private int width;
    private int height;
    private Snake snake;
    private Mouse mouse;
    public static Room game;
    private String[] drawAtMatrix = {" . ", " x ", " X ", "^_^", "RIP"};
    private int SNAKE_BODY =  1;
    private int SNAKE_HEAD = 2;
    private int MOUSE_BODY = 3;
    private int RIP = 4;
    private int INITIAL_DELAY = 520;
    private int BASE_DELAY = 200;
    private int DELAY_SLEEP = 20;
    private int LEVEL_15 = 15;

    public static void main(String[] args) {
        Snake snake = new Snake(10, 10);
        game = new Room(20, 20, snake);
        game.snake.setDirection(SnakeDirection.DOWN);

        game.createMouse();
        game.run();
    }

    public Room(int width, int height, Snake snake) {
        this.width = width;
        this.height = height;
        this.snake = snake;
        game = this;
    }

    public Snake getSnake() {
        return snake;
    }

    public Mouse getMouse() {
        return mouse;
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

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    public void run() {
        //Создаем объект "наблюдатель за клавиатурой" и стартуем его.
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        //пока змея жива
        while (snake.isAlive()) {
            //"наблюдатель" содержит события о нажатии клавиш?
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                //Если равно символу 'q' - выйти из игры.
                if (event.getKeyChar() == 'q') return;

                //Если "стрелка влево" - сдвинуть фигурку влево
                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    snake.setDirection(SnakeDirection.LEFT);
                    //Если "стрелка вправо" - сдвинуть фигурку вправо
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    snake.setDirection(SnakeDirection.RIGHT);
                    //Если "стрелка вверх" - сдвинуть фигурку вверх
                else if (event.getKeyCode() == KeyEvent.VK_UP)
                    snake.setDirection(SnakeDirection.UP);
                    //Если "стрелка вниз" - сдвинуть фигурку вниз
                else if (event.getKeyCode() == KeyEvent.VK_DOWN)
                    snake.setDirection(SnakeDirection.DOWN);
            }

            snake.move();
            print();
            sleep();
        }

        System.out.println("Game Over!");
    }

    /**
     * Выводим на экран текущее состояние игры
     */
    public void print() {
        int[][] matrix = new int[height][width];
        ArrayList<SnakeSection> sections = new ArrayList<SnakeSection>(snake.getSections());

        for (SnakeSection snakeSection : sections) {
            matrix[snakeSection.getY()][snakeSection.getX()] = SNAKE_BODY;
        }

        matrix[snake.getY()][snake.getX()] = snake.isAlive() ? SNAKE_HEAD : RIP;
        matrix[mouse.getY()][mouse.getX()] = MOUSE_BODY;

        //Выводим все это на экран
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(drawAtMatrix[matrix[y][x]]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    public void eatMouse() {
        createMouse();
    }

    public void createMouse() {
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);

        mouse = new Mouse(x, y);
    }


    /**
     * Программа делает паузу, длинна которой зависит от длинны змеи.
     */
    public void sleep() {
        try {
            int level = snake.getSections().size();
            int delay = level < LEVEL_15 ? (INITIAL_DELAY - DELAY_SLEEP * level) : BASE_DELAY;
            
            Thread.sleep(delay);
        } catch (InterruptedException exception) {
            System.out.println("Interrupted has been occurred " + exception);
        }
    }
}
