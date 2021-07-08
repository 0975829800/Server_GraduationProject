package ID;

import Type.EquipmentType;
import Type.ShopEquipment;
import Type.ShopItem;

import java.util.ArrayList;

public class ShopID {
  public static final int Shop1 = 1;
  public static final int Shop2 = 2;
  public static ArrayList<ShopItem> shopItem = new ArrayList<>();
  public static ArrayList<ShopEquipment> shopEquipment = new ArrayList<>();


  public static void SetItemShop(){ //set shop items
    shopItem.add(new ShopItem(Shop1,0,ItemID.HP_Potion,1,100));
    shopItem.add(new ShopItem(Shop1,1,ItemID.MP_Potion,1,100));
    shopItem.add(new ShopItem(Shop1,2,ItemID.HP_Potion,2,300));
    shopItem.add(new ShopItem(Shop1,3,ItemID.MP_Potion,2,300));
    shopItem.add(new ShopItem(Shop1,4,ItemID.GetCoin,2,-1000));
  }

  public static void SetEquipmentShop(){ //set shop equipment
    int i = 0;
    for (EquipmentType e : EquipmentID.EquipmentInformation){
      shopEquipment.add(new ShopEquipment(Shop1, i, e, 1 , 0, 0));
    }
  }
}
