import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 *
 * Solution to the ACM Programming Contest Problem "FindingSeats"
 * https://icpcarchive.ecs.baylor.edu/external/39/3932.pdf
 *
 *
 */

public class FindingSeats {

	ArrayList<String> array;

	/**
	 * 
	 * Idea of summing the seats up so every entry can be calculated from its rectangles.
	 * 
	 * Algorithm in O(n^3 * log n)
	 * 
	 * @param seats
	 * @param persons
	 */
	private String calculateMinimumRectangle(int[][] seats, int persons) {
		int area = seats.length * seats[0].length;
		for (int i = seats.length - 1; i >= 0; i--) {
			for (int j = seats[i].length - 1; j >= 0; j--) {
				int max_k = i;
				for (int k = -1; k <= max_k; k++) {
					int min_l = -1;
					int max_l = j;
					while (max_l >= min_l) {
						int middle_l = min_l + ((max_l - min_l) / 2);
						int sub = 0;
						if (k >= 0) 
							sub += seats[k][j];
						if (middle_l >= 0) 
							sub += seats[i][middle_l];
						if (k >= 0 && middle_l >= 0) 
							sub -= seats[k][middle_l];
						if (seats[i][j] - sub >= persons) {
							int act = (i + 1) * (j + 1);
							if (middle_l >= 0)
								act -= (i + 1) * (middle_l + 1);
							if (k >= 0)
								act -= (k + 1) * (j + 1);
							if (k >= 0 && middle_l >= 0)
								act += (k + 1) * (middle_l + 1);
							if (act < area)
								area = act;
							min_l = middle_l + 1;
						} else {
							if (middle_l == -1) 
								k = max_k;
							max_l = middle_l - 1;
						}
					}

				}
			}
		}
		return "" + area;
	}

	//Helper Methods

	public static void main(String[] args) {
		if (args.length > 0) {
			if (args[0].contains("f")) new FindingSeats(false);
			else new FindingSeats(true);
		} else new FindingSeats(true);
	}

	FindingSeats(boolean live) {
		String s = "";
		String ret = "";
		if (!live) {
			readFile();
			s = readFileLine();
		} else s = readLine();

		while (!s.equals("0 0 0")) {
			String[] numberString = s.split(" ");
			int[] numbers = new int[3];
			for (int i = 0; i < numbers.length; i++) {
				if (numberString.length <= i)
					return;
				try {
					numbers[i] = Integer.parseInt(numberString[i]);
				} catch (NumberFormatException e) {
					System.out.println("Error! Exception: " + e);
				}

			}

			if (numbers[2] < 0)
				return;

			int[][] seats = new int[numbers[0]][numbers[1]];
			parseInput(seats, live);
			ret += calculateMinimumRectangle(seats, numbers[2]) + "\n";

			if (!live) s = readFileLine();
			else s = readLine();
		}
		System.out.println(ret);
	}

	/**
	 * 
	 * parses the readLine input into a summation of the aivalable seats which
	 * are in the rectangle
	 * 
	 * @param seats
	 */
	private void parseInput(int[][] seats, boolean live) {
		for (int i = 0; i < seats.length; i++) {
			String s = "";
			if (live)
				s = readLine();
			else
				s = readFileLine();

			for (int j = 0; j < s.length(); j++) {
				if (s.length() != seats[i].length) {
					return;
				}
				int curr = 0;
				if (i >= 1) {
					curr += seats[i - 1][j];
					if (j >= 1) {
						curr -= seats[i - 1][j - 1];
					}
				}
				if (j >= 1) {
					curr += seats[i][j - 1];
				}
				curr += s.charAt(j) == '.' ? 1 : 0;
				seats[i][j] = curr;
			}
		}
	}

	private String readLine() {
		String s = "";
		try {
			InputStreamReader converter = new InputStreamReader(System.in);
			BufferedReader in = new BufferedReader(converter);
			s = in.readLine();
		} catch (Exception e) {
			System.out.println("Error! Exception: " + e);
		}
		return s;
	}

	private void readFile() {
		array = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader("./FindingSeats-Testcase.txt"))) {
			for (String line; (line = br.readLine()) != null;) {
				array.add(line);
			}
		} catch (IOException e) {
			System.out.println("Error! Exception: " + e);
		}
	}

	private String readFileLine() {
		if (array.size() <= 0)
			return "0 0 0";
		String s = array.get(0);
		array.remove(0);
		return s;
	}
}
