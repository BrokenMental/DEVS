/**
 *
 */
package genDevs.jaxb.fddevs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.acims.fddevs.LambdaType;
import com.acims.fddevs.TaType;

/**
 * @author Saurabh
 *
 */
public class AtomicFDD implements AtomicJAXBInterface, AtomicFDDInterface {
    
    private String modelName;
    
    private String host;
    
    private String[] inports;
    
    private String[] outports;
    
    private String[] states;
    
    private Hashtable<String, Transition> deltints;
    
    private Hashtable<String, ExtTransition> deltexts;
    
    private Hashtable<String, LambdaType> lamda;
    
    private Hashtable<String, TaType> timeAdvance;
    
    private String generatedAtomicDEVS;
    
    private String model;
    
    private boolean isSimView = true;
    
    public AtomicFDD(String modelName, String host) {
        this.modelName = modelName;
        this.host = host;
    }
    //bpz Sept 2007
    public String makeHoldOrPassivate(String nextState,double timeout){
        String s=  "\t\tholdIn(\"" + nextState.trim() + "\"," + timeout;
        if (timeout == Double.POSITIVE_INFINITY)
            s =  "\t\tpassivateIn(\"" + nextState.trim()+ "\"";
        if (nextState.equals("passive"))
            s =  "\t\tpassivate(";
        return s;
    }
    public String getImports() {
        String str = "";
        str += "\nimport genDevs.modeling.*;";
        str += "\nimport genDevs.simulation.*;";
        str += "\nimport java.util.*;";
        str += "\nimport GenCol.*;";
        if (isSimView) {
            str += "\nimport simView.*;";
            str += "\nimport java.awt.*;";
        }
        str += "\n\n";
        return str;
    }
    
    public void generateDEVSModel(String packageNm) {
        //bpz Sept 2007
        model = "";
        if (isSimView) {
            model ="package "+packageNm+".Models.java;\n";
        }
        String imports = getImports();
        model += imports;
        
        if (isSimView) {
            model += "public class " + modelName.trim()
            + " extends ViewableAtomic{\n";
        } else {
            model += "public class " + modelName.trim() + " extends atomic{\n";
        }
        //bpz Sept 2007
        model += "\ndouble Infinity = INFINITY;\n";
        System.out.println("\n\nGenerate Atomic Model-----------------");
        String deltintString = getDeltintString();
        // System.out.println(deltintString);
        String deltextString = getDeltextString();
        // System.out.println(deltextString);
        String outString = getOutString();
        // System.out.println(outString);
        String constructor = getConstructor();
        // System.out.println(constructor);
        String initializer = getInitializer();
        // System.out.println(initializer);
        
        model += constructor + initializer + deltintString + deltextString
                + outString;
        model += "}\n";
        
        generatedAtomicDEVS = model;
        //System.out.println(generatedAtomicDEVS);
    }
    
    public double getTimeoutForState(String state) {
        double timeout =  Double.POSITIVE_INFINITY;//0 default value for undefined;
        Enumeration e = timeAdvance.elements();
        while (e.hasMoreElements()) {
            TaType ta = (TaType) e.nextElement();
            if (ta.getState().equals(state)) {
                timeout = ta.getTimeout();
            }
        }
        return timeout;
    }
    
    public String getStringForModelingDeltint(Transition tr) {
        String str = "";
        double timeout = getTimeoutForState(tr.getNextState().trim());
        str += "\tif(phaseIs(\"" + tr.getStartState().trim() + "\")){\n"
                //bpz Sept 2007
                //  + "\t\tholdIn(\"" + tr.getNextState().trim() + "\"," + timeout
                + makeHoldOrPassivate(tr.getNextState(),timeout)
                + ");\n" + "\t}\n";
        return str;
    }
    
    public String getDeltintString() {
        String str = "\npublic void deltint(){\n";
        if (deltints != null)
            if (deltints.elements() != null) {
            Enumeration e = deltints.elements();
            int count = 0;
            while (e.hasMoreElements()) {
                Transition tr = (Transition) e.nextElement();
                if (count == 0) {
                    str += this.getStringForModelingDeltint(tr);
                } else {
                    str += "\telse\n";
                    str += this.getStringForModelingDeltint(tr);
                }
                count++;
            }
            if (count > 0)
                str += "\telse\n";
            }
        str += "\tpassivate();\n";
        str += "}\n";
        return str;
    }
    
    public String getHookFunction(String hook) {
        String str = "";
        str += "\npublic void " + hook + "{\n";
        str += "\tSystem.out.println(\"Processing: "
                + hook.substring(7, hook.length()) + "\");\n";
        str += "}\n";
        
        return str;
    }
    
    public String printHooksForPhaseProcessing() {
        String str = "";
        
        Vector<String> hooks = new Vector<String>();
        if (deltexts != null)
            if (deltexts.elements() != null) {
            Enumeration e = deltexts.elements();
            while (e.hasMoreElements()) {
                ExtTransition tr = (ExtTransition) e.nextElement();
                String hook = tr.getHook().trim();
                if(!hooks.contains(hook))
                    hooks.add(hook);
            }
            }
        
        if(hooks.size()==0)return str;
        Iterator it = hooks.iterator();
        while(it.hasNext()){
            String hook = (String)it.next();
            str += getHookFunction(hook);
        }
        
/*		if (deltexts != null)
                        if (deltexts.elements() != null) {
                                Enumeration e = deltexts.elements();
                                while (e.hasMoreElements()) {
                                        ExtTransition tr = (ExtTransition) e.nextElement();
                                        String hook = tr.getHook();
                                        str += getHookFunction(hook);
                                }
                        }
 */
        return str;
    }
    //bpz in
    public String getStringFromModelingDeltextValuesPerMsg(String incoming, Vector values){
        String str = "\t\t";
        str += "if(this.messageOnPort(x, \"in" + incoming.trim()
        + "\", i)){\n";
        int count = 0;
        Iterator it = values.iterator();
        while (it.hasNext()) {
            ExtTransition tr = (ExtTransition) it.next();
            if(count==0){
                str += "\t\t\tif(phaseIs(\""
                        + tr.getTransition().getStartState().trim() + "\" )){\n";
            } else{
                str += "\t\t\telse if(phaseIs(\""
                        + tr.getTransition().getStartState().trim() + "\" )){\n";
            }
            str += "\t\t\t\t" + tr.getHook().trim() + ";\n";
            double timeout = getTimeoutForState(tr.getTransition().getNextState().trim());
            if (tr.isScheduleIndicator()) {
                //bpz Sept 2007
                str += makeHoldOrPassivate(tr.getTransition().getNextState(),timeout)
                /*
                str += "\t\t\t\tholdIn(\""
                        + tr.getTransition().getNextState().trim() + "\","
                        + timeout
                 */
                + ");\n";
            } else {
                str += "\t\t\t\tphase = \""
                        + tr.getTransition().getNextState().trim() + "\";\n";
            }
            str += "\t\t\t}\n";
            count++;
        }
        
        str += "\t\t}\n";
        return str;
    }
    public String getStringForModelingDeltext(ExtTransition tr) {
        String str = "\t\t";
        
        str += "if(this.messageOnPort(x, \"in" + tr.getIncomingMsg().trim()
        + "\", i)){\n";
        str += "\t\t\tif(phaseIs(\""
                + tr.getTransition().getStartState().trim() + "\" )){\n";
        str += "\t\t\t\t" + tr.getHook().trim() + ";\n";
        double timeout = this.getTimeoutForState(tr.getTransition()
        .getNextState().trim());
        if (tr.isScheduleIndicator()) {
            str += "\t\t\t\tholdIn(\""
                    + tr.getTransition().getNextState().trim() + "\","
                    + timeout + ");\n";
        } else {
            str += "\t\t\t\tphase = \""
                    + tr.getTransition().getNextState().trim() + "\";\n";
        }
        str += "\t\t\t}\n";
        str += "\t\t}\n";
        return str;
    }
    
    private Hashtable addExtTrValueToHashtable(ExtTransition tr, Hashtable ht){
        
        if(ht.containsKey(tr.getIncomingMsg().trim())){
            Vector values = (Vector)ht.get(tr.getIncomingMsg().trim());
            values.add(tr);
            ht.put(tr.getIncomingMsg().trim(), values);
        } else{
            Vector values = new Vector();
            values.add(tr);
            ht.put(tr.getIncomingMsg().trim(), values);
        }
        
        return ht;
    }
    public String getDeltextString() {
        String str = "\npublic void deltext(double e, message x){\n";
        str += "\tContinue(e);\n";
        str += "\tfor(int i=0; i<x.getLength(); i++){\n";
        
        Hashtable<String, Vector<ExtTransition>> incomingMsgAndTransitions = new
                Hashtable<String, Vector<ExtTransition>>();
        if (deltexts != null)
            if (deltexts.elements() != null) {
            Enumeration e = deltexts.elements();
            while (e.hasMoreElements()) {
                ExtTransition tr = (ExtTransition) e.nextElement();
                String IncomingMsg = tr.getIncomingMsg();
                incomingMsgAndTransitions =
                        addExtTrValueToHashtable(tr,incomingMsgAndTransitions);
            }
            }
        
        if(incomingMsgAndTransitions.size()>0){
            Enumeration e = incomingMsgAndTransitions.keys();
            while(e.hasMoreElements()){
                String incoming = (String)e.nextElement();
                Vector values = (Vector)incomingMsgAndTransitions.get(incoming);
                str += getStringFromModelingDeltextValuesPerMsg(incoming, values);
            }
        }
/*		if (deltexts != null)
                        if (deltexts.elements() != null) {
                                Enumeration e = deltexts.elements();
                                while (e.hasMoreElements()) {
                                        ExtTransition tr = (ExtTransition) e.nextElement();
                                        str += this.getStringForModelingDeltext(tr);
                                }
                        }
 */
        str += "\t}\n";
        str += "}\n";
        
        str += printHooksForPhaseProcessing();
        
        return str;
    }
    
    public Hashtable addMsgsPerPhase(Hashtable ht, String phase, LambdaType lamda){
        if(ht.containsKey(phase)){
            Vector values = (Vector)ht.get(phase);
            if(!values.contains(lamda))
                values.add(lamda);
            ht.put(phase, values);
            return ht;
        } else{
            Vector values = new Vector();
            values.add(lamda);
            ht.put(phase, values);
            return ht;
        }
    }
    
    public String getOutputMessageString() {
        String str = "";
        
        Hashtable<String, Vector<String>> phaseAndLamdas = new
                Hashtable<String, Vector<String>>();
        Vector<String> messageStrings = new Vector<String>();
        
        if (lamda != null) {
            Enumeration e = lamda.elements();
            while (e.hasMoreElements()) {
                LambdaType lamda = (LambdaType) e.nextElement();
                
                String phase = lamda.getState().trim();
                phaseAndLamdas = addMsgsPerPhase(phaseAndLamdas,phase,lamda);
            }
        }
        
        Enumeration e = phaseAndLamdas.keys();
        while(e.hasMoreElements()){
            String phase = (String)e.nextElement();
            str += "\tif(phaseIs(\"" + phase + "\")){\n";
            Vector values = (Vector)phaseAndLamdas.get(phase);
            Iterator it = values.iterator();
            while(it.hasNext()){
                LambdaType lamda = (LambdaType)it.next();
                String message = lamda.getOutport();
                message = message.substring(3, message.length());
                String msgStr = "\t\tm.add(makeContent(\"" + lamda.getOutport()
                + "\",new entity(\"" + message + "\")));\n";
                if(!messageStrings.contains(msgStr)){
                    messageStrings.add(msgStr);
                    //    str += msgStr;   //bpz
                }
                str += msgStr;
            }
            str += "\t}\n";
        }
        
        
        /*
                if (lamda != null) {
                        Enumeration e = lamda.elements();
                        while (e.hasMoreElements()) {
                                LambdaType lamda = (LambdaType) e.nextElement();
         
                                str += "\tif(phaseIs(\"" + lamda.getState() + "\")){\n";
                                String message = lamda.getOutport();
                                message = message.substring(3, message.length());
                                str += "\t\tm.add(makeContent(\"" + lamda.getOutport()
                                                + "\",new entity(\"" + message + "\")));\n";
                                str += "\t}\n";
                        }
                }
         */
        return str;
    }
    
    public String getOutString() {
        String str = "\npublic message out(){\n";
        str += "\tmessage m = new message();\n";
        str += getOutputMessageString();
        str += "\treturn m;\n";
        str += "}\n";
        return str;
    }
    
    public String getInitializer() {
        //bpz Sept 2007
        String str = "\npublic void initialize(){\n";
        if (deltints != null) {
            if (deltints.keys() != null) {
                Enumeration e = deltints.keys();
                Transition defaultTransition = null;
                while (e.hasMoreElements()) {
                    String key = (String) e.nextElement();
                    Transition tr = (Transition) deltints.get(key);
                    if (tr == null) {
                        str += "\tpassivate();\n";
                        return str;
                    }
                    defaultTransition = tr;
                    if (Integer.parseInt(key) != 1)
                        continue;
                    defaultTransition = tr;
                    break;
                }
                double timeout = getTimeoutForState(defaultTransition
                        .getStartState().trim());
                str += makeHoldOrPassivate(defaultTransition.getStartState(),timeout)
                
                //    str += "\tholdIn(\"" + defaultTransition.getStartState().trim()
                //     + "\"," + timeout
                + ");\n";
            } else
                str += "\tpassivate();\n";
        } else
            str += "\tpassivate();\n";
        
        str += "}\n";
        return str;
    }
    
    public String getConstructor() {
        String str = "";
        str += "\npublic " + modelName.trim() + "(){";
        str += "\nthis(\"" +modelName.trim()+"\");";
        str += "}\n";
        str += "\npublic " + modelName.trim() + "(String nm){";
        str += "\nsuper(nm);\n";
        str += getInportsString();
        str += getOutportsString();
        str += "}\n";
        str += "\n";
        return str;
    }
    
    public String getInportsString() {
        //bpz Sept 2007
        String str = "";
        if (inports == null)
            return str;
        if (inports.length == 0)
            return str;
        for (int i = 0; i < inports.length; i++) {
            str += "\taddInport(\"in" + inports[i] + "\");\n";
            str += "\taddTestInput(\"in" + inports[i]+"\",new entity());\n";
            str += "\taddTestInput(\"in" + inports[i]+"\",new entity(),1);\n";
        }
        return str;
    }
    
    public String getOutportsString() {
        String str = "";
        if (outports == null)
            return str;
        if (outports.length == 0)
            return str;
        for (int i = 0; i < outports.length; i++) {
            str += "\taddOutport(\"" + outports[i] + "\");\n";
        }
        return str;
    }
    
    public String getGeneratedDEVSAtomicModel() {
        return generatedAtomicDEVS;
    }
    
    public void writeDevsjavaModel(String folder) {
        String fileName = folder + File.separator + modelName + ".java";
        
        File file = new File(fileName);
        try {
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            if (generatedAtomicDEVS == null)
                return;
            writer.write(generatedAtomicDEVS);
            writer.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * JAXBInterface implemented functions
     */
    
    public void setHost(String ip) {
        host = ip;
    }
    
    public void setModelName(String name) {
        // TODO Auto-generated method stub
        modelName = name;
    }
    
    public void setDeltext(Hashtable<String, ExtTransition> deltexts) {
        // TODO Auto-generated method stub
        this.deltexts = deltexts;
        
    }
    
    public void setDeltint(Hashtable<String, Transition> deltints) {
        // TODO Auto-generated method stub
        this.deltints = deltints;
    }
    
    public void setInports(String[] inports) {
        // TODO Auto-generated method stub
        this.inports = inports;
    }
    
    public void setLamda(Hashtable<String, LambdaType> lamdas) {
        // TODO Auto-generated method stub
        this.lamda = lamdas;
    }
    
    public void setOutports(String[] outports) {
        // TODO Auto-generated method stub
        this.outports = outports;
    }
    
    public void setStates(String[] states) {
        // TODO Auto-generated method stub
        this.states = states;
    }
    
    public void setTimeAdvance(Hashtable<String, TaType> tas) {
        // TODO Auto-generated method stub
        this.timeAdvance = tas;
    }
    
    /**
     * FDDInterfaceImplemented methods
     */
    
    public Hashtable<String, ExtTransition> getDeltext() {
        // TODO Auto-generated method stub
        return this.deltexts;
    }
    
    public Hashtable<String, Transition> getDeltint() {
        // TODO Auto-generated method stub
        return this.deltints;
    }
    
    public String[] getInports() {
        // TODO Auto-generated method stub
        return this.inports;
    }
    
    public Hashtable<String, LambdaType> getLamda() {
        // TODO Auto-generated method stub
        return this.lamda;
    }
    
    public String[] getOutports() {
        // TODO Auto-generated method stub
        return this.outports;
    }
    
    public String[] getStates() {
        // TODO Auto-generated method stub
        return this.states;
    }
    
    public Hashtable<String, TaType> getTimeAdvance() {
        // TODO Auto-generated method stub
        return this.timeAdvance;
    }
    
    public String getModelName() {
        // TODO Auto-generated method stub
        return this.modelName;
    }
    
    public String getHost() {
        return this.host;
    }
    
}
