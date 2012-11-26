package util;

import java.util.*;

import GenCol.Queue;

public class RelationIteratorUtil implements Iterator{
private RelationUtil r;
private Queue keys;
private Object curKey = null;
private Set curSet;
private Queue values;
private boolean change = true;

public RelationIteratorUtil(RelationUtil R){
r = R;
Set keyset = R.keySet();
keys = Queue.set2Queue(keyset);
}

private Object Next(){
if (keys.isEmpty())return null;
if (change){
change = false;
curKey = keys.first();
curSet = r.getSet(curKey);
values = Queue.set2Queue(curSet);
}
if (values.isEmpty()) return null;
return new PairUtil(curKey,values.first());
}


private void removeNext(){
values.remove();
if (values.isEmpty()){
  keys.remove();
  change = true;
  }
}

public boolean hasNext(){
return Next() != null;
}

public Object next(){
Object ret = Next();
removeNext();
return ret;
}

public void remove(){
}

}
