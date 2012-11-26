package genDevs.modeling;

import GenCol.*;

import java.util.*;



public abstract class devs extends entity implements IODevs{
//IOBasicDevs,EntityInterface{

//protected
public messageHandler mh;
protected IODevs myParent;

public devs(String name){
super(name);
mh = new messageHandler();
}

public void setParent(IODevs p) { myParent = p;}
public IODevs getParent() {
   if(this.getName()=="modelR1b")  System.out.println("get parent for "+this.getName());
    return myParent;}

public void addInport(String portName){mh.addInport(portName);}
public void addOutport(String portName){mh.addOutport(portName);}

public void removeInport(String portName){mh.removeInport(portName);}
public void removeOutport(String portName){mh.removeOutport(portName);}

public void printInports(){mh.printInports();}
public void printOutports(){mh.printOutports();}


public ContentInterface makeContent(PortInterface port,EntityInterface value)
              {return mh.makeContent(port, value); }
public content makeContent(String p,entity value){  //for use in usual devs
return new content(p,value);
}
public boolean   messageOnPort(MessageInterface x, PortInterface port, ContentInterface c)
            {return mh.messageOnPort(x,port,c);}

public boolean   messageOnPort(message x,String p, int i){ //for use in usual devs
return x.onPort(p,i);
}
abstract public  void initialize();

//-----------------------------------------------------------------
public IODevs findModelWithName(String nm){  //Xiaolin Hu, Nov 18, 2003
  if(nm.compareTo(getName())==0) return this;
  else return null;
}

//check if model iod is a brother model of this model
public boolean isBrother(IODevs iod){
  if(this.getParent()!=null)
    if(this.getParent()==iod.getParent()) return true;

  return false;
}

public ports getInports(){
  return mh.getInports();
}

public ports getOutports(){
  return mh.getOutports();
}

public void dyn_addInport(String portName){
  addInport(portName);
  updateView();
}

public void dyn_addOutport(String portName){
  addOutport(portName);
  updateView();
}

public void dyn_removeOutport(String portName){
  removeOutport(portName);
  updateView();
}

public void dyn_removeInport(String portName){
  removeInport(portName);
  updateView();
}

public void updateView(){
  // do nothing
}

}

