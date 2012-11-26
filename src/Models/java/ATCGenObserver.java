package Models.java;

import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class ATCGenObserver extends ViewableAtomic{

double Infinity = INFINITY;

public ATCGenObserver(){
this("ATCGenObserver");}

public ATCGenObserver(String nm){
super(nm);
	addInport("inRRCommand");
	addTestInput("inRRCommand",new entity());
	addTestInput("inRRCommand",new entity(),1);
	addInport("inDoRRObs");
	addTestInput("inDoRRObs",new entity());
	addTestInput("inDoRRObs",new entity(),1);
	addOutport("outPass");
}

public void initialize(){
		passivate();
}

public void deltint(){
	if(phaseIs("waitForRR")){
		holdIn("sendFail",1.0);
	}
	else
	if(phaseIs("sendPass")){
		passivate();
	}
	else
	if(phaseIs("sendFail")){
		passivate();
	}
	else
	if(phaseIs("passive")){
		passivate();
	}
	else
	passivate();
}

public void deltext(double e, message x){
	Continue(e);
	for(int i=0; i<x.getLength(); i++){
		if(this.messageOnPort(x, "inDoRRObs", i)){
			if(phaseIs("passive" )){
				processwaitForRR();
		holdIn("waitForRR",10.0);
			}
		}
		if(this.messageOnPort(x, "inRRCommand", i)){
			if(phaseIs("waitForRR" )){
				processsendPass();
		holdIn("sendPass",1.0);
			}
		}
	}
}

public void processsendPass(){
	System.out.println("Processing: sendPass()");
}

public void processwaitForRR(){
	System.out.println("Processing: waitForRR()");
}

public message out(){
	message m = new message();
	if(phaseIs("sendPass")){
		m.add(makeContent("outPass",new entity("Pass")));
	}
	return m;
}
}
