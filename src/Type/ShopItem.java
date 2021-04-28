package Type;

import Tools.ToCSharpTool;

public class ShopItem {
  public static final int SendSize = 20;

  public int order;
  public int ShopID;
  public int ItemID;
  public int Rarity;
  public int Price;

  public ShopItem(int ShopID,int order, int ItemID, int Rarity, int Price){
    this.ShopID = ShopID;
    this.order = order;
    this.ItemID = ItemID;
    this.Rarity = Rarity;
    this.Price = Price;
  }

  public byte[] getByte(){
    byte[] temp;
    byte[] ans = new byte[SendSize];
    temp = ToCSharpTool.ToCSharp(order);
    System.arraycopy(temp,0,ans,0,4);
    temp = ToCSharpTool.ToCSharp(ShopID);
    System.arraycopy(temp,0,ans,4,4);
    temp = ToCSharpTool.ToCSharp(ItemID);
    System.arraycopy(temp,0,ans,8,4);
    temp = ToCSharpTool.ToCSharp(Rarity);
    System.arraycopy(temp,0,ans,12,4);
    temp = ToCSharpTool.ToCSharp(Price);
    System.arraycopy(temp,0,ans,16,4);
    return ans;
  }
}
