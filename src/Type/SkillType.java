package Type;

public class SkillType {
  public int SkillID;
  public double DamagePercent;
  public String DamageSource;
  public boolean Criticalable;
  public double CoolTime;
  public int MP;

  public SkillType(String Line){
    String[] s = Line.split(",");
    SkillID = Integer.parseInt(s[0]);
    DamagePercent = Double.parseDouble(s[2]);
    DamageSource = s[3];
    if(s[4].compareTo("O") == 0){
      Criticalable = true;
    }
    else {
      Criticalable = false;
    }
    CoolTime = Double.parseDouble(s[5]);
    MP = Integer.parseInt(s[6]);
  }
}
