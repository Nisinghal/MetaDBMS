import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class program1_2017302_2017094 {

	public static void main(String[] args) {

		String metadataFileName = "metadata.txt";
		BufferedReader metadataFileReader;
		int index = 0;
		String reqField = args[0];
		int reqIndex = -1;
		boolean exists = false;
		String type = "";
		ArrayList<Integer> size = new ArrayList<Integer>();
		int ind = -1;
		int bytesize = 0;
		int maxlen = 0;
		try {
			metadataFileReader = new BufferedReader(new FileReader(metadataFileName));
			String line = metadataFileReader.readLine();
			while (line != null) {
				String[] array = line.split(",");
				System.out.println("Field: " + array[0] + ", Type: " + array[1] + ", Size: " + array[2]);
				if (ind != -1)
					size.add(size.get(ind) + Integer.parseInt(array[2]));
				else
					size.add(Integer.parseInt(array[2]));
				ind++;
				if (array[0].toLowerCase().equals(reqField.toLowerCase())) {
					reqIndex = index;
					exists = true;
					maxlen = Integer.parseInt(array[2]);
					bytesize = size.get(size.size() - 1) - Integer.parseInt(array[2]);
					type = array[1];
				}
				index++;
				line = metadataFileReader.readLine();
			}
			metadataFileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.printf("\nFinish reading data description file...\n\n");
		ArrayList<String> vals = new ArrayList<>();

		String fileName = "db.data.txt";

		BufferedReader fileReader;
		try {
			fileReader = new BufferedReader(new FileReader(fileName));
			System.out.printf("The data file contains these records:\n\n");
			String line = fileReader.readLine();
			while (line != null) {
				System.out.println(line);
				char[] array = line.toCharArray();
				if (exists) {
					String element = "";
					if (bytesize + maxlen >= array.length) {
						element = new String(array, bytesize, array.length - bytesize);
					} else {
						element = new String(array, bytesize, maxlen);
					}
					vals.add(element);
				}
				line = fileReader.readLine();
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.printf("\nFind max value in the field " + reqField + "\n");

		if (!exists) {
			System.out.println("Error: field name not found.");
		} else {
			program1_2017302_2017094 M = new program1_2017302_2017094();
			if (type.equals("F")) {
				System.out.println(M.maxDoubleType(vals));
			} else if (type.equals("I")) {
				System.out.println(M.maxIntType(vals));
			} else {
				System.out.println(M.maxStringType(vals));
			}
		}
	}

	private String maxStringType(ArrayList<String> vals) {
		// TODO Auto-generated method stub
		String answer = "";
		int N = vals.size();
		for (int i = 0; i < N; i++) {
			String str = vals.get(i).trim();
			if (str.compareTo(answer) > 0) {
				answer = str;
			}
		}
		return answer;
	}

	public double maxDoubleType(ArrayList<String> arr) {
		double answer = Double.MIN_VALUE;
		int N = arr.size();
		for (int i = 0; i < N; i++) {
			String str = arr.get(i).trim();
			answer = Math.max(answer, Double.parseDouble(str));
		}
		return answer;
	}

	public int maxIntType(ArrayList<String> arr) {
		int answer = Integer.MIN_VALUE;
		int N = arr.size();
		for (int i = 0; i < N; i++) {
			answer = Math.max(answer, Integer.parseInt(arr.get(i).trim()));
		}
		return answer;
	}
}
