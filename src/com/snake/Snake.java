package com.snake;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<SnakeSection> sections;
    private boolean isAlive;
    private SnakeDirection direction;

    public Snake(int x, int y) {
        this.sections = new ArrayList<>();
        this.sections.add(new SnakeSection(x, y));
        this.isAlive = true;
    }

    public List<SnakeSection> getSections() {
        return sections;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public SnakeDirection getDirection() {
        return direction;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    public int getX() {
        return sections.get(0).getX();
    }

    public int getY() {
        return sections.get(0).getY();
    }

    public void move() {
        if (!isAlive) {
            return;
        } else {
            switch (direction) {
                case UP:
                    move(0, -1);
                    break;

                case RIGHT:
                    move(1, 0);
                    break;

                case DOWN:
                    move(0, 1);
                    break;

                case LEFT:
                    move(-1, 0);
                    break;

                default:
                    isAlive = false;
            }
        }
    }

    public void move(int directionX, int directionY) {

    }

    public void checkBorders(SnakeSection head) {
        if (head.getX() >= Room.game.getWidth() || head.getY() >= Room.game.getHeight()) {
            isAlive = false;
        } else if (head.getX() < 0 || head.getY() < 0) {
            isAlive = false;
        }
    }

    public void checkBody(SnakeSection head) {
        if (sections.contains(head)) {
            isAlive = false;
        }
    }


}
