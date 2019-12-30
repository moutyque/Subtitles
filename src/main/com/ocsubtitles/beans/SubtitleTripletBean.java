package com.ocsubtitles.beans;

import java.time.LocalTime;

public class SubtitleTripletBean {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + (int) (number ^ (number >>> 32));
		result = prime * result + ((originalFileName == null) ? 0 : originalFileName.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubtitleTripletBean other = (SubtitleTripletBean) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (number != other.number)
			return false;
		if (originalFileName == null) {
			if (other.originalFileName != null)
				return false;
		} else if (!originalFileName.equals(other.originalFileName))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SubtitleTripletBean [number=" + number + ", start=" + start + ", end=" + end + ", text=" + text
				+ ", fileName=" + originalFileName + "]";
	}
	public static final String splitTimeString = " --> ";
	private long number = 0;
	private LocalTime  start;
	private LocalTime end;
	private String text ="";
	private String originalFileName="";

	
	public long getNumber() {
		return number;
	}
	public LocalTime getStart() {
		return start;
	}
	public void setStart(LocalTime start) {
		this.start = start;
	}
	public LocalTime getEnd() {
		return end;
	}
	public void setEnd(LocalTime end) {
		this.end = end;
	}
	public String getText() {
		return text;
	}
	public void setText(String txt) {
		this.text = txt;
	}

	
public SubtitleTripletBean (long l) {
	this.number = l;
}

public SubtitleTripletBean() {

}

public String getOriginalFileName() {
	return originalFileName;
}
public void setOriginalFileName(String fileName) {
	this.originalFileName = fileName;
}





}
