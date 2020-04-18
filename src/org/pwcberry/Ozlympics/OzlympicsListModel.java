package org.pwcberry.Ozlympics;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 * List model class for use with the Ozlympics application.
 */
public class OzlympicsListModel<T> implements ListModel {
	private ArrayList<T> data;

	/**
	 * Construct a model based on an array of type T.
	 */
	public OzlympicsListModel(T[] data) {
		this.data = new ArrayList<T>();
		for (T item : data) {
			this.data.add(item);
		}
	}

	/**
	 * Construct a model based on a List of type T.
	 */
	public OzlympicsListModel(List<T> data) {
		this.data = new ArrayList<T>();
		this.data.addAll(data);
	}

	/**
	 * Retrieve the object at the specified index.
	 */
	public Object getElementAt(int index) {
		return data.get(index);
	}

	/**
	 * Returns the number of items contained by the view model.
	 */
	public int getSize() {
		return data.size();
	}

	public int indexOf(T item) {
		return data.indexOf(item);
	}

	public void addListDataListener(ListDataListener listener) {
		// This method allows a listener to be notified when an item
		// has been added to the model.
		// This is not required for the application.
	}

	public void removeListDataListener(ListDataListener listener) {
		// This method allows a listener to be notified when an item
		// has been removed to the model.
		// This is not required for the application.
	}
}
