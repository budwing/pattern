package com.github.budwing.pattern.state;

public class Imago implements ButterFlyState {

	public void eat() {
		System.out.println("���ǳɳ棬�ҳ��ۣ�");
	}

	public void fly() {
		System.out.println("���ǳɳ棬���ܷɣ�");
	}

	public void walk() {
		System.out.println("���ǳɳ棬�����ߣ�");
	}

}
