package com.dragon;

import java.util.ArrayList;
import java.util.List;

public class Dragon {
    private List<DragonSection> sections;
    private boolean isAlive;
    private DragonDirection direction;

    public Dragon(int x, int y) {
        this.sections = new ArrayList<>();
        this.sections.add(new DragonSection(x, y));
        this.isAlive = true;
    }

    public List<DragonSection> getSections() {
        return sections;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public DragonDirection getDirection() {
        return direction;
    }

    public void setDirection(DragonDirection direction) {
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
        DragonSection head = new DragonSection(getX() + directionX, getY() + directionY);
        Sword sword = Matrix.game.getSword();

        checkBorders(head);
        checkBody(head);

        if (isAlive) {
            if (head.getX() == sword.getX() && head.getY() == sword.getY()) {
                sections.add(0, head);
                Matrix.game.killDragon();
            } else {
                sections.add(0, head);
                sections.remove(sections.size() - 1);
            }
        }

    }

    public void checkBorders(DragonSection head) {
        if (head.getX() >= Matrix.game.getWidth() || head.getY() >= Matrix.game.getHeight()) {
            isAlive = false;
        } else if (head.getX() < 0 || head.getY() < 0) {
            isAlive = false;
        }
    }

    public void checkBody(DragonSection head) {
        if (sections.contains(head)) {
            isAlive = false;
        }
    }


}
