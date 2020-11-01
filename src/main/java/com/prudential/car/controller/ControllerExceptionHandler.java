package com.prudential.car.controller;

import com.prudential.car.controller.bean.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {
	private Logger logger= LoggerFactory.getLogger(ControllerExceptionHandler.class);

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ApiResponse jsonErrorHandler(Exception e) {
		logger.error("",e);
		ApiResponse response = new ApiResponse();
		response.setResultCode(ApiResponse.ResultCode.FAIL);
		response.setMes("Has exception");
		return response;
	}
}
