/*
 * dominace.java
 *
 * Created on December 31, 2007, 9:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package util;

import GenCol.Pair;
import java.util.Iterator;

/**
 *
 * @author Bernie
 */
public class dominance <T> {
    
    protected RelationUtil dominatesTrans;
    
    /** Creates a new instance of dominace */
    public dominance() {
    }
    
    
    public dominance(RelationUtil basicDominates) {
        dominatesTrans = transitiveClosure(basicDominates);
        System.out.println(dominatesTrans);
    }
    
    
    public  EnsembleSetUtil findDominators(EnsembleSetUtil objects){
        EnsembleSetUtil dominators = new EnsembleSetUtil();
        Iterator it = objects.iterator();
        while (it.hasNext()){
            int size = dominators.size();
            T o = (T)it.next();
            if (o == null)continue;
            EnsembleSetUtil dominated = dominatesSubset(o,dominators);
            dominators.removeAll(dominated);
            if (newDominator(o,dominators))
                dominators.add(o);
        }
        return dominators;
    }
    
    public boolean dominates(T t1,T t2){
        return true;
    }
    
    public EnsembleSetUtil dominatesSubset(T o,EnsembleSetUtil dominators){
        EnsembleSetUtil dominated = new EnsembleSetUtil();
        Iterator it = dominators.iterator();
        while (it.hasNext()){
            T dominator = (T)it.next();
            if (dominates(o,dominator))
                dominated.add(dominator);
        }
        return dominated;
    }
    
    
    
    public  boolean newDominator(T o,EnsembleSetUtil dominators){
        Iterator it = dominators.iterator();
        while (it.hasNext()){
            T dominator = (T)it.next();
            if (dominates(dominator,o))
                return false;
        }
        return true;
    }
    
    public static RelationUtil copyRelation(RelationUtil from,RelationUtil to){
        Iterator it = from.iterator();
        while (it.hasNext()){
            PairUtil p = (PairUtil)it.next();
            to.put(p.getKey(),p.getValue());
        }
        return to;
    }
    
    public static RelationUtil compose(RelationUtil r1,RelationUtil r2){
        RelationUtil res = new RelationUtil();
        Iterator it = r1.iterator();
        while (it.hasNext()){
            PairUtil p = (PairUtil)it.next();
            Iterator is = r2.iterator();
            while (is.hasNext()){
                PairUtil q = (PairUtil)is.next();
                if (p.getValue().equals(q.getKey()))
                    res.put(p.getKey(),q.getValue());
            }
        }
        return res;
    }
    
    public static RelationUtil removePairs(RelationUtil from,RelationUtil to){
        RelationUtil res = new RelationUtil();
        Iterator it = from.iterator();
        while (it.hasNext()){
            PairUtil p = (PairUtil)it.next();
            if(!to.contains(p.getKey(),p.getValue()))
                res.put(p.getKey(),p.getValue());
        }
        return res;
    }
    
    public static RelationUtil transitiveClosure(RelationUtil r){
        RelationUtil res = new RelationUtil();
        res = copyRelation(r,res);
        
        RelationUtil newPairs = new RelationUtil();
        while(true){
            newPairs = compose(res,res);
            newPairs = removePairs(newPairs,res);
            if (newPairs.isEmpty())break;
            res = copyRelation(newPairs,res);
        }
        return res;
    }
    
    public static void main(String args[]){
        RelationUtil r = new RelationUtil();
        r.put("a","b");
        r.put("b","c");
        //   System.out.println(compose(r,r));
       // System.out.println(transitiveClosure(r));
    }
    
    
}
