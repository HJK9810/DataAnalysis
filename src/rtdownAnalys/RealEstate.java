package rtdownAnalys;

import java.util.List;

public class RealEstate {

	public static void main(String[] args) {
		ReadCSV rc = new ReadCSV();

		List fileList = rc.getFileList();
		for(int i =0;i<fileList.size();i++) {
			System.out.println("No. "+ i + " file start");
			rc.DataInput(fileList.get(i).toString());
			System.out.println("No. "+ i + " file clear");
		}
		rc.Datawrite();
	}
}