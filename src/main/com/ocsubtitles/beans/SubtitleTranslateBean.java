package com.ocsubtitles.beans;

public class SubtitleTranslateBean {


	private SubtitleTripletBean triplet;
	private String translation;
	
	public long getNumber() {
		return triplet.getNumber();
	}
	public String getStart() {
		return triplet.getStart().toString();
	}
	public String getEnd() {
		return triplet.getEnd().toString();
	}
	public String getText() {
		return triplet.getText();
	}
	
	public String getTranslation() {
		if(null==translation) {
			return " ";
		}
		return translation;
	}
	public void setTranslation(String otherLangageText) {
		this.translation = otherLangageText;
	}
	
	public SubtitleTranslateBean(SubtitleTripletBean triplet,String text) {
		this.triplet = triplet;
		this.translation = text;
	}
	
	public SubtitleTranslateBean(SubtitleTripletBean triplet) {
		this.triplet = triplet;
		this.translation = null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((translation == null) ? 0 : translation.hashCode());
		result = prime * result + ((triplet == null) ? 0 : triplet.hashCode());
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
		SubtitleTranslateBean other = (SubtitleTranslateBean) obj;
		if (translation == null) {
			if (other.translation != null)
				return false;
		} else if (!translation.equals(other.translation))
			return false;
		if (triplet == null) {
			if (other.triplet != null)
				return false;
		} else if (!triplet.equals(other.triplet))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SubtitleTranslateBean [triplet=" + triplet.toString() + ", translation=" + translation + "]";
	}
	
}
