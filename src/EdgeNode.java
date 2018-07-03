
public class EdgeNode {

   private EdgeNode next;
   Edge data;

   public EdgeNode(Edge e, EdgeNode n) {
      next = n;
      data = e;
   }

   public EdgeNode getNext() {
      return next;
   }

   public void setNext(EdgeNode n) {
      next = n;
   }

   public Edge getData() {
      return data;
   }

   public void setData(Edge e) {
      data = e;
   }
   
}
