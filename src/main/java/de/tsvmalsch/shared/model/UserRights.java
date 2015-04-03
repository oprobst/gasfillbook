package de.tsvmalsch.shared.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Embeddable
public class UserRights implements Serializable {

	/**
	 * suid
	 */
	private static final long serialVersionUID = -3387478939230147281L;

	private Date lastBriefing = null;

	public Date getLastBriefing() {
		return lastBriefing;
	}

	public void setLastBriefing(Date lastBriefing) {
		this.lastBriefing = lastBriefing;
	}

	public BlendingType getBlendingAuthorization() {
		return blendingAuthorization;
	}

	public void setBlendingAuthorization(BlendingType blendingAuthorization) {
		this.blendingAuthorization = blendingAuthorization;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	private BlendingType blendingAuthorization = BlendingType.AIR;

}
