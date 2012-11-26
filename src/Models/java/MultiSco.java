package Models.java;
import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class MultiSco extends ViewableAtomic{

double Infinity = INFINITY;

public MultiSco(){
	addInport("injob3Done");
	addTestInput("injob3Done",new entity());
	addTestInput("injob3Done",new entity(),1);
	addInport("injob2Done");
	addTestInput("injob2Done",new entity());
	addTestInput("injob2Done",new entity(),1);
	addInport("injob1Done");
	addTestInput("injob1Done",new entity());
	addTestInput("injob1Done",new entity(),1);
	addInport("injob1Done	");
	addTestInput("injob1Done	",new entity());
	addTestInput("injob1Done	",new entity(),1);
	addInport("injobToProcess");
	addTestInput("injobToProcess",new entity());
	addTestInput("injobToProcess",new entity(),1);
	addOutport("outjobDone");
	addOutport("outjobDone");
	addOutport("outjobDone");
	addOutport("outjobDone");
	addOutport("outjobDone");
	addOutport("outjobDone");
	addOutport("outjobDone");
	addOutport("outjobDone");
	addOutport("outjobDone");
	addOutport("outjobDone");
	addOutport("outjobDone");
	addOutport("outjobDone");
	addOutport("outjobInProcess");
}

public void initialize(){
		passivate();
}

public void deltint(){
	if(phaseIs("send_out3_13")){
		passivateIn("proc1Busy");
	}
	else
	if(phaseIs("send_out3_3")){
		passivate();
	}
	else
	if(phaseIs("send_out2_23")){
		passivateIn("proc3Busy");
	}
	else
	if(phaseIs("send_out2_12")){
		passivateIn("proc1Busy");
	}
	else
	if(phaseIs("send_out2_2")){
		passivate();
	}
	else
	if(phaseIs("send_out1_13")){
		passivateIn("proc3Busy");
	}
	else
	if(phaseIs("send_out3_123")){
		passivateIn("proc12Busy");
	}
	else
	if(phaseIs("send_out1_12")){
		passivateIn("proc2Busy");
	}
	else
	if(phaseIs("send_out2_123")){
		passivateIn("proc13Busy");
	}
	else
	if(phaseIs("send_out1_1")){
		passivate();
	}
	else
	if(phaseIs("send_out1_123")){
		passivateIn("proc23Busy");
	}
	else
	if(phaseIs("passive")){
		passivate();
	}
	else
	if(phaseIs("send_out3_23")){
		passivateIn("proc2Busy");
	}
	else
	passivate();
}

public void deltext(double e, message x){
	Continue(e);
	for(int i=0; i<x.getLength(); i++){
		if(this.messageOnPort(x, "injobToProcess", i)){
			if(phaseIs("proc123Busy" )){
				processproc123Busy();
		passivateIn("proc123Busy");
			}
			else if(phaseIs("proc23Busy" )){
				processproc123Busy();
		passivateIn("proc123Busy");
			}
			else if(phaseIs("proc3Busy" )){
				processproc13Busy();
		passivateIn("proc13Busy");
			}
			else if(phaseIs("proc2Busy" )){
				processproc12Busy();
		passivateIn("proc12Busy");
			}
			else if(phaseIs("proc12Busy" )){
				processproc123Busy();
		passivateIn("proc123Busy");
			}
			else if(phaseIs("proc1Busy" )){
				processproc12Busy();
		passivateIn("proc12Busy");
			}
			else if(phaseIs("passive" )){
				processproc1Busy();
		passivateIn("proc1Busy");
			}
		}
		if(this.messageOnPort(x, "injob2Done", i)){
			if(phaseIs("passive" )){
				processpassive();
		passivate();
			}
			else if(phaseIs("proc23Busy" )){
				processsend_out2_23();
		holdIn("send_out2_23",0.0);
			}
			else if(phaseIs("proc12Busy" )){
				processsend_out2_12();
		holdIn("send_out2_12",0.0);
			}
			else if(phaseIs("proc2Busy" )){
				processsend_out2_2();
		holdIn("send_out2_2",0.0);
			}
			else if(phaseIs("proc123Busy" )){
				processsend_out2_123();
		holdIn("send_out2_123",0.0);
			}
		}
		if(this.messageOnPort(x, "injob1Done", i)){
			if(phaseIs("passive" )){
				processpassive();
		passivate();
			}
			else if(phaseIs("proc13Busy" )){
				processsend_out1_13();
		holdIn("send_out1_13",0.0);
			}
			else if(phaseIs("proc12Busy" )){
				processsend_out1_12();
		holdIn("send_out1_12",0.0);
			}
			else if(phaseIs("proc1Busy" )){
				processsend_out1_1();
		holdIn("send_out1_1",0.0);
			}
			else if(phaseIs("proc123Busy" )){
				processsend_out1_123();
		holdIn("send_out1_123",0.0);
			}
		}
		if(this.messageOnPort(x, "injob3Done", i)){
			if(phaseIs("proc23Busy" )){
				processsend_out3_23();
		holdIn("send_out3_23",0.0);
			}
			else if(phaseIs("proc13Busy" )){
				processsend_out3_13();
		holdIn("send_out3_13",0.0);
			}
			else if(phaseIs("proc3Busy" )){
				processsend_out3_3();
		holdIn("send_out3_3",0.0);
			}
			else if(phaseIs("passive" )){
				processpassive();
		passivate();
			}
			else if(phaseIs("proc123Busy" )){
				processsend_out3_123();
		holdIn("send_out3_123",0.0);
			}
		}
	}
}

public void processsend_out3_23(){
	System.out.println("Processing: send_out3_23()");
}

public void processsend_out3_13(){
	System.out.println("Processing: send_out3_13()");
}

public void processsend_out3_3(){
	System.out.println("Processing: send_out3_3()");
}

public void processpassive(){
	System.out.println("Processing: passive()");
}

public void processsend_out2_23(){
	System.out.println("Processing: send_out2_23()");
}

public void processsend_out2_12(){
	System.out.println("Processing: send_out2_12()");
}

public void processsend_out2_2(){
	System.out.println("Processing: send_out2_2()");
}

public void processsend_out1_13(){
	System.out.println("Processing: send_out1_13()");
}

public void processsend_out1_12(){
	System.out.println("Processing: send_out1_12()");
}

public void processsend_out1_1(){
	System.out.println("Processing: send_out1_1()");
}

public void processproc123Busy(){
	System.out.println("Processing: proc123Busy()");
}

public void processproc13Busy(){
	System.out.println("Processing: proc13Busy()");
}

public void processproc12Busy(){
	System.out.println("Processing: proc12Busy()");
}

public void processproc1Busy(){
	System.out.println("Processing: proc1Busy()");
}

public void processsend_out3_123(){
	System.out.println("Processing: send_out3_123()");
}

public void processsend_out2_123(){
	System.out.println("Processing: send_out2_123()");
}

public void processsend_out1_123(){
	System.out.println("Processing: send_out1_123()");
}

public message out(){
	message m = new message();
	if(phaseIs("proc123Busy")){
		m.add(makeContent("outjobInProcess",new entity("jobInProcess")));
	}
	if(phaseIs("send_out2_12")){
		m.add(makeContent("outjobDone",new entity("jobDone")));
	}
	if(phaseIs("proc1Busy")){
	}
	if(phaseIs("send_out1_13")){
	}
	if(phaseIs("send_out1_12")){
	}
	if(phaseIs("send_out3_23")){
	}
	if(phaseIs("send_out1_1")){
	}
	if(phaseIs("proc13Busy")){
	}
	if(phaseIs("proc12Busy")){
	}
	if(phaseIs("send_out3_123")){
	}
	if(phaseIs("send_out2_23")){
	}
	if(phaseIs("send_out2_2")){
	}
	if(phaseIs("send_out2_123")){
	}
	if(phaseIs("send_out3_13")){
	}
	if(phaseIs("send_out1_123")){
	}
	if(phaseIs("send_out3_3")){
	}
	return m;
}
}
