package GenCol;


import java.util.*;

/* Stores linked list */
public class Queue extends LinkedList { //adds at beginning, removes at end

    //the following remove can not be used as a mixed query/state method -- 
    //the revision below allows this to happen and is implemented in DEVSJAVA 3.2
    
    public Object remove() {
        if (size() > 0) {
            remove(0);
            return null;
        } else {
            return null;
        }
    }
    
    /*
    //DEVSJAVA2.7.0 implementation is revised for compatability with JDK5.0 
public Object remove(){
if (size()>0){
return remove(0);
}
return null;
}
     */
    
    public Object first() {
        return get(0);
    }


    static public Queue set2Queue(Set s) {
        Queue q = new Queue();
        Iterator it = s.iterator();
        while (it.hasNext()) {
            q.add(it.next());
        }
        return q;
    }


}

