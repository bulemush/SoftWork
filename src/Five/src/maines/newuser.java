/**
 * 用户ID,PASSWORD
 * 成功返回true，失败返回false
 */
package Five.src.maines;

import mains.JDBCUtils;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
public class newuser {
    //资源预准备
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public boolean newus(String password ,String id){
        //创建连接
        Connection conn = JDBCUtils.getConnection();
        System.out.println("连接成功！");
        try {

            //4.MySQL语句
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月和小时的格式为两个大写字母
            java.util.Date date = new Date(new java.util.Date().getTime());//获得当前时间
            String day = df.format(date);//将当前时间转换成特定格式的时间字符串，这样便可以插入到数据库中
            System.out.println(day.toString());
            String sql = "insert into getin(id,recent,password) values(?,?,?)";
             ps  = conn.prepareStatement(sql);
             ps.setString(1,id);
             ps.setString(2,day);
             ps.setString(3,password);
            //6.遍历结果集
            if(ps.executeUpdate()==1) {
                //7.获取数据
//                name = ps.getString("recent");
//                System.out.println("恭喜你" + name + "创建用户！");
////                password = rs.getString("password");
//                System.out.println(password);
                System.out.println("创建成功！");
                JDBCUtils.close(conn,ps,rs);
                return true ;
            }else{
                System.out.println("创建错误！");
                JDBCUtils.close(conn,ps,rs);
                return false ;
            }
         } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
