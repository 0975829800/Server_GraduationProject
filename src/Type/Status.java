package Type;

import Tools.ToCSharpTool;

public class Status {
  public static final int SendSize = 52;

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
  public int coin;
  public int Skill_Point;
  public int State;
  public int coin;

  public Status(){

  }
  public Status(int playID, int HP, int MAX_HP, int MP, int MAX_MP, int STR, int MG, int AGI, int LUC, int level,int coin, int skill_point, int state) {
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
  	this.coin = coin;
  	this.Skill_Point = skill_point;
  	this.State = state;
  }
  public byte[] getByte(){
    byte[] temp;
    byte[] ans  = new byte[SendSize];
    temp = ToCSharpTool.ToCSharp(PlayID);
    System.arraycopy(temp,0,ans,0,4);
    temp = ToCSharpTool.ToCSharp(HP);
    System.arraycopy(temp,0,ans,4,4);
    temp = ToCSharpTool.ToCSharp(MAX_HP);
    System.arraycopy(temp,0,ans,8,4);
    temp = ToCSharpTool.ToCSharp(MP);
    System.arraycopy(temp,0,ans,12,4);
    temp = ToCSharpTool.ToCSharp(MAX_MP);
    System.arraycopy(temp,0,ans,16,4);
    temp = ToCSharpTool.ToCSharp(STR);
    System.arraycopy(temp,0,ans,20,4);
    temp = ToCSharpTool.ToCSharp(MG);
    System.arraycopy(temp,0,ans,24,4);
    temp = ToCSharpTool.ToCSharp(AGI);
    System.arraycopy(temp,0,ans,28,4);
    temp = ToCSharpTool.ToCSharp(LUC);
    System.arraycopy(temp,0,ans,32,4);
    temp = ToCSharpTool.ToCSharp(Level);
    System.arraycopy(temp,0,ans,36,4);
    temp = ToCSharpTool.ToCSharp(coin);
    System.arraycopy(temp,0,ans,40,4);
    temp = ToCSharpTool.ToCSharp(Skill_Point);
    System.arraycopy(temp,0,ans,44,4);
    temp = ToCSharpTool.ToCSharp(State);
    System.arraycopy(temp,0,ans,48,4);
    return ans;
  }
}
