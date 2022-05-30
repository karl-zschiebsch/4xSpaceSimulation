package org.tss.controller;

public enum ResourceType {
	CREDIT;

	public record ResourceCost(ResourceType type, Double costs) {

	}
}
