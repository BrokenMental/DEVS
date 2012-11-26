/**
 *
 */
package genDevs.jaxb.coupled;

import java.util.Iterator;
import java.util.Vector;

/**
 * @author Saurabh
 * Aug 25, 2007, 9:11:05 AM
 * genDevs.modeling.coupled
 * CoupledDEVS.java
 *
 */
public class CoupledDEVS implements CoupledJAXBInterface, CoupledDEVSInterface{
    
    private String coupledModelName;
    private String host;
    
    private Vector<String> coupledModelClassNames;
    private Vector<CouplingRelation> couplingRelations;
    
    private String model;
    
    private boolean isSimView = true;
    
    public CoupledDEVS() {
        // TODO Auto-generated constructor stub
    }
    
    public CoupledDEVS(String modelName, String host){
        coupledModelName = modelName;
        this.host = host;
        
        coupledModelClassNames = new Vector<String>();
        couplingRelations = new Vector<CouplingRelation>();
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
    }
    
    public void generateDEVSModel(){
        
    }
    
    public void setSimView(boolean flag){
        isSimView = flag;
    }
    
    public String getGeneratedImports(){
        String str = "";
        str += "\nimport genDevs.modeling.*;";
        str += "\nimport genDevs.simulation.*;";
        str += "\nimport java.util.*;";
        str += "\nimport GenCol.*;";
        //str += "\nimport Models.java.*;";
        if(isSimView){
            str += "\nimport simView.*;";
            str += "\nimport java.awt.*;";
        }
        str += "\n\n";
        
        return str;
    }
    public String getGeneratedConstructor(){
        String str="";
        //bpz Sept 2007
        if(isSimView)
            str += "public class "+this.coupledModelName+" extends ViewableDigraph{\n";
        else
            str += "public class "+this.coupledModelName+" extends digraph{\n";
        str += "\npublic " + coupledModelName.trim() + "(){";
        str += "\nthis(\"" +coupledModelName.trim()+"\");";
        str += "}\n";
        str += "\npublic " + coupledModelName.trim() + "(String nm){";
        str += "\nsuper(nm);\n";
        str += "\n";
        
        str += this.getGeneratedModelInstances();
        str += this.getGeneratedCouplings();
        
        str += "\n}\n}";
        
        
        return str;
    }
    
    
    public String getGeneratedCouplings(){
        String str = "";
        Iterator it = this.couplingRelations.iterator();
        while(it.hasNext()){
            CouplingRelation couprel = (CouplingRelation)it.next();
            //bpz 2007
            if ( !couprel.getSrc().equals(couprel.getDest()))
            str += "\n\taddCoupling("+
                    couprel.getSrc()+"Instance"+",\""+couprel.getOutport()+"\","+
                    couprel.getDest()+"Instance"+",\""+couprel.getInport()+"\");\n";
        }
        
        return str;
    }
    
    public String getGeneratedModelInstances(){
        String str= "";
        Iterator it = this.coupledModelClassNames.iterator();
        while(it.hasNext()){
            str += getGeneratedModelInstance((String)(it.next()));
        }
        return str;
    }
    
    public String getGeneratedModelInstance(String modelClass){
        String str = "";
        
        String atomicTag="";
        String digraphTag = "";
        
        
        if(isSimView){
            atomicTag = "ViewableAtomic";
            digraphTag = "ViewableDigraph";
        } else {
            atomicTag = "atomic";
            digraphTag = "digraph";
        }
        
        String filename = genDevs.jaxb.gui.FrameFDDEVS.packageXmlPath+modelClass+".xml";
        
        boolean modelTypes[] = genDevs.jaxb.gui.FrameFDDEVS.getRootModelTypeFromFile(filename);
        boolean isDigraph = modelTypes[0];
        boolean isAtomic = modelTypes[1];
        
        if(isDigraph){
            str += "\t"+digraphTag+" "+modelClass+"Instance = new "+modelClass+"();\n";
        }
        
        if(isAtomic){
            str += "\t"+atomicTag+" "+modelClass+"Instance = new "+modelClass+"();\n";
        }
        
        str += "\tadd("+modelClass+"Instance);\n\n";
        
        return str;
    }
    
    public String getGeneratedDEVSCoupledModel(){
    	
    	//sm September 2007
    	model  = "";
    	if (isSimView) {
            model ="package Models.java;\n";
        }
        String str = "";
        str += this.getGeneratedImports();
        str += this.getGeneratedConstructor();
        model += str;
        
        return model;
    }
    
    @Override
    public void setCoupledModelComponents(Vector<String> modelClassNames) {
        this.coupledModelClassNames = modelClassNames;
    }
    
    @Override
    public void setCoupledModelHost(String host) {
        this.host = host;
        
    }
    
    @Override
    public void setCoupledModelName(String name) {
        this.coupledModelName = name;
        
    }
    
    @Override
    public void setCouplingRelations(Vector<CouplingRelation> couplingRelations) {
        this.couplingRelations = couplingRelations;
    }
    
    @Override
    public String getCoupledModelHost() {
        return this.host;
    }
    
    @Override
    public String getCoupledModelName() {
        return this.coupledModelName;
    }
    
    @Override
    public Vector<CouplingRelation> getCouplingRelations() {
        return this.couplingRelations;
    }
    
    @Override
    public Vector<String> getModelComponents() {
        return this.getModelComponents();
    }
    
}
