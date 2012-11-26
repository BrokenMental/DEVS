package Models.java;

import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class pipAr extends ViewableDigraph{

public pipAr(){
this("pipAr");}

public pipAr(String nm){
super(nm);

	ViewableAtomic procWport2Instance = new procWport2();
	add(procWport2Instance);

	ViewableAtomic pipeCoordInstance = new pipeCoord();
	add(pipeCoordInstance);

	ViewableAtomic procWport1Instance = new procWport1();
	add(procWport1Instance);


	addCoupling(procWport2Instance,"outJob2",pipeCoordInstance,"inJob2");

	addCoupling(procWport1Instance,"outJob1",pipeCoordInstance,"inJob1");

	addCoupling(pipeCoordInstance,"outJ2",procWport2Instance,"inJ2");

	addCoupling(pipeCoordInstance,"outJ1",procWport1Instance,"inJ1");

}
}