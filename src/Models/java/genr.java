package Models.java;
import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class  genr extends ViewableAtomic{

double Infinity = INFINITY;

public genr(){
	addInport("inJob");
	addTestInput("inJob",new entity());
	addTestInput("inJob",new entity(),1);
	addInport("inStart");
	addTestInput("inStart",new entity());
	addTestInput("inStart",new entity(),1);
	addOutport("outActive");
	addOutport("outResult");
}

public void initialize(){
		passivate();
}

public void deltint(){
	if(phaseIs("active")){
		holdIn("processing",1.0);
	}
	else
	if(phaseIs("processing")){
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
			if(phaseIs("active" )){
				processprocessing();
		holdIn("processing",1.0);
			}
		}
		if(this.messageOnPort(x, "inStart", i)){
			if(phaseIs("passive" )){
				processactive();
		holdIn("active",15.0);
			}
		}
	}
}

public void processprocessing(){
	System.out.println("Processing: processing()");
}

public void processactive(){
	System.out.println("Processing: active()");
}

public message out(){
	message m = new message();
	if(phaseIs("processing")){
		m.add(makeContent("outResult",new entity("Result")));
		m.add(makeContent("outResult",new entity("Result")));
	}
	if(phaseIs("active")){
		m.add(makeContent("outActive",new entity("Active")));
		m.add(makeContent("outActive",new entity("Active")));
	}
	return m;
}
}
