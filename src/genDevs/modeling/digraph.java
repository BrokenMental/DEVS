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
import genDevs.simulation.*;
import genDevs.simulation.distributed.*;
import genDevs.modeling.basicModels.*;
import util.*;




public class digraph extends devs implements Coupled{
    protected coordinator myCoordinator;
    protected ComponentsInterface components;
    protected couprel cp;
    
    public digraph(String nm){
        super(nm);
        components = new Components();
        cp = new couprel();
    }
    
    public void add(IODevs iod){
        components.add(iod);
        ((devs)iod).setParent(this);
    }
    
    public void addCoupling(IODevs src, String p1, IODevs dest, String p2){
        cp.add((GenCol.entity)src,new port(p1),(GenCol.entity)dest,new port(p2));
    }
    
    public void clear(){
        components = new Components();
        cp = new couprel();
        mh.inports = new ports();
        mh.outports = new ports();
    }
    
    /**
     * the method to add couplings with delay. A delay model is created and added into the coupling
     * @author  Xiaolin Hu
     */
    public void addCouplingWithDelay(IODevs src, String p1, IODevs dest, String p2, double delay){
        // find a unique ID for the DelayModel
        int UID = 0;
        componentIterator cit = getComponents().cIterator();
        while (cit.hasNext()){
            IOBasicDevs iod = cit.nextComponent();
            if((iod.getName()).startsWith("DelayModel_")) UID++;
        }
        delayModel dM = new delayModel("DelayModel_"+UID, delay);
        add(dM);
        addCoupling(src,p1, dM, "in");
        addCoupling(dM, "out", dest, p2);
    }
    
    public void addPair(GenCol.Pair cs, GenCol.Pair cd){
        cp.add(cs,cd);
    }
    
    public void removePair(GenCol.Pair  cs, GenCol.Pair  cd){
        cp.remove(cs,cd);
    }
    
    public void remove(IODevs d){
        components.remove(d);
    }
    
    public IODevs withName(String nm){
        Class [] classes  = {ensembleBag.getTheClass("java.lang.String")};
        Object [] args  = {nm};
        return (IODevs)components.whichOne("equalName",classes,args);
    }
    
    public ComponentsInterface getComponents(){
        return components;
    }
    
    public couprel getCouprel(){
        return cp;
    }
    
    public String toString(){
        String s = "";
        componentIterator cit = getComponents().cIterator();
        while (cit.hasNext()){
            IOBasicDevs iod = cit.nextComponent();
            s += " " + iod.toString();
        }
        return s;
    }
    
    public void showState(){
        components.tellAll("showState");
    }
    
    
    public void initialize(){
        components.tellAll("initialize");
    }
    
    
    public void setSimulator(CoupledSimulatorInterface sim){}
    public ActivityInterface getActivity(){return new activity("name");}
    public void deltext(double e,MessageInterface x){}
    public void deltcon(double e,MessageInterface x){}
    public void deltint(){}
    public MessageInterface Out(){return new message();}
    public double ta(){return 0;}
    
    public componentIterator iterator(){
        return getComponents().cIterator();
    }
    
    public void setCoordinator(coordinator coordinator_) {myCoordinator = coordinator_;}
    public coordinator getCoordinator() {return myCoordinator;}
    
//by saurabh
    
    public void printComponents(){
        System.out.println("The components of the digraph "+getName()+ " are:---------->");
        
        ComponentsInterface cpi = getComponents();
        componentIterator i = cpi.cIterator();
        while(i.hasNext()){
            IOBasicDevs iodb = i.nextComponent();
            System.out.println("Printing the iodb name " + iodb.getName());
        }
    }
    
    
    public void printCouprel(couprel cr){
        Iterator i = cr.iterator();
        while(i.hasNext())
            System.out.println("Now printing relations: "+i.next().toString());
        
    }
    
    
    /**-------------------------------------------------------------------------
     * -------------------------------------------------------------------------
     * The code below is for variable structure DEVS
     * Xiaolin Hu, March 14, 2006
     */
    
    public void dyn_addModel(IODevs iod){
        add(iod);
        getCoordinator().setNewSimulator((IOBasicDevs)iod);
    }
    
    /**
     *  This method adds couplings between any two models
     *  parameter preservePortName = ture --> preserve the port name, otherwise create
     *  the intermediate port name dynamically follwing the format #srcM%srcP%destP%destM#
     */
    public void dyn_addCoupling_AnyTwoModel(IODevs srcModel, String p1,
            IODevs destModel, String p2, boolean preservePortName){
        if(srcModel==null || destModel==null){
            System.out.println("ERROR: The source model or the destination model does not exist.");
            return;
        }
        //first check if destModel is a descendant model of the source model
        if(srcModel instanceof digraph)
            if(((digraph)srcModel).hasDescendantModel(destModel)){
            ( (digraph) (destModel.getParent())).dyn_addUpCouplingPath(srcModel,
                    p1, destModel, p2, preservePortName);
            return;
            }
        // otherwise, go one layer up
        if(srcModel.getParent()!=null)
            ((digraph)(srcModel.getParent())).dyn_findDestToAddCoupling(srcModel, p1, destModel, p2,preservePortName);
        //else System.out.println("ERROR: The source model is the root model.");
    }
    
    public void dyn_findDestToAddCoupling(IODevs srcModel, String p1,
            IODevs destModel, String p2, boolean preservePortName){
        String src = srcModel.getName();
        String dyn_portName;
        if(destModel==this) //this means the origianl srcModel actually belongs to destModel
            dyn_addCoupling_Util(srcModel.getName(),p1,destModel.getName(),p2);
        // check if the destModel belongs to a model which is brother model of the srcModel
        else if(hasDescendantModel(destModel))
            ((digraph)(destModel.getParent())).dyn_addUpCouplingPath(srcModel,p1,destModel,p2,preservePortName);
        else{ // first adds the internal output coupling path from the srcModel to this model and then goes one layer up
            if(preservePortName)
                dyn_portName = p1;  // OR p2 -- in this case, p1 and p2 shoule be the same
            else{
                if (p1.startsWith("#") && p1.endsWith("#"))  dyn_portName = p1;
                else if (p2.endsWith("#") && p2.endsWith("#")) dyn_portName = p2;
                else dyn_portName = "#" + src + "%" + p1 + "%" + p2 + "%" + destModel.getName() + "#";
            }
            dyn_addOutport(dyn_portName);
            dyn_addCoupling_Util(src,p1,this.getName(),dyn_portName);
            if(getParent()!=null)
                ((digraph)(getParent())).dyn_findDestToAddCoupling(this,dyn_portName,destModel,p2,preservePortName);
        }
    }
    
    public void dyn_addUpCouplingPath(IODevs srcM, String p1,
            IODevs destM, String p2, boolean preservePortName){
        String dyn_portName;
        if(srcM.getParent()==this && destM.getParent()==this)    //srcM and destM are brothers
            dyn_addCoupling_Util(srcM.getName(),p1,destM.getName(),p2);
        
        else if(srcM.getParent()==this){  // this is output coupling
            String src = srcM.getName();
            if(destM == this)
                dyn_addCoupling_Util(src,p1,this.getName(),p2);// this model is the destination model
            else{// doesn't find the destination, needs to go one layer upper
                if(preservePortName)
                    dyn_portName = p1; // OR p2 -- in this case, p1 and p2 shoule be the same
                else{
                    if(p1.startsWith("#")&&p1.endsWith("#")) dyn_portName = p1;
                    else if(p2.endsWith("#")&&p2.endsWith("#")) dyn_portName = p2;
                    else dyn_portName = "#"+src+"%"+p1+"%"+p2+"%"+destM.getName()+"#";
                }
                dyn_addOutport(dyn_portName);
                dyn_addCoupling_Util(src,p1,this.getName(),dyn_portName);
                digraph P = (digraph)getParent();
                if(P!=null) P.dyn_addUpCouplingPath(this, dyn_portName, destM, p2,preservePortName);
                else System.out.println("the source or the destination of the coupling cannot be found!");
            }
        } else if(destM.getParent()==this){  //this is the input coupling
            String dest = destM.getName();
            if(srcM == this)
                dyn_addCoupling_Util(this.getName(),p1,dest,p2);// this model is the src model
            else { // doesn't find the source, needs to go one layer upper
                if(preservePortName)
                    dyn_portName = p1; //OR p2 -- in this case, p1 and p2 shoule be the same
                else{
                    if(p1.startsWith("#")&&p1.endsWith("#")) dyn_portName = p1;
                    else if(p2.endsWith("#")&&p2.endsWith("#")) dyn_portName = p2;
                    else dyn_portName = "#"+srcM.getName()+"%"+p1+"%"+p2+"%"+dest+"#";
                }
                dyn_addInport(dyn_portName);
                dyn_addCoupling_Util(this.getName(),dyn_portName,dest,p2);
                digraph P = (digraph)getParent();
                if(P!=null) P.dyn_addUpCouplingPath(srcM, p1, this, dyn_portName,preservePortName);
                else System.out.println("the source or the destination of the coupling cannot be found!");
            }
        } else System.out.println("the source or the destination of the coupling cannot be found!");
    }
    
    /**
     * A utility founction to support dynamic coupling adding, including update the
     * coupling infomration in the coordiantor
     */
    public void dyn_addCoupling_Util(String src, String p1, String dest, String p2){
        if(!cp.contains(new GenCol.Pair(src,p1),new GenCol.Pair(dest,p2))){
            addPair(new GenCol.Pair(src, p1), new GenCol.Pair(dest, p2));
            coordinator c = getCoordinator();
            if(c!=null) c.addCoupling(src, p1, dest, p2);
        }
    }
    
//-----------------------------------
    /**
     *  This method removes couplings, including intermediate couplings, between any two models
     */
    public void dyn_removeCoupling_AnyTwoModel(IODevs srcModel, String p1, IODevs destModel, String p2){
        if(srcModel==null || destModel==null){
            System.out.println("ERROR: The source model or the destination model does not exist.");
            return;
        }
        //first check if destModel is a descendant model of the source model
        if(srcModel instanceof digraph)
            if(((digraph)srcModel).hasDescendantModel(destModel)){
            ( (digraph) (destModel.getParent())).dyn_removeUpCouplingPath(srcModel,
                    p1, destModel, p2);
            return;
            }
        // otherwise, go one layer up
        if(srcModel.getParent()!=null)
            ((digraph)(srcModel.getParent())).dyn_findDestToRemoveCoupling(srcModel, p1, destModel, p2);
        //else System.out.println("ERROR: The source model is the root model.");
    }
    
    public void dyn_findDestToRemoveCoupling(IODevs srcModel, String p1, IODevs destModel, String p2){
        String src = srcModel.getName();
        String dyn_portName;
        if(destModel==this) //this means the origianl srcModel actually belongs to destModel
            dyn_removeCoupling_Util(srcModel.getName(),p1,destModel.getName(),p2);
        // check if the destModel belongs to a model which is brother model of the srcModel
        else if(hasDescendantModel(destModel))
            ((digraph)(destModel.getParent())).dyn_removeUpCouplingPath(srcModel,p1,destModel,p2);
        else{ // first adds the internal output coupling path from the srcModel to this model and then goes one layer up
            if(p1.startsWith("#")&&p1.endsWith("#")) dyn_portName = p1;
            else if(p2.endsWith("#")&&p2.endsWith("#")) dyn_portName = p2;
            else dyn_portName = "#"+src+"%"+p1+"%"+p2+"%"+destModel.getName()+"#";
            dyn_removeCoupling_Util(src,p1,this.getName(),dyn_portName);
            dyn_removeOutport(dyn_portName);
            if(getParent()!=null)
                ((digraph)(getParent())).dyn_findDestToRemoveCoupling(this,dyn_portName,destModel,p2);
        }
    }
    
    public void dyn_removeUpCouplingPath(IODevs srcM, String p1, IODevs destM, String p2){
        String dyn_portName;
        if(srcM.getParent()==this && destM.getParent()==this)    //srcM and destM are brothers
            dyn_removeCoupling_Util(srcM.getName(),p1,destM.getName(),p2);
        
        else if(srcM.getParent()==this){  // this is output coupling
            String src = srcM.getName();
            if(destM == this)
                dyn_removeCoupling_Util(src,p1,this.getName(),p2);// this model is the destination model
            else{// doesn't find the destination, needs to go one layer upper
                if(p1.startsWith("#")&&p1.endsWith("#")) dyn_portName = p1;
                else if(p2.endsWith("#")&&p2.endsWith("#")) dyn_portName = p2;
                else dyn_portName = "#"+src+"%"+p1+"%"+p2+"%"+destM.getName()+"#";
                dyn_removeCoupling_Util(src,p1,this.getName(),dyn_portName);
                dyn_removeOutport(dyn_portName);
                digraph P = (digraph)getParent();
                if(P!=null) P.dyn_removeUpCouplingPath(this, dyn_portName, destM, p2);
                else System.out.println("the source or the destination of the coupling cannot be found!");
            }
        } else if(destM.getParent()==this){  //this is the input coupling
            String dest = destM.getName();
            if(srcM == this)
                dyn_removeCoupling_Util(this.getName(),p1,dest,p2);// this model is the src model
            else { // doesn't find the source, needs to go one layer upper
                if(p1.startsWith("#")&&p1.endsWith("#")) dyn_portName = p1;
                else if(p2.endsWith("#")&&p2.endsWith("#")) dyn_portName = p2;
                else dyn_portName = "#"+srcM.getName()+"%"+p1+"%"+p2+"%"+dest+"#";
                dyn_removeCoupling_Util(this.getName(),dyn_portName,dest,p2);
                dyn_removeInport(dyn_portName);
                digraph P = (digraph)getParent();
                if(P!=null) P.dyn_removeUpCouplingPath(srcM, p1, this, dyn_portName);
                else System.out.println("the source or the destination of the coupling cannot be found!");
            }
        } else System.out.println("the source or the destination of the coupling cannot be found!");
    }
    
    /**
     * A utility founction to support dynamic coupling removing, including update the
     * coupling infomration in the coordiantor
     */
    public void dyn_removeCoupling_Util(String src, String p1, String dest, String p2){
        removePair(new GenCol.Pair(src,p1),new GenCol.Pair(dest,p2));
        coordinator c = getCoordinator();
        if(c!=null) c.removeCoupling(src,p1,dest,p2);
    }
    
    /**
     * remove all the couplings for model modelName
     * assuming modelName is a direct child of this coupled model
     * Also clean all the intermediate couplings added by dyn_addCoupling_AnyTwoModel()
     */
    public void removeModelCoupling(String modelName){
        String mName, srcPort, pName, destPort;
        IODevs srcM, destM;
        couprel mc = getCouprel();
        Iterator it = mc.iterator();
        while (it.hasNext()){
            GenCol.Pair pr = (GenCol.Pair)it.next();
            GenCol.Pair cs = (GenCol.Pair)pr.getKey();
            GenCol.Pair cd = (GenCol.Pair)pr.getValue();
            String src =  (String)cs.getKey();
            String dest = (String)cd.getKey();
            if(src.equals(modelName)){
                pName = (String)cd.getValue();
                if(pName.startsWith("#")&&pName.endsWith("#")){ //this is an intermediate coupling
                    //the port name is in the format of "#srcM%srcPort%destPort%destM#"
                    mName = pName.substring(pName.lastIndexOf("%")+1,pName.length()-1);
                    destM = getRootParent().findModelWithName(mName);
                    srcM = withName(modelName);
                    srcPort = (String)cs.getValue();
                    destPort = pName.substring(pName.indexOf("%", pName.indexOf("%")+1)+1,pName.lastIndexOf("%"));
                    this.dyn_removeCoupling_AnyTwoModel(srcM,srcPort,destM,destPort);
                } else
                    dyn_removeCoupling_Util(src, (String)cs.getValue(), dest,pName);
            } else if(dest.equals(modelName)){
                pName = (String)cs.getValue();
                if(pName.startsWith("#")&&pName.endsWith("#")){ //this is an intermediate coupling
                    //the port name is in the format of "#srcM%srcPort%destPort%destM#"
                    mName = pName.substring(1,pName.indexOf("%"));
                    srcM = getRootParent().findModelWithName(mName);
                    destM = withName(modelName);
                    srcPort = pName.substring(pName.indexOf("%")+1,pName.indexOf("%",pName.indexOf("%")+1));
                    destPort = (String)cd.getValue();
                    this.dyn_removeCoupling_AnyTwoModel(srcM,srcPort,destM,destPort);
                } else
                    this.dyn_removeCoupling_Util(src,pName,dest,(String)cd.getValue());
            }
        }
    }
    
//-----------------------------------
    public void dyn_removeModel(IODevs rmModel){ //remove a child model
        if(rmModel==null) return;
        else{
            // first remove all the coupling related to that model
            removeModelCoupling(rmModel.getName());
            // then remove the model
            remove(rmModel);
            getCoordinator().removeModel(rmModel);
        }
    }
    
    public void dyn_removeModelAndAncestors(IODevs rmModel){
        this.dyn_removeModel(rmModel);
        if(this.getComponents().size()==0){
            digraph P = (digraph)getParent();
            if(P!=null) P.dyn_removeModelAndAncestors(this);
        }
    }
    
    public IODevs findModelWithName(String nm){  //search the whole subtree
        if(nm.compareTo(this.getName())==0) return this;
        else{
            componentIterator cit = getComponents().cIterator();
            while (cit.hasNext()){
                IODevs iod = cit.nextComponent();
                IODevs m = iod.findModelWithName(nm);
                if(m!=null) return m;
            }
            return null;
        }
    }
    
    public ComponentsInterface findModel_NameStartWith(String nm){  //search the whole subtree
        ComponentsInterface c = new Components();
        componentIterator cit = getComponents().cIterator();
        while (cit.hasNext()){
            IODevs iod = cit.nextComponent();
            if (iod.getName().startsWith(nm))
                c.add(iod);
            if (iod instanceof digraph)
                c.addAll( ((digraph)iod).findModel_NameStartWith(nm));
        }
        return c;
    }
    
    public IODevs getAncestor_BrotherOf(IODevs m){
        if(this.isBrother(m)) return this;
        else{
            digraph p = (digraph)getParent();
            if(p==null) return null;
            else return p.getAncestor_BrotherOf(m);
        }
    }
    
    public IODevs getAncestor_ChildOf(IODevs m){
        digraph p = (digraph)getParent();
        if(p==null) return null;
        else{
            if(p==m) return this;
            else return p.getAncestor_ChildOf(m);
        }
    }
    
    public IODevs getRootParent(){
        digraph p = (digraph)getParent();
        if(p==null) return this;
        else return p.getRootParent();
    }
    
// check if model iod belongs to the subtree of this coupled model
    public boolean hasDescendantModel(IODevs iod){
        IODevs p = iod.getParent();
        if(p==null) return false;
        else{
            if(p==this) return true;
            else return hasDescendantModel(p);
        }
    }
//------------------------------------------------------------------------------
    
    
    
}



