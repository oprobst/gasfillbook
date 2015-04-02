package de.tsvmalsch.shared.model;

import java.util.Date;

import javax.persistence.Embeddable;

@Embeddable
public class UserRights {

	private Boolean isAuthorizedToBlendPartial = false;
	
	private Date lastBriefing= null;

	private Boolean isAuthorizedToFillNx40 = false;

	private Boolean isAuthorizedToUseMixer = false;

	public Boolean getIsAuthorizedToBlendPartial() {
		return isAuthorizedToBlendPartial;
	}

	public Boolean getIsAuthorizedToFillNx40() {
		return isAuthorizedToFillNx40;
	}

	public Boolean getIsAuthorizedToUseMixer() {
		return isAuthorizedToUseMixer;
	}

	public Date getLastBriefing() {
		return lastBriefing;
	}

	public void setIsAuthorizedToBlendPartial(Boolean isAuthorizedToBlendPartial) {
		this.isAuthorizedToBlendPartial = isAuthorizedToBlendPartial;
	}

	public void setIsAuthorizedToFillNx40(Boolean isAuthorizedToFillNx40) {
		this.isAuthorizedToFillNx40 = isAuthorizedToFillNx40;
	}
	
	public void setIsAuthorizedToUseMixer(Boolean isAuthorizedToUseMixer) {
		this.isAuthorizedToUseMixer = isAuthorizedToUseMixer;
	}

	public void setLastBriefing(Date lastBriefing) {
		this.lastBriefing = lastBriefing;
	}

}
