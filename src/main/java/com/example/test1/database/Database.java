package com.example.test1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.test1.model.Job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private final static String DB_NAME="test1";
    private final static int DATABASE_VERSION=1;
    public Database(@Nullable Context context) {
        super(context, DB_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table job("+
                "id integer primary key autoincrement, "+
                "name text,"+
                "content text,"+
                "finishDate text,"+
                "status text,"+
                "collaborate integer)";
        db.execSQL(sql);
    }
    public long addJob(Job job){
        ContentValues values = new ContentValues();
        values.put("name", job.getName());
        values.put("content",job.getContent());
        String[] date = job.getFinishDate().trim().split("/");
        String d = date[2]+"-"+date[1]+"-"+date[0];
        values.put("finishDate", d);
        values.put("status",job.getStatus());
        values.put("collaborate",job.isCollaborate());
        SQLiteDatabase st = getWritableDatabase();
        return st.insert("job",null,values);
    }
    public List<Job> getAll(){
        SQLiteDatabase st = getReadableDatabase();
        List<Job> list = new ArrayList<>();
        Cursor rs = st.query("job",null,null,null,null,null,null);
        while(rs!=null && rs.moveToNext()){
            int isCollab = rs.getInt(5);
            boolean b = true;
            if(isCollab==0) b=false;
            list.add(new Job(rs.getInt(0),rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),b));
        }
        rs.close();
        return list;
    }
    public List<Job> getByStatus(String status){
        SQLiteDatabase st = getReadableDatabase();
        List<Job> list = new ArrayList<>();
        String where="status=?";
        String[] args = {status};
        Cursor rs = st.query("job",null,where,args,null,null,null);
        while(rs!=null && rs.moveToNext()){
            int isCollab = rs.getInt(5);
            boolean b = true;
            if(isCollab==0) b=false;
            list.add(new Job(rs.getInt(0),rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),b));
        }
        rs.close();
        return list;
    }
    public long updateJob(Job job){
        ContentValues values = new ContentValues();
        values.put("name", job.getName());
        values.put("content",job.getContent());
        String[] date = job.getFinishDate().trim().split("/");
        String d = date[2]+"-"+date[1]+"-"+date[0];
        values.put("finishDate", d);
        values.put("status",job.getStatus());
        values.put("collaborate",job.isCollaborate());
        SQLiteDatabase sd = getWritableDatabase();
        String where = "id=?";
        String args[]={job.getId()+""};
        return  sd.update("job", values, where, args);
    }
    public int deleteJobById(int id){
        String where="id=?";
        String[] args = {id+""};
        SQLiteDatabase sd = getWritableDatabase();
        return sd.delete("job", where, args);
    }
    public List<Job> searchJobByKey(String key){
        List<Job> list = new ArrayList<>();
        String where = "name like ? or content like ?";
        String[] args = {"%"+key+"%", "%"+key+"%"};
        String orderBy = "finishDate asc";
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("job",null,where, args,null,null,orderBy);
        while(rs!=null && rs.moveToNext()){
            int isCollab = rs.getInt(5);
            boolean b = true;
            if(isCollab==0) b=false;
            list.add(new Job(rs.getInt(0),rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),b));
        }
        rs.close();
        return list;
    }
    public HashMap<String, Integer> countJobByStatus(){
        HashMap<String,Integer> list = new HashMap<>();
        String wherecout[] = {"status","count(*)"};
        String groupBy = "status";
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("job",wherecout,null, null,groupBy,null,null);
        while(rs!=null && rs.moveToNext()){
            list.put(rs.getString(0),rs.getInt(1));
        }
        rs.close();
        return list;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
