package com.github.budwing.pattern.ferry.processor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.github.budwing.pattern.ferry.service.DataCollector;
import com.github.budwing.pattern.ferry.vo.FerryEntry;
import com.github.budwing.pattern.ferry.vo.FerryRequest;
import com.github.budwing.pattern.ferry.vo.FerryStatus;
import org.apache.log4j.Logger;

/**
 * �����ռ�������
 * �÷���������һ������Ľű��ļ���Ĭ�Ͻű��ļ�����Ϊ"<����id>.ferry"��
 * �ýű��ļ��ж�����һ���ж��ٸ���ĿҪ�ڶɣ��Լ���Щ��Ŀ�Ļ�����Ϣ��
 * �磬��Ӧ�İڶ��ļ������͵ȡ���ʽ���£�
 * ��Ŀ���ͣ�<��Ŀ��������ֵ>,<��Ŀ��������ֵ>...,<�����ļ�����>
 * ���ݻ��ռ���cachePath��
 */
public class CollectingProcessor extends ExportProcessor {
	private static final Logger logger = Logger.getLogger(CollectingProcessor.class);
	protected String suffix = ".ferry";
	private Map<String, DataCollector> dataCollectors;
	
	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	public Map<String, DataCollector> getDataCollectors() {
		return dataCollectors;
	}

	public void setDataCollectors(Map<String, DataCollector> dataCollectors) {
		this.dataCollectors = dataCollectors;
	}

	public boolean doProcess(FerryRequest request) throws Exception  {
		if (logger.isDebugEnabled()) {
			logger.debug("�����ռ��ڶ�����"+request.getRequestId()+"�����ݣ�"); 
		}
		boolean result =false;
		
		//��Ϊ�����ж����Ŀ��Ҫִ�У���˻���ʱ�������ʱ�����Ϊ��ǰʱ�����ͳһ
		request.setRequestExecuteTime(new Date());
		request.setStatus(new FerryStatus(FerryStatus.GATHER));
		
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
						script = dataCollector.collectEntry(request, entry);
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
			request.setStatus(new FerryStatus(FerryStatus.ERROR));
			logger.error("�ռ����ݳ���"+e);
			throw e;
		}
		
		request.setStatus(new FerryStatus(FerryStatus.GATHER_COMPLETE));
		
		if (logger.isDebugEnabled()) {
			logger.debug("����"+request.getRequestId()+"�����ռ���ϣ�"); 
		}
		
		if (!result) {
			//resultΪfalseʱ֤��û���ռ����κ�����
			request.setStatus(new FerryStatus(FerryStatus.GATHER_NOTHING));
		}
		
		return true;
	}
	
	public String getExportCachePath() {
		return "d:/";
	}
	
}
