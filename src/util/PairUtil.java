/*
/*
/*   Pair is more  primitive than Entry which is private to Hashtable
*/

package util;



import java.util.*;

public class PairUtil {
public Object key,value;

// Empty Pair constructor
public PairUtil(){
}

// Key and value pairs
public PairUtil(Object Key, Object Value){
key = Key;
value = Value;
}

public String toString(){
return "key = " + key.toString() + " ,value = "+ value.toString();
}


public Object getKey(){
return key;
}

public Object getValue(){
return value;
}


}
