package com.github.budwing.pattern.ferry.processor;

import com.github.budwing.pattern.ferry.service.PartitionWorker;
import com.github.budwing.pattern.ferry.vo.FerryRequest;
import org.apache.log4j.Logger;

import static com.github.budwing.pattern.ferry.vo.FerryStatus.*;
import static com.github.budwing.pattern.ferry.vo.FerryStatus.getStatus;

/**
 * ��ָ����С�ֿ����ݣ������ֺÿ������ת�Ƶ�burningPath��
 */
public class PartitionProcessor extends ExportProcessor {
	private static final Logger logger = Logger.getLogger(PartitionProcessor.class);
	protected Long partitionSize = 734003200l; //Ĭ��700M
	private PartitionWorker partitionWorker;
	
	public Long getPartitionSize() {
		return partitionSize;
	}

	public void setPartitionSize(Long partitionSize) {
		this.partitionSize = partitionSize;
	}

	public PartitionWorker getPartitionWorker() {
		return partitionWorker;
	}

	public void setPartitionWorker(PartitionWorker partitionWorker) {
		this.partitionWorker = partitionWorker;
	}
	
	public boolean doProcess(final FerryRequest request) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("���ڷֿ�����"+request.getRequestId()+"��"); 
		}
		
		boolean result = false;
		request.setStatus(getStatus(PARTITION));
		try {
			result = partitionWorker.doPartition(request);
		} catch (Exception e) {
			request.setStatus(getStatus(ERROR));
			logger.error("�ֿ����"+e);
			throw e;
		}
		request.setStatus(getStatus(PARTITION_COMPLETE));
		if(!result) {
			request.setStatus(getStatus(CACHE_FOR_BURNING));
		} else {
			request.setStatus(getStatus(WAIT_BURNING));
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("����"+request.getRequestId()+"�ֿ���ɣ�"); 
		}
		
		return true;
	}

}
