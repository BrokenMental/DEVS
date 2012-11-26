/**
 * 
 */
package genDevs.jaxb.coupled;
import java.util.*;
/**
 * @author Saurabh
 * Aug 25, 2007, 9:12:01 AM
 * genDevs.modeling.coupled
 * CoupledJAXBInterface.java
 * 
 */
public interface CoupledJAXBInterface  {

	public void setCoupledModelName(String name);
	public void setCoupledModelHost(String host);
	public void setCoupledModelComponents(Vector<String> modelClassNames);
	public void setCouplingRelations(Vector<CouplingRelation> couplingRelations);
}
