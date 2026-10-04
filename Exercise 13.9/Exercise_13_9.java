// Exercise 13.9
// (Enable Circle comparable)

// GeometricObject.java: The abstract GeometricObject class
// Code from Exercise 1.11
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

class Circle extends GeometricObject implements Comparable<Circle> {
    
    private double radius;
    
    // No-arg constructor
    protected Circle() {
        // Default radius
        this.radius = 5;
    }
    
    // Constructor with radius
    protected Circle(double radius) {
        this.radius = radius;
    }
    
    // Return radius
    public double getRadius() {
        return radius;
    }
    
    // Set a new radius
    public void setRadius(double newRadius) {
        radius = newRadius;
    }
    
    // Return area
    @Override 
    public double getArea() {
        return Math.PI * Math.pow(radius, 2);
    }
    
    // Return diameter
    public double getDiameter() {
        return 2 * radius;
    }
    
    // Return perimeter / circumference
    @Override 
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }
    
    // Print the circle info
    public void printCircle() {
        System.out.println("The circle is created and the radius is " + radius);
    }
    
    @Override
    public int compareTo(Circle c) {
        return Double.compare(this.radius, c.radius);
    }
    
    @Override
    public boolean equals(Object o) {
      if (o instanceof Circle circle)
        return radius == circle.radius;
      else
        return false;
    }   

    @Override
    public int hashCode() {
        return Double.hashCode(radius);
    }
    
}