package com.checker.miner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

@Repository
public class MineResultRepositorySearchV2Implementation implements MineResultRepositorySearchV2 {

	@Autowired
	JdbcTemplate jdbcTemplate;

	Logger logger = Logger.getLogger(MineResultRepositorySearchV2Implementation.class);

	@Override
	public List<MineResult> customSearch(Date date, String park, Integer parkID, String entrance, Integer entranceID) {
		ArrayList<MineResult> list = new ArrayList<>();
		// Return blank if there is nothing
		if (date == null && park == null && parkID == null && entrance == null && entranceID == null) {
			return list;
		}

		StringBuilder sqlBuilder = new StringBuilder("SELECT mine_result.sys_id, mine_result.date,");
		sqlBuilder.append(" mine_result.park_sys_id, mine_result.entrance_sys_id,");
		sqlBuilder.append(" entrance.name AS entrance_name, park.name AS park_name" + " FROM mine_result");

		// Handle joins
		sqlBuilder.append(" INNER JOIN entrance ON mine_result.entrance_sys_id = entrance.sys_id");
		sqlBuilder.append(" INNER JOIN park ON mine_result.park_sys_id = park.sys_id");
		sqlBuilder.append(" WHERE ");
		ArrayList<String> whereClauses = new ArrayList<>();
		ArrayList<Object> argsList = new ArrayList<>();
		// Handle queries
		if (date != null) {
			whereClauses.add("mine_result.date LIKE ?");
			argsList.add(date);
		}
		if (park != null) {
			whereClauses.add("park.name LIKE ?");
			argsList.add(park);
		}
		if (parkID != null) {
			whereClauses.add("mine_result.park_sys_id LIKE ?");
			argsList.add(parkID);
		}
		if (entrance != null) {
			whereClauses.add("entrance.name LIKE ?");
			argsList.add(entrance);
		}
		if (entranceID != null) {
			whereClauses.add("mine_result.entrance_sys_id LIKE ?");
			argsList.add(entranceID);
		}
		sqlBuilder.append(String.join(" AND ", whereClauses));

		String sql = sqlBuilder.toString();
		this.logger.debug("SQL = " + sql);
		this.logger.debug("Args = " + argsList);

		return this.jdbcTemplate.query(sql, argsList.toArray(), new ResultSetExtractor<List<MineResult>>() {

			@Override
			public List<MineResult> extractData(ResultSet rs) throws SQLException, DataAccessException {
				ArrayList<MineResult> list = new ArrayList<>();
				while (rs.next()) {
					MineResult mineResult = new MineResult();
					Park park = new Park();
					Entrance entrance = new Entrance();

					mineResult.setSysId(rs.getLong("sys_id"));
					mineResult.setDate(rs.getDate("date"));
					park.setSysId(rs.getLong("park_sys_id"));
					park.setName(rs.getString("park_name"));
					mineResult.setPark(park);
					entrance.setSysId(rs.getLong("entrance_sys_id"));
					entrance.setName(rs.getString("entrance_name"));
					mineResult.setEntrance(entrance);

					list.add(mineResult);
				}
				return list;
			}

		});
	}

}
