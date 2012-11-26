package eecs6110.cacheexample;

import GenCol.entity;
import genDevs.modeling.message;
import simView.ViewableAtomic;

public class Processor extends ViewableAtomic {
    public static final String DEFAULT_NAME = Processor.class.getSimpleName();
    public static final String OUTPUT_ADDRESS = "Address";
    public static final String INPUT_DUMMY = "Dummy";

    
    private final RandomNumberGenerator mAddresses;
    
    private int mCount;
    
    public Processor() {
        this(DEFAULT_NAME);
    }
    
    public Processor(String name) {
        super(name);        
        mAddresses = new RandomNumberGenerator(Computer.MAX_ADDRESS);        
        addOutport(OUTPUT_ADDRESS);
        addInport(INPUT_DUMMY);
        addTestInput(INPUT_DUMMY, new entity(INPUT_DUMMY));
    }

    @Override
    public void initialize() {
        mCount = 0;
        passivateIn("passive");        
    }

    @Override
    public void deltint() { 
        if (mCount >= Computer.ADDRESSES_TO_PROCESS) {            
            holdIn("InjectTransit", 0);
        }
        else if (phaseIs("InjectDet")) {
            passivateIn("passive");
        }
    }

    @Override
    public void deltext(double e, message x) {
        Continue(e);
        holdIn("Active", 1);
    }

    @Override
    public message out() {
        message m = new message();
        if (phaseIs("Active")) {            
            int address = mAddresses.getInt();
            entity e = new entity(String.valueOf(address));
            m.add(makeContent(OUTPUT_ADDRESS, e));
            ++mCount;
        }
        return m;
    }
}
