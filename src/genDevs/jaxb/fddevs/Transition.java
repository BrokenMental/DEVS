/**
 * 
 */
package genDevs.jaxb.fddevs;

/**
 * @author Saurabh
 *
 */
public class Transition {

	private String startState;
	private String nextState;
	
	/**
	 * 
	 */
	public Transition(String startState, String nextState) {
		this.startState = startState;
		this.nextState = nextState;
	}
	/**
	 * @return the nextState
	 */
	public String getNextState() {
		return nextState;
	}
	/**
	 * @param nextState the nextState to set
	 */
	public void setNextState(String nextState) {
		this.nextState = nextState;
	}
	/**
	 * @return the startState
	 */
	public String getStartState() {
		return startState;
	}
	/**
	 * @param startState the startState to set
	 */
	public void setStartState(String startState) {
		this.startState = startState;
	}
	

}
