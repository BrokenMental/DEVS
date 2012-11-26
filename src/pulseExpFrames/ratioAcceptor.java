package pulseExpFrames;

import simView.*;
import genDevs.plots.*;
import java.awt.*;
import java.lang.*;
import genDevs.modeling.*;
import genDevs.simulation.*;
import GenCol.*;

import java.util.*;



public class ratioAcceptor extends  ViewableDigraph{

public ratioAcceptor(){
this(10);
}

public ratioAcceptor(double threshold){
super("ratioAcceptor");

addInport("in");
addInport("setDivisor");
addInport("setDividend");
addOutport("out");
addOutport("stop");

ViewableAtomic quantity = new sum("quantity",0);
add(quantity);

addCoupling(this,"in",quantity,"in");

addCoupling(quantity,"out",this,"out");

ViewableAtomic d = new instantReal.divide("divide");
add(d);

addCoupling(this,"setDividend",d,"setDividend");
addCoupling(this,"setDivisor",d,"in");

ViewableAtomic thresh = new thresholdTester(threshold);
add(thresh);


addCoupling(d,"out",thresh,"in");
addCoupling(thresh,"out",this,"stop");

}

    /**
     * Automatically generated by the SimView program.
     * Do not edit this manually, as such changes will get overwritten.
     */
    public void layoutForSimView()
    {
        preferredSize = new Dimension(515, 253);
        ((ViewableComponent)withName("quantity")).setPreferredLocation(new Point(57, 37));
        ((ViewableComponent)withName("thresholdTester")).setPreferredLocation(new Point(203, 100));
        ((ViewableComponent)withName("divide")).setPreferredLocation(new Point(41, 151));
    }
}
