package Models.java;

import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class pipeCoord extends ViewableAtomic{

double Infinity = INFINITY;

public pipeCoord(){
this("pipeCoord");}

public pipeCoord(String nm){
super(nm);
	addInport("inJob2");
	addTestInput("inJob2",new entity());
	addTestInput("inJob2",new entity(),1);
	addInport("inJob1");
	addTestInput("inJob1",new entity());
	addTestInput("inJob1",new entity(),1);
	addInport("inJob");
	addTestInput("inJob",new entity());
	addTestInput("inJob",new entity(),1);
	addOutport("outJobDone");
	addOutport("outJ3");
	addOutport("outJ2");
	addOutport("outJ1");
}

public void initialize(){
		passivate();
}

public void deltint(){
	if(phaseIs("sendOut")){
		passivate();
	}
	else
	if(phaseIs("sendJ3")){
		passivate();
	}
	else
	if(phaseIs("sendJ2")){
		passivate();
	}
	else
	if(phaseIs("sendJ1")){
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
		if(this.messageOnPort(x, "inJob", i)){
			if(phaseIs("passive" )){
				processsendJ1();
		holdIn("sendJ1",0.0);
			}
		}
		if(this.messageOnPort(x, "inJob2", i)){
			if(phaseIs("passive" )){
				processsendJ3();
		holdIn("sendJ3",0.0);
			}
		}
		if(this.messageOnPort(x, "inJob1", i)){
			if(phaseIs("passive" )){
				processsendJ2();
		holdIn("sendJ2",0.0);
			}
		}
	}
}

public void processsendJ3(){
	System.out.println("Processing: sendJ3()");
}

public void processsendJ2(){
	System.out.println("Processing: sendJ2()");
}

public void processsendJ1(){
	System.out.println("Processing: sendJ1()");
}

public message out(){
	message m = new message();
	if(phaseIs("sendOut")){
		m.add(makeContent("outJobDone",new entity("JobDone")));
	}
	if(phaseIs("sendJ3")){
		m.add(makeContent("outJ3",new entity("J3")));
	}
	if(phaseIs("sendJ2")){
		m.add(makeContent("outJ2",new entity("J2")));
	}
	if(phaseIs("sendJ1")){
		m.add(makeContent("outJ1",new entity("J1")));
	}
	return m;
}
}
