package jacobphillips.matrixmultiplication;

import jacobphillips.Component;
import jacobphillips.Log;
import jacobphillips.matrixmultiplication.Matrix.InvalidMatrixException;
import java.util.ArrayList;
import java.util.List;

public class SlaveProcessor extends Component {       
    private static final String DEFAULT_NAME  = SlaveProcessor.class.getSimpleName();
    
    private static final String OUTPUT_MASTER = "Product";
    private static final String INPUT_MASTER  = "Matrices";
    
    private String mOutput;    
    
    public SlaveProcessor(int index) {
        super(DEFAULT_NAME + index);
    }
    
    @Override
    protected List<String> initializeInputPorts() {
        List<String> inputs = new ArrayList<String>(1);        
        inputs.add(INPUT_MASTER);
        return inputs;
    }
    
    @Override
    protected List<String> initializeOutputPorts() {
        List<String> outputs = new ArrayList<String>(1);        
        outputs.add(OUTPUT_MASTER);
        return outputs;
    }

    @Override
    protected void onInput(int portIndex, String input) {
        Log.v(getName(), "onInput()");
        
        Matrix[] matrices = Matrix.fromString(input);        
        Matrix product;
        try {
            long start = System.nanoTime();
            product = matrices[0].multiplyBy(matrices[1]);
            long endMult = System.nanoTime();
            mOutput = product.toString();
            long endConv = System.nanoTime();
            Log.i(getName(), "Multiply: " + (endMult - start) + " | Convert: " + (endConv - endMult));
        } 
        catch (InvalidMatrixException ex) {
            Log.e(getName(), "Error processing input");
        }
    }

    @Override
    protected String onOutput(int portIndex) {
        Log.v(getName(), "onOutput()");
        return mOutput;
    }
}
