/**   
 * @Title: FarmerAdpater.java 
 * @Package com.jr001.adpater 
 * @Description: 模糊匹配，AutoCompleteTextView模糊查询
 * @author jiaone@163.com
 * @date 2015-4-23 上午10:48:21 
 * @version V1.0   
 */
package com.elder.abilityevaluate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FuzzyAdpater<T> extends BaseAdapter implements Filterable {

	public static final int FIND_TYPE_PRE = 0x01;
	public static final int FIND_TYPE_END = 0x02;
	public static final int FIND_TYPE_ANY = 0x03;

	private List<T> mObjects;
	private final Object mLock = new Object();

	private int mResource;

	private int mFieldId = 0;

	private Context mContext;

	private ArrayList<T> mOriginalValues;
	private ArrayFilter mFilter;

	private LayoutInflater mInflater;

	private int mFindType = FIND_TYPE_PRE;

	public static final int ALL = -1;// 全部
	private int maxMatch = 10;// 最多显示多少个可能选项
	private int mfindLength = -1;// 从头开始的匹配字数

	public FuzzyAdpater(Context context, int layoutResourceId,
			int textViewResourceId, T[] objects, int maxMatch, int findType,
			int findLength) {
		// TODO Auto-generated constructor stub
		mContext = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mResource = layoutResourceId;
		mObjects = Arrays.asList(objects);
		mFieldId = textViewResourceId;
		this.maxMatch = maxMatch;
		mFindType = findType;
		mfindLength = findLength;
	}

	public int getCount() {
		return mObjects.size();
	}

	public T getItem(int position) {
		return mObjects.get(position);
	}

	public int getPosition(T item) {
		return mObjects.indexOf(item);
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		TextView text;

		if (convertView == null) {
			view = mInflater.inflate(mResource, parent, false);
		} else {
			view = convertView;
		}

		try {
			if (mFieldId == 0) {
				text = (TextView) view;
			} else {
				text = (TextView) view.findViewById(mFieldId);
			}
		} catch (ClassCastException e) {
			System.err.println("ArrayAdapter" + "/n"
					+ "You must supply a resource ID for a TextView");
			throw new IllegalStateException(
					"ArrayAdapter requires the resource ID to be a TextView", e);
		}
		text.setText(getItem(position).toString());
		return view;
	}

	@Override
	public Filter getFilter() {
		if (mFilter == null) {
			mFilter = new ArrayFilter();
		}
		return mFilter;
	}

	private class ArrayFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence prefix) {
			FilterResults results = new FilterResults();

			if (mOriginalValues == null) {
				synchronized (mLock) {
					mOriginalValues = new ArrayList<T>(mObjects);//
				}
			}

			if (prefix == null || prefix.length() == 0) {
				synchronized (mLock) {
					ArrayList<T> list = new ArrayList<T>(mOriginalValues);// List<T>
					results.values = list;
					results.count = list.size();
				}
			} else {
				String prefixString = prefix.toString().trim().toLowerCase();
				final int count = mOriginalValues.size();
				final Set<T> newValues = new HashSet<T>(count);
				for (int i = 0; i < count; i++) {
					final T value = mOriginalValues.get(i);
					final String mValueText = value.toString().toLowerCase();
					switch (mFindType) {
					case FIND_TYPE_ANY:
						if (mValueText.indexOf(prefixString) > -1) {
							newValues.add(value);
						}
						break;
					case FIND_TYPE_END:
						int len = mValueText.length();
						// 从头数的匹配位数
						int fl = mfindLength > len || mfindLength <= 0 ? len
								: mfindLength;
						if (prefixString.length() > fl) {
							break;
						}
						if (mValueText.substring(0, fl)
								.substring(fl - prefixString.length())
								.equals(prefixString)) {
							newValues.add(value);
						}
						break;
					default:
						if (mValueText.indexOf(prefixString) == 0) {
							newValues.add(value);
						}
						break;
					}
					if (maxMatch > 0) {// 有数量限制
						if (newValues.size() > maxMatch - 1) {// 不要太多
							break;
						}
					}

				}
				List<T> list = Set2List(newValues);// 转成List
				results.values = list;
				results.count = list.size();
			}
			return results;
		}

		protected void publishResults(CharSequence constraint,
				FilterResults results) {

			mObjects = (List<T>) results.values;
			if (results.count > 0) {
				notifyDataSetChanged();
			} else {
				notifyDataSetInvalidated();
			}
		}
	}

	// List Set 相互转换
	public <T extends Object> Set<T> List2Set(List<T> tList) {
		Set<T> tSet = new HashSet<T>(tList);
		// TODO 具体实现看需求转换成不同的Set的子类。
		return tSet;
	}

	public <T extends Object> List<T> Set2List(Set<T> oSet) {
		List<T> tList = new ArrayList<T>(oSet);
		// TODO 需要在用到的时候另外写构造，根据需要生成List的对应子类。
		return tList;
	}

}
