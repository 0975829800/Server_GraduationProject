package ID;

import ServerMainBody.Server;
import Type.EquipmentType;
import Type.SkillType;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class EquipmentID {
  public static Queue<EquipmentType> EquipmentInformation = new LinkedList<>();

  public static void setEquipmentInformation() {
    try{
      InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream("src\\ID\\Equipment.csv"));
      BufferedReader reader = new BufferedReader(inputStreamReader);

      String line = null;
      reader.readLine();
      while ((line = reader.readLine())!= null){
        EquipmentInformation.add(new EquipmentType(line));
      }
      reader.close();
    }catch (Exception e){
      System.out.println(e);
    }

    if(Server.debug){
      for (EquipmentType s : EquipmentInformation){
        System.out.printf("%d %.1f %.1f %.1f %.1f %.1f %.1f %.1f %s %d",
                s.EID,s.STR,s.MG,s.AGI,s.LUC,s.HP,s.MP,s.GrowthRate,s.part,s.price);
      }
    }
  }
}
