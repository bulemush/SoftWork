/**
 * @zhang
 * 连接工具
 * 释放工具
 */


/*
导入包
 */
package mains;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {
    /**
     * 静态值
     */
    private final static String url ;
    private final static String name ;
    private final static String password ;
    private static String driver ;

    /**
     * 获取参数
     */
    static{
        try {
            //获取资源路径
            ClassLoader classLoader = JDBCUtils.class.getClassLoader();
            URL res = classLoader.getResource("jdbc_connection.properties");
            assert res != null;
            String path = res.getPath();
            System.out.println(path);

            //创建properties对象
            Properties pro = new Properties();

            //传入数据
            pro.load(new FileReader(path));
            url = pro.getProperty("url");
            name = pro.getProperty("name");
            password = pro.getProperty("password");
            driver = pro.getProperty("driver");

            //注册驱动
            Class.forName(driver);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     *
     * @return
     */
    //开启连接
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(url,name,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param conn
     * @param stmt
     */

    public static void close(Connection conn,Statement stmt){
        if(stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void close(Connection conn,Statement stmt,Statement stmt1){
        if(stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(stmt1 != null){
            try {
                stmt1.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     *
     * @param conn
     * @param stmt
     * @param rs
     */
    public static void close(Connection conn, Statement stmt, ResultSet rs){
        if(stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
