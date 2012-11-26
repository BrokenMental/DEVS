/*
 * StructureDeltext.java
 * 
 * Created on Jul 12, 2007, 2:04:24 PM
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package genDevs.jaxb.gui;

/**
 *
 * @author Saurabh
 */
public class StructureDeltext {

    int seqId;
    String incomingMsg;
    String startState;
    String nextState;
    boolean reschedule;
    double ta;
    String outgoingMsg;
    
    public StructureDeltext(int id, String incomingMsg, 
            String start, String next, boolean reschedule,
            double ta, String outgoingMsg) {
        seqId = id;
        this.incomingMsg = incomingMsg;
        this.startState = start;
        this.nextState = next;
        this.ta = ta;
        this.reschedule = reschedule;
        this.outgoingMsg = outgoingMsg;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((incomingMsg == null) ? 0 : incomingMsg.hashCode());
		result = prime * result
				+ ((nextState == null) ? 0 : nextState.hashCode());
		result = prime * result
				+ ((outgoingMsg == null) ? 0 : outgoingMsg.hashCode());
		result = prime * result + (reschedule ? 1231 : 1237);
		result = prime * result + seqId;
		result = prime * result
				+ ((startState == null) ? 0 : startState.hashCode());
		long temp;
		temp = Double.doubleToLongBits(ta);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final StructureDeltext other = (StructureDeltext) obj;
		if (incomingMsg == null) {
			if (other.incomingMsg != null)
				return false;
		} else if (!incomingMsg.equals(other.incomingMsg))
			return false;
		if (nextState == null) {
			if (other.nextState != null)
				return false;
		} else if (!nextState.equals(other.nextState))
			return false;
		if (outgoingMsg == null) {
			if (other.outgoingMsg != null)
				return false;
		} else if (!outgoingMsg.equals(other.outgoingMsg))
			return false;
		if (reschedule != other.reschedule)
			return false;
		if (startState == null) {
			if (other.startState != null)
				return false;
		} else if (!startState.equals(other.startState))
			return false;
		if (Double.doubleToLongBits(ta) != Double.doubleToLongBits(other.ta))
			return false;
		return true;
	}

}
