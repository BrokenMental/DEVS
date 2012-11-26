/*
 * StructureCoupling.java
 * 
 * Created on Jul 14, 2007, 5:33:22 PM
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package genDevs.jaxb.gui;

/**
 *
 * @author Saurabh
 */
public class StructureCoupling {

    String src;
    String dest;
    String outport;
    String inport;
    
    public StructureCoupling(String src, String outport,
            String dest, String inport) {
        this.src = src;
        this.outport = outport;
        this.dest = dest;
        this.inport = inport;
    }

}
