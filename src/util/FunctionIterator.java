
package util;


import java.util.*;

import GenCol.Queue;


public class FunctionIterator implements Iterator{
private FunctionUtil f;
private Queue keys;
private Object curKey = null;


public FunctionIterator (FunctionUtil F){
f = F;
Set keyset = F.keySet();
keys = Queue.set2Queue(keyset);
}

private PairUtil Next(){
if (keys.isEmpty())return null;
curKey = keys.first();
return new PairUtil(curKey,f.get(curKey));
}


public Object next(){
Object ret = Next();
removeNext();
return ret;
}



private void removeNext(){
keys.remove();
}

public boolean hasNext(){
return Next() != null;
}



public void remove(){
}

}
