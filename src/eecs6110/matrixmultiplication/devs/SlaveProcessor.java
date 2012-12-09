package eecs6110.matrixmultiplication.devs;

import GenCol.entity;
import eecs6110.matrixmultiplication.Matrix;
import eecs6110.matrixmultiplication.Matrix.InvalidMatrixException;
import genDevs.modeling.message;
import simView.ViewableAtomic;

public class SlaveProcessor extends ViewableAtomic {       
    static final String DEFAULT_NAME = SlaveProcessor.class.getSimpleName();
    static final String OUTPUT_MASTER = "Product";
    static final String INPUT_MASTER = "Matrices";
    
    private final int mIndex;
    
    private String mOutput;    
    
    public SlaveProcessor(int index) {
        super(DEFAULT_NAME + index); 
        if (Settings.DEBUG) System.out.println(getName() + ": [constructor] | phase = " + getPhase());
        
        mIndex = index;
        
        addInport(INPUT_MASTER);        
        addOutport(OUTPUT_MASTER);
//        addTestInput(INPUT_MASTER, new entity(INPUT_MASTER));
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
        for (int i = 0; i < x.getLength(); ++i) {
            if(messageOnPort(x, INPUT_MASTER, i)) {
                if (Settings.DEBUG) System.out.println(getName() + ": deltext() -> messageOnPort() " + i);
                String input = x.getValOnPort(INPUT_MASTER, i).getName();
                processInput(input);
                holdIn(Devs.ACTIVE, 1);                                   
            }
        }
    }

    @Override
    public message out() {
        if (Settings.DEBUG) System.out.println(getName() + ": out() | phase = " + getPhase());
        message m = new message();
        if (phaseIs(Devs.ACTIVE)) {            
            entity e = new entity(mOutput);
            m.add(makeContent(OUTPUT_MASTER, e));
        }
        return m;
    }
    
    private void processInput(String input) {        
        if (Settings.DEBUG) System.out.println(getName() + ": processInput() | phase = " + getPhase());
        Matrix[] matrices = Matrix.fromString(input);        
        Matrix product;
        try {
            long start = System.nanoTime();
            product = matrices[0].multiplyBy(matrices[1]);
            long endMult = System.nanoTime();
            mOutput = product.toString();
            long endConv = System.nanoTime();
            System.out.println(getName() + ": Multiply: " + (endMult - start) + " | Convert: " + (endConv - endMult));
        } 
        catch (InvalidMatrixException ex) {
            System.err.println(getName() + ": Error processing input.");
        }
    }
}
