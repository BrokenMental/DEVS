/**
 * 
 */
package genDevs.jaxb.fddevs;

/**
 * @author Saurabh
 *
 */
public class ExtTransition {

	private String incomingMsg;
	private boolean scheduleIndicator;
	private Transition transition;
	private String hook;
	/**
	 * 
	 */
	public ExtTransition(String inMsg, boolean indicator, Transition tr) {
		this.incomingMsg = inMsg;
		this.scheduleIndicator = indicator;
		this.transition = tr;
		setHook();
	}

	public void setHook() {
		hook = "process"+transition.getNextState()+"()";
	}
	
	public String getHook() {
		return hook;
	}
	/**
	 * @return the incomingMsg
	 */
	public String getIncomingMsg() {
		return incomingMsg;
	}
	/**
	 * @param incomingMsg the incomingMsg to set
	 */
	public void setIncomingMsg(String incomingMsg) {
		this.incomingMsg = incomingMsg;
	}
	/**
	 * @return the scheduleIndicator
	 */
	public boolean isScheduleIndicator() {
		return scheduleIndicator;
	}
	/**
	 * @param scheduleIndicator the scheduleIndicator to set
	 */
	public void setScheduleIndicator(boolean scheduleIndicator) {
		this.scheduleIndicator = scheduleIndicator;
	}
	/**
	 * @return the transition
	 */
	public Transition getTransition() {
		return transition;
	}
	/**
	 * @param transition the transition to set
	 */
	public void setTransition(Transition transition) {
		this.transition = transition;
	}
	
}
