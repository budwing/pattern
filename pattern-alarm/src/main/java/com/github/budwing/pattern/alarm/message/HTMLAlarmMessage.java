package com.github.budwing.pattern.alarm.message;

public class HTMLAlarmMessage extends AlarmMessage {
	public String build() {
		StringBuilder emailContent = new StringBuilder("�𾴵��쵼��<br/><br/>");
		emailContent.append("&nbsp;&nbsp;&nbsp;&nbsp;<font color=red>")
				.append("��ֹ��").append(getTimeFormat().format(getTime())).append("��<b>��")
				.append(getDistrict()).append(getDuration()).append("���ڷ�����")
				.append(getPatientNumber()).append("��").append(getDesease())
				.append("��Ⱦ����</b>���������ѳ�����ʷͬ��ˮƽ����ע������������С�<br/>")
				.append("</font><br/><br/><br/>���ʼ���ϵͳ�Զ����ɣ����ػظ���");
		return emailContent.toString();
	}
}


