package Type;

import Tools.ToCSharpTool;

public class ItemType{
  public static final int SendSize = 20;

  public int PlayerID;
  public int ItemBox_ID;
  public int Item_ID;
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
    temp = ToCSharpTool.ToCSharp(PlayerID);
    System.arraycopy(temp,0,ans,0,4);
    temp = ToCSharpTool.ToCSharp(ItemBox_ID);
    System.arraycopy(temp,0,ans,4,4);
    temp = ToCSharpTool.ToCSharp(Item_ID);
    System.arraycopy(temp,0,ans,8,4);
    temp = ToCSharpTool.ToCSharp(Rarity);
    System.arraycopy(temp,0,ans,12,4);
    temp = ToCSharpTool.ToCSharp(Amount);
    System.arraycopy(temp,0,ans,16,4);
    return ans;
  }
}