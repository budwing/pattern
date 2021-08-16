package com.github.budwing.pattern.factory.method;

public abstract class Plant {
	
	//��������
	public abstract Fruit generateFruit();

	public void reproduce() {
		Fruit fruit = generateFruit();
		String seed = fruit.getSeed();
		System.out.println("Reproduce by "+seed);
	}
}
