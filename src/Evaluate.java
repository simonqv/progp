// LAB S2 - DD1362 Programmeringsparadigm.
// Simon Larpers Qvist
// Beata Johansson

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Evaluate {
    public ParseTree tree;                      // Parse Tree to traverse.
    public ArrayList<Double> startCoordinates;  // Start coordinates [0,0].
    public int currentV;                        // The current angle counter clockwise.
    public boolean isDown;                      // Boolean flag for if the pen is up or down.
    public String currentColor;                 // The current pen color.
    public ArrayList<String> colors;            // Logged color changes.
    public int numOfReps;                       // Number of repetitions.
    public boolean foundError;                  // Boolean flag for errors, that is true when an error-leaf has been found.
    public ArrayList<Double> currentPosition;   // Current position [x,y].
    public ArrayList<String> lines;             // Array to save all lines that should be printed.

    /**
     * Constructs a new Evaluate instance.
     * 
     * @param tree parse tree.
     */
    public Evaluate(ParseTree tree) {
        this.tree = tree;

        this.startCoordinates = new ArrayList<Double>();
        this.startCoordinates.add(0.0);
        this.startCoordinates.add(0.0);

        this.currentPosition = new ArrayList<Double>(startCoordinates);
        this.currentV = 0;
        this.isDown = false;
        this.colors = new ArrayList<String>();
        this.currentColor = "#0000FF";
        this.numOfReps = 1;
        this.foundError = false;
        this.lines = new ArrayList<String>();
        }

    /**
     * Traverses and evaluates a parse tree.
     * 
     * @param tree
     * @return Leaf or branch.
     */
    public ParseTree traverse(ParseTree tree){
        numOfReps = 1;
        ArrayList<Double> oldPosition = new ArrayList<Double>();
        if (tree == null) {
            return tree;
        }
        // If a leaf is found...
        if (tree.isLeaf() && foundError == false) {
            // Get the data that is stored in the leaf.
            Object data = tree.getData();
            int d = 0;
            // And based on the instruction in the leaf, choose the right action.
            switch(tree.getInstruction()){
                case ERROR:
                    // Flag for error and print error message.
                    foundError = true;
                    tree.errorMessage();
                    break;
                case FORW:
                    // Store the old prosition and move to new coordinates.
                    oldPosition = new ArrayList<>(currentPosition);
                    d = (Integer) data;
                    Move(currentPosition.get(0), currentPosition.get(1), currentV, d);
                    // If the pen is down, the coordinates are stored.
                    if (isDown == true){
                        saveLineToPrint(currentColor,oldPosition,currentPosition);
                    }
                    break;
                case BACK:
                    // Store the old prosition and move to new coordinates.
                    oldPosition = new ArrayList<>(currentPosition);
                    d = -(Integer) data;
                    Move(currentPosition.get(0), currentPosition.get(1), currentV, d);
                    // If the pen is down, the coordinates are stored.
                    if (isDown == true){
                        saveLineToPrint(currentColor,oldPosition,currentPosition);
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
                    numOfReps = (Integer) data;
                    break;
                default:
            }
            return tree;
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
        return tree; 
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
        double newX = x + d * Math.cos(Math.PI * v/180);
        double newY = y + d * Math.sin(Math.PI * v/180);
        currentPosition.set(0, newX);
        currentPosition.set(1, newY);
    }

    /**
     * Saves lines that are to be printed to an array.
     * 
     * @param curCol Current color.
     * @param oldPos Old position.
     * @param curPos New position.
     */
    public void saveLineToPrint(String curCol,ArrayList<Double> oldPos, ArrayList<Double> curPos){
        // If a coordinate contains "-0.0000", this means that a whole turn has been made. 
        // This must be changed into "0.0000".
        if (oldPos.get(0)<0 && oldPos.get(0)>-0.00005){oldPos.set(0,0.0);}
        if (oldPos.get(1)<0 && oldPos.get(1)>-0.00005){oldPos.set(1,0.0);}
        if (curPos.get(0)<0 && curPos.get(0)>-0.00005){curPos.set(0,0.0);}
        if (curPos.get(1)<0 && curPos.get(1)>-0.00005){curPos.set(1,0.0);}
        // Save the formatted string to the array.
        String s = String.format(Locale.US, "%s %.4f %.4f %.4f %.4f\n", curCol, oldPos.get(0), oldPos.get(1), curPos.get(0), curPos.get(1));
        lines.add(s);
    }

    /**
     * Print all line segments.
     */
    public void printLineSegments() {
        for (String line : lines) {
           System.out.printf(line);
        }
    }

}

