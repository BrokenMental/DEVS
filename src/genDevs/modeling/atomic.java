/*      Copyright 2002 Arizona Board of regents on behalf of
 *                  The University of Arizona
 *                     All Rights Reserved
 *         (USE & RESTRICTION - Please read COPYRIGHT file)
 *
 *  Version    : DEVSJAVA 3.0 Beta
 *  Date       : 02-22-03
 */
package genDevs.modeling;

import GenCol.*;
import java.util.*;
import java.awt.Color;
import genDevs.simulation.*;
import genDevs.simulation.distributed.*;
import util.*;

//public abstract
public class atomic extends devs implements AtomicInterface{
 protected double sigma;
 protected String phase;
 protected ActivityInterface a;
 protected double  INFINITY  = DevsInterface.INFINITY;
 protected CoupledSimulatorInterface mySim;

public CoupledSimulatorInterface getSim(){
return mySim;
}

public atomic(){
this("atomic");
}

public atomic(String name){
super(name);
sigma = INFINITY;
}
public atomic(ActivityInterface a){
this(a.getName());
this.a = a;
}
public void setSimulator(CoupledSimulatorInterface mySim){
this.mySim = mySim;
}

public String getPhase(){
return phase;
}

public void setSigma(double sigma){

  this.sigma = sigma;
}

public double getSigma(){
return sigma;
}

public void initialize(){
}

public void Continue(double e){
 if (sigma < INFINITY)
    setSigma(sigma - e);
}


    public void passivateIn(String phase) {holdIn(phase, INFINITY);}
    public void passivate() {passivateIn("passive");}
    public void holdIn(String phase, double sigma) {holdIn(phase, sigma, null);}

    public void holdIn(String phase, double sigma,ActivityInterface a)
    {

        this.phase = phase;
        setSigma(sigma);
        if (a != null) {
            this.a = a;
            mySim.startActivity(a);
        }
    }


  public void startActivity(ActivityInterface a){
    this.a = a;
    mySim.startActivity(a);
  }

  public boolean phaseIs(String phase){
  return this.phase.equals(phase);
  }


  public double ta(){
      return sigma;
  }

  public void deltint(){}
  public void deltext(double e,MessageInterface x){deltext(e,(message)x);}
  public void deltext(double e,message x){} //usual devs
  public void deltcon(double e,MessageInterface x){deltcon(e,(message)x);}
  public void deltcon(double e,message x){ //usual devs
   deltint();
   deltext(0,x);
}

 public MessageInterface Out(){

 return out();
}

 public message out(){
 return new message();
}

 public String toString(){
 return "phase :"+ phase + " sigma : " + sigma ;
 }

  public void showState(){
    Logging.log("Model "+ getName() + "'s state: ", Logging.full);
    Logging.log("phase :"+ phase + " sigma : " + sigma, Logging.full);
  }

  public ActivityInterface getActivity(){
  return a;
  }

    public String stringState(){
    return "";
    }
 /**-------------------------------------------------------------------------
  * -------------------------------------------------------------------------
  * The code below is for variable structure DEVS
  * Xiaolin Hu, March 14, 2006
 */
public void addModel(IODevs iod){
  digraph P = (digraph)getParent();
  if(P!=null) P.dyn_addModel(iod);
}

public void addModelToBrotherOf (IODevs newModel, String bortherName){
  IODevs bro = this.getModel(bortherName);
  digraph P = (digraph)bro.getParent();
  if(P!=null) P.dyn_addModel(newModel);
}

public void addModelToChildOf (IODevs newModel, String parentName){
  IODevs P = this.getModel(parentName);
  if(P instanceof digraph) ((digraph)P).dyn_addModel(newModel);
}

public void removeModel(String modelName){ // remove any model
  IODevs m = getModel(modelName);
  digraph P = (digraph)m.getParent();
  if(P!=null) P.dyn_removeModel(m);
}

public void removeModelAndAncestors(String modelName){
  IODevs m = getModel(modelName);
  digraph P=null;
  if(m!=null) P = (digraph)m.getParent();
  if(P!=null) P.dyn_removeModelAndAncestors(m);
}

public void removeAncestorBrotherOf(String modelName){
  IODevs iod = getMyAncestor_BrotherOf(modelName);
  digraph P = (digraph)iod.getParent();
  if(P!=null) P.dyn_removeModel(iod);
}

public void addCoupling(String src, String p1, String dest, String p2) {
  IODevs srcM, destM;
  srcM = getModel(src);
  destM = getModel(dest);
  addCoupling(srcM, p1, destM, p2);
}

public void addCoupling(IODevs srcM, String p1, IODevs destM, String p2) {
  digraph P = (digraph) getParent();
  P.dyn_addCoupling_AnyTwoModel(srcM, p1, destM, p2, false);
}

public void removeCoupling(String src, String p1, String dest, String p2) {
  IODevs srcM, destM;
  srcM = getModel(src);
  destM = getModel(dest);
  removeCoupling(srcM, p1, destM, p2);
}

public void removeCoupling(IODevs srcM, String p1, IODevs destM, String p2) {
  digraph P = (digraph) getParent();
  P.dyn_removeCoupling_AnyTwoModel(srcM, p1, destM, p2);
}

public void addCouplingUsingSrcPort(String src, String dest, String portName) {
  IODevs srcM, destM;
  srcM = getModel(src);
  destM = getModel(dest);
  addCouplingUsingSrcPort(srcM, destM, portName);
}

public void addCouplingUsingSrcPort(IODevs srcM, IODevs destM,
                                    String portName) {
  //first make sure the dest model has this port
  destM.dyn_addInport(portName);
  digraph P = (digraph) getParent();
  P.dyn_addCoupling_AnyTwoModel(srcM, portName, destM, portName, true);
}

public void addCouplingUsingDestPort(String src, String dest,
                                     String portName) {
  IODevs srcM, destM;
  srcM = getModel(src);
  destM = getModel(dest);
  addCouplingUsingDestPort(srcM, destM, portName);
}

public void addCouplingUsingDestPort(IODevs srcM, IODevs destM,
                                     String portName) {
  //first make sure the src model has this port
  srcM.dyn_addOutport(portName);
  digraph P = (digraph) getParent();
  P.dyn_addCoupling_AnyTwoModel(srcM, portName, destM, portName, true);
}

public void addCouplingsUsingSrcPorts(String src, String dest) {
  IODevs srcM, destM;
  srcM = getModel(src);
  destM = getModel(dest);
  addCouplingsUsingSrcPorts(srcM, destM);
}

public void addCouplingsUsingSrcPorts(IODevs srcM, IODevs destM) {
  ports ps = srcM.getOutports();
  Iterator ip = ps.iterator();
  while (ip.hasNext()) {
    port p = (port) ip.next();
    this.addCouplingUsingSrcPort(srcM, destM, p.getName());
  }
}

public void addCouplingsUsingDestPorts(String src, String dest) {
  IODevs srcM, destM;
  srcM = getModel(src);
  destM = getModel(dest);
  addCouplingsUsingDestPorts(srcM, destM);
}

public void addCouplingsUsingDestPorts(IODevs srcM, IODevs destM) {
  ports ps = destM.getInports();
  Iterator ip = ps.iterator();
  while (ip.hasNext()) {
    port p = (port) ip.next();
    this.addCouplingUsingDestPort(srcM, destM, p.getName());
  }
}

   public void addInport(String modelName, String port){
     IODevs m = getModel(modelName);
     m.dyn_addInport(port);
   }
   public void addInport(IODevs model, String port){
     model.dyn_addInport(port);
   }

   public void addOutport(String modelName, String port){
     IODevs m = getModel(modelName);
     m.dyn_addOutport(port);
   }
   public void addOutport(IODevs model, String port){
     model.dyn_addOutport(port);
   }

   public void removeInport(String modelName, String port){
     IODevs m = getModel(modelName);
     m.dyn_removeInport(port);
   }
   public void removeInport(IODevs model, String port){
     model.dyn_removeInport(port);
   }

   public void removeOutport(String modelName, String port){
     IODevs m = getModel(modelName);
     m.dyn_removeOutport(port);
   }
   public void removeOutport(IODevs model, String port){
     model.dyn_removeOutport(port);
   }

   public IODevs getModel(String modelName){
     IODevs m;
     digraph P = (digraph)getParent();
     //first check if the model is the paprent
     if(modelName.compareTo(P.getName())==0) return P;
     else{
       //check if the model is a brother
       m = P.withName(modelName);
       if(m==null) // otherwise, search the whole tree
         m= P.getRootParent().findModelWithName(modelName);
     }
     return m;
   }

   public IODevs getParentOf(String modelName){
     IODevs m = getModel(modelName);
     return m.getParent();
   }

   public ComponentsInterface getChildrenOf(String modelName){
     IODevs P = getModel(modelName);
     if(!(P instanceof digraph)) return null;
     return ((digraph)P).getComponents();
   }

   public ComponentsInterface getBrothersOf(String modelName){
     IODevs m = getModel(modelName);
     ComponentsInterface brothers, models;
     models = ((digraph)m.getParent()).getComponents();
     componentIterator im = models.cIterator();
     brothers = new Components();
     while(im.hasNext()){
       IODevs b = (IODevs)im.nextComponent();
       if(b.getName()!=modelName)  brothers.add(b);
     }
     return brothers;
   }

   public IODevs getOneBrotherOf(String modelName){
     IODevs m = getModel(modelName);
     ComponentsInterface models = ((digraph)m.getParent()).getComponents();
     componentIterator im = models.cIterator();
     while(im.hasNext()){
       IODevs b = (IODevs)im.nextComponent();
       if(b.getName()!=modelName)
         return b;
     }
     return null;
   }

   public IODevs getMyAncestor_BrotherOf(String modelName){
     IODevs m = getModel(modelName);
     if(this.isBrother(m)) return this;
     digraph P = (digraph)getParent();
     return P.getAncestor_BrotherOf(m);
   }

   public IODevs getMyAncestor_ChildOf(String modelName){
     IODevs m= getModel(modelName);
     digraph P = (digraph)getParent();
     if(P==m) return this;
     else return P.getAncestor_ChildOf(m);
   }

   public IODevs getMyAncestor_ParentOf(String modelName){
     IODevs m = getModel(modelName);
     digraph p = (digraph)getParent();
     if(p.hasDescendantModel(this)) return p;
     else return null;
   }

   public ComponentsInterface getDescendantsOf_StartsWith(String modelName, String nameStart){
     IODevs m = getModel(modelName);
     return ((digraph)m.getParent()).findModel_NameStartWith(nameStart);
   }

   public IODevs getOneBrother(){
     return this.getOneBrotherOf(getName());
   }

   public ComponentsInterface  getBrothers(){
     return this.getBrothersOf(getName());
   }

}