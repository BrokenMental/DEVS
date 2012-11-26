package Models.java;

import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class ATCGenGenr extends ViewableAtomic{

double Infinity = INFINITY;

public ATCGenGenr(){
this("ATCGenr");}

public ATCGenGenr(String nm){
super(nm);
	addOutport("outDoRRObs");
	addOutport("outDataSpec");
}

public void initialize(){
		holdIn("active",10.0);
}

public void deltint(){
	if(phaseIs("sendDoRRObs")){
		holdIn("active",10.0);
	}
	else
	if(phaseIs("sendSpec")){
		holdIn("sendDoRRObs",1.0);
	}
	else
	if(phaseIs("active")){
		holdIn("sendSpec",1.0);
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
	if(phaseIs("sendSpec")){
		m.add(makeContent("outDoRRObs",new entity("DoRRObs")));
	}
	if(phaseIs("active")){
		m.add(makeContent("outDataSpec",new entity("DataSpec")));
	}
	return m;
}
}
