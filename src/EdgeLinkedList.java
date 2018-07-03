
public class EdgeLinkedList {

   private EdgeNode head, tail;
   private int size;

   public EdgeLinkedList() {
      head = tail = null;
      size = 0;
   }

   public int size() {
      return size;
   }

   public boolean isEmpty() {
      return size == 0;
   }

   public void addHead(Edge e) {
      EdgeNode n = new EdgeNode(e, head);
      head = n;
      size++;
      if (tail == null)
         tail = head;
   }

   public void addTail(Edge e) {
      if (e.vertex1.equals(e.vertex2)) return;
      EdgeNode n = new EdgeNode(e, null);
      if (tail == null)
         head = tail = n;
      else {
         tail.setNext(n);
         tail = n;
      }
      size++;
   }

   public void removeHead() throws Exception {
      if (head == null)
         throw new Exception("Empty List");
      head = head.getNext();
      if (head == null)
         tail = head;
      size--;
   }
   
   public void removeTail() {
      if (tail == null)
         return;
      else {
         if (head == tail) {
            head = null;
            tail = null;
         }
         else {
            EdgeNode previousToTail = head;
                while (previousToTail.getNext() != tail)
                   previousToTail = previousToTail.getNext();
                tail = previousToTail;
                tail.setNext(null);
         }
      }
      size--;
   }

   public EdgeNode getHead() {
      return head;
   }

   public void setHead(EdgeNode head) {
      this.head = head;
   }

   public EdgeNode getTail() {  
      return tail;
   }
   
   public void setTail(EdgeNode tail) {
      this.tail = tail;
      
   }
   
   public void remove(Edge item) throws Exception{
      if (item == tail.getData()) {
         removeTail();
         return;
      }
      EdgeNode currentNode = head;
      EdgeNode previousNode = null;
      while(currentNode != null){
          if(item.equals(currentNode.getData())){
              if(previousNode  == null) {
                head = currentNode.getNext();
              }
              else {
                previousNode.setNext(currentNode.getNext());
              }
              size--;
          }
          else {
            previousNode = currentNode;
          }
          currentNode = currentNode.getNext();
      }
  }

   public void clear() throws Exception {
      while (!isEmpty()) {
         removeHead();
     }
      
   }

   public boolean find(Vertex p) {
      EdgeNode currentNode = head;
      while(currentNode != null){
          if(p.equals(currentNode.getData().getVertex1()) || p.equals(currentNode.getData().getVertex2())){
              return true;
          }
          currentNode = currentNode.getNext();
      }
      return false;
   }
   
   
   
}
