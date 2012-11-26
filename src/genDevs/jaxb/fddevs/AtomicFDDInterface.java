/**
 * 
 */
package genDevs.jaxb.fddevs;

import java.util.Hashtable;

/**
 * @author Saurabh
 *
 */
public interface AtomicFDDInterface {
	public String getModelName();
	public String getHost();
	public String[] getInports();
	public String[] getOutports();
	public String[] getStates();
	public Hashtable getDeltint();
	public Hashtable getDeltext();
	public Hashtable getTimeAdvance();
	public Hashtable getLamda();
}
