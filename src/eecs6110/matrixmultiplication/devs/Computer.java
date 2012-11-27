package eecs6110.matrixmultiplication.devs;

import java.awt.Point;
import simView.ViewableComponent;
import simView.ViewableDigraph;

public class Computer extends ViewableDigraph {  
    private final SlaveProcessor[] mSlaveProcessors;
    
    public Computer() {
        super("Computer");
        
        MasterProcessor masterProcessor = new MasterProcessor();
        
        int numSlaves = Settings.NUM_SLAVES;
        if (numSlaves < 1) numSlaves = 1;
        else if (numSlaves > 20) numSlaves = 20;
        
        mSlaveProcessors = new SlaveProcessor[numSlaves];
        
        
        add(masterProcessor);
        
        for (int i = 0; i < mSlaveProcessors.length; ++ i) {
            mSlaveProcessors[i] = new SlaveProcessor(i);
            add(mSlaveProcessors[i]);
            addCoupling(masterProcessor, MasterProcessor.OUTPUT_SLAVES + i, 
                    mSlaveProcessors[i], SlaveProcessor.INPUT_MASTER);
            addCoupling(mSlaveProcessors[i], SlaveProcessor.OUTPUT_MASTER,
                    masterProcessor, MasterProcessor.INPUT_SLAVE + i);
        }
        initialize();        
    }

    @Override
    public void layoutForSimView() {
        if (withName(MasterProcessor.DEFAULT_NAME) != null) {
             ((ViewableComponent) withName(MasterProcessor.DEFAULT_NAME)).setPreferredLocation(new Point(50, 50));
        }
        
        for (int i = 0; i < mSlaveProcessors.length; ++ i) {
            if (withName(mSlaveProcessors[i].getName()) != null) {
                 ((ViewableComponent) withName(mSlaveProcessors[i].getName())).setPreferredLocation(new Point(300, 50 * i));
            }
        }
    }    
}

