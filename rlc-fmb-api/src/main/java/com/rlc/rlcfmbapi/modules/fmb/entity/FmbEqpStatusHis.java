/**
 * Project Name:fmb
 * File Name:FmbEqpStatusHis.java
 * Package Name:com.rlc.modules.fmb.entity
 * Date:2021年1月7日下午2:17:37
 * Copyright (c) 2021, http://www.xxx.com/ All Rights Reserved.
 *
*/

package com.rlc.rlcfmbapi.modules.fmb.entity;

import com.rlc.rlcbase.persistence.DataEntity;

/**
 * ClassName:FmbEqpStatusHis <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2021年1月7日 下午2:17:37 <br/>
 * @author   RLC_ZYC
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class FmbEqpStatusHis extends DataEntity<FmbEqpStatusHis> {
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = 1L;

	private String eqpId;

    private String eqpName;

    private String eqpStatus;

    private Long statusDuration;

	public String getEqpId() {
		return eqpId;
	}

	public void setEqpId(String eqpId) {
		this.eqpId = eqpId;
	}

	public String getEqpName() {
		return eqpName;
	}

	public void setEqpName(String eqpName) {
		this.eqpName = eqpName;
	}

	public String getEqpStatus() {
		return eqpStatus;
	}

	public void setEqpStatus(String eqpStatus) {
		this.eqpStatus = eqpStatus;
	}

	public Long getStatusDuration() {
		return statusDuration;
	}

	public void setStatusDuration(Long statusDuration) {
		this.statusDuration = statusDuration;
	}
}