package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;


public class DepartmentDaoJDBC implements DepartmentDao {
	
	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO department "
					+"(Name) "
					+"VALUES "
					+"(?)",
					Statement.RETURN_GENERATED_KEYS
					);
			
			st.setString(1, obj.getName());
			
			int rowsAffect = st.executeUpdate();
			
			if (rowsAffect > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				
				DB.closeResultset(rs);
			}
			else {
				throw new DbException("Unexoected error! NNo rows affected!");
			}
			
			
					
		}
		
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatemant(st);
		}
		
	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE department "
					+"SET Name = ? "
					+"WHERE Id = ?"
					
					);
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			
			st.executeUpdate();
			
			
			
					
		}
		
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatemant(st);
		}
		
	}

	@Override
	public void deleteByID(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM department WHERE Id = ? ");
			
			st.setInt(1, id);
			
			st.executeUpdate();
		}
		catch(SQLException e ) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatemant(st);
		}
		
		
	}
		
	

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * "
					+ "FROM Department "
					+ "WHERE Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Department obj = new Department();
				obj.setId(rs.getInt("ID"));
				obj.setName(rs.getString("Name"));
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatemant(st);
			DB.closeResultset(rs);
		}
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT *  "
					+ "FROM  department "
					+ "ORDER BY Id");
			
			
			rs = st.executeQuery();
			
			List<Department> list = new ArrayList<>();
			
			
			while (rs.next()) {
				
				Department obj = new Department();
				obj.setName(rs.getString("Name"));
				obj.setId(rs.getInt("Id"));
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatemant(st);
			DB.closeResultset(rs);
		}
	}

}