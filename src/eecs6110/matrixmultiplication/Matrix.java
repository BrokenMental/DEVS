package eecs6110.matrixmultiplication;

/** 
 * Class for generating and manipulating matrices.
 * Most methods of the class return the resulting int[][]
 * as a new instance of the Matrix class so that method calls 
 * can be chained (e.g. matrix.transpose().reverse().get();).
 */
public final class Matrix {
    public static class InvalidMatrixException extends Exception {
        public InvalidMatrixException(String msg) {
            super(msg);
        }
    }    
    
    public static final int TYPE_RANDOM                 = 1;
    public static final int TYPE_IDENTITY               = 2;
    public static final int TYPE_SORTED                 = 3;
    public static final int TYPE_UNIFORM                = 4;
    public static final int TYPE_RANDOM_UNIFORM_ROWS    = 5;
    public static final int TYPE_SORTED_UNIFORM_ROWS    = 6;
    
    public static final String MATRIX_DELIM = "&";
    public static final String ROW_DELIM = ":";
    public static final String COL_DELIM = ";";
    
    
    private final int mRows;
    private final int mCols;
    private final int[][] mMatrix;
    
    public Matrix(int[][] matrix) {
        mMatrix = matrix;
        mRows = matrix.length;
        mCols = matrix[0].length;
    }
    
    public Matrix(int rows, int columns, int minValue, int maxValue, int type) 
            throws InvalidMatrixException {        
        // Check arguments.
        if (rows < 1 || columns < 1 || minValue > maxValue) {
            throw new InvalidMatrixException("A matrix must have"
                    + " a positive number of rows and columns,"
                    + " and a minValue <= maxValue.");
        }
        
        // Initialize matrix
        mMatrix = new int[rows][columns];
        mRows = rows;
        mCols = columns;
        
        int[] values;
        int value;
        
        // Handle special case.
        if (minValue == maxValue || type == TYPE_UNIFORM) {
            for (int i = 0; i < rows; ++i) {                    
                for (int j = 0; j < columns; ++j) {
                    mMatrix[i][j] = minValue;
                }
            }  
            return;
        }
        
        switch (type) {
            case TYPE_IDENTITY:
                for (int i = 0; i < rows; ++i) {               
                    for (int j = 0; j < rows; ++j) {
                        if (i == j) mMatrix[i][j] = 1;
                        else mMatrix[i][j] = 0;
                    }
                } 
                break;
            case TYPE_RANDOM:
                for (int i = 0; i < rows; ++i) {
                    for (int j = 0; j < columns; ++j) {                
                        mMatrix[i][j] = getRandomInteger(minValue, maxValue);
                    }
                }
                break;                
            case TYPE_SORTED:
                values = makeSortedArray(rows * columns, minValue, maxValue);        
                for (int i = 0; i < rows; ++i) {               
                    for (int j = 0; j < columns; ++j) {
                        mMatrix[i][j] = values[i * columns + j];
                    }
                } 
                break;
            case TYPE_RANDOM_UNIFORM_ROWS:      
                for (int i = 0; i < rows; ++i) {        
                    value = getRandomInteger(minValue, maxValue);
                    for (int j = 0; j < columns; ++j) {                
                        mMatrix[i][j] = value;
                    }
                }
                break;
            case TYPE_SORTED_UNIFORM_ROWS:
                values = makeSortedArray(rows * columns, minValue, maxValue);                
                for (int i = 0; i < rows; ++i) {                                
                    value = values[i * columns];
                    for (int j = 0; j < columns; ++j) {
                        mMatrix[i][j] = value;
                    }
                } 
                break;
        }
    }
    
    public Matrix(String matrixString) {
        String[] rows = matrixString.split(ROW_DELIM); 
        String[] values = rows[0].split(COL_DELIM);    
        mRows = rows.length;
        mCols = values.length;
        mMatrix = new int[mRows][mCols];        
        for (int i = 0; i < rows.length; ++i) {            
            values = rows[i].split(COL_DELIM);
            for (int j = 0; j < values.length; ++j) {                
                mMatrix[i][j] = Integer.valueOf(values[j]);
            }            
        }
    }
    
    /** Return the number of rows in the matrix */
    public int rows() {
        return mRows;
    }
    
    /** Return the number of columns in the matrix */
    public int columns() {
        return mCols;
    }
    
    /** Returns the internal matrix used by this object. */
    public int[][] get() {
        return mMatrix;
    }
    
    /** Returns a matrix object containing a copy of the internal matrix. */
    public Matrix copy() {
        int[][] copy = new int[mRows][mCols];                
        for (int i = 0; i < mRows; ++i) {
            for (int j = 0; j < mCols; ++j) {
                copy[i][j] = mMatrix[i][j];
            }
        }
        return new Matrix(copy);
    }

    /** Transpose the internal matrix */
    public Matrix transpose() {              
        int[][] transposed = new int[mCols][mRows];        
        for (int i = 0; i < mRows; ++i) {                    
            for (int j = 0; j < mCols; ++j) {
                transposed[j][i] = mMatrix[i][j];
            }
        }
        return new Matrix(transposed);        
    }
    
    /** Reverse the internal matrix */
    public Matrix reverse() {                
        int[][] reversed = new int[mRows][mCols];       
        for (int i = 0; i < mRows; ++i) {                    
            for (int j = 0; j < mCols; ++j) {                
                reversed[mRows - 1 - i][mCols - 1 - j] = mMatrix[i][j];
            }
        }
        return new Matrix(reversed);        
    }
    
    /** 
     * Multiply the internal matrix by another. The internal matrix
     * is the left matrix and the given matrix is the right matrix.
     * Throws an exception if the given matrix doesn't have the same
     * number of rows as the internal matrix has columns.
     */
    public Matrix multiplyBy(Matrix matrix) throws InvalidMatrixException {        
        if (matrix == null || matrix.rows() != mCols) {
            throw new InvalidMatrixException("Can only multiply by a matrix"
                    + " with the same number of rows as the internal matrix"
                    + " has columns (" + mCols + ").");
        }            
        int[][] product = new int[mRows][matrix.columns()];
        int[][] m2 = matrix.get();
        for (int i = 0; i < mRows; ++i) {
            for (int j = 0; j < matrix.columns(); ++j) {
                for (int k = 0; k < mCols; ++k) {
                    product[i][j] += mMatrix[i][k] * m2[k][j];
                }
            }
        }        
        return new Matrix(product);
    }
    
    /** 
     * Appends another matrix onto the end of the internal matrix
     * row by row. The given matrix must have the same number of 
     * columns as the internal one.
     */
    public Matrix join(Matrix matrix) throws InvalidMatrixException {
        if (matrix == null || matrix.columns() != mCols) {
            throw new InvalidMatrixException("Can only join with a matrix"
                    + " having the same number of columns as the internal "
                    + "matrix (" + mCols + ").");
        }
        int[][] joined = new int[mRows + matrix.rows()][mCols];
        int[][] m2 = matrix.get();
        
        // Insert the internal matrix.
        for (int i = 0; i < mRows; ++i) {
            for (int j = 0; j < mCols; ++j) {
                joined[i][j] = mMatrix[i][j];
            }
        }
        
        // Add the second matrix.
        for (int i = 0; i < matrix.rows(); ++i) {
            for (int j = 0; j < mCols; ++j) {
                joined[i + mRows][j] = m2[i][j];
            }
        }
        
        return new Matrix(joined);
    }
    
    /** Alias of join(). */
    public Matrix append(Matrix matrix) throws InvalidMatrixException {
        return join(matrix);
    }
    
    /** 
     * Divides a matrix into smaller matrices. Separation is done by row.  
     * The given number of rows in each resulting matrix is a maximum and
     * must be at least 1. Left over rows will be put into an extra matrix.
     */
    public Matrix[] splitByRows(int maxRowsPerMatrix) throws InvalidMatrixException {
        if (maxRowsPerMatrix < 1) {
            throw new InvalidMatrixException("maxRowsPerMatrix must be > 0");
        }
        
        // Handle special case 
        if (maxRowsPerMatrix >= mRows) {
            return new Matrix[] {copy()};
        }
        
        int fullMatrices = mRows / maxRowsPerMatrix;
        int remainingRows = mRows % maxRowsPerMatrix;
        int numMatrices = fullMatrices + (remainingRows == 0 ? 0 : 1);

        Matrix[] matrices = new Matrix[numMatrices];
        int[][] submatrix;        
        int offset, mainRow;
        
        // Add all the full matrices        
        for (int i = 0; i < fullMatrices; ++i) {
            submatrix = new int[maxRowsPerMatrix][mCols];
            offset = i * maxRowsPerMatrix;
            for (int j = 0; j < maxRowsPerMatrix; ++j) {
                mainRow = j + offset;
                for (int k = 0; k < mCols; ++k) {                    
                    submatrix[j][k] = mMatrix[mainRow][k];
                }
            }
            matrices[i] = new Matrix(submatrix);
        }
        
        // Add a matrix of the remaining rows if necessary        
        if (remainingRows > 0) {
            submatrix = new int[remainingRows][mCols];
            offset = fullMatrices * maxRowsPerMatrix;
            for (int i = 0; i < remainingRows; ++i) {
                mainRow = i + offset;
                for (int j = 0; j < mCols; ++j) {                    
                    submatrix[i][j] = mMatrix[mainRow][j];
                }
            }
            matrices[fullMatrices] = new Matrix(submatrix);
        }
        
        return matrices;
    }
    
    /**
     * Split the internal matrix into the given number of parts.
     * Separation is done by row. The number of parts is the maximum
     * number of matrices returned. If the given number of parts is
     * more than there are rows in the matrix, the number of matrices
     * returned will be less than the number of parts given.
     */
    public Matrix[] splitIntoParts(int maxNumParts) throws InvalidMatrixException {
        if (maxNumParts < 1) {
            throw new InvalidMatrixException("maxNumParts must be > 0");
        }
        
        // Handle special case
        if (maxNumParts == 1) {
            return new Matrix[] {copy()};
        }
        
        int rowsPerMatrix = mRows / maxNumParts;        
        if (rowsPerMatrix < 1) rowsPerMatrix = 1;
        
        Matrix[] matrices = splitByRows(rowsPerMatrix);            
        
        if (mRows % maxNumParts == 0 || mRows < maxNumParts) {
            return matrices;
        }
        
        // Combine the last two matrices in the array.
        
        int len = matrices.length - 1;        
        Matrix[] results = new Matrix[len];
        
        // Add all but the last matrix
        for (int i = 0; i < len - 1; ++i) {
            results[i] = matrices[i];
        }
        
        // This is using the shortened length.
        results[len - 1] = matrices[len - 1].join(matrices[len]);   
        
        return results;
    }
    
    /** Convert a matrix to a string using pre-defined delimeters */
    @Override
    public String toString() {        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mRows; ++i) {
            if (i != 0) sb.append(ROW_DELIM);
            for (int j = 0; j < mCols; ++j) {
                if (j != 0) sb.append(COL_DELIM);
                sb.append(mMatrix[i][j]);
            }            
        }
        return sb.toString();
    }
    
    /** Convert multiple matrices to a single string */
    public static String toString(Matrix... matrices) {
        StringBuilder sb = new StringBuilder();
        for (Matrix m : matrices) {
            if (sb.length() > 0) sb.append(MATRIX_DELIM);
            sb.append(m.toString());
        }
        return sb.toString();
    }
    
    /** Convert a string to an integer matrix using pre-defined delimeters */
    public static Matrix[] fromString(String matricesString) {        
        String[] matricesSplit = matricesString.split(MATRIX_DELIM);
        int len = matricesSplit.length;
        Matrix[] matrices = new Matrix[len];
        for (int i = 0; i < len; ++i) {
            matrices[i] = new Matrix(matricesSplit[i]);
        }
        return matrices;
    }
    
    
    /** 
     * Create an array of the given length where values are spread
     * out evenly between the minimum (inclusive) and maximum (exclusive).
     */ 
    private int[] makeSortedArray(int length, int min, int max) {        
        if (length < 1 || max < min) return null;
        
        int     numValues       = (max - min);
        float   scalingFactor   = ((float) numValues) / ((float)(length));
        int[]   sortedArray     = new int[length];
        
        for (int i = 0; i < length; ++i) {               
            sortedArray[i] = min + Math.round(i * scalingFactor);
        } 
        
        return sortedArray;                
    }
    
    /** Returns an integer in the range min <= number <= max */
    private int getRandomInteger(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }
}
