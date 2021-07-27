package com.github.budwing.pattern.alarm;

public class PhoneAlarmMessage extends AlarmMessage {
	public String build() {
		StringBuilder phonecontent = new StringBuilder("�𾴵��쵼");
		phonecontent.append("  ��ֹ��").append(getTimeFormat().format(getTime()))
				.append(" ��").append(getDistrict()).append(getDuration()).append("���ڷ�����")
				.append(getPatientNumber()).append("��").append(getDesease())
				.append("��Ⱦ����   �������ѳ�����ʷͬ��ˮƽ  ��ע�������������  ");
		return phonecontent.toString();
	}
}
