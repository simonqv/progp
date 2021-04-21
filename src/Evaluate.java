// LAB S2 - DD1362 Programmeringsparadigm.
// Simon Larpers Qvist
// Beata Johansson

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Evaluate {
    public ParseTree tree;                      // Parse Tree to traverse.
    public int currentV;                        // The current angle counter clockwise.
    public boolean isDown;                      // Boolean flag for if the pen is up or down.
    public String currentColor;                 // The current pen color.
    public int numOfReps;                       // Number of repetitions.
    public Double[] currentPosition;            // Current position [x,y].
    public StringBuilder output;

    /**
     * Constructs a new Evaluate instance.
     * 
     * @param tree parse tree.
     */
    public Evaluate(ParseTree tree) {
        this.tree = tree;

        this.currentPosition = new Double[]{0.0, 0.0};
        this.currentV = 0;
        this.isDown = false;
        this.currentColor = "#0000FF";
        this.numOfReps = 1;
        this.output = new StringBuilder();
        }

    DecimalFormat format = new DecimalFormat("0.0000");

    /**
     * Traverses and evaluates a parse tree.
     * 
     * @param tree
     * @return Leaf or branch.
     */
    public void traverse(ParseTree tree) throws SyntaxError {
        numOfReps = 1;

        if (tree == null) {
            return;
        }
        // If a leaf is found...
        if (tree.isLeaf()) {
            // Get the data that is stored in the leaf.
            Object data = tree.getData();
            int d = 0;
            // And based on the instruction in the leaf, choose the right action.
            switch(tree.getInstruction()){
                case ERROR:
                    throw new SyntaxError(tree.errorMessage());
                case FORW:
                    d = (Integer) data;

                    // If the pen is down, the coordinates are stored.
                    if (isDown){
                        output
                                .append(currentColor)
                                .append(" ")
                                .append(format.format(currentPosition[0]))
                                .append(" ")
                                .append(format.format(currentPosition[1]));

                        Move(currentPosition[0], currentPosition[1], currentV, d);

                        output
                                .append(" ")
                                .append(format.format(currentPosition[0]))
                                .append(" ")
                                .append(format.format(currentPosition[1]))
                                .append("\n");
                    }
                    else { Move(currentPosition[0], currentPosition[1], currentV, d); }
                    break;
                case BACK:
                    // Store the old prosition and move to new coordinates.
                    d = -(Integer) data;
                    if (isDown){
                        output
                                .append(currentColor)
                                .append(" ")
                                .append(format.format(currentPosition[0]))
                                .append(" ")
                                .append(format.format(currentPosition[1]));

                        Move(currentPosition[0], currentPosition[1], currentV, d);

                        output
                                .append(" ")
                                .append(format.format(currentPosition[0]))
                                .append(" ")
                                .append(format.format(currentPosition[1]))
                                .append("\n");
                    }
                    else { Move(currentPosition[0], currentPosition[1], currentV, d); }
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
                    numOfReps = (Integer) data;
                    break;
                default:
            }

        }
        else {
           // Traverse the left branch first.
           traverse(tree.getLeft());
           // Repeat the traversal of the right branch. 
           // In the default case this is repeated 1 time.
           int reps = numOfReps;
           for (int rep = 0; rep < reps; rep++ ){
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

