package Models.java;

import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class procFinish extends ViewableAtomic{

double Infinity = INFINITY;

public procFinish(){
this("procFinish");}

public procFinish(String nm){
super(nm);
	addInport("ininStop");
	addTestInput("ininStop",new entity());
	addTestInput("ininStop",new entity(),1);
	addInport("ininJob");
	addTestInput("ininJob",new entity());
	addTestInput("ininJob",new entity(),1);
	addOutport("outThatsit");
	addOutport("outJob");
}

public void initialize(){
		passivateIn("waitForJob");
}

public void deltint(){
	if(phaseIs("finishing")){
		passivate();
	}
	else
	if(phaseIs("busy")){
		passivateIn("waitForJob");
	}
	else
	if(phaseIs("waitForJob")){
		passivateIn("waitForJob");
	}
	else
	passivate();
}

public void deltext(double e, message x){
	Continue(e);
	for(int i=0; i<x.getLength(); i++){
		if(this.messageOnPort(x, "ininJob", i)){
			if(phaseIs("waitForJob" )){
				processbusy();
		holdIn("busy",10.0);
			}
		}
		if(this.messageOnPort(x, "ininStop", i)){
			if(phaseIs("waitForJob" )){
				processpassive();
		passivate();
			}
			else if(phaseIs("busy" )){
				processfinishing();
				phase = "finishing";
			}
		}
	}
}

public void processpassive(){
	System.out.println("Processing: passive()");
}

public void processfinishing(){
	System.out.println("Processing: finishing()");
}

public void processbusy(){
	System.out.println("Processing: busy()");
}

public message out(){
	message m = new message();
	if(phaseIs("busy")){
		m.add(makeContent("outJob",new entity("Job")));
	}
	if(phaseIs("finishing")){
		m.add(makeContent("outThatsit",new entity("Thatsit")));
	}
	return m;
}
}
