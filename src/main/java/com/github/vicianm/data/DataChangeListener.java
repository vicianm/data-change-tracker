package com.github.vicianm.data;

import java.util.Set;

/**
 * <p>Listener of data change events.</p>
 * 
 * <p>Typical usage:</p>
 * <p><blockquote><pre>
 *    public void onCreate() {
 *        AccTxData.addListener(this,
 *                AccTxData.ACC_ENTRIES,
 *                AccTxData.CHECKED_ACC_ENTRY_INDICES);
 *    }
 *    
 *    public void onDestroy() {
 *        AccTxData.removeListener(this);
 *    }
 *    
 *    // Its banned to modify *Data objects here!
 *    public void dataChanged(Map<Object, Object> data) {
 *        
 *        List newEntries = data.get(AccTxData.ACC_ENTRIES);
 *        if (newEntries != null) {
 *            entries = newEntries;
 *            nofityDataSetChanged();
 *        }
 *        
 *        Integer selection = data.get(AccTxData.CHECKED_ACC_ENTRY_INDICES);
 *        if (selection != null) {
 *            mSelection = selection;
 *            notifyDataSelectionChanged();
 *        }
 *        
 *    }
 * </pre></blockquote>
 */
public interface DataChangeListener {
    
    /**
     * Called to notify that one or more data objects has changed.
     * <p><b>WARNING:</b> It is banned to modify data objects in this method.</p>
     * @param changedDataIds IDs of changed data. This is never <code>null</code>.
     */
    void dataChanged(Set<Object> changedDataIds);
    
}
