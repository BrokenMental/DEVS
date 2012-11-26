
package pulseExpFrames;

import simView.*;
import genDevs.plots.*;
import java.awt.*;
import java.lang.*;
import genDevs.modeling.*;
import genDevs.simulation.*;
import GenCol.*;
import genDevs.modeling.*;

public class rateEstimatorTime extends  ViewableDigraph{

public rateEstimatorTime(){
this("rateEstimatorTime",10);
}

public rateEstimatorTime(String nm,double threshold){
super(nm);
addInport("in");
addInport("start");
addOutport("rate");
addTestInput("in",new doubleEnt(1),0);
addTestInput("start",new doubleEnt(10),0);
addTestInput("in",new doubleEnt(1),2);

ViewableAtomic sum = new sum("sum",0);//state
add(sum);
addCoupling(this,"in",sum,"in");


ViewableAtomic cl = new myTimer("timer");
add(cl);
addCoupling(this,"start",cl,"start");
addCoupling(cl,"out",sum,"reset");


ViewableAtomic d = new instantReal.divide("divide");
add(d);

addCoupling(sum,"out",d,"setDividend");
addCoupling(cl,"out",d,"in");
addCoupling(d,"out",this,"rate");

}

    /**
     * Automatically generated by the SimView program.
     * Do not edit this manually, as such changes will get overwritten.
     */
    public void layoutForSimView()
    {
        preferredSize = new Dimension(464, 204);
        ((ViewableComponent)withName("divide")).setPreferredLocation(new Point(169, 116));
        ((ViewableComponent)withName("sum")).setPreferredLocation(new Point(13, 29));
        ((ViewableComponent)withName("timer")).setPreferredLocation(new Point(-10, 118));
    }
}
