package com.github.budwing.pattern.ferry.processor;

import com.github.budwing.pattern.ferry.service.FerryRequestService;
import com.github.budwing.pattern.ferry.vo.FerryRequest;

/**
 * �������ݵĴ�����
 */
public abstract class ExportProcessor {
	protected String suffix = ".ferry";
	protected Long partitionSize = 734003200l; //Ĭ��700M
	protected FerryRequestService ferryRequestService;

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public Long getPartitionSize() {
		return partitionSize;
	}

	public void setPartitionSize(Long partitionSize) {
		this.partitionSize = partitionSize;
	}

	public FerryRequestService getFerryRequestService() {
		return ferryRequestService;
	}

	public void setFerryRequestService(FerryRequestService ferryRequestService) {
		this.ferryRequestService = ferryRequestService;
	}

	/**
	 * �ռ�����
	 * @param request
	 * @throws Exception
	 */
	public abstract boolean collect(FerryRequest request) throws Exception;
	
	/**
	 * ���Ѿ��ռ��õ����ݽ��м��ܵȹ���
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public abstract boolean encrypt(FerryRequest request) throws Exception;
	
	/**
	 * �ֿ飬����true������Ӧ����������¼����֮���������¼����
	 * @param request
	 * @throws Exception
	 */
	public abstract boolean partition(FerryRequest request) throws Exception;
	
	/**
	 * ������������¼ָ��
	 * @param request
	 * @throws Exception
	 */
	public abstract void burning(FerryRequest request) throws Exception;
	
	public abstract String getExportCachePath();
	
}
