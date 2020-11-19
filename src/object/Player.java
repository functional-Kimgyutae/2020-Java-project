package object;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.JDBCUtil;

public class Player {
	public String id;
	public String name;
	public int win;
	public int lose;

	public void Player(String id) {
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM `Java_project_users` WHERE id = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			rs.next();
			this.id = rs.getString("id");
			this.name = rs.getString("user_name");
			this.win = rs.getInt("win");
			this.lose = rs.getInt("lose");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Player.java contructor error");
		}finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
			JDBCUtil.close(con);
		}
	}
	public String name() {
		return name;
	}
	
	public int win() {
		return win;
	}
	
	public int lose() {
		return lose;
	}
	public void wining() {
		this.win++;
	}
	
	public void loseing() {
		this.lose++;
	}
	
	public void update() {
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		int rs;
		String sql = "UPDATE `Java_project_users` SET `win`=?,`lose`=? WHERE id = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, win);
			pstmt.setInt(2, lose);
			pstmt.setString(3, id);
			rs = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(pstmt);
			JDBCUtil.close(con);
		}			
		Player(this.id);
	}
	
}
