package com.voyanta.test.entities;

import org.apache.commons.lang3.ObjectUtils;

public class OperationRequest {
	private String id;
	
	private double [][] operations = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double [][] getOperations() {
		return operations;
	}

	public void setOperations(double [][] operations) {
		this.operations = operations;
	}
	
	@Override
	public int hashCode() {
		return id == null? 17 : id.hashCode();
	}
	
	@Override
	public boolean equals(Object object){
		if(this == object) {
			return true;
		}
		
		if(!(object instanceof OperationRequest)) {
			return false;
		}
		
		return equals((OperationRequest) object);
	}

	private boolean equals(OperationRequest other) {
		return ObjectUtils.compare(this.id, other.id) == 0;
	}
}
