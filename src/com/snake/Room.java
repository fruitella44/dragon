package com.snake;

import java.awt.event.KeyEvent;

public class Room {
    private int width;
    private int height;
    private Snake snake;
    private Mouse mouse;
    private int LEVEL_11 = 11;
    private int LEVEL_15 = 15;

    public static Room game;

    public static void main(String[] args) {
        Snake snake = new Snake(10, 10);
        game = new Room(20, 20, snake);
        game.snake.setDirection(SnakeDirection.DOWN);

        game.createMouse();
        game.run();
        game.print();
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

    /**
     * Основной цикл программы.
     * Тут происходят все важные действия
     */
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

            snake.move();   //двигаем змею
            print();        //отображаем текущее состояние игры
            sleep();        //пауза между ходами
        }

        System.out.println("Game Over!");
    }

    public void print() {
        String[][] field = new String[height][width];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = ".";
            }
        }

        int x = game.snake.getSections().get(0).getX();
        int y = game.snake.getSections().get(0).getY();
        field[y][x] = "X";

        int snakeBody = game.snake.getSections().size();

        for (int i = 1; i < snakeBody; i++) {
            int snakePositionX = game.snake.getSections().get(i).getX();
            int snakePositionY = game.snake.getSections().get(i).getY();
            field[snakePositionY][snakePositionX] = "x";
        }

        int mousePositionX = game.mouse.getX();
        int mousePositionY = game.mouse.getY();
        field[mousePositionY][mousePositionX] = "^";

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }

    }

    public void eatMouse() {
        createMouse();
    }

    public void createMouse() {
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);

        mouse = new Mouse(x, y);
    }


    public void sleep() {
        // делаем паузу, длинна которой зависит от длинны змеи
        try {
            if (snake.getSections().size() < LEVEL_11) {
                Thread.sleep(500);
            } else if (snake.getSections().size() == LEVEL_11) {
                Thread.sleep(300);
            } else if (snake.getSections().size() >= LEVEL_15) {
                Thread.sleep(200);
            }

        } catch (InterruptedException exception) {
            System.out.println("Interrupted has been occurred " + exception);
        }
    }
}
