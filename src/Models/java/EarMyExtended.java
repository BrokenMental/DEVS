package Models.java;

import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class EarMyExtended extends ViewableAtomic{
    
    double Infinity = INFINITY;
    
    public EarMyExtended(){
        this("EarMyExtended");}
    
    public EarMyExtended(String nm){
        super(nm);
        addInport("inBeat");
        addTestInput("inBeat",new entity());
        addTestInput("inBeat",new entity(),1);
        addOutport("outNotSync");
        addOutport("outSync");
        addOutport("outReport");
    }
    
    public void initialize(){
        holdIn("Active",11.0);
    }
    
    public void deltint(){
        if(phaseIs("Active")){
            outReport = "";
            holdIn("Active",11.0);
        } else
            passivate();
    }
    
    String outReport = "";
    
    public void deltext(double e, message x){
        Continue(e);
        for(int i=0; i<x.getLength(); i++){
            if(this.messageOnPort(x, "inBeat", i)){
                if(phaseIs("Active" )){
                    processNotSyncOut();
                    outReport = "NotSync";
                    phase = "Active";
                }
            }
        }
    }
    
    public void deltcon(double e, message x) {
        for(int i=0; i<x.getLength(); i++){
            if(this.messageOnPort(x, "inBeat", i)){
                if(phaseIs("Active" )){
                    processNotSyncOut();
                    outReport = "Sync";
                    holdIn("Active",.0);
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
        if(phaseIs("Active")){
            m.add(makeContent("outReport",new entity(outReport)));
        }
        return m;
    }
}

