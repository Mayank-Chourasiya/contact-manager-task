package com.contact.httpResponse;

public class DataResponse implements RestResponse {

	private int status;

	private String message;

	private Object data;

	public DataResponse() {
		super();

	}

	public DataResponse(int status, String message, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "DataResponse [status=" + status + ", message=" + message + ", data=" + data + "]";
	}

}
