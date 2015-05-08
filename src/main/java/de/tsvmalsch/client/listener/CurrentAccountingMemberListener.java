package de.tsvmalsch.client.listener;

import de.tsvmalsch.shared.model.Member;

/**
 * Will be notified if the accounted member change.
 * 
 * @author Oliver Probst
 */
public interface CurrentAccountingMemberListener {

	void accountedMember(Member theOneWhoPays);
}
