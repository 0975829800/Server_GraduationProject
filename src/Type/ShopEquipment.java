package Type;

import Tools.ToCSharpTool;

public class ShopEquipment {
  public static final int SendSize = 32;

  public int order;
  public int ShopID;
  public int EquipmentID;
  public int part;
  public int Rarity;
  public int Level = 1; //不傳
  public int Skill1;
  public int Skill2;
  public int Price;

  public ShopEquipment(int ShopID, int order, EquipmentType e, int Rarity, int Skill1, int Skill2){
    this.EquipmentID = e.EID;
    this.order = order;
    this.part = e.part;
    this.Rarity = Rarity;
    this.ShopID = ShopID;
    this.Skill1 = Skill1;
    this.Skill2 = Skill2;
    this.Price = e.price;
  }

  public byte[] getByte(){
    byte[] temp;
    byte[] ans = new byte[SendSize];
    temp = ToCSharpTool.ToCSharp(order);
    System.arraycopy(temp,0,ans,0,4);
    temp = ToCSharpTool.ToCSharp(ShopID);
    System.arraycopy(temp,0,ans,4,4);
    temp = ToCSharpTool.ToCSharp(EquipmentID);
    System.arraycopy(temp,0,ans,8,4);
    temp = ToCSharpTool.ToCSharp(part);
    System.arraycopy(temp,0,ans,12,4);
    temp = ToCSharpTool.ToCSharp(order);
    System.arraycopy(temp,0,ans,16,4);
    temp = ToCSharpTool.ToCSharp(Skill1);
    System.arraycopy(temp,0,ans,20,4);
    temp = ToCSharpTool.ToCSharp(Skill2);
    System.arraycopy(temp,0,ans,24,4);
    temp = ToCSharpTool.ToCSharp(Price);
    System.arraycopy(temp,0,ans,28,4);
    return ans;
  }
}
