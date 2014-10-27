package com.ptsisi.hibernate;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by zhaoding on 14-10-27.
 */
public class SinglePage<E> implements List<E> {

	private int pageNo;

	private int pageSize;

	private Long total;

	private List<E> items;

	/**
	 * <p>
	 * Constructor for SinglePage.
	 * </p>
	 */
	public SinglePage() {
		super();
	}

	/**
	 * <p>
	 * Constructor for SinglePage.
	 * </p>
	 *
	 * @param pageNo a int.
	 * @param pageSize a int.
	 * @param total a int.
	 * @param dataItems a {@link java.util.List} object.
	 */
	public SinglePage(int pageNo, int pageSize, Long total, List<E> dataItems) {
		this.items = dataItems;
		if (pageSize < 1) {
			this.pageSize = 2;
		} else {
			this.pageSize = pageSize;
		}
		if (pageNo < 1) {
			this.pageNo = 1;
		} else {
			this.pageNo = pageNo;
		}
		this.total = total;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<E> getItems() {
		return items;
	}

	public void setItems(List<E> items) {
		this.items = items;
	}

	@Override public int size() {
		return items.size();
	}

	@Override public boolean isEmpty() {
		return items.isEmpty();
	}

	@Override public boolean contains(Object o) {
		return items.contains(o);
	}

	@Override public Iterator<E> iterator() {
		return items.iterator();
	}

	@Override public Object[] toArray() {
		return items.toArray();
	}

	@Override public <T> T[] toArray(T[] a) {
		return items.toArray(a);
	}

	@Override public boolean add(E e) {
		throw new RuntimeException("unsupported retailAll");
	}

	@Override public boolean remove(Object o) {
		throw new RuntimeException("unsupported retailAll");
	}

	@Override public boolean containsAll(Collection<?> c) {
		return items.containsAll(c);
	}

	@Override public boolean addAll(Collection<? extends E> c) {
		throw new RuntimeException("unsupported retailAll");
	}

	@Override public boolean addAll(int index, Collection<? extends E> c) {
		throw new RuntimeException("unsupported retailAll");
	}

	@Override public boolean removeAll(Collection<?> c) {
		throw new RuntimeException("unsupported retailAll");
	}

	@Override public boolean retainAll(Collection<?> c) {
		throw new RuntimeException("unsupported retailAll");
	}

	@Override public void clear() {
		throw new RuntimeException("unsupported retailAll");
	}

	@Override public E get(int index) {
		return items.get(index);
	}

	@Override public E set(int index, E element) {
		return items.set(index, element);
	}

	@Override public void add(int index, E element) {
		items.add(index, element);
	}

	@Override public E remove(int index) {
		return items.remove(index);
	}

	@Override public int indexOf(Object o) {
		return items.indexOf(o);
	}

	@Override public int lastIndexOf(Object o) {
		return items.lastIndexOf(o);
	}

	@Override public ListIterator<E> listIterator() {
		return items.listIterator();
	}

	@Override public ListIterator<E> listIterator(int index) {
		return items.listIterator(index);
	}

	@Override public List<E> subList(int fromIndex, int toIndex) {
		return items.subList(fromIndex, toIndex);
	}
}
