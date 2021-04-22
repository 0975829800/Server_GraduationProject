package Type;

public class MonsterType {
  public int    MapObjectID;
  public int    MonsterID;     //狀態
  public double Longitude;  //經度
  public double Latitude;   //緯度
  public int    Location;   //地區(用來判定生成
  public int[]  Skills;

  public boolean Fighting = false; //是否在戰鬥狀態

  public int    HatredPlayer;     //仇恨對象

  public int[]  DamagePID;        //傷害統計(計算哪個人打了多少血，最多的設為仇恨對象
  public int[]  DamageStatistic;

  public int    HP;
  public int    MaxHP;
  public int    MP;
  public int    MAX_MP;
  public int    State;
  public int    Attack;
  public int    Defence;
  public int    Critical;
  public int    MagicAttack;
  public int    MagicDefence;
}
