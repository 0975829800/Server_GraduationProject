package Type;

import java.util.Arrays;

public class MonsterType {
  public int    MapObjectID;
  public int    MonsterID;     //狀態
  public String MonsterName;
  public double Longitude;  //經度
  public double Latitude;   //緯度
  public int    Location;   //地區(用來判定生成
  public int[]  Skills = new int[10];

  public int exp;
  public int coin;
  public int[] drop;

  public boolean Fighting = false; //是否在戰鬥狀態

  public int    HatredPlayer;     //仇恨對象

  public int[]  DamagePID = new int[10];        //傷害統計(計算哪個人打了多少血，最多的設為仇恨對象
  public int[]  DamageStatistic = new int[10];

  public double AttackSpeed;
  public double HP;
  public double MAX_HP;
  public double MP;
  public double MAX_MP;
  public int State;
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
    exp = Integer.parseInt(items[3]);
    coin = Integer.parseInt(items[4]);
    AttackSpeed = Double.parseDouble(items[5]);
    Attack = Double.parseDouble(items[6]);
    Defence = Double.parseDouble(items[7]);
    Critical = Double.parseDouble(items[8]);
    MagicAttack = Double.parseDouble(items[9]);
    MagicDefence = Double.parseDouble(items[10]);
    MAX_HP = Double.parseDouble(items[11]);
    Skills[0] = Integer.parseInt(items[12]);
  }


  public String ToString() {
    return "MonsterType{" +
            "MapObjectID=" + MapObjectID +
            ", MonsterID=" + MonsterID +
            ", MonsterName='" + MonsterName + '\'' +
            ", Longitude=" + Longitude +
            ", Latitude=" + Latitude +
            ", Location=" + Location +
            ", Skills=" + Arrays.toString(Skills) +
            ", exp=" + exp +
            ", coin=" + coin +
            ", drop=" + Arrays.toString(drop) +
            ", Fighting=" + Fighting +
            ", HatredPlayer=" + HatredPlayer +
            ", DamagePID=" + Arrays.toString(DamagePID) +
            ", DamageStatistic=" + Arrays.toString(DamageStatistic) +
            ", AttackSpeed=" + AttackSpeed +
            ", HP=" + HP +
            ", MAX_HP=" + MAX_HP +
            ", MP=" + MP +
            ", MAX_MP=" + MAX_MP +
            ", State=" + State +
            ", Attack=" + Attack +
            ", Defence=" + Defence +
            ", Critical=" + Critical +
            ", MagicAttack=" + MagicAttack +
            ", MagicDefence=" + MagicDefence +
            '}';
  }
}
