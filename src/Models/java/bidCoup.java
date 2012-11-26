package Models.java;

import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class bidCoup extends ViewableDigraph{

public bidCoup(){
this("bidCoup");}

public bidCoup(String nm){
super(nm);

	ViewableAtomic bidProcInstance = new bidProc();
	add(bidProcInstance);

	ViewableAtomic bidCoordInstance = new bidCoord();
	add(bidCoordInstance);


	addCoupling(bidProcInstance,"outBid",bidCoordInstance,"inBid");

	addCoupling(bidCoordInstance,"outfree?",bidProcInstance,"infree?");

	addCoupling(bidProcInstance,"outJobDone",bidCoordInstance,"inJobDone");

	addCoupling(bidCoordInstance,"outJob",bidProcInstance,"inJob");

}
}