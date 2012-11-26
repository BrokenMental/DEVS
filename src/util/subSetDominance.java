/*
 * dominace.java
 *
 * Created on December 31, 2007, 9:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package util;

import java.util.Iterator;

/**
 *
 * @author Bernie
 */
public class subSetDominance  extends dominance <EnsembleSetUtil>  {
    
    
    /** Creates a new instance of dominace */
    
    public subSetDominance(RelationUtil basicDominates) {
       super(basicDominates);
    }
    
    
    public subSetDominance() {
        
    }
    public boolean dominates(EnsembleSetUtil s1, EnsembleSetUtil s2){
        //   return s1.containsAll(s2);
        return dominatesTrans.contains(s1,s2);
    }
    
    public static void main(String args[]){
        
        
        EnsembleSetUtil abc = new EnsembleSetUtil();
        abc.add("a"); abc.add("b"); abc.add("c");
        
        EnsembleSetUtil ab = new EnsembleSetUtil();
        ab.add("a"); ab.add("b");
        
        EnsembleSetUtil ac = new EnsembleSetUtil();
        ac.add("a"); ac.add("c");
        
        EnsembleSetUtil c = new EnsembleSetUtil();
        c.add("c");
        
        EnsembleSetUtil d = new EnsembleSetUtil();
        d.add("d");
        
        EnsembleSetUtil sets = new EnsembleSetUtil();
        sets.add(abc);
        sets.add(ab);
        sets.add(ac);
        sets.add(c);
        sets.add(d);
        
        
        RelationUtil basicIncludes = new RelationUtil();
        basicIncludes.put(abc,ac);
        basicIncludes.put(abc,ab);
        basicIncludes.put(ac,c);
      //  basicIncludes.put(c,d);
        
        subSetDominance ssd = new subSetDominance(basicIncludes);
        
        System.out.println(ssd.findDominators(sets));
    }
    
    
}

