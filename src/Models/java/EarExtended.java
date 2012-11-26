/*
 * Ear.java
 *
 * Created on October 24, 2007, 3:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Models.java;

import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class EarExtended extends ViewableAtomic{
    
    double Infinity = INFINITY;
    int flag;
    
    public EarExtended(){
        this("EarExtended");}
    
    public EarExtended(String nm){
        super(nm);
        addInport("inBeat");
        addTestInput("inBeat",new entity());
        addTestInput("inBeat",new entity(),1);
        addOutport("outNotSync");
        addOutport("outSync");
    }
    
    public void initialize(){
        flag = 0;
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
                if((phaseIs("Active" )) && flag == 0) {
                    System.out.println("regular e = " + e);
                    processNotSyncOut();
                    phase = "NotSyncOut";
                } else if(phaseIs("Active" )){
                    if(flag == 1){
                        System.out.println("deltcon e = " + e);
                        processSyncOut();
                        holdIn("SyncOut",0.0);
                        flag = 0;
                    }
                }
            }
        }
    }
    public void deltcon(double e, message x) {
        flag = 1;
        System.out.println("deltcon " + flag);
        //  System.outprint(flag);
// external transistion followed by internal transition
        //    deltext (0, x);
        //    deltint();
        
// internal transition followed by an external transition
        deltint();
        deltext(0, x);
        
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
        }
        if(phaseIs("SyncOut")){
            m.add(makeContent("outSync",new entity("Sync")));
        }
        return m;
    }
}
