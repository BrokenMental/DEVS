package eecs6110.matrixmultiplication.devs;

import GenCol.entity;
import eecs6110.matrixmultiplication.Matrix;
import eecs6110.matrixmultiplication.Matrix.InvalidMatrixException;
import genDevs.modeling.message;
import java.util.ArrayList;
import java.util.List;
import simView.ViewableAtomic;

public class MasterProcessor extends ViewableAtomic {       
    public static final String DEFAULT_NAME = MasterProcessor.class.getSimpleName();        
    public static final String OUTPUT_SLAVES = "Matrices";
    public static final String INPUT_SLAVE = "Product";
    
    
    private final int mNumSlaves;
    private final long[] mSlaveStartTime;
    
    private Matrix mMatrix1;
    private Matrix mMatrix2;    
    private Matrix[] mMatrix1Parts;
    
    public MasterProcessor() {
        this(DEFAULT_NAME);
    }
    
    public MasterProcessor(String name) {
        super(name);      
        
        mNumSlaves = Settings.NUM_SLAVES;  
        mSlaveStartTime = new long[mNumSlaves];
        
        try {
            createMatrices();
            mMatrix1Parts = mMatrix1.splitIntoParts(mNumSlaves);
            showMatrices();
        }
        catch(InvalidMatrixException e) {
            System.err.println(e.getMessage());
        }
        
        for (int i = 0; i < mNumSlaves; ++i) {
            addInport(INPUT_SLAVE + i);
            addTestInput(INPUT_SLAVE + i, new entity(INPUT_SLAVE + i));
            addOutport(OUTPUT_SLAVES + i);
        }
    }

    @Override
    public void initialize() {
        System.out.println(getName() + ": initialize()");                
        passivateIn("passive");        
    }

    @Override
    public void deltint() { 
        System.out.println(getName() + ": deltint()");
        if (phaseIs("InjectDet")) {
            System.out.println(getName() + ": deltint() -> InjectDet");
            passivateIn("passive");
        }
    }

    @Override
    public void deltext(double e, message x) {
        System.out.println(getName() + ": deltext()");
        String input;
        for (int i = 0; i < x.getLength(); ++i) {
            for (int j = 0; j < mNumSlaves; ++j) {
                if(messageOnPort(x, INPUT_SLAVE + j, i)) {
                    System.out.println(getName() + ": deltext() -> messageOnPort()");
                    input = x.getValOnPort(INPUT_SLAVE + j, i).getName();
                    processInput(input, j);
                    holdIn("active", 1);                                   
                }
            }           
        }
    }

    @Override
    public message out() {
        System.out.println(getName() + ": out()");
        message m = new message();
        if (phaseIs("Active")) {    
            System.out.println(getName() + ": out() -> phaseIs(Active)");
            for (int i = 0; i < mNumSlaves; ++i) {
                entity e = new entity(getOutput(i));
                m.add(makeContent(OUTPUT_SLAVES + i, e));
            }
        }
        return m;
    }
    
    private void createMatrices() throws InvalidMatrixException {        
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
        if (mSlaveStartTime[slave] > 0) {
            long execTime = System.nanoTime() - mSlaveStartTime[slave];
            System.out.println("Slave " + slave + " execution time: "
                + execTime + " ns");
        }
    }
    
    private String getOutput(int slave) {                
        String output = mMatrix1Parts[slave].toString();         
        mSlaveStartTime[slave] = System.nanoTime();
        return output;
    }   
    
    private void showMatrices() {        
        System.out.println(mMatrix1.toString());
        System.out.println(mMatrix2.toString());
        for (Matrix m : mMatrix1Parts) {
            System.out.println(m.toString());   
        }
    }
}
