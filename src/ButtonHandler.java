
import java.awt.Container;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JFrame;

public class ButtonHandler implements ActionListener{
   
   GraphGUI gg;
   GraphPicturePanel p;
   CoordinateLinkedList cl;
   EdgeLinkedList el;
   CustomMouseListener ml;
   Vertex[] neighborVertex= new  Vertex[100];
   
   
   EdgeLinkedList unUsedEdges = new EdgeLinkedList();
   EdgeLinkedList findMinimum = new EdgeLinkedList();
   EdgeLinkedList spanningTree = new EdgeLinkedList();
   Edge result = new Edge (new Vertex (-1,-1),new Vertex (-1,-1));
   
   
   
   
   public ButtonHandler(GraphGUI gui, GraphPicturePanel panel, CoordinateLinkedList vList, EdgeLinkedList eList, EdgeLinkedList sTree) {
      gg = gui;
      p =panel;
      cl = vList;
      el = eList;
      spanningTree = sTree;
      ml = new CustomMouseListener(cl, el, p);
      p.addMouseListener(ml);
      for (int i=0; i<100; i++) {
         neighborVertex[i] = (new Vertex(-1,-1));
      }
   }

   public void actionPerformed(ActionEvent event) {
      String buttonName = event.getActionCommand();
      if (buttonName.equals("Help")) {
         helpCommand();
      }
      if (buttonName.equals("Add Vertex")) {
         addVertexCommand();
      }
      if (buttonName.equals("Add Edge")) {
         addEdgeCommand();
      }
      if (buttonName.equals("Move Vertex")) {
         addMoveCommand();
      }
      if (buttonName.equals("Change a Weight to:")) {
         addChangeWeightCommand();
      }
      if (buttonName.equals("Random Weights")) {
         addrandomWeightsCommand();
      }
      if (buttonName.equals("Add All Edges")) {
         addAllEdgesCommand();
      }
      if (buttonName.equals("Minimal Spanning Tree")) {
         try {
            addMinimalSpanningTreeCommand();
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
      if (buttonName.equals("Shortest Path")) {
         shortestPathCommand();
      }

      
   } 


   private void helpCommand() {
      p.setMinimalButton(false);
      JFrame helpFrame = new JFrame();
      helpFrame.setSize(450,250);
      helpFrame.setLocation(200,200);
      helpFrame.setTitle("Help");
      Container helpContentPane= helpFrame.getContentPane(); 
      TextArea myTextArea= new TextArea(); 
      myTextArea.append("-In order to pick vertices, select the radio button marked Add Vertices,\n"
                        + "and select positions of vertices in the right half of the gui by\n"
                        + "clicking the mouse. \n" 
                        + "-Select the radio button marked Add Edges to add edges. An Edge is\n"
                        + "made by clicking on the two vertices that specify its ends.\n"
                        + "-Before choosing the option to change a weight (or add a weight) to an\n" 
                        + "edge a value for the weight is entered in the text box.\n"
                        + "-The buttons marked Add All Edges and Random Weights are shortcuts\n"  
                        + "that add in all possible edges between selected vertices and choose\n" 
                        + "random weights for edges in a graph \n"
                        + "-Pressing the button Minimal Spanning Tree causes the gui to calculate a \n" 
                        + "minimal spanning tree \n"
                        + "-Shortest path between two vertices requires the selection of the vertices \n");  
      helpContentPane.add(myTextArea);
      helpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      helpFrame.setVisible(true);
   }
   
   private void addVertexCommand() {
      p.setMinimalButton(false);
      ml.setString("addVertex");
   }
   
   private void addEdgeCommand() {
      p.setMinimalButton(false);
      ml.setString("addEdge");
   }
   
   private void addMoveCommand() {
      p.setMinimalButton(false);
      ml.setString("moveVertex");
      
   }
   
   private void addChangeWeightCommand() {
      p.setMinimalButton(false);
      ml.setString("changeWeight");
   }
   
   private void shortestPathCommand() {
      p.setMinimalButton(false);
      ml.setString("shortestPath");
   }
   
   
   private void addrandomWeightsCommand() {
      p.setMinimalButton(false);
      Random x = new Random();
      EdgeNode e= el.getHead();
      EdgeListIterator myEIterator = new EdgeListIterator(e);
      while (myEIterator.hasNext()){
         Edge d = myEIterator.next();
         d.setWeight(x.nextInt(20));
      }
      p.repaint();
   }
   
   //add all edges of unconnected vertices
   public void addAllEdgesCommand() {
      p.setMinimalButton(false);
      CoordinateNode n= cl.getHead();
      int nextNeighbor = 0;
      boolean findMatch = false;
      VertexListIterator myVIterator = new VertexListIterator(n);
      while (myVIterator.hasNext()){
         Vertex p = myVIterator.next();
         EdgeNode e= el.getHead();
         EdgeListIterator myEIterator = new EdgeListIterator(e);
         while (myEIterator.hasNext()){
            Edge d = myEIterator.next();
            if(d.vertex1 == p){
               neighborVertex[nextNeighbor] = d.vertex2;
               nextNeighbor++;
            } 
            if(d.vertex2 == p){
               neighborVertex[nextNeighbor] = d.vertex1;
               nextNeighbor++;
            } 
         }
         CoordinateNode n2= cl.getHead();
         VertexListIterator myVIterator2 = new VertexListIterator(n2); 
         while (myVIterator2.hasNext()){
            findMatch = false;
            Vertex p2 = myVIterator2.next();
            for (int i =0; i<=nextNeighbor; i++) {
               if (p2.equals(neighborVertex[i])) {
                  findMatch = true;
                  break;
               }
            }
            if (findMatch == false) {
               el.addTail(new Edge(p, p2));
               findMatch = true;
            }
         }
         for (int i =0; i<100; i++) {
            neighborVertex[i] = new Vertex(-1, -1);
         }
      }
   }
   
   //calculate the minimal spanning tree of the graph
   private void addMinimalSpanningTreeCommand() throws Exception {
      spanningTree.clear();
      unUsedEdges.clear();
      p.setMinimalButton(true);
      EdgeNode x = el.getHead();
      EdgeListIterator myEIterator = new EdgeListIterator(x);
      while (myEIterator.hasNext()){
         Edge e = myEIterator.next();
         unUsedEdges.addTail(e);
         unUsedEdges.getTail().getData().setWeight(e.getWeight());
      }
      findMinimumEdge(unUsedEdges);
      spanningTree.addTail(new Edge ((Vertex) result.getVertex1(), (Vertex) result.getVertex2()));
      spanningTree.getTail().getData().setWeight(result.getWeight());
      unUsedEdges.remove(result);
      while (!Complete()) {
         EdgeNode y = unUsedEdges.getHead();
         EdgeListIterator myEIterator2 = new EdgeListIterator(y);
         while (myEIterator2.hasNext()){
            Edge e2 = myEIterator2.next();
            findMinimum.addTail(e2);
            findMinimum.getTail().getData().setWeight(e2.getWeight());
         }
         findMinimumEdge(findMinimum);
         while (!isConnected(result)) {
            findMinimum.remove(result);
            findMinimumEdge(findMinimum);
         } 
         spanningTree.addTail(new Edge ((Vertex) result.getVertex1(), (Vertex) result.getVertex2()));
         spanningTree.getTail().getData().setWeight(result.getWeight());
         unUsedEdges.remove(result); 
         findMinimum.clear();
      }
      p.repaint();
   }
   
   //return true if the mst has all the vertices, and therefore complete
   private boolean Complete() {
      CoordinateNode n = cl.getHead();
      VertexListIterator myVIterator = new VertexListIterator(n);
      while (myVIterator.hasNext()){
         Vertex p = myVIterator.next();
         if(isConnected(new Edge (p, p)) != true) {
            return false;
         }
      }
      return true;
   }

   // return true if one of the edges' vertices is in the mst, and the edge can therefore be added to the mst
   private boolean isConnected(Edge currentMinimum) {
      EdgeNode n = spanningTree.getHead();
      EdgeListIterator myEIterator = new EdgeListIterator(n);
      while (myEIterator.hasNext()){
         Edge e= myEIterator.next();
         if (e.getVertex1().equals(currentMinimum.getVertex1()) || e.getVertex1().equals(currentMinimum.getVertex2())
               || e.getVertex2().equals(currentMinimum.getVertex2()) || e.getVertex2().equals(currentMinimum.getVertex1())) {
            return true;
         }
      }
      return false;
   }

   // find the smallest edge, so that if it is connected, it can be added to the mst
   private void findMinimumEdge(EdgeLinkedList unUsedEdges2) {
      int minimum = 100;
      EdgeNode x = unUsedEdges2.getHead();
      EdgeListIterator myEIterator = new EdgeListIterator(x);
      while (myEIterator.hasNext()){
         Edge e = myEIterator.next();
         if (e.getWeight() < minimum) {
            minimum = e.getWeight();
            result.setVertex1((Vertex) e.getVertex1());
            result.setVertex2((Vertex) e.getVertex2());
            result.setWeight(e.getWeight());
         }
      } 
   }

   

   
}
