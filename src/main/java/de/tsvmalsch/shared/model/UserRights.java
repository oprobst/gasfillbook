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

	public int getBlendingAuthorization() {
		return blendingAuthorization;
	}

	public void setBlendingAuthorization(int blendingAuthorization) {
		this.blendingAuthorization = blendingAuthorization;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	private int blendingAuthorization = BlendingType.AIR;

	private boolean briefingRequired() {
		Date minimumBriefingDate = new Date(System.currentTimeMillis() - 1000
				* 60 * 60 * 24 * (365 + 30));
		return (minimumBriefingDate.compareTo(this.lastBriefing) <= 0);

	}

	public boolean isAllowedToFillAir() {
		return briefingRequired();
	}

	public boolean isAllowedToFillNx40() {
		return briefingRequired()
				&& blendingAuthorization >= BlendingType.NX40_CASCADE;
	}

	public boolean isAllowedToFillPartial() {
		return briefingRequired()
				&& blendingAuthorization >= BlendingType.PARTIAL_METHOD;
	}

	public boolean isAllowedToFillMixer() {
		return briefingRequired()
				&& blendingAuthorization >= BlendingType.MIXER;
	}
}
