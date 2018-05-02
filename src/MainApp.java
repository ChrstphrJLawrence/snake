import processing.core.PApplet;
import processing.core.PFont;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class MainApp extends PApplet {
    boolean loser;
    PFont f;
    int points;
    ArrayList<ArrayList<CellState>> gameBoard;
    int w, h;
    int initAppleX, initAppleY;
    LinkedList<SnakePart> snake;
    int highScore = 0;

    public static void main(String[] args) {
        PApplet.main("MainApp", args);
    }
    public void setup() {
        frameRate(10);
        background(0,100,0);
        loser = false;
        f = createFont("Arial",28,true);
        points = 0;
        w = 40;
        h = 40;
        gameBoard = new ArrayList<>();
        for (int i = 0; i < h; i++) {
            gameBoard.add(new ArrayList<>());
            for (int j = 0; j < w; j++) {
                gameBoard.get(i).add(CellState.EMPTY);
            }
        }
        addApple();
        snake = new LinkedList<>();
        snake.add(new SnakePart(w/2, h/2));
        SnakePart head = snake.getFirst();
        gameBoard.get(head.y).set(head.x, CellState.SNAKE);
    }

    public void addApple() {
        Random random = new Random();
        initAppleX = random.nextInt(w);
        initAppleY = random.nextInt(h);
        if (gameBoard.get(initAppleY).get(initAppleX) == CellState.SNAKE) {
            addApple();
        }
        gameBoard.get(initAppleY).set(initAppleX, CellState.APPLE);
    }

    public void controlSnake(SnakePart head) {
        if (key == CODED && keyCode == LEFT || key == 'a') {
            if (head.x - 1 < 0) {
                loser = true;
                return;
            }
            if (gameBoard.get(head.y).get(head.x - 1) == CellState.SNAKE) {
                loser = true;
            }
            head.x -= 1;
        }
        if (key == CODED && keyCode == RIGHT || key == 'd') {
            if (head.x + 1 > w - 1) {
                loser = true;
                return;
            }
            if (gameBoard.get(head.y).get(head.x + 1) == CellState.SNAKE) {
                loser = true;
            }
            head.x += 1;
        }
        if (key == CODED && keyCode == UP || key == 'w') {
            if (head.y - 1 < 0) {
                loser = true;
                return;
            }
            if (gameBoard.get(head.y - 1).get(head.x) == CellState.SNAKE) {
                loser = true;
            }
            head.y -= 1;
        }
        if (key == CODED && keyCode == DOWN || key == 's') {
            if (head.y + 1 > h - 1) {
                loser = true;
                return;
            }
            if (gameBoard.get(head.y + 1).get(head.x) == CellState.SNAKE) {
                loser = true;
            }
            head.y += 1;
        }
    }

    public void draw() {
        fill(0, 100, 0);
        rect(0, 400, 400, 100);
        if (loser == true) {
            if (points > highScore) {
                highScore = points;
            }
            textFont(f, 28);
            fill(0);
            text("Points: " + points + "  |  High Score: " + highScore, 50, 435);
            fill(255, 0, 0);
            textFont(f, 36);
            text("YOU LOSE",100,475);
            textFont(f, 16);
            fill(0);
            text("Press 'r' to restart.", 10, 495);
            if (key == 'r') {
                setup();
            }
        }
        else if (points > 1598) {
            textFont(f, 28);
            fill(0);
            text("Points: " + points, 125, 435);
            fill(0, 0, 255);
            textFont(f, 36);
            text("YOU WIN!!!",100,475);
        }
        else {
            SnakePart head = new SnakePart(snake.getFirst());
            controlSnake(head);
            snake.addFirst(head);

            if (gameBoard.get(head.y).get(head.x) == CellState.APPLE) {
                addApple();
                points += 1;
            }
            else {
                SnakePart removed = snake.removeLast();
                gameBoard.get(removed.y).set(removed.x, CellState.EMPTY);
            }

            gameBoard.get(head.y).set(head.x, CellState.SNAKE);

            textFont(f, 28);
            fill(0);
            text("Points: " + points + "  |  High Score: " + highScore, 50, 435);
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    if (gameBoard.get(i).get(j).equals(CellState.EMPTY)) {
                        fill(0, 0, 0);
                    }
                    else if (gameBoard.get(i).get(j).equals(CellState.SNAKE)) {
                        fill(0, 100, 0);
                    }
                    else {
                        fill(255, 0, 0);
                    }
                    rect(10 * j, 10 * i, 10, 10);
                }
            }
        }
    }

    public void settings() {
        size(400, 500);
    }
}
