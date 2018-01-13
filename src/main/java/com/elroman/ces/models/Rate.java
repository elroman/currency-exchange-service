package com.elroman.ces.models;

import com.elroman.ces.utils.DateUtil;
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

    @Column(name = "rateTime", nullable = false, length = 50)
    private Date rateTime = DateUtil.getDateWithTimeZone(new Date());

    @ManyToOne(fetch = FetchType.EAGER)
    private Currency currencyFrom;

    @ManyToOne(fetch = FetchType.EAGER)
    private Currency currencyTo;

    @Column(name = "rate", nullable = false)
    private BigDecimal rate;

    @ManyToOne(fetch = FetchType.EAGER)
    private RateSource rateSource;

    public Rate() {
    }

    public Rate(String id) {
        this.id = id;
    }

    public Rate(Currency currencyFrom, Currency currencyTo, BigDecimal rate, RateSource rateSource) {
        this.rateTime = DateUtil.getDateWithTimeZone(new Date());
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.rate = rate;
        this.rateSource = rateSource;
    }

    public String getId() {
        return id;
    }

    public Date getRateTime() {
        return rateTime;
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
                "id='" + id + '\'' +
                ", rateTime=" + rateTime +
                ", currencyFrom=" + currencyFrom +
                ", currencyTo=" + currencyTo +
                ", rate=" + rate +
                ", rateSource=" + rateSource +
                '}';
    }

    public String toJson() {
        StringBuffer sb = new StringBuffer();
        sb.append("{")
                .append("\"fromCurr\":\"").append(currencyFrom.getAlias()).append("\",")
                .append("\"toCurr\":\"").append(currencyTo.getAlias()).append("\",")
                .append("\"rate\":").append(rate).append(",")
                .append("\"rate time\":\"").append(rateTime).append("\",")
                .append("\"source ID\":\"").append(rateSource.getSourceName()).append("\"")
                .append("}");
        return sb.toString();
    }
}
