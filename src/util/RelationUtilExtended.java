

/*
/*  no correspondence in Java collections
/*  iteration is through Pairs rather than Entries
 */

package util;


import java.util.*;


public class RelationUtilExtended <D,R> extends RelationUtil{
    public  Set<D> baseKeys;
    public  Set<R> baseValues;
    
    
    public RelationUtilExtended(){
        super();
        baseKeys = new HashSet();
        baseValues = new HashSet();
    }
    
    public RelationUtilExtended(Set<D> baseKeys, Set<R> baseValues){
        super();
        this.baseKeys = baseKeys;
        this.baseValues = baseValues;
    }
    
    public void addKey(D key){
        baseKeys.add(key);
    }
    
    public void addValue(R value){
        baseValues.add(value);
    }
    
    public void removeKey(D key){
        baseKeys.remove(key);
    }
    
    public void removeValue(R value){
        baseValues.remove(value);
    }
    
    public void removeAllWithKey(D key){
        removeAll(key);
    }
    
    public void removeAllWithValue(R value){
       for (D key:getDomainOf(value)){
           remove(key,value);
       }
        
    }
    
    public  synchronized Object putExtended(D key, R value){
        addKey(key);
        addValue(value);
        return put(key,value);
    }
    
    public  synchronized Object removeExtended(D key, R value){
        return remove(key,value);
    }
    
    public Set<D> DomainSet(){
        return keySet();
    }
    
    public Set<R> RangeSet(){
        return valueSet();
    }
    
    public Set<R> getRangeOf(D key){
        return getSet(key);
    }
    
    
    public R getValueOf(D key){
        return (R)get(key);
    }
    
    public Set<D> getDomainOf(R value){
        HashSet hs = new HashSet();
        Iterator it = iterator();
        while(it.hasNext()){
            PairUtil p = (PairUtil)it.next();
            if (value.equals(p.getValue()))
                hs.add(p.getKey());
        }
        return hs;
    }
    
    
    public boolean containsExtended(D key, R value){
        return  getRangeOf(key).contains(value);
    }
    
    public int sizeOfRange(D key){
        return getRangeOf(key).size();
    }
    
    public boolean keyLacksValue(D key){
        return sizeOfRange(key)==0;
    }
    
    public boolean keyHasSingleValue(D key){
        return sizeOfRange(key)==1;
    }
    
    public boolean keyHasMultipleValues(D key){
        return sizeOfRange(key)>1;
    }
    
    public boolean keyHasAtLeastOneValue(R value){
        return sizeOfDomain(value) >0;
    }
    
    public int sizeOfDomain(R value){
        return getDomainOf(value).size();
    }
    
    public boolean valueHasSingleKey(R value){
        return sizeOfDomain(value)==1;
    }
    
    public boolean valueHasMultipleKeys(R value){
        return sizeOfDomain(value)> 1;
    }
    
    public boolean valueHasAtLeastOneKey(R value){
        return sizeOfDomain(value) > 0;
    }
    
    public double completeness(){
        if (baseKeys.size()>0)
            return (double)DomainSet().size()/baseKeys.size();
        else return 0;
    }
    
    public int assignedRangeSetSize(){
        int total = 0;
        for(D key: baseKeys)   {
            total += sizeOfRange(key);
        }
        return total;
    }
    
    public double ambiguity(){//problem with addition
        if (baseKeys.size()>0)
            return (double)assignedRangeSetSize()/baseKeys.size();
        else return 0;
    }
    
    
    
    public double ambiguityForCoveredKeys(){
        if (DomainSet().size()>0)
            return (double)assignedRangeSetSize()/DomainSet().size();
        else return 0;
    }
    
    
    public double spuriousness(){
        //System.out.println(baseValues.size()+" "+ RangeSet().size());
        if (baseValues.size()>0)
            return 100.0*(baseValues.size() - RangeSet().size())
            /baseValues.size();
        else return 0;
    }
    
    
    public static void main(String argv[]) {
        
        class  HashSetabc extends HashSet<String>{
            public HashSetabc(){
                add("a");
                add("b");
                add("c");
            }
        }
        class  HashSetABC extends HashSet<String>{
            public HashSetABC(){
                add("A");
                add("B");
                add("C");
            }
        }
        
      /*
        class stringRelExt extends RelationUtilExtended <String,String>{
            public stringRelExt(){
                this(new HashSetabc(),new HashSetABC());
            }
        }
       
        stringRelExt r = new stringRelExt();
       */
        RelationUtilExtended r = new RelationUtilExtended<String,String>
                (new HashSet<String>(),new HashSet<String>());
        //  r.baseKeys = new HashSetabc();
        //    r.baseValues = new HashSetABC();
        r.addKey("a");
        r.addKey("b");
        r.addKey("c");
        r.addKey("d");
        r.putExtended("a","A");
        r.putExtended("a","AA");
        r.putExtended("b","A");
        r.putExtended("c","A");
        r.addValue("AAA");
        
        System.out.println(r.getRangeOf("a"));
        System.out.println(r.getDomainOf("A"));
        System.out.println(r.sizeOfRange("a"));
        System.out.println(r.completeness());
        System.out.println(r.ambiguity());
        System.out.println(r.ambiguityForCoveredKeys());
        System.out.println(r.spuriousness());
        
        r.removeExtended("c","A");
        
        System.out.println(r.getRangeOf("a"));
        System.out.println(r.getDomainOf("A"));
        System.out.println(r.sizeOfRange("a"));
        System.out.println(r.completeness());
        System.out.println(r.ambiguity());
        System.out.println(r.ambiguityForCoveredKeys());
        System.out.println(r.spuriousness());
        
        r.removeAllWithValue("A");
        System.out.println(r.getDomainOf("A"));
    }
}



