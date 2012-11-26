package Models.java;

import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class bidCoord extends ViewableAtomic{

double Infinity = INFINITY;

public bidCoord(){
this("bidCoord");}

public bidCoord(String nm){
super(nm);
	addInport("inJobDone");
	addTestInput("inJobDone",new entity());
	addTestInput("inJobDone",new entity(),1);
	addInport("inBid");
	addTestInput("inBid",new entity());
	addTestInput("inBid",new entity(),1);
	addInport("inJob");
	addTestInput("inJob",new entity());
	addTestInput("inJob",new entity(),1);
	addOutport("outJobDone");
	addOutport("outJob");
	addOutport("outfree?");
}

public void initialize(){
		passivate();
}

public void deltint(){
	if(phaseIs("send_Job")){
		passivate();
	}
	else
	if(phaseIs("send_y")){
		passivate();
	}
	else
	if(phaseIs("send_free")){
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
		if(this.messageOnPort(x, "inJob", i)){
			if(phaseIs("passive" )){
				processsend_free();
		holdIn("send_free",0.0);
			}
		}
		if(this.messageOnPort(x, "inJobDone", i)){
			if(phaseIs("passive" )){
				processsend_Job();
		holdIn("send_Job",0.0);
			}
			else if(phaseIs("waitForBid" )){
				processsend_Job();
		holdIn("send_Job",0.0);
			}
		}
		if(this.messageOnPort(x, "inBid", i)){
			if(phaseIs("waitForBid" )){
				processsend_y();
		holdIn("send_y",0.0);
			}
		}
	}
}

public void processsend_Job(){
	System.out.println("Processing: send_Job()");
}

public void processsend_y(){
	System.out.println("Processing: send_y()");
}

public void processsend_free(){
	System.out.println("Processing: send_free()");
}

public message out(){
	message m = new message();
	if(phaseIs("send_Job")){
		m.add(makeContent("outJobDone",new entity("JobDone")));
	}
	if(phaseIs("send_free")){
		m.add(makeContent("outfree?",new entity("free?")));
	}
	if(phaseIs("send_y")){
		m.add(makeContent("outJob",new entity("Job")));
	}
	return m;
}
}
