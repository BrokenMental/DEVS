package Models.java;

import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class Coordinator extends ViewableAtomic{

double Infinity = INFINITY;

public Coordinator(){
this("Coordinator");}

public Coordinator(String nm){
super(nm);
	addInport("inSensorData");
	addTestInput("inSensorData",new entity());
	addTestInput("inSensorData",new entity(),1);
	addOutport("outbidSubmit");
}

public void initialize(){
		holdIn("sendBidSubmit",1.0);
}

public void deltint(){
	if(phaseIs("passive")){
		passivate();
	}
	else
	if(phaseIs("sendBidSubmit")){
		holdIn("waitForBid",0.0);
	}
	else
	passivate();
}

public void deltext(double e, message x){
	Continue(e);
	for(int i=0; i<x.getLength(); i++){
		if(this.messageOnPort(x, "ininSensorData", i)){
			if(phaseIs("passive" )){
				processsendBidSubmit();
		holdIn("sendBidSubmit",1.0);
			}
		}
	}
}

public void processsendBidSubmit(){
	System.out.println("Processing: sendBidSubmit()");
}

public message out(){
	message m = new message();
	if(phaseIs("sendBidSubmit")){
		m.add(makeContent("outbidSubmit",new entity("bidSubmit")));
	}
	return m;
}
}
