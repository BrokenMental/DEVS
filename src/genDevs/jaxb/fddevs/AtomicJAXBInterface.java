package genDevs.jaxb.fddevs;

import java.util.*;

import com.acims.fddevs.LambdaType;
import com.acims.fddevs.TaType;

public interface AtomicJAXBInterface {

	public void setModelName(String name);
	public void setHost(String ip);
	public void setInports(String[] inports);
	public void setOutports(String[] outports);
	public void setStates(String[] states);
	public void setDeltint(Hashtable<String, Transition> deltints);
	public void setDeltext(Hashtable<String, ExtTransition> deltexts);
	public void setLamda(Hashtable<String, LambdaType> lamdas);
	public void setTimeAdvance(Hashtable<String, TaType> tas);
	
}
