package eecs6110.matrixmultiplication.devs;

import GenCol.entity;
import eecs6110.matrixmultiplication.Matrix;
import eecs6110.matrixmultiplication.Matrix.InvalidMatrixException;
import genDevs.modeling.message;
import simView.ViewableAtomic;

public class MasterProcessor extends ViewableAtomic {       
    static final String DEFAULT_NAME = MasterProcessor.class.getSimpleName();        
    static final String OUTPUT_SLAVE = "Matrices";
    static final String INPUT_SLAVES = "Product";
    
    
    private final int    mNumSlaves;
    private final long[] mSlaveStartTime;
    
    private Matrix   mMatrix1;
    private Matrix   mMatrix2;    
    private Matrix[] mMatrix1Parts;
    
    public MasterProcessor(int numSlaves) {
        super(DEFAULT_NAME);      
        if (Settings.DEBUG) System.out.println(getName() + ": [constructor] | phase = " + getPhase());
        
        mNumSlaves = numSlaves;  
        mSlaveStartTime = new long[mNumSlaves];
        
        // Initialize matrices
        try {
            createMatrices();
            mMatrix1Parts = mMatrix1.splitIntoParts(mNumSlaves);
            if (Settings.DEBUG) showMatrices();
        }
        catch(InvalidMatrixException e) {
            System.err.println(e.getMessage());
        }
        
//        addInport(INPUT_SLAVES);
        addTestInput(INPUT_SLAVES, new entity(INPUT_SLAVES));
                
        // Add input port for slave processors.        
        for (int i = 0; i < mNumSlaves; ++i) {              
            addInport(INPUT_SLAVES + i);
            addOutport(OUTPUT_SLAVE + i);
//            addTestInput(INPUT_SLAVES + i, new entity(INPUT_SLAVES + i));
        }
    }

    @Override
    public void initialize() {
        if (Settings.DEBUG) System.out.println(getName() + ": initialize() | phase = " + getPhase());                
        passivateIn(Devs.PASSIVE);        
    }

    @Override
    public void deltint() { 
        if (Settings.DEBUG) System.out.println(getName() + ": deltint() | phase = " + getPhase());
        if (phaseIs(Devs.INJECT_DETECT)) {            
            passivateIn(Devs.PASSIVE);
        }
    }

    @Override
    public void deltext(double e, message x) {
        if (Settings.DEBUG) System.out.println(getName() + ": deltext() | phase = " + getPhase());        
        String input;
        for (int i = 0; i < x.getLength(); ++i) {
            for (int j = 0; j < mNumSlaves; ++j) {
                if (messageOnPort(x, INPUT_SLAVES + j, i)) {
                    if (Settings.DEBUG) System.out.println(getName() + ": deltext() -> messageOnPort() " + i + " " + j);
                    input = x.getValOnPort(INPUT_SLAVES + j, i).getName();
                    processInput(input, j);                        
                    holdIn(Devs.ACTIVE, 1);                                
                }
            }           
        }        
//        Continue(e);
        holdIn(Devs.ACTIVE, 1);  
    }

    @Override
    public message out() {
        if (Settings.DEBUG) System.out.println(getName() + ": out() | phase = " + getPhase());
        message m = new message();
        if (phaseIs(Devs.ACTIVE)) {            
            String output;
            for (int i = 0; i < mNumSlaves; ++i) {                
                output = getOutput(i);
                entity e = new entity(output);
                m.add(makeContent(OUTPUT_SLAVE + i, e));
            }
        }
        return m;
    }
    
    private void createMatrices() throws InvalidMatrixException {        
        if (Settings.DEBUG) System.out.println(getName() + ": createMatrices() | phase = " + getPhase());
        mMatrix1 = new Matrix(
                Settings.MATRIX1_ROWS,      Settings.MATRIX1_COLS, 
                Settings.MATRIX1_MIN_VALUE, Settings.MATRIX1_MAX_VALUE, 
                Settings.MATRIX1_TYPE);
        
        mMatrix2 = new Matrix(
                Settings.MATRIX2_ROWS,      Settings.MATRIX2_COLS, 
                Settings.MATRIX2_MIN_VALUE, Settings.MATRIX2_MAX_VALUE, 
                Settings.MATRIX2_TYPE);
        
        switch (Settings.MATRIX1_MODIFICATION) {
            case Settings.MATRIX_MODIFICATION_TRANSPOSE:
                mMatrix1 = mMatrix1.transpose();
                break;
            case Settings.MATRIX_MODIFICATION_REVERSE:
                mMatrix1 = mMatrix1.reverse();
                break;
        }
        
        switch (Settings.MATRIX2_MODIFICATION) {
            case Settings.MATRIX_MODIFICATION_TRANSPOSE:
                mMatrix2 = mMatrix2.transpose();
                break;
            case Settings.MATRIX_MODIFICATION_REVERSE:
                mMatrix2 = mMatrix2.reverse();
                break;
        }       
    }
    
    private void processInput(String input, int slave) {
        if (Settings.DEBUG) System.out.println(getName() + ": processInput() " + slave + " | phase = " + getPhase());
        if (mSlaveStartTime[slave] > 0) {
            long execTime = System.nanoTime() - mSlaveStartTime[slave];
            System.out.println("Slave " + slave + " execution time: "
                + execTime + " ns");
        }
    }
    
    private String getOutput(int slave) {  
        if (Settings.DEBUG) System.out.println(getName() + ": getOutput() " + slave + "  | phase = " + getPhase());
        String output = Matrix.toString(mMatrix1Parts[slave], mMatrix2);
        mSlaveStartTime[slave] = System.nanoTime();
        return output;
    }   
    
    private void showMatrices() { 
        if (Settings.DEBUG) System.out.println(getName() + ": showMatrices() | phase = " + getPhase());
        System.out.println(mMatrix1.toString());
        System.out.println(mMatrix2.toString());
        for (Matrix m : mMatrix1Parts) {
            System.out.println(m.toString());   
        }
    }
}
