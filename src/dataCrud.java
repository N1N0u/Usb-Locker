import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dataCrud {
	
	
	
	public String getPass() throws SQLException
	{
		String pString=null;
		String sqlString="select mdp from users where id=1";
		Connection connection=sqlConnection.conector();
		Statement st=connection.createStatement();
		ResultSet rs=st.executeQuery(sqlString);
		while (rs.next())
		{
			pString=(rs.getString("mdp"));
		}
		return pString;
	}
	public void setPass(String p) throws ClassNotFoundException, SQLException
	{
		String sql="update users set mdp='"+p+"' where id=1";
		sqlConnection.executeSQLQuery(sql);
	}
	public String getDate() throws SQLException
	{
		String pString="";
		String sqlString="select date from users where id=1";
		Connection connection=sqlConnection.conector();
		Statement st=connection.createStatement();
		ResultSet rs=st.executeQuery(sqlString);
		while (rs.next())
		{
			pString=(rs.getString("date"));
		}
		return pString;
	}
	public void setDate(String d) throws ClassNotFoundException, SQLException
	{
		String sql="update users set date='"+d+"' where id=1";
		sqlConnection.executeSQLQuery(sql);
	}
	
	public String getKey() throws SQLException
	{
		String pString="";
		String sqlString="select key from users where id=1";
		Connection connection=sqlConnection.conector();
		Statement st=connection.createStatement();
		ResultSet rs=st.executeQuery(sqlString);
		while (rs.next())
		{
			pString=(rs.getString("key"));
		}
		return pString;
	}
	public void setKey(String k) throws ClassNotFoundException, SQLException
	{
		String sql="update users set key='"+k+"' where id=1";
		sqlConnection.executeSQLQuery(sql);
	}
	

}
