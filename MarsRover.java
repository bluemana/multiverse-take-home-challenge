import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class MarsRover {

    private static class World {

        private final int rows;
        private final int cols;

        public World(int rows, int cols) {
            this.rows = rows;
            this.cols = cols;
        }

        public int getRows() {
            return rows;
        }

        public int getCols() {
            return cols;
        }
    }

    private enum Orientation {
        N,
        E,
        S,
        W
    }

    private static class Rover {

        private int x;
        private int y;
        private Orientation orientation;
        private boolean isLost;

        public Rover(int x, int y, Orientation orientation) {
            this.x = x;
            this.y = y;
            this.orientation = orientation;
            isLost = false;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Orientation getOrientation() {
            return orientation;
        }

        public boolean isLost() {
            return isLost;
        }

        public boolean moveForward(World world) {
            if (orientation == Orientation.N) {
                if (y == world.getRows() - 1) {
                    isLost = true;
                    return false;
                }
                y++;
            } else if (orientation == Orientation.E) {
                if (x == world.getCols() - 1) {
                    isLost = true;
                    return false;
                }
                x++;
            } else if (orientation == Orientation.S) {
                if (y == 0) {
                    isLost = true;
                    return false;
                }
                y--;
            } else if (orientation == Orientation.W) {
                if (x == 0) {
                    isLost = true;
                    return false;
                }
                x--;
            }
            return true;
        }

        public void rotateRight() {
            if (orientation == Orientation.N) {
                orientation = Orientation.E;
            } else if (orientation == Orientation.E) {
                orientation = Orientation.S;
            } else if (orientation == Orientation.S) {
                orientation = Orientation.W;
            } else if (orientation == Orientation.W) {
                orientation = Orientation.N;
            }
        }

        public void rotateLeft() {
            if (orientation == Orientation.N) {
                orientation = Orientation.W;
            } else if (orientation == Orientation.E) {
                orientation = Orientation.N;
            } else if (orientation == Orientation.S) {
                orientation = Orientation.E;
            } else if (orientation == Orientation.W) {
                orientation = Orientation.S;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Writer writer = new OutputStreamWriter(System.out)) {
                String line = reader.readLine();
                String[] tokens = line.split(" ");
                int rows = Integer.parseInt(tokens[0]);
                int cols = Integer.parseInt(tokens[1]);
                World world = new World(rows, cols);
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    tokens = line.split("\\) ");
                    tokens[0] = tokens[0].substring(1);
                    String moves = tokens[1];
                    tokens = tokens[0].split(", ");
                    int x = Integer.parseInt(tokens[0]);
                    int y = Integer.parseInt(tokens[1]);
                    Orientation orientation = Orientation.valueOf(tokens[2]);
                    Rover rover = new Rover(x, y, orientation);
                    moves(world, rover, moves);
                    if (rover.isLost()) {
                        writer.write(String.format("(%d, %d, %s) LOST\n", rover.getX(), rover.getY(), rover.getOrientation()));
                    } else {
                        writer.write(String.format("(%d, %d, %s)\n", rover.getX(), rover.getY(), rover.getOrientation()));
                    }
                    writer.flush();
                }
        }
    }

    public static void moves(World world, Rover rover, String moves) {
        for (String move : moves.split("")) {
            if (!move(world, rover, move)) {
                break;
            }
        }
    }

    public static boolean move(World world, Rover rover, String move) {
        if (rover.isLost()) {
            return false;
        }
        if (move.equals("F")) {
            return rover.moveForward(world);
        }
        if (move.equals("R")) {
            rover.rotateRight();
            return true;
        }
        if (move.equals("L")) {
            rover.rotateLeft();
            return true;
        }
        return true;
    }
}