package com.emroxriprap.klok.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Created by administrator on 7/30/15.
 */
public class KlokContract {

    public static final String CONTENT_AUTHORITY = "com.emroxriprap.klok";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_ENTRIES = "entries";

    // Normalize all dates that go into
    // the database to the start of the the Julian day at UTC.
    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

    public static final class KlokEntry implements BaseColumns {
        public static final String TABLE_NAME = "entries";

        public static final String COLUMN_JOB_NAME = "job_name";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_ADDRESS_ONE= "address_one";
        public static final String COLUMN_ADDRESS_TWO = "address_two";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_STATE = "state";
        public static final String COLUMN_HOURS = "hours";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ENTRIES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ENTRIES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ENTRIES;

        public static Uri buildEntriesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static String getEntryIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }
}