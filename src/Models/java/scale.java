package Models.java;

import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class scale extends ViewableAtomic{

double Infinity = INFINITY;

public scale(){
this("scale");}

public scale(String nm){
super(nm);
	addOutport("outG");
	addOutport("outF");
	addOutport("outE");
	addOutport("outD");
	addOutport("outC");
	addOutport("outB");
	addOutport("outA");
}

public void initialize(){
		holdIn("a",1.0);
}

public void deltint(){
	if(phaseIs("f")){
		holdIn("g",1.0);
	}
	else
	if(phaseIs("e")){
		holdIn("f",1.0);
	}
	else
	if(phaseIs("d")){
		holdIn("e",1.0);
	}
	else
	if(phaseIs("c")){
		holdIn("d",1.0);
	}
	else
	if(phaseIs("b")){
		holdIn("c",1.0);
	}
	else
	if(phaseIs("a")){
		holdIn("b",1.0);
	}
	else
	if(phaseIs("g")){
		holdIn("a",1.0);
	}
	else
	passivate();
}

public void deltext(double e, message x){
	Continue(e);
	for(int i=0; i<x.getLength(); i++){
	}
}

public message out(){
	message m = new message();
	if(phaseIs("b")){
		m.add(makeContent("outB",new entity("B")));
	}
	if(phaseIs("a")){
		m.add(makeContent("outA",new entity("A")));
	}
	if(phaseIs("g")){
		m.add(makeContent("outG",new entity("G")));
	}
	if(phaseIs("f")){
		m.add(makeContent("outF",new entity("F")));
	}
	if(phaseIs("e")){
		m.add(makeContent("outE",new entity("E")));
	}
	if(phaseIs("d")){
		m.add(makeContent("outD",new entity("D")));
	}
	if(phaseIs("c")){
		m.add(makeContent("outC",new entity("C")));
	}
	return m;
}
}
