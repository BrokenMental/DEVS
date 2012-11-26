package Models.java;

import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class simCoord extends ViewableAtomic{

double Infinity = INFINITY;

public simCoord(){
this("simCoord");}

public simCoord(String nm){
super(nm);
	addInport("inOut");
	addTestInput("inOut",new entity());
	addTestInput("inOut",new entity(),1);
	addInport("inTN");
	addTestInput("inTN",new entity());
	addTestInput("inTN",new entity(),1);
	addOutport("outApplyDelt");
	addOutport("outNextTN");
	addOutport("outGetOut");
}

public void initialize(){
		holdIn("requestTN",1.0);
}

public void deltint(){
	if(phaseIs("sendGetOut")){
		passivateIn("waitForOut");
	}
	else
	if(phaseIs("sendApplyDelt")){
		holdIn("requestTN",1.0);
	}
	else
	if(phaseIs("requestTN")){
		passivateIn("waitForTN");
	}
	else
	passivate();
}

public void deltext(double e, message x){
	Continue(e);
	for(int i=0; i<x.getLength(); i++){
		if(this.messageOnPort(x, "inTN", i)){
			if(phaseIs("waitForTN" )){
				processsendGetOut();
		holdIn("sendGetOut",1.0);
			}
		}
		if(this.messageOnPort(x, "inOut", i)){
			if(phaseIs("waitForOut" )){
				processsendApplyDelt();
		holdIn("sendApplyDelt",1.0);
			}
		}
	}
}

public void processsendApplyDelt(){
	System.out.println("Processing: sendApplyDelt()");
}

public void processsendGetOut(){
	System.out.println("Processing: sendGetOut()");
}

public message out(){
	message m = new message();
	if(phaseIs("sendGetOut")){
		m.add(makeContent("outGetOut",new entity("GetOut")));
	}
	if(phaseIs("requestTN")){
		m.add(makeContent("outNextTN",new entity("NextTN")));
	}
	if(phaseIs("sendApplyDelt")){
		m.add(makeContent("outApplyDelt",new entity("ApplyDelt")));
	}
	return m;
}
}
