package com.preethzcodez.ecommerceexample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.preethzcodez.ecommerceexample.pojo.Cart;
import com.preethzcodez.ecommerceexample.pojo.Category;
import com.preethzcodez.ecommerceexample.pojo.Product;
import com.preethzcodez.ecommerceexample.pojo.Tax;
import com.preethzcodez.ecommerceexample.pojo.User;
import com.preethzcodez.ecommerceexample.pojo.Variant;
import com.preethzcodez.ecommerceexample.utils.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.preethzcodez.ecommerceexample.utils.Util.formatDouble;

/**
 * Created by Preeth on 1/4/2018
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
    private static final String QUANTITY = "quantity";
    public static final String VIEW_COUNT = "view_count";
    public static final String ORDER_COUNT = "order_count";
    public static final String SHARE_COUNT = "share_count";

    // Table Names Static Variables
    private static final String UserTable = "user_details";
    private static final String CategoriesTable = "listview";
    private static final String SubCategoriesMappingTable = "subcategories_mapping";
    private static final String ProductsTable = "products";
    private static final String VariantsTable = "variants";
    private static final String WishListTable = "wishlist";
    private static final String OrderHistoryTable = "order_history";
    private static final String ShoppingCartTable = "shopping_cart";

    // Create User Table
    private static final String CREATE_USER_TABLE = "CREATE TABLE " + UserTable + "("
            + EMAIL + " TEXT PRIMARY KEY,"
            + NAME + " TEXT NOT NULL,"
            + MOBILE + " TEXT NOT NULL,"
            + PASSWORD + " TEXT NOT NULL" + ")";

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

    // Create Order History Table
    private static final String CREATE_ORDER_HISTORY_TABLE = "CREATE TABLE " + OrderHistoryTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + PDT_ID + " INTEGER NOT NULL,"
            + VAR_ID + " INTEGER NOT NULL,"
            + QUANTITY + " INTEGER NOT NULL,"
            + EMAIL + " TEXT NOT NULL" + ")";

    // Create Shopping Cart Table
    private static final String CREATE_SHOPPING_CART_TABLE = "CREATE TABLE " + ShoppingCartTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + PDT_ID + " INTEGER NOT NULL,"
            + VAR_ID + " INTEGER NOT NULL,"
            + QUANTITY + " INTEGER NOT NULL,"
            + EMAIL + " TEXT NOT NULL" + ")";

    // Create Wish List Table
    private static final String CREATE_WISHLIST_TABLE = "CREATE TABLE " + WishListTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + PDT_ID + " INTEGER NOT NULL,"
            + EMAIL + " TEXT NOT NULL" + ")";

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
        db.execSQL(CREATE_ORDER_HISTORY_TABLE);
        db.execSQL(CREATE_SHOPPING_CART_TABLE);
        db.execSQL(CREATE_WISHLIST_TABLE);
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
        db.execSQL("DROP TABLE IF EXISTS " + OrderHistoryTable);
        db.execSQL("DROP TABLE IF EXISTS " + ShoppingCartTable);
        db.execSQL("DROP TABLE IF EXISTS " + WishListTable);

        // Create tables again
        onCreate(db);
    }

    // Insert Categories
    public void insertCategories(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID, id);
        values.put(NAME, name);

        // Check If Value Already Exists
        boolean isUpdate = false;
        String selectQuery = "SELECT * FROM " + CategoriesTable + " WHERE " + ID + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(CategoriesTable, values, ID + " = ?",
                    new String[]{String.valueOf(id)});
        } else {
            db.insert(CategoriesTable, null, values);
        }
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

        // Check If Value Already Exists
        boolean isUpdate = false;
        String selectQuery = "SELECT * FROM " + ProductsTable + " WHERE " + ID + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(ProductsTable, values, ID + " = ?",
                    new String[]{String.valueOf(id)});
            db.close();
            return;
        }

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

        // Check If Value Already Exists
        boolean isUpdate = false;
        String selectQuery = "SELECT * FROM " + VariantsTable + " WHERE " + ID + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(VariantsTable, values, ID + " = ?",
                    new String[]{String.valueOf(id)});
        } else {
            db.insert(VariantsTable, null, values);
        }

        db.close();
    }

    // Insert Child Category Mapping
    public void insertChildCategoryMapping(int category_id, int child_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CAT_ID, category_id);
        values.put(SUB_ID, child_id);

        // Check If Value Already Exists
        boolean isUpdate = false;
        int id = 0;
        String selectQuery = "SELECT * FROM " + SubCategoriesMappingTable + " WHERE " + CAT_ID + "=? AND " + SUB_ID + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(category_id), String.valueOf(child_id)});
        if (cursor.moveToFirst()) {
            isUpdate = true;
            id = cursor.getInt(cursor.getColumnIndex(ID));
        }
        cursor.close();

        if (isUpdate) {
            db.update(SubCategoriesMappingTable, values, ID + " = ?",
                    new String[]{String.valueOf(id)});
        } else {
            db.insert(SubCategoriesMappingTable, null, values);
        }

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

    // Get Products List By Selected Filters
    public List<Product> getProductsList(int sortById, List<String> sizes, List<String> colors, int cat_id, String email) {
        List<String> productIdList = new ArrayList<>();
        List<Product> productList = new ArrayList<>();

        // Create Query According To Filter
        String selectQuery = "SELECT DISTINCT " + PDT_ID + " FROM " + VariantsTable;
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
                    selectQuery = selectQuery + " AND " + COLOR + " IN " + inClauseColors;
                } else {
                    selectQuery = selectQuery + " WHERE " + COLOR + " IN " + inClauseColors;
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

        try {
            if (productIdList.size() > 0) {
                String inClause = Util.getInClause(productIdList);
                selectQuery = "SELECT  * FROM " + ProductsTable + " WHERE " + ID + " IN " + inClause;
                if (cat_id > 0) {
                    selectQuery = selectQuery + " AND " + CAT_ID + "=?";
                }
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

                if (cat_id > 0) {
                    cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(cat_id)});
                } else {
                    cursor = db.rawQuery(selectQuery, null);
                }

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Product product = new Product();
                        int id = cursor.getInt(cursor.getColumnIndex(ID));
                        product.setId(id);
                        product.setName(cursor.getString(cursor.getColumnIndex(NAME)));

                        // Get Item Wish Listed
                        boolean isShortlisted = isShortlistedItem(id, email);
                        product.setShortlisted(isShortlisted);

                        // Get Price Range
                        String priceRange = getProductPriceRangeById(id);
                        product.setPrice_range(priceRange);

                        // Adding product to list
                        productList.add(product);
                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor.close();

        // return products list
        return productList;
    }

    // Get Category List
    public List<Category> getCategoryList() {
        List<Category> categoryList = new ArrayList<>();

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
        List<Category> subcategoryList = new ArrayList<>();

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

    /*// Get Products List By Category
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
    }*/

    // Get Product Details By Id
    public Product getProductDetailsById(int id, String email) {
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
            boolean isShortlistedItem = isShortlistedItem(product.getId(), email);
            product.setShortlisted(isShortlistedItem);
            product.setTax(tax);
        }
        cursor.close();
        db.close();

        // return product
        return product;
    }

    // Get Variant Details By Id
    private Variant getVariantDetailsById(int id) {
        Variant variant = new Variant();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + VariantsTable + " WHERE " + ID + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            variant.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            variant.setColor(cursor.getString(cursor.getColumnIndex(COLOR)));
            variant.setPrice(cursor.getString(cursor.getColumnIndex(PRICE)));
            variant.setSize(cursor.getDouble(cursor.getColumnIndex(SIZE)));
        }
        cursor.close();
        db.close();

        // return variant
        return variant;
    }

    // Get Product Size By Id
    public List<String> getSizeByProductId(int id) {
        List<String> sizeList = new ArrayList<>();

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
        List<String> colorList = new ArrayList<>();

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
        List<String> colorList = new ArrayList<>();

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

    // Get Product Variant By Id, Size, Color
    public Variant getProductVariant(int id, String size, String color) {
        double price;

        Variant variant = new Variant();

        // Select All Query
        String selectQuery;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        if (size == null) {
            selectQuery = "SELECT * FROM " + VariantsTable + " WHERE " + PDT_ID + "=? AND " + COLOR + "=?";
            cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id), color});
        } else {
            selectQuery = "SELECT * FROM " + VariantsTable + " WHERE " + PDT_ID + "=? AND " + SIZE + "=? AND " + COLOR + "=?";
            cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id), size, color});
        }

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            int var_id = cursor.getInt(cursor.getColumnIndex(ID));
            String priceString = cursor.getString(cursor.getColumnIndex(PRICE));
            price = Double.parseDouble(priceString);
            priceString = formatDouble(price);

            variant.setId(var_id);
            variant.setPrice(priceString);
        }
        cursor.close();
        db.close();

        return variant;
    }

    // Get All Colors
    public List<String> getAllColors() {
        List<String> colorList = new ArrayList<>();

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
        List<String> sizeList = new ArrayList<>();

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

    // Register User
    public long registerUser(String name, String email, String mobile, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(EMAIL, email);
        values.put(MOBILE, mobile);
        values.put(PASSWORD, password);

        return db.insert(UserTable, null, values);
    }

    // Get User
    public User getUser(String email) {
        User user = new User();

        // Select Query
        String selectQuery = "SELECT * FROM " + UserTable + " WHERE " + EMAIL + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{email});

        if (cursor.moveToFirst()) {
            user.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
            user.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(PASSWORD)));
            user.setMobile(cursor.getString(cursor.getColumnIndex(MOBILE)));
        }
        cursor.close();
        db.close();

        // return user
        return user;
    }

    // Insert Product Into Cart
    public long insertIntoCart(int pdt_id, int var_id, int quantity, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PDT_ID, pdt_id);
        values.put(VAR_ID, var_id);
        values.put(QUANTITY, quantity);
        values.put(EMAIL, email);

        // Check If Value Already Exists
        String selectQuery = "SELECT * FROM " + ShoppingCartTable + " WHERE " + EMAIL + "=? AND " + PDT_ID + "=? AND " + VAR_ID + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{email, String.valueOf(pdt_id), String.valueOf(var_id)});
        if (cursor.moveToFirst()) {
            cursor.close();
            return -1;
        }
        cursor.close();
        return db.insert(ShoppingCartTable, null, values);
    }

    // Get Shopping Cart Items
    public List<Cart> getCartItems(String email) {
        List<Cart> shoppingCart = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ShoppingCartTable + " WHERE " + EMAIL + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{email});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Cart cart = new Cart();
                int id = cursor.getInt(cursor.getColumnIndex(ID));
                int productId = cursor.getInt(cursor.getColumnIndex(PDT_ID));
                int variantId = cursor.getInt(cursor.getColumnIndex(VAR_ID));
                int quantity = cursor.getInt(cursor.getColumnIndex(QUANTITY));

                Product product = getProductDetailsById(productId, email);
                Variant variant = getVariantDetailsById(variantId);

                cart.setId(id);
                cart.setItemQuantity(quantity);
                cart.setProduct(product);
                cart.setVariant(variant);

                // Adding to list
                shoppingCart.add(cart);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return cart items list
        return shoppingCart;
    }

    // Delete Cart Item By Id
    public boolean deleteCartItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ShoppingCartTable, ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    // Delete Cart Items
    public void deleteCartItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ShoppingCartTable, null, null);
    }

    // Get Cart Item Count
    public int getCartItemCount(String email) {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + ShoppingCartTable + " WHERE " + EMAIL + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{email});
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count;
    }

    // Insert Order
    public void insertOrderHistory(List<Cart> shoppingCart, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i = 0; i < shoppingCart.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(PDT_ID, shoppingCart.get(i).getProduct().getId());
            values.put(VAR_ID, shoppingCart.get(i).getVariant().getId());
            values.put(QUANTITY, shoppingCart.get(i).getItemQuantity());
            values.put(EMAIL, email);
            db.insert(OrderHistoryTable, null, values);
        }
        db.close();
    }

    // Add Item Into Wish List
    public long shortlistItem(int pdt_id, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PDT_ID, pdt_id);
        values.put(EMAIL, email);
        return db.insert(WishListTable, null, values);
    }

    // Remove Item From Wish List
    public boolean removeShortlistedItem(int pdt_id, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(WishListTable, PDT_ID + "=? AND " + EMAIL + "=?", new String[]{String.valueOf(pdt_id), email}) > 0;
    }

    // Get Wishlist Items
    public List<Product> getShortListedItems(String email) {
        List<Product> productList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + WishListTable + " WHERE " + EMAIL + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{email});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int pdt_id = cursor.getInt(cursor.getColumnIndex(PDT_ID));
                Product product = getProductDetailsById(pdt_id, email);
                String priceRange = getProductPriceRangeById(pdt_id);
                product.setPrice_range(priceRange);

                // Adding to list
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return product list
        return productList;
    }

    // Check Product In Wish List
    private boolean isShortlistedItem(int pdt_id, String email) {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + WishListTable + " WHERE " + EMAIL + "=? AND " + PDT_ID + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{email, String.valueOf(pdt_id)});

        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }

    // Get Order History
    public List<Cart> getOrders(String email) {
        List<Cart> shoppingCart = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + OrderHistoryTable + " WHERE " + EMAIL + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{email});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Cart cart = new Cart();
                int id = cursor.getInt(cursor.getColumnIndex(ID));
                int productId = cursor.getInt(cursor.getColumnIndex(PDT_ID));
                int variantId = cursor.getInt(cursor.getColumnIndex(VAR_ID));
                int quantity = cursor.getInt(cursor.getColumnIndex(QUANTITY));

                Product product = getProductDetailsById(productId, email);
                Variant variant = getVariantDetailsById(variantId);

                cart.setId(id);
                cart.setItemQuantity(quantity);
                cart.setProduct(product);
                cart.setVariant(variant);

                // Adding to list
                shoppingCart.add(cart);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return order items list
        return shoppingCart;
    }

    // Update Cart Item Quantity
    public void updateItemQuantity(int quantity, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QUANTITY, quantity);
        db.update(ShoppingCartTable, values, ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}
