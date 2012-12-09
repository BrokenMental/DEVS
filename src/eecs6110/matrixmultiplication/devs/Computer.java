package eecs6110.matrixmultiplication.devs;

import java.awt.Point;
import simView.ViewableComponent;
import simView.ViewableDigraph;

public class Computer extends ViewableDigraph {  
    static final String DEFAULT_NAME = Computer.class.getSimpleName();
    
    private final SlaveProcessor[] mSlaveProcessors;
    private final int mNumSlaves;
    
    public Computer() {
        super(DEFAULT_NAME);
        if (Settings.DEBUG) System.out.println(getName() + ": [constructor]");        
        
        if      (Settings.NUM_SLAVES < 1)   mNumSlaves = 1;        
        else if (Settings.NUM_SLAVES > 20)  mNumSlaves = 20;
        else                                mNumSlaves = Settings.NUM_SLAVES;
        
        mSlaveProcessors = new SlaveProcessor[mNumSlaves];
        
        MasterProcessor masterProcessor = new MasterProcessor(mNumSlaves);
        
        add(masterProcessor);
        
        // Add slave processors to layout and add connections to master processor.
        for (int i = 0; i < mSlaveProcessors.length; ++ i) {
            mSlaveProcessors[i] = new SlaveProcessor(i);
            add(mSlaveProcessors[i]);            
            addCoupling(masterProcessor, MasterProcessor.OUTPUT_SLAVE + i, 
                    mSlaveProcessors[i], SlaveProcessor.INPUT_MASTER);
            addCoupling(mSlaveProcessors[i], SlaveProcessor.OUTPUT_MASTER,
                    masterProcessor, MasterProcessor.INPUT_SLAVES + i);
        }
        
        initialize();        
    }

    @Override
    public void layoutForSimView() {        
        if (Settings.DEBUG) System.out.println(getName() + ": layoutForSimView()");
        
        int masterLeft   = 50;
        int slavesLeft   = masterLeft + 250;
        int slavesMargin = 50;
        int masterTop    = slavesMargin * (mNumSlaves + 1) / 2;
        
        
        if (withName(MasterProcessor.DEFAULT_NAME) != null) {
             ((ViewableComponent) withName(MasterProcessor.DEFAULT_NAME)).setPreferredLocation(new Point(masterLeft, masterTop));
        }
        
        for (int i = 0; i < mSlaveProcessors.length; ++ i) {
            if (withName(mSlaveProcessors[i].getName()) != null) {
                 ((ViewableComponent) withName(mSlaveProcessors[i].getName()))
                         .setPreferredLocation(new Point(slavesLeft, slavesMargin * (i + 1)));
            }
        }
    }    
}

