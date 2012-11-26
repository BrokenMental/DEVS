package Models.java;
import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class waitForTwo extends ViewableAtomic{

double Infinity = INFINITY;

public waitForTwo(){
	addInport("inMsg");
	addTestInput("inMsg",new entity());
	addTestInput("inMsg",new entity(),1);
	addOutport("outFail:2ndNotRecvd");
	addOutport("outPass:RcvdTwo");
}

public void initialize(){
		passivate();
}

public void deltint(){
	if(phaseIs("recvd2nd")){
		passivate();
	}
	else
	if(phaseIs("recvdFirst")){
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
		if(this.messageOnPort(x, "inMsg", i)){
			if(phaseIs("recvdFirst" )){
				processrecvd2nd();
		holdIn("recvd2nd",0.0);
			}
			else if(phaseIs("passive" )){
				processrecvdFirst();
		holdIn("recvdFirst",20.0);
			}
		}
	}
}

public void processrecvd2nd(){
	System.out.println("Processing: recvd2nd()");
}

public void processrecvdFirst(){
	System.out.println("Processing: recvdFirst()");
}

public message out(){
	message m = new message();
	if(phaseIs("recvdFirst")){
		m.add(makeContent("outFail:2ndNotRecvd",new entity("Fail:2ndNotRecvd")));
	}
	if(phaseIs("recvd2nd")){
		m.add(makeContent("outPass:RcvdTwo",new entity("Pass:RcvdTwo")));
	}
	return m;
}
}
