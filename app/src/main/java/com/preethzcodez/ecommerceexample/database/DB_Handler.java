package com.preethzcodez.ecommerceexample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Preeth on 1/4/2018.
 */

public class DB_Handler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "E-Commerce";

    // Column Names Static Variables
    private static final String ID = "id";
    private static final String CAT_ID = "category_id";
    private static final String SUB_ID = "subcategory_id";
    private static final String PDT_ID = "product_id";
    private static final String VAR_ID = "variant_id";
    private static final String ADDR_ID = "address_id";
    private static final String NAME = "name";
    private static final String DATE = "added_on";
    private static final String SIZE = "size";
    private static final String COLOR = "color";
    private static final String PRICE = "price";
    private static final String TAX_NAME = "tax_name";
    private static final String TAX_VALUE = "tax_value";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String MOBILE = "mobile_no";
    private static final String ADDRESS_LINE1 = "address_line1";
    private static final String ADDRESS_LINE2 = "address_line2";
    private static final String PIN = "pin";
    public static final String VIEW_COUNT = "view_count";
    public static final String ORDER_COUNT = "order_count";
    public static final String SHARE_COUNT = "share_count";

    // Table Names Static Variables
    private static final String UserTable = "user_details";
    private static final String CategoriesTable = "categories";
    private static final String SubCategoriesMappingTable = "subcategories_mapping";
    private static final String ProductsTable = "products";
    private static final String VariantsTable = "variants";
    private static final String AddressTable = "address_book";
    private static final String OrderHistoryTable = "order_history";

    // Create User Table
    private static final String CREATE_USER_TABLE = "CREATE TABLE " + UserTable + "("
            + EMAIL + " TEXT PRIMARY KEY,"
            + NAME + " TEXT NOT NULL,"
            + MOBILE + " TEXT NOT NULL,"
            + PASSWORD + " TEXT NOT NULL" + ")";

    // Create Address Book Table
    private static final String CREATE_ADDRESS_BOOK = "CREATE TABLE " + AddressTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + NAME + " TEXT NOT NULL,"
            + ADDRESS_LINE1 + " TEXT NOT NULL,"
            + ADDRESS_LINE2 + " TEXT,"
            + MOBILE + " TEXT,"
            + PIN + " TEXT NOT NULL" + ")";

    // Create Categories Table
    private static final String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + CategoriesTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + NAME + " TEXT NOT NULL" + ")";

    // Create Subcategories Mapping Table
    private static final String CREATE_SUBCATEGORIES_MAPPING_TABLE = "CREATE TABLE " + SubCategoriesMappingTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + CAT_ID + " INTEGER NOT NULL,"
            + SUB_ID + " INTEGER NOT NULL" + ")";

    // Create Products Table
    private static final String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + ProductsTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + NAME + " TEXT NOT NULL,"
            + DATE + " TEXT NOT NULL,"
            + TAX_NAME + " TEXT NOT NULL,"
            + TAX_VALUE + " REAL NOT NULL,"
            + VIEW_COUNT + " INTEGER NOT NULL,"
            + ORDER_COUNT + " INTEGER NOT NULL,"
            + SHARE_COUNT + " INTEGER NOT NULL" + ")";

    // Create Variants Table
    private static final String CREATE_VARIANTS_TABLE = "CREATE TABLE " + VariantsTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + SIZE + " TEXT,"
            + COLOR + " TEXT NOT NULL,"
            + PRICE + " TEXT NOT NULL,"
            + PDT_ID + " INTEGER NOT NULL" + ")";

    // Create Variants Table
    private static final String CREATE_ORDER_HISTORY_TABLE = "CREATE TABLE " + OrderHistoryTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + PDT_ID + " INTEGER NOT NULL,"
            + VAR_ID + " INTEGER NOT NULL,"
            + ADDR_ID + " INTEGER NOT NULL" + ")";

    public DB_Handler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_CATEGORIES_TABLE);
        db.execSQL(CREATE_PRODUCTS_TABLE);
        db.execSQL(CREATE_VARIANTS_TABLE);
        db.execSQL(CREATE_SUBCATEGORIES_MAPPING_TABLE);
        db.execSQL(CREATE_ADDRESS_BOOK);
        db.execSQL(CREATE_ORDER_HISTORY_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + UserTable);
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable);
        db.execSQL("DROP TABLE IF EXISTS " + ProductsTable);
        db.execSQL("DROP TABLE IF EXISTS " + VariantsTable);
        db.execSQL("DROP TABLE IF EXISTS " + SubCategoriesMappingTable);
        db.execSQL("DROP TABLE IF EXISTS " + AddressTable);
        db.execSQL("DROP TABLE IF EXISTS " + OrderHistoryTable);

        // Create tables again
        onCreate(db);
    }

    // Insert Categories
    public void insertCategories(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID, id);
        values.put(NAME, name);

        db.insert(CategoriesTable, null, values);
        db.close();
    }

    // Insert Products
    public void insertProducts(int id, String name, String date, String tax_name, Double tax_value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID, id);
        values.put(NAME, name);
        values.put(DATE, date);
        values.put(TAX_NAME, tax_name);
        values.put(TAX_VALUE, tax_value);

        // Set View / Order / Share Counts To 0
        values.put(VIEW_COUNT, 0);
        values.put(ORDER_COUNT, 0);
        values.put(SHARE_COUNT, 0);

        db.insert(ProductsTable, null, values);
        db.close();
    }

    // Insert Variants
    public void insertVariants(int id, String size, String color, String price, int product_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID, id);
        values.put(SIZE, size);
        values.put(COLOR, color);
        values.put(PRICE, price);
        values.put(PDT_ID, product_id);

        db.insert(VariantsTable, null, values);
        db.close();
    }

    // Insert Child Category Mapping
    public void insertChildCategoryMapping(int category_id, int child_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CAT_ID, category_id);
        values.put(SUB_ID, child_id);

        db.insert(SubCategoriesMappingTable, null, values);
        db.close();
    }

    // Update View / Share / Order Counts
    public void updateCounts(String COL_NAME, int count) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, count);

        // updating row
        db.update(ProductsTable, values, ID + " = ?",
                new String[]{String.valueOf(count)});
        db.close();
    }

    // Get Item Count - Product Table
    public int getItemCount() {
        String countQuery = "SELECT  * FROM " + ProductsTable;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        // return count
        return count;
    }
}
