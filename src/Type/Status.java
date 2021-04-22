package Type;

public class Status {
  public static final int SendSize = 0;

  public int PlayID;
  public int HP;
  public int MAX_HP;
  public int MP;
  public int MAX_MP;
  public int STR;
  public int MG;
  public int AGI;
  public int LUC;
  public int Level;
  public int Skill_Point;
  public int State;

  public Status(){

  }
  public Status(int playID, int HP, int MAX_HP, int MP, int MAX_MP, int STR, int MG, int AGI, int LUC, int level, int skill_point, int state) {
  	this.PlayID = playID;
  	this.HP = HP;
  	this.MAX_HP = MAX_HP;
  	this.MP = MP;
  	this.MAX_MP = MAX_MP;
  	this.STR = STR;
  	this.MG = MG;
  	this.AGI = AGI;
  	this.LUC = LUC;
  	this.Level = level;
  	this.Skill_Point = skill_point;
  	this.State = state;
  }
}
