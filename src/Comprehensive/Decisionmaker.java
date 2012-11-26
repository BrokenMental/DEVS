package Comprehensive;

import genDevs.simulation.*;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;
import java.awt.*;
import genDevs.modeling.message.*;
import java.util.*;
import java.lang.StringBuffer.*;
import util.EnsembleSetUtil;
import util.InformationParse;

public class Decisionmaker extends ViewableAtomic {

    int i=1;
    message m;
    int TIME_FACTOR = 1000;
    double [] dm;
    double [] I;
    double KK;
   
   // variabels for Deterministic atomic model
    double D=1;                               //variable to know which of atomic model will be on
    double packet_size_D=30;                    // Size of packet in bytes
    double Data_rate_D=2;                      // the rate of packet trnsmission in Mbyte per second
    double processing_time_D=200;            //Total execution time for Deterministic model in second
    double factor_D=1000;                       // factor to make all the models consistent
    //double P_D=8;
    //double [] Detreministic_array={D,packet_size_D,Data_rate_D,processing_time_D,factor_D}; //array to transmit to feed deterministic model

    String DeterministicInfo = ""+ D + ":" + packet_size_D +":" + Data_rate_D + ":" + processing_time_D + ":" + factor_D +":";
  // String DeterministicStop=""+D+":"+P_D+":";
    // String DeterministicInfo = ""+D+":"+processing_time_D+":"+interval_time_D+":"+TIME_FACTOR+":";
   ///////////////////////////////////////////////////////////////////////////////////
 
     
    public Decisionmaker(){
        super("Decisionmaker");

            addInport("Input");
            //addTestInput("Input", new entity ("Input"));

            addInport("InjectDet");        // Input port to ON\OFF deterministc model from user
            addTestInput("InjectDet", new entity("InjectDet"));
       

           addOutport("InformationPort");            // Output port to transmit messages to atomic models

                   
           addOutport("Stop");            // Output port to transmit messages to atomic models

        
    }

    public void initialize(){
      passivateIn("passive");
    }

    public void deltint(){

        if(phaseIs("InjectDet"))
            passivateIn("passive");
            
    }


        public void deltext (double e, message x)
    {
            Continue(e);

            
           for(int l=0; l<x.getLength();l++){

               if (this.messageOnPort(x,"input",l)){KK=KK+1;System.out.print(KK+"\n");}

               if(this.messageOnPort(x, "InjectDet", l) && phaseIs("passive"))
                    holdIn("InjectDet",0);

               
               if(this.messageOnPort(x, "Input", l) && phaseIs("Input"))
                    holdIn("Input",0);
           }


            
      for(int l=0; l<x.getLength();l++){
               if(messageOnPort(x,"ComingFeedback",l)){
        entity val=x.getValOnPort("ComingFeedback", l);
        String info=val.getName();
        InformationParse IP = new InformationParse();
        dm = IP.parse(info);

            }
     }
                         
     }
       
        
    public message out()
    {
        int ST=0;
        message m = new message();
       // entity v = new entity();

        if(phaseIs("InjectDet")){

            m.add(makeContent("InformationPort",new entity(DeterministicInfo)));
        }  
     
        return m;
    }
}

