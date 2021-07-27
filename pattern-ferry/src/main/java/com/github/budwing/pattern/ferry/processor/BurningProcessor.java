package com.github.budwing.pattern.ferry.processor;

import com.github.budwing.pattern.ferry.vo.FerryRequest;
import org.apache.log4j.Logger;

public class BurningProcessor extends ExportProcessor {
	private static final Logger logger = Logger.getLogger(BurningProcessor.class);
	
	public boolean doProcess(FerryRequest request) {
		boolean result = false;
		if (logger.isDebugEnabled()) {
			logger.debug("���ڿ�¼"+request.getRequestId()+"�����ݣ�"); 
		}
		
		try {
			result = burning(request);
		} catch (Exception e) {
			logger.error(e);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("��¼"+request.getRequestId()+"�����ݽ�����"); 
		}
		return result;
	}

	public native boolean burning(FerryRequest request) throws Exception;
	
}
