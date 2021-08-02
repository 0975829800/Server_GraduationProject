package Tools;

import Type.PlayerInformation;

import java.util.Map;

public class LevelTool {
  public static int expControl(PlayerInformation p , int exp){
    double level1 = 10;
    double rate = 1.5;
    int level_up = 0;
    p.status.EXP += exp;
    if(p.status.EXP > level1 * Math.pow(rate,p.status.Level-1)){
      p.status.EXP = (int)(level1 * Math.pow(rate,p.status.Level-1)) - p.status.EXP;
      level_up = 1;
      p.status.Skill_Point++;
    }
    return level_up;
  }
}
