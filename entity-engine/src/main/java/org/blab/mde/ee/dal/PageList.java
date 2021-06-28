package org.blab.mde.ee.dal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import com.google.common.collect.ArrayListMultimap;

import static org.blab.mde.core.util.Guarder.requireNotEmpty;


public class PageList<V> implements Serializable {

  private int total;

  private int pageSize = 50;

  private int page;

  private int totalPage;

  private ArrayList<V> elements = new ArrayList<>(0);

  public static <V> PageList<V> create() {
    return new PageList<>();
  }

  // proxy list method
  public int size() {
    return getElements().size();
  }

  public boolean isEmpty() {
    return getElements().isEmpty();
  }

  public boolean contains(Object o) {
    return getElements().contains(o);
  }

  public int indexOf(Object o) {
    return getElements().indexOf(o);
  }

  public int lastIndexOf(Object o) {
    return getElements().lastIndexOf(o);
  }

  @SuppressWarnings("NullableProblems")
  public Object[] toArray() {
    return getElements().toArray();
  }

  @SuppressWarnings("NullableProblems")
  public <T> T[] toArray(T[] a) {
    return getElements().toArray(a);
  }

  public V get(int index) {
    return getElements().get(index);
  }

  public V set(int index, V element) {
    return getElements().set(index, element);
  }

  public boolean add(V o) {
    return getElements().add(o);
  }

  public void add(int index, V element) {
    getElements().add(index, element);
  }

  public V remove(int index) {
    return getElements().remove(index);
  }

  public boolean remove(Object o) {
    return getElements().remove(o);
  }

  public void clear() {
    getElements().clear();
  }

  public boolean addAll(Collection<? extends V> c) {
    return getElements().addAll(c);
  }

  public boolean addAll(int index, Collection<? extends V> c) {
    return getElements().addAll(index, c);
  }

  public boolean removeAll(Collection c) {
    return getElements().removeAll(c);
  }

  public boolean retainAll(Collection c) {
    return getElements().retainAll(c);
  }

  public ListIterator<V> listIterator(int index) {
    return getElements().listIterator(index);
  }

  public ListIterator<V> listIterator() {
    return getElements().listIterator();
  }

  public Iterator<V> iterator() {
    return getElements().iterator();
  }

  public List<V> subList(int fromIndex, int toIndex) {
    return getElements().subList(fromIndex, toIndex);
  }

  public void forEach(Consumer<? super V> action) {
    getElements().forEach(action);
  }

  public Spliterator<V> spliterator() {
    return getElements().spliterator();
  }

  public boolean removeIf(Predicate<? super V> filter) {
    return getElements().removeIf(filter);
  }

  public void replaceAll(UnaryOperator<V> operator) {
    getElements().replaceAll(operator);
  }

  public void sort(Comparator<? super V> c) {
    getElements().sort(c);
  }

  public boolean containsAll(Collection c) {
    return getElements().containsAll(c);
  }

  public Stream<V> stream() {
    return getElements().stream();
  }

  public Stream<V> parallelStream() {
    return getElements().parallelStream();
  }

  public void trimToSize() {
    getElements().trimToSize();
  }

  public void ensureCapacity(int minCapacity) {
    getElements().ensureCapacity(minCapacity);
  }

  @SuppressWarnings("unchecked")
  public PageList<V> elements(Collection<?> objects) {
    if (objects instanceof ArrayList) {
      setElements((ArrayList<V>)objects);
    } else {
      getElements().addAll((Collection<? extends V>)objects);
    }

    return this;
  }

  public PageList<V> total(int total) {
    setTotal(total);
    return this;
  }

  public PageList<V> page(int page) {
    setPage(page);
    return this;
  }

  public PageList<V> pageSize(int pageSize) {
    setPageSize(pageSize);
    return this;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {

    this.totalPage = (total + getPageSize() - 1) / getPageSize();
    this.total = total;
  }

  public int getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(int totalPage) {
    this.totalPage = totalPage;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public ArrayList<V> getElements() {
    return elements;
  }

  public void setElements(ArrayList<V> elements) {
    this.elements = elements;
  }

  private ArrayListMultimap<String, Object> attributes = ArrayListMultimap.create();

  public ArrayListMultimap<String, Object> getAttributes() {
    return attributes;
  }

  public void setAttributes(ArrayListMultimap<String, Object> attributes) {
    this.attributes = attributes;
  }

  public void addAttribute(String name, Object... values) {
    requireNotEmpty(values);
    Arrays.stream(values)
        .forEach(it -> getAttributes().put(name, it));
  }

  public List<Object> getAttribute(String name) {
    return getAttributes().get(name);
  }

  public Optional<Object> getFirstAtribute(String name) {
    return getAttributes().get(name).stream().findFirst();
  }
}
