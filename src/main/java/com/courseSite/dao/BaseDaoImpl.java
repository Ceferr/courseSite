package com.courseSite.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public abstract class BaseDaoImpl<T> implements BaseDao<T>{


    private Class<T> clazz;
    @Resource(name = "sessionFactory")
    protected SessionFactory sessionFactory;

    public BaseDaoImpl(){
        super();
        this.clazz = null;
        this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Session getCurrentSession() {
        return this.sessionFactory.openSession();
    }

    @Override
    public T load(Serializable id) {
        return (T)this.getCurrentSession().load(clazz,id);
    }

    @Override
    public T get(Serializable id) {
//        return (T)this.getCurrentSession().get(clazz,id);
        String className = clazz.getName();
        String hql = "From "+className+" where id = :id";
        Map map = new HashMap();
        map.put("id",id);
        Query query = getQuery(hql,map);
        T t = (T) query.uniqueResult();
        return t;
    }

    @Override
    public T get(String storePath) {
        String className = clazz.getName();
        String hql = "From "+className+" where path = :path";
        Map map = new HashMap();
        map.put("path",storePath);
        Query query = getQuery(hql,map);
        T t = (T) query.uniqueResult();
        return t;
    }

    @Override
    public List<T> get(Object ID, String IDtype) {
        String className = clazz.getName();
        String hql = "From "+className+" where "+IDtype+" = :"+IDtype;
        Map map = new HashMap();
        map.put(IDtype,ID);
        Query query = getQuery(hql,map);
        List lists = query.list();
        return lists;
    }

    @Override
    public T getOne(Object ID, String IDtype) {
        String className = clazz.getName();
        String hql = "From "+className+" where "+IDtype+" = :"+IDtype;
        Map map = new HashMap();
        map.put(IDtype,ID);
        Query query = getQuery(hql,map);
        T t = (T)query.uniqueResult();
        return t;
    }

    @Override
    public Long getCount() {
        String className = clazz.getName();
        String hql = "select count(*) From "+className;
        Query query = this.getCurrentSession().createQuery(hql);
        Long count = (Long) query.uniqueResult();
        return count;
    }

    /*
    * 查询所有记录
    * */
    @Override
    public List<T> findAll(String IDtype) {
        String className = clazz.getName();
        String hql = "From "+className+" x order by x."+IDtype+" asc";
        Query query = this.getCurrentSession().createQuery(hql);
        return query.list();
    }

    @Override
    public List<T> findAllByPage(String IDtype, Integer start, Integer size) {
        String className = clazz.getName();
        String hql = "From "+className+" x order by x."+IDtype+" asc";
        Query query = this.getCurrentSession().createQuery(hql);
        query.setFirstResult(start).setMaxResults(size);
        return query.list();
    }

    @Override
    public List<T> findByPage(String IDtype,Long ID, Integer start, Integer size) {
        String className = clazz.getName();
        String hql = "From "+className+" where "+IDtype+" = :"+IDtype+" order by studentID asc";
        Map map = new HashMap();
        map.put(IDtype,ID);
        Query query = getQuery(hql,map);
        query.setFirstResult(start).setMaxResults(size);
        return query.list();
    }

    @Override
    public void persist(T entity) {
        getCurrentSession().persist(entity);
    }

    @Override
    public Serializable save(T entity) {
        return this.getCurrentSession().save(entity);
    }

    @Override
    public T merge(T entity) {
        return (T) this.getCurrentSession().merge(entity);
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
    public void delete(final Serializable id) {
        String className = clazz.getName();
        if (className != null && className != "") {
            String hql = "delete "+className+" where id = :id";
            Map map = new HashMap();
            map.put("id",id);
            Query query = getQuery(hql,map);
            query.executeUpdate();
        }
    }

    @Override
    public void delete(String storePath) {
        //String tableName = DaoUtil.getTableName(clazz);
        String className = clazz.getName();
        if (className != null && className != "") {
            String hql = "delete "+className+" where path = :path";
            Map map = new HashMap();
            map.put("path",storePath);
            Query query = getQuery(hql,map);
            query.executeUpdate();
        }
    }

    @Override
    public void deleteRecord(Long studentID) {
        String className = clazz.getName();
        if (className != null && className != "") {
            String hql = "delete "+className+" where studentID = :studentID";
            Map map = new HashMap();
            map.put("studentID",studentID);
            Query query = getQuery(hql,map);
            query.executeUpdate();
        }
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

//    public String getTableName(){
//        Annotation[] annotations = clazz.getAnnotations();
//        String tableName = "";
//        for (Annotation annotation : annotations) {
//            if (annotation instanceof Table) {
//                tableName = ((Table) annotation).name();
//            }
//        }
//        tableName = tableName.substring(0, 1).toUpperCase() + tableName.substring(1);
//        return tableName;
//    }
}
