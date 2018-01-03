package com.preethzcodez.ecommerceexample.database;

import android.content.Context;
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

    // Table Names Static Variables
    private static final String UserTable = "user_details";
    private static final String CategoriesTable = "categories";
    private static final String SubCategoriesMappingTable = "subcategories_mapping";
    private static final String ProductsTable = "products";
    private static final String VariantsTable = "variants";
    private static final String AddressTable = "address_book";
    private static final String OrderHistoryTable = "order_history";

    // Create User Table
    String CREATE_USER_TABLE = "CREATE TABLE " + UserTable + "("
            + EMAIL + " TEXT PRIMARY KEY,"
            + NAME + " TEXT NOT NULL,"
            + MOBILE + " TEXT NOT NULL,"
            + PASSWORD + " TEXT NOT NULL" + ")";

    // Create Address Book Table
    String CREATE_ADDRESS_BOOK = "CREATE TABLE " + AddressTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + NAME + " TEXT NOT NULL,"
            + ADDRESS_LINE1 + " TEXT NOT NULL,"
            + ADDRESS_LINE2 + " TEXT,"
            + MOBILE + " TEXT,"
            + PIN + " TEXT NOT NULL" + ")";

    // Create Categories Table
    String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + CategoriesTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + NAME + " TEXT NOT NULL" + ")";

    // Create Subcategories Mapping Table
    String CREATE_SUBCATEGORIES_MAPPING_TABLE = "CREATE TABLE " + SubCategoriesMappingTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + CAT_ID + " INTEGER NOT NULL,"
            + SUB_ID + " INTEGER NOT NULL" + ")";

    // Create Products Table
    String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + ProductsTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + NAME + " TEXT NOT NULL,"
            + DATE + " TEXT NOT NULL,"
            + TAX_NAME + " TEXT NOT NULL,"
            + TAX_VALUE + " REAL NOT NULL" + ")";

    // Create Variants Table
    String CREATE_VARIANTS_TABLE = "CREATE TABLE " + VariantsTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + NAME + " TEXT NOT NULL,"
            + SIZE + " TEXT,"
            + COLOR + " TEXT NOT NULL,"
            + PRICE + " TEXT NOT NULL" + ")";

    // Create Variants Table
    String CREATE_ORDER_HISTORY_TABLE = "CREATE TABLE " + OrderHistoryTable + "("
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
}
