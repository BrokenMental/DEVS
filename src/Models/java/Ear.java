package Models.java;

import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class Ear extends ViewableAtomic{
    
    double Infinity = INFINITY;
    
    public Ear(){
        this("Ear");}
    
    public Ear(String nm){
        super(nm);
        addInport("inBeat");
        addTestInput("inBeat",new entity());
        addTestInput("inBeat",new entity(),1);
        addOutport("outNotSync");
        addOutport("outSync");
    }
    
    public void initialize(){
        holdIn("Active",11.0);
    }
    
    public void deltint(){
        if(phaseIs("SyncOut")){
            holdIn("Active",11.0);
        } else
            if(phaseIs("NotSyncOut")){
            holdIn("Active",11.0);
            } else
                if(phaseIs("Active")){
            holdIn("Active",11.0);
                } else
                    passivate();
    }
    
    public void deltext(double e, message x){
        Continue(e);
        for(int i=0; i<x.getLength(); i++){
            if(this.messageOnPort(x, "inBeat", i)){
                if(phaseIs("Active" )){
                    processNotSyncOut();
                    phase = "NotSyncOut";
                } else if(phaseIs("Active" )){
                    processSyncOut();
                    holdIn("SyncOut",0.0);
                }
            }
        }
    }
    
    public void processNotSyncOut(){
        System.out.println("Processing: NotSyncOut()");
    }
    
    public void processSyncOut(){
        System.out.println("Processing: SyncOut()");
    }
    
    public message out(){
        message m = new message();
        if(phaseIs("NotSyncOut")){
            m.add(makeContent("outNotSync",new entity("NotSync")));
            System.out.println("Ear: send outNotSync");
        }
        if(phaseIs("SyncOut")){
            m.add(makeContent("outSync",new entity("Sync")));
            System.out.println("Ear: send outSync");
        }
        return m;
    }
}
