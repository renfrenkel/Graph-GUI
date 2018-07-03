import java.awt.Point;

public class Vertex extends Point {
   
   Boolean highlight;
   
   public Vertex () {
      super();
      highlight = false;
   }
   
   public Vertex (int x, int y) {
      super(x,y);
      highlight = false;
   }

   @Override
   public boolean equals (Object o) {
      if (this == o) return true;
      if (o == null) return false;
      if (getClass() != o.getClass()) return false;
       if ((this.getX() == ((Point) o).getX() && this.getY() == ((Point) o).getY()) || (this.getY() == ((Point) o).getX() && this.getX() == ((Point) o).getY()))
          return true;
       return false;
    }
   
   public Boolean getHighlight() {
      return highlight;
   }

   public void setHighlight(Boolean highlight) {
      this.highlight = highlight;
   }
}
