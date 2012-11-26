/**
 * 
 */
package genDevs.jaxb.coupled;

import genDevs.jaxb.fddevs.AtomicFDD;
import genDevs.modeling.coupledDevs;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.acims.coupled.*;

/**
 * @author Saurabh
 * Aug 25, 2007, 9:11:26 AM
 * genDevs.modeling.coupled
 * CoupledJAXB.java
 * 
 */
public class CoupledJAXB implements CoupledJAXBInterface{


	private CoupledDEVS coupledDevs;
	private String javaModelString;
    
	private String coupledModelName;
	private String coupledModelHost;
	private String coupledTagStr = "";
    private Vector<String> modelComponentNames;
    private Vector<CouplingRelation> couplingRelations;
    
	public CoupledJAXB() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public String returnJavaModelString(){
        return javaModelString;
    }
	public void initializeModel(String xmlFileName) {
        try {
            
            JAXBContext jc = JAXBContext.newInstance("com.acims.coupled");
            Unmarshaller unm = jc.createUnmarshaller();
            
            //			JAXBElement element = (JAXBElement)
            //				//unm.unmarshal(new File("xmlModels/fddevsXmlInstance.xml"));
            //				unm.unmarshal(new File(xmlFileName));
            //
            DigraphType digraph = (DigraphType)unm.unmarshal(new File(xmlFileName));
            
            //AtomicType atomic = (AtomicType) element.getValue();
            String coupledModelName = digraph.getName();
            String host = digraph.getHost();
            
            
            if(coupledDevs == null)
                coupledDevs = new CoupledDEVS(coupledModelName, host);
            
            System.out.println("ModelName: "+coupledModelName);
            System.out.println("HostName: "+host);
            
            if(modelComponentNames == null)
            	modelComponentNames =new Vector<String>();
            if(couplingRelations == null)
            	couplingRelations = new Vector<CouplingRelation>();
                        
            ModelsType modelsType = (ModelsType)digraph.getModels();
            ArrayList list = (ArrayList)modelsType.getModel();
            for(int i=0; i<list.size(); i++) {
                //System.out.println(list.get(i));
                Object obj = (Object)list.get(i);
                if(obj instanceof com.acims.coupled.ModelType){
                	processModelType((ModelType)obj);
                } else
                    System.out.println("Can not process type: "+obj.toString());
            }
            
            coupledDevs.setCoupledModelComponents(modelComponentNames);
                        
            CouplingsType couplingsType = (CouplingsType)digraph.getCouplings();
            list = (ArrayList)couplingsType.getCoupling();
            for(int i=0; i<list.size(); i++){
            	Object obj = (Object)list.get(i);
            	if(obj instanceof com.acims.coupled.CouplingType){
            		processCouplingType((CouplingType)obj);
            	}
            	else
            		System.out.println("Can not process type: "+obj.toString());
            }
            
            coupledDevs.setCouplingRelations(couplingRelations);
            
            coupledDevs.generateDEVSModel();
            
            javaModelString = coupledDevs.getGeneratedDEVSCoupledModel();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
	
	private void processCouplingType(CouplingType coupling){
		CouplingRelation couprel = new CouplingRelation(
				coupling.getSrcModel(),coupling.getDestModel(),
				coupling.getInport(), coupling.getOutport());
		
		if(!couplingRelations.contains(couprel))
			couplingRelations.add(couprel);
		
	}
	private void processModelType(ModelType model){
		String devs = model.getDevs();
		if(!modelComponentNames.contains(devs)){
			modelComponentNames.add(devs);
		}
	}
	
	@Override
	public void setCoupledModelComponents(Vector<String> modelClassNames) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCoupledModelHost(String host) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCoupledModelName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCouplingRelations(Vector<CouplingRelation> couplingRelations) {
		// TODO Auto-generated method stub
		
	}

}
