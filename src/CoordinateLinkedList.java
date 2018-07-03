
public class CoordinateLinkedList{
   private CoordinateNode head, tail;
   private int size;

   public CoordinateLinkedList() {
      head = tail = null;
      size = 0;
   }

   public int size() {
      return size;
   }

   public boolean isEmpty() {
      return size == 0;
   }

   public void addHead(Vertex p) {
      CoordinateNode n = new CoordinateNode(p.x, p.y, head);
      head = n;
      size++;
      if (tail == null)
         tail = head;
   }

   public void addTail(Vertex p) {
      CoordinateNode n = new CoordinateNode(p.x, p.y, null);
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
            CoordinateNode previousToTail = head;
                while (previousToTail.getNext() != tail)
                   previousToTail = previousToTail.getNext();
                tail = previousToTail;
                tail.setNext(null);
         }
      }
   }
   
   public CoordinateNode getHead() {
      return head;
   }

   public void setHead(CoordinateNode head) {
      this.head = head;
   }

   public CoordinateNode getTail() {  
      return tail;
   }
   
   public void remove(Vertex item){
      if (item == tail.getData()) {
         removeTail();
         return;
      }
      CoordinateNode currentNode = head;
      CoordinateNode previousNode = null;
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
     
  
   
   
}
