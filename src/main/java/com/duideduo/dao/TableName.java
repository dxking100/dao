/**  

 * Copyright © 2017天天兑科技有限公司. All rights reserved.

 *

 * @Title: Test.java

 * @Prject: wallet

 * @Package: com.ttd.system

 * @Description: TODO

 * @author: victor  

 * @date: 2017年3月7日 上午10:29:41

 * @version: V1.0  

 */  
package com.duideduo.dao;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**

 * @ClassName: TableName

 * @Description: 读取表名

 * @author: victor

 * @date: 2017年3月7日 上午10:29:41


 */
@Target(ElementType.TYPE)//用在那里
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableName {
	String value() default "";

}


