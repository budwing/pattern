package com.github.budwing.pattern.ferry.service;

import com.github.budwing.pattern.ferry.vo.FerryRequest;

public interface DataEncryptor {
	/**
	 * ��������ļ����ܣ������ü��ܺ���ļ�����ԭ�ļ�
	 * @param request ��Ҫ���ܵ��ļ�
	 * @return �����Ƿ�ɹ�
	 * @throws Exception 
	 */
	public boolean encrypt(FerryRequest request) throws Exception;
	
}
