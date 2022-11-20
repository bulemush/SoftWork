/**
 * 用户ID,PASSWORD
 * 成功返回true，失败返回false
 */

package Five.src.maines;

import mains.JDBCUtils;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class loadin {
    //资源预准备
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String name = null;

    //id and password passwordin
    String password = null;
    String id = "123";
    String passwordin  = "123456789";

    public boolean join(String passwordin ,String id) {
        //创建连接
        Connection conn = JDBCUtils.getConnection();
        System.out.println("连接成功！");
        try {
            stmt = conn.createStatement();
            //4.MySQL语句
            String sql = "select password from user where id = " + id ;
            //5.执行sql
            rs = stmt.executeQuery(sql);
            //6.遍历结果集
            if(rs.next()) {
                //7.获取数据
//                name = rs.getString("recent");
                password = rs.getString("password");
                System.out.println(password);
            }else{
                System.out.println("结果错误！无该用户！");
                return false;
            }
            if(Objects.equals(passwordin, password)){
                System.out.println("登陆成功！");
                //年月日时分秒
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月和小时的格式为两个大写字母
                java.util.Date date = new Date(new java.util.Date().getTime());//获得当前时间
                String day = df.format(date);//将当前时间转换成特定格式的时间字符串，这样便可以插入到数据库中
                System.out.println(day.toString());
                String sql01 = "update user set recent='" + day + "' where id = " + id ;
                System.out.println(sql01);
                stmt.execute(sql01);
                JDBCUtils.close(conn,stmt,rs);
                return true ;
            }else{
                System.out.println("密码错误！");
                JDBCUtils.close(conn,stmt,rs);
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
