/**   
* @Title: XyytDataHelper.java 
* @Package com.xyyt.tpc.utils 
* @Description: 数据库操作类 
* @author 山东山大新元易通信息科技有限公司 www.sdxyyt.com   
* @date 2015-3-11 上午11:34:10 
* @version V1.0   
*/
package com.elder.abilityevaluate.utils;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.DbModelSelector;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.SqlInfo;
import com.lidroid.xutils.db.table.DbModel;
import com.lidroid.xutils.exception.DbException;

public class DataBaseHelper {
	public static DbUtils db;
	private Class<?> eClass = getClass();
	public static DataBaseHelper getInstance(Context context,Class<?> cl){
		DataBaseHelper helper = new DataBaseHelper();
		if(db==null){
			db = DbUtils.create(context);
		}
		try {
			db.configAllowTransaction(true);
			db.createTableIfNotExist(cl);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return helper;
	}
	private DataBaseHelper(){
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

	 public  List<Object> exeuSqls(String sql){
		 List<Object> list = new ArrayList<Object>();
		 try {
			 Cursor cursor = db.execQuery(sql);
			if (null != cursor && cursor.getCount() > 0) {
				cursor.moveToFirst();
				while (!cursor.isAfterLast()) {
					String code = cursor.getString(0);
					String name  =cursor.getString(1);
					Object sta = new Object();

					list.add(sta);
					cursor.moveToNext();
				}
				cursor.close();
			}	
		} catch (DbException e) {
			e.printStackTrace();
		}
		return list;
	 }
}
