package Models.java;
import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class pulseGenr extends ViewableAtomic{

double Infinity = INFINITY;

public pulseGenr(){
	addInport("inStop");
	addTestInput("inStop",new entity());
	addTestInput("inStop",new entity(),1);
	addInport("inStart");
	addTestInput("inStart",new entity());
	addTestInput("inStart",new entity(),1);
	addOutport("outPulse");
	addOutport("outPulse");
}

public void initialize(){
		passivate();
}

public void deltint(){
	if(phaseIs("inprocess")){
		passivate();
	}
	else
	if(phaseIs("active")){
		holdIn("active",5.0);
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
		if(this.messageOnPort(x, "inStop", i)){
			if(phaseIs("active" )){
				processinprocess();
				phase = "inprocess";
			}
		}
		if(this.messageOnPort(x, "inStart", i)){
			if(phaseIs("passive" )){
				processactive();
		holdIn("active",5.0);
			}
		}
	}
}

public void processinprocess(){
	System.out.println("Processing: inprocess()");
}

public void processactive(){
	System.out.println("Processing: active()");
}

public message out(){
	message m = new message();
	if(phaseIs("inprocess")){
		m.add(makeContent("outPulse",new entity("Pulse")));
	}
	if(phaseIs("active")){
		m.add(makeContent("outPulse",new entity("Pulse")));
	}
	return m;
}
}
