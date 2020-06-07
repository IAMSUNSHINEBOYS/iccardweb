package com.alen.mapper;

import java.io.Serializable;
import java.util.List;

public interface DaoMapper<T,ID extends Serializable>{

    /**
     *  通过id查询
     */
    T getById(Serializable id);

    /**
     * 通过对象查询
     */
    T get(T t);

    /**
     * 获取全部对象
     *
     * @param <T>
     * @param entityClass
     * @return
     */
    List<T> getList(T t);

    List<T> getPageList(T t);

    /**
     * 新增对象
     *
     * @param o
     */
    int insert(T o);

    /**
     * 修改对象
     *
     * @param o
     */
    void update(T o);

    /**
     * 修改对象
     *
     * @param o
     */
    void updateByID(T o);

    /**
     * 删除对象
     *
     * @param o
     */
    int delete(T o);

    /**
     * 根据ID删除对象
     *
     * @param <T>
     * @param entityClass
     * @param id
     */
    int deleteByIds(Serializable... id);

}
