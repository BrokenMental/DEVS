package eecs6110.matrixmultiplication.devs;

import GenCol.entity;
import eecs6110.matrixmultiplication.Matrix;
import eecs6110.matrixmultiplication.Matrix.InvalidMatrixException;
import genDevs.modeling.message;
import simView.ViewableAtomic;

public class SlaveProcessor extends ViewableAtomic {       
    public static final String DEFAULT_NAME = SlaveProcessor.class.getSimpleName();
    public static final String OUTPUT_MASTER = "Product";
    public static final String INPUT_MASTER = "Matrices";
    
    private String mOutput;    
    private final int mIndex;
    
    public SlaveProcessor(int index) {
        this(DEFAULT_NAME + index, index);
    }
    
    private SlaveProcessor(String name, int index) {
        super(name); 
        mIndex = index;
        addOutport(OUTPUT_MASTER);
        addInport(INPUT_MASTER);
        addTestInput(INPUT_MASTER, new entity(INPUT_MASTER));
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
        for (int i = 0; i < x.getLength(); ++i) {
            if(messageOnPort(x, INPUT_MASTER, i)) {
                System.out.println(getName() + ": deltext() -> messageOnPort()");
                String input = x.getValOnPort(INPUT_MASTER, i).getName();
                processInput(input);
                holdIn("active", 1);                                   
            }
        }
    }

    @Override
    public message out() {
        System.out.println(getName() + ": out()");
        message m = new message();
        if (phaseIs("Active")) {   
            System.out.println(getName() + ": out() -> phaseIs(Active)");
            entity e = new entity(mOutput);
            m.add(makeContent(OUTPUT_MASTER, e));
        }
        return m;
    }
    
    private void processInput(String input) {        
        Matrix[] matrices = Matrix.fromString(input);        
        Matrix product;
        try {
            product = matrices[0].multiplyBy(matrices[1]);
            mOutput = product.toString();
        } 
        catch (InvalidMatrixException ex) {
            System.err.println(getName() + ": Error processing input.");
        }
    }
}
