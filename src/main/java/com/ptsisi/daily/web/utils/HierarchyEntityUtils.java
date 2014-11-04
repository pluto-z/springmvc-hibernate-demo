package com.ptsisi.daily.web.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ptsisi.common.HierarchyEntity;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.*;

/**
 * Created by zhaoding on 14-11-3.
 */
public final class HierarchyEntityUtils {

  /**
   * 得到给定节点的所有家族结点，包括自身
   * 
   * @param root
   *          指定根节点
   * @param <T>
   *          a T object.
   * @return 包含自身的家族节点集合
   */
  public static <T extends HierarchyEntity<T>> Set<T> getFamily(T root) {
    Set<T> nodes = Sets.newHashSet();
    nodes.add(root);
    loadChildren(root, nodes);
    return nodes;
  }

  /**
   * 加载字节点
   * 
   * @param node
   * @param children
   */
  private static <T extends HierarchyEntity<T>> void loadChildren(T node, Set<T> children) {
    if (null == node.getChildren()) { return; }
    for (T one : node.getChildren()) {
      children.add(one);
      loadChildren(one, children);
    }
  }

  /**
   * 按照上下关系排序
   * 
   * @param datas
   *          a {@link java.util.List} object.
   * @param <T>
   *          a T object.
   * @return a {@link java.util.Map} object.
   */
  public static <T extends HierarchyEntity<T>> Map<T, String> sort(List<T> datas) {
    return sort(datas, null);
  }

  /**
   * 按照上下关系和指定属性排序
   * 
   * @param datas
   *          a {@link java.util.List} object.
   * @param property
   *          a {@link java.lang.String} object.
   * @param <T>
   *          a T object.
   * @return a {@link java.util.Map} object.
   */
  public static <T extends HierarchyEntity<T>> Map<T, String> sort(List<T> datas, String property) {
    final Map<T, String> sortedMap = tag(datas, property);
    Collections.sort(datas, new Comparator<HierarchyEntity<T>>() {
      public int compare(HierarchyEntity<T> arg0, HierarchyEntity<T> arg1) {
        String tag0 = sortedMap.get(arg0);
        String tag1 = sortedMap.get(arg1);
        return tag0.compareTo(tag1);
      }
    });
    return sortedMap;
  }

  /**
   * <p>
   * tag.
   * </p>
   * 
   * @param datas
   *          a {@link java.util.List} object.
   * @param property
   *          a {@link java.lang.String} object.
   * @param <T>
   *          a T object.
   * @return a {@link java.util.Map} object.
   */
  public static <T extends HierarchyEntity<T>> Map<T, String> tag(List<T> datas, String property) {
    final Map<T, String> sortedMap = Maps.newHashMap();
    for (T de : datas) {
      String myId = null;
      if (null == property) {
        myId = String.valueOf(de.getId());
      } else {
        try {
          myId = String.valueOf(PropertyUtils.getProperty(de, property));
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
      myId = myId + "_";
      if (null != de.getParent() && sortedMap.containsKey(de.getParent())) {
        myId = String.valueOf(sortedMap.get(de.getParent()) + myId);
        if (!myId.endsWith("_")) {
          myId += "_";
        }
      }
      updatedTagFor(myId, de, sortedMap);
      sortedMap.put(de, myId);
    }
    for (T de : datas) {
      String tag = sortedMap.get(de);
      if (tag.endsWith("_")) {
        sortedMap.put(de, tag.substring(0, tag.length() - 1));
      }
    }
    return sortedMap;
  }

  private static <T extends HierarchyEntity<T>> void updatedTagFor(String prefix, T root, Map<T, String> sortedMap) {
    for (T child : root.getChildren()) {
      if (sortedMap.containsKey(child)) {
        sortedMap.put(child, prefix + sortedMap.get(child));
        updatedTagFor(prefix, child, sortedMap);
      }
    }
  }

  /**
   * <p>
   * getRoots.
   * </p>
   * 
   * @param nodes
   *          a {@link java.util.List} object.
   * @param <T>
   *          a T object.
   * @return a {@link java.util.List} object.
   */
  public static <T extends HierarchyEntity<T>> List<T> getRoots(final List<T> nodes) {
    List<T> roots = Lists.newArrayList();
    for (T m : nodes)
      if (null == m.getParent() || !nodes.contains(m.getParent())) roots.add(m);
    return roots;
  }

  /**
   * <p>
   * Get the path from current node to root. First element is current and last is root.
   * </p>
   * 
   * @param node
   *          current node
   * @return a {@link java.util.List} object.
   */
  public static <T extends HierarchyEntity<T>> List<T> getPath(final T node) {
    List<T> path = Lists.newArrayList();
    T curNode = node;
    while (null != curNode && !path.contains(curNode)) {
      path.add(0, curNode);
      curNode = curNode.getParent();
    }
    return path;
  }

  /**
   * <p>
   * addParent.
   * </p>
   * 
   * @param nodes
   *          a {@link java.util.Collection} object.
   * @param <T>
   *          a T object.
   */
  public static <T extends HierarchyEntity<T>> void addParent(Collection<T> nodes) {
    addParent(nodes, null);
  }

  /**
   * <p>
   * addParent.
   * </p>
   * 
   * @param nodes
   *          a {@link java.util.Collection} object.
   * @param toRoot
   *          a T object.
   * @param <T>
   *          a T object.
   */
  public static <T extends HierarchyEntity<T>> void addParent(Collection<T> nodes, T toRoot) {
    Set<T> parents = Sets.newHashSet();
    for (T node : nodes) {
      while (null != node.getParent() && !parents.contains(node.getParent())
          && !Objects.equals(node.getParent(), toRoot)) {
        parents.add(node.getParent());
        node = node.getParent();
      }
    }
    nodes.addAll(parents);
  }
}
