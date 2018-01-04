package com.elroman.ces.models;

import com.google.gson.Gson;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "currency")
public class Currency {

	@Id
	@NotNull
	private int code;

	@NotNull
	@Column(name = "alias", nullable = false, length = 3)
	private String alias;

	@NotNull
	@Column(name = "name", nullable = false, length = 50)
	private String name;

	public Currency() {
	}

	public Currency(int code) {
		this.code = code;
	}

	public Currency(int code, String alias, String name) {
		this.code = code;
		this.alias = alias;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getAlias() {
		return alias;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
