// Exercise 8.29
// (Identical arrays)

  

    public static void main(String[] args) {
        
        // initialize
        int[][] m1 = getMatrix(1);
        int[][] m2 = getMatrix(2);
        
        // compare
        boolean equal = equals(m1, m2);
        
        // output
        System.out.print("The matrices are ");
        if (!equal) {
            System.out.print("NOT ");
        }
        System.out.print("identical.");
    }

    public static int[][] getMatrix(int matrixIndex) {

        // Initialize matrix and scanner
        int[][] matrix = new int[3][3];
        Scanner matrixScanner = new Scanner(System.in);

        // Ask for matrix values
        String askString = "Enter m%d (a 3 by 3 matrix) row by row: ";
        String result = String.format(askString, matrixIndex);
        System.out.print(result);
        String matrixVals = matrixScanner.nextLine();

        // Create list from matrix values
        String[] matrixList = matrixVals.split(" ");

        // Iterate through list using nested for loop
        for (int ix = 0; ix < 3; ix++) {
            for (int iy = 0; iy < 3; iy++) {
                matrix[ix][iy] = Integer.parseInt(matrixList[ix + iy]);
            }
        }

        return matrix;

    }

    public static boolean equals(int[][] m1, int[][] m2) {
        return Arrays.deepEquals(m1, m2);
    }

