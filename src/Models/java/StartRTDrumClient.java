package Models.java;

import genDevs.simulation.distributed.*;
import util.*;


public class StartRTDrumClient
{
   static public  void main(String[] args)
    {
        new RTCoupledSimulatorClient(
            new Drum(),
            // "128.196.29.162",  //wireless IP address
         //  "192.168.1.6",
             "localhost",
            Constants.serverPortNumber);
    }
}




