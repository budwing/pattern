package com.github.budwing.pattern.ferry.processor;

import com.github.budwing.pattern.ferry.service.DataEncryptor;
import com.github.budwing.pattern.ferry.vo.FerryRequest;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;

import static com.github.budwing.pattern.ferry.vo.FerryStatus.*;

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
		List<File> ferryDataFiles = request.getBurningFiles();
		request.setStatus(getStatus(SCAN_VIRUS));
		try {
			if(dataEncryptor.scanVirus(ferryDataFiles)) {
				if(dataEncryptor.scanTrojan(ferryDataFiles)) {
					request.setStatus(getStatus(ENCRYPTING));
					if (logger.isDebugEnabled()) {
						logger.debug("��֤"+request.getRequestId()+"�����ݽ�����"); 
					}
					return dataEncryptor.encrypt(ferryDataFiles);
				} else {
					request.setStatus(getStatus(ENCRYPT_FAIL));
				}
			} else {
				request.setStatus(getStatus(SCAN_VIRUS_FAIL));
			}
		} catch (Exception e) {
			request.setStatus(getStatus(ERROR));
			logger.error("����У��ʱ����"+e);
			throw e;
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("��֤"+request.getRequestId()+"�����ݳ������⣬���ܴ��ڲ�����"); 
		}
		
		return true;
	}

}
