package com.swapair.server.opencv;


import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CropImage {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        String filename1 = "C:\\Users\\gyul chyoung\\Pictures\\test\\origins\\test2.jpg";
        Mat img1 = Imgcodecs.imread(filename1);

        Size size = new Size(600,600);
        Imgproc.resize(img1,img1,size);

        Mat img_gray = Imgcodecs.imread(filename1, Imgcodecs.IMREAD_GRAYSCALE);
        Imgproc.resize(img_gray,img_gray,size);

//        HighGui.imshow("Image", img1);
//        HighGui.waitKey(0);
//        HighGui.destroyAllWindows();
        Size ksize = new Size(3, 3);
        double sigmaX=0;
        Mat img_blur = new Mat();
        Imgproc.GaussianBlur(img_gray, img_blur, ksize, sigmaX);
        Mat img_bin = new Mat();
        Imgproc.threshold(img_blur, img_bin, 127, 255, Imgproc.THRESH_BINARY); //thresh127,0
//        HighGui.imshow("Image", img_bin);
//        HighGui.waitKey(0);
//        HighGui.destroyAllWindows();

        Mat edged = new Mat();
        Imgproc.Canny(img_blur, edged,80, 250); // 1은 low 2는 high 그값 사이만 추출됨

        Mat kernel = new Mat();
        Mat closed = new Mat();
        Imgproc.getStructuringElement(Imgproc.MORPH_RECT,ksize);
        Imgproc.morphologyEx(edged,closed, Imgproc.MORPH_CLOSE, kernel);
        HighGui.imshow("Image", closed);
        HighGui.waitKey(0);
        HighGui.destroyAllWindows();

        List<MatOfPoint> contours = new ArrayList<>();
        Mat closed_copy = new Mat();
        Mat hierarchy = new Mat();
        closed.copyTo(closed_copy);
        Imgproc.findContours(closed_copy,contours,hierarchy,Imgproc.RETR_EXTERNAL,Imgproc.CHAIN_APPROX_SIMPLE);

        List<MatOfPoint> new_contours = new ArrayList<>();

        if (contours.size() > 0) {
            for (int idx = 0; idx < contours.size(); idx++) {
                Rect rect = Imgproc.boundingRect(contours.get(idx));
                if (rect.height > 80 && rect.width > 80) {  //이미지가 너무 클때도 자르는건 어떨까
                    new_contours.add(contours.get(idx));
                }
            }
        }

        System.out.println("num of object "+new_contours.size());
        Mat contours_image = new Mat();
        Imgproc.resize(img1,contours_image,size);
        Scalar color = new Scalar(0,255,0);
        Imgproc.drawContours(contours_image, new_contours, -1,color,3);

//        HighGui.imshow("Image", contours_image);
//        HighGui.waitKey(0);
//        HighGui.destroyAllWindows();




        int x_min =0,x_max = 0;
        int y_min =0,y_max = 0;
        List<Double> value_x = new ArrayList<>();
        List<Double> value_y = new ArrayList<>();
        Point[] points;
        for (int i = 0; i < new_contours.size(); i++) {
            x_min =0;
            x_max = 0;
            y_min =0;
            y_max = 0;
            value_x.clear();
            value_y.clear();

            MatOfPoint mp = new_contours.get(i);
            points = mp.toArray();
            for (int j = 0; j < points.length; j++) {
                value_x.add(points[j].x);
                value_y.add(points[j].y);
            }
            x_max= Collections.max(value_x).intValue();
            x_min = Collections.min(value_x).intValue();
            y_max = Collections.max(value_y).intValue();
            y_min = Collections.min(value_y).intValue();
            System.out.println("max, min x "+x_max+" "+x_min+" y "+y_max+" "+y_min);

            int x = x_min;
            int y = y_min;
            int w = x_max - x_min;
            int h = y_max - y_min;

            Rect rectCrop = new Rect(x,y,w,h);
            Mat cropImg = new Mat(img1, rectCrop);

            HighGui.imshow("Image", cropImg);
            HighGui.waitKey(0);
            HighGui.destroyAllWindows();
        }



    }



}
