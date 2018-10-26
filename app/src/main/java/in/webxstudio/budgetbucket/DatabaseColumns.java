package in.webxstudio.budgetbucket;

import android.provider.BaseColumns;

public class DatabaseColumns {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.

    public static final String TABLE_NAME = "entry";
    public static final String COLUMN_NAME_EXPENSE = "expense";
    public static final String COLUMN_NAME_TYPE = "type";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_NOTE = "note";

}
