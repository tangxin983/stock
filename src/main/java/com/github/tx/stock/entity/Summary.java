package com.github.tx.stock.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 
 * 
 * @author tangx
 * @since 2014-11-21
 */
@Table(name = "summary")
public class Summary {

	@Column(name = "symbol")
	private String symbol; //

	@Column(name = "strategy")
	private String strategy;

	@Column(name = "begin_date")
	private Integer begin; //

	@Column(name = "end_date")
	private Integer end; //

	@Column(name = "hit")
	private Integer hit; //

	@Column(name = "limit_times")
	private Integer limit; //

	@Column(name = "spec1")
	private Double spec1; //

	@Column(name = "spec2")
	private Double spec2; //

	@Column(name = "spec3")
	private Double spec3; //

	@Column(name = "spec4")
	private Double spec4; //

	@Column(name = "spec5")
	private Double spec5; //

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Integer getBegin() {
		return begin;
	}

	public void setBegin(Integer begin) {
		this.begin = begin;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public Integer getHit() {
		return hit;
	}

	public void setHit(Integer hit) {
		this.hit = hit;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Double getSpec1() {
		return spec1;
	}

	public void setSpec1(Double spec1) {
		this.spec1 = spec1;
	}

	public Double getSpec2() {
		return spec2;
	}

	public void setSpec2(Double spec2) {
		this.spec2 = spec2;
	}

	public Double getSpec3() {
		return spec3;
	}

	public void setSpec3(Double spec3) {
		this.spec3 = spec3;
	}

	public Double getSpec4() {
		return spec4;
	}

	public void setSpec4(Double spec4) {
		this.spec4 = spec4;
	}

	public Double getSpec5() {
		return spec5;
	}

	public void setSpec5(Double spec5) {
		this.spec5 = spec5;
	}

}
