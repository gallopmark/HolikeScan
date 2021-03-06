package com.haolaike.hotlikescan.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.haolaike.hotlikescan.beans.LogBeans;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LOG_BEANS".
*/
public class LogBeansDao extends AbstractDao<LogBeans, Long> {

    public static final String TABLENAME = "LOG_BEANS";

    /**
     * Properties of entity LogBeans.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property _id = new Property(0, Long.class, "_id", true, "_id");
        public final static Property OrderCode = new Property(1, String.class, "orderCode", false, "ORDER_CODE");
        public final static Property CallStart = new Property(2, String.class, "callStart", false, "CALL_START");
        public final static Property DnsStart = new Property(3, String.class, "dnsStart", false, "DNS_START");
        public final static Property DnsEnd = new Property(4, String.class, "dnsEnd", false, "DNS_END");
        public final static Property ConnectStart = new Property(5, String.class, "connectStart", false, "CONNECT_START");
        public final static Property ConnectEnd = new Property(6, String.class, "connectEnd", false, "CONNECT_END");
        public final static Property ConnectionAcquired = new Property(7, String.class, "connectionAcquired", false, "CONNECTION_ACQUIRED");
        public final static Property RequestHeadersStart = new Property(8, String.class, "requestHeadersStart", false, "REQUEST_HEADERS_START");
        public final static Property RequestHeadersEnd = new Property(9, String.class, "requestHeadersEnd", false, "REQUEST_HEADERS_END");
        public final static Property ResponseHeadersStart = new Property(10, String.class, "responseHeadersStart", false, "RESPONSE_HEADERS_START");
        public final static Property ResponseHeadersEnd = new Property(11, String.class, "responseHeadersEnd", false, "RESPONSE_HEADERS_END");
        public final static Property ResponseBodyStart = new Property(12, String.class, "responseBodyStart", false, "RESPONSE_BODY_START");
        public final static Property ResponseBodyEnd = new Property(13, String.class, "responseBodyEnd", false, "RESPONSE_BODY_END");
        public final static Property ConnectionReleased = new Property(14, String.class, "connectionReleased", false, "CONNECTION_RELEASED");
        public final static Property CallEnd = new Property(15, String.class, "callEnd", false, "CALL_END");
        public final static Property CallFailed = new Property(16, String.class, "callFailed", false, "CALL_FAILED");
        public final static Property CreateTime = new Property(17, java.util.Date.class, "createTime", false, "CREATE_TIME");
    }


    public LogBeansDao(DaoConfig config) {
        super(config);
    }
    
    public LogBeansDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LOG_BEANS\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: _id
                "\"ORDER_CODE\" TEXT," + // 1: orderCode
                "\"CALL_START\" TEXT," + // 2: callStart
                "\"DNS_START\" TEXT," + // 3: dnsStart
                "\"DNS_END\" TEXT," + // 4: dnsEnd
                "\"CONNECT_START\" TEXT," + // 5: connectStart
                "\"CONNECT_END\" TEXT," + // 6: connectEnd
                "\"CONNECTION_ACQUIRED\" TEXT," + // 7: connectionAcquired
                "\"REQUEST_HEADERS_START\" TEXT," + // 8: requestHeadersStart
                "\"REQUEST_HEADERS_END\" TEXT," + // 9: requestHeadersEnd
                "\"RESPONSE_HEADERS_START\" TEXT," + // 10: responseHeadersStart
                "\"RESPONSE_HEADERS_END\" TEXT," + // 11: responseHeadersEnd
                "\"RESPONSE_BODY_START\" TEXT," + // 12: responseBodyStart
                "\"RESPONSE_BODY_END\" TEXT," + // 13: responseBodyEnd
                "\"CONNECTION_RELEASED\" TEXT," + // 14: connectionReleased
                "\"CALL_END\" TEXT," + // 15: callEnd
                "\"CALL_FAILED\" TEXT," + // 16: callFailed
                "\"CREATE_TIME\" INTEGER);"); // 17: createTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LOG_BEANS\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LogBeans entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
 
        String orderCode = entity.getOrderCode();
        if (orderCode != null) {
            stmt.bindString(2, orderCode);
        }
 
        String callStart = entity.getCallStart();
        if (callStart != null) {
            stmt.bindString(3, callStart);
        }
 
        String dnsStart = entity.getDnsStart();
        if (dnsStart != null) {
            stmt.bindString(4, dnsStart);
        }
 
        String dnsEnd = entity.getDnsEnd();
        if (dnsEnd != null) {
            stmt.bindString(5, dnsEnd);
        }
 
        String connectStart = entity.getConnectStart();
        if (connectStart != null) {
            stmt.bindString(6, connectStart);
        }
 
        String connectEnd = entity.getConnectEnd();
        if (connectEnd != null) {
            stmt.bindString(7, connectEnd);
        }
 
        String connectionAcquired = entity.getConnectionAcquired();
        if (connectionAcquired != null) {
            stmt.bindString(8, connectionAcquired);
        }
 
        String requestHeadersStart = entity.getRequestHeadersStart();
        if (requestHeadersStart != null) {
            stmt.bindString(9, requestHeadersStart);
        }
 
        String requestHeadersEnd = entity.getRequestHeadersEnd();
        if (requestHeadersEnd != null) {
            stmt.bindString(10, requestHeadersEnd);
        }
 
        String responseHeadersStart = entity.getResponseHeadersStart();
        if (responseHeadersStart != null) {
            stmt.bindString(11, responseHeadersStart);
        }
 
        String responseHeadersEnd = entity.getResponseHeadersEnd();
        if (responseHeadersEnd != null) {
            stmt.bindString(12, responseHeadersEnd);
        }
 
        String responseBodyStart = entity.getResponseBodyStart();
        if (responseBodyStart != null) {
            stmt.bindString(13, responseBodyStart);
        }
 
        String responseBodyEnd = entity.getResponseBodyEnd();
        if (responseBodyEnd != null) {
            stmt.bindString(14, responseBodyEnd);
        }
 
        String connectionReleased = entity.getConnectionReleased();
        if (connectionReleased != null) {
            stmt.bindString(15, connectionReleased);
        }
 
        String callEnd = entity.getCallEnd();
        if (callEnd != null) {
            stmt.bindString(16, callEnd);
        }
 
        String callFailed = entity.getCallFailed();
        if (callFailed != null) {
            stmt.bindString(17, callFailed);
        }
 
        java.util.Date createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindLong(18, createTime.getTime());
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LogBeans entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
 
        String orderCode = entity.getOrderCode();
        if (orderCode != null) {
            stmt.bindString(2, orderCode);
        }
 
        String callStart = entity.getCallStart();
        if (callStart != null) {
            stmt.bindString(3, callStart);
        }
 
        String dnsStart = entity.getDnsStart();
        if (dnsStart != null) {
            stmt.bindString(4, dnsStart);
        }
 
        String dnsEnd = entity.getDnsEnd();
        if (dnsEnd != null) {
            stmt.bindString(5, dnsEnd);
        }
 
        String connectStart = entity.getConnectStart();
        if (connectStart != null) {
            stmt.bindString(6, connectStart);
        }
 
        String connectEnd = entity.getConnectEnd();
        if (connectEnd != null) {
            stmt.bindString(7, connectEnd);
        }
 
        String connectionAcquired = entity.getConnectionAcquired();
        if (connectionAcquired != null) {
            stmt.bindString(8, connectionAcquired);
        }
 
        String requestHeadersStart = entity.getRequestHeadersStart();
        if (requestHeadersStart != null) {
            stmt.bindString(9, requestHeadersStart);
        }
 
        String requestHeadersEnd = entity.getRequestHeadersEnd();
        if (requestHeadersEnd != null) {
            stmt.bindString(10, requestHeadersEnd);
        }
 
        String responseHeadersStart = entity.getResponseHeadersStart();
        if (responseHeadersStart != null) {
            stmt.bindString(11, responseHeadersStart);
        }
 
        String responseHeadersEnd = entity.getResponseHeadersEnd();
        if (responseHeadersEnd != null) {
            stmt.bindString(12, responseHeadersEnd);
        }
 
        String responseBodyStart = entity.getResponseBodyStart();
        if (responseBodyStart != null) {
            stmt.bindString(13, responseBodyStart);
        }
 
        String responseBodyEnd = entity.getResponseBodyEnd();
        if (responseBodyEnd != null) {
            stmt.bindString(14, responseBodyEnd);
        }
 
        String connectionReleased = entity.getConnectionReleased();
        if (connectionReleased != null) {
            stmt.bindString(15, connectionReleased);
        }
 
        String callEnd = entity.getCallEnd();
        if (callEnd != null) {
            stmt.bindString(16, callEnd);
        }
 
        String callFailed = entity.getCallFailed();
        if (callFailed != null) {
            stmt.bindString(17, callFailed);
        }
 
        java.util.Date createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindLong(18, createTime.getTime());
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public LogBeans readEntity(Cursor cursor, int offset) {
        LogBeans entity = new LogBeans( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // _id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // orderCode
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // callStart
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // dnsStart
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // dnsEnd
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // connectStart
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // connectEnd
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // connectionAcquired
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // requestHeadersStart
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // requestHeadersEnd
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // responseHeadersStart
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // responseHeadersEnd
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // responseBodyStart
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // responseBodyEnd
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // connectionReleased
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // callEnd
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // callFailed
            cursor.isNull(offset + 17) ? null : new java.util.Date(cursor.getLong(offset + 17)) // createTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LogBeans entity, int offset) {
        entity.set_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setOrderCode(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCallStart(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDnsStart(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDnsEnd(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setConnectStart(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setConnectEnd(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setConnectionAcquired(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setRequestHeadersStart(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setRequestHeadersEnd(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setResponseHeadersStart(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setResponseHeadersEnd(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setResponseBodyStart(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setResponseBodyEnd(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setConnectionReleased(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setCallEnd(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setCallFailed(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setCreateTime(cursor.isNull(offset + 17) ? null : new java.util.Date(cursor.getLong(offset + 17)));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(LogBeans entity, long rowId) {
        entity.set_id(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(LogBeans entity) {
        if(entity != null) {
            return entity.get_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(LogBeans entity) {
        return entity.get_id() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
