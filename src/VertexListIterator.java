
public class VertexListIterator {

   private CoordinateNode current;

   public VertexListIterator(CoordinateNode c) {
      current = c;
   }

   public Vertex next() {
      Vertex answer = current.getData();
      current = current.getNext();
      return answer;
   }

   public boolean hasNext() {
      return current != null;
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }

   
}
