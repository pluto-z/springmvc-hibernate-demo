package com.ptsisi.common.model;

import com.ptsisi.common.TimeEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.Date;

@MappedSuperclass
public abstract class IntegerIdTimeObject extends IntegerIdObject implements TimeEntity {

	private static final long serialVersionUID = -3494716518594445433L;

	@NotNull
	@Column(name = "created_at", nullable = false)
	private Date createdAt;

	@NotNull
	@Column(name = "updated_at", nullable = false)
	private Date updatedAt;

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
