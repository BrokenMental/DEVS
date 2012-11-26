package util;

import java.util.*;

import GenCol.Queue;

public class restrictRelation extends RelationUtil {
    public String domainSpec, rangeSpec;
    public EnsembleSetUtil domain, range;

    public restrictRelation(String domainSpec, String rangeSpec,
                            EnsembleSetUtil domain, EnsembleSetUtil range) {
        this.domainSpec = domainSpec;
        this.rangeSpec = rangeSpec;
        this.domain = domain;
        this.range = range;
    }

    public void setRelation(RelationUtil r){
      Iterator it = r.iterator();
      while (it.hasNext()){
          PairUtil p = (PairUtil)it.next();
          addPair((String)p.getKey(),(String)p.getValue());
      }
    }

    public void addPair(String key, String val) {
        if (domain.contains(key) && range.contains(val)) {
            put(key, val);
        } else {
            System.out.println(key + " or " + val + " not in domain/range");
            System.exit(3);
        }
    }

    public void addAllVals(String key) {
        Iterator is = range.iterator();
        while (is.hasNext()) {
            addPair(key, (String) is.next());
        }
    }

    public void addAllKeys(String val) {
        Iterator is = domain.iterator();
        while (is.hasNext()) {
            addPair((String) is.next(), val);
        }
    }

    public void addAllPairs() {
        Iterator it = domain.iterator();
        Iterator is = range.iterator();
        while (it.hasNext()) {
            while (is.hasNext()) {
                addPair((String) it.next(), (String) is.next());
            }
        }
    }

    public void addAllValsExcept(String key, String[] vals) {
        EnsembleSetUtil es = stringOps.toEnsembleSet(vals);
        EnsembleSetUtil rc = new EnsembleSetUtil(range);
        rc.removeAll(es);
        Iterator is = rc.iterator();
        while (is.hasNext()) {
            addPair(key, (String) is.next());
        }
    }

    public void addAllKeysExcept(String[] keys, String val) {
        EnsembleSetUtil es = stringOps.toEnsembleSet(keys);
        EnsembleSetUtil dc = new EnsembleSetUtil(domain);
        dc.removeAll(es);
        Iterator is = dc.iterator();
        while (is.hasNext()) {
            addPair((String) is.next(), val);
        }
    }

    public Queue place(Queue specs) {
        Iterator it = specs.iterator();
        int i = 0;
        while (it.hasNext()) {
            String spec = (String) it.next();
            if (spec.equals(rangeSpec)) {
                break;
            } else {
                i++;
            }
        }
        if (!specs.contains(domainSpec)) {
            specs.add(i, domainSpec);
        }
        if (!specs.contains(rangeSpec)) {
            if (i + 1 < specs.size()) {
                specs.add(i + 1, rangeSpec);
            } else {
                specs.add(rangeSpec);
            }
        }
        return specs;
    }

  public  restrictRelation makeConverse(){
        restrictRelation r = new restrictRelation(rangeSpec,domainSpec,range,domain);
        Iterator it = iterator();
        while(it.hasNext()){
            PairUtil p = (PairUtil)it.next();
            r.put(p.getValue(),p.getKey());
        }
        return r;
    }
}
