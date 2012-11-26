package Models.java;
import genDevs.simulation.distributed.*;
import java.net.*;

public class StartRTDrumEarServer_1
{
    	static public void main(String[] args) {
  try{
    System.out.println("For use by clients, this host is " +InetAddress.getLocalHost().getHostAddress());
      }catch(Exception c){}
     new RTCentralCoordServer(new DrumEar(), 10);
    }
}

