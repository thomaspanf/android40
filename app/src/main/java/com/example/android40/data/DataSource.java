package com.example.android40.data;

import android.util.Log;

import com.example.android40.model.Album;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class DataSource implements Serializable {
    private static final long serialVersionUID =  4L;
    public static final String directory = "data";
    public static final String storedFile = "userData";

    private ArrayList<Album> albumList;

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

    public DataSource(){
        albumList = new ArrayList<>();
    }

    public void addAlbum(Album a){
        albumList.add(a);
    }

    public boolean deleteAlbum(Album a){
        if(albumList.size() == 0) return false;
        if(a == null) return false;
        for(Album albums : albumList){
            if(a.getName().equals(albums.getName())){
                albumList.remove(albums);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Album> getAlbumList(){
        return albumList;
    }

    public void setAlbumList(ArrayList<Album> albumList) {
        this.albumList = albumList;
    }



    //    public static void getDataText() {
//        File file = new File(File.p"data");
//        Log.d(TAG, "getDataText: " + file.exists());
//    }

    public ArrayList<Album> load() throws FileNotFoundException, IOException, ClassNotFoundException {

        ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(directory + File.separator + storedFile)
        );
        return (ArrayList<Album>) ois.readObject();
    }

    public void save(ArrayList<Album> albumList) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(directory + File.separator + storedFile));
        Log.d(TAG, "save: " + albumList.get(0).getName());
        oos.writeObject(albumList);
    }
}
