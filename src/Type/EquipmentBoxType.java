package Type;

import Action.Equip;
import ID.EquipmentID;
import ID.SkillID;
import Tools.ToCSharpTool;

public class EquipmentBoxType {
  public static final int SendSize = 32;

  public int PlayerID;
  public int EquipmentBox_ID;  //每個Player的裝備箱排列順序(以這個來限制道具多寡
  public int Equipment_ID;     //道具編號
  public int Rarity;
  public int Part;
  public int Level;
  public int Skill_ID_1;
  public int Skill_ID_2;
  public boolean Equipping = false;

  public EquipmentBoxType(){

  }
  public EquipmentBoxType(int PlayerID,int EquipmentBoxID, EquipmentType e){
    this.PlayerID = PlayerID;
    this.EquipmentBox_ID = EquipmentBoxID;
    Equipment_ID = e.EID;
    Rarity = 1;
    Level = 1;
    int skill1 = 0;
    switch (e.EID%8){
      case 0:          //盾
        skill1 = 4;
        break;
      case 1: case 2:   //頭 身體
        skill1 = 12;
        break;
      case 3:           //手
        skill1 = 13;
        break;
      case 4:           //腳
        skill1 = 14;
        break;
      case 5:         //劍
        skill1 = 1;
        break;
      case 6:         //杖
        skill1 = 2;
        break;
      case 7:         //弓
        skill1 = 3;
        break;
    }
    Skill_ID_1 = skill1;
    Equipping = false;
  }

  public EquipmentBoxType(int PlayerID,int EquipmentBoxID, int Equipment_ID, int Rarity, int Part, int Level, int Equipping, int Skill_ID_1, int Skill_ID_2){
    this.PlayerID = PlayerID;
    this.EquipmentBox_ID = EquipmentBoxID;
    this.Equipment_ID = Equipment_ID;
    this.Rarity = Rarity;
    this.Part = Part;
    this.Level = Level;
    this.Skill_ID_1 = Skill_ID_1;
    this.Skill_ID_2 = Skill_ID_2;
    if(Equipping == 0)
      this.Equipping = false;
    else
      this.Equipping = true;
  }
  public EquipmentBoxType(int PID,int EquipmentBox_ID, int Equipment_ID, int Rarity, int Part, int Level, int Skill1, int Skill2){
    this.PlayerID = PID;
    this.EquipmentBox_ID = EquipmentBox_ID;
    this.Equipment_ID = Equipment_ID;
    this.Level = Level;
    this.Part = Part;
    this.Skill_ID_1 = Skill1;
    this.Skill_ID_2 = Skill2;
    this.Rarity = Rarity;
  }

  public int[] getEquipStatus(){
    int STR = 0, MG = 0, AGI = 0, LUC = 0;
    double power = 0;
    if(Rarity == 1){
      power = 1;
    }else if(Rarity == 2){
      power = 1.5;
    }else if(Rarity == 3){
      power = 2;
    }

    for(EquipmentType e: EquipmentID.EquipmentInformation){
      if(Equipment_ID == e.EID){
        STR = (int)(e.STR * power);
        MG = (int)(e.MG * power);
        AGI = (int)(e.AGI * power);
        LUC = (int)(e.LUC * power);
      }
    }

    return new int[]{STR, MG, AGI, LUC};
  }

  public byte[] getByte(){
    byte[] temp;
    byte[] ans  = new byte[SendSize];
    temp = ToCSharpTool.ToCSharp(EquipmentBox_ID);
    System.arraycopy(temp,0,ans,0,4);
    temp = ToCSharpTool.ToCSharp(Equipment_ID);
    System.arraycopy(temp,0,ans,4,4);
    temp = ToCSharpTool.ToCSharp(Rarity);
    System.arraycopy(temp,0,ans,8,4);
    temp = ToCSharpTool.ToCSharp(Part);
    System.arraycopy(temp,0,ans,12,4);
    temp = ToCSharpTool.ToCSharp(Level);
    System.arraycopy(temp,0,ans,16,4);
    temp = ToCSharpTool.ToCSharp(Skill_ID_1);
    System.arraycopy(temp,0,ans,20,4);
    temp = ToCSharpTool.ToCSharp(Skill_ID_2);
    System.arraycopy(temp,0,ans,24,4);
    if (Equipping)  temp = ToCSharpTool.ToCSharp(1);       //boolean以int 1 or -1來傳送
    else            temp = ToCSharpTool.ToCSharp(0);
    System.arraycopy(temp,0,ans,28,4);
    return ans;
  }


}
