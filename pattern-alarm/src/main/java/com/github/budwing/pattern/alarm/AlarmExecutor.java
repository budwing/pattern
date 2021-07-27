package com.github.budwing.pattern.alarm;

public class AlarmExecutor {
	public void execute(Alarm alarm) {
		for (int i = 0; i < alarm.getRetryTimes(); i++) {
			if (alarm.execute()) {
				break;
			}
		}
	}


	/*public static void main(String[] args) {
		Alarm a = new Alarm();
		a.setDesease("�˺�");
		a.setDistrict("ɽ��ʡΫ����");
		a.setDuration(5);
		a.setPatientNumber(38);
		a.setTime(new Date());

		a.setSmsURL("http://www.sina.com.cn");

		a.sendMsg(new ArrayList());
	}*/
}

