package Models.java;

import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class procFinisher extends ViewableAtomic{

double Infinity = INFINITY;

public procFinisher(){
this("procFinisher");}

public procFinisher(String nm){
super(nm);
	addInport("inStop");
	addTestInput("inStop",new entity());
	addTestInput("inStop",new entity(),1);
	addInport("inStop");
	addTestInput("inStop",new entity());
	addTestInput("inStop",new entity(),1);
	addInport("inJob");
	addTestInput("inJob",new entity());
	addTestInput("inJob",new entity(),1);
	addOutport("outJob");
	addOutport("outJob");
	addOutport("outThatsit");
}

public void initialize(){
		holdIn("busy",10.0);
}

public void deltint(){
	if(phaseIs("waitForJob")){
		holdIn("waitForJob",0.0);
	}
	else
	if(phaseIs("finishing")){
		passivate();
	}
	else
	if(phaseIs("busy")){
		holdIn("waitForJob",0.0);
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

public void processbusy(){
	System.out.println("Processing: busy()");
}

public void processpassive(){
	System.out.println("Processing: passive()");
}

public void processfinishing(){
	System.out.println("Processing: finishing()");
}

public message out(){
	message m = new message();
	if(phaseIs("busy")){
		m.add(makeContent("outJob",new entity("Job")));
	}
	if(phaseIs("finishing")){
		m.add(makeContent("outThatsit",new entity("Thatsit")));
		m.add(makeContent("outJob",new entity("Job")));
	}
	return m;
}
}
