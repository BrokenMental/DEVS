package jacobphillips.matrixmultiplication;

import jacobphillips.Component;
import jacobphillips.Log;
import java.util.ArrayList;
import java.util.List;

public class SlaveProcessor extends Component {       
    private static final String DEFAULT_NAME  = SlaveProcessor.class.getSimpleName();
    
    private static final String OUTPUT_MASTER = "Product";
    private static final String INPUT_MASTER  = "Matrices";
    
    private final List<Long> mExecutionTimes;
    
    private String mOutput;    
    
    public SlaveProcessor(int index) {
        super(DEFAULT_NAME + index);
        setClockLimit(Settings.CLOCK_LIMIT);
        mExecutionTimes = new ArrayList<Long>();
    }
    
    public List<Long> getExecutionTimes() {
        Log.v(getClock(), getName(), "getExecutionTimes()");
        return mExecutionTimes;
    }
    
    @Override
    protected String[] onDefineInputs() {
        Log.v(getClock(), getName(), "onDefineInputs()");
        List<String> inputs = new ArrayList<String>(1);        
        inputs.add(INPUT_MASTER);
        return inputs.toArray(new String[inputs.size()]);
    }
    
    @Override
    protected String[] onDefineOutputs() {
        Log.v(getClock(), getName(), "onDefineOutputs()");
        List<String> outputs = new ArrayList<String>(1);        
        outputs.add(OUTPUT_MASTER);
        return outputs.toArray(new String[outputs.size()]);
    }

    @Override
    protected void onInputReceived(int index, String input) {
        Log.v(getClock(), getName(), "onInputReceived()");        
        Matrix[] matrices = Matrix.fromString(input);                         
        
        // Time the multiplication.
        long start = System.nanoTime();
        Matrix product = matrices[0].multiplyBy(matrices[1]);
        long time = System.nanoTime() - start;
        System.out.println(getName() + "," + getClock() + "," + time);
        mExecutionTimes.add(time);
        mOutput = product.toString();
    }

    @Override
    protected String onOutputRequested(int index) {
        Log.v(getClock(), getName(), "onOutputRequested()");
        return mOutput;
    }

    @Override
    protected void onClockLimitReached() {
        Log.v(getClock(), getName(), "onClockLimitReached()");
        
        long total = 0;
        long min = Long.MAX_VALUE;
        long max = 0;
        for (long t : mExecutionTimes) {
            total += t;
            if (t < min) min = t;
            if (t > max) max = t;
        }
        System.out.println(getName() + "," + min + "," + max + "," + total / mExecutionTimes.size());
//        Log.i(getClock(), getName(), "Avg = " +  
//                + " (min = " + min
//                + ", max = " + max
//                + ") ns.");
        
//        Log.d(getClock(), getName(), mExecutionTimes.get(0).toString());
    }
}
