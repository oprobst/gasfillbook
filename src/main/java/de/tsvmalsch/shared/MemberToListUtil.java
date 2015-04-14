package de.tsvmalsch.shared;

import java.util.LinkedList;
import java.util.List;

import de.tsvmalsch.shared.model.Member;

public final class MemberToListUtil {
	/**
	 * TODO: Exception handling!
	 * 
	 * Convert a comma separated list of member numbers to a List of Member
	 * objects.
	 *
	 * @param list
	 *            the list of member numbers string, separated by comma.
	 * @return A list of all matching members.
	 */
	public static List<Member> commaSepListToMemberList(
			List<Member> memberList, String list) {
		List<Member> result = new LinkedList<>();
		String[] member = list.split(",");
		for (Member m : memberList) {
			int nr = m.getMemberNumber();
			for (String number : member) {
				if (number.trim().equals(Integer.toString(nr))) {
					result.add(m);
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * Convert a List of Member objects to a comma separated list of their
	 * numbers.
	 *
	 * @param list
	 *            the list of member objects.
	 * @return A string of the member numbers
	 */
	public static String memberListToCommaSeparatedString(List<Member> members) {

		StringBuilder sb = new StringBuilder();
		for (Member m : members) {
			sb.append(m.getMemberNumber());
			sb.append(", ");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();

	}

}
