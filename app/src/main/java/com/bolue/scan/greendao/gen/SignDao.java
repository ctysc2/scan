package com.bolue.scan.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.bolue.scan.greendao.entity.Sign;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SIGN".
*/
public class SignDao extends AbstractDao<Sign, Long> {

    public static final String TABLENAME = "SIGN";

    /**
     * Properties of entity Sign.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Key = new Property(0, Long.class, "key", true, "_id");
        public final static Property Id = new Property(1, int.class, "id", false, "ID");
        public final static Property CheckCode = new Property(2, String.class, "checkCode", false, "CHECK_CODE");
        public final static Property UserName = new Property(3, String.class, "userName", false, "USER_NAME");
    }


    public SignDao(DaoConfig config) {
        super(config);
    }
    
    public SignDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SIGN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: key
                "\"ID\" INTEGER NOT NULL ," + // 1: id
                "\"CHECK_CODE\" TEXT," + // 2: checkCode
                "\"USER_NAME\" TEXT);"); // 3: userName
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SIGN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Sign entity) {
        stmt.clearBindings();
 
        Long key = entity.getKey();
        if (key != null) {
            stmt.bindLong(1, key);
        }
        stmt.bindLong(2, entity.getId());
 
        String checkCode = entity.getCheckCode();
        if (checkCode != null) {
            stmt.bindString(3, checkCode);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(4, userName);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Sign entity) {
        stmt.clearBindings();
 
        Long key = entity.getKey();
        if (key != null) {
            stmt.bindLong(1, key);
        }
        stmt.bindLong(2, entity.getId());
 
        String checkCode = entity.getCheckCode();
        if (checkCode != null) {
            stmt.bindString(3, checkCode);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(4, userName);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Sign readEntity(Cursor cursor, int offset) {
        Sign entity = new Sign( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // key
            cursor.getInt(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // checkCode
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // userName
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Sign entity, int offset) {
        entity.setKey(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setId(cursor.getInt(offset + 1));
        entity.setCheckCode(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUserName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Sign entity, long rowId) {
        entity.setKey(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Sign entity) {
        if(entity != null) {
            return entity.getKey();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Sign entity) {
        return entity.getKey() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
