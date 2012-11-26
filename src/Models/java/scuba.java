package Models.java;
import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class scuba extends ViewableAtomic{

double Infinity = INFINITY;

public scuba(){
this("scuba");}

public scuba(String nm){
super(nm);
	addInport("inComeUp");
	addTestInput("inComeUp",new entity());
	addTestInput("inComeUp",new entity(),1);
	addInport("inGoDive");
	addTestInput("inGoDive",new entity());
	addTestInput("inGoDive",new entity(),1);
	addOutport("outBackUp");
}

public void initialize(){
		passivateIn("surface");
}

public void deltint(){
	if(phaseIs("at5")){
		passivateIn("surface");
	}
	else
	if(phaseIs("at20")){
		holdIn("at5",5.0);
	}
	else
	if(phaseIs("at40")){
		holdIn("at20",20.0);
	}
	else
	if(phaseIs("at60")){
		holdIn("at40",40.0);
	}
	else
	if(phaseIs("surface")){
		passivateIn("surface");
	}
	else
	passivate();
}

public void deltext(double e, message x){
	Continue(e);
	for(int i=0; i<x.getLength(); i++){
		if(this.messageOnPort(x, "inComeUp", i)){
			if(phaseIs("at40" )){
				processat5();
		holdIn("at5",5.0);
			}
			else if(phaseIs("at20" )){
				processat5();
		holdIn("at5",5.0);
			}
			else if(phaseIs("at60" )){
				processat5();
		holdIn("at5",5.0);
			}
		}
		if(this.messageOnPort(x, "inGoDive", i)){
			if(phaseIs("surface" )){
				processat60();
		holdIn("at60",60.0);
			}
		}
	}
}

public void processat5(){
	System.out.println("Processing: at5()");
}

public void processat60(){
	System.out.println("Processing: at60()");
}

public message out(){
	message m = new message();
	if(phaseIs("at5")){
		m.add(makeContent("outBackUp",new entity("BackUp")));
	}
	return m;
}
}
