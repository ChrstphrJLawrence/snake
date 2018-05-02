public class SnakePart {
    int x;
    int y;

    SnakePart(int x, int y) {
        this.x = x;
        this.y = y;
    }

    SnakePart(SnakePart other) {
        this.x = other.x;
        this.y = other.y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
