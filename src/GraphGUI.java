
import java.awt.Color;
import java.awt.Container;
import javax.swing.*;

public class GraphGUI extends JFrame{
   
  CoordinateLinkedList VertexList = new CoordinateLinkedList();
  EdgeLinkedList EdgeList = new EdgeLinkedList();
  EdgeLinkedList MinimalSpanningTree = new EdgeLinkedList();
   
   public static void main(String args[]) {
      try {
         UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
      }
      catch (ClassNotFoundException  | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException e) { 
      }
     new GraphGUI();
      
   }
   
   public GraphGUI() {
      super("GG1854");
      setSize(600,400);
      setLocation(100,100);
      setTitle("Graph GUI");
      Container contentPane = getContentPane();
      GraphPicturePanel myPanel = new GraphPicturePanel(VertexList, EdgeList, MinimalSpanningTree);
      contentPane.add(myPanel);
      ButtonHandler bh = new ButtonHandler(this, myPanel, VertexList, EdgeList, MinimalSpanningTree);
      myPanel.addButtons(bh);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
  } 
   
}
   
