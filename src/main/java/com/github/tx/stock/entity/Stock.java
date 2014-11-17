package com.github.tx.stock.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * @author tangx
 * @since 2014年10月22日
 */

@Table(name = "sz000001")
public class Stock {

	@Id
	private Integer date;

	@Column
	private Double open;

	@Column
	private Double high;

	@Column
	private Double low;

	@Column
	private Double close;

	@Column
	private Long volume;

	public Integer getDate() {
		return date;
	}

	public void setDate(Integer date) {
		this.date = date;
	}

	public Double getOpen() {
		return open;
	}

	public void setOpen(Double open) {
		this.open = open;
	}

	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public Double getClose() {
		return close;
	}

	public void setClose(Double close) {
		this.close = close;
	}

	public Long getVolume() {
		return volume;
	}

	public void setVolume(Long volume) {
		this.volume = volume;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
