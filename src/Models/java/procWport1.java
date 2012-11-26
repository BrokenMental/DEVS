package Models.java;

import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class procWport1 extends ViewableAtomic{

double Infinity = INFINITY;

public procWport1(){
this("procWport1");}

public procWport1(String nm){
super(nm);
	addInport("inJ1");
	addTestInput("inJ1",new entity());
	addTestInput("inJ1",new entity(),1);
	addOutport("outJob1");
}

public void initialize(){
		passivate();
}

public void deltint(){
	if(phaseIs("bust")){
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
		if(this.messageOnPort(x, "inJ1", i)){
			if(phaseIs("passive" )){
				processbust();
		holdIn("bust",7.0);
			}
		}
	}
}

public void processbust(){
	System.out.println("Processing: bust()");
}

public message out(){
	message m = new message();
	if(phaseIs("bust")){
		m.add(makeContent("outJob1",new entity("Job1")));
	}
	return m;
}
}
