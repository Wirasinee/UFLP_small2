package uflp_small2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Wirasinee
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class testUFLP {
    
    static Scanner in = new Scanner(System.in);
    static Files file = new Files();
    static int[] inputFile = null;
    static int index = 0;
    static int n, m;
    static int[] w;//ต้นทุนแต่ละโกดัง
    static int sumW = 0;//ต้นทุนรวม
    static int[][] distance;
    static Properties textXml = new Properties();
    static int s ;
    public static void main(String[] args) {

        testUFLP uflp = new testUFLP();
        uflp.getXML();
        System.out.println(textXml.getProperty("file.path"));//C:\\Users\\Wirasinee\\Desktop\\input_test1.txt
        try {
            String pathFile = in.next();
           
            uflp.inputFile = file.readFileInteger(pathFile);
        } catch (FileNotFoundException ex) {
            System.err.println(textXml.getProperty("email.support"));
        }
        uflp.inputAll(uflp.inputFile);
        s=((((n-1)*(n-2))/2)+1)+(((n*(n-1))/2)+1);
        int[] min = new int[m]; // [0,0,0]
        int[][] contectPeopleStatus = new int[s][n]; //จำนวนรวมของลูกค้าแต่ละโกดัง
        int[][] statusNearCustomer = new int[n][m]; // เก็บ โกดังที่ใกล้กับลูกค้า
        int C = Integer.MAX_VALUE;
        int[] openStatus = new int[n];
        int[] open = new int[n];
        int[] sumMinDistance = new int[s]; //ผลรวมเส้นทางสั่นสุดของแต่ละลูกค้า
        for (int q = 1; q <= s; q++) {
            String openString = String.format(("%"+n+"s"), Integer.toBinaryString(q)).replace(' ', '0');
            for (int i = 0; i < openString.length(); i++) {
                open[i] = (Integer.parseInt(openString.charAt(i) + ""));
            }

            //System.out.println(">" + Arrays.toString(open) + "  ||  ");
            statusNearCustomer = new int[n][m];
            for (int j = 0; j < m; j++) {

                int minDistance = Integer.MAX_VALUE;
                for (int i = 0; i < n; i++) {

                    if (open[i] == 1) { //ถ้าลองให้มันเปิด

                        if (minDistance >= distance[i][j]) { //ถ้าระยะทางลูกค้าไปโกดังเป็นเส้นทางสั่นสุด
                            minDistance = distance[i][j];
                            statusNearCustomer[i][j] = 1;              //ให้เปิดโกดัง
                            for (int z = i - 1; z >= 0; z--) {
                                statusNearCustomer[z][j] = 0;
                            }
                        }
                    }

                }
                for (int i = 0; i < n; i++) {
                    if (statusNearCustomer[i][j] >= 1) {

                        contectPeopleStatus[q - 1][i] += statusNearCustomer[i][j];

                    }
                }//System.out.println(contectPeopleStatus[q]);
                min[j] = minDistance; //จะได้ระยะทางเส้นทางสั่นสุดของแต่ละลูกค้า
                sumMinDistance[q - 1] += min[j];

            }
            sumW = 0;
            for (int i = 0; i < n; i++) {
                if (contectPeopleStatus[q - 1][i] >= 1) {
                    sumW += w[i];

                }
            }
            //System.out.println("--" + sumW);
            if ((sumW + sumMinDistance[q - 1]) <= C) {

                C = sumW + sumMinDistance[q - 1];
                openStatus = open.clone();

            }
        }
        
        System.out.println("_________________________________");
        System.out.println(textXml.getProperty("status.service"));
        for (int q = 1; q <= s; q++) {
            System.out.print("["+String.format("%3s", Integer.toBinaryString(q)).replace(' ', '0') + "] ");
            for (int i = 0; i < n; i++) {
                System.out.print(contectPeopleStatus[q - 1][i]+" ");
            }
            System.out.println();

        }

        System.out.println("_________________________________");
        System.out.println(textXml.getProperty("C(S)")+ C);
        System.out.println(textXml.getProperty("status.open")+ Arrays.toString(openStatus));
        System.out.print(textXml.getProperty("status"));
        for(int j=0;j<n;j++){
            if(openStatus[j]>=1){
                System.out.print(j+1+" ");
            }
        }

    }

    public static void inputAll(int[] file) {

        System.out.println(textXml.getProperty("status.(n)")+": "+ inputFile[index]);
        n = inputFile[index++]; //จ โกดัง
        System.out.println(textXml.getProperty("m.(m)")+": "+ inputFile[index]);
        m = inputFile[index++]; //จ ลูกค้า
        distance = new int[n][m];
        System.out.println(textXml.getProperty("w"));
        w = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.println(textXml.getProperty("status")+ (i + 1) + " : " + inputFile[index]);
            w[i] = file[index];
            index++;
        }
        System.out.println("distance[ลูกค้า\\คลัง]: ");//ระยะทาง

        for (int j = 0; j < m; j++) {
            System.out.println(textXml.getProperty("m") + (j + 1) + " : ");
            for (int i = 0; i < n; i++) {
                System.out.print(file[index] + " ");
                distance[i][j] = file[index++];
            }
            System.out.println();
        }
    }
    
    public static void getXML(){
    InputStream is = null;
        try {
            is = new FileInputStream("src\\uflp_small2\\stringUFLP.xml");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(testUFLP.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //load the xml file into properties format
            textXml.loadFromXML(is);
        } catch (IOException ex) {
            Logger.getLogger(testUFLP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
