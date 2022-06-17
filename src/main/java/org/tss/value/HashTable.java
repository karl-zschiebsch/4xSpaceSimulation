package org.tss.value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Table;

public class HashTable<R, C, V> implements Table<R, C, V>, Serializable {
	private static final long serialVersionUID = 2945543070571394050L;

	private final Set<R> rows;
	private final Set<C> columns;

	private final Map<R, Integer> rowToIndex;
	private final Map<C, Integer> columnToIndex;

	private V[][] array;

	public HashTable(Set<R> rows, Set<C> columns) {
		this.rows = rows;
		this.columns = columns;

		rowToIndex = mapIndex(rows);
		columnToIndex = mapIndex(columns);

		@SuppressWarnings("unchecked")
		V[][] temp = (V[][]) new Object[rows.size()][columns.size()];
		array = temp;
	}

	private <T> Map<T, Integer> mapIndex(Collection<T> c) {
		Map<T, Integer> map = new HashMap<>(c.size());
		int index = 0;
		for (T t : c) {
			map.put(t, index);
			index++;
		}
		return map;
	}

	@Override
	public boolean contains(Object rowKey, Object columnKey) {
		return containsRow(rowKey) && containsColumn(columnKey);
	}

	@Override
	public boolean containsRow(Object rowKey) {
		return rows.contains(rowKey);
	}

	@Override
	public boolean containsColumn(Object columnKey) {
		return columns.contains(columnKey);
	}

	@Override
	public boolean containsValue(Object value) {
		for (V[] vs : array) {
			for (V v : vs) {
				if (v.equals(value)) {
					return true;
				}
			}
		}
		return false;
	}

	public V at(int rowPosition, int columnPosition) {
		return array[rowPosition][columnPosition];
	}

	@Override
	public V get(Object rowKey, Object columnKey) {
		Integer rowPosition = rowToIndex.get(rowKey);
		Integer columnPosition = rowToIndex.get(columnKey);
		return at(rowPosition, columnPosition);
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public int size() {
		return rows.size() * columns.size();
	}

	@Override
	public void clear() {
		Arrays.fill(array, null);
	}

	public V set(int rowPosition, int columnPosition, V value) {
		V old = array[rowPosition][columnPosition];
		array[rowPosition][columnPosition] = value;
		return old;
	}

	@Override
	public V put(R rowKey, C columnKey, V value) {
		Integer rowPosition = rowToIndex.get(rowKey);
		Integer columnPosition = columnToIndex.get(columnKey);
		return set(rowPosition, columnPosition, value);
	}

	@Override
	public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
		for (Cell<? extends R, ? extends C, ? extends V> cell : table.cellSet()) {
			put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
		}
	}

	@Override
	public V remove(Object rowKey, Object columnKey) {
		Integer rowPosition = rowToIndex.get(rowKey);
		Integer columnPosition = columnToIndex.get(columnKey);
		V old = array[rowPosition][columnPosition];
		array[rowPosition][columnPosition] = null;
		return old;
	}

	@Override
	public Map<C, V> row(R rowKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<R, V> column(C columnKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Cell<R, C, V>> cellSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<R> rowKeySet() {
		return rows;
	}

	@Override
	public Set<C> columnKeySet() {
		return columns;
	}

	@Override
	public Collection<V> values() {
		Collection<V> values = new ArrayList<V>(size());
		for (V[] vs : array) {
			for (V v : vs) {
				values.add(v);
			}
		}
		return values;
	}

	@Override
	public Map<R, Map<C, V>> rowMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<C, Map<R, V>> columnMap() {
		// TODO Auto-generated method stub
		return null;
	}

}
