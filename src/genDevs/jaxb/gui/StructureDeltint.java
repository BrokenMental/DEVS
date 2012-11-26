/*
 * StructureDeltint.java
 * 
 * Created on Jul 12, 2007, 1:33:32 PM
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package genDevs.jaxb.gui;

    
/**
 *
 * @author Saurabh
 */
public class StructureDeltint {

    int seqId;
    String startState;
    String nextState;
    double ta;
    String outgoingMsg;
    
    public StructureDeltint(int id, String start, String  next, double ta, String outMsg) {
        seqId = id;
        startState = start;
        nextState = next;
        this.ta = ta;
        outgoingMsg = outMsg;
    }

}
