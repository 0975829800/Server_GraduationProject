package Type;

public class ShopItem {
  public int ShopID;
  public int order;
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
}
