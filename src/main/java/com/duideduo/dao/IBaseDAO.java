/**  

 * Copyright © 2017天天兑科技有限公司. All rights reserved.

 *

 * @Title: IBaseDAO.java

 * @Prject: wallet

 * @Package: com.ttd.system

 * @Description: TODO

 * @author: victor  

 * @date: 2017年3月5日 上午11:59:13

 * @version: V1.0  

 */
package com.duideduo.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @ClassName: IBaseDAO
 * 
 * @Description: 基础DAO接口
 * 
 * @author: victor
 * 
 * @date: 2017年3月5日 上午11:59:13
 * 
 * 
 */
public interface IBaseDAO<T> {
	/**
	 * 
	 * 
	 * @Title: add
	 * 
	 * @Description: 单表插入 ,传入里象时，若有参数为空，则插入语句会过滤这个字段
	 * 
	 * @param t
	 * @return
	 * 
	 * @return: int
	 */
	public int add(T t);

	/**
	 * 
	
	 * @Title: update
	
	 * @Description: 单表更新
	
	 * @param t
	 * @param id 更新id
	 * @return
	
	 * @return: int
	 */
	public int update(T t, String id);

	/**
	 * 
	 * 
	 * @Title: delete
	 * 
	 * @Description: TODO
	 * 
	 * @param id
	 * @return
	 * 
	 * @return: int
	 */
	public int delete(String id);

	/**
	 * 
	 * 
	 * @Title: queryById
	 * 
	 * @Description: 单表查询
	 * 
	 * @param id
	 * @return
	 * 
	 * @return: T
	 */
	public T queryById(String id);

	/**
	 * 
	 * 
	 * @Title: queryByWhere
	 * 
	 * @Description: 单表查询
	 * 
	 * @param where 查询条件
	 * @return
	 * 
	 * @return: List<T>
	 */
	public List<T> queryByWhere(String where, ArrayList<Object> params);

}
