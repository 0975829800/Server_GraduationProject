package Type;


public class MissionType {
  public int AcceptNPC;
  public int CompleteNPC;
  public int DemandType;
  public int DemandBelong;
  public int CompleteAmount;
  public int Coin;
  public int EXP;
  public int RewardType;
  public int RewardBelong;
  public int RewardAmount;

  public MissionType(String data){
    String[] tmp = data.split(",");
    AcceptNPC = Integer.parseInt(tmp[1]);
    CompleteNPC = Integer.parseInt(tmp[2]);
    DemandType = Integer.parseInt(tmp[3]);
    DemandBelong = Integer.parseInt(tmp[4]);
    CompleteAmount = Integer.parseInt(tmp[5]);
    Coin = Integer.parseInt(tmp[6]);
    EXP = Integer.parseInt(tmp[7]);
    RewardType = Integer.parseInt(tmp[8]);
    RewardBelong = Integer.parseInt(tmp[9]);
    RewardAmount = Integer.parseInt(tmp[10]);
  }

}
