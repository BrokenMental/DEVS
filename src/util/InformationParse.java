/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

/**
 *
 * @author Administrator
 */
public class InformationParse {

    public void InformationParser(){
        double [] d={0,0,0,0,0,0,0};

    }

    public double[] parse(String info){
        double [] d={0,0,0,0,0,0,0};
        String temp1="", temp2="";
        int start=info.indexOf(":");
        temp1=info.substring(0,start);
        d[0] = Double.parseDouble(temp1);
        int end=info.indexOf(":", start+1);
        int i=1;
        while(i<7 && end>0 ){
        temp2=info.substring(start+1,end);
        //System.out.println("temp2 =  " + temp2);
        d[i] = Double.parseDouble(temp2);
        //System.out.println("array["+i+"] =" + d[i]);
        i++;
        start=end;
        end=info.indexOf(":", start+1);
        }

        return d;
    }

}
