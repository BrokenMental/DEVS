package Models.java;

import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class simSim extends ViewableAtomic{

double Infinity = INFINITY;

public simSim(){
this("simSim");}

public simSim(String nm){
super(nm);
}

public void initialize(){
	passivate();
}

public void deltint(){
	passivate();
}

public void deltext(double e, message x){
	Continue(e);
	for(int i=0; i<x.getLength(); i++){
	}
}

public message out(){
	message m = new message();
	return m;
}
}
