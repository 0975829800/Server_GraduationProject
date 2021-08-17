package Type;

import ID.ItemID;
import Tools.ToCSharpTool;

public class ShopItem {
  public static final int SendSize = 20;
  public static int counter = 0;
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

  public ShopItem(String data){
    String[] tmp = data.split(",");

    order = counter;
    counter++;
    ShopID = 0;
    ItemID = Integer.parseInt(tmp[0]);
    Rarity = Integer.parseInt(tmp[2]);
    Price = Integer.parseInt(tmp[6]);
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
