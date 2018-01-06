package com.preethzcodez.ecommerceexample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.preethzcodez.ecommerceexample.pojo.Category;
import com.preethzcodez.ecommerceexample.pojo.Product;
import com.preethzcodez.ecommerceexample.pojo.Tax;
import com.preethzcodez.ecommerceexample.utils.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.preethzcodez.ecommerceexample.utils.Util.formatDouble;

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
    private static final String CategoriesTable = "listview";
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
            + CAT_ID + " INTEGER NOT NULL,"
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
    public void insertProducts(int id, int cat_id, String name, String date, String tax_name, Double tax_value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID, id);
        values.put(CAT_ID, cat_id);
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
    public void updateCounts(String COL_NAME, int count, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, count);

        // updating row
        db.update(ProductsTable, values, ID + " = ?",
                new String[]{String.valueOf(id)});
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

    // Get Products List
    public List<Product> getProductsList(int sortById) {
        List<Product> productList = new ArrayList<Product>();

        // Create Query According To Sort By
        String selectQuery = null;
        switch (sortById) {
            case 0: // Most Recent
                selectQuery = "SELECT  * FROM " + ProductsTable + " ORDER BY date(" + DATE + ") DESC";
                break;

            case 1: // Most Orders
                selectQuery = "SELECT  * FROM " + ProductsTable + " ORDER BY " + ORDER_COUNT + " DESC";
                break;

            case 2: // Most Shares
                selectQuery = "SELECT  * FROM " + ProductsTable + " ORDER BY " + SHARE_COUNT + " DESC";
                break;

            case 3: // Most Viewed
                selectQuery = "SELECT  * FROM " + ProductsTable + " ORDER BY " + VIEW_COUNT + " DESC";
                break;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                int id = cursor.getInt(cursor.getColumnIndex(ID));
                product.setId(id);
                product.setName(cursor.getString(cursor.getColumnIndex(NAME)));

                // Get Price Range
                String priceRange = getProductPriceRangeById(id);
                product.setPrice_range(priceRange);

                // Adding product to list
                productList.add(product);
            } while (cursor.moveToNext());
        }

        // return products list
        return productList;
    }

    // Get Products List By Selected Filters
    public List<Product> getProductsListByFilter(int sortById, List<String> sizes, List<String> colors) {
        List<String> productIdList = new ArrayList<>();
        List<Product> productList = new ArrayList<Product>();

        // Create Query According To Filter
        String selectQuery = "SELECT DISTINCT "+PDT_ID+" FROM " + VariantsTable;
        try {
            if (sizes.size() > 0) {
                String inClauseSizes = Util.getInClause(sizes);
                selectQuery = selectQuery + " WHERE " + SIZE + " IN " + inClauseSizes;
            }
        } catch (Exception ignore) {
        }

        try {
            if (colors.size() > 0) {
                String inClauseColors = Util.getInClause(colors);
                if (sizes.size() > 0) {
                    selectQuery = selectQuery + " AND " + COLOR + " IN "+ inClauseColors;
                } else {
                    selectQuery = selectQuery + " WHERE " + COLOR + " IN "+ inClauseColors;
                }
            }
        } catch (Exception ignore) {
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(PDT_ID));

                // Adding product id to list
                productIdList.add(String.valueOf(id));
            } while (cursor.moveToNext());
        }

        try
        {
            if(productIdList.size() > 0)
            {
                String inClause = Util.getInClause(productIdList);
                selectQuery = "SELECT  * FROM " + ProductsTable + " WHERE "+ID+" IN "+inClause;
                switch (sortById) {
                    case 0: // Most Recent
                        selectQuery = selectQuery + " ORDER BY date(" + DATE + ") DESC";
                        break;

                    case 1: // Most Orders
                        selectQuery = selectQuery + " ORDER BY " + ORDER_COUNT + " DESC";
                        break;

                    case 2: // Most Shares
                        selectQuery = selectQuery + " ORDER BY " + SHARE_COUNT + " DESC";
                        break;

                    case 3: // Most Viewed
                        selectQuery = selectQuery + " ORDER BY " + VIEW_COUNT + " DESC";
                        break;
                }

                cursor = db.rawQuery(selectQuery, null);

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Product product = new Product();
                        product.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                        product.setName(cursor.getString(cursor.getColumnIndex(NAME)));

                        // Adding product to list
                        productList.add(product);
                    } while (cursor.moveToNext());
                }
            }
        }
        catch (Exception ignore){

        }

        // return products list
        return productList;
    }

    // Get Category List
    public List<Category> getCategoryList() {
        List<Category> categoryList = new ArrayList<Category>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + CategoriesTable;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                int categoryId = cursor.getInt(cursor.getColumnIndex(ID));
                String name = cursor.getString(cursor.getColumnIndex(NAME));

                String selectCategory = "SELECT  * FROM " + SubCategoriesMappingTable + " WHERE " + SUB_ID + "=?";
                Cursor c = db.rawQuery(selectCategory, new String[]{String.valueOf(categoryId)});
                if (!c.moveToFirst()) { // don't add if category has a sub category
                    category.setId(categoryId);
                    category.setName(name);

                    // Adding category to list
                    categoryList.add(category);
                }
                c.close();
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return category list
        return categoryList;
    }

    // Get Subcategory By Category Id
    public List<Category> getSubcategoryList(int id) {
        List<Category> subcategoryList = new ArrayList<Category>();

        // Select Subcategories
        String selectQuery = "SELECT  " + SUB_ID + " FROM " + SubCategoriesMappingTable + " WHERE " + CAT_ID + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(cursor.getInt(cursor.getColumnIndex(SUB_ID)));

                String selectSubCategory = "SELECT  " + NAME + " FROM " + CategoriesTable + " WHERE " + ID + "= ?";
                Cursor c = db.rawQuery(selectSubCategory, new String[]{String.valueOf(category.getId())});
                if (c.moveToFirst()) {
                    do {
                        category.setName(c.getString(c.getColumnIndex(NAME)));
                    } while (c.moveToNext());

                    // Adding category to list
                    subcategoryList.add(category);
                }
                c.close();
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return category list
        return subcategoryList;
    }

    // Get Products List By Category
    public List<Product> getProductsListByCategory(int id) {
        List<Product> productList = new ArrayList<Product>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + ProductsTable + " WHERE " + CAT_ID + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                product.setName(cursor.getString(cursor.getColumnIndex(NAME)));

                // Adding contact to list
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return products list
        return productList;
    }

    // Get Product Details By Id
    public Product getProductDetailsById(int id) {
        Product product = new Product();
        Tax tax = new Tax();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ProductsTable + " WHERE " + ID + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            product.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            product.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            tax.setName(cursor.getString(cursor.getColumnIndex(TAX_NAME)));
            tax.setValue(cursor.getDouble(cursor.getColumnIndex(TAX_VALUE)));
            product.setTax(tax);
        }
        cursor.close();
        db.close();

        // return product
        return product;
    }

    // Get Product Size By Id
    public List<String> getSizeByProductId(int id) {
        List<String> sizeList = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + SIZE + " FROM " + VariantsTable + " WHERE " + PDT_ID + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String size = cursor.getString(cursor.getColumnIndex(SIZE));
                if (size != null && !size.equalsIgnoreCase("null")) {
                    sizeList.add(size);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return size list
        return sizeList;
    }

    // Get Product Color By Size And Id
    public List<String> getColorBySelectedSize(int id, String size) {
        List<String> colorList = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + COLOR + " FROM " + VariantsTable + " WHERE " + PDT_ID + "=? AND " + SIZE + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id), size});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String color = cursor.getString(cursor.getColumnIndex(COLOR));
                colorList.add(color);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return size list
        return colorList;
    }

    // Get Product Colors Id
    public List<String> getProductColorsById(int id) {
        List<String> colorList = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + COLOR + " FROM " + VariantsTable + " WHERE " + PDT_ID + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String color = cursor.getString(cursor.getColumnIndex(COLOR));
                colorList.add(color);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return color list
        return colorList;
    }

    // Get Product Price Range By Id
    public String getProductPriceRangeById(int id) {
        List<Double> priceList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + PRICE + " FROM " + VariantsTable + " WHERE " + PDT_ID + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String priceString = cursor.getString(cursor.getColumnIndex(PRICE));
                double price = Double.parseDouble(priceString);
                priceList.add(price);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        double minimumPrice = Collections.min(priceList);
        double maximumPrice = Collections.max(priceList);

        if (minimumPrice == maximumPrice) {
            return "Rs." + formatDouble(maximumPrice);
        } else {
            return "Rs." + formatDouble(minimumPrice) + " - Rs." + formatDouble(maximumPrice);
        }
    }

    // Get Product Price Range By Id, Size, Color
    public String getProductPrice(int id, String size, String color) {
        double price = 0;

        // Select All Query
        String selectQuery;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        if (size == null) {
            selectQuery = "SELECT " + PRICE + " FROM " + VariantsTable + " WHERE " + PDT_ID + "=? AND " + COLOR + "=?";
            cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id), color});
        } else {
            selectQuery = "SELECT " + PRICE + " FROM " + VariantsTable + " WHERE " + PDT_ID + "=? AND " + SIZE + "=? AND " + COLOR + "=?";
            cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id), size, color});
        }

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            String priceString = cursor.getString(cursor.getColumnIndex(PRICE));
            price = Double.parseDouble(priceString);
        }
        cursor.close();
        db.close();

        return "Rs." + formatDouble(price);
    }

    // Get All Colors
    public List<String> getAllColors() {
        List<String> colorList = new ArrayList<String>();

        // Select Query
        String selectQuery = "SELECT DISTINCT " + COLOR + " FROM " + VariantsTable;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String color = cursor.getString(cursor.getColumnIndex(COLOR));
                colorList.add(color);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return color list
        return colorList;
    }

    // Get All Sizes
    public List<String> getAllSizes() {
        List<String> sizeList = new ArrayList<String>();

        // Select Query
        String selectQuery = "SELECT DISTINCT " + SIZE + " FROM " + VariantsTable;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String size = cursor.getString(cursor.getColumnIndex(SIZE));
                if (size != null && !size.equalsIgnoreCase("null")) {
                    sizeList.add(size);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return size list
        return sizeList;
    }
}
