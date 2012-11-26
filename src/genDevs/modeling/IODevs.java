package genDevs.modeling;

import GenCol.*;

import java.util.*;



public interface IODevs extends EntityInterface{

public void addInport(String portName);
public void addOutport(String portName); //----------- delete?
public void removeInport(String portName);  //-------- delete?
public void removeOutport(String portName);
public ContentInterface makeContent(PortInterface port,EntityInterface value);
public boolean   messageOnPort(MessageInterface x, PortInterface port, ContentInterface c);

// The following methods are added by Xiaolin Hu, March 2006
public IODevs getParent();
public IODevs findModelWithName(String modelName);
public ports getInports();
public ports getOutports();
public void dyn_addInport(String portName);
public void dyn_addOutport(String portName);
public void dyn_removeInport(String portName);
public void dyn_removeOutport(String portName);
}