import java.awt.Point;

public class Edge {

   Vertex vertex1;
   Vertex vertex2;
   int weight;
   
   public Edge(Vertex one, Vertex two) {
      vertex1= one;
      vertex2 = two;
      weight = -1;
   }

   public Point getVertex1() {
      return vertex1;
   }

   public void setVertex1(Vertex vertex1) {
      this.vertex1 = vertex1;
   }

   public Point getVertex2() {
      return vertex2;
   }

   public void setVertex2(Vertex vertex2) {
      this.vertex2 = vertex2;
   }

   public int getWeight() {
      return weight;
   }

   public void setWeight(int weight) {
      this.weight = weight;
   }
   
  @Override
  public boolean equals (Object o) {
     if (this == o) return true;
     if (o == null) return false;
     if (getClass() != o.getClass()) return false;
      if (((Edge) o).getVertex1().equals(vertex1) && ((Edge) o).getVertex2().equals(vertex2) ||((Edge) o).getVertex2().equals(vertex1) && ((Edge) o).getVertex1().equals(vertex2) )
         return true;
      return false;
   }

   
   
}
