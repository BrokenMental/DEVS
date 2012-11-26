package Models.java;

import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class SUT extends ViewableAtomic{

double Infinity = INFINITY;

public SUT(){
this("SUT");}

public SUT(String nm){
super(nm);
	addInport("inRadarTrack");
	addTestInput("inRadarTrack",new entity());
	addTestInput("inRadarTrack",new entity(),1);
	addOutport("outRRCommand");
}

public void initialize(){
		passivateIn("waitForTrack");
}

public void deltint(){
	if(phaseIs("sendRR")){
		passivateIn("waitForTrack");
	}
	else
	if(phaseIs("waitForTrack")){
		passivateIn("waitForTrack");
	}
	else
	passivate();
}

public void deltext(double e, message x){
	Continue(e);
	for(int i=0; i<x.getLength(); i++){
		if(this.messageOnPort(x, "inRadarTrack", i)){
			if(phaseIs("waitForTrack" )){
				processsendRR();
		holdIn("sendRR",1.0);
			}
		}
	}
}

public void processsendRR(){
	System.out.println("Processing: sendRR()");
}

public message out(){
	message m = new message();
	if(phaseIs("sendRR")){
		m.add(makeContent("outRRCommand",new entity("RRCommand")));
	}
	return m;
}
}
