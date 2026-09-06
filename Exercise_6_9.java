// Exercise 6.9
// (Conversions between feet and meters)


public static void main(String[] args) {
    printTable();
}

// Convert foot value to meters
public static double footToMeter(double foot) {
  double meter = 0.305 * foot;
  return meter;
}

// Convert meter value to feet
public static double meterToFoot(double meter) {
  double foot = 3.279 * meter;
  return foot;
}

public static void printTable() {
    
    // Print Header 
    System.out.format("%4s     %6s               %6s    %4s %n", "Feet", "Meters", "Meters", "Feet");
    System.out.print("----------------------------------------------\n");
    
    String outputFormat = "%4.1f     %5.3f                %4.1f      %5.3f\n";
    
    for (int i = 0; i < 10; i++) {
        
        double feet = i + 1;
        double meters = 20 + (i * 5);
        
        System.out.format(outputFormat, feet, footToMeter(feet), meters, meterToFoot(meters));
    }
}