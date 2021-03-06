package com.bolue.scan.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.bolue.scan.greendao.entity.Participant;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PARTICIPANT".
*/
public class ParticipantDao extends AbstractDao<Participant, Long> {

    public static final String TABLENAME = "PARTICIPANT";

    /**
     * Properties of entity Participant.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Key = new Property(0, Long.class, "key", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property LessonId = new Property(2, int.class, "lessonId", false, "LESSON_ID");
        public final static Property UserId = new Property(3, int.class, "userId", false, "USER_ID");
        public final static Property CheckCode = new Property(4, String.class, "checkCode", false, "CHECK_CODE");
        public final static Property Account = new Property(5, String.class, "account", false, "ACCOUNT");
        public final static Property Status = new Property(6, int.class, "status", false, "STATUS");
        public final static Property Is_invited = new Property(7, boolean.class, "is_invited", false, "IS_INVITED");
    }


    public ParticipantDao(DaoConfig config) {
        super(config);
    }
    
    public ParticipantDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PARTICIPANT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: key
                "\"NAME\" TEXT," + // 1: name
                "\"LESSON_ID\" INTEGER NOT NULL ," + // 2: lessonId
                "\"USER_ID\" INTEGER NOT NULL ," + // 3: userId
                "\"CHECK_CODE\" TEXT," + // 4: checkCode
                "\"ACCOUNT\" TEXT," + // 5: account
                "\"STATUS\" INTEGER NOT NULL ," + // 6: status
                "\"IS_INVITED\" INTEGER NOT NULL );"); // 7: is_invited
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PARTICIPANT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Participant entity) {
        stmt.clearBindings();
 
        Long key = entity.getKey();
        if (key != null) {
            stmt.bindLong(1, key);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
        stmt.bindLong(3, entity.getLessonId());
        stmt.bindLong(4, entity.getUserId());
 
        String checkCode = entity.getCheckCode();
        if (checkCode != null) {
            stmt.bindString(5, checkCode);
        }
 
        String account = entity.getAccount();
        if (account != null) {
            stmt.bindString(6, account);
        }
        stmt.bindLong(7, entity.getStatus());
        stmt.bindLong(8, entity.getIs_invited() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Participant entity) {
        stmt.clearBindings();
 
        Long key = entity.getKey();
        if (key != null) {
            stmt.bindLong(1, key);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
        stmt.bindLong(3, entity.getLessonId());
        stmt.bindLong(4, entity.getUserId());
 
        String checkCode = entity.getCheckCode();
        if (checkCode != null) {
            stmt.bindString(5, checkCode);
        }
 
        String account = entity.getAccount();
        if (account != null) {
            stmt.bindString(6, account);
        }
        stmt.bindLong(7, entity.getStatus());
        stmt.bindLong(8, entity.getIs_invited() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Participant readEntity(Cursor cursor, int offset) {
        Participant entity = new Participant( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // key
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.getInt(offset + 2), // lessonId
            cursor.getInt(offset + 3), // userId
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // checkCode
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // account
            cursor.getInt(offset + 6), // status
            cursor.getShort(offset + 7) != 0 // is_invited
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Participant entity, int offset) {
        entity.setKey(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setLessonId(cursor.getInt(offset + 2));
        entity.setUserId(cursor.getInt(offset + 3));
        entity.setCheckCode(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setAccount(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setStatus(cursor.getInt(offset + 6));
        entity.setIs_invited(cursor.getShort(offset + 7) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Participant entity, long rowId) {
        entity.setKey(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Participant entity) {
        if(entity != null) {
            return entity.getKey();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Participant entity) {
        return entity.getKey() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
