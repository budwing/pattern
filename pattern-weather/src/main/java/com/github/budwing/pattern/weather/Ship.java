package com.github.budwing.pattern.weather;

import java.util.List;

public class Ship {
	private List<Equipment> equipments;

	public List<Equipment> getEquipments() {
		return equipments;
	}

	public void setEquipments(List<Equipment> equipments) {
		this.equipments = equipments;
	}

	public boolean canSailToday(Weather weather) {
		if(!isEquipmentsAbnormal() && weather.isFitForSail()) {
			return true;
		}

		return false;
	}

	public boolean isEquipmentsAbnormal() {
		for(Equipment e:equipments) {
			if(e.isAbnormal()) {
				return true;
			}
		}
		
		return false;
	}
}
