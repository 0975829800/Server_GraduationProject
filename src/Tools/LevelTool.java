package Tools;

import Type.PlayerInformation;

import java.util.Map;

public class LevelTool {
  public static int expControl(PlayerInformation p , int exp){
    double level1 = 50;
    double rate = 1.5;
    int level_up = 0;
    p.status.exp += exp;
    if(p.status.exp > level1 * Math.pow(1.5,p.status.Level-1)){
      exp = (int)(level1 * Math.pow(1.5,p.status.Level-1)) - exp;
      level_up = 1;
      p.status.Skill_Point++;
    }
    return level_up;
  }
}
