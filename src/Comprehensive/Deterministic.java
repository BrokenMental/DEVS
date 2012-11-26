package Comprehensive;
import genDevs.modeling.*;
import genDevs.simulation.*;
import GenCol.*;
import java.awt.*;
import simView.*;
import util.InformationParse;
import genDevs.modeling.message.*;
import java.util.*;
import java.lang.StringBuffer.*;
import util.EnsembleSetUtil;
import java.util.Calendar;

public class Deterministic extends ViewableAtomic {
    double clock;
    double latency;
    String Packet;
    double interval_time;
    double [] D;           // array to receive the variabels from decisionmaker
    double k;
    String FeedbackStop= ""+1+":";
    double [] ST;
    int jump=1;
    double Packetsize;
    double e;

   public Deterministic(String n){

       super(n);
        addInport("Stop");
        addTestInput("Stop",new entity("Stop"));
        addInport("InfoPort");
        addOutport("PacketsPort");
        addOutport("Feedback");
     }

    public void initialize(){
       phase="passive";
       sigma=INFINITY;
       clock=0;
       latency=0;
       super.initialize();
    }

    public void deltext (double e, message x)
    {
        
        Continue(e);

         for(int l=0;l<x.getLength();l++){
/*
            if(this.messageOnPort(x,"Stop",l) && phaseIs("passive"))
              holdIn("Stop", 0);


         else if( this.messageOnPort(x,"Stop",l)){
                entity value=x.getValOnPort("Stop",l);
                String infor=value.getName();
                InformationParse IPS=new InformationParse();
                double [] ST=IPS.parse(infor);
                if(ST[0]==6){holdIn("Stop", 0);
                }}
*/

          if(messageOnPort(x,"InfoPort",l)){
                entity val=x.getValOnPort("InfoPort", l);
                String info=val.getName();
                //System.out.println(info);
                InformationParse IP = new InformationParse();
                double []copy = IP.parse(info);
                 Packetsize=copy[1];
                if(copy[0]==1 ){
                 D = IP.parse(info);
                if(D[0]==1){     // check if the message for deterministic model or not
                interval_time=(D[1]*D[4])/(D[2]*(1024*1024));
                holdIn("active", interval_time);
                }
                }
                }
         }
        }


    public void deltint(){

        clock=clock+sigma;
      // System.out.println(">>>>>>>>>>>>>>>>> = " + i);
        if(phaseIs("Stop")){passivateIn("passive");}

        if(clock <= D[3] && phaseIs("active"))
            holdIn("InjectTransit", 0);
        else if(clock <= D[3] && phaseIs("InjectTransit"))
            holdIn("active", interval_time);
        else passivate();

    }

    public message out()
    {
        message m =new message ();


     Calendar cal = Calendar.getInstance();
latency=cal.getTimeInMillis();



        String s=getName();
               
        if(s.contains("Node1")){
        double x2=(int)(Math.random()*3);
        double y2=(int)(Math.random()*3);
         Packet= ""+0.0+":"+0.0+":"+x2+":"+y2+":"+Packetsize+":"+latency+":"+1+":";
        }

       if(s.contains("Node2")){
        double x2=(int)(Math.random()*3);
        double y2=(int)(Math.random()*3);
         Packet= ""+1.0+":"+0.0+":"+x2+":"+y2+":"+Packetsize+":"+latency+":"+2+":";
        }

       if(s.contains("Node3")){
        double x2=(int)(Math.random()*3);
        double y2=(int)(Math.random()*3);
         Packet= ""+2.0+":"+0.0+":"+x2+":"+y2+":"+Packetsize+":"+latency+":"+3+":";
        }

       if(s.contains("Node4")){
        double x2=(int)(Math.random()*3);
        double y2=(int)(Math.random()*3);
         Packet= ""+0.0+":"+1.0+":"+x2+":"+y2+":"+Packetsize+":"+latency+":"+4+":";
        }

       if(s.contains("Node5")){
        double x2=(int)(Math.random()*3);
        double y2=(int)(Math.random()*3);
         Packet= ""+1.0+":"+1.0+":"+x2+":"+y2+":"+Packetsize+":"+latency+":"+5+":";
        }

       if(s.contains("Node6")){
        double x2=(int)(Math.random()*3);
        double y2=(int)(Math.random()*3);
         Packet= ""+2.0+":"+1.0+":"+x2+":"+y2+":"+Packetsize+":"+latency+":"+6+":";
        }

       if(s.contains("Node7")){
        double x2=(int)(Math.random()*3);
        double y2=(int)(Math.random()*3);
         Packet= ""+0.0+":"+2.0+":"+x2+":"+y2+":"+Packetsize+":"+latency+":"+7+":";
        }

       if(s.contains("Node8")){
        double x2=(int)(Math.random()*3);
        double y2=(int)(Math.random()*3);
         Packet= ""+1.0+":"+2.0+":"+x2+":"+y2+":"+Packetsize+":"+latency+":"+8+":";
        }

       if(s.contains("Node9")){
        double x2=(int)(Math.random()*3);
        double y2=(int)(Math.random()*3);
         Packet= ""+2.0+":"+2.0+":"+x2+":"+y2+":"+Packetsize+":"+latency+":"+9+":";
        }
          
        if(phaseIs("active"))
        {
            m.add(makeContent("PacketsPort",new entity (Packet)));
        }

        if(phaseIs("Stop")){
          m.add(makeContent("Feedback",new entity(FeedbackStop)));
        }
            return m;
    }
}