package com.elroman.ces.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "rate_source")
public class RateSource {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "id", unique = true, length = 32)
	private String id;

	@NotNull
	@Column(name = "name", nullable = false, length = 50)
	private String name;

	public RateSource() {
	}

	public RateSource(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "RateSource{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}