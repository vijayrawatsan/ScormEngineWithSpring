package in.xebia.poc.impl;

import in.xebia.poc.api.GenericDao;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class GenericDaoImpl<K, E> implements GenericDao<K, E> {

	private Class<E> entityClass;

	
	private EntityManager entityManager;
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@SuppressWarnings("unchecked")
	public GenericDaoImpl() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
	}

	public void persist(E entity) {
		entityManager.persist(entity);

	}

	public void remove(E entity) {
		entityManager.remove(entity);

	}

	public E findById(K id) {
		return entityManager.find(entityClass, id);
	}
	
	public E merge(E entity) {
		return entityManager.merge(entity);
	}
	
	@SuppressWarnings("unchecked")
	public List<E> findAll() {
		return entityManager.createQuery("Select e From " + entityClass.getCanonicalName() + " e").getResultList();
	}
	
	protected EntityManager getEntityManager() {
		return entityManager;
	}
}
