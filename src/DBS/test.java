package DBS;

import java.sql.SQLException;

public class test {
  public static void main(String[] args) {
    try {
      DBConnection con = new DBConnection();
//      System.out.println(Arrays.toString(c.getAccountInform("2")));
      try {
//        Thread.sleep(7000);
//        System.out.println(con.register("4568","zzzz","e04"));
//        System.out.println(con.login("456789","zzzzzz"));
//        System.out.println(con.setName(31240985,"????"));

//        System.out.println(con.setTeam(31240985,11));

//        System.out.println(con.addEquipment_bag(31240985,1,1,1,1,0,0,0));
//        System.out.println(con.getEquipment_bag(31240985).get(0).PlayerID);
//        System.out.println(con.updateEquipment_bag(146937808,1,1,1,1,1,1,1));
//        System.out.println(con.delEquipment_bag(146937808,1));

//        System.out.println(con.addItem_bag(31240985,1,1,1));
//        System.out.println(con.getItem_bag(31240985).get(0).PlayerID);
//        System.out.println(con.updateItem_bag(31240985,2,1,1));
//        System.out.println(con.delItem_bag(31240985,2));

//        System.out.println(con.addFriend(31240985,2));  //one way , add*2 for two account addFriend
//        System.out.println(con.delFriend(31240985,2));
//        System.out.println(con.getFriend(31240985));

//        System.out.println(con.addStatus(31240985,1,2,3,4,5,6,7,8,9,10));
//        System.out.println(con.getStatus(31240985).MAX_MP);
//        System.out.println(con.updateStatus(146937808,1,2,3,4,5,6,7,8,9,10,11));
//        System.out.println(con.delStatus(146937808));

//        System.out.println(con.addProgress(31240985,1));
//        System.out.println(con.setProgress(31240985,1));
//        System.out.println(con.getProgress(31240985).get(0).PlayID);
//        System.out.println(con);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    } }
}
