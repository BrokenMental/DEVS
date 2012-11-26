package Models.java;

import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class InteroperabilityTB extends ViewableDigraph{

public InteroperabilityTB(){
this("InteroperabilityTB");}

public InteroperabilityTB(String nm){
super(nm);

	ViewableAtomic SUTInstance = new SUT();
	add(SUTInstance);

	ViewableAtomic ATCGenObserverInstance = new ATCGenObserver();
	add(ATCGenObserverInstance);

	ViewableAtomic SensorSimInstance = new SensorSim();
	add(SensorSimInstance);


	addCoupling(SUTInstance,"outRRCommand",ATCGenObserverInstance,"inRRCommand");

	addCoupling(SensorSimInstance,"outRadarTrack",SUTInstance,"inRadarTrack");

}
}