import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class GraphPicturePanel extends JPanel {
   
   CoordinateLinkedList vertexPictureList;
   EdgeLinkedList edgePictureList;
   EdgeLinkedList SpanningList;
   JTextField changeWeightText = new JTextField();
   Boolean minimalButton = false;
  
   
public Boolean getMinimalButton() {
      return minimalButton;
   }


   public void setMinimalButton(Boolean m) {
      this.minimalButton = m;
   }


public GraphPicturePanel(CoordinateLinkedList myVList, EdgeLinkedList myEList, EdgeLinkedList sTree) {
       super();
       vertexPictureList = myVList;
       edgePictureList = myEList;
       SpanningList = sTree;
 }
   
   
   public void addButtons(ButtonHandler bh) {
      setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
      JRadioButton addVertex = new JRadioButton("Add Vertex");
      JRadioButton addEdge = new JRadioButton("Add Edge");
      JRadioButton moveVertex = new JRadioButton("Move Vertex");
      JRadioButton shortestPath = new JRadioButton("Shortest Path");
      JRadioButton changeWeight = new JRadioButton("Change a Weight to:");
      
      //only 1 button in a group can be seleced at a time
      ButtonGroup btnGroup = new ButtonGroup();
      btnGroup.add(addVertex);
      btnGroup.add(addEdge);
      btnGroup.add(moveVertex);
      btnGroup.add(shortestPath);
      btnGroup.add(changeWeight);
      
      //remove background color around radio buttons
      Border noBorder = new LineBorder(Color.WHITE, 0);
      addVertex.setBorder(noBorder);
      addVertex.setContentAreaFilled(false);
      addEdge.setBorder(noBorder);
      addEdge.setContentAreaFilled(false);
      moveVertex.setBorder(noBorder);
      moveVertex.setContentAreaFilled(false);
      shortestPath.setBorder(noBorder);
      shortestPath.setContentAreaFilled(false);
      changeWeight.setBorder(noBorder);
      changeWeight.setContentAreaFilled(false);
                 
      JButton addEdges = new JButton("Add All Edges");
      JButton randomWeights = new JButton("Random Weights");
      JButton minimalSpanningTree = new JButton("Minimal Spanning Tree");
      JButton help = new JButton("Help");
      
      // create empty space between buttons
      add(addVertex);
      add(Box.createRigidArea(new Dimension(0,20)));
      add(addEdge);
      add(Box.createRigidArea(new Dimension(0,20)));
      add(moveVertex);
      add(Box.createRigidArea(new Dimension(0,20)));
      add(shortestPath);
      add(Box.createRigidArea(new Dimension(0,20)));
      add(changeWeight);
      
      // add input box
      Dimension dim = new Dimension(75,20);
      changeWeightText.setMaximumSize(dim);
      add(changeWeightText);
      
      Dimension d = new Dimension(200,50);
      addEdges.setMinimumSize(d);
      addEdges.setMaximumSize(d);
      addEdges.setPreferredSize(d);
      add(addEdges);
      randomWeights.setMinimumSize(d);
      randomWeights.setMaximumSize(d);
      randomWeights.setPreferredSize(d);
      add(randomWeights);
      minimalSpanningTree.setMinimumSize(d);
      minimalSpanningTree.setMaximumSize(d);
      minimalSpanningTree.setPreferredSize(d);
      add(minimalSpanningTree);
      help.setMinimumSize(d);
      help.setMaximumSize(d);
      help.setPreferredSize(d);
      add(help);
      
      help.addActionListener(bh);
      addVertex.addActionListener(bh);
      addEdge.addActionListener(bh);
      moveVertex.addActionListener(bh);
      changeWeight.addActionListener(bh);
      randomWeights.addActionListener(bh);
      addEdges.addActionListener(bh);
      minimalSpanningTree.addActionListener(bh);
      shortestPath.addActionListener(bh);
      
   }
   
   public JTextField getTextField() {
      return changeWeightText;
   }
    
   public void paint(Graphics g) { 
      super.paint(g);
      setVisible(true);
      repaint();
   }
   
   // paint all vertices, edges, and the spanning tree, when called on
   public void paintComponent(Graphics g){
      super.paintComponent(g);
      setBackground(new Color(204, 255, 255));
      CoordinateNode n= vertexPictureList.getHead();
      VertexListIterator myVIterator = new VertexListIterator(n);
      while (myVIterator.hasNext()){
         Vertex p = myVIterator.next();
         if (p.getHighlight() == true) {
            g.setColor(Color.orange);
         }
         else {
            g.setColor(new Color (255, 0, 127));
         }
         g.fillOval(p.x,p.y, 10, 10);
      }
      EdgeNode c= edgePictureList.getHead();
      EdgeListIterator myEIterator = new EdgeListIterator(c);
      if (minimalButton == false) {
      while (myEIterator.hasNext()){
         Edge e = myEIterator.next();
         g.setColor(new Color (0, 0, 255));
         g.drawLine(e.vertex1.x,e.vertex1.y,e.vertex2.x,e.vertex2.y);
         g.setColor(Color.black);
         if(e.getWeight()!=-1) {
            g.drawString(Integer.toString(e.getWeight()),((e.vertex1.x)+(e.vertex2.x))/2, ((e.vertex1.y)+(e.vertex2.y))/2);
         }
      }
      }
      else {
         while (myEIterator.hasNext()){
            Edge e = myEIterator.next();
            if(partOfSpanningTree(e)) {
               g.setColor(new Color (0, 255, 0));
            }
            else {
               g.setColor(new Color (0, 0, 255));
            }
            g.drawLine(e.vertex1.x,e.vertex1.y,e.vertex2.x,e.vertex2.y);
            g.setColor(Color.black);
            if(e.getWeight()!=-1) {
               g.drawString(Integer.toString(e.getWeight()),((e.vertex1.x)+(e.vertex2.x))/2, ((e.vertex1.y)+(e.vertex2.y))/2);
            }
      }
      setVisible(true);
    }
   }

   
   private boolean partOfSpanningTree(Edge e) {
      EdgeNode c= SpanningList.getHead();
      EdgeListIterator myEIterator = new EdgeListIterator(c);
      while (myEIterator.hasNext()){
         Edge x = myEIterator.next();
         if (e.equals(x)) return true;
      }
      return false;  
   }
   
}
   
   
