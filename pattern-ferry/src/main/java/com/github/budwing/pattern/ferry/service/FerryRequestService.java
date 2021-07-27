package com.github.budwing.pattern.ferry.service;

import com.github.budwing.pattern.ferry.vo.FerryRequest;

import java.util.Date;
import java.util.List;

public interface FerryRequestService {
	public void addAndAudit(FerryRequest obj) throws Exception;
	public void addWithStatus(Object obj, String statusId) throws Exception;
	public void modifyAndAudit(FerryRequest obj) throws Exception;
	public FerryRequest getWithParamValues(String requestId) throws Exception;
	public FerryRequest getWithTask(String requestId) throws Exception;
	
	/**
	 * 2014��6���޶�ʱ����
	 * ��ѯһ���ļ���Դ�����һ�ΰڶ�ʱ������޸�ʱ��
	 * @param entryId
	 * @return
	 */
	public Long getFileEntryLastModifyTime(String taskId, String entryId);
	
	public Date getLatestRequestExecTime(String requestId) throws Exception;
	public List getNeedCollectingRequests() throws Exception;
	public List getNeedPartitionRequests() throws Exception;
	public List getNeedBurningRequests() throws Exception;
	
	
	public void changeRequestStatus(String requestId, String status) throws Exception;
	public void saveExecTime(String requestId, Date date) throws Exception;
	
	/**
	 * ����ڶ���������ʼ״̬�����������ɵ�״̬
	 * 1�����������ռ���������
	 * 2���������ݿ��м�¼��Ŀ¼����
	 * @param request
	 */
	public void clearRequest(FerryRequest request) throws Exception;
	
	/**
	 * Ϊ֧�ֶ��̴߳������µķ����ӿ�
	 * @param request
	 */
	public void startCollect(FerryRequest request) throws Exception;
	public void startCheck(FerryRequest request) throws Exception;
	public void startPartition(FerryRequest request) throws Exception;
	public void startBurn(FerryRequest request) throws Exception;	
	
	
	public void finishTaskOfRequest(FerryRequest request) throws Exception;
}
