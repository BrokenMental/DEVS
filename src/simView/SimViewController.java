package simView;

import javax.swing.*;
/**
 * <p>Title: </p>
 * <p>Description: Final Devs version</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Saurabh Mittal
 * @version 1.0
 * August 17, 2004
 */

public class SimViewController extends SimView
    implements SimViewControllerInterface{

  private static ViewableDigraph model;

  public SimViewController() {
    super();
    System.out.println("SimView Controller started");
  }

  /**
   * Starts this application.
   */
  static public void main(String[] args) {
    new SimViewController();
  }

  static public void setModel(ViewableDigraph model_){
    model = model_;
  }

  public ViewableDigraph getModel(){
    return super.model;
  }



  public void stepSimView(){
    if(coordinator==null){
      return;
    }

    modelView.stepToBeTaken();

    setStatusLabelToStepping();
    coordinator.simulate(1);

    clock.set(coordinator.getTimeOfLastEvent());
  }


  public void runSimView(boolean isSelected){
    if(coordinator== null){
      return;
    }

    if(isSelected){
      modelView.runToOccur();

      setStatusLabelToRunning();
      coordinator.simulate(Integer.MAX_VALUE);
    }
    else{
      coordinator.simulate(0);
      setStatusLabelToReady();
    }
  }

  public void informSimView(){
    coordinator.interrupt();
  }

}