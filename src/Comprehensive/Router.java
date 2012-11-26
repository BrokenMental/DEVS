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


public class Router extends ViewableAtomic {
    double clock;
    String packet1;
    String packet2;
    String packet3;
    String packet4;
    double Datasize_R1=0;
    double Datasize_R2=0;
    double Datasize_R3=0;
    double Datasize_R4=0;
    double [] ST;
    double Xs;
    double Ys;
    double Xd;
    double Yd;
    double Packetsize;
    double initial_time=0;
    double  last_time[]={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    double  job_delay[]={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    double  job_counter[]={0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    double  total_delay[]={0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    double Total_delay;
    double packet_counter=0;



   public Router(String router){
       super("router");

      addInport("In");
      addOutport("Out1");
      addOutport("Out2");
      addOutport("Out3");
      addOutport("Out4");
     }

    public void initialize(){
       phase="passive";
       sigma=INFINITY;
       clock=0;
       super.initialize();
    }


    public void deltext (double e, message x)
    {
                      Continue(e);

         for(int l=0;l<x.getLength();l++){

         if( this.messageOnPort(x,"In",l))
         {entity value=x.getValOnPort("In",l);
                String infor=value.getName();
                InformationParse IPS=new InformationParse();
                double [] ST=IPS.parse(infor);
                
       
         double Xoffset=ST[2]-ST[0];
         double Yoffset=ST[3]-ST[1];
        Xs=ST[0];
        Ys=ST[1];
        Xd=ST[2];
        Yd=ST[3];
        Packetsize=ST[4];
        initial_time=ST[5];
        double address=ST[6];
         
         if (Xoffset>0)
         {  ST[0]=ST[0]+1;
         // Xd=ST[2];
         packet1 = ""+ST[0]+":"+ST[1]+":"+Xd+":"+Yd+":"+Packetsize+":"+initial_time+":"+address+":";
         Datasize_R1=Datasize_R1+Packetsize;
         holdIn("active_Rout1", 0);
         }

         if (Xoffset<0)
         {  ST[0]=ST[0]-1;
           // Xd=ST[2];
         packet2 = ""+ST[0]+":"+ST[1]+":"+Xd+":"+Yd+":"+Packetsize+":"+initial_time+":"+address+":";
         Datasize_R2=Datasize_R2+Packetsize;
          holdIn("active_Rout2", 0);
         }


         if ((Xoffset== 0) && Yoffset > 0)
         { ST[1]=ST[1]+1;
          // Yd=ST[3];
         packet3 = ""+ST[0]+":"+ST[1]+":"+Xd+":"+Yd+":"+Packetsize+":"+initial_time+":"+address+":";
         Datasize_R3=Datasize_R3+Packetsize;
          holdIn("active_Rout3", 0);

         }


         if ((Xoffset== 0) && Yoffset < 0)
         { ST[1]=ST[1]-1;
           //Yd=ST[3];
         packet4 = ""+ST[0]+":"+ST[1]+":"+Xd+":"+Yd+":"+Packetsize+":"+initial_time+":"+address+":";
         Datasize_R4=Datasize_R4+Packetsize;
          holdIn("active_Rout4", 0);
         }


         if ((Xoffset== 0) && Yoffset== 0) 
         {
             if(address==1)
             {
               Calendar cal = Calendar.getInstance();
             double current_time= cal.getTimeInMillis();
             Total_delay=Total_delay+(current_time-initial_time);
             packet_counter=packet_counter+1;
             System.out.print("("+Xd+","+Yd+")"+"\n");
             System.out.print("packet_counter="+" "+packet_counter+"\n");
             //System.out.print("total_delay in ms from   "+address+"    "+job_delay[0]+"   initialtime  "+initial_time+"  lasttime"+last_time[0]+"\n");
             System.out.print("\n"+"\n");
         }

             else    if(address==2)
             {
                
              Calendar cal = Calendar.getInstance();
             double current_time= cal.getTimeInMillis();
             Total_delay=Total_delay+(current_time-initial_time);
             packet_counter=packet_counter+1;
             System.out.print("("+Xd+","+Yd+")"+"\n");
             System.out.print("packet_counter="+" "+packet_counter+"\n");
             //System.out.print("total_delay in ms from   "+address+"    "+job_delay[0]+"   initialtime  "+initial_time+"  lasttime"+last_time[0]+"\n");
             System.out.print("\n"+"\n");
         }

           else  if(address==3)
             {
              Calendar cal = Calendar.getInstance();
             double current_time= cal.getTimeInMillis();
             Total_delay=Total_delay+(current_time-initial_time);
             packet_counter=packet_counter+1;
             System.out.print("("+Xd+","+Yd+")"+"\n");
             System.out.print("packet_counter="+" "+packet_counter+"\n");
             //System.out.print("total_delay in ms from   "+address+"    "+job_delay[0]+"   initialtime  "+initial_time+"  lasttime"+last_time[0]+"\n");
             System.out.print("\n"+"\n");
         }

             else  if(address==4)
             {
              Calendar cal = Calendar.getInstance();
             double current_time= cal.getTimeInMillis();
             Total_delay=Total_delay+(current_time-initial_time);
             packet_counter=packet_counter+1;
             System.out.print("("+Xd+","+Yd+")"+"\n");
             System.out.print("packet_counter="+" "+packet_counter+"\n");
             //System.out.print("total_delay in ms from   "+address+"    "+job_delay[0]+"   initialtime  "+initial_time+"  lasttime"+last_time[0]+"\n");
             System.out.print("\n"+"\n");
         }

             else  if(address==5)
             {
              Calendar cal = Calendar.getInstance();
             double current_time= cal.getTimeInMillis();
             Total_delay=Total_delay+(current_time-initial_time);
             packet_counter=packet_counter+1;
             System.out.print("("+Xd+","+Yd+")"+"\n");
             System.out.print("packet_counter="+" "+packet_counter+"\n");
             //System.out.print("total_delay in ms from   "+address+"    "+job_delay[0]+"   initialtime  "+initial_time+"  lasttime"+last_time[0]+"\n");
             System.out.print("\n"+"\n");
         }


             else  if(address==6)
             {
              Calendar cal = Calendar.getInstance();
             double current_time= cal.getTimeInMillis();
             Total_delay=Total_delay+(current_time-initial_time);
             packet_counter=packet_counter+1;
             System.out.print("("+Xd+","+Yd+")"+"\n");
             System.out.print("packet_counter="+" "+packet_counter+"\n");
             //System.out.print("total_delay in ms from   "+address+"    "+job_delay[0]+"   initialtime  "+initial_time+"  lasttime"+last_time[0]+"\n");
             System.out.print("\n"+"\n");
         }

             else  if(address==7)
             {
              Calendar cal = Calendar.getInstance();
             double current_time= cal.getTimeInMillis();
             Total_delay=Total_delay+(current_time-initial_time);
             packet_counter=packet_counter+1;
             System.out.print("("+Xd+","+Yd+")"+"\n");
             System.out.print("packet_counter="+" "+packet_counter+"\n");
             //System.out.print("total_delay in ms from   "+address+"    "+job_delay[0]+"   initialtime  "+initial_time+"  lasttime"+last_time[0]+"\n");
             System.out.print("\n"+"\n");
         }

             else  if(address==8)
             {
              Calendar cal = Calendar.getInstance();
             double current_time= cal.getTimeInMillis();
             Total_delay=Total_delay+(current_time-initial_time);
             packet_counter=packet_counter+1;
             System.out.print("("+Xd+","+Yd+")"+"\n");
             System.out.print("packet_counter="+" "+packet_counter+"\n");
             //System.out.print("total_delay in ms from   "+address+"    "+job_delay[0]+"   initialtime  "+initial_time+"  lasttime"+last_time[0]+"\n");
             System.out.print("\n"+"\n");
         }

        else  if(address==9)
             {
              Calendar cal = Calendar.getInstance();
             double current_time= cal.getTimeInMillis();
             Total_delay=Total_delay+(current_time-initial_time);
             packet_counter=packet_counter+1;
             System.out.print("("+Xd+","+Yd+")"+"\n");
             System.out.print("packet_counter="+" "+packet_counter+"\n");
             //System.out.print("total_delay in ms from   "+address+"    "+job_delay[0]+"   initialtime  "+initial_time+"  lasttime"+last_time[0]+"\n");
             System.out.print("\n"+"\n");
         }
    
         }}}}


 public void deltint(){
           //clock=clock+sigma;
           //System.out.println("clock= " + clock);
    }
    
    public message out()
    {
        message m =new message ();

if(phaseIs("active_Rout1")){
            m.add(makeContent("Out1",new entity(packet1)));
            System.out.print("("+Xs+","+Ys+")"+"  "+"("+Xd+","+Yd+")"+"\n");
             System.out.print("Datasize_R1= "+Datasize_R1+"\n");
              System.out.print("Datasize_R2= "+Datasize_R2+"\n");
        System.out.print("Datasize_R3= "+Datasize_R3+"\n");
        System.out.print("Datasize_R4= "+Datasize_R4+"\n");
        //System.out.print("Latency= "+job_delay+"\n");

                System.out.print("\n"+"\n");
            passivateIn("passive");
        }



        if(phaseIs("active_Rout2")){
            m.add(makeContent("Out2",new entity(packet2)));
System.out.print("("+Xs+","+Ys+")"+"  "+"("+Xd+","+Yd+")"+"\n");
            System.out.print("Datasize_R1= "+Datasize_R1+"\n");
         System.out.print("Datasize_R2= "+Datasize_R2+"\n");
        System.out.print("Datasize_R3= "+Datasize_R3+"\n");
        System.out.print("Datasize_R4= "+Datasize_R4+"\n");
      //  System.out.print("Latency= "+job_delay+"\n");

                System.out.print("\n"+"\n");
            passivateIn("passive");
        }



        if(phaseIs("active_Rout3")){
            m.add(makeContent("Out3",new entity(packet3)));
System.out.print("("+Xs+","+Ys+")"+"  "+"("+Xd+","+Yd+")"+"\n");
            System.out.print("Datasize_R1= "+Datasize_R1+"\n");
             System.out.print("Datasize_R2= "+Datasize_R2+"\n");
        System.out.print("Datasize_R3= "+Datasize_R3+"\n");
        System.out.print("Datasize_R4= "+Datasize_R4+"\n");
      //  System.out.print("Latency= "+job_delay+"\n");

                System.out.print("\n"+"\n");
            passivateIn("passive");
        }



        if(phaseIs("active_Rout4")){
            m.add(makeContent("Out4",new entity(packet4)));
System.out.print("("+Xs+","+Ys+")"+"  "+"("+Xd+","+Yd+")"+"\n");
            System.out.print("Datasize_R1= "+Datasize_R1+"\n");
        System.out.print("Datasize_R2= "+Datasize_R2+"\n");
        System.out.print("Datasize_R3= "+Datasize_R3+"\n");
        System.out.print("Datasize_R4= "+Datasize_R4+"\n");
      //  System.out.print("Latency= "+job_delay+"\n");

                System.out.print("\n"+"\n");
            passivateIn("passive");
        }
                   return m;

     }
}