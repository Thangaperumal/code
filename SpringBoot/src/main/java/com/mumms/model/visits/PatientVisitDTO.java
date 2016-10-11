package com.mumms.model.visits;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PatientVisitDTO {
	String id;
	@JsonIgnore
	String patientNumber;
	String careProviderNumber;
	String careProviderRole;
	String visitType;
	String duration;
	String startDate;
	String arriveLat;
	String arriveLon;
	String departLat;
	String departLon;
	String parking;
	String tolls;
	String transitFees;
	String billable;
	String travelMileage;
	String travelDuration;
	Boolean faceToFaceVisit;
	List<PhysicianServiceDTO> physicianServices;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPatientNumber() {
		return patientNumber;
	}

	public void setPatientNumber(String patientNumber) {
		this.patientNumber = patientNumber;
	}

	public String getCareProviderNumber() {
		return careProviderNumber;
	}

	public void setCareProviderNumber(String careProviderNumber) {
		this.careProviderNumber = careProviderNumber;
	}

	public String getCareProviderRole() {
		return careProviderRole;
	}

	public void setCareProviderRole(String careProviderRole) {
		this.careProviderRole = careProviderRole;
	}

	public String getVisitType() {
		return visitType;
	}

	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getArriveLat() {
		return arriveLat;
	}

	public void setArriveLat(String arriveLat) {
		this.arriveLat = arriveLat;
	}

	public String getArriveLon() {
		return arriveLon;
	}

	public void setArriveLon(String arriveLon) {
		this.arriveLon = arriveLon;
	}

	public String getDepartLat() {
		return departLat;
	}

	public void setDepartLat(String departLat) {
		this.departLat = departLat;
	}

	public String getDepartLon() {
		return departLon;
	}

	public void setDepartLon(String departLon) {
		this.departLon = departLon;
	}

	public String getParking() {
		return parking;
	}

	public void setParking(String parking) {
		this.parking = parking;
	}

	public String getTolls() {
		return tolls;
	}

	public void setTolls(String tolls) {
		this.tolls = tolls;
	}

	public String getTransitFees() {
		return transitFees;
	}

	public void setTransitFees(String transitFees) {
		this.transitFees = transitFees;
	}

	public String getBillable() {
		return billable;
	}

	public void setBillable(String billable) {
		this.billable = billable;
	}

	public String getTravelMileage() {
		return travelMileage;
	}

	public void setTravelMileage(String travelMileage) {
		this.travelMileage = travelMileage;
	}

	public String getTravelDuration() {
		return travelDuration;
	}

	public void setTravelDuration(String travelDuration) {
		this.travelDuration = travelDuration;
	}

	public Boolean getFaceToFaceVisit() {
		return faceToFaceVisit;
	}

	public void setFaceToFaceVisit(Boolean faceToFaceVisit) {
		this.faceToFaceVisit = faceToFaceVisit;
	}

	public List<PhysicianServiceDTO> getPhysicianServices() {
		return physicianServices;
	}

	public void setPhysicianServices(List<PhysicianServiceDTO> physicianServices) {
		this.physicianServices = physicianServices;
	}
}
