package DBS;

import java.sql.*;

public class DBConnection {

  public static void main(String[] args) {
    //宣告Connection物件
    Connection con;
    //驅動程式名
    String driver = "com.mysql.jdbc.Driver";
    //URL指向要訪問的資料庫名mydata
    String url = "jdbc:mysql://localhost:3306/bookshop";
    //MySQL配置時的使用者名稱
    String user = "root";
    //MySQL配置時的密碼
    String password = "1234";
    //遍歷查詢結果集
    try {
      //載入驅動程式
      Class.forName("com.mysql.jdbc.Driver");
      //1.getConnection()方法，連線MySQL資料庫！！
      con = DriverManager.getConnection(url,user,password);
      if(!con.isClosed())
        System.out.println("Succeeded connecting to the Database!");
      //2.建立statement類物件，用來執行SQL語句！！
      Statement statement = con.createStatement();
      //要執行的SQL語句
      String sql = "select * from author";
      //3.ResultSet類，用來存放獲取的結果集！！
      ResultSet rs = statement.executeQuery(sql);


      String n=null;
      String p=null;
      while(rs.next()) {

        //獲取name列的數

        n=rs.getString("ISBN");

        //獲取password列的資料

        p=rs.getString("Author");
        System.out.println(n+"\t"+p);
      }

      rs.close();
      con.close();
    } catch(ClassNotFoundException e) {
      //資料庫驅動類異常處理
      System.out.println("Sorry,can`t find the Driver!");
      e.printStackTrace();
    } catch(SQLException e) {
      //資料庫連線失敗異常處理
      e.printStackTrace();
    }catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }finally{
      System.out.println("資料庫資料成功獲取！！");
    }
  }

}
