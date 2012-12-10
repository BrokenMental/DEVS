package jacobphillips.matrixmultiplication;

import jacobphillips.ComponentGroup;
import jacobphillips.Log;
import java.awt.Point;
import java.util.List;

public class Main extends ComponentGroup {  
    private static final String DEFAULT_NAME = Main.class.getSimpleName();
    
    private MasterProcessor mMaster;
    private SlaveProcessor[] mSlaves;
    
    @Override
    protected void createChildren() {
        Log.v(getName(), "createChildren()");
        mMaster = new MasterProcessor();
        mSlaves = new SlaveProcessor[Settings.NUM_SLAVES];
        for (int i = 0; i < Settings.NUM_SLAVES; ++ i) {
            mSlaves[i] = new SlaveProcessor(i);           
        }  
    }
    
    @Override
    protected void addChildren() {        
        Log.v(getName(), "addChildren()");
        add(mMaster);
        for (int i = 0; i < mSlaves.length; ++ i) {
            add(mSlaves[i]);             
        }      
    }

    @Override
    protected void makeConnections() {
        Log.v(getName(), "makeConnections()");
        List<String> inputs = mMaster.getInputPorts();
        List<String> outputs = mMaster.getOutputPorts();
        String input, output;
        for (int i = 0; i < mSlaves.length; ++ i) {           
            input = mSlaves[i].getInputPorts().get(0);
            output = mSlaves[i].getOutputPorts().get(0);
            addCoupling(mMaster, outputs.get(i), mSlaves[i], input);
            addCoupling(mSlaves[i], output, mMaster, inputs.get(i));
        }  
    }

    @Override
    public void layoutForSimView() {
        Log.v(getName(), "layoutForSimView()");
        
        int masterLeft   = 50;
        int slavesLeft   = masterLeft + 250;
        int slaveSpacing = 50;
        int masterTop    = slaveSpacing * (mSlaves.length + 1) / 2;

        mMaster.setPreferredLocation(new Point(masterLeft, masterTop));
        for (int i = 0; i < mSlaves.length; ++ i) {
            mSlaves[i].setPreferredLocation(new Point(slavesLeft, slaveSpacing * (i + 1)));
        }
    }    
}

