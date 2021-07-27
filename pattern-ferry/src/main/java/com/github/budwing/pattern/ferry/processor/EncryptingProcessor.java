package com.github.budwing.pattern.ferry.processor;

import com.github.budwing.pattern.ferry.service.DataEncryptor;
import com.github.budwing.pattern.ferry.vo.FerryRequest;
import com.github.budwing.pattern.ferry.vo.FerryStatus;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;

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
		ferryRequestService.changeRequestStatus(request.getRequestId(), FerryStatus.SCAN_VIRUS);
		try {
			if(dataEncryptor.scanVirus(ferryDataFiles)) {
				if(dataEncryptor.scanTrojan(ferryDataFiles)) {
					ferryRequestService.changeRequestStatus(request.getRequestId(), FerryStatus.ENCRYPTING);
					if (logger.isDebugEnabled()) {
						logger.debug("��֤"+request.getRequestId()+"�����ݽ�����"); 
					}
					return dataEncryptor.encrypt(ferryDataFiles);
				} else {
					ferryRequestService.changeRequestStatus(request.getRequestId(), FerryStatus.ENCRYPT_FAIL);
				}
			} else {
				ferryRequestService.changeRequestStatus(request.getRequestId(), FerryStatus.SCAN_VIRUS_FAIL);
			}
		} catch (Exception e) {
			ferryRequestService.changeRequestStatus(request.getRequestId(), FerryStatus.ERROR);
			logger.error("����У��ʱ����"+e);
			throw e;
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("��֤"+request.getRequestId()+"�����ݳ������⣬���ܴ��ڲ�����"); 
		}
		
		return true;
	}

}
