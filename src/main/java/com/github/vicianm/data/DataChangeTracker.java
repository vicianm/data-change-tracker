package com.github.vicianm.data;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @see #addChange(Object)
 * @see #removeChange(Object)
 */
// TODO this class should not be public
public class DataChangeTracker {
	
	protected Set<Object> mChangedDataIds;
	
	protected DataChangeEventDispatcher mEventDispatcher;

	public DataChangeTracker() {
		// TODO write test that DCT returns changed data in the same order in which the data have been changed
		mChangedDataIds = new LinkedHashSet<Object>();
	}
	
	public void setDataChangeEventDispatcher(DataChangeEventDispatcher eventDispatcher) {
		mEventDispatcher = eventDispatcher;
	}
	
	// TODO this method should be protected
	public void addChange(Object changedDataId) {
		mEventDispatcher.assertNotifyIsNotActive();
		mChangedDataIds.add(changedDataId);
	}
	
	protected void removeChange(Object changedDataId) {
		mEventDispatcher.assertNotifyIsNotActive();
		mChangedDataIds.remove(changedDataId);
	}
	
	protected boolean isDataChanged(Object dataId) {
		return mChangedDataIds.contains(dataId);
	}
	
	protected Set<Object> getChangedDataIds() {
		return mChangedDataIds;
	}
	
	protected void clearDataChanges() {
		mChangedDataIds.clear();
	}
	
}
