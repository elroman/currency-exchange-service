package com.elroman.ces.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "rate")
public class Rate {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "id", unique = true, length = 32)
	private String id;

	@Column(name = "date", nullable = false, length = 50)
	private Date date;

	@ManyToOne(fetch = FetchType.LAZY)
	private Currency currencyFrom;

	@ManyToOne(fetch = FetchType.LAZY)
	private Currency currencyTo;

	@Column(name = "rate", nullable = false)
	private BigDecimal rate;

	@ManyToOne(fetch = FetchType.LAZY)
	private RateSource rateSource;

	public Rate() {
	}

	public Rate(String id) {
		this.id = id;
	}

	public Rate(Date date, Currency currencyFrom, Currency currencyTo, BigDecimal rate, RateSource rateSource) {
		this.date = date;
		this.currencyFrom = currencyFrom;
		this.currencyTo = currencyTo;
		this.rate = rate;
		this.rateSource = rateSource;
	}

	public String getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public Currency getCurrencyFrom() {
		return currencyFrom;
	}

	public Currency getCurrencyTo() {
		return currencyTo;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public RateSource getRateSource() {
		return rateSource;
	}

	@Override
	public String toString() {
		return "Rate{" +
				"id=" + id +
				", date=" + date +
				", currencyFrom=" + currencyFrom +
				", currencyTo=" + currencyTo +
				", rate=" + rate +
				", rateSource=" + rateSource +
				'}';
	}
}
