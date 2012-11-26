package Models.java;

import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class Drum extends ViewableAtomic{

double Infinity = INFINITY;

public Drum(){
this("Drum");}

public Drum(String nm){
super(nm);
	addOutport("outBeat");
}

public void initialize(){
		holdIn("Active",10.0);
}

public void deltint(){
	if(phaseIs("Active")){
		holdIn("Active",10.0);
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
	if(phaseIs("Active")){
		m.add(makeContent("outBeat",new entity("Beat")));
        System.out.println("Drum: Send outBeat");
	}
	return m;
}
}
