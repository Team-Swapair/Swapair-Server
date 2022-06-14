package com.swapair.server.opencv;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.opencv.core.*;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.ORB;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// 이미지 제대로 들아왔는지 확인하는 것 있어야 함
// 테스트 자동화는 엑셀로 값비교 예정
//회색이미지로도 비교해보자

class TestParams {
    public String from;
    public String to;
    public Boolean label;
}

public class FeatureDetect {

    public static List<TestParams> readExcel(String filename){
        List<TestParams> params = new ArrayList<>();
        try {
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fis);

            Sheet worksheet = workbook.getSheetAt(0);
            System.out.println(worksheet.getPhysicalNumberOfRows());

            for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
                Row row = worksheet.getRow(i);

                TestParams tp = new TestParams();

                tp.from = row.getCell(1).getStringCellValue();
                tp.to = row.getCell(2).getStringCellValue();
                if (row.getCell(3).getNumericCellValue() == 1) {
                    tp.label = true;
                } else {
                    tp.label = false;
                }

                params.add(tp);
            }

        } catch (FileNotFoundException e) {
            System.out.println("엑셀실패");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("엑셀실패");
            e.printStackTrace();
        }
        System.out.println("데이터 불러오기 완료");
        return params;
    }

    public static void testImage() {

    }

    public static void main(String[] args){

        File file = new File("test11.txt");
        FileWriter writer = null;


        //set image path
        String path = "C:\\Users\\gyul chyoung\\Pictures\\test\\";
//        String path = "C:\\Users\\gyul chyoung\\Pictures\\test\\from_01.jpeg";

        List<TestParams> params = readExcel("C:\\Projects\\swapair\\ImageTest.xlsx");
        int ret_num = 2; //유사 feature 개수가 몇개이상일 경우
        int dist = 30;  //기준이 되는 거리 (기준보다 가까우면 일치하는 특징)
        int corr = 0;   //예측 맞은 수
        int no_yes=0;   //다른데 같다고 한 경우
        int yes_no = 0; //같은데 다르다고 한 경우
        for (TestParams tp : params) {
            int ret = compareFeature(path+tp.from+".jpeg", path+tp.to+".jpeg", dist);
            boolean res;
            if (ret > ret_num) {
                res = true;
            } else {
                res = false;
            }
            if (res == tp.label) {
                corr++;
            }else{
                if(tp.label == true){
                    yes_no++;
                }else {
                    no_yes++;
                }
            }
        }


        double corr_rate = (double)corr/params.size();

        try {
            writer = new FileWriter(file, false);
            writer.write("test case : "+params.size());
            writer.write("\nreturn standard = "+ ret_num);
            writer.write("\ndistance standard = "+ dist );
            writer.write("\ncorrectness = "+ corr );
            writer.write("\ncorrectness rate = "+ corr_rate);
            writer.write("\n다른데 똑같다고 판단 = "+ no_yes);
            writer.write("\n똑같은데 다르다고 판단 = "+ yes_no);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        int ret = compareFeature(filename1, filename2);
//
//        if (ret > ret_num) {
//            System.out.println("Two images are same");
//        } else {
//            System.out.println("Two images are different");
//        }
    }

    /**
     * compare that two images is similar using feature mapping
     * @return integer count similarity within images
     */

    public static int compareFeature(String filename1, String filename2, int std_dist){
        int retVal =0;
        long startTime = System.currentTimeMillis();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        //Load images to compare`
//        Mat img1 = Imgcodecs.imread(filename1, Imgcodecs.IMREAD_REDUCED_GRAYSCALE_8);
//        Mat img2 = Imgcodecs.imread(filename2, Imgcodecs.IMREAD_REDUCED_GRAYSCALE_8);

        Mat img2 = Imgcodecs.imread(filename2, Imgcodecs.IMREAD_COLOR);
        Mat img1 = Imgcodecs.imread(filename1, Imgcodecs.IMREAD_COLOR);

        //Declare key point of images
        MatOfKeyPoint keyPoints1 = new MatOfKeyPoint();
        MatOfKeyPoint keyPoints2 = new MatOfKeyPoint();
        Mat descriptors1 = new Mat();
        Mat descriptors2 = new Mat();

        //Definition of ORB key point detector and descriptor extractors
        ORB orb = ORB.create(); //()안에 최대 검출 feature수 지정 가능
        orb.detectAndCompute(img1, new Mat(), keyPoints1,descriptors1);
        orb.detectAndCompute(img2, new Mat(), keyPoints2,descriptors2);

        //definition of descriptor matcher
        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);

        //Match points of two images
        MatOfDMatch matches = new MatOfDMatch();
        System.out.println("Type of Image1= " + descriptors1.type() + ", Type of Image2= " + descriptors2.type());
        System.out.println("Cols of Image1= " + descriptors1.cols() + ", Cols of Image2= " + descriptors2.cols());

        //Avoid assertion failed
        //Assertion failed (type == src2.type() && src1.cols == src2.cols && (type == CV_32F || type == CV_8U)
        if (descriptors2.cols() == descriptors1.cols()) {
            matcher.match(descriptors1, descriptors2, matches);

            //CHeck matches of key points
            DMatch[] match = matches.toArray();
            double max_dist = 0;
            double min_dist = 100;

            for (int i = 0; i < descriptors1.rows(); i++) {
                double dist = match[i].distance;
                if(dist<min_dist) min_dist = dist;
                if (dist>max_dist) max_dist = dist;
            }
//            System.out.println("max_dist= " + max_dist + ", min_dist= " + min_dist);

            //Extract good images (distance under 10)
            for (int i = 0; i < descriptors1.rows(); i++) {
                if (match[i].distance <= std_dist) {
                    retVal++;
                }
            }
            System.out.println("matching count= " + retVal);
        }
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("estimatedTime= " + estimatedTime + "ms");
        return retVal;

    }

}




