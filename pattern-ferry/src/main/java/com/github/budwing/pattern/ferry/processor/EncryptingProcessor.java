package com.github.budwing.pattern.ferry.processor;

import com.github.budwing.pattern.ferry.service.DataEncryptor;
import com.github.budwing.pattern.ferry.vo.FerryRequest;
import org.apache.log4j.Logger;

import static com.github.budwing.pattern.ferry.vo.FerryStatus.ERROR;
import static com.github.budwing.pattern.ferry.vo.FerryStatus.getStatus;

public class EncryptingProcessor extends ExportProcessor {
	private static final Logger logger = Logger.getLogger(EncryptingProcessor.class);
	private DataEncryptor dataEncryptor;
	
	public DataEncryptor getDataEncryptor() {
		return dataEncryptor;
	}

	public void setDataEncryptor(DataEncryptor dataEncryptor) {
		this.dataEncryptor = dataEncryptor;
	}

	public boolean doProcess(FerryRequest request) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("������֤����"+request.getRequestId()+"�����ݣ�"); 
		}
		boolean result = false;
		try {
			result = dataEncryptor.encrypt(request);
		} catch (Exception e) {
			request.setStatus(getStatus(ERROR));
			logger.error("����У��ʱ����"+e);
			throw e;
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("��֤"+request.getRequestId()+"�����ݽ�����"); 
		}
		
		return result;
	}

}
