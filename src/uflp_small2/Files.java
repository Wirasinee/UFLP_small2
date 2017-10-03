package uflp_small2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Wirasinee
 */
public class Files {

    public int[] readFileInteger(String pathFile) throws FileNotFoundException  {

        File f = new File(pathFile);
        Scanner s = new Scanner(f);
        int ctr = 0;
        while (s.hasNext()) {
            ctr++;
            s.nextInt();
        }
        int[] arr = new int[ctr];
        Scanner s1 = new Scanner(f);

        for (int i = 0; i < arr.length; i++) {
            arr[i] = s1.nextInt();
        }
        return arr;

    }

}
