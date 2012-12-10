package jacobphillips.matrixmultiplication;

import jacobphillips.Log;

class Settings {    
    static final int MATRIX_MODIFICATION_NONE           = 0;
    static final int MATRIX_MODIFICATION_REVERSE        = 1;
    static final int MATRIX_MODIFICATION_TRANSPOSE      = 2;
    
    
// ==================================================================================    
// ==================================================================================      
    /*
     * Below are settings that can be adjusted to modify the implementation.
     * 
     * NOTE: Project must be CLEANED before changes take effect.
     */  
    private static final int SLAVES         = 3;

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
// ==================================================================================
// ==================================================================================
    
    
    
    // Enforce a usable amount of slaves;
    static final int NUM_SLAVES;    
    static {
        if      (SLAVES < 1)   NUM_SLAVES = 1;        
        else if (SLAVES > 20)  NUM_SLAVES = 20;
        else                   NUM_SLAVES = SLAVES;
        
        Log.setLevel(Log.INFO);
    }
}
