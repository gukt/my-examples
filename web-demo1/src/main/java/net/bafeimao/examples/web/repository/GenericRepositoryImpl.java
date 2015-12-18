package net.bafeimao.examples.web.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import net.bafeimao.examples.web.domain.BaseEntity;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class GenericRepositoryImpl<T extends BaseEntity<TKey>, TKey extends Serializable> implements
		GenericRepository<T, TKey> {

	private static final Logger LOGGER = LoggerFactory.getLogger(GenericRepositoryImpl.class);

	@Autowired
	protected SessionFactory sessionFactory;

	protected Class<T> entityClass;

	private String entityName;

	@SuppressWarnings("unchecked")
	public GenericRepositoryImpl() {
		entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		if (entityClass != null) {
			entityName = entityClass.getSimpleName();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		List<T> list = null;
		Query query = getSession().createQuery("from " + entityName);
		list = query.list();
		return list;
	}

	@Override
	public List<T> findAll(String queryString, Object... queryArgs) {
		return findAll(queryString, queryArgs, null, null);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll(String queryString, Object[] queryArgs, Integer firstResult, Integer maxResults) {
		Query query = prepareQuery(queryString, queryArgs);

		if (firstResult != null && firstResult > 0)
			query.setFirstResult(firstResult);

		if (maxResults != null && maxResults > 0)
			query.setMaxResults(maxResults);

		List<T> entities = query.list();
		return entities;
	}

	public T findById(String queryString, Object... queryArgs) {
		List<T> entities = findAll(queryString, queryArgs, 0, 1);
		return entities == null || entities.size() == 0 ? null : entities.iterator().next();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T findById(TKey id) {
		return (T) getSession().get(entityClass, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T load(TKey id) {
		return (T) getSession().load(entityClass, id);
	}

	@Override
	public boolean delete(TKey id) {
		int result = this.executeNonQuery("delete " + entityName + " where id=?", id);
		return result != 0;
	}

	@Override
	public void delete(T entity) {
		getSession().delete(entity);
	}

	@Override
	public T save( T entity) {
		getSession().saveOrUpdate(entity);
		return entity;
	}

	@Override
	public Long count() {
		return this.count("select count(*) from " + this.entityName);
	}

	@Override
	public Long count(String queryString, Object... queryArgs) {
		queryString = queryString.trim();
		if (!queryString.contains("select") && queryString.startsWith("where")) {
			queryString = "select count(*) from " + this.entityName + " " + queryString;
		}
		return (Long) executeScalar(queryString, queryArgs);
	}

	@Override
	public int executeNonQuery(String queryString, Object... queryArgs) {
		Query query = getSession().createQuery(queryString);
		bindParamters(query, queryArgs);
		return query.executeUpdate();
	}

	@Override
	public Object executeScalar(String queryString, Object... queryArgs) {
		Query query = this.prepareQuery(queryString, queryArgs);
		Object result = query.uniqueResult();
		return result;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	// ============ PRIVATE METHODS ============

	private Query prepareQuery(String queryString, Object... queryArgs) {
		if (queryString == null)
			queryString = "";

		String tmpString = queryString.toLowerCase().trim();

		// 如果QueryString里面没有提供where关键字的话,会自动识别在必要时补上where关键字
		//
		// 以下两种情况不要自动加where关键字:
		// 1. 空字符串情况不要加where(用于查找所有记录并且不带任何where条件)
		// 2. 查询表达式中带有from关键字的情况
		//
		if (tmpString.length() > 0 && !tmpString.contains("from") && !tmpString.contains("where"))
			queryString = " where " + queryString;

		// 自动为"省略的查询字符串"前加上"from {EntityName}"(如果写查询的家伙偷懒没有写的话)
		if (!tmpString.contains("from"))
			queryString = "from " + entityName + " " + queryString;

		LOGGER.info("prepareQuery: {}", queryString);

		Query query = getSession().createQuery(queryString);
		bindParamters(query, queryArgs);
		return query;
	}

	private void bindParamters(Query query, Object[] params) {
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
	}
}
