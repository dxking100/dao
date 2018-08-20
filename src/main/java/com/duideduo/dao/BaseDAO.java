/**  

 * Copyright © 2017天天兑科技有限公司. All rights reserved.

 *

 * @Title: BaseDAO.java

 * @Prject: wallet

 * @Package: com.ttd.system

 * @Description: TODO

 * @author: victor  

 * @date: 2017年3月5日 下午12:00:08

 * @version: V1.0  

 */
package com.duideduo.dao;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * 
 * @ClassName: BaseDAO
 * 
 * @Description: 基础DAO接口实现
 * 
 * @author: victor
 * @param <T>
 * 
 * @date: 2017年3月5日 下午12:00:08
 * 
 */
public abstract class BaseDAO<T> implements IBaseDAO<T> {
	Class<T> entityClass;

	@Autowired
	public JdbcTemplate jdbcTemplate;

	@SuppressWarnings("unchecked")
	public BaseDAO() {
		Type t = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) t).getActualTypeArguments();
		entityClass = (Class<T>) params[0];

	}

	/*
	 * (non Javadoc)
	 * 
	 * @Title: add
	 * 
	 * @Description: TODO
	 * 
	 * @param t
	 * 
	 * @return
	 * 
	 * @see com.ttd.system.IBaseDAO#add(java.lang.Object)
	 * 
	 */
	@Override
	public int add(T t) {
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlParam = new StringBuffer();// sql语句
		StringBuffer question = new StringBuffer();// sql语句
		ArrayList<Object> params = new ArrayList<Object>(); // 参数
		String tableName = entityClass.getAnnotation(TableName.class).value();

		Field fields[] = entityClass.getDeclaredFields(); // 类参数

		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			field.setAccessible(true);
			try {
				if (field.get(t) != null) {
					sqlParam.append(field.getName()+",");
					question.append("?,"); // 对应的问
					params.add(field.get(t));
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		sql.append("insert into " + tableName);
		sql.append("(" + sqlParam.toString().substring(0,sqlParam.toString().length()-1) + ") values");
		sql.append("(" + question.toString().substring(0,question.toString().length()-1) + ")");
		System.out.println(sql.toString());
		System.out.println(params.toString());
		return jdbcTemplate.update(sql.toString(), params.toArray());
	}

	/*
	 * (non Javadoc)
	 * 
	 * @Title: update
	 * 
	 * @Description: TODO
	 * 
	 * @param t
	 * 
	 * @return
	 * 
	 * @see com.ttd.system.IBaseDAO#update(java.lang.Object)
	 * 
	 */
	@Override
	public int update(T t,String id) {
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlParam = new StringBuffer();// sql语句
		ArrayList<Object> params = new ArrayList<Object>(); // 参数
		String tableName = entityClass.getAnnotation(TableName.class).value();

		Field fields[] = entityClass.getDeclaredFields(); // 类参数

		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			field.setAccessible(true);
			try {
				if (field.get(t) != null) {

					if (field.getName() != "id") {
						sqlParam.append(field.getName() + "=?,");
						params.add(field.get(t));
					}
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		sql.append("update " + tableName);
		sql.append(" set " + sqlParam.toString().substring(0, sqlParam.toString().length()-1));
		sql.append(" where id = " + id);
		return jdbcTemplate.update(sql.toString(), params.toArray());
	}

	/*
	 * (non Javadoc)
	 * 
	 * @Title: delete
	 * 
	 * @Description: TODO
	 * 
	 * @param t
	 * 
	 * @return
	 * 
	 * @see com.ttd.system.IBaseDAO#delete(java.lang.Object)
	 * 
	 */
	@Override
	public int delete(String id) {
		Object param[] = {id};
		StringBuffer sql = new StringBuffer();
		String tableName = entityClass.getAnnotation(TableName.class).value();

		sql.append("delete from " + tableName);
		sql.append(" where id = ?");
		return jdbcTemplate.update(sql.toString(),param);
	}
	
/* (non Javadoc)
	
	 * @Title: queryById
	
	 * @Description: TODO
	
	 * @param id
	 * @return
	
	 * @see com.ttd.system.IBaseDAO#queryById(java.lang.String)
	
	 */
	@Override
	public T queryById(String id) {
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(id);
		String where = "where id = ?";
		List<T> list =  this.queryByWhere(where,paramList);
		if(list.size() >0){
			return  list.get(0);
		}
		return null;
	}


	/*
	 * (non Javadoc)
	 * 
	 * @Title: query
	 * 
	 * @Description: TODO
	 * 
	 * @param t
	 * 
	 * @return
	 * 
	 * @see com.ttd.system.IBaseDAO#query(java.lang.Object)
	 * 
	 */
	public List<T> queryByWhere(String where,ArrayList<Object> params) {
		StringBuffer sql = new StringBuffer();
		String tableName = entityClass.getAnnotation(TableName.class).value();
		sql.append("select * from "+tableName+" "+where);
		List<T> list = this.jdbcTemplate.query(sql.toString(),params.toArray(), new RowMapper<T>() {

			@Override
			public T mapRow(ResultSet rs, int rowNum) throws SQLException {
				T o = null;
				try {
					o = entityClass.newInstance();

					/*
					 * 反射运行方法 // Method m =
					 * entityClass.getDeclaredMethod("setId", int.class); // int
					 * a = m.invoke(o, 123); //传参，带 返回值
					 * 
					 */

					// /**
					// * 反射注秘属性
					// */
					// Field f = entityClass.getDeclaredField("id");
					// f.setAccessible(true);
					// f.set(o, 22222);

					Field fields[] = entityClass.getDeclaredFields(); // 类参数

					for (int i = 0; i < fields.length; i++) {
						Field field = fields[i];
						field.setAccessible(true);
						if(rs.getObject(field.getName()) != null){
							field.set(o, rs.getObject(field.getName()));
						}
					}

				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return o;
			}

		});
		return list;
	}

	
	
	
	

}
