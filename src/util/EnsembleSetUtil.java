package util;

import java.util.*;

public class EnsembleSetUtil extends HashSet {

    public EnsembleSetUtil() {
        super();
    }

    public EnsembleSetUtil(Collection c) {
        super(c);
    }

    public String toString() {
        return new LinkedList(this).toString();
    }

}

