package com.me.metadata.db;

import com.me.metadata.db.model.Filed;
import com.me.metadata.db.model.Table;
import freemarker.template.Template;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * entity代码生成
 * wu_hc
 */
public class GenerateUtils {

    public static final String DB_NAME = "jy_game";

    private final static String PACKAGE_NAME = "com.me.metadata.db.entity";
    private final static String URL = String.format("jdbc:mysql://localhost:3306/%s?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true", DB_NAME);
    private final static String USER = "root";
    private final static String PASSWORD = "123456";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        return connection;
    }

    /**
     * 关闭数据库连接
     *
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    public List<String> getTableNames() throws Exception {
        List<String> tableNames = new ArrayList<>();
        Connection conn = getConnection();
        ResultSet rs = null;
        try {
            //获取数据库的元数据
            DatabaseMetaData db = conn.getMetaData();
            //从元数据中获取到所有的表名
            rs = db.getTables(null, null, null, new String[]{"TABLE"});
            while (rs.next()) {
                tableNames.add(rs.getString(3));
            }
        } catch (SQLException e) {
        } finally {
            try {
                rs.close();
                closeConnection(conn);
            } catch (SQLException e) {
            }
        }
        return tableNames;
    }

    static Function<String, String> upperFirstCase = (str) -> {
        char[] chars = str.toCharArray();
        chars[0] -= 32;
        return String.valueOf(chars);
    };


    public static Table getTables(String tableName) throws Exception {
        //与数据库的连接
        Connection conn = getConnection();
        Statement pStemt = null;
        List<Filed> filedList = new ArrayList<>();
        ResultSet rs = null;
        try {
            pStemt = conn.createStatement();
            rs = pStemt.executeQuery(
                    String.format("SELECT COLUMN_NAME,DATA_TYPE,COLUMN_COMMENT,COLUMN_KEY FROM information_schema.columns WHERE table_schema='%s' and table_name='%s' order By ORDINAL_POSITION", "jy_game", tableName));
            while (rs.next()) {
                Filed filed = new Filed();
                filed.setName(rs.getString("COLUMN_NAME"));
                filed.setType(rs.getString("DATA_TYPE"));
                filed.setComment(rs.getString("COLUMN_COMMENT"));
                filed.setPri("pri".equalsIgnoreCase(rs.getString("COLUMN_KEY")));
                filedList.add(filed);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                }
            }
        }
        String[] temp = tableName.split("_");
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : temp) {
            if (s.length() == 1) {
                continue;
            }
            stringBuilder.append(upperFirstCase.apply(s)).append("Entity");
        }

        Table table = new Table();
        table.setClassName(stringBuilder.toString());
        table.setFiledList(filedList);
        table.setTableName(tableName);
        table.setComment("");
        table.setDbName(DB_NAME);
        table.setPackageName(PACKAGE_NAME);
        return table;
    }

    public static void main(String[] args) throws Exception {
        Table table = getTables("t_club");

        Template template = FreeMarkerTemplateUtils.getTemplate("entity.ftl");
        File file = new File(String.format("E:\\workspace\\me-game\\me-metadata\\src\\main\\java\\com\\me\\metadata\\db\\entity\\%s.java", table.getClassName()
        ));
        FileOutputStream fos = new FileOutputStream(file);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        template.process(table, out);
    }


}
