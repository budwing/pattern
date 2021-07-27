package com.github.budwing.pattern.ferry.processor;

import com.github.budwing.pattern.ferry.service.PartitionWorker;
import com.github.budwing.pattern.ferry.vo.FerryRequest;
import com.github.budwing.pattern.ferry.vo.FerryStatus;
import org.apache.log4j.Logger;

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
		
		ferryRequestService.changeRequestStatus(request.getRequestId(), FerryStatus.PARTITION);
		try {
			result = partitionWorker.doPartition(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ferryRequestService.changeRequestStatus(request.getRequestId(), FerryStatus.ERROR);
			logger.error("�ֿ����"+e);
			throw e;
		}
		ferryRequestService.changeRequestStatus(request.getRequestId(), FerryStatus.PARTITION_COMPLETE);
		if(!result) {
			ferryRequestService.changeRequestStatus(request.getRequestId(), FerryStatus.CACHE_FOR_BURNING);
		} else {
			ferryRequestService.changeRequestStatus(request.getRequestId(), FerryStatus.WAIT_BURNING);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("����"+request.getRequestId()+"�ֿ���ɣ�"); 
		}
		
		return true;
	}

}
