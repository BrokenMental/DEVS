package Models.java;

import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class SensorSim extends ViewableAtomic{

double Infinity = INFINITY;

public SensorSim(){
this("SensorSim");}

public SensorSim(String nm){
super(nm);
	addInport("inDataSpec");
	addTestInput("inDataSpec",new entity());
	addTestInput("inDataSpec",new entity(),1);
	addOutport("outRadarTrack");
}

public void initialize(){
		holdIn("sendSensorTrack",1.0);
}

public void deltint(){
	if(phaseIs("sendSensorTrack")){
		passivateIn("waitForDataSpec");
	}
	else
	passivate();
}

public void deltext(double e, message x){
	Continue(e);
	for(int i=0; i<x.getLength(); i++){
		if(this.messageOnPort(x, "inDataSpec", i)){
			if(phaseIs("waitForDataSpec" )){
				processsendSensorTrack();
		holdIn("sendSensorTrack",1.0);
			}
		}
	}
}

public void processsendSensorTrack(){
	System.out.println("Processing: sendSensorTrack()");
}

public message out(){
	message m = new message();
	if(phaseIs("sendSensorTrack")){
		m.add(makeContent("outRadarTrack",new entity("RadarTrack")));
	}
	return m;
}
}
