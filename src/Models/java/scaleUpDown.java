package Models.java;

import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class scaleUpDown extends ViewableAtomic{

double Infinity = INFINITY;

public scaleUpDown(){
this("scaleUpDown");}

public scaleUpDown(String nm){
super(nm);
	addInport("GoUp");
	addTestInput("GoUp",new entity());
	addTestInput("GoUp",new entity(),1);
	addOutport("outDone");
}

public void initialize(){
		passivateIn("waitForStart");
}

public void deltint(){
	if(phaseIs("noteG")){
		holdIn("noteA",1.0);
	}
	else
	if(phaseIs("noteF")){
		holdIn("noteG",1.0);
	}
	else
	if(phaseIs("noteE")){
		holdIn("noteF",1.0);
	}
	else
	if(phaseIs("noteD")){
		holdIn("noteE",1.0);
	}
	else
	if(phaseIs("noteC")){
		holdIn("noteD",1.0);
	}
	else
	if(phaseIs("waitForStart")){
		passivateIn("waitForStart");
	}
	else
	if(phaseIs("noteB")){
		holdIn("noteC",1.0);
	}
	else
	if(phaseIs("noteA")){
		holdIn("noteB",1.0);
	}
	else
	passivate();
}

public void deltext(double e, message x){
	Continue(e);
	for(int i=0; i<x.getLength(); i++){
		if(this.messageOnPort(x, "inGoUp", i)){
			if(phaseIs("waitForStart" )){
				processnoteC();
		holdIn("noteC",1.0);
			}
		}
	}
}

public void processnoteC(){
	System.out.println("Processing: noteC()");
}

public message out(){
	message m = new message();
	if(phaseIs("noteB")){
		m.add(makeContent("outDone",new entity("Done")));
	}
	return m;
}
}
