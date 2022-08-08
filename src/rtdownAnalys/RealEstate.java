package rtdownAnalys;

import java.util.Scanner;

public class RealEstate {

	public static void main(String[] args) {
		ReadCSV rc = new ReadCSV();
		Scanner input = new Scanner(System.in);
		rc.DataInput();
		rc.Datawrite();
		
//		System.out.println("확인하고자 하는 데이터를 입력해주세요.");
//		System.out.println(" 1. 전체 데이터 출력");
//		System.out.println(" 2. 단지별 데이터 출력");
//		int num = input.nextInt();
//		
//		if(num == 1) rc.FileReadAll();
//		else if(num == 2) rc.FileReadPerName();
	}
}