package Models.java;

import genDevs.simulation.distributed.*;
import util.*;


public class StartRTDrumClient_1
{
   static public  void main(String[] args)
    {
        new TunableCoupledSimulatorClient(
            new Drum(),
            // "128.196.29.162",  //wireless IP address
         //  "192.168.1.6",
             "localhost",
            Constants.serverPortNumber);
    }
}




