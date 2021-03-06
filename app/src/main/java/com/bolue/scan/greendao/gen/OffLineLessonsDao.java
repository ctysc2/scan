package com.bolue.scan.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.bolue.scan.greendao.entity.OffLineLessons;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "OFF_LINE_LESSONS".
*/
public class OffLineLessonsDao extends AbstractDao<OffLineLessons, Long> {

    public static final String TABLENAME = "OFF_LINE_LESSONS";

    /**
     * Properties of entity OffLineLessons.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Key = new Property(0, Long.class, "key", true, "_id");
        public final static Property Id = new Property(1, Long.class, "id", false, "ID");
        public final static Property Title = new Property(2, String.class, "title", false, "TITLE");
        public final static Property Brief_image = new Property(3, String.class, "brief_image", false, "BRIEF_IMAGE");
        public final static Property Start_time = new Property(4, long.class, "start_time", false, "START_TIME");
        public final static Property End_time = new Property(5, long.class, "end_time", false, "END_TIME");
        public final static Property Status = new Property(6, int.class, "status", false, "STATUS");
        public final static Property Join_num = new Property(7, String.class, "join_num", false, "JOIN_NUM");
        public final static Property Site = new Property(8, String.class, "site", false, "SITE");
        public final static Property Longitude = new Property(9, double.class, "longitude", false, "LONGITUDE");
        public final static Property Latitude = new Property(10, double.class, "latitude", false, "LATITUDE");
        public final static Property Enroll_count = new Property(11, int.class, "enroll_count", false, "ENROLL_COUNT");
        public final static Property UserName = new Property(12, String.class, "userName", false, "USER_NAME");
    }


    public OffLineLessonsDao(DaoConfig config) {
        super(config);
    }
    
    public OffLineLessonsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"OFF_LINE_LESSONS\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: key
                "\"ID\" INTEGER," + // 1: id
                "\"TITLE\" TEXT," + // 2: title
                "\"BRIEF_IMAGE\" TEXT," + // 3: brief_image
                "\"START_TIME\" INTEGER NOT NULL ," + // 4: start_time
                "\"END_TIME\" INTEGER NOT NULL ," + // 5: end_time
                "\"STATUS\" INTEGER NOT NULL ," + // 6: status
                "\"JOIN_NUM\" TEXT," + // 7: join_num
                "\"SITE\" TEXT," + // 8: site
                "\"LONGITUDE\" REAL NOT NULL ," + // 9: longitude
                "\"LATITUDE\" REAL NOT NULL ," + // 10: latitude
                "\"ENROLL_COUNT\" INTEGER NOT NULL ," + // 11: enroll_count
                "\"USER_NAME\" TEXT);"); // 12: userName
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"OFF_LINE_LESSONS\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, OffLineLessons entity) {
        stmt.clearBindings();
 
        Long key = entity.getKey();
        if (key != null) {
            stmt.bindLong(1, key);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(2, id);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        String brief_image = entity.getBrief_image();
        if (brief_image != null) {
            stmt.bindString(4, brief_image);
        }
        stmt.bindLong(5, entity.getStart_time());
        stmt.bindLong(6, entity.getEnd_time());
        stmt.bindLong(7, entity.getStatus());
 
        String join_num = entity.getJoin_num();
        if (join_num != null) {
            stmt.bindString(8, join_num);
        }
 
        String site = entity.getSite();
        if (site != null) {
            stmt.bindString(9, site);
        }
        stmt.bindDouble(10, entity.getLongitude());
        stmt.bindDouble(11, entity.getLatitude());
        stmt.bindLong(12, entity.getEnroll_count());
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(13, userName);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, OffLineLessons entity) {
        stmt.clearBindings();
 
        Long key = entity.getKey();
        if (key != null) {
            stmt.bindLong(1, key);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(2, id);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        String brief_image = entity.getBrief_image();
        if (brief_image != null) {
            stmt.bindString(4, brief_image);
        }
        stmt.bindLong(5, entity.getStart_time());
        stmt.bindLong(6, entity.getEnd_time());
        stmt.bindLong(7, entity.getStatus());
 
        String join_num = entity.getJoin_num();
        if (join_num != null) {
            stmt.bindString(8, join_num);
        }
 
        String site = entity.getSite();
        if (site != null) {
            stmt.bindString(9, site);
        }
        stmt.bindDouble(10, entity.getLongitude());
        stmt.bindDouble(11, entity.getLatitude());
        stmt.bindLong(12, entity.getEnroll_count());
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(13, userName);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public OffLineLessons readEntity(Cursor cursor, int offset) {
        OffLineLessons entity = new OffLineLessons( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // key
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // title
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // brief_image
            cursor.getLong(offset + 4), // start_time
            cursor.getLong(offset + 5), // end_time
            cursor.getInt(offset + 6), // status
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // join_num
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // site
            cursor.getDouble(offset + 9), // longitude
            cursor.getDouble(offset + 10), // latitude
            cursor.getInt(offset + 11), // enroll_count
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12) // userName
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, OffLineLessons entity, int offset) {
        entity.setKey(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setBrief_image(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setStart_time(cursor.getLong(offset + 4));
        entity.setEnd_time(cursor.getLong(offset + 5));
        entity.setStatus(cursor.getInt(offset + 6));
        entity.setJoin_num(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setSite(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setLongitude(cursor.getDouble(offset + 9));
        entity.setLatitude(cursor.getDouble(offset + 10));
        entity.setEnroll_count(cursor.getInt(offset + 11));
        entity.setUserName(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(OffLineLessons entity, long rowId) {
        entity.setKey(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(OffLineLessons entity) {
        if(entity != null) {
            return entity.getKey();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(OffLineLessons entity) {
        return entity.getKey() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
