package com.ptsisi.common.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import com.ptsisi.common.Entity;
import org.hibernate.annotations.GenericGenerator;

import com.ptsisi.common.predicate.ValidEntityKeyPredicate;

@MappedSuperclass
public abstract class IntegerIdObject implements Entity {

	private static final long serialVersionUID = -6579548329158743153L;

	@NotNull
	@Id
	@GenericGenerator(name = "daily_custom", strategy = "com.ptsisi.hibernate.TableSeqGenerator")
	@GeneratedValue(generator = "daily_custom")
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isPersisted() {
		return ValidEntityKeyPredicate.Instance.evaluate(id);
	}

	public boolean isTransient() {
		return !ValidEntityKeyPredicate.Instance.evaluate(id);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return (null == id) ? 629 : this.id.hashCode();
	}

	/**
	 * <p>
	 * 比较id,如果任一方id是null,则不相等
	 * </p>
	 * 由于业务对象被CGlib或者javassist增强的原因，这里只提供一般的基于id的比较,不提供基于Class的比较。<br>
	 * 如果在存在继承结构， 请重置equals方法。
	 *
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(final Object object) {
		if (this == object) return true;
		if (!(object instanceof IntegerIdObject)) {
			return false;
		}
		IntegerIdObject rhs = (IntegerIdObject) object;
		if (null == getId() || null == rhs.getId()) {
			return false;
		}
		return getId().equals(rhs.getId());
	}
}
