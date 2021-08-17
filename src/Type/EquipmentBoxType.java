package Type;

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


  public void Equip(int EquipmentBox_ID){
    Equipping = true;
  }
  public void disEquip(int EquipmentBox_ID){
    Equipping = false;
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
