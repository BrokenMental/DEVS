/**
 * 
 */
package app;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import com.acims.fddevs.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Saurabh
 *
 */
public class jaxbUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
		
			JAXBContext jc = JAXBContext.newInstance("com.acims.fddevs");
			Unmarshaller unm = jc.createUnmarshaller();
		
			JAXBElement element = (JAXBElement)
				unm.unmarshal(new File("xmlModels/fddevsXmlInstance.xml"));
			
			AtomicType atomic = (AtomicType) element.getValue();
			String modelName = atomic.getModelName();
			String host = atomic.getHost();
			
			System.out.println("ModelName: "+modelName);
			System.out.println("HostName: "+host);
			
			ArrayList list = (ArrayList)atomic.getInportsAndOutportsAndStates();
			for(int i=0; i<list.size(); i++) {
				//System.out.println(list.get(i));
				Object obj = (Object)list.get(i);
				if(obj instanceof InportType) {
					processInportType((InportType)obj);
				}
				else if(obj instanceof OutportType) {
					processOutportType((OutportType)obj);
				}
				else if (obj instanceof DeltintType) {
					processDeltintType((DeltintType)obj);
				}
				else if(obj instanceof DeltextType) {
					processDeltextType((DeltextType)obj);
				}
				else if(obj instanceof StatesType) {
					processStatesType((StatesType)obj);
				}
				else if(obj instanceof TimeAdvanceType) {
					processTimeAdvanceType((TimeAdvanceType)obj);
				}
				else if(obj instanceof LamdaAllType) {
					processLamdaAllType((LamdaAllType)obj);
				}
				else
					System.out.println("Can not process type: "+obj.toString());
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void processLamdaAllType(LamdaAllType type) {
		// TODO Auto-generated method stub
		
	}

	private static void processTimeAdvanceType(TimeAdvanceType type) {
		// TODO Auto-generated method stub
		ArrayList taTypes = (ArrayList)type.getTa();
		if(taTypes.size()==0) return;
		System.out.println ("Total TimeAdvance Sets: "+taTypes.size());
		for(int i=0; i<taTypes.size(); i++) {
			TaType ta = (TaType)taTypes.get(i);
			System.out.println("\tState: "+ta.getState()+"\tta: "+ta.getTimeout());
		}
	}

	private static void processStatesType(StatesType type) {
		// TODO Auto-generated method stub
		ArrayList states = (ArrayList)type.getState();
		if(states.size()==0) return;
		System.out.println ("Total states: "+states.size());
		for(int i=0; i<states.size(); i++) {
			System.out.println("\tstate: "+(String)states.get(i));
		}
	}

	private static void processDeltextType(DeltextType type) {
		// TODO Auto-generated method stub
		
	}

	private static void processDeltintType(DeltintType type) {
		// TODO Auto-generated method stub
		
	}

	private static void processOutportType(OutportType type) {
		// TODO Auto-generated method stub
		
	}

	private static void processInportType(InportType type) {
		// TODO Auto-generated method stub
		
	}

}
