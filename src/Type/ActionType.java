package Type;

import java.net.ServerSocket;

public class ActionType {
  public static final int ActionTypeSize = 28;
  public int ActionID;
  public int PlayerID;
  public int TargetID;
  public double Information1;
  public double Information2;

  public ActionType(int ActionID, int PID, int TargetID, double I1, double I2) {
    this.ActionID = ActionID;
    this.PlayerID = PID;
    this.TargetID = TargetID;
    this.Information1 = I1;
    this.Information2 = I2;
  }
}
