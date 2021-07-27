package com.github.budwing.pattern.alarm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneAlarmMessage extends AlarmMessage {
	private Map<String, String> voiceMapping = new HashMap();
	private int phoneRetryTimes = 3;
	
	public String build() {
		StringBuilder phonecontent = new StringBuilder("�𾴵��쵼");
		phonecontent.append("  ��ֹ��").append(getTimeFormat().format(getTime()))
				.append(" ��").append(getDistrict()).append(getDuration()).append("���ڷ�����")
				.append(getPatientNumber()).append("��").append(getDesease())
				.append("��Ⱦ����   �������ѳ�����ʷͬ��ˮƽ  ��ע�������������  ");
		return phonecontent.toString();
	}
	
	public boolean send(List<Employee> contacts) {
		// ��绰
		String phonecontent = build();

		String[] voiceFiles = new String[phonecontent.length()];
		for (int i = 0; i < phonecontent.length(); i++) {
			voiceFiles[i] = voiceMapping.get(phonecontent.charAt(i));
		}

		for (Employee e : contacts) {
			for (int i = 0; i < phoneRetryTimes; i++) {
				if (phone(e.getTelephone(), voiceFiles)) {
					log(e, 3, phonecontent.toString(), "success");
					break;
				}
			}
			log(e, 3, phonecontent.toString(), "failure");
		}
		
		return true;
	}
	
	public native boolean phone(String phoneNum, String[] voiceFiles);
}
