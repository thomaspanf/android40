package com.example.android40;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

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
    private static final long serialVersionUID =  4L;
    public static final String directory = "data";
    public static final String storedFile = "userData.ser";

    public ArrayList<Album> albumList;

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
        this.albumList = new ArrayList<>();

    }

    public void addAlbum(Album a){
        this.albumList.add(a);

    }

    public void createAlbumList(){
        this.albumList = new ArrayList<>();
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

    public void saveToDisk(Context context){
        ObjectOutputStream oos;
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(storedFile, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static DataSource loadFromDisk(Context context){
        DataSource ds = new DataSource();
        try {
            FileInputStream fis = context.openFileInput(storedFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ds = (DataSource) ois.readObject();

            if (ds.albumList == null) {
                ds.albumList = new ArrayList<Album>();
            }

            fis.close();
            ois.close();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
        return ds;
    }


}
