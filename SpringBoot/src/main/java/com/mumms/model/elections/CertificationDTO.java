package com.mumms.model.elections;

public class CertificationDTO {
	private String id= null;
	private String f2fVisitId= null;
	private String f2fVisitPhysicianName= null;
	private String attendingPhysicianId= null;
	private String attendingPhysicianName= null;
	private String attendingPhysicianVerbalDate= null;
	private String attendingPhysicianWrittenDate= null;
	private String hospicePhysicianId= null;
	private String hospicePhysicianName= null;
	private String hospicePhysicianVerbalDate= null;
	private String hospicePhysicianWrittenDate= null;
	private String certificationType= null;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getF2fVisitId() {
		return f2fVisitId;
	}
	public void setF2fVisitId(String f2fVisitId) {
		this.f2fVisitId = f2fVisitId;
	}
	public String getF2fVisitPhysicianName() {
		return f2fVisitPhysicianName;
	}
	public void setF2fVisitPhysicianName(String f2fVisitPhysicianName) {
		this.f2fVisitPhysicianName = f2fVisitPhysicianName;
	}
	public String getAttendingPhysicianId() {
		return attendingPhysicianId;
	}
	public void setAttendingPhysicianId(String attendingPhysicianId) {
		this.attendingPhysicianId = attendingPhysicianId;
	}
	public String getAttendingPhysicianName() {
		return attendingPhysicianName;
	}
	public void setAttendingPhysicianName(String attendingPhysicianName) {
		this.attendingPhysicianName = attendingPhysicianName;
	}
	public String getAttendingPhysicianVerbalDate() {
		return attendingPhysicianVerbalDate;
	}
	public void setAttendingPhysicianVerbalDate(String attendingPhysicianVerbalDate) {
		this.attendingPhysicianVerbalDate = attendingPhysicianVerbalDate;
	}
	public String getAttendingPhysicianWrittenDate() {
		return attendingPhysicianWrittenDate;
	}
	public void setAttendingPhysicianWrittenDate(String attendingPhysicianWrittenDate) {
		this.attendingPhysicianWrittenDate = attendingPhysicianWrittenDate;
	}
	public String getHospicePhysicianId() {
		return hospicePhysicianId;
	}
	public void setHospicePhysicianId(String hospicePhysicianId) {
		this.hospicePhysicianId = hospicePhysicianId;
	}
	public String getHospicePhysicianName() {
		return hospicePhysicianName;
	}
	public void setHospicePhysicianName(String hospicePhysicianName) {
		this.hospicePhysicianName = hospicePhysicianName;
	}
	public String getHospicePhysicianVerbalDate() {
		return hospicePhysicianVerbalDate;
	}
	public void setHospicePhysicianVerbalDate(String hospicePhysicianVerbalDate) {
		this.hospicePhysicianVerbalDate = hospicePhysicianVerbalDate;
	}
	public String getHospicePhysicianWrittenDate() {
		return hospicePhysicianWrittenDate;
	}
	public void setHospicePhysicianWrittenDate(String hospicePhysicianWrittenDate) {
		this.hospicePhysicianWrittenDate = hospicePhysicianWrittenDate;
	}
	public String getCertificationType() {
		return certificationType;
	}
	public void setCertificationType(String certificationType) {
		this.certificationType = certificationType;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Certification {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    f2fVisitId: ").append(toIndentedString(f2fVisitId)).append("\n");
		sb.append("    f2fVisitPhysicianName: ").append(toIndentedString(f2fVisitPhysicianName)).append("\n");
		sb.append("    attendingPhysicianName: ").append(toIndentedString(attendingPhysicianName)).append("\n");
		sb.append("    attendingPhysicianVerbalDate: ").append(toIndentedString(attendingPhysicianVerbalDate)).append("\n");
		sb.append("    attendingPhysicianWrittenDate: ").append(toIndentedString(attendingPhysicianWrittenDate)).append("\n");
		sb.append("    hospicePhysicianId: ").append(toIndentedString(hospicePhysicianId)).append("\n");
		sb.append("    hospicePhysicianName: ").append(toIndentedString(hospicePhysicianName)).append("\n");
		sb.append("    hospicePhysicianVerbalDate: ").append(toIndentedString(hospicePhysicianVerbalDate)).append("\n");
		sb.append("    hospicePhysicianWrittenDate: ").append(toIndentedString(hospicePhysicianWrittenDate)).append("\n");
		sb.append("    certificationType: ").append(toIndentedString(certificationType)).append("\n");

		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
