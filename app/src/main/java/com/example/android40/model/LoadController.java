//package com.example.android40.model;
//
//import java.io.*;
//import java.util.ArrayList;
//import android.content.Context;
//
//public class LoadController implements Serializable {
//    /**
//     *
//     */
//    private static final long serialVersionUID = 0L;
//    public static final String fileName = "data.dat";
//    public ArrayList<Album> albums;
//     static Album searchResults = new Album("results");
//
//
//    public static void loadFromDisk(Context context) {
//        LoadController pa = null;
//        try {
//            FileInputStream fis = context.openFileInput(fileName);
//            ObjectInputStream ois = new ObjectInputStream(fis);
//            pa = (LoadController) ois.readObject();
//
//            if (pa.albums == null) {
//                pa.albums = new ArrayList<Album>();
//            }
//            fis.close();
//            ois.close();
//        } catch (FileNotFoundException e) {
//            return null;
//        } catch (IOException e) {
//            return null;
//        } catch (ClassNotFoundException e) {
//            return null;
//        } catch (Exception e) {
//            return null;
//        }
//        return pa;
//    }
//
//    public void saveToDisk(Context context){
//        ObjectOutputStream oos;
//        try {
//            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
//            objectOutputStream.writeObject(this);
//            objectOutputStream.close();
//            fileOutputStream.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//}
