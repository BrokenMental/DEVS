/**
 * 
 */
package genDevs.jaxb.coupled;
import java.util.*;


/**
 * @author Saurabh
 * Aug 25, 2007, 9:11:47 AM
 * genDevs.modeling.coupled
 * CoupledDEVSInterface.java
 * 
 */
public interface CoupledDEVSInterface {

		public String getCoupledModelName();
		public String getCoupledModelHost();
		public Vector<String> getModelComponents();
		public Vector<CouplingRelation> getCouplingRelations();
		
}
