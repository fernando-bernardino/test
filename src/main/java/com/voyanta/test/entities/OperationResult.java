package com.voyanta.test.entities;

import org.apache.commons.lang3.ObjectUtils;

public class OperationResult {
	private String id;
	private double [] result;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double [] getResult() {
		return result;
	}
	public void setResult(double [] result) {
		this.result = result;
	}
	
	@Override
	public int hashCode() {
		return id == null? 17 : id.hashCode();
	}
	
	@Override
	public boolean equals(Object object) {
		if(!(object instanceof OperationResult)) {
			return false;
		}
		
		OperationResult other = (OperationResult) object;
		
		return equals(other);
	}
	
	private boolean equals(OperationResult other) {
		return ObjectUtils.compare(this.id, other.id) == 0;
	}
}
