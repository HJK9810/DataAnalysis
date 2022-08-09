package rtdownAnalys;

import java.util.List;

public class RealEstate {
	
	protected void process() {
		ReadCSV rc = new ReadCSV();
		FileAdr adress = new FileAdr();
		
		for(String filename : adress.FileNames) {
			List fileList = rc.getFileList(adress.DIRECTION + filename);
			for(int i =0;i<fileList.size();i++) {
				if(filename.contains("Rent")) rc.RentDataInput(fileList.get(i).toString());
				else if(filename.equals("DasedaeTrade")) rc.RentDataInput(fileList.get(i).toString());
				else rc.DataInput(fileList.get(i).toString());
			}
			rc.Datawrite(filename + adress.All);
			System.out.println(filename + " write clear");
		}
	}
}