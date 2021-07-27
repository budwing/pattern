package com.github.budwing.pattern.ferry.processor;

import com.github.budwing.pattern.ferry.service.DataCollector;
import com.github.budwing.pattern.ferry.service.DataEncryptor;
import com.github.budwing.pattern.ferry.service.PartitionWorker;
import com.github.budwing.pattern.ferry.vo.FerryEntry;
import com.github.budwing.pattern.ferry.vo.FerryRequest;
import com.github.budwing.pattern.ferry.vo.FerryStatus;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DefaultExportProcessor extends ExportProcessor {
	private static final Logger logger = Logger.getLogger(DefaultExportProcessor.class);
	private Map<String, DataCollector> dataCollectors;
	private PartitionWorker partitionWorker;
	private DataEncryptor dataEncryptor;
	
	public Map<String, DataCollector> getDataCollectors() {
		return dataCollectors;
	}

	public void setDataCollectors(Map<String, DataCollector> dataCollectors) {
		this.dataCollectors = dataCollectors;
	}

	public PartitionWorker getPartitionWorker() {
		return partitionWorker;
	}

	public void setPartitionWorker(PartitionWorker partitionWorker) {
		this.partitionWorker = partitionWorker;
	}

	public DataEncryptor getDataEncryptor() {
		return dataEncryptor;
	}

	public void setDataEncryptor(DataEncryptor dataEncryptor) {
		this.dataEncryptor = dataEncryptor;
	}

	/**
	 * �����ռ�������
	 * �÷���������һ������Ľű��ļ���Ĭ�Ͻű��ļ�����Ϊ"<����id>.ferry"��
	 * �ýű��ļ��ж�����һ���ж��ٸ���ĿҪ�ڶɣ��Լ���Щ��Ŀ�Ļ�����Ϣ��
	 * �磬��Ӧ�İڶ��ļ������͵ȡ���ʽ���£�
	 * ��Ŀ���ͣ�<��Ŀ��������ֵ>,<��Ŀ��������ֵ>...,<�����ļ�����>
	 * ���ݻ��ռ���cachePath��
	 */
	public boolean collect(FerryRequest request) throws Exception  {
		if (logger.isDebugEnabled()) {
			logger.debug("�����ռ��ڶ�����"+request.getRequestId()+"�����ݣ�"); 
		}
		boolean result =false;
		
		//��Ϊ�����ж����Ŀ��Ҫִ�У���˻���ʱ�������ʱ�����Ϊ��ǰʱ�����ͳһ
		Date execTime = new Date();
		ferryRequestService.changeRequestStatus(request.getRequestId(), FerryStatus.GATHER);
		
		//�ű��ļ�����
		String fileName = request.getRequestId()+suffix;
		File ferryFile = new File(getExportCachePath() + fileName);
		
		try {
			ferryFile.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(ferryFile, false));
			
			List<FerryEntry> entrys = request.getEntrys();
			
			DataCollector dataCollector = null;
			String script = null;
			if (entrys!=null) {
				for (FerryEntry entry : entrys) {
					//�����ļ������ݿ�ѡ��ͬ�������ռ���
					dataCollector = dataCollectors.get(entry.getEntryObjType());
					if (dataCollector != null) {
						script = dataCollector.collectEntry(request, entry,
								execTime);
						if (script != null && script.length() > 0) {
							writer.write(script);
							writer.newLine();
							result = true;
						} else {
							continue;
						}

						dataCollector = null;
					}
				}
			}
			writer.close();
		} catch (Exception e) {
			ferryRequestService.changeRequestStatus(request.getRequestId(), FerryStatus.ERROR);
			logger.error("�ռ����ݳ���"+e);
			throw e;
		}
		
		ferryRequestService.saveExecTime(request.getRequestId(), execTime);
		ferryRequestService.changeRequestStatus(request.getRequestId(), FerryStatus.GATHER_COMPLETE);
		
		if (logger.isDebugEnabled()) {
			logger.debug("����"+request.getRequestId()+"�����ռ���ϣ�"); 
		}
		
		if (!result) {
			//resultΪfalseʱ֤��û���ռ����κ�����
			ferryRequestService.changeRequestStatus(request.getRequestId(),
					FerryStatus.GATHER_NOTHING);
		}
		return result;
	}
	
	/**
	 * ��ָ����С�ֿ����ݣ������ֺÿ������ת�Ƶ�burningPath��
	 */
	public boolean partition(final FerryRequest request) throws Exception {
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
		
		return result;
	}

	public boolean encrypt(FerryRequest request) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("������֤����"+request.getRequestId()+"�����ݣ�"); 
		}
		List<File> ferryDataFiles = request.getBurningFiles();
		ferryRequestService.changeRequestStatus(request.getRequestId(), FerryStatus.SCAN_VIRUS);
		try {
			if(dataEncryptor.scanVirus(ferryDataFiles)) {
				if(dataEncryptor.scanTrojan(ferryDataFiles)) {
					ferryRequestService.changeRequestStatus(request.getRequestId(), FerryStatus.ENCRYPTING);
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
		
		return false;
	}

	public native void burning(FerryRequest request) throws Exception;
	
	public String getExportCachePath() {
		return "d:/";
	}
}
