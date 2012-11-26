/*      Copyright 1999 Arizona Board of regents on behalf of
 *                  The University of Arizona
 *                     All Rights Reserved
 *         (USE & RESTRICTION - Please read COPYRIGHT file)
 *
 *  Version    : DEVSJAVA2.6
 *  Date       : 04-15-00
 */

package GenCol;

import java.lang.*;
import util.*;


public class doubleEnt extends entity {

	double v;

    public doubleEnt(double[] Detreministic_array) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

public doubleEnt(double t)
{
  v = t;

}

public boolean greaterThan(entity ent){
    return (this.v > ((doubleEnt)  ent).getv());
}

public void setv(double t){v = t;}

public double getv(){return v;}

public void print(){System.out.print( v);}

public boolean equal(entity ent){
//System.out.println(v + " " + ((doubleEnt)ent).getv());
    return (Math.abs(this.v -((doubleEnt)  ent).getv())< 0.0000001);
}

public boolean equals(Object ent){ //needed for Relation
    if (!(ent instanceof doubleEnt)) return false;
    return  equal((entity)ent);
}

public entity  copy(){
     doubleEnt  ip = new doubleEnt(getv());
     return (entity)ip;
}

public String getName(){
     return Double.toString(v);
}

public String toString(){
  //s.s("--------"+v+"------------Inside to stying doubleent");
  String s = (String)Double.toString(v);
  return s;
}

public static double toObject(String str){
   return (double)Double.parseDouble(str);
}

public static double toObject(entity en){
  //s.s("-----String---"+en.toString()+"------------*********************Inside to stying doubleent");
  return toObject(en.toString());
}


}
