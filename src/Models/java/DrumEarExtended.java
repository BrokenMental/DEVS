/*
 * DrumEar.java
 *
 * Created on October 24, 2007, 4:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Models.java;

import genDevs.modeling.*;
import genDevs.simulation.*;
import java.util.*;
import GenCol.*;
import simView.*;
import java.awt.*;

public class DrumEarExtended extends ViewableDigraph{

public DrumEarExtended(){
this("DrumEarExtended");}

public DrumEarExtended(String nm){
super(nm);
addOutport("out");

	ViewableAtomic DrumInstance = new Drum();
	add(DrumInstance);

	ViewableAtomic EarInstance = new EarMyExtended();//new EarExtended();//
	add(EarInstance);


	addCoupling(DrumInstance,"outBeat",EarInstance,"inBeat");
        addCoupling(EarInstance,"outSync",this,"out");
        addCoupling(EarInstance,"outNotSync",this,"out");
        addCoupling(EarInstance,"outReport",this,"out");
}
    /**
     * Automatically generated by the SimView program.
     * Do not edit this manually, as such changes will get overwritten.
     */
    public void layoutForSimView()
    {
ComputeLayout = false;
        preferredSize = new Dimension(565, 223);
        if (withName("EarMyExtended")!=null)
             ((ViewableComponent)withName("EarMyExtended")).setPreferredLocation(new Point(246, 93));
        if (withName("Drum")!=null)
             ((ViewableComponent)withName("Drum")).setPreferredLocation(new Point(109, 87));
    }
}
