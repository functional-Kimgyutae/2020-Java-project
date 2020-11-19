package views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import util.JDBCUtil;
import util.Util;

public class RegisterController extends MasterController{
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private PasswordField pass;
	
	@FXML 
	private PasswordField passConfirm;
	
	public void register() {
		String id = txtId.getText().trim();
		String name = txtName.getText().trim();
		String strPass = pass.getText().trim();
		String confirm = passConfirm.getText().trim();
		
		if(id.isEmpty()|| name.isEmpty()|| strPass.isEmpty()) {
			Util.showAlert("비여있음", "필수 입력창이 비어있습니다.", AlertType.INFORMATION);
			return;
		}
		if(!strPass.equals(confirm)) {
			Util.showAlert("불일치", "비밀번호가 일치하지 않습니다.", AlertType.INFORMATION);
			return;
		}
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sqlExist = "SELECT * FROM Java_project_users WHERE id = ?";
		String sqlInsert = "INSERT INTO `Java_project_users`(`id`, `user_name`, `password`, `win`, `lose`) VALUES (?,?,PASSWORD(?),0,0)";
		try {
			pstmt = con.prepareStatement(sqlExist);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Util.showAlert("회원 중복", "이미 해당 id의 유저가 존재합니다. 다른 아이디를 사용하세요.",AlertType.INFORMATION);
				return;
			}
			JDBCUtil.close(pstmt);
			pstmt = con.prepareStatement(sqlInsert);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, strPass);
			if(pstmt.executeUpdate() != 1) {
				Util.showAlert("에러","데이터베이스에 회원정보가 올바르게 입력되지 않았습니다.", AlertType.ERROR);
				return;
			}
			Util.showAlert("성공", "데이터베이스에 회원정보가 올바르게 입력되었습니다.", AlertType.INFORMATION);
			Main.app.slideOut(getRoot());
			Main.app.loadPane("login");
		} catch (Exception e) {
			Util.showAlert("에러","데이터베이스 연결중 오류가 발생했습니다.", AlertType.ERROR);
			return;
		}finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
			JDBCUtil.close(con);
		}
	}
	public void cancel() {
		Main.app.slideOut(getRoot());
		Main.app.loadPane("login");
	}
	
}
