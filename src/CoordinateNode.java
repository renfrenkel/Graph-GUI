
public class CoordinateNode {
   private CoordinateNode next;
   Vertex vertex;

   public CoordinateNode(int x, int y, CoordinateNode n) {
      next = n;
      vertex = new Vertex(x, y);
   }

   public CoordinateNode getNext() {
      return next;
   }

   public void setNext(CoordinateNode n) {
      next = n;
   }

   public Vertex getData() {
      return vertex;
   }

   public void setVertex(Vertex v) {
      this.vertex = v;
   }
   
   
}
