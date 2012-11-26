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

public interface SimViewControllerInterface {
  public abstract void stepSimView();
  public abstract void runSimView(boolean isSelected);
  public abstract void informSimView();
}