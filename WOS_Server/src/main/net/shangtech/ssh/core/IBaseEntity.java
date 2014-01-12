package net.shangtech.ssh.core;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

/**
 * �ļ����� IBaseEntity.java<br/>
 * ���ߣ� �����<br/>
 * �汾�� 2014-1-12 ����10:32:22 v1.0<br/>
 * ���ڣ� 2014-1-12<br/>
 * ��������������ΪInteger��ʵ�常��
 */
@MappedSuperclass
public class IBaseEntity implements Serializable {
	/**
	 * serialVersionUID:TODO(��һ�仰�������������ʾʲô)
	 * @since v1.0
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}

  	