package ID;


import ServerMainBody.Server;
import Type.MonsterType;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.Queue;

public class MonsterID {
  public static Queue<MonsterType> MonsterInformation = new LinkedList<>();
  public static int length;

  public static void GetMonsterInformation(){
    try{
      InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream("src\\ID\\Monster.csv"), Charset.forName("big5"));
      BufferedReader reader = new BufferedReader(inputStreamReader);

      String line = null;
      reader.readLine();
      while ((line = reader.readLine())!= null){
        MonsterInformation.add(new MonsterType(line));
      }
      reader.close();
    }catch (Exception e){
      System.out.println(e);
    }
    length = MonsterInformation.size();

    if(Server.debug){
      for(MonsterType m : MonsterInformation){
        System.out.println(String.format("%d %s %.1f %.1f ",m.MonsterID,m.MonsterName,m.MAX_HP,m.MAX_MP));
      }
    }
  }
}
