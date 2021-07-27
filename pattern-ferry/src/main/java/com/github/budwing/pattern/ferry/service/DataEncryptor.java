package com.github.budwing.pattern.ferry.service;

import java.io.File;
import java.util.List;

public interface DataEncryptor {
	/**
	 * ��������ļ����ܣ������ü��ܺ���ļ�����ԭ�ļ�
	 * @param files ��Ҫ���ܵ��ļ�
	 * @return �����Ƿ�ɹ�
	 * @throws Exception 
	 */
	public boolean encrypt(List<File> files) throws Exception;
	
	/**
	 * ��������ļ�ɨ�裬�Ƿ���ڲ���
	 * @param files
	 * @return �Ƿ���ڲ���
	 * @throws Exception ������Ϣ����ͨ���쳣����ʽ����
	 */
	public boolean scanVirus(List<File> files) throws Exception;
	
	public boolean scanTrojan(List<File> files) throws Exception;
	
	public boolean otherCheck(List<File> files) throws Exception;
}
