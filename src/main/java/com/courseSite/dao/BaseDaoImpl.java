package com.courseSite.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BaseDaoImpl<T,PK extends Serializable> implements BaseDao<T,PK>{


    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return this.sessionFactory.openSession();
    }

    @Override
    public T load(Class<T> clazz,PK id) {
        return (T)this.getCurrentSession().load(clazz,id);
    }

    @Override
    public T get(Class<T> clazz,PK id) {
        return (T)this.getCurrentSession().get(clazz,id);
    }

    @Override
    public List<T> findAll() {
        return null;
    }

    @Override
    public void persist(T entity) {
        getCurrentSession().persist(entity);
    }

    @Override
    public PK save(T entity) {
        return (PK)this.getCurrentSession().save(entity);
    }

    @Override
    public void saveOrUpdate(T entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public void update(T entity){
        this.getCurrentSession().update(entity);
    }

    @Override
    public void delete(T entity) {
        this.getCurrentSession().delete(entity);
    }

    @Override
    public Query getQuery(String hql, Map<String, Object> map) {
        Query query = getCurrentSession().createQuery(hql);
        // TODO Auto-generated method stub
        if (map != null) {
            Set<String> keySet = map.keySet();
            for (String string : keySet) {
                Object obj = map.get(string);
                //这里考虑传入的参数是什么类型，不同类型使用的方法不同
                if(obj instanceof Collection<?>){
                    query.setParameterList(string, (Collection<?>)obj);
                }else if(obj instanceof Object[]){
                    query.setParameterList(string, (Object[])obj);
                }else{
                    query.setParameter(string, obj);
                }
            }
        }
        return query;
    }

    @Override
    public void flush() {
        getCurrentSession().flush();
    }
}
