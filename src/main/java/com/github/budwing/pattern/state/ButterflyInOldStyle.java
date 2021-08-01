package com.github.budwing.pattern.state;

public class ButterflyInOldStyle {
	private String state = "grub";

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void walk() {
		if ("grub".equals(state)) {
			System.out.println("�����׳棬��ֻ������");
		} else {
			System.out.println("���ǳɳ棬�������ˣ�");
		}
	}

	public void fly() {
		if ("grub".equals(state)) {
			System.out.println("�����׳棬�Ҳ��ܷɣ�");
		} else {
			System.out.println("���ǳɳ棬�ҿ��Էɣ�");
		}
	}

	public void eat() {
		if ("grub".equals(state)) {
			System.out.println("�����׳棬�ҳ���ˣ�");
		} else {
			System.out.println("���ǳɳ棬�Ҳ��ۣ�");
		}
	}
}
