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
      shopEquipment.add(new ShopEquipment(Shop1, i, e, 1 , skill1, 0));
      i++;
    }
  }
}
