package lk.ijse.gdse71.supermarketfx.dao;

import lk.ijse.gdse71.supermarketfx.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUtil {
    public static <T>T execute(String sql,Object... obj) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement(sql);

        for (int i = 0; i < obj.length; i++) {
            pst.setObject(i + 1, obj[i]);
        }
        if (sql.startsWith("SELECT")|| sql.startsWith("select")) {
            ResultSet rs = pst.executeQuery();
            return (T) rs;
        }else{
            int i = pst.executeUpdate();
            boolean isSaved = i > 0;
            return (T) ((Boolean) isSaved);
        }
    }
}
