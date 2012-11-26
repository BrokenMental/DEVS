/**
 *
 */
package genDevs.jaxb.fddevs;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import com.acims.fddevs.*;
/**
 * @author Saurabh
 *
 */
public class AtomicJAXB implements AtomicJAXBInterface{
     //bpz Sept 2007
   // private
            public AtomicFDD atomicFDD;
    private String javaModelString;
    private String xmlModelString;
    
    private String atomicTagStr = "";
    private String inportStr="";
    private String outportStr="";
    private String timeAdvanceStr="";
    private String deltintStr="";
    private String deltextStr="";
    private String lamdaStr="";
    private String stateStr="";
    
    public AtomicJAXB() {
        atomicFDD = null;
    }
    
    public static void main(String[] args) {
        String fileName = "C:/FDDEVS/Models/xml"+File.separator+
                //"fddevsXmlInstance.xml";
                //"JTAC.xml";
                "genr.xml";
        AtomicJAXB atJaxb = new AtomicJAXB();
        atJaxb.initializeModel("",fileName);
        //javaModelString = atomicFDD.getGeneratedDEVSAtomicModel();
    }
    
    public String returnJavaModelString(){
        return javaModelString;
    }
    
    public String returnXmlModelString(){
        makeXmlString();
        return xmlModelString;
    }
    
    public void makeXmlString(){
        xmlModelString = "\n<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>" ;
        xmlModelString += atomicTagStr;
        xmlModelString += inportStr;
        xmlModelString += outportStr;
        xmlModelString += stateStr;
        xmlModelString += timeAdvanceStr;
        xmlModelString += deltintStr;
        xmlModelString += deltextStr;
        xmlModelString += "\n"+"</atomicType>";
    }
    
    public void resetXmlString(){
        inportStr="";
        outportStr="";
        timeAdvanceStr="";
        deltintStr="";
        deltextStr="";
        lamdaStr="";
        stateStr="";
    }
    
    private void writeAtomicTagXml(AtomicType atomic){
        atomicTagStr =
                "\n<atomicType host=\""+
                atomic.getHost()+"\" modelName=\""+
                atomic.getModelName()+"\" xmlns=\"http://www.saurabh-mittal.com/NewXMLSchema\">";
    }
  
     public void initializeModel(String xmlFileName){//bpz
         initializeModel("",xmlFileName);
     }
     
    public void initializeModel(String packageNm,String xmlFileName) {//pbz
        try {        
            JAXBContext jc = JAXBContext.newInstance("com.acims.fddevs");
            Unmarshaller unm = jc.createUnmarshaller();
           
            AtomicType atomic = (AtomicType)unm.unmarshal(new File(xmlFileName));
            String modelName = atomic.getModelName();
            String host = atomic.getHost();

            resetXmlString();
            
            writeAtomicTagXml(atomic);
            
            if(atomicFDD == null)
                atomicFDD = new AtomicFDD(modelName, host);
            
         //   System.out.println("ModelName: "+modelName);
         //   System.out.println("HostName: "+host);
            
            ArrayList list = (ArrayList)atomic.getInportsAndOutportsAndStates();
            for(int i=0; i<list.size(); i++) {
                //System.out.println(list.get(i));
                Object obj = (Object)list.get(i);
                if(obj instanceof InportType) {
                    processInportType((InportType)obj);
                } else if(obj instanceof OutportType) {
                    processOutportType((OutportType)obj);
                } else if (obj instanceof DeltintType) {
                    processDeltintType((DeltintType)obj);
                } else if(obj instanceof DeltextType) {
                    processDeltextType((DeltextType)obj);
                } else if(obj instanceof StatesType) {
                    processStatesType((StatesType)obj);
                } else if(obj instanceof TimeAdvanceType) {
                    processTimeAdvanceType((TimeAdvanceType)obj);
                } else if(obj instanceof LamdaAllType) {
                    processLamdaAllType((LamdaAllType)obj);
                } else
                    System.out.println("Can not process type: "+obj.toString());
            }
            
            atomicFDD.generateDEVSModel(packageNm);
            javaModelString = atomicFDD.getGeneratedDEVSAtomicModel();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
 
    private void writeLamdaXml(LambdaType lamda){
        lamdaStr +="\n\t"+"<lamda>";
        lamdaStr+="\n\t  "+"<state>"+lamda.getState()+"</state>";
        lamdaStr+="\n\t  "+"outport>"+lamda.getOutport()+"</outport>";
        lamdaStr+="\n\t"+"</lamda>";
    }
    
    private void processLamdaAllType(LamdaAllType type) {
        // TODO Auto-generated method stub
        lamdaStr="\n<LamdaSet>";
        
        ArrayList listLamdas = (ArrayList)type.getLamda();
        if(listLamdas.size()==0)return;
        Hashtable<String, LambdaType> lamdaTable = new Hashtable<String, LambdaType>();
        for(int i=0; i<listLamdas.size(); i++) {
            LambdaType lamda = (LambdaType)listLamdas.get(i);
            writeLamdaXml(lamda);
            lamdaTable.put(new String(""+i), lamda);
        }
        
        lamdaStr += "\n</LamdaSet>";
        
        setLamda(lamdaTable);
    }
    
    private void writeTimeAdvanceXml(TaType ta){
        timeAdvanceStr += "\n\t"+"<ta>";
        timeAdvanceStr += "\n\t  "+"<state>"+ta.getState()+"</state>";
         //bpz Sept 2007
        double tout = ta.getTimeout();
        String toutString = String.valueOf(tout);
        if (tout==Double.POSITIVE_INFINITY)
            toutString = "INF";//INFINITY";
        timeAdvanceStr += "\n\t  "+"<Timeout>"+toutString+"</Timeout>";
        //    timeAdvanceStr += "\n\t  "+"<Timeout>"+ta.getTimeout()+"</Timeout>";
        timeAdvanceStr += "\n\t"+"</ta>";
    }
    private void processTimeAdvanceType(TimeAdvanceType type) {
        timeAdvanceStr += "\n<TimeAdvance>";
        
        // TODO Auto-generated method stub
        ArrayList taTypes = (ArrayList)type.getTa();
        if(taTypes.size()==0) return;
        Hashtable<String, TaType> taTypeTable = new Hashtable<String, TaType>();
        
      //  System.out.println("Total TimeAdvance Sets: "+taTypes.size());
        for(int i=0; i<taTypes.size(); i++) {
            TaType ta = (TaType)taTypes.get(i);
            writeTimeAdvanceXml(ta);
            System.out.println("\tState: "+ta.getState()+"\tta: "+ta.getTimeout());
            taTypeTable.put(new String(""+i), ta);
        }
        timeAdvanceStr += "\n</TimeAdvance>";
        
        setTimeAdvance(taTypeTable);
    }
    
    private void writeStateXml(String state){
        stateStr += "\n\t"+"<state>"+state+"</state>";
    }
    private void processStatesType(StatesType type) {
        stateStr += "\n"+"<states>";
        
        // TODO Auto-generated method stub
        ArrayList states = (ArrayList)type.getState();
        if(states.size()==0) return;
        String[] statesStr = new String[states.size()];
     //   System.out.println("Total states: "+states.size());
        for(int i=0; i<states.size(); i++) {
            statesStr[i] = (String)states.get(i);
            writeStateXml(statesStr[i]);
        //    System.out.println("\tstate: "+(String)states.get(i));
        }
        stateStr += "\n"+"</states>";
        setStates(statesStr);
    }
    
    private String writeTransitionXml(String prefix, TransitionType trType){
        String str="";
        str += prefix+"<transition>";
        str += prefix+"\t"+"<StartState>"+trType.getStartState()+"</StartState>";
        str += prefix+"\t"+"<NextState>"+trType.getNextState()+"</NextState>";
        str += prefix+"</transition>";
        return str;
    }
    
    private void writeDeltextXml(ExtTransitionType exTrType, TransitionType trType){
        deltextStr += "\n\t"+"<ExternalTransition extTransitionID=\""+exTrType.getExtTransitionID()+"\">";
        deltextStr += "\n\t  "+"<IncomingMessage>"+exTrType.getIncomingMessage()+"</IncomingMessage>";
        deltextStr += writeTransitionXml("\n\t  ",  trType);
        deltextStr += "\n\t  "+"<scheduleIndicator>"+exTrType.isScheduleIndicator()+"</scheduleIndicator>";
        deltextStr += "\n\t"+"</ExternalTransition>";
        
    }
    private void processDeltextType(DeltextType type) {
        deltextStr += "\n"+"<deltext>";
        
        ArrayList list = (ArrayList)type.getExternalTransition();
        if(list.size()==0)return;
        Hashtable<String, ExtTransition> extTrTable = new Hashtable<String, ExtTransition>();
        
        for(int i=0; i<list.size(); i++) {
            ExtTransitionType extTrType = (ExtTransitionType)list.get(i);
            String id = ""+extTrType.getExtTransitionID();
            boolean indicator = extTrType.isScheduleIndicator();
            TransitionType transitionType = (TransitionType)extTrType.getTransition();
            writeDeltextXml(extTrType,transitionType);
            Transition transition = new Transition(transitionType.getStartState(),
                    transitionType.getNextState());
            String inMsg = extTrType.getIncomingMessage();
            ExtTransition extTransition = new ExtTransition(inMsg,indicator,transition);
            extTrTable.put(id, extTransition);
        }
        
        deltextStr += "\n"+"</deltext>";
        
        setDeltext(extTrTable);
    }
    
    private void writeDeltintXml(IntTransitionType intTr, TransitionType trType){
        deltintStr += "\n\t"+"<InternalTransition extTransitionID=\""+intTr.getIntTransitionID()+"\">";
        deltintStr += writeTransitionXml("\n\t  ",  trType);
        deltintStr += "\n\t"+"</InternalTransition>";
    }
    private void processDeltintType(DeltintType type) {
        // TODO Auto-generated method stub
        deltintStr += "\n"+"<deltint>";
        ArrayList list = (ArrayList)type.getInternalTransition();
        if(list.size()==0) return;
        Hashtable<String, Transition> intTrTable = new Hashtable<String, Transition>();
        for(int i=0; i<list.size(); i++) {
            IntTransitionType intTr = (IntTransitionType)list.get(i);
            String id = ""+intTr.getIntTransitionID();
            ArrayList trList = (ArrayList)intTr.getTransition();
            TransitionType transitionType = (TransitionType)trList.get(0);
            writeDeltintXml(intTr, transitionType);
            Transition transition = new Transition(transitionType.getStartState(),
                    transitionType.getNextState());
            intTrTable.put(id, transition);
        }
        deltintStr += "\n"+"</deltint>";
        setDeltint(intTrTable);
    }
    
    private void writeOutportXml(String state){
        outportStr += "\n\t"+"<outport>"+state+"</outport>";
    }
    
    private void processOutportType(OutportType type) {
        // TODO Auto-generated method stub
        outportStr += "\n"+"<outports>";
        ArrayList outports = (ArrayList)type.getOutport();
        if(outports.size()==0)return;
        String[] outportsStr = new String[outports.size()];
        for(int i=0; i<outports.size(); i++) {
            outportsStr[i] = (String)outports.get(i);
            writeOutportXml(outportsStr[i]);
        }
        outportStr += "\n"+"</outports>";
        setOutports(outportsStr);
    }
    
    private void writeInportXml(String state){
        inportStr += "\n\t"+"<inport>"+state+"</inport>";
    }
    
    private void processInportType(InportType type) {
        // TODO Auto-generated method stub
        inportStr += "\n"+"<inports>";
        ArrayList inports = (ArrayList)type.getInport();
        if(inports.size()==0)return;
        String[] inportsStr = new String[inports.size()];
        for(int i=0; i<inports.size(); i++) {
            inportsStr[i] = (String)inports.get(i);
            writeInportXml(inportsStr[i]);
        }
        inportStr += "\n"+"</inports>";
        setInports(inportsStr);
    }
    
    
    
    /**
     * AtomicJAXBInterface implemented functions
     */
    
    public void setHost(String ip) {
        // TODO Auto-generated method stub
        
    }
    
    public void setModelName(String name) {
        // TODO Auto-generated method stub
        
    }
    
    public void setDeltext(Hashtable<String, ExtTransition> detexts) {
        // TODO Auto-generated method stub
        atomicFDD.setDeltext(detexts);
    }
    
    public void setDeltint(Hashtable<String, Transition> deltints) {
        // TODO Auto-generated method stub
        atomicFDD.setDeltint(deltints);
    }
    
    public void setInports(String[] inports) {
        // TODO Auto-generated method stub
        atomicFDD.setInports(inports);
    }
    
    public void setLamda(Hashtable<String, LambdaType> lamdas) {
        // TODO Auto-generated method stub
        atomicFDD.setLamda(lamdas);
    }
    
    public void setOutports(String[] outports) {
        // TODO Auto-generated method stub
        atomicFDD.setOutports(outports);
    }
    
    public void setStates(String[] states) {
        // TODO Auto-generated method stub
        atomicFDD.setStates(states);
    }
    
    public void setTimeAdvance(Hashtable<String, TaType> tas) {
        // TODO Auto-generated method stub
        atomicFDD.setTimeAdvance(tas);
    }
    
    
   
    
}
