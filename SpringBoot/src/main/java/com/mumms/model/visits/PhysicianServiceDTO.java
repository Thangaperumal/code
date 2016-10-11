package com.mumms.model.visits;

import java.util.List;

public class PhysicianServiceDTO {
	String hcpcsCode;
	List<String> hcpcsDiagnoses;
	String rate;

	public String getHcpcsCode() {
		return hcpcsCode;
	}

	public void setHcpcsCode(String hcpcsCode) {
		this.hcpcsCode = hcpcsCode;
	}

	public List<String> getHcpcsDiagnoses() {
		return hcpcsDiagnoses;
	}

	public void setHcpcsDiagnoses(List<String> hcpcsDiagnoses) {
		this.hcpcsDiagnoses = hcpcsDiagnoses;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}
}
