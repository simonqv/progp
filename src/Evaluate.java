// LAB S2 - DD1362 Programmeringsparadigm.
// Simon Larpers Qvist
// Beata Johansson

import java.text.DecimalFormat;

public class Evaluate {

    public int currentV;                                        // The current angle counter clockwise.
    public boolean isDown;                                      // Boolean flag for if the pen is up or down.
    public String currentColor;                                 // The current pen color.
    public int numOfReps;                                       // Number of repetitions.
    public Double[] currentPosition;                            // Current position [x,y].
    public StringBuilder output;                                // Output string.
    DecimalFormat format = new DecimalFormat("0.0000");  // Formats decimal output.

    /**
     * Constructs a new Evaluate instance.
     */
    public Evaluate() {
        // Starting values.
        this.currentPosition = new Double[]{0.0, 0.0};
        this.currentV = 0;
        this.isDown = false;
        this.currentColor = "#0000FF";
        this.numOfReps = 1;
        this.output = new StringBuilder();
    }


    /**
     * Traverses and evaluates a parse tree.
     *
     * @param tree the ParseTree to evaluate.
     * @throws SyntaxError if error token.
     */
    public void traverse(ParseTree tree) throws SyntaxError {
        // Standard num repetitions.
        numOfReps = 1;
        if (tree == null) {
            return;
        }
        // If a leaf is found.
        if (tree.isLeaf()) {
            // Get data from leaf.
            Object data = tree.getData();
            int d;
            // Based on the instruction in the leaf, choose the right action.
            switch (tree.getInstruction()) {
                case ERROR:
                    throw new SyntaxError(tree.errorMessage());
                case FORW:
                    d = (Integer) data;

                    // If the pen is down, the coordinates are stored.
                    if (isDown) {
                        // Append current position.
                        output
                                .append(currentColor)
                                .append(" ")
                                .append(format.format(currentPosition[0]))
                                .append(" ")
                                .append(format.format(currentPosition[1]));

                        // Calc new position.
                        Move(currentPosition[0], currentPosition[1], currentV, d);

                        // Append new position.
                        output
                                .append(" ")
                                .append(format.format(currentPosition[0]))
                                .append(" ")
                                .append(format.format(currentPosition[1]))
                                .append("\n");
                    } else {
                        // Calc new position.
                        Move(currentPosition[0], currentPosition[1], currentV, d);
                    }
                    break;
                case BACK:
                    // Store the old position and move to new coordinates.
                    d = -(Integer) data;
                    if (isDown) {
                        // Append current position.
                        output
                                .append(currentColor)
                                .append(" ")
                                .append(format.format(currentPosition[0]))
                                .append(" ")
                                .append(format.format(currentPosition[1]));

                        // Calc new position.
                        Move(currentPosition[0], currentPosition[1], currentV, d);

                        // Append new position.
                        output
                                .append(" ")
                                .append(format.format(currentPosition[0]))
                                .append(" ")
                                .append(format.format(currentPosition[1]))
                                .append("\n");
                    } else {
                        // Calc new position.
                        Move(currentPosition[0], currentPosition[1], currentV, d);
                    }
                    break;
                case UP:
                    isDown = false;
                    break;
                case DOWN:
                    isDown = true;
                    break;
                case LEFT:
                    currentV = currentV + (Integer) data;
                    break;
                case RIGHT:
                    currentV = currentV - (Integer) data;
                    break;
                case COLOR:
                    currentColor = (String) data;
                    break;
                case REP:
                    // Sets how many reps to do.
                    numOfReps = (Integer) data;
                    break;
                default:
            }

        } else {
            // Traverse the left branch first.
            traverse(tree.getLeft());
            // Repeat the traversal of the right branch.
            // In the default case this is repeated 1 time.
            int reps = numOfReps;
            for (int rep = 0; rep < reps; rep++) {
                traverse(tree.getRight());
            }
        }
    }

    /**
     * Moves to a new set of coordinates.
     *
     * @param x X-coordinate.
     * @param y Y-coordinate.
     * @param v Angle.
     * @param d Distance
     */
    public void Move(double x, double y, int v, int d) {
        double newX = x + d * Math.cos(Math.PI * v / 180);
        double newY = y + d * Math.sin(Math.PI * v / 180);

        // If walked a lap, set -0.0000 to 0.0000
        if (newX < 0 && newX > -0.00005) {
            newX = 0.0;
        }
        if (newY < 0 && newY > -0.00005) {
            newY = 0.0;
        }
        currentPosition[0] = newX;
        currentPosition[1] = newY;
    }
}

