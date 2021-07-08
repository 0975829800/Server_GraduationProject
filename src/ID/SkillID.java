package ID;

import ServerMainBody.Server;
import Type.MonsterType;
import Type.SkillType;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class SkillID {
  public static Queue<SkillType> SkillInformation = new LinkedList<>();

  public static void setSkillInformation(){

    try{
      InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream("src\\ID\\Skill.csv"));
      BufferedReader reader = new BufferedReader(inputStreamReader);

      String line = null;
      reader.readLine();
      while ((line = reader.readLine())!= null){
        SkillInformation.add(new SkillType(line));
      }
      reader.close();
    }catch (Exception e){
      System.out.println(e);
    }

    if(Server.debug){
      for (SkillType s : SkillInformation){
        System.out.printf("%d %.1f %s %.1f %d\n",s.SkillID,s.DamagePercent,s.DamageSource,s.CoolTime,s.MP);
      }
    }
  }
}
