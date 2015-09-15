package eu.rivetgroup.security.cryptonotepad.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CryptoNotesDB extends SQLiteOpenHelper {
    private static CryptoNotesDB instance = null;

    static final String DATABASE_NAME = "cryptoNotes";
    static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String FIELD_LOGIN = "login";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_PASSWORD = "password";

    private final String CREATE_TABLE_USERS = "create table " + TABLE_USERS + "(" +
            FIELD_ID + " integer primary key, " +
            FIELD_LOGIN + " text, " +
            FIELD_PASSWORD + " text" +
            ");" +
            "";
    public static final String TABLE_NOTES = "notes";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_NOTE_TEXT = "noteText";

    private final String CREATE_TABLE_NOTES = "create table " + TABLE_NOTES + "(" +
            FIELD_ID + " integer primary key, " +
            FIELD_USER_ID + " integer, " +
            FIELD_NOTE_TEXT + " text" +
            ");" +
            "";

    private CryptoNotesDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static CryptoNotesDB getInstance(Context context) {
        if (instance == null) {
            instance = new CryptoNotesDB(context);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_NOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int checkPasswordAndGetId(String login, String password) {
        int userId;

        String passwordEncoded = CryptoHelper.getMD5(password);
        SQLiteDatabase db = getReadableDatabase();

        String query = String.format("SELECT * FROM " + TABLE_USERS + " WHERE " + FIELD_LOGIN + " = \"%s\" AND " + FIELD_PASSWORD + " = \"%s\"", login, passwordEncoded);
        Log.d("CryptoNotesDB", query);
        Cursor cursor = db.rawQuery(query, new String[]{});

        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex(FIELD_ID));
        } else userId = -1;
        cursor.close();

        return userId;
    }

    public boolean addUser(String login, String password) {
        if (login.trim().isEmpty() || password.trim().isEmpty())
            return false;

        String passwordEncoded = CryptoHelper.getMD5(password);
        SQLiteDatabase db = getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(FIELD_LOGIN, login);
        cv.put(FIELD_PASSWORD, passwordEncoded);

        return db.insert(TABLE_USERS, null, cv) > 0;
    }

    public void addNote(int userId, String noteText) {
        SQLiteDatabase db = getReadableDatabase();


        noteText = CryptoHelper.encrypt(noteText);

        ContentValues cv = new ContentValues();
        cv.put(FIELD_USER_ID, userId);
        cv.put(FIELD_NOTE_TEXT, noteText);

        db.insert(TABLE_NOTES, null, cv);
    }

    public Cursor getLogins() {
        SQLiteDatabase db = getReadableDatabase();

        return db.query(TABLE_USERS, new String[]{FIELD_ID, FIELD_LOGIN}, "", null, null,
                null, FIELD_LOGIN + " ASC");
    }

    public Cursor getNotesForUser(int userId) {
        SQLiteDatabase db = getReadableDatabase();

        return db.query(TABLE_NOTES, new String[]{FIELD_ID, FIELD_NOTE_TEXT}, FIELD_USER_ID + " = ?", new String[]{Integer.toString(userId)}, null, null, null);
    }

    public String getNote(int noteId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_NOTES, new String[]{"*"}, FIELD_ID + " = ? ",
                new String[]{Integer.toString(noteId)}, null, null, null);

        c.moveToFirst();

        String note = c.getString(c.getColumnIndex(FIELD_NOTE_TEXT));

        note = CryptoHelper.decrypt(note);

        c.close();

        return note;
    }

    public void deleteNote(int noteId) {
        SQLiteDatabase db = getReadableDatabase();

        db.delete(TABLE_NOTES, FIELD_ID + " = ?", new String[]{Integer.toString(noteId)});
    }

    public void updateNote(int noteId, String noteText) {
        SQLiteDatabase db = getReadableDatabase();

        noteText = CryptoHelper.encrypt(noteText);

        ContentValues cv = new ContentValues();
        cv.put(FIELD_NOTE_TEXT, noteText);
        db.update(TABLE_NOTES, cv, FIELD_ID + " = ?", new String[]{Integer.toString(noteId)});
    }

    public int getUserId(String username) {
        SQLiteDatabase db = getReadableDatabase();
        int id = 0;
        Cursor c = db.rawQuery("SELECT " + FIELD_ID + " FROM " + TABLE_USERS + " WHERE " + FIELD_LOGIN + " = ?", new String[] { username});

        if (c.moveToFirst()) {
            id = c.getInt(c.getColumnIndex(FIELD_ID));
        }

        c.close();

        return id;
    }

}
