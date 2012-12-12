package jacobphillips;

import GenCol.entity;
import genDevs.modeling.message;
import simView.ViewableAtomic;

public abstract class Component extends ViewableAtomic {
    /** Component grows by this many pixels for each port beyond 3. */
    public static final int EXTRA_HEIGHT_PER_PORT = 17;
    
    protected static final String TEST_INPUT = "ACTIVATE";
    
    private final String[] mInputs;
    private final String[] mOutputs;

    private int mClockLimit;
    
    private boolean mHasFinished;
    
    public Component(String name) {
        super(name);
        Log.v(getClock(), getName(), "Constructor");    
        
        mClockLimit = 0;
        
        mInputs = onDefineInputs();
        mOutputs = onDefineOutputs();        
        
        addTestInput(TEST_INPUT, new entity(TEST_INPUT));
        for (String s : mInputs) {
            addInport(s);
        }
        for (String s : mOutputs) {
            addOutport(s);
        }
    }

    @Override
    public final void initialize() {
        Log.v(getClock(), getName(), "Initializing"); 
        mHasFinished = false;
        passivateIn(Devs.PASSIVE); 
    }

    @Override
    public final void deltint() { 
        Log.v(getClock(), getName(), "deltint(), phase is " + getPhase());  
        if (mClockLimit > 0 && getClock() > mClockLimit) {            
            if (!mHasFinished) {
                onClockLimitReached();
                mHasFinished = true;
            }
            return;
        }
        
        if (phaseIs(Devs.INJECT_DETECT)) {
            passivateIn(Devs.PASSIVE);
        }
    }

    @Override
    public final void deltext(double e, message msg) {                  
        Log.v(getClock(), getName(), "deltext(), phase is " + getPhase());
        if (mClockLimit > 0 && getClock() > mClockLimit) {
            if (!mHasFinished) {
                onClockLimitReached();
                mHasFinished = true;
            }
            return;
        }        
        Continue(e);
        String port;
        for (int i = 0; i < msg.getLength(); ++i) {
           for (int j = 0; j < mInputs.length; ++j) {
               port = mInputs[j];
               if (messageOnPort(msg, port, i)) {
                   long time = System.nanoTime();
                   onInputReceived(j, msg.getValOnPort(port, i).getName());
                   Log.v(getClock(), getName(), "onInputReceived() took " + (System.nanoTime() - time) + " ns");
               }
           }
        }
        holdIn(Devs.ACTIVE, 1);
    }

    @Override
    public final message out() {
        Log.v(getClock(), getName(), "out(), phase is " + getPhase());
        message m = new message();
        if (mClockLimit > 0 && getClock() > mClockLimit) {
            if (!mHasFinished) {
                onClockLimitReached();
                mHasFinished = true;
            }
            return m;
        }                        
        String output;
        if (phaseIs(Devs.ACTIVE)) {              
            for (int i = 0; i < mOutputs.length; ++i) {                              
                long time = System.nanoTime();
                output = onOutputRequested(i);
                Log.v(getClock(), getName(), "onOutputRequested() took " + (System.nanoTime() - time) + " ns");
                m.add(makeContent(mOutputs[i], new entity(output)));
            }
        }
        return m;
    }
    
    public final int getClock() {
        try { return (int) (float) Float.valueOf(getFormattedTL()) + 1; }
        catch (Exception e) { return 0; }
    }
    
    public final int getClockLimit() {
        return mClockLimit;
    }
    
    public final void setClockLimit(int limit) {
        mClockLimit = limit;
    }

    /** Get a list of the named input ports for this component. */ 
    public final String[] getInputs() {
        Log.v(getName(), "getInputPorts");
        return mInputs;
    }
    
    /** Get a list of the named output ports for this component. */
    public final String[] getOutputs() {
        Log.v(getName(), "getOutputPorts");
        return mOutputs;
    }

    /** Called when the input port list should be initialized. */
    protected abstract String[] onDefineInputs();
    
    /** Called when the output port list should be initialized. */
    protected abstract String[] onDefineOutputs();

    /** Called when an input is received on the specified port. */
    protected abstract void onInputReceived(int index, String input);
    
    /** Called when an output should be created on the specified port. */
    protected abstract String onOutputRequested(int index);
    
    /** Called when the clock reaches its limit, if one is defined. */
    protected abstract void onClockLimitReached();
}