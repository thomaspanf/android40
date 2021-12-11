package com.example.android40;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.example.android40.model.Album;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class DataSource implements Serializable {
    private static final long serialVersionUID = 111696345129311948L;
    public static final String directory = "data";
    public static final String storedFile = "userData.ser";
    private Album currentAlbum;
    public byte[] imageByteArray;

    public ArrayList<Album> albumList = new ArrayList<>();

    private static final String TAG = "DATASOURCE";
    public static ArrayList<Album> loadAlbums(){
        ArrayList<Album> albumList = new ArrayList<>();
        albumList.add(new Album("A"));
        albumList.add(new Album("B"));
        albumList.add(new Album("C"));
        albumList.add(new Album("D"));
        albumList.add(new Album("E"));
        return albumList;
    }

//    public DataSource(){
//        this.albumList = new ArrayList<>();
//        currentAlbum = null;
//    }

    public void addAlbum(Album a){
        this.albumList.add(a);

    }

    public void createAlbumList(){
        this.albumList = new ArrayList<>();
    }

    public void setCurrentAlbum(Album a){
        currentAlbum = a;
    }

    public Album getCurrentAlbum(){
        return currentAlbum;
    }

    public boolean deleteAlbum(Album a){
        if(this.albumList.size() == 0) return false;
        if(a == null) return false;
        for(Album albums : this.albumList){
            if(a.getName().equals(albums.getName())){
                this.albumList.remove(albums);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Album> getAlbumList(){
        return this.albumList;
    }

    public void setAlbumList(ArrayList<Album> albumList) {
        this.albumList = albumList;
    }

//    public void save(ArrayList<Album> arrayList, Context context){
//        ObjectOutputStream oos = null;
//        FileOutputStream fos = null;
//        try {
//            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
//            fos.write(stringBuilder.toString().getBytes());
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
//            objectOutputStream.writeObject(arrayList);
//            objectOutputStream.close();
//            FileOutputStream.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

//    public static ArrayList<Album> load(Context context) throws IOException, ClassNotFoundException {
//        //DataSource ds = new DataSource();
//        FileInputStream fis = null;
//        ObjectInputStream  ois = null;
//        try {
//            fis = context.openFileInput(storedFile);
//            ois = new ObjectInputStream(fis);
//
////
////            if (ds.albumList == null) {
////                ds.albumList = new ArrayList<Album>();
////            }
//
//
//        } catch (FileNotFoundException e) {
//            return null;
//        } catch (IOException e) {
//            return null;
//        } catch (Exception e) {
//            return null;
//        } finally {
//            if(fis != null){
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if(ois != null){
//                try {
//                    ois.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return (ArrayList<Album>) ois.readObject();
//    }

    public void saveToDisk(Context context){
        ObjectOutputStream oos = null;
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(storedFile, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(this);




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null ){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(oos != null ){
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static DataSource loadFromDisk(Context context) throws IOException, ClassNotFoundException {
        DataSource dataSource;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        fis = context.openFileInput(storedFile);
        ois = new ObjectInputStream(fis);
        dataSource = (DataSource) ois.readObject();



        if (dataSource.albumList == null) {
            dataSource.albumList = new ArrayList<Album>();
        }

        fis.close();
        ois.close();

        return dataSource;
//        try {
//            fis = context.openFileInput(storedFile);
//            ois = new ObjectInputStream(fis);
//            dataSource = (DataSource) ois.readObject();
//
//            if (dataSource.albumList == null) {
//                dataSource.albumList = new ArrayList<Album>();
//            }
//
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
//        } finally{
//            if(fis != null ){
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if(ois != null ){
//                try {
//                    ois.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }


    }


}
