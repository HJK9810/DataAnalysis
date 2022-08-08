package rtdownAnalys;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

public class ReadCSV {
	private static String DIRECTION = "C:\\javatest\\dataAnalysis\\ApartDeal\\";
	private static String SUJIFILE = "아파트(매매)__실거래가_20220808095913.csv";
	private static String SEOUL = "아파트(매매)__실거래가_20220808154349.csv";
	private static HashMap<String, List<String>> landDatas = new HashMap<String, List<String>>();
	private static HashSet<Integer> title = new HashSet<>();
	
	protected void DataInput() {
//		HashMap<String, List<String>> landDatas = new HashMap<String, List<String>>();
		File f = new File(DIRECTION + SEOUL);
		try {
			CSVReader read = new CSVReader(new FileReader(f));
			String[] readLine;
			int count = 0;
			List<String> datas = new ArrayList<>();

			while ((readLine = read.readNext()) != null) {
				count++;
				if (count < 17) continue;
				
				String keys = readLine[0] + "," + readLine[4] + "," + Math.floor(Double.parseDouble(readLine[5]));
				if(!landDatas.containsKey(keys)) datas = new ArrayList<>();
				else datas = landDatas.get(keys);

				String price = readLine[8].replace(",", "");
				title.add(Integer.parseInt(readLine[6]));
				datas.add(readLine[6] + "," + price);
//				System.out.println(readLine[4] + ", " + readLine[5]);
				landDatas.put(keys, datas);
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
		System.out.println();
	}
	
	protected void Datawrite() {
		File f = new File("C:\\javatest\\dataAnalysis\\Analysis\\월별서울아파트매매가.csv");
		try {
			CSVWriter write = new CSVWriter(new FileWriter(f));
			List<Integer> years = new ArrayList<Integer>(title);
			Collections.sort(years);
			List<String> indexTitle = new ArrayList<String>();
			
			indexTitle.add("시군구");
			indexTitle.add("단지명");
			indexTitle.add("전용면적(㎡)");
			for(int year : years) {
				indexTitle.add(Integer.toString(year));
			}
			
			write.writeNext(indexTitle.toArray(new String[indexTitle.size()]));
			
			for (Entry<String, List<String>> entry : landDatas.entrySet()) {
				String[] perDates = new String[15];
				List<String> data = entry.getValue();
				perDates[0] = entry.getKey().split(",")[0];
				perDates[1] = entry.getKey().split(",")[1];
				perDates[2] = entry.getKey().split(",")[2];
				String date = "";
				double sum = 0;
				int count = 0;
				int idx = 0;

				for(String line : data) {
					String[] ary = line.split(",");
					if(date.isEmpty()) date = ary[0];
					
			   		if(!date.equals(ary[0])) {
			   			idx = indexTitle.indexOf(date);
			   			
			   			if(sum != 0 && count != 0) perDates[idx] = String.format("%.2f", sum / count);
			   			date = ary[0];
			   			sum = Integer.parseInt(ary[1].replace(",", ""));
			   			count = 1;
			   		} else {
			   			sum += Integer.parseInt(ary[1].replace(",", ""));
			   			count++;
			   		}
				}
				
				idx = indexTitle.indexOf(date);
				perDates[idx] = String.format("%.2f", sum / count);
				write.writeNext(perDates);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void FileReadAll() {
		File f = new File(DIRECTION + SUJIFILE);
		try {
			CSVReader read = new CSVReader(new FileReader(f));
			String[] readLine;
			int count = 0;

			while ((readLine = read.readNext()) != null) {
				count++;
				if (count < 16) continue;
				System.out.printf("%-20s %8s %6s %8s %3s\n", readLine[4], readLine[5], readLine[6], readLine[8], readLine[9]);
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}
	
	protected void FileReadPerName() {
		for (Entry<String, List<String>> entry : landDatas.entrySet()) {
			List<String> data = entry.getValue();
			System.out.println("======================================");
			System.out.printf("%s : 전체 %d 건 거래\n", entry.getKey(), data.size());
			System.out.println("======================================");
			for(String line : data) {
				String[] ary = line.split(",");
		   		System.out.println("계약년월 : " + ary[0]);
		   		System.out.println("거래금액(만원) : " + ary[1]);
		   		System.out.println("--------------------------------------");
			}
			
	   		System.out.println("======================================");
		}
	}
}
