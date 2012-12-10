package jacobphillips;

import GenCol.entity;
import genDevs.modeling.message;
import java.util.List;
import simView.ViewableAtomic;

public abstract class Component extends ViewableAtomic {       
    private static final String DEFAULT_NAME = Component.class.getSimpleName(); 
    
    protected static final String TEST_INPUT_INJECT = "inject";
    
    private final List<String> mInputs;
    private final List<String> mOutputs;
   
    public Component() {
        this(DEFAULT_NAME);
    }
    
    public Component(String name) {
        super(name);
        Log.v(getName(), "Constructor");    
        
        mInputs = initializeInputPorts();
        mOutputs = initializeOutputPorts();        
        
        addTestInput(TEST_INPUT_INJECT, new entity(TEST_INPUT_INJECT));
        for (String s : mInputs) {
            addInport(s);
        }
        for (String s : mOutputs) {
            addOutport(s);
        }
    }

    @Override
    public void initialize() {
        Log.v(getName(), "Initializing");        
        passivateIn(Devs.PASSIVE); 
    }

    @Override
    public void deltint() { 
        Log.v(getName(), "deltint(), phase is " + getPhase());        
        if (phaseIs(Devs.INJECT_DETECT)) {
            passivateIn(Devs.PASSIVE);
        }
    }

    @Override
    public void deltext(double e, message msg) {
        Log.v(getName(), "deltext(), phase is " + getPhase());
        int len = mInputs.size();
        String port;
        for (int i = 0; i < msg.getLength(); ++i) {
           for (int j = 0; j < len; ++j) {
               port = mInputs.get(j);
               if (messageOnPort(msg, port, i)) {
                   long time = System.nanoTime();
                   onInput(j, msg.getValOnPort(port, i).getName());
                   Log.v(getName(), "onInput() took " + (System.nanoTime() - time) + " ns");
               }
           }
        }
        holdIn(Devs.ACTIVE, 1);
    }

    @Override
    public message out() {
        Log.v(getName(), "out(), phase is " + getPhase());
        message m = new message();
        int len = mOutputs.size();  
        String output;
        if (phaseIs(Devs.ACTIVE)) {                        
            for (int i = 0; i < len; ++i) {                              
                long time = System.nanoTime();
                output = onOutput(i);
                Log.v(getName(), "onOutput() took " + (System.nanoTime() - time) + " ns");
                m.add(makeContent(mOutputs.get(i), new entity(output)));
            }
        }
        return m;
    }
    
    /** Get a list of the named input ports for this component. */ 
    public final List<String> getInputPorts() {
        Log.v(getName(), "getInputPorts");
        return mInputs;
    }
    
    /** Get a list of the named output ports for this component. */
    public final List<String> getOutputPorts() {
        Log.v(getName(), "getOutputPorts");
        return mOutputs;
    }

    /** Called when the input port list should be initialized. */
    protected abstract List<String> initializeInputPorts();
    
    /** Called when the output port list should be initialized. */
    protected abstract List<String> initializeOutputPorts();

    /** Called when an input is received on the specified port. */
    protected abstract void onInput(int portIndex, String input);
    
    /** Called when an output should be created on the specified port. */
    protected abstract String onOutput(int portIndex);
}
