package Type;

import Tools.ByteArrayTransform;

public class ActionType {
  public static final int ActionTypeSize = 36;
  public int ActionID;
  public int MoverMapID;
  public int MoverID;
  public int TargetMapID;
  public int TargetID;
  public double Information1;
  public double Information2;

  public ActionType(int ActionID, int MMID, int PID, int TMID, int TargetID, double I1, double I2) {
    this.ActionID = ActionID;
    this.MoverMapID = MMID;
    this.MoverID = PID;
    this.TargetMapID = TMID;
    this.TargetID = TargetID;
    this.Information1 = I1;
    this.Information2 = I2;
  }
  public ActionType(){

  }
  //byte array to ActionType
  public  ActionType(byte[] Data){
    ActionID = ByteArrayTransform.ToInt(Data, 0);
    MoverMapID = ByteArrayTransform.ToInt(Data, 4);
    MoverID = ByteArrayTransform.ToInt(Data, 8);
    TargetMapID = ByteArrayTransform.ToInt(Data, 12);
    TargetID = ByteArrayTransform.ToInt(Data, 16);
    Information1 = ByteArrayTransform.ToDouble(Data, 20);
    Information2 = ByteArrayTransform.ToDouble(Data, 28);
  }
}
