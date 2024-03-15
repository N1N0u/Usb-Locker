import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class sqlConnection {
	Connection con=null;
	public static Connection conector()
	{
		try{
			Class.forName("org.sqlite.JDBC");
			Connection con=DriverManager.getConnection("jdbc:sqlite:rsc\\data.error.db");
			//System.out.println("Connected");
			return con;
		}catch(Exception e){ JOptionPane.showMessageDialog(null, e); return null;}
	}
	public static void executeSQLQuery (String sql) throws ClassNotFoundException, SQLException {
        Connection con = conector();
        Statement st = con.createStatement();
        int n = st.executeUpdate(sql);
        st.close();
        con.close();
    }
	

}