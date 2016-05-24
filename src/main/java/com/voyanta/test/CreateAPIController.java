package com.voyanta.test;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.voyanta.test.addition.AdditionRequestProcessor;
import com.voyanta.test.entities.OperationRequest;
import com.voyanta.test.entities.OperationResult;

@RestController
public class CreateAPIController {
	Log logger = LogFactory.getLog(CreateAPIController.class);
	
	@Autowired
	AdditionRequestProcessor aditionRequestProcessor;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes =  "application/json;charset=UTF-8", 
			produces = "application/json;charset=UTF-8")
	
	public @ResponseBody ResponseEntity<OperationResult> add(@RequestBody OperationRequest operations){
		ResponseEntity<OperationResult> response;
		
		try {
			OperationResult result = aditionRequestProcessor.execute(operations);
			
			response = new ResponseEntity<OperationResult>(result, HttpStatus.OK);
			
		} catch (IllegalArgumentException e) {
			response = new ResponseEntity<OperationResult>(HttpStatus.BAD_REQUEST);
			
			logger.warn(ExceptionUtils.getStackTrace(e));
			
		} catch (Exception e) {
			response = new ResponseEntity<OperationResult>(HttpStatus.INTERNAL_SERVER_ERROR);
			
			logger.warn(ExceptionUtils.getStackTrace(e));
		}
		
		return response;
	}


	public void setAditionProcessor(AdditionRequestProcessor aditionProcessor) {
		this.aditionRequestProcessor = aditionProcessor;
	}
}
