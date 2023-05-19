import controller.FlexiController;
import controller.FlexiControllerImpl;
import controller.SwingController;
import controller.SwingControllerImpl;
import model.FlexiUser;
import model.FlexiUserImpl;
import view.FlexiView;
import view.FlexiViewImpl;
import view.IView;
import view.JFrameView;

/**
 * This represents the main method of our project.
 */
public class Main {
  /**
   * This is the main driver function of the project.
   *
   * @param args The arguments passed
   */
  public static void main(String[] args) {
    if (args[0].equals("text")) {
      FlexiUser u = new FlexiUserImpl();
      FlexiView v = new FlexiViewImpl(System.out);
      FlexiController fc = new FlexiControllerImpl(u, v, System.in);
      fc.showPortfolioTypeSelection();
    } else if (args[0].equals("gui")) {
      FlexiUser user = new FlexiUserImpl();
      SwingController c = new SwingControllerImpl(user);
      IView view = new JFrameView("Portfolio Management System");
      c.setView(view);
    } else {
      System.out.println("Invalid arguments.");
    }
  }
}
