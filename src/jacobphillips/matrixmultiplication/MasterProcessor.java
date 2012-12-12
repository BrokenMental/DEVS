package jacobphillips.matrixmultiplication;

import jacobphillips.Component;
import jacobphillips.Log;
import java.util.ArrayList;
import java.util.List;

public class MasterProcessor extends Component {       
    private static final String DEFAULT_NAME = MasterProcessor.class.getSimpleName();        
    
    private static final String OUTPUT_SLAVE = "Matrices";
    private static final String INPUT_SLAVE  = "Product";

    private Matrix   mMatrix1;
    private Matrix   mMatrix2;
    private Matrix[] mMatrix1Parts;
    
    public MasterProcessor() {
        this(DEFAULT_NAME);
    }
    
    public MasterProcessor(String name) {
        super(name); 
        setClockLimit(Settings.CLOCK_LIMIT);
        createMatrices();
        System.out.println(mMatrix1.toString());
    }
    
    private void createMatrices() {        
        Log.v(getClock(), getName(), "createMatrices()");
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
        
        mMatrix1Parts = mMatrix1.splitIntoParts(Settings.NUM_SLAVES);
        showMatrices();
    }

    private void showMatrices() {
        if (Log.getLevel() < Log.VERBOSE) return;
        Log.v(getClock(), getName(), "showMatrices()");
        Log.v(getClock(), getName(), "Matrix 1: " + mMatrix1.toString());
        Log.v(getClock(), getName(), "Matrix 2: " + mMatrix2.toString());        
        for (int i = 0; i < mMatrix1Parts.length; ++i) {
            Log.v(getClock(), getName(), "Matrix 1 part " + (i + 1) + ": " + mMatrix1Parts[i].toString());
        }
    }
    
    @Override
    protected String[] onDefineInputs() {
        Log.v(getClock(), getName(), "onDefineInputs()");
        List<String> inputs = new ArrayList<String>(Settings.NUM_SLAVES); 
        for (int i = 0; i < Settings.NUM_SLAVES; ++i) {
            inputs.add(INPUT_SLAVE + i);
        }
        return inputs.toArray(new String[inputs.size()]);
    }
    
    @Override
    protected String[] onDefineOutputs() {
        Log.v(getClock(), getName(), "onDefineOutputs()");
        List<String> outputs = new ArrayList<String>(Settings.NUM_SLAVES); 
        for (int i = 0; i < Settings.NUM_SLAVES; ++i) {
            outputs.add(OUTPUT_SLAVE + i);
        }
        return outputs.toArray(new String[outputs.size()]);
    }

    @Override
    protected void onInputReceived(int index, String input) {
        Log.v(getClock(), getName(), "onInputReceived()");
        
    }

    @Override
    protected String onOutputRequested(int index) {
        Log.v(getClock(), getName(), "onOutputRequested()");     
        if (Settings.RECREATE_MATRICES) createMatrices();        
        return Matrix.toString(mMatrix1Parts[index], mMatrix2);
    }

    @Override
    protected void onClockLimitReached() {
        Log.v(getClock(), getName(), "onClockLimitReached()");        
    }
}
