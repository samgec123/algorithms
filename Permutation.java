package com.test;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Permutation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Date date1 = new Date();
			char[] character = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
					'h' };
			Set<String> firstResult = new HashSet<String>();
			Set<String> intermediateResult = new HashSet<String>();
			Set<String> finalResult = new HashSet<String>();
			intermediateResult.add(String.valueOf(character[0])
					+ String.valueOf(character[1]));
			intermediateResult.add(String.valueOf(character[1])
					+ String.valueOf(character[0]));
			String tempStr = null;
			for (int i = 2; i < character.length; i++) {
				firstResult.clear();
				for (Iterator<String> iter = intermediateResult.iterator(); iter
						.hasNext();) {
					tempStr = iter.next();
					finalResult.clear();
					LinkedList<Character> list = new LinkedList<Character>();
					for (int j = 0; j <= tempStr.length(); j++) {
						list.clear();
						for (int k = 0; k < j; k++) {
							list.add(k, tempStr.charAt(k));
						}
						list.add(j, character[i]);
						for (int l = j; l < tempStr.length(); l++) {
							list.add(l + 1, tempStr.charAt(l));
						}
						String result = "";
						for (int m = 0; m < list.size(); m++) {
							result += list.get(m);
						}
						finalResult.add(result);
					}

					firstResult.addAll(finalResult);
				}
				intermediateResult.clear();
				intermediateResult.addAll(firstResult);

			}

			SortedSet<String> sortedSet = new TreeSet();
			sortedSet.addAll(firstResult);
			for (Iterator<String> iter = sortedSet.iterator(); iter.hasNext();) {
				String tempStr1 = iter.next();
				System.out.println(tempStr1);
			}
			Date date2 = new Date();
			System.out.println(date2.getTime() - date1.getTime());
		} catch (Exception e) {
			System.out.println("exception is:" + e.getMessage());
		}
	}
}
