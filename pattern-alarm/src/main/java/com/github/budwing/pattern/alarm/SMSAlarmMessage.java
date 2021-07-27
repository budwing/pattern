package com.github.budwing.pattern.alarm;


public class SMSAlarmMessage extends AlarmMessage {
	public String build() {
		StringBuilder smscontent = new StringBuilder("�𾴵��쵼��\n");
		smscontent.append("��ֹ��").append(getTimeFormat().format(getTime()))
				.append("����").append(getDistrict()).append(getDuration())
				.append("���ڷ�����").append(getPatientNumber()).append("��")
				.append(getDesease()).append("��Ⱦ�������������ѳ�����ʷͬ��ˮƽ����ע������������С�\n")
				.append("����Ϣ��ϵͳ�Զ����ɣ����ػظ���");
		return smscontent.toString();
	}

}
