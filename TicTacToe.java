package com.test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import jdk.internal.org.objectweb.asm.tree.IntInsnNode;

public class TicTacToe {

	/**
	 * This is a tick tac toe program. It uses future moves to check computer's
	 * move. Empty position is denoted by e, computer move is denoted by 1 and
	 * player move is denoted by 0.
	 * 
	 * @author Sameer Bhalerao
	 */
	public static void main(String[] args) {
		char[][] initiArr = new char[][] { { 'h', 'h', 'h' },
				{ 'h', 'h', 'h' }, { 'h', 'h', 'h' } };
		boolean cont = true;
		while (true) {
			userStep(initiArr);
			if (checkWin(initiArr, '0')) {
				System.out.println("player wins");
				System.exit(0);
			}
			initiArr = systemStep(initiArr);
			if (checkWin(initiArr, '1')) {
				System.out.println("system wins");
				System.exit(0);
			}
		}

	}

	private static boolean checkWin(char[][] initiArr, char c) {
		// TODO Auto-generated method stub
		if (initiArr[0][0] == c && initiArr[0][1] == c && initiArr[0][2] == c) {
			return true;
		}
		if (initiArr[1][0] == c && initiArr[1][1] == c && initiArr[1][2] == c) {
			return true;
		}
		if (initiArr[2][0] == c && initiArr[2][1] == c && initiArr[2][2] == c) {
			return true;
		}
		if (initiArr[0][0] == c && initiArr[1][0] == c && initiArr[2][0] == c) {
			return true;
		}
		if (initiArr[0][1] == c && initiArr[1][1] == c && initiArr[2][1] == c) {
			return true;
		}
		if (initiArr[0][2] == c && initiArr[1][2] == c && initiArr[2][2] == c) {
			return true;
		}
		if (initiArr[0][0] == c && initiArr[1][1] == c && initiArr[2][2] == c) {
			return true;
		}
		if (initiArr[2][0] == c && initiArr[1][1] == c && initiArr[0][2] == c) {
			return true;
		}
		return false;
	}

	private static char[][] systemStep(char[][] initiArr) {
		// if winning, put that one

		int[] a = findWinningPos(initiArr);
		if (a[0] != -1) {
			initiArr[a[0]][a[1]] = '1';
			return initiArr;
		}
		a = findLosingPos(initiArr);
		if (a[0] != -1) {
			initiArr[a[0]][a[1]] = '1';
			return initiArr;
		}

		// look for two position ahead

		char[] b = convertToOneDim(initiArr);
		int ones = countChar(b, '1');
		int zeros = countChar(b, '0');
		int hs = countChar(b, 'h');
		char[] c = new char[9];
		for (int i = 0; i <= ones; i++) {
			c[i] = '1';
		}
		for (int i = ones + 1; i < zeros + ones + 1; i++) {
			c[i] = '0';
		}
		for (int i = zeros + ones + 1; i < zeros + ones + hs; i++) {
			c[i] = 'h';
		}

		// permutate(a)
		Set<String> oneStepAhead = permutate(c);
		Set<String> correctOneStepAhead = getCorrectMoves(b, oneStepAhead);
		// Set<String> allVals = permutate(b);
		// initiArr[a[0]][a[1]] = '1';
		// take any corrected step and that is the move my system

		if (!correctOneStepAhead.isEmpty()) {
			String str = correctOneStepAhead.iterator().next();
			char[] temp = new char[9];
			str.getChars(0, 9, temp, 0);
			initiArr = convertToTwoDim(temp);
		}
		return initiArr;
	}

	private static Set<String> getCorrectMoves(char[] b,
			Set<String> oneStepAhead) {
		Iterator<String> iter = oneStepAhead.iterator();
		Set<String> resultString = new HashSet<String>();
		while (iter.hasNext()) {
			String move = iter.next();
			char[] a = new char[9];
			move.getChars(0, 9, a, 0);
			if (oneStepAhead(b, a)) {
				resultString.add(String.copyValueOf(a));
			}
		}

		return resultString;
	}

	private static boolean oneStepAhead(char[] b, char[] a) {
		// TODO Auto-generated method stub
		int sum = 0;
		for (int i = 0; i < 9; i++) {
			if ((b[i] == '0' && a[i] == '0') || (b[i] == '1' && a[i] == '1')
					|| (b[i] == 'h' && a[i] == 'h')
					|| (b[i] == 'h' && a[i] == '1')) {
				sum += 1;
			}

		}

		return sum == 9 ? true : false;
	}

	private static int countChar(char[] b, char c) {
		// TODO Auto-generated method stub
		int sum = 0;
		for (int i = 0; i < 9; i++)
			if (b[i] == c)
				sum += 1;
		return sum;
	}

	private static void userStep(char[][] initiArr) {
		// TODO Auto-generated method stub
		while (true) {
			System.out
					.println("--------Player please choose position x,y----------------");
			Scanner scanner = new Scanner(System.in);
			String input = scanner.next();
			String[] xy = input.split(",");
			if (xy.length != 2) {
				// input string is not correct
				System.out
						.println("-----------input is not in correct format x,y-------------");
				continue;
			}
			if (initiArr[Integer.valueOf(xy[0])][Integer.valueOf(xy[1])] == 'h') {
				initiArr[Integer.valueOf(xy[0])][Integer.valueOf(xy[1])] = '0';
				break;
			} else {
				System.out
						.println("--------------Please select some other value-------------------");
				continue;
			}
		}
	}

	private static char[] convertToOneDim(char[][] twoDimArr) {
		char[] initiArr = new char[9];
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				initiArr[i * 3 + j] = twoDimArr[i][j];
		return initiArr;

	}

	private static char[][] convertToTwoDim(char[] oneDimArr) {
		char[][] initiArr = new char[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				initiArr[i][j] = oneDimArr[i * 3 + j];
			}
		}
		return initiArr;
	}

	private static char[][] movePlayer(char a[][], int i, int j) {
		a[i][j] = '0';
		return a;
	}

	private static char[][] moveComp(char a[][], int i, int j) {
		a[i][j] = '1';
		return a;
	}

	private static int[] findWinningPos(char[][] a) {
		// column wise position
		int[] pos = new int[] { -1, -1 };
		for (int i = 0; i < 3; i++) {
			if (a[i][0] == '1' && a[i][1] == '1' && a[i][2] == 'h') {
				pos[0] = i;
				pos[1] = 2;
				break;
			} else if (a[i][0] == '1' && a[i][1] == 'h' && a[i][2] == '1') {
				pos[0] = i;
				pos[1] = 1;
				break;
			} else if (a[i][0] == 'h' && a[i][1] == '1' && a[i][2] == '1') {
				pos[0] = i;
				pos[1] = 0;
				break;
			}
			if (a[0][i] == '1' && a[1][i] == '1' && a[2][i] == 'h') {
				pos[0] = 2;
				pos[1] = i;
				break;
			} else if (a[i][0] == '1' && a[i][1] == 'h' && a[i][2] == '1') {
				pos[0] = 1;
				pos[1] = i;
				break;
			} else if (a[i][0] == 'h' && a[i][1] == '1' && a[i][2] == '1') {
				pos[0] = 0;
				pos[1] = i;
				break;
			}
		}
		// check forward
		if (a[0][0] == 'h' && a[1][1] == '1' && a[2][2] == '1') {
			pos[0] = 0;
			pos[1] = 0;

		} else if (a[0][0] == '1' && a[1][1] == 'h' && a[2][2] == '1') {
			pos[0] = 1;
			pos[1] = 1;

		} else if (a[0][0] == '1' && a[1][1] == '1' && a[1][2] == 'h') {
			pos[0] = 2;
			pos[1] = 2;
		}

		// check backward
		if (a[0][2] == 'h' && a[1][1] == '1' && a[2][0] == '1') {
			pos[0] = 0;
			pos[1] = 2;

		} else if (a[0][2] == '1' && a[1][1] == 'h' && a[2][0] == '1') {
			pos[0] = 1;
			pos[1] = 1;

		} else if (a[0][2] == '1' && a[1][1] == '1' && a[2][0] == 'h') {
			pos[0] = 2;
			pos[1] = 0;
		}
		return pos;
	}

	private static int countRow(char[] a, char ch) {
		int sum = 0;
		for (int i = 0; i < a.length; i++)
			if (a[i] == ch)
				sum += 1;
		return sum;
	}

	private static int countColumn(char[][] a, char ch) {

		for (int i = 0; i < 3; i++) {
			int sum = 0;
			for (int j = 0; j < 3; j++) {
				if (a[j][i] == ch)
					sum += 1;
			}
			if (sum == 2)
				return i;
		}
		return 0;
	}

	private static int[] findLosingPos(char[][] a) {
		// column wise position
		int[] pos = new int[] { -1, -1 };
		for (int i = 0; i < 3; i++) {
			if (a[i][0] == '0' && a[i][1] == '0' && a[i][2] == 'h') {
				pos[0] = i;
				pos[1] = 2;
				break;
			} else if (a[i][0] == '0' && a[i][1] == 'h' && a[i][2] == '0') {
				pos[0] = i;
				pos[1] = 1;
				break;
			} else if (a[i][0] == 'h' && a[i][1] == '0' && a[i][2] == '0') {
				pos[0] = i;
				pos[1] = 0;
				break;
			}
			if (a[0][i] == '0' && a[1][i] == '0' && a[2][i] == 'h') {
				pos[0] = 2;
				pos[1] = i;
				break;
			} else if (a[i][0] == '0' && a[i][1] == 'h' && a[i][2] == '0') {
				pos[0] = 1;
				pos[1] = i;
				break;
			} else if (a[i][0] == 'h' && a[i][1] == '0' && a[i][2] == '0') {
				pos[0] = 0;
				pos[1] = i;
				break;
			}
		}
		// check forward
		if (a[0][0] == 'h' && a[1][1] == '0' && a[2][2] == '0') {
			pos[0] = 0;
			pos[1] = 0;

		} else if (a[0][0] == '0' && a[1][1] == 'h' && a[2][2] == '0') {
			pos[0] = 1;
			pos[1] = 1;

		} else if (a[0][0] == '0' && a[1][1] == '0' && a[1][2] == 'h') {
			pos[0] = 2;
			pos[1] = 2;
		}

		// check backward
		if (a[0][2] == 'h' && a[1][1] == '0' && a[2][0] == '0') {
			pos[0] = 0;
			pos[1] = 2;

		} else if (a[0][2] == '0' && a[1][1] == 'h' && a[2][0] == '0') {
			pos[0] = 1;
			pos[1] = 1;

		} else if (a[0][2] == '0' && a[1][1] == '0' && a[2][0] == 'h') {
			pos[0] = 2;
			pos[1] = 0;
		}
		return pos;
	}

	private static Set<String> permutate(char[] a) {
		Set<String> firstResult = new HashSet<String>();
		Set<String> intermediateResult = new HashSet<String>();
		Set<String> finalResult = new HashSet<String>();
		intermediateResult.add(String.valueOf(a[0]) + String.valueOf(a[1]));
		intermediateResult.add(String.valueOf(a[1]) + String.valueOf(a[0]));
		String tempStr = null;
		for (int i = 2; i < a.length; i++) {
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
					list.add(j, a[i]);
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
		return firstResult;
	}
}
