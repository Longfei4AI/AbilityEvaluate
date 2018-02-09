/**   
* @Title: CustomDataHelper.java
* @Package com.custom.tpc.utils
* @Description: 数据库操作类 
* @author 山东山大新元易通信息科技有限公司 www.sdcustom.com
* @date 2015-3-11 上午11:34:10 
* @version V1.0   
*/
package com.elder.abilityevaluate.utils;

import android.content.Context;
import android.database.Cursor;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.DbModelSelector;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.SqlInfo;
import com.lidroid.xutils.db.table.DbModel;
import com.lidroid.xutils.exception.DbException;
import java.util.ArrayList;
import java.util.List;

public class CustomDataHelper {
	public static DbUtils db;
	private Class<?> eClass = getClass();
	public static CustomDataHelper getInstance(Context context, Class<?> cl){
		CustomDataHelper helper = new CustomDataHelper();
		if(db==null){
			db = DbUtils.create(context);
		}
		try {
//			db.execNonQuery("alter table BaseInformationListActivity add  text ");
			db.execNonQuery("alter table ArcGrade add gradeCode text ");
			db.execNonQuery("alter table ArcStation add lineCode,lineName,countyName text ");
			db.configAllowTransaction(true);
			db.createTableIfNotExist(cl);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return helper;
	}
	private CustomDataHelper(){
		super();
	}
	
	public long count(){
		long s = 0;
		try {
			s = db.count(eClass);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public long count(Selector selector){
		long s = 0;
		try {
			s = db.count(selector);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public <T> List<T> getList(Class<T> entityType){
		List<T> list = new ArrayList<T>();
		try {
			list = db.findAll(entityType);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public <T> List<T> getListBySelector(Selector selector) {
		List<T> list = new ArrayList<T>();
		try {
			list = db.findAll(selector);
		} catch (DbException e1) {
			e1.printStackTrace();
		}
		return list;
	}
	
	public boolean save(Object o) {
		try {
			db.save(o);
		} catch (DbException e1) {
			e1.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean update(Object o) {
		try {
			db.update(o);
		} catch (DbException e1) {
			e1.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean saveOrUpdate(Object o) {
		try {
			db.saveOrUpdate(o);
		} catch (DbException e1) {
			e1.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean delete(Object o) {
		try {
			db.delete(o);
		} catch (DbException e1) {
			e1.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean deleteAll(List<?> list) {
		try {
			db.deleteAll(list);
		} catch (DbException e1) {
			e1.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean deleteAll(Class<?> cla) {
		try {
			db.deleteAll(cla);
		} catch (DbException e1) {
			e1.printStackTrace();
			return false;
		}
		return true;
	}
	
	public DbModel findDbModelFirst(DbModelSelector selector){
		 DbModel localDbModel = null;
		 try {
			 localDbModel = db.findDbModelFirst(selector);
		} catch (DbException e) {
			e.printStackTrace();
		}
         return localDbModel;
	}
	
	public DbModel findDbModelFirst(String sql){
		 DbModel localDbModel = null;
		 SqlInfo sqlInfo = new SqlInfo(sql);
		 try {
			 localDbModel = db.findDbModelFirst(sqlInfo);
		} catch (DbException e) {
			e.printStackTrace();
		}
        return localDbModel;
	}
	
	public List<DbModel> findDbModelAll(String sql){
		List<DbModel> dbModels = null;
		SqlInfo sqlInfo = new SqlInfo(sql);
		try {
			dbModels = db.findDbModelAll(sqlInfo);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return dbModels;
	}
	
	public List<DbModel> findDbModelAll(DbModelSelector selector){
		List<DbModel> dbModels = null;
		try {
			dbModels = db.findDbModelAll(selector);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return dbModels;
	}
	
	
	 public <T> T findFirst(Selector selector){
		 T t = null;
		 try {
			t = db.findFirst(selector);
		} catch (DbException e) {
			e.printStackTrace();
		}
		 return t;
	 }
	 public void exeuSql(String sql){
		 try {
			db.execNonQuery(sql);
		} catch (DbException e) {
			e.printStackTrace();
		}
	 }
}
