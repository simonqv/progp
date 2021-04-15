import java.util.ArrayList;
import java.util.List;

public class Runner {
    ParseTree tree;
    List<Object> coordinates = new ArrayList<>();

    public Runner(ParseTree tree) {
        this.tree = tree;
    }

    public void move() {
        List<Object> l = new ArrayList<>();

        System.out.println(l);
        LeafNode hej = (LeafNode) tree.process();
        System.out.println(hej.getInstruction());



   //    if (node.left != null) {
   //        toString(sb, node.left);
   //        sb.append(", ");
   //    }

   //    // in order traversal
   //    sb.append(node.data);

   //    if (node.right != null) {
   //        sb.append(", ");
   //        toString(sb, node.right);
   //    }
    }


    }

