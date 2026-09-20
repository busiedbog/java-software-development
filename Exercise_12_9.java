// Exercise 12.9
// (BinaryFormatException)

void main(String[] args) {
    
    Scanner scanner = new Scanner(System.in);
    
    System.out.print("Enter binary string: ");
    String binary = scanner.nextLine();
    
    System.out.printf("Decimal: %f", bin2Dec(binary));
    
}

// Create BinaryFormatException
public class BinaryFormatException extends RuntimeException {
    public BinaryFormatException(String message) {
        super(message);
    }
}

// Convert binary to decimal
double bin2Dec(String binaryString) throws NumberFormatException, BinaryFormatException {
        
    // Multiply each digit in binary number by 2 raised to the power of its
    // positional value and then add up the values
    double finalNumber = 0;
    for (int i = binaryString.length(); i > 0; i--) {

        char digit = binaryString.charAt(i - 1);

        // Validate if char is not 1 or 0
        if (digit != '0' && digit != '1') {
            throw new BinaryFormatException("Number is not a valid binary number.");
        }

        int numberInPlace = Character.getNumericValue(digit);
        int digitPlace = binaryString.length() - i;

        finalNumber += numberInPlace * Math.pow(2, digitPlace);


        System.out.printf("Digit place: %d  Number: %d  Addition: %f\n", digitPlace, numberInPlace, finalNumber);
    }
    
    return finalNumber;

}