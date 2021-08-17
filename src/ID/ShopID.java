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
    shopItem.addAll(ItemID.shopItems);
  }

  public static void SetEquipmentShop(){ //set shop equipment
    int i = 0;
    for (EquipmentType e : EquipmentID.EquipmentInformation){
      shopEquipment.add(new ShopEquipment(Shop1, i, e, 0 , 0, 0));
      i++;
    }
  }
}
