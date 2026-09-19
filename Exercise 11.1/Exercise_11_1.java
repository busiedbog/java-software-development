// Exercise 11.1
// (The Triangle Class)

// GeometricObject.java: The abstract GeometricObject class
abstract class GeometricObject {
    
    private String color = "white";
    private boolean filled;
    
    /**Default construct*/
    protected GeometricObject() {
    }
    
    /**Construct a geometric object*/
    protected GeometricObject(String color, boolean filled) {
        this.color = color;
        this.filled = filled;
    }
    
    /**Getter method for color*/
    public String getColor() {
        return color;
    }
    
    /**Setter method for color*/
    public void setColor(String color) {
        this.color = color;
    }
    
    /**Getter method for filled. Since filled is boolean,
    so, the get method name is isFilled*/
    public boolean isFilled() {
        return filled;
    }
    
    /**Setter method for filled*/
    public void setFilled(boolean filled) {
        this.filled = filled;
    }
    
    /**Abstract method findArea*/
    public abstract double getArea();
    
    /**Abstract method getPerimeter*/
    public abstract double getPerimeter();
    
}

class Triangle extends GeometricObject {
    
    // Triangle double data fields
    private double side1 = 1.0;
    private double side2 = 1.0;
    private double side3 = 1.0;
    
    // No-arg constructor
    Triangle() {
    }
    
    // Constructor with specified sides
    Triangle(double newSide1, double newSide2, double newSide3) {
        side1 = newSide1;
        side2 = newSide2;
        side3 = newSide3;
    }
    
    // Accessor Methods
    double getSide1() {
        return side1;
    }
    double getSide2() {
        return side2;
    }
    double getSide3() {
        return side3;
    }
    
    // Get Area
    @Override public double getArea() {
        return (side1 + side2 + side3)/2;
    }
    
    // Get Perimeter
    @Override public double getPerimeter() {
        return side1 + side2 + side3;
    }
    
    @Override public String toString() {
        return "Triangle: side1 = " + side1 + " side2 = " + side2 +
        " side3 = " + side3;
    }
}

void main(String[] args) {
    
    // Create triangle for user
    Triangle tri = createTriangle();
    
    // Print info about triangle
    String formatString = "\nArea: %f\nPerimeter: %f\nColor: %s\nFilled: %b";
    System.out.printf(formatString, tri.getArea(), tri.getPerimeter(), tri.getColor(), tri.isFilled());
    
}

Triangle createTriangle() {
    
    // Create scanner
    Scanner scanner = new Scanner(System.in);
    
    // Prompt user for side1, side2, side3, color, filled
    System.out.print("Side 1 length: ");
    double side1 = scanner.nextDouble();
    
    System.out.print("Side 2 length: ");
    double side2 = scanner.nextDouble();
    
    System.out.print("Side 3 length: ");
    double side3 = scanner.nextDouble();
    
    System.out.print("Color: ");
    // use this to prevent the newline character from being read as color
    scanner.nextLine();
    String color = scanner.nextLine();
    
    System.out.print("Filled: ");
    boolean filled = scanner.nextBoolean();
    
    // Create triangle from specifications
    Triangle requestedTriangle = new Triangle(side1, side2, side3);
    requestedTriangle.setColor(color);
    requestedTriangle.setFilled(filled);
    
    return requestedTriangle;
}