package com.courseSite.dao;


import org.hibernate.Query;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDao <T>{
    Session getCurrentSession();

    T load(Serializable id);

    T get(Serializable id);

    T get(String storePath);

    List get(Long ID,String IDtype);

    List<T> findAll();

    void persist(T entity);

    Serializable save(T entity);

    T merge(T entity);

    void saveOrUpdate(T entity);

    void update(T entity);

    void delete(T entity);

    void delete(final Serializable id);

    void delete(String storePath);

    void delete(Long studentID);

    void flush();

    Query getQuery(String hql, Map<String,Object> map);
}
