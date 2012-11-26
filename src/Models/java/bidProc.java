package Models.java;
import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class bidProc extends ViewableAtomic{

double Infinity = INFINITY;

public bidProc(){
this("bidProc");}

public bidProc(String nm){
super(nm);
	addInport("inJob");
	addTestInput("inJob",new entity());
	addTestInput("inJob",new entity(),1);
	addInport("infree?");
	addTestInput("infree?",new entity());
	addTestInput("infree?",new entity(),1);
	addOutport("outJobDone");
	addOutport("outBid");

}

public void initialize(){
		passivate();
}

public void deltint(){
	if(phaseIs("busy")){
		passivate();
	}
	else
	if(phaseIs("bid")){
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
				processbusy();
		holdIn("busy",5.0);
			}
		}
		if(this.messageOnPort(x, "infree?", i)){
			if(phaseIs("passive" )){
				processbid();
		holdIn("bid",0.1);
			}
		}
	}
}

public void processbusy(){
	System.out.println("Processing: busy()");
}

public void processbid(){
	System.out.println("Processing: bid()");
}

public message out(){
	message m = new message();
	if(phaseIs("bid")){
		m.add(makeContent("outBid",new entity("Bid")));
	}
	if(phaseIs("busy")){

		m.add(makeContent("outJobDone",new entity("JobDone")));
	}
	return m;
}
}
