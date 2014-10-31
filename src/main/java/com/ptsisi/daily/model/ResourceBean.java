package com.ptsisi.daily.model;

import com.ptsisi.common.model.IntegerIdObject;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="com.ptsisi.daily.Resource")
@Table(name = "SYS_RESOURCES")
public class ResourceBean extends IntegerIdObject implements com.ptsisi.daily.Resource {

	private static final long serialVersionUID = 2436223935048921152L;

	@NotBlank
	@Length(max = 50)
	private String name;

	@Length(max = 100)
	private String permission;

	@NotBlank
	@Length(max = 50)
	@Column(unique = true)
	private String value;

	@Override public String getName() {
		return name;
	}

	@Override public void setName(String name) {
		this.name = name;
	}

	@Override public String getPermission() {
		return permission;
	}

	@Override public void setPermission(String permission) {
		this.permission = permission;
	}

	@Override public String getValue() {
		return value;
	}

	@Override public void setValue(String value) {
		this.value = value;
	}
}
