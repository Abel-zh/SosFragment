package com.naicha.vo;

import common.base.BaseEntity;

/**
 * @Description: json头实体
 * @author: ethanchiu@126.com
 * @date: Dec 25, 2013
 */
public class JsonEntity<T> extends BaseEntity {

	private static final long serialVersionUID = -5066223913900955702L;

	private int category;

	private int version;

	private String command;

	private int code;

	private String desc;

	private T data;

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
