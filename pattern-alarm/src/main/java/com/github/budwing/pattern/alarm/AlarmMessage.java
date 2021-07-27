package com.github.budwing.pattern.alarm;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AlarmMessage {
	private String desease;
	private int duration;
	private int patientNumber;
	private String district;
	private Date time;
	private SimpleDateFormat timeFormat;

	public AlarmMessage(SimpleDateFormat timeFormat) {
		this.timeFormat = timeFormat;
	}

	public String getDesease() {
		return desease;
	}

	public void setDesease(String desease) {
		this.desease = desease;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getPatientNumber() {
		return patientNumber;
	}

	public void setPatientNumber(int patientNumber) {
		this.patientNumber = patientNumber;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public SimpleDateFormat getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(SimpleDateFormat timeFormat) {
		this.timeFormat = timeFormat;
	}

	public String buildPhoneMessage() {
		StringBuilder phonecontent = new StringBuilder("�𾴵��쵼");
		phonecontent.append("  ��ֹ��").append(getTimeFormat().format(getTime()))
				.append(" ��").append(getDistrict()).append(getDuration()).append("���ڷ�����")
				.append(getPatientNumber()).append("��").append(getDesease())
				.append("��Ⱦ����   �������ѳ�����ʷͬ��ˮƽ  ��ע�������������  ");
		return phonecontent.toString();
	}

	public String buildEmailMessage() {
		StringBuilder emailContent = new StringBuilder("�𾴵��쵼��<br/><br/>");
		emailContent.append("&nbsp;&nbsp;&nbsp;&nbsp;<font color=red>")
				.append("��ֹ��").append(getTimeFormat().format(getTime())).append("��<b>��")
				.append(getDistrict()).append(getDuration()).append("���ڷ�����")
				.append(getPatientNumber()).append("��").append(getDesease())
				.append("��Ⱦ����</b>���������ѳ�����ʷͬ��ˮƽ����ע������������С�<br/>")
				.append("</font><br/><br/><br/>���ʼ���ϵͳ�Զ����ɣ����ػظ���");
		return emailContent.toString();
	}

	public String buildSMSMessage() {
		StringBuilder smscontent = new StringBuilder("�𾴵��쵼��\n");
		smscontent.append("��ֹ��").append(getTimeFormat().format(getTime())).append("����")
				.append(getDistrict()).append(getDuration()).append("���ڷ�����")
				.append(getPatientNumber()).append("��").append(getDesease())
				.append("��Ⱦ�������������ѳ�����ʷͬ��ˮƽ����ע������������С�\n")
				.append("����Ϣ��ϵͳ�Զ����ɣ����ػظ���");
		return smscontent.toString();
	}
}