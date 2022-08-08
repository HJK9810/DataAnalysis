package chat;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class AnalysisKor {
	protected static List<String> Questions = new ArrayList<String>();
	protected static List<String> Answere = new ArrayList<String>();

	public static void main(String[] args) {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		FindWord();
		while(true) {
			try {
				Date date = new Date();
				System.out.println("==="+date+"================================");
				String str = printWord(input.readLine());
				
				if(str.equals("End")) {
					System.out.println("프로그램을 종료합니다.");
					break;
				} else {
					System.out.println(str);
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Error 발생 / 종료.");
				break;
			}
		}
	}
	
	public static String printWord(String strToExtrtKwrd) {
		if(strToExtrtKwrd.length() == 0) return "값을 입력해주세요.";
		
		KeywordExtractor ke = new KeywordExtractor();
		KeywordList kl = ke.extractKeyword(strToExtrtKwrd, true);
		for( int i = 0; i < kl.size(); i++ ) {
			String kwrd = kl.get(i).getString();
			int index = Questions.indexOf(kwrd);
			if(index != -1) return Answere.get(index);
			else if(kwrd.equals("종료")) return "End";
		}
		return "다시 입력해주세요.";
	}
	
	public static void FindWord() {
		File f = new File("C:\\javatest\\dataAnalysis\\chatter.csv");
		try {
			CSVReader read = new CSVReader(new FileReader(f));
			String[] readLine;

			while((readLine = read.readNext()) != null) {
				Questions.add(readLine[0]);
				Answere.add(readLine[1]);
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}
}