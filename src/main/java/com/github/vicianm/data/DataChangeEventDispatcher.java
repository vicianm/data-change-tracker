package com.github.vicianm.data;

import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @see #addListener(DataChangeListener, Object...)
 * @see #removeListener(DataChangeListener)
 * @see #notifyDataSetChanged()
 */
public class DataChangeEventDispatcher {

	public static final String TAG = DataChangeEventDispatcher.class.getSimpleName();
	
	protected Map<DataChangeListener, Object[]> mListeners;
	
	protected DataChangeTracker mDataChangeTracker;
	
	protected boolean mNofityActive = false;
	
	public DataChangeEventDispatcher() {
		mListeners = new HashMap<DataChangeListener, Object[]>();
	}
	
	public void setDataChangeTracker(DataChangeTracker dataChangeTracker) {
		mDataChangeTracker = dataChangeTracker;
	}
	
	public void addListener(DataChangeListener listener, Object... dataIds) {
		mListeners.put(listener, dataIds);
	}
	
	public void removeListener(DataChangeListener listener) {
		mListeners.remove(listener);
	}
	
	public void notifyDataSetChanged() {
		
		assertNotifyIsNotActive();
		
		mNofityActive = true;
		
		debugNotify();
		
		for (DataChangeListener listener : mListeners.keySet()) {
			
			// IDs of data in which listener is interested in
			Object[] idsListener = mListeners.get(listener);
			// IDs of data in which listener is interested in and has changed
			Set<Object> idsNotify = new LinkedHashSet<Object>();
			
			for (Object id : idsListener) {
				if (mDataChangeTracker.isDataChanged(id)) {
					idsNotify.add(id);
				}
			}
			if (!idsNotify.isEmpty()) {
				listener.dataChanged(idsNotify);
			}
		}
		
		mNofityActive = false;
	}
	
	protected void debugNotify() {
		if (BuildConfig.DEBUG) {
			StringBuilder changedIdsStr = new StringBuilder();
			Iterator<Object> iter = mDataChangeTracker.getChangedDataIds().iterator();
			while (iter.hasNext()) {
				changedIdsStr.append(iter.next());
				if (iter.hasNext()) changedIdsStr.append(", ");
			}
			Log.d(TAG, "Notify data changed [" + changedIdsStr + "]");
		}
	}
	
	/**
	 * It's banned to modify data while {@link #notifyDataSetChanged()} method
	 * is being executed. This is design pattern which prevents infinite loops
	 * or very complicated event chains.
	 */
	protected void assertNotifyIsNotActive() {
		if (mNofityActive) {
			throw new RuntimeException("Data cannot be changed " +
					"when notifyDataSetChanged() is being executed.");
		}
	}
	
}
