package eecs6110.matrixmultiplication.devs;

import eecs6110.matrixmultiplication.Matrix;

class Settings {    
    static final int MATRIX_MODIFICATION_NONE           = 0;
    static final int MATRIX_MODIFICATION_REVERSE        = 1;
    static final int MATRIX_MODIFICATION_TRANSPOSE      = 2;
    
    
    /*
     * Below are settings that can be adjusted to modify the implementation.
     * 
     * NOTE: Project must be CLEANED before changes take effect.
     */
    
    // If there are more slaves than rows in matrix1, the excess number
    // of slaves are ignored. Computer.java enforces a minimum of 1 and a
    // maximum of 20 slaves.
    static final int NUM_SLAVES             = 3;

    static final int MATRIX1_ROWS           = 100;
    static final int MATRIX1_COLS           = 100;
    static final int MATRIX1_MIN_VALUE      = 1;
    static final int MATRIX1_MAX_VALUE      = 100;
    static final int MATRIX1_TYPE           = Matrix.TYPE_RANDOM;
    static final int MATRIX1_MODIFICATION   = MATRIX_MODIFICATION_NONE;    
    
    static final int MATRIX2_ROWS           = MATRIX1_COLS; // Don't change this one.
    static final int MATRIX2_COLS           = 100; 
    static final int MATRIX2_MIN_VALUE      = 1;
    static final int MATRIX2_MAX_VALUE      = 100;
    static final int MATRIX2_TYPE           = Matrix.TYPE_RANDOM;
    static final int MATRIX2_MODIFICATION   = MATRIX_MODIFICATION_NONE;
}
