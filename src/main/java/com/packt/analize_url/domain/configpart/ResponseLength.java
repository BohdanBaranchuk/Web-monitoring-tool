package com.packt.analize_url.domain.configpart;

import javax.persistence.*;

@Embeddable
public class ResponseLength {
	
	@Column(name="MINSIZE")
	private int minLength;
	
	@Column(name="MAXSIZE")
	private int maxLength;

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

}
