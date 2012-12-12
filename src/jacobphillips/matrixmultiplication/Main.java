package jacobphillips.matrixmultiplication;

import jacobphillips.Component;
import jacobphillips.ComponentGroup;
import jacobphillips.Log;
import java.awt.Dimension;
import java.awt.Point;

public class Main extends ComponentGroup {  
    private static final String NAME = "Matrix Multiplication";
    
    private MasterProcessor mMaster;
    private SlaveProcessor[] mSlaves;
    
    public Main() {
        super(NAME);
    }
    
    @Override
    protected void onCreateChildComponents() {
        Log.v(getName(), "onCreateChildComponents()");
        mMaster = new MasterProcessor();
        mSlaves = new SlaveProcessor[Settings.NUM_SLAVES];
        for (int i = 0; i < Settings.NUM_SLAVES; ++ i) {
            mSlaves[i] = new SlaveProcessor(i);           
        }  
    }
    
    @Override
    protected void onAddChildComponents() {        
        Log.v(getName(), "onAddChildComponents()");
        add(mMaster);
        for (int i = 0; i < mSlaves.length; ++ i) {
            add(mSlaves[i]);             
        }      
    }

    @Override
    protected void onAddCoupling() {
        Log.v(getName(), "onAddCoupling()");
        for (int i = 0; i < mSlaves.length; ++ i) {
            addCoupling(mMaster,    mMaster.getOutputs()[i],    mSlaves[i], mSlaves[i].getInputs()[0]);
            addCoupling(mSlaves[i], mSlaves[i].getOutputs()[0], mMaster,    mMaster.getInputs()[i]);
        }
    }

    @Override
    public void layoutForSimView() {
        Log.v(getName(), "layoutForSimView()");
        
        int masterLeft   = 50;
        int slavesLeft   = masterLeft + 250;
        int slaveSpacing = 50;        
        
        int extraInputs = (mSlaves.length - 3) < 0 ? 0 : mSlaves.length - 3; 
        int masterTopAdjustment = extraInputs * Component.EXTRA_HEIGHT_PER_PORT / 2;
        int masterTop    = slaveSpacing * (mSlaves.length + 1) / 2 - masterTopAdjustment;

        mMaster.setPreferredLocation(new Point(masterLeft, masterTop));
        for (int i = 0; i < mSlaves.length; ++ i) {
            mSlaves[i].setPreferredLocation(new Point(slavesLeft, slaveSpacing * (i + 1)));
        }
        
        setPreferredSize(new Dimension(slavesLeft + 300, slaveSpacing * (mSlaves.length + 2)));
    }    
}

