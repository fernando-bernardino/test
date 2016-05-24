package com.voyanta.test.data.cache;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="addition_cache")
public class CacheEntry {
	
	@Id
    @Column(name="operands", nullable=false)
	private String operands;
	
    @Column(name="result", nullable=false)
	private double result;
	
	public String getOperands() {
		return operands;
	}
	public void setOperands(String operands) {
		this.operands = operands;
	}
	public double getResult() {
		return result;
	}
	public void setResult(double result) {
		this.result = result;
	}
	
	@Override
    public int hashCode() {
        return operands == null? 17 : operands.hashCode();
	}
	
	@Override
	public boolean equals(Object object){
		return object instanceof CacheEntry ?
					equals((CacheEntry) object) :
					false;
	}
	
	private boolean equals(CacheEntry entry) {
		if(operands == null) {
			return entry.getOperands() == null;
		} else {
			return operands.equals(entry.getOperands());
		}
	}	
}
