package Models.java;
import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class IABM extends ViewableAtomic{

double Infinity = INFINITY;

public IABM(){
this("IABM");}

public IABM(String nm){
super(nm);
	addInport("inJobResults");
	addTestInput("inJobResults",new entity());
	addTestInput("inJobResults",new entity(),1);
	addInport("inBid");
	addTestInput("inBid",new entity());
	addTestInput("inBid",new entity(),1);
	addInport("inSensorDataRequest");
	addTestInput("inSensorDataRequest",new entity());
	addTestInput("inSensorDataRequest",new entity(),1);
	addOutport("outJobResults");
	addOutport("outJob");
	addOutport("outSubmitBid");
}

public void initialize(){
		passivate();
}

public void deltint(){
	if(phaseIs("sendJobResults")){
		passivate();
	}
	else
	if(phaseIs("sendJob")){
		passivateIn("waitForJob");
	}
	else
	if(phaseIs("sendSubmitBid")){
		passivateIn("waitForBid");
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
		if(this.messageOnPort(x, "inJobResults", i)){
			if(phaseIs("waitForJob" )){
				processsendJobResults();
		holdIn("sendJobResults",0.0);
			}
		}
		if(this.messageOnPort(x, "inSensorDataRequest", i)){
			if(phaseIs("passive" )){
				processsendSubmitBid();
		holdIn("sendSubmitBid",0.0);
			}
		}
		if(this.messageOnPort(x, "inBid", i)){
			if(phaseIs("waitForBid" )){
				processsendJob();
		holdIn("sendJob",0.0);
			}
		}
	}
}

public void processsendJobResults(){
	System.out.println("Processing: sendJobResults()");
}

public void processsendJob(){
	System.out.println("Processing: sendJob()");
}

public void processsendSubmitBid(){
	System.out.println("Processing: sendSubmitBid()");
}

public message out(){
	message m = new message();
	if(phaseIs("sendJob")){
		m.add(makeContent("outJob",new entity("Job")));
	}
	if(phaseIs("sendJobResults")){
		m.add(makeContent("outJobResults",new entity("JobResults")));
	}
	if(phaseIs("sendSubmitBid")){
		m.add(makeContent("outSubmitBid",new entity("SubmitBid")));
	}
	return m;
}
}
