package jacobphillips.matrixmultiplication;

import jacobphillips.Component;
import jacobphillips.Log;
import jacobphillips.matrixmultiplication.Matrix.InvalidMatrixException;
import java.util.ArrayList;
import java.util.List;

public class MasterProcessor extends Component {       
    private static final String DEFAULT_NAME = MasterProcessor.class.getSimpleName();        
    
    private static final String OUTPUT_SLAVE = "Matrices";
    private static final String INPUT_SLAVE  = "Product";

    private Matrix   mMatrix1;
    private Matrix   mMatrix2;    
    private Matrix[] mMatrix1Parts;

    private final long[] mSlaveStartTimes;
    
    public MasterProcessor() {
        super(DEFAULT_NAME);       

        mSlaveStartTimes = new long[Settings.NUM_SLAVES];
        try {
            createMatrices();
            mMatrix1Parts = mMatrix1.splitIntoParts(Settings.NUM_SLAVES);
            showMatrices();
        }
        catch(InvalidMatrixException e) {
            Log.e(getName(), e.getMessage());
        }
    }
    
    private void createMatrices() throws InvalidMatrixException {        
        Log.v(getName(), "createMatrices()");
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

    private void showMatrices() {
        Log.v(getName(), "showMatrices()");
        Log.i(getName(), mMatrix1.toString());
        Log.i(getName(), mMatrix2.toString());        
        for (Matrix m : mMatrix1Parts) {
            Log.i(getName(), m.toString());
        }
    }
    
    @Override
    protected List<String> initializeInputPorts() {
        Log.v(getName(), "initializeInputPorts()");
        List<String> inputs = new ArrayList<String>(Settings.NUM_SLAVES); 
        for (int i = 0; i < Settings.NUM_SLAVES; ++i) {
            inputs.add(INPUT_SLAVE + i);
        }
        return inputs;
    }
    
    @Override
    protected List<String> initializeOutputPorts() {
        Log.v(getName(), "initializeOutputPorts()");
        List<String> outputs = new ArrayList<String>(Settings.NUM_SLAVES); 
        for (int i = 0; i < Settings.NUM_SLAVES; ++i) {
            outputs.add(OUTPUT_SLAVE + i);
        }
        return outputs;
    }

    @Override
    protected void onInput(int portIndex, String input) {
        Log.v(getName(), "onInput()");
        if (mSlaveStartTimes[portIndex] > 0) {
            Log.i(getName(), "Slave " + portIndex + " execution time: "
                + (System.nanoTime() - mSlaveStartTimes[portIndex]) + " ns");
        }
    }

    @Override
    protected String onOutput(int portIndex) {
        Log.v(getName(), "onOutput()");
        String output = Matrix.toString(mMatrix1Parts[portIndex], mMatrix2);
        mSlaveStartTimes[portIndex] = System.nanoTime();
        return output;
    }
}
