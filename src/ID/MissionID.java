package ID;

import Type.MissionType;
import Type.MonsterType;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class MissionID {
  public static MissionType[] missions = new MissionType[30];
  public static int length = 0;

  public static void setMissions(){
    try{
      InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream("src\\ID\\Mission.csv"), Charset.forName("big5"));
      BufferedReader reader = new BufferedReader(inputStreamReader);

      String line;
      int counter = 1;
      reader.readLine();
      while ((line = reader.readLine())!= null){
        missions[counter] = new MissionType(line);
        counter++;
      }
      length = counter;
      reader.close();
    }catch (Exception e){
      e.printStackTrace();
    }


  }
}
