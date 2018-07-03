
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;

public class CustomMouseListener extends MouseAdapter{

   int xCoordinate;
   int yCoordinate;
   
   CoordinateLinkedList cl;
   String currentButton;
   EdgeLinkedList el;
   GraphPicturePanel p;
   JTextField t;
   
   Vertex[] twoPoints= new  Vertex[2];
   Vertex[] swapPoints= new  Vertex[2];
   Vertex[] storeX= new  Vertex[100];
   Vertex[] storeY= new  Vertex[100];
   int[] storeWeightX = new int[100];
   int[] storeWeightY = new int[100];
   
  
   
   public CustomMouseListener(CoordinateLinkedList vList, EdgeLinkedList eList,GraphPicturePanel panel) {
      super();
      cl = vList; 
      el = eList;
      p = panel;
      t = p.getTextField();
      twoPoints[0] = null;
      twoPoints[1] = null;
      swapPoints[0] = null;
      swapPoints[1] = null;
      for (int i=0; i<100; i++) {
         storeX[i] = (new Vertex(-1,-1));
         storeY[i] = (new Vertex(-1,-1));
      }
   }
   
   @Override
      public void mouseClicked(MouseEvent e) {
            xCoordinate= e.getX();
            yCoordinate = e.getY(); 
            try {
               setNewList();
            } catch (Exception e1) {
              e1.printStackTrace();
            }
            p.repaint();
      }

   @Override
      public void mousePressed(MouseEvent e) {
      }
   
   @Override
     public void mouseReleased(MouseEvent e) {
      }

   @Override
      public void mouseEntered(MouseEvent e) {
      }
   
   @Override
      public void mouseExited(MouseEvent e) {
      }
   
      
      public int getxCoordinate() {
         return xCoordinate;
      }

      public void setxCoordinate(int xCoordinate) {
         this.xCoordinate = xCoordinate;
      }

      public int getyCoordinate() {
         return yCoordinate;
      }

      public void setyCoordinate(int yCoordinate) {
         this.yCoordinate = yCoordinate;
      }
      
      public void setString(String x) {
         currentButton = x;
      }
      
      // set the button, to perform the action of clicked button
      public void setNewList() throws Exception {
         if(currentButton == "addVertex") {
            adjustVertexList();
         }
         if (currentButton == "addEdge") {
            adjustEdgeList();
         }
         if (currentButton == "moveVertex") {
            moveVertexInList();
         }
         if (currentButton == "changeWeight") {
            changeWeightInList();
         }
      }
      
      //add new vertex
      public void adjustVertexList() {
         if (getxCoordinate()!=0 || getyCoordinate()!=0)
            cl.addTail(new Vertex(getxCoordinate(),getyCoordinate()));
      }
      
      // add edge connecting the two clicked vertices
      public void adjustEdgeList() {
         Vertex add = new Vertex();
         if (getxCoordinate()!=0 || getyCoordinate()!=0) {
            if(twoPoints[0]== null) {
               CoordinateNode n= cl.getHead();
               VertexListIterator myVIterator = new VertexListIterator(n);
               while (myVIterator.hasNext()){
                  Vertex v = myVIterator.next();
                  if ((getxCoordinate()>(v.x)-20 && getxCoordinate()<(v.x)+20)&&(getyCoordinate()>(v.y)-20 && getyCoordinate()<(v.y)+20)) {
                     cl.remove(v);
                     cl.addTail(v);
                     cl.getTail().getData().setHighlight(true);
                     p.repaint();
                     add = v;
                     twoPoints[0] = add; 
                     break;
                  }
               }
            }
            else if (twoPoints[1]== null) {
               CoordinateNode n= cl.getHead();
               VertexListIterator myVIterator = new VertexListIterator(n);
               while (myVIterator.hasNext()){
                  Vertex p = myVIterator.next();
                  if ((getxCoordinate()>(p.x)-20 && getxCoordinate()<(p.x)+20)&&(getyCoordinate()>(p.y)-20 && getyCoordinate()<(p.y)+20)) {
                     cl.getTail().getData().setHighlight(false);
                     add = p;
                     twoPoints[1] = add; 
                     el.addTail(new Edge(twoPoints[0],twoPoints[1]));
                     twoPoints[0] = null;
                     twoPoints[1] = null;
                     break;
                  }
               }
           }
         }
      }
      
      //change the clicked vertex to the new location clicked
      private void moveVertexInList() throws Exception {
         int nextY = 0;
         int nextX = 0;
         if (swapPoints[0]==null) {
            CoordinateNode n= cl.getHead();
            VertexListIterator myVIterator = new VertexListIterator(n);
            while (myVIterator.hasNext()){
               Vertex p = myVIterator.next();
               if ((getxCoordinate()>(p.x)-20 && getxCoordinate()<(p.x)+20)&&(getyCoordinate()>(p.y)-20 && getyCoordinate()<(p.y)+20)) {
                  swapPoints[0]=p;
                  cl.remove(p);
                  break;
               }
            }
            EdgeNode e= el.getHead();
            EdgeListIterator myEIterator = new EdgeListIterator(e);
            while (myEIterator.hasNext()){
               Edge d = myEIterator.next();
               if(d.vertex1.equals(swapPoints[0])){
                  storeY[nextY] = d.vertex2;
                  storeWeightY[nextY] = d.getWeight();
                  nextY++;
                  el.remove(d);
               }
               else if(d.vertex2.equals(swapPoints[0])){
                  storeX[nextX] = d.vertex1;
                  storeWeightX[nextX] = d.getWeight();
                  nextX++;
                  el.remove(d);
               }
            }
         }
         else {
            if (getxCoordinate()!=0 || getyCoordinate()!=0){
            Vertex replacement = new Vertex(getxCoordinate(),getyCoordinate());
            Vertex blank = new Vertex (-1,-1);
            cl.addTail(replacement);
            int a = 0;
            while (!storeY[a].equals(blank)) {
               el.addTail(new Edge(storeY[a], replacement));
               el.getTail().getData().setWeight(storeWeightY[a]);
               a++;
            }
            int b = 0;
            while ((!storeX[b].equals(blank))) {
               el.addTail(new Edge(storeX[b], replacement));
               el.getTail().getData().setWeight(storeWeightX[b]);
               b++;
            }
            for (int i=0; i<100; i++) {
               storeX[i] = blank;
               storeY[i] = blank;
            }
            swapPoints[0]=null;
            }
         }
      }

    //change the weight of the clicked edge to the user's input       
    private void changeWeightInList() throws Exception {
       String newWeight = t.getText();
       int newWeightInt;
       newWeightInt = Integer.parseInt(newWeight);
       Vertex add = new Vertex();
       if (getxCoordinate()!=0 || getyCoordinate()!=0) {
          if(twoPoints[0]== null) {
             CoordinateNode n= cl.getHead();
             VertexListIterator myVIterator = new VertexListIterator(n);
             while (myVIterator.hasNext()){
                Vertex p = myVIterator.next();
                if ((getxCoordinate()>(p.x)-20 && getxCoordinate()<(p.x)+20)&&(getyCoordinate()>(p.y)-20 && getyCoordinate()<(p.y)+20)) {
                   add = p;
                   twoPoints[0] = add; 
                   break;
                }
             }
          }
          else if (twoPoints[1]== null) {
             CoordinateNode n= cl.getHead();
             VertexListIterator myVIterator = new VertexListIterator(n);
             while (myVIterator.hasNext()){
                Vertex p = myVIterator.next();
                if ((getxCoordinate()>(p.x)-20 && getxCoordinate()<(p.x)+20)&&(getyCoordinate()>(p.y)-20 && getyCoordinate()<(p.y)+20)) {
                   add = p;
                   twoPoints[1] = add; 
                   EdgeNode e= el.getHead();
                   EdgeListIterator myEIterator = new EdgeListIterator(e);
                   while (myEIterator.hasNext()){
                      Edge d = myEIterator.next();
                      if((d.vertex1.equals(twoPoints[0]) && d.vertex2.equals(twoPoints[1])) || d.vertex2.equals(twoPoints[0]) && d.vertex1.equals(twoPoints[1])){
                         el.remove(d);
                         el.addTail(new Edge(d.vertex1,d.vertex2));
                         el.getTail().getData().setWeight(newWeightInt);   
                      }
                   }
                   twoPoints[0] = null;
                   twoPoints[1] = null;
                   break;
                }
             }
          }
       }
    } 
    
        
    
   }