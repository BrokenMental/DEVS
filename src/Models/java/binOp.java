package Models.java;

import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class binOp extends ViewableAtomic{
    
    double Infinity = INFINITY;
    intEnt left, right;
    
    public binOp(){
        this("binOp");}
    
    public binOp(String nm){
        super(nm);
        addInport("inRight");
        addTestInput("inRight",new intEnt(1));
        addTestInput("inRight",new intEnt(1),1);
        addInport("inLeft");
        addTestInput("inLeft",new intEnt(1));
        addTestInput("inLeft",new intEnt(1),1);
        addOutport("outResult");
    }
    
    public void initialize(){
        passivateIn("none");
    }
    
    public void deltint(){
        if(phaseIs("bothReceived")){
            passivateIn("none");
        } else
            if(phaseIs("none")){
            passivateIn("none");
            } else
                passivate();
    }
    
    
    public void deltext(double e, message x){
        Continue(e);
        for(int i=0; i<x.getLength(); i++){
            if(this.messageOnPort(x, "inLeft", i)){
                intEnt val = (intEnt)getEntityOnPort(x,"inLeft");
                left = val;
                if(phaseIs("rightReceived" )){
                    holdIn("bothReceived",0.0);
                } else if(phaseIs("none" )){
                    passivateIn("leftReceived");
                }
            }
            if(this.messageOnPort(x, "inRight", i)){
                intEnt val = (intEnt)getEntityOnPort(x,"inRight");
                right = val;
                if(phaseIs("leftReceived" )){   
                    holdIn("bothReceived",0.0);
                } else if(phaseIs("none" )){
                    passivateIn("rightReceived");
                }
            }
        }
    }
    
   
    
    public message out(){
        message m = new message();
        if(phaseIs("bothReceived")){
            m.add(makeContent("outResult",new intEnt(left.getv()+right.getv())));
        }
        return m;
    }
}
