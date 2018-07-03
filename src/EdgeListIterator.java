
public class EdgeListIterator {

   private EdgeNode current;

   public EdgeListIterator(EdgeNode e) {
      current = e;
   }

   public Edge next() {
      Edge answer = current.getData();
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
