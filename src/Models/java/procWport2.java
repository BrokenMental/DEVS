package Models.java;

import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class procWport2 extends ViewableAtomic{

double Infinity = INFINITY;

public procWport2(){
this("procWport2");}

public procWport2(String nm){
super(nm);
	addInport("inJ2");
	addTestInput("inJ2",new entity());
	addTestInput("inJ2",new entity(),1);
	addOutport("outJob2");
}

public void initialize(){
		passivate();
}

public void deltint(){
	if(phaseIs("busy")){
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
		if(this.messageOnPort(x, "inJ2", i)){
			if(phaseIs("passive" )){
				processbusy();
		holdIn("busy",8.0);
			}
		}
	}
}

public void processbusy(){
	System.out.println("Processing: busy()");
}

public message out(){
	message m = new message();
	if(phaseIs("busy")){
		m.add(makeContent("outJob2",new entity("Job2")));
	}
	return m;
}
}
