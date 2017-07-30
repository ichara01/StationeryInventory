package com.example.ioanna.stationeryinventory;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.ioanna.stationeryinventory.DbBitmapUtility.getBitmapAsByteArray;
import static com.example.ioanna.stationeryinventory.DbBitmapUtility.getImage;
import static com.example.ioanna.stationeryinventory.ListFragment.KEY_ID;

public class EditStationery extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int LOADER_ID = 2;
    private EditText nameEditText, quantityEditText, priceEditText, supplierEditText, imageNameEditText, supplierPhoneEditText;
    private TextView codeTextView;
    private ImageView imageToUpload;
    private Button btnBrowse, btnDelete;
    private ImageButton btnIncrease, btnDecrease, btnOrder;
    private String validationMessage = "";
    // In case of existing stationery, get item ID
    private static long itemID = -1;
    // Boolean flag that keeps track of whether the stationery item has been edited
    private boolean mStationeryHasChanged = false;

    /**
     * OnTouchListener that listens for any user touches on a View, implying that they are modifying
     * the view, and we change the mPetHasChanged boolean to true.
     */
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mStationeryHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.layout_edit_stationery);
            Bundle extras = getIntent().getExtras();
            InitializeActionBar();
            if (extras != null) {
                itemID = extras.getLong(KEY_ID);
                if (itemID != -1) {
                    // Initialize a loader to read the pet data from the database
                    // and display the current values in the editor
                    getLoaderManager().initLoader(LOADER_ID, null, this);
                }
            }
            // Get controls
            GetActivityControls();
            // Check if user has changed the stationery data
            CheckIfItemHasChanged();
            //Upload stationery images
            imageToUpload.setOnClickListener(this);
            btnBrowse.setOnClickListener(this);
            //Update User Interface
            updateUI();
        }
    }

    private void GetActivityControls() {
        nameEditText = (EditText) findViewById(R.id.edit_name);
        quantityEditText = (EditText) findViewById(R.id.edit_quantity);
        priceEditText = (EditText) findViewById(R.id.edit_price);
        supplierEditText = (EditText) findViewById(R.id.edit_supplier);
        supplierPhoneEditText = (EditText) findViewById(R.id.edit_supplier_phone);
        imageNameEditText = (EditText) findViewById(R.id.edit_image_name);
        imageToUpload = (ImageView) findViewById(R.id.image_item);
        btnBrowse = (Button) findViewById(R.id.btn_browse);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        codeTextView = (TextView) findViewById(R.id.txt_code);
        btnIncrease = (ImageButton) findViewById(R.id.btn_increase);
        btnDecrease = (ImageButton) findViewById(R.id.btn_descrease);
        btnOrder = (ImageButton) findViewById(R.id.btn_order);
    }

    private void CheckIfItemHasChanged() {
        // Setup OnTouchListeners on all the input fields, so we can determine if the user
        // has touched or modified them. This will let us know if there are unsaved changes
        // or not, if the user tries to leave the editor without saving.
        nameEditText.setOnTouchListener(mTouchListener);
        supplierEditText.setOnTouchListener(mTouchListener);
        supplierPhoneEditText.setOnTouchListener(mTouchListener);
        quantityEditText.setOnTouchListener(mTouchListener);
        priceEditText.setOnTouchListener(mTouchListener);
        imageNameEditText.setOnTouchListener(mTouchListener);
        imageToUpload.setOnTouchListener(mTouchListener);
        btnIncrease.setOnTouchListener(mTouchListener);
        btnDecrease.setOnTouchListener(mTouchListener);
    }

    // Update UI based on activity mode
    private void updateUI() {
        if (itemID == -1) {
            // Set Title
            setTitle(getString(R.string.new_stationery));
            //Set quantity editText editable
            quantityEditText.setEnabled(true);
            btnIncrease.setVisibility(View.GONE);
            btnDecrease.setVisibility(View.GONE);
            btnIncrease.setEnabled(false);
            btnDecrease.setEnabled(false);
            btnDelete.setVisibility(View.GONE);
            btnOrder.setVisibility(View.GONE);
        } else {
            // Set Title
            setTitle(getString(R.string.edit_stationery));
            //Set quantity editText to read-only
            quantityEditText.setEnabled(false);
            btnIncrease.setVisibility(View.VISIBLE);
            btnDecrease.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
            btnOrder.setVisibility(View.VISIBLE);
        }
    }

    //Initialize Action Bar
    private void InitializeActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayUseLogoEnabled(true);
            }
        }
    }

    private void ClearStationeryData() {
        nameEditText.setText("");
        supplierEditText.setText("");
        supplierPhoneEditText.setText("");
        quantityEditText.setText("");
        priceEditText.setText("");
        imageNameEditText.setText("");
        codeTextView.setText("");
        imageToUpload.setImageBitmap(null);
    }

    //Return to home page
    public void goToHome(View view) {
        ReturnToMainActivity();
    }

    // On click on save button, check if user
    // creates new item and calls insert method OR
    // updated existing item and calls update method
    public void saveChanges(View view) {
        if (itemID == -1) {
            if (insertStationery() == true)
                ReturnToMainActivity();
            else
                Toast.makeText(this, validationMessage, Toast.LENGTH_LONG).show();
        } else {
            if (updateStationery() == true)
                ReturnToMainActivity();
            else
                Toast.makeText(this, validationMessage, Toast.LENGTH_LONG).show();
        }
    }

    private void ReturnToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private boolean insertStationery() {
        String name = nameEditText.getText().toString();
        Double price = null;
        String priveValue = priceEditText.getText().toString();
        if (priveValue != null && !priveValue.isEmpty())
            price = Double.valueOf(priveValue);
        Integer quantity = null;
        String quantityValue = quantityEditText.getText().toString();
        if (quantityValue != null && !quantityValue.isEmpty())
            quantity = Integer.valueOf(quantityValue);
        String supplier = supplierEditText.getText().toString();
        String supplierPhone = supplierPhoneEditText.getText().toString();
        String imageName = imageNameEditText.getText().toString();
        byte[] byteImage = null;
        if (imageToUpload.getDrawable() != null) {
            Bitmap image = ((BitmapDrawable) imageToUpload.getDrawable()).getBitmap();
            if (image != null)
                byteImage = getBitmapAsByteArray(image);
        }
        // Create a ContentValues object where column names are the keys
        ContentValues values = new ContentValues();
        values.put(StationeryContract.StationeryEntry.COLUMN_STATIONERY_ITEMNAME, name);
        values.put(StationeryContract.StationeryEntry.COLUMN_STATIONERY_SUPPLIER, supplier);
        values.put(StationeryContract.StationeryEntry.COLUMN_STATIONERY_SUPPLIER_PHONE, supplierPhone);
        values.put(StationeryContract.StationeryEntry.COLUMN_STATIONERY_PRICE, price);
        values.put(StationeryContract.StationeryEntry.COLUMN_STATIONERY_QUANTITY, quantity);
        values.put(StationeryContract.StationeryEntry.COLUMN_STATIONERY_ITEMIMAGENAME, imageName);
        values.put(StationeryContract.StationeryEntry.COLUMN_STATIONERY_ITEMIMAGEDATA, byteImage);
        if (IsDataValid(values)) {
            Uri newUri = this.getContentResolver().insert(StationeryContract.StationeryEntry.STATIONERY_URI, values);
            // Show a toast message depending on whether or not the insertion was successful
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.insert_stationery_failed), Toast.LENGTH_SHORT).show();
                return false;
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.insert_stationery_successful), Toast.LENGTH_SHORT).show();
                return true;
            }
        } else
            return false;
    }

    private boolean updateStationery() {
        String name = nameEditText.getText().toString();
        Double price = null;
        String priveValue = priceEditText.getText().toString();
        if (priveValue != null && !priveValue.isEmpty())
            price = Double.valueOf(priveValue);
        Integer quantity = null;
        String quantityValue = quantityEditText.getText().toString();
        if (quantityValue != null && !quantityValue.isEmpty())
            quantity = Integer.valueOf(quantityValue);
        String supplier = supplierEditText.getText().toString();
        String supplierPhone = supplierPhoneEditText.getText().toString();
        String imageName = imageNameEditText.getText().toString();
        Bitmap image = ((BitmapDrawable) imageToUpload.getDrawable()).getBitmap();
        byte[] byteImage = getBitmapAsByteArray(image);
        // Create a ContentValues object where column names are the keys
        ContentValues values = new ContentValues();
        values.put(StationeryContract.StationeryEntry.COLUMN_STATIONERY_ITEMNAME, name);
        values.put(StationeryContract.StationeryEntry.COLUMN_STATIONERY_SUPPLIER, supplier);
        values.put(StationeryContract.StationeryEntry.COLUMN_STATIONERY_SUPPLIER_PHONE, supplierPhone);
        values.put(StationeryContract.StationeryEntry.COLUMN_STATIONERY_PRICE, price);
        values.put(StationeryContract.StationeryEntry.COLUMN_STATIONERY_QUANTITY, quantity);
        values.put(StationeryContract.StationeryEntry.COLUMN_STATIONERY_ITEMIMAGENAME, imageName);
        values.put(StationeryContract.StationeryEntry.COLUMN_STATIONERY_ITEMIMAGEDATA, byteImage);
        if (IsDataValid(values)) {
            int numOfRows = this.getContentResolver().update(ContentUris.withAppendedId(StationeryContract.StationeryEntry.STATIONERY_URI, itemID), values, null, null);
            // Show a toast message depending on whether or not the update was successful
            if (numOfRows == 1) {
                // Notify that a row was updated and attempt to sync changes
                this.getContentResolver().notifyChange(StationeryContract.StationeryEntry.STATIONERY_URI, null);
                return true;
            }
        }
        return false;
    }

    //Check if user enters required data
    private boolean IsDataValid(ContentValues values) {
        validationMessage = getString(R.string.validation_msg);
        boolean flag = true;
        // Check that the stationery image is not null
        byte[] image = values.getAsByteArray(StationeryContract.StationeryEntry.COLUMN_STATIONERY_ITEMIMAGEDATA);
        if (image == null || image.length == 0) {
            validationMessage = validationMessage + "\n " + getString(R.string.stationey_image).replace(':', ' ');
            flag = false;
        }
        // Check that the stationery image name is not null
        String imageName = values.getAsString(StationeryContract.StationeryEntry.COLUMN_STATIONERY_ITEMIMAGENAME);
        if (imageName == null || imageName.isEmpty()) {
            validationMessage = validationMessage + "\n " + getString(R.string.stationey_image_name).replace(':', ' ');
            flag = false;
        }
        // Check that the stationery is not null
        String name = values.getAsString(StationeryContract.StationeryEntry.COLUMN_STATIONERY_ITEMNAME);
        if (name == null || name.isEmpty()) {
            validationMessage = validationMessage + "\n " + getString(R.string.stationery_name).replace(':', ' ');
            flag = false;
        }
        // Check that the stationery supplier is not null
        String supplier = values.getAsString(StationeryContract.StationeryEntry.COLUMN_STATIONERY_SUPPLIER);
        if (supplier == null || supplier.isEmpty()) {
            validationMessage = validationMessage + "\n " + getString(R.string.stationery_brand).replace(':', ' ');
            flag = false;
        }
        // Check that the stationery supplier phone is not null
        String supplierPhone = values.getAsString(StationeryContract.StationeryEntry.COLUMN_STATIONERY_SUPPLIER_PHONE);
        if (supplierPhone == null || supplierPhone.isEmpty()) {
            validationMessage = validationMessage + "\n " + getString(R.string.stationery_supplier_phone).replace(':', ' ');
            flag = false;
        }
        // Check that the stationery quantity is not empty
        Integer quantity = values.getAsInteger(StationeryContract.StationeryEntry.COLUMN_STATIONERY_QUANTITY);
        if (quantity == null) {
            validationMessage = validationMessage + "\n " + getString(R.string.stationery_quantity).replace(':', ' ');
            flag = false;
        }
        // Check that the stationery price is not empty
        Double price = values.getAsDouble(StationeryContract.StationeryEntry.COLUMN_STATIONERY_PRICE);
        if (price == null) {
            validationMessage = validationMessage + "\n " + getString(R.string.stationery_price).replace(':', ' ');
            flag = false;
        }
        return flag;
    }

    //Prompt the user to confirm that they want to delete this stationery
    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteStationery();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //Perform the deletion of the stationery in the database
    private void deleteStationery() {
        // Only perform the delete if this is an existing stationery
        if (itemID != -1) {
            // Call the ContentResolver to delete the stationery at the given content URI.
            int rowsDeleted = getContentResolver().delete(ContentUris.withAppendedId(StationeryContract.StationeryEntry.STATIONERY_URI, itemID), null, null);
            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.editor_delete_stationery_failed), Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_delete_stationery_successful), Toast.LENGTH_SHORT).show();
                // Notify that a row was updated and attempt to sync changes
                this.getContentResolver().notifyChange(StationeryContract.StationeryEntry.STATIONERY_URI, null);
            }
        }
        // Close the activity
        finish();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Since the editor shows all stationery attributes, define a projection that contains
        // all columns from the stationery table
        String[] projection = {
                StationeryContract.StationeryEntry._ID,
                StationeryContract.StationeryEntry.COLUMN_STATIONERY_ITEMNAME,
                StationeryContract.StationeryEntry.COLUMN_STATIONERY_ITEMIMAGEDATA,
                StationeryContract.StationeryEntry.COLUMN_STATIONERY_ITEMIMAGENAME,
                StationeryContract.StationeryEntry.COLUMN_STATIONERY_SUPPLIER,
                StationeryContract.StationeryEntry.COLUMN_STATIONERY_SUPPLIER_PHONE,
                StationeryContract.StationeryEntry.COLUMN_STATIONERY_PRICE,
                StationeryContract.StationeryEntry.COLUMN_STATIONERY_QUANTITY};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                ContentUris.withAppendedId(StationeryContract.StationeryEntry.STATIONERY_URI, itemID),   // Query the content URI for the current stationery
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (data == null || data.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (data.moveToFirst()) {
            // Extract out the value from the Cursor for the given column index
            int code = data.getInt(data.getColumnIndex(StationeryContract.StationeryEntry._ID));
            String name = data.getString(data.getColumnIndex(StationeryContract.StationeryEntry.COLUMN_STATIONERY_ITEMNAME));
            String supplier = data.getString(data.getColumnIndex(StationeryContract.StationeryEntry.COLUMN_STATIONERY_SUPPLIER));
            String supplierPhone = data.getString(data.getColumnIndex(StationeryContract.StationeryEntry.COLUMN_STATIONERY_SUPPLIER_PHONE));
            double price = data.getDouble(data.getColumnIndex(StationeryContract.StationeryEntry.COLUMN_STATIONERY_PRICE));
            int quantity = data.getInt(data.getColumnIndex(StationeryContract.StationeryEntry.COLUMN_STATIONERY_QUANTITY));
            String imageName = data.getString(data.getColumnIndex(StationeryContract.StationeryEntry.COLUMN_STATIONERY_ITEMIMAGENAME));
            byte[] image = data.getBlob(data.getColumnIndex(StationeryContract.StationeryEntry.COLUMN_STATIONERY_ITEMIMAGEDATA));

            // Update the views on the screen with the values from the database
            nameEditText.setText(name);
            supplierEditText.setText(supplier);
            supplierPhoneEditText.setText(supplierPhone);
            quantityEditText.setText(String.valueOf(quantity));
            if (quantity == 0)
                btnDecrease.setEnabled(false);
            priceEditText.setText(Double.toString(price));
            imageNameEditText.setText(imageName);
            codeTextView.setText(String.valueOf(code));
            if (image != null && image.length > 0) {
                imageToUpload.setImageBitmap(getImage(image));
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If the loader is invalidated, clear out all the data from the input fields.
        ClearStationeryData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_item:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;
            case R.id.btn_browse:
                Intent galleryIntent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent1, RESULT_LOAD_IMAGE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imageToUpload.setImageURI(selectedImage);
        }
    }

    // Increase quantity
    public void increaseQuantity(View view) {
        LinearLayout lr = (LinearLayout) view.getParent();
        String quantityText = quantityEditText.getText().toString();
        Integer quantity = Integer.valueOf(quantityText);
        quantityEditText.setText(String.valueOf(quantity + 1));
        if (quantity == 0) {
            btnDecrease.setEnabled(true);
        }
    }

    //Descrease quantity
    public void descreaseQuantity(View view) {
        LinearLayout lr = (LinearLayout) view.getParent();
        String quantityText = quantityEditText.getText().toString();
        Integer quantity = Integer.valueOf(quantityText);
        if (quantity > 0) {
            quantityEditText.setText(String.valueOf(quantity - 1));
            if (quantity == 0)
                btnDecrease.setEnabled(false);
        }
    }

    public void delete(View view) {
        showDeleteConfirmationDialog();
    }

    public void order(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", supplierPhoneEditText.getText().toString(), null));
        startActivity(intent);
    }
}
