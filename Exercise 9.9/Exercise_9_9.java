// Exercise 9.9
// (Geometry: n-sided regular polygon)

public class Exercise_9_9 {
    public static void main(String[] args) {
        
        String outputString = "Polygon %d\nArea: %f\nPerimeter: %f\n";
        
        // Create array of regular polygons
        RegularPolygon[] polygons = new RegularPolygon[3];
        
        polygons[0] = new RegularPolygon();
        polygons[1] = new RegularPolygon(6, 4);
        polygons[2] = new RegularPolygon(10, 4, 5.6, 7.8);
        
        for (int i = 0; i < polygons.length; i++) {
            RegularPolygon p = polygons[i];
            String formattedString = String.format(outputString, i+1, p.getArea(), p.getPerimeter());
            System.out.println(formattedString);
        }
    }
}

// Polygon Class
class RegularPolygon{
    
    // Data fields
    private int n = 3; // side amount
    private double side = 1; // side length
    private double x = 0;
    private double y = 0;
    
    // No-arg Constructor
    public RegularPolygon() {
    }
    
    public RegularPolygon(int newN, double newSide) {
        n = newN;
        side = newSide;
    }
    
    // Fully Specified Constructor
    public RegularPolygon(int newN, double newSide, double newX, double newY) {
        n = newN;
        side = newSide;
        x = newX;
        y = newY;
    }
    
    // Mutator Method
    public void setDetails(int newN, double newSide, double newX, double newY) {
        n = newN;
        side = newSide;
        x = newX;
        y = newY;
    }
    
    // Accessor Methods
    public double getSideLength() {
        return side;
    }
    
    public int getSides() {
        return n;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    // Return perimeter of the polygon
    public double getPerimeter() {
        return side * n;
    }
    
    // Return area of the polygon
    public double getArea() {
        return (n * Math.pow(side, 2) / (4 * Math.tan( Math.PI / n )));
    }
    
}