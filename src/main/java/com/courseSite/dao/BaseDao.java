package com.courseSite.dao;


import org.hibernate.Query;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDao <T,PK extends Serializable>{
    T load(Class<T> clazz,PK id);

    T get(Class<T> clazz,PK id);

    List<T> findAll();

    void persist(T entity);

    PK save(T entity);

    void saveOrUpdate(T entity);

    void update(T entity);

    void delete(T entity);

    void flush();

    Query getQuery(String hql, Map<String,Object> map);
}
