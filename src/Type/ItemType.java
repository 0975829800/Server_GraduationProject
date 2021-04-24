package Type;

import Tools.ToCSharpTool;

public class ItemType{
  public static final int SendSize = 16;

  public int PlayerID;    //不用傳給玩家

  public int ItemBox_ID;  //每個Player的道具箱排列順序(以這個來限制道具多寡
  public int Item_ID;     //道具編號
  public int Rarity;
  public int Amount;

  public ItemType(int PlayerID, int ItemBox_ID, int Item_ID, int Rarity, int Amount){
    this.PlayerID = PlayerID;
    this.Item_ID = Item_ID;
    this.Rarity = Rarity;
    this.Amount = Amount;
    this.ItemBox_ID = ItemBox_ID;
  }
  public  ItemType() {

  }

  public byte[] getByte(){
    byte[] temp;
    byte[] ans  = new byte[SendSize];
    temp = ToCSharpTool.ToCSharp(ItemBox_ID);
    System.arraycopy(temp,0,ans,0,4);
    temp = ToCSharpTool.ToCSharp(Item_ID);
    System.arraycopy(temp,0,ans,4,4);
    temp = ToCSharpTool.ToCSharp(Rarity);
    System.arraycopy(temp,0,ans,8,4);
    temp = ToCSharpTool.ToCSharp(Amount);
    System.arraycopy(temp,0,ans,12,4);
    return ans;
  }
}