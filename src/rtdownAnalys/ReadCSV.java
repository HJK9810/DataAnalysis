package rtdownAnalys;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

public class ReadCSV {
    private static String DIRECTION = "C:\\javatest\\dataAnalysis\\AptRent";
    private static HashMap<String, List<String>> landDatas = new HashMap<String, List<String>>();
    private static HashSet<Integer> title = new HashSet<>();

    public List getFileList() {
        File myDir = new File(DIRECTION);
        File[] contents = myDir.listFiles();
        List<File> fileList = new ArrayList<>();
        for (File temp : contents) {
            fileList.add(temp);
        }
        return fileList;
    }
    
    protected void DataInput(String fileName) {
        try {
            System.out.println(fileName);
            CSVReader read = new CSVReader(new InputStreamReader(new FileInputStream(fileName), "CP949"));
            String[] readLine;
            int count = 0;
            List<String> datas = new ArrayList<>();

            while ((readLine = read.readNext()) != null) {
                count++;
                if (count < 17) continue;

                if(!readLine[12].equals("")) continue;

                double floorSize = 0;
                String keys;
                int addN = 0;
                try {
                    floorSize = Double.parseDouble(readLine[5]);
                    keys = readLine[0] + "\t" + readLine[4] + "\t" + Math.floor(floorSize);
                } catch (NumberFormatException  e) {
                    keys = readLine[0] + "\t" + readLine[4]+","+ readLine[5] + "\t" + Math.floor(Double.parseDouble(readLine[6]));
                    addN = 1;
                }

                if (!landDatas.containsKey(keys)) datas = new ArrayList<>();
                else datas = landDatas.get(keys);

                String price = readLine[8 + addN].replace(",", "");
                title.add(Integer.parseInt(readLine[6]));
                datas.add(readLine[6 + addN] + "," + price);
                landDatas.put(keys, datas);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    protected void RentDataInput(String fileName) {
        try {
            System.out.println(fileName);
            CSVReader read = new CSVReader(new InputStreamReader(new FileInputStream(fileName), "CP949"));
            String[] readLine;
            int count = 0;
            List<String> datas = new ArrayList<>();

            while ((readLine = read.readNext()) != null) {
                count++;
                if (count < 17) continue;
                
                double floorSize = 0;
                String keys;
                int addN = 0;
                try {
                    floorSize = Double.parseDouble(readLine[6]);
                    keys = readLine[0] + "\t" + readLine[4] + "\t" + Math.floor(floorSize);
                } catch (NumberFormatException  e) {
                    keys = readLine[0] + "\t" + readLine[4]+","+ readLine[5] + "\t" + Math.floor(Double.parseDouble(readLine[7]));
                    addN = 1;
                }

                if (!landDatas.containsKey(keys)) datas = new ArrayList<>();
                else datas = landDatas.get(keys);

                String price = readLine[9 + addN].replace(",", "");
                title.add(Integer.parseInt(readLine[7]));
                datas.add(readLine[7 + addN] + "," + price);
                landDatas.put(keys, datas);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    protected void Datawrite() {
        File f = new File("C:\\javatest\\dataAnalysis\\Analysis\\전체월별서울아파트전세가.csv");
        try {
            CSVWriter write = new CSVWriter(new FileWriter(f));
            List<Integer> years = new ArrayList<Integer>(title);
            Collections.sort(years);
            List<String> indexTitle = new ArrayList<String>();

            indexTitle.add("시군구");
            indexTitle.add("단지명");
            indexTitle.add("전용면적(㎡)");
            for (int year : years) {
                indexTitle.add(Integer.toString(year));
            }

            write.writeNext(indexTitle.toArray(new String[indexTitle.size()]));

            for (Entry<String, List<String>> entry : landDatas.entrySet()) {
                String[] perDates = new String[indexTitle.size()];
                List<String> data = entry.getValue();
                perDates[0] = entry.getKey().split("\t")[0];
                perDates[1] = entry.getKey().split("\t")[1];
                perDates[2] = entry.getKey().split("\t")[2];
                String date = "";
                double sum = 0;
                int count = 0;
                int idx = 0;

                for (String line : data) {
                    String[] ary = line.split(",");
                    if (date.isEmpty()) date = ary[0];

                    if (!date.equals(ary[0])) {
                        idx = indexTitle.indexOf(date);
                        if(idx == -1) continue;

                        if (sum != 0 && count != 0) perDates[idx] = String.format("%.2f", sum / count);
                        date = ary[0];
                        sum = Integer.parseInt(ary[1].replace(",", ""));
                        count = 1;
                    } else {
                        sum += Integer.parseInt(ary[1].replace(",", ""));
                        count++;
                    }
                }
                idx = indexTitle.indexOf(date);
                if(idx == -1) continue;
                
                perDates[idx] = String.format("%.2f", sum / count);
                write.writeNext(perDates);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        landDatas.clear();
        title.clear();
    }
}