package com.github.budwing.pattern.state;

public class Pupa implements ButterFlyState {

	public void eat() {
		System.out.println("����Ӽ���Ҳ��Զ�����");
	}

	public void fly() {
		System.out.println("����Ӽ���Ҳ��ܷɣ�");
	}

	public void walk() {
		System.out.println("����Ӽ���Ҳ��ܶ���");
	}

}
