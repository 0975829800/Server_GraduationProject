package Type;

public class MonsterType {
  public int    MapObjectID;
  public int    MonsterID;     //狀態
  public String MonsterName;
  public double Longitude;  //經度
  public double Latitude;   //緯度
  public int    Location;   //地區(用來判定生成
  public int[]  Skills = new int[10];

  public boolean Fighting = false; //是否在戰鬥狀態

  public int    HatredPlayer;     //仇恨對象

  public int[]  DamagePID = new int[10];        //傷害統計(計算哪個人打了多少血，最多的設為仇恨對象
  public int[]  DamageStatistic = new int[10];

  public double AttackSpeed;
  public double HP;
  public double MAX_HP;
  public double MP;
  public double MAX_MP;
  public double State;
  public double Attack;
  public double Defence;
  public double Critical;
  public double MagicAttack;
  public double MagicDefence;

  public MonsterType(){ }
  public MonsterType(String Line){ //csv input
    String[] items = Line.split(",");
    MonsterID = Integer.parseInt(items[0]);
    MonsterName = items[1];
    AttackSpeed = Double.parseDouble(items[2]);
    Attack = Double.parseDouble(items[3]);
    Defence = Double.parseDouble(items[4]);
    Critical = Double.parseDouble(items[5]);
    MagicAttack = Double.parseDouble(items[6]);
    MagicDefence = Double.parseDouble(items[7]);
    MAX_HP = Double.parseDouble(items[8]);
    MAX_MP = Double.parseDouble(items[9]);
    for(int i = 10; i < items.length; i++){
      Skills[i-10] = Integer.parseInt(items[i]);
    }
  }

}
