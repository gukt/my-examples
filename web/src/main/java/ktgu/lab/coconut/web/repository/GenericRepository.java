package ktgu.lab.coconut.web.repository;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

import javax.validation.Valid;

public interface GenericRepository<T, TKey extends Serializable> {

	List<T> findAll();

	List<T> findAll(String queryString, Object... queryArgs);

	List<T> findAll(String queryString, Object[] queryArgs, Integer firstResult, Integer maxResult);

	T findById(String queryString, Object... queryArgs);

	T findById(TKey id);

	T load(TKey id);

	int executeNonQuery(String queryString, Object... queryArgs);

	Object executeScalar(String queryString, Object... queryArgs);

	Long count();

	Long count(String queryString, Object... queryArgs);

	boolean delete(TKey id);

	void delete(T entity);

	/**
	 * 新增或更新指定的entity对象,该方法内部是简单的调用{@link Session#saveOrUpdate(Object)}来提供实现的
	 */
	T save(T entity);

}
