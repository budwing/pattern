package com.github.budwing.pattern.state;

public class Grub implements ButterFlyState {

	public void eat() {
		System.out.println("�����׳棬�ҳ���ˣ�");
	}

	public void fly() {
		System.out.println("�����׳棬�Ҳ��ܷɣ�");
	}

	public void walk() {
		System.out.println("�����׳棬��ֻ������");
	}

}
