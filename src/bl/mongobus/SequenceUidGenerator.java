package bl.mongobus;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import dao.MongoDBConnectionFactory;

public class SequenceUidGenerator {

    private static volatile Object handle = new Object();
    protected static String dbName = "form";
    protected static String collectionName = "sequenceuid";
    /** The list of maximum UIDs that can be used. */
    private static Map<String, UidHashItemInner> uidHash = new HashMap<String, UidHashItemInner>();
    /** The number of UIDs to allocate at a time. */
    private static long UID_ALLOCATION_SIZE = 100000;

    /**
     * Gets a new unique ID.
     * 
     * @param ctx
     *            the sesion context
     * @param handle
     *            the database handle
     * @return the new uid
     */
    public static long getNewUid(String dbName) {

        /*
         * In an ideal world, this locking would be done using synchronizationon on the method, rather than the newUidSemaphore. However, the method
         * calls startTransaction, which locks the handle. This caused a deadlock in at least one case. To get around this, the handle is locked
         * first, ensuring that the startTransaction will be able to lock the handle.
         */

        synchronized (handle) {
            long result;

            // First, see if we can allocate a UID from the cache.
            String uidHashKey = dbName;
            UidHashItemInner uidHashItem = (UidHashItemInner) uidHash.get(uidHashKey);
            if (uidHashItem != null && uidHashItem.getLast() < uidHashItem.getMax()) {
                result = uidHashItem.getLast() + 1;
                uidHashItem.setLast(result);
                return result;
            }

            // If it was null, create a new one, and save it.
            if (uidHashItem == null) {
                uidHashItem = new UidHashItemInner();
                uidHash.put(uidHashKey, uidHashItem);
            }

            DB db = MongoDBConnectionFactory.getConnection(dbName);
            DBCollection dc = db.getCollection(collectionName);
            DBObject dob = dc.findOne();
            if (dob == null) {
                dob = new BasicDBObject("uid", 20140118l);
                dc.save(dob);
            }
            long oldMax = Long.parseLong(String.valueOf(dob.get("uid")));
            // Determine result and the new max and save it.
            result = oldMax + 1;
            long newMax = oldMax + UID_ALLOCATION_SIZE;

            dob.put("uid", newMax);
            dc.save(dob);

            // Save the values.
            uidHashItem.setLast(result);
            uidHashItem.setMax(newMax);

            // Done.
            return result;
        }
    }

    /**
     * This class represents the values in the UID hash map.
     */
    private static class UidHashItemInner {
        /** The last UID handed out. */
        private long last;

        /** The maximum UID that can be used without going back to the database. */
        private long max;

        /**
         * Get the last UID used.
         * 
         * @return the last UID
         */
        private long getLast() {
            return last;
        }

        /**
         * Set the last UID used.
         * 
         * @param last
         *            the UID
         */
        private void setLast(long last) {
            this.last = last;
        }

        /**
         * Get the maximum UID that can be used, before going back to the database.
         * 
         * @return the UID
         */
        private long getMax() {
            return max;
        }

        /**
         * Set the maximum UID.
         * 
         * @param max
         *            the UID
         */
        private void setMax(long max) {
            this.max = max;
        }
    }
}
