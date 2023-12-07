package com.jwtTokenAuth.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomMessage {
	
	private String message;
	private String msgKey;
	private Object data;
	private Date time;

}
