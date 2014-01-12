package net.shangtech.ssh.core;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * �ļ����� BaseDao.java<br/>
 * ���ߣ� �����<br/>
 * �汾�� 2014-1-12 ����10:59:15 v1.0<br/>
 * ���ڣ� 2014-1-12<br/>
 * ���������ݷ��ʸ��࣬���в������������hibernateTemplate
 */
@SuppressWarnings("unchecked")
public class BaseDao<T> extends HibernateDaoSupport {
	/**
	 * ���ߣ� �����<br/>
	 * �汾�� 2014-1-12 ����11:40:23 v1.0<br/>
	 * ���ڣ� 2014-1-12<br/>
	 * @param entity<br/>
	 * ��������Ӽ�¼
	 */
	public void insert(T entity) {
		this.getHibernateTemplate().save(entity);
	}
	
	/**
	 * ���ߣ� �����<br/>
	 * �汾�� 2014-1-12 ����11:40:44 v1.0<br/>
	 * ���ڣ� 2014-1-12<br/>
	 * @param id<br/>
	 * ������ɾ����¼
	 */
	public void delete(long id) {
		this.getHibernateTemplate().delete(this.find(id));
	}
	
	/**
	 * ���ߣ� �����<br/>
	 * �汾�� 2014-1-12 ����11:40:55 v1.0<br/>
	 * ���ڣ� 2014-1-12<br/>
	 * @param obj<br/>
	 * ���������¼�¼
	 */
	public void update(Object obj) {
		this.getHibernateTemplate().merge(obj);
	}
	
	/**
	 * ���ߣ� �����<br/>
	 * �汾�� 2014-1-12 ����11:41:07 v1.0<br/>
	 * ���ڣ� 2014-1-12<br/>
	 * @param id<br/>
	 * @return<br/>
	 * �����������������Ҽ�¼
	 */
	public T find(long id) {
		T obj = null;
		obj = (T) this.getHibernateTemplate().get(getEntityClass(), id);
		return obj;
	}
	
	/**
	 * ���ߣ� �����<br/>
	 * �汾�� 2014-1-12 ����11:41:23 v1.0<br/>
	 * ���ڣ� 2014-1-12<br/>
	 * @param orderBy �������,��"order by sort desc",�����������Ӧ��ʹ��findAll()����<br/>
	 * @return<br/>
	 * �������������м�¼,ʹ��orderBy��������
	 */
	public List<T> findAll(String orderBy){
		String queryString = "from " + getEntityClass().getSimpleName();
		if (StringUtils.isNotBlank(orderBy)) {
			queryString += " order by " + orderBy;
		}
		return this.getHibernateTemplate().find(queryString);
	}
	
	/**
	 * ���ߣ� �����<br/>
	 * �汾�� 2014-1-12 ����11:41:23 v1.0<br/>
	 * ���ڣ� 2014-1-12<br/>
	 * @return<br/>
	 * �������������м�¼
	 */
	public List<T> findAll(){
		return findAll(null);
	}
	
	/**
	 * ���ߣ� �����<br/>
	 * �汾�� 2014-1-12 ����11:44:43 v1.0<br/>
	 * ���ڣ� 2014-1-12<br/>
	 * @param start ��ҳ�ӵڼ�����¼��ʼ<br/>
	 * @param limit ��ҳ��༸����¼<br/>
	 * @param orderBy	�������,��"order by sort desc"<br/>
	 * @return<br/>
	 * �������������м�¼����ҳ��ʾ
	 */
	public List<T> findAll(final int start, final int limit, String orderBy){
		String queryString = "from " + getEntityClass().getSimpleName();
		if (StringUtils.isNotBlank(orderBy)) {
			queryString += " order by " + orderBy;
		}
		final String hql = queryString;
		return super.getHibernateTemplate().executeFind(new HibernateCallback<List<T>>(){
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult(start)
					.setMaxResults(limit);
				return query.list();
			}
		});
	}
	
	/**
	 * ���ߣ� �����<br/>
	 * �汾�� 2014-1-12 ����2:24:50 v1.0<br/>
	 * ���ڣ� 2014-1-12<br/>
	 * @param start ���ҳ�ӵڼ�����¼��ʼ<br/>
	 * @param limit ���ҳ��༸����¼<br/>
	 * @return<br/>
	 * ������
	 */
	public List<T> findAll(final int start, final int limit){
		return findAll(start, limit, null);
	}
	
	/**
	 * ���ߣ� �����<br/>
	 * �汾�� 2014-1-12 ����2:25:50 v1.0<br/>
	 * ���ڣ� 2014-1-12<br/>
	 * @param hql ��ѯ�ַ���,where��ͷ<br/>
	 * @param values ��ѯ����ֵ<br/>
	 * @return<br/>
	 * ������
	 */
	public List<T> find(String hql, Object...values){
		String queryString = "from " + getEntityClass().getSimpleName() + " o " + hql;
		return super.getHibernateTemplate().find(queryString, values);
	}
	
	/**
	 * ���ߣ� �����<br/>
	 * �汾�� 2014-1-12 ����2:26:47 v1.0<br/>
	 * ���ڣ� 2014-1-12<br/>
	 * @param start ���ҳ�ӵڼ�����¼��ʼ<br/>
	 * @param limit ���ҳ������м�����¼<br/>
	 * @param hql ��ѯ�ַ���,where��ͷ<br/>
	 * @param values ��ѯ����ֵ<br/>
	 * @return<br/>
	 * ������
	 */
	public List<T> find(final int start, final int limit, String hql, final Object...values){
		final String queryString = "from " + getEntityClass().getSimpleName() + " o " + hql;
		return super.getHibernateTemplate().executeFind(new HibernateCallback<List<T>>(){
			@Override
			public List<T> doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(queryString);
				if(values != null && values.length > 0){
					for(int i = 0; i < values.length; i++){
						query.setParameter(i+1, values[i]);
					}
				}
				query.setFirstResult(start);
				query.setMaxResults(limit);
				return query.list();
			}
		});
	}
	
	/**
	 * ���ߣ� �����<br/>
	 * �汾�� 2014-1-12 ����2:27:49 v1.0<br/>
	 * ���ڣ� 2014-1-12<br/>
	 * @param queryString �����Ĳ�ѯ�ַ���<br/>
	 * @param values ��ѯ����ֵ<br/>
	 * @return<br/>
	 * �������ò�ѯΪ�ۼ���ѯ,���ز�ѯ�������һ����¼,��ѯ�ַ���Ϊ�����Ĳ�ѯ
	 */
	public Object gather(String queryString, Object...values){
		List<Object> list = super.getHibernateTemplate().find(queryString, values);
		if(list != null && !list.isEmpty())
			return list.get(0);
		return null;
	}
	
	/**
	 * ���ߣ� �����<br/>
	 * �汾�� 2014-1-12 ����2:30:06 v1.0<br/>
	 * ���ڣ� 2014-1-12<br/>
	 * @return<br/>
	 * ������ͳ��ĳ�ű��ܵļ�¼��
	 */
	public long countAll(){
		String queryString = "select count(o) from " + getEntityClass().getSimpleName() + " o ";
		Object count = gather(queryString);
		if(count != null)
			return (Long) count;
		return 0L;
	}
	
	/**
	 * ���ߣ� �����<br/>
	 * �汾�� 2014-1-12 ����2:30:54 v1.0<br/>
	 * ���ڣ� 2014-1-12<br/>
	 * @param hql where��ͷ�Ĳ�ѯ�ַ���<br/>
	 * @param values hql�еĲ���ֵ<br/>
	 * @return<br/>
	 * ��������ѯ���������ļ�¼����
	 */
	public long count(String hql, Object...values){
		String queryString = "select count(o) from " + getEntityClass().getSimpleName() + " o " + hql;
		Object count = gather(queryString, values);
		if(count != null)
			return (Long) count;
		return 0L;
	}
	
	/**
	 * ���ߣ� �����<br/>
	 * �汾�� 2014-1-12 ����5:43:01 v1.0<br/>
	 * ���ڣ� 2014-1-12<br/>
	 * @param queryString �����Ĳ�ѯ���,��update o from Table o set o.xx=? where condition<br/>
	 * @param values<br/>
	 * ������ִ��ָ�����,һ����������������
	 */
	public void execute(String queryString, Object...values){
		super.getHibernateTemplate().bulkUpdate(queryString, values);
	}
	
	/**
	 * ���ߣ� �����<br/>
	 * �汾�� 2014-1-12 ����2:31:51 v1.0<br/>
	 * ���ڣ� 2014-1-12<br/>
	 * @return<br/>
	 * ��������ȡʵ��T������
	 */
	private Class<?> getEntityClass() {
		Class<?> entityClass = (Class<?>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		return entityClass;
	}
	
	/**
	 * ���ߣ� �����<br/>
	 * �汾�� 2014-1-12 ����2:32:14 v1.0<br/>
	 * ���ڣ� 2014-1-12<br/>
	 * @param sessionFactory<br/>
	 * ������ʵ��sessionFactory�Զ�ע��
	 */
	@Autowired
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
}

  	