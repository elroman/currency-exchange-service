package com.elroman.ces.models;

import com.google.gson.Gson;
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
	@Column(name = "source_name", nullable = false, length = 50)
	private String sourceName;

	@Column(name = "source_url", nullable = false, length = 150)
	private String sourceUrl;

	@Column(name = "isActive")
	private Boolean active;

	public RateSource() {
	}

	public RateSource(String sourceName, String sourceUrl, Boolean active) {
		this.sourceName = sourceName;
		this.sourceUrl = sourceUrl;
		this.active = active;
	}

	public String getId() {
		return id;
	}

	public String getSourceName() {
		return sourceName;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}