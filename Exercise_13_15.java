// Template take from the book at https://liveexample.pearsoncmg.com/test/Exercise13_15_13e.txt
import java.math.*;
import java.util.Scanner;

public class Exercise_13_15 {
  public static void main(String[] args) {
    // Prompt the user to enter two Rational numbers
    Scanner input = new Scanner(System.in);
    System.out.print("Enter rational r1 with numerator and denominator seperated by a space: ");
    String n1 = input.next();
    String d1 = input.next();

    System.out.print("Enter rational r2 with numerator and denominator seperated by a space: ");
    String n2 = input.next();
    String d2 = input.next();

    RationalUsingBigInteger r1 = new RationalUsingBigInteger(
      new BigInteger(n1), new BigInteger(d1));
    RationalUsingBigInteger r2 = new RationalUsingBigInteger(
      new BigInteger(n2), new BigInteger(d2));

    // Display results
    System.out.println(r1 + " + " + r2 + " = " + r1.add(r2));
    System.out.println(r1 + " - " + r2 + " = " + r1.subtract(r2));
    System.out.println(r1 + " * " + r2 + " = " + r1.multiply(r2));
    System.out.println(r1 + " / " + r2 + " = " + r1.divide(r2));
    System.out.println(r2 + " is " + r2.doubleValue());
  }
}

// Name the revised Rational class RationalUsingBigInteger 
class RationalUsingBigInteger extends Number 
    implements Comparable<RationalUsingBigInteger> {
  // Data fields for numerator and denominator
  private BigInteger numerator = BigInteger.ZERO;
  private BigInteger denominator = BigInteger.ONE;
  
  // No-arg constructor
  RationalUsingBigInteger() {
  }
  
  // Constructor with numerator and denominator values
  RationalUsingBigInteger(BigInteger n, BigInteger d) {
      numerator = n;
      denominator = d;
  } 
  
  public BigInteger getNumerator() {
      return numerator;
  }
 
   public BigInteger getDenominator() {
      return denominator;
  }
  
  public RationalUsingBigInteger add(RationalUsingBigInteger secondRational) {
    BigInteger n = numerator.multiply(secondRational.getDenominator())
      .add(denominator.multiply(secondRational.getNumerator()));
    BigInteger d = denominator.multiply(secondRational.getDenominator());
    return new RationalUsingBigInteger(n, d);
  }

  /** Subtract a rational number from this rational */
  public RationalUsingBigInteger subtract(RationalUsingBigInteger secondRational) {
    BigInteger n = numerator.multiply(secondRational.getDenominator())
      .subtract(denominator.multiply(secondRational.getNumerator()));
    BigInteger d = denominator.multiply(secondRational.getDenominator());
    return new RationalUsingBigInteger(n, d);
  }

  /** Multiply a rational number by this rational */
  public RationalUsingBigInteger multiply(RationalUsingBigInteger secondRational) {
    BigInteger n = numerator.multiply(secondRational.getNumerator());
    BigInteger d = denominator.multiply(secondRational.getDenominator());
    return new RationalUsingBigInteger(n, d);
  }

  /** Divide a rational number by this rational */
  public RationalUsingBigInteger divide(RationalUsingBigInteger secondRational) {
    BigInteger n = numerator.multiply(secondRational.getDenominator());
    BigInteger d = denominator.multiply(secondRational.getNumerator());
    return new RationalUsingBigInteger(n, d);
  }
  
  @Override
  public double doubleValue() {
      return numerator.doubleValue() / denominator.doubleValue();
  }
  
  @Override
  public String toString() {
      return numerator + "/" + denominator;
  }
  
  @Override
  public float floatValue() {
      return numerator.floatValue() / denominator.floatValue();
  }

    @Override
    public int intValue() {
        return numerator.intValue() / denominator.intValue();
    }

    @Override
    public long longValue() {
        return numerator.longValue() / denominator.longValue();
    }

    @Override
    public int compareTo(RationalUsingBigInteger o) {
        return this.subtract(o).getNumerator().compareTo(BigInteger.ZERO);
    }
  
}