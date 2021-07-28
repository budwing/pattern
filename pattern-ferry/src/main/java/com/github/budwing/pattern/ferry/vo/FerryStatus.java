package com.github.budwing.pattern.ferry.vo;

import java.lang.ref.SoftReference;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FerryStatus {
	public static final String NEW = "new";                 //�½���δ�ύ���
	public static final String SUBMIT = "submit";           //�ύ��ˣ�δ����
	public static final String PASS = "audit_pass";         //����ͨ��
	public static final String REFUSE = "audit_refuse";     //�������
	public static final String QUEUE = "queue";             //��������Ŷӣ��ȴ�����
	public static final String GATHER = "gather_data";      //�ռ�����
	public static final String GATHER_COMPLETE = "gather_complete";        //�ռ��������
	public static final String GATHER_NOTHING = "gather_nothing";          //ʲô����Ҳû�ռ���
	public static final String PARTITION = "partition";					   //���ڷֿ�
	public static final String PARTITION_COMPLETE = "partition_complete";  //�ֿ����
	public static final String WAIT_BURNING = "wait_burning";              //�����������ȴ��������
	public static final String BURNING = "burning";                        //���ڿ�¼
	public static final String BURNING_COMPLETE = "burning_complete";      //��¼���
	public static final String BURNING_ERROR = "burning_error";            //��¼����
	public static final String WAIT_FERRY = "wait_ferry";                  //�����������ȴ�ȡ�߹��̣����аڶ�
	public static final String FERRYING = "ferrying";                      //���̱�ȡ�ߣ����ڰڶ���
	public static final String CACHE_FOR_BURNING = "cache_for_burning";                      //�Ѿ��ֿ���ɣ���δ�ﵽ��¼����Ҫ��ֵ���ȴ����������㹻���¼
	public static final String ERROR = "error";                      //�Ѿ��ֿ���ɣ���δ�ﵽ��¼����Ҫ��ֵ���ȴ����������㹻���¼
	public static final String SCAN_VIRUS = "scan_virus";
	public static final String SCAN_VIRUS_FAIL = "scan_virus_fail";
	public static final String SCAN_VIRUS_FINISHED = "scan_virus_finished";
	public static final String ENCRYPTING = "encrypting";
	public static final String ENCRYPT_FAIL = "encrypt_fail";
	public static final String ENCRYPT_FINISHED = "encrypt_finished";
	
    private String statusId;
    private String statusName;
    private String statusDesc;
    private Date statusTime;
    
    private static Map<String, SoftReference<FerryStatus>> cache = new HashMap<String, SoftReference<FerryStatus>>();
    
    public static FerryStatus getStatus(String id) {
    	SoftReference<FerryStatus> status = cache.get(id);
    	
    	if(status==null || status.get()==null) {
    		status = new SoftReference<FerryStatus>(new FerryStatus(id));
    		cache.put(id, status);
    	}
    	
    	return status.get();
    }

    private FerryStatus() {
		super();
	}

	private FerryStatus(String statusId) {
		super();
		this.statusId = statusId;
	}

	public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

	public Date getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(Date statusTime) {
		this.statusTime = statusTime;
	}
}