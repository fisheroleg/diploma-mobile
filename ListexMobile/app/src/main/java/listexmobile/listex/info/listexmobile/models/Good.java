    package listexmobile.listex.info.listexmobile.models;

    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.graphics.drawable.Drawable;
    import android.widget.ArrayAdapter;

    import com.google.gson.Gson;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.Calendar;
    import java.util.List;

    import listexmobile.listex.info.listexmobile.helpers.DBContract;
    import listexmobile.listex.info.listexmobile.helpers.DatabaseHelper;
    import listexmobile.listex.info.listexmobile.helpers.ReviewFactory;
    import listexmobile.listex.info.listexmobile.networking.PhotoLoader;

    /**
     * Created by oleg-note on 14.05.2016.
     */
    public class Good {
        String mDTC = null;
        ArrayList<Attr> mAttrs;
        int mId;
        String mName;
        Float mRating;
        Integer mVotes;
        Drawable mImage;
        ArrayList<String> mPhotos;
        ArrayList<Review> mReviews;

        public Good(int id, String name, ArrayList<Attr> attrs, ArrayList<String> photos, Float rating, Integer votes) {
            this.mId = id;
            this.mRating = rating;
            this.mVotes = votes;
            this.mName = name;
            this.mAttrs = attrs;
            this.mPhotos = photos;
        }

        public Good(int id, Context context) {
            DatabaseHelper mDbHelper = new DatabaseHelper(context);
            SQLiteDatabase db = mDbHelper.getReadableDatabase();

            String[] projection = {
                    DBContract.GoodEntry.COLUMN_NAME_GOOD_ID,
                    DBContract.GoodEntry.COLUMN_NAME_NAME,
                    DBContract.GoodEntry.COLUMN_NAME_ATTRS,
                    DBContract.GoodEntry.COLUMN_NAME_VOTES,
                    DBContract.GoodEntry.COLUMN_NAME_PHOTO,
                    DBContract.GoodEntry.COLUMN_NAME_RATING,
                    DBContract.GoodEntry.COLUMN_NAME_DTC
            };

            String selection = DBContract.GoodEntry.COLUMN_NAME_GOOD_ID + " = ?";
            String[] selectionArgs = new String[] { Integer.toString(id) };

            ArrayList<Good> goods = new ArrayList<>();
            Cursor c = db.query(DBContract.GoodEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
            if (c != null ) {
                if  (c.moveToFirst()) {
                    mName = c.getString(c.getColumnIndex(DBContract.GoodEntry.COLUMN_NAME_NAME));
                    mId = c.getInt(c.getColumnIndex(DBContract.GoodEntry.COLUMN_NAME_GOOD_ID));
                    mPhotos = new ArrayList<>();
                    mPhotos.add(
                            c.getString(c.getColumnIndex(DBContract.GoodEntry.COLUMN_NAME_PHOTO))
                    );
                    mRating = Float.parseFloat(
                            c.getString(c.getColumnIndex(DBContract.GoodEntry.COLUMN_NAME_RATING))
                    );
                    mVotes = Integer.parseInt(
                            c.getString(c.getColumnIndex(DBContract.GoodEntry.COLUMN_NAME_VOTES))
                    );
                    mDTC = c.getString(c.getColumnIndex(DBContract.GoodEntry.COLUMN_NAME_DTC));

                    try {
                        JSONArray attrs = new JSONArray(
                                c.getString(c.getColumnIndex(DBContract.GoodEntry.COLUMN_NAME_ATTRS))
                        );
                        mAttrs = new ArrayList<>();
                        for (int i = 0; i < attrs.length(); i++)
                        {
                            JSONObject row = attrs.getJSONObject(i);
                            mAttrs.add(new Attr(row.getString("_attr"), row.getString("_value") ));
                        }

                    } catch (Exception e) {
                        // TODO Handle
                        e.printStackTrace();
                        c.close();
                    }
                }
                c.close();
            }
        }

        public Good(JSONObject content) {
            try {
                JSONObject goodObject = content.getJSONObject("object").getJSONObject("goods");
                JSONArray attrsObject = goodObject.getJSONArray("attrvalues");
                Integer id = Integer.parseInt(goodObject.getString("id"));
                String name = goodObject.getString("name");

                // get attrs
                ArrayList<Attr> attrs = new ArrayList<>();
                List filter = Arrays.asList("Страна", "Глубина", "Ширина", "Высота", "Вес брутто");
                for (int i = 0; i < attrsObject.length(); i++)
                {
                    JSONObject row = attrsObject.getJSONObject(i);
                    boolean single = true;
                    try {
                        if ( ! row.getString("multiplier").equals("1.00"))
                            single = false;
                    } catch (JSONException e) {
                    }

                    if(filter.contains(row.getString("name")) && single) {
                        String type;
                        try {
                            type = row.getString("type");
                        } catch (JSONException e) {
                            type = "";
                        }
                        attrs.add(new Attr(row.getString("name"), row.getString("value") + type));
                    }
                }

                // TODO: handle error
                // get photos
                JSONArray photos = goodObject.getJSONArray("photos");
                ArrayList<String> links = new ArrayList<>();
                for(int i = 0 ; i < photos.length(); i++){
                    JSONObject photo = photos.getJSONObject(i);
                    if (photo.getString("type").equals("default")) {
                        links.add(photo.getString("url"));
                    }
                }

                //TODO: add data to response
                // get reviews
                this.mReviews = ReviewFactory.parseReviews(content);

                // get rating
                this.mRating = 4.5f;/*Float.parseFloat(
                        content.getJSONObject("object").getString("rating")
                );*/
                this.mId = id;
                this.mName = name;
                this.mAttrs = attrs;
                this.mPhotos = links;
            } catch (JSONException e) {
                e.printStackTrace();
                //TODO Handle json error
            }
        }

        public boolean Save(Context context) {
            DatabaseHelper mDbHelper = new DatabaseHelper(context);
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH-mm-ss");
            String formattedDate = df.format(c.getTime());

            ContentValues values = new ContentValues();
            values.put(DBContract.GoodEntry.COLUMN_NAME_GOOD_ID, mId);
            values.put(DBContract.GoodEntry.COLUMN_NAME_NAME, mName);
            values.put(DBContract.GoodEntry.COLUMN_NAME_DTC, formattedDate);
            values.put(DBContract.GoodEntry.COLUMN_NAME_PHOTO, mPhotos == null ? "" :
                    mPhotos.get(0));
            values.put(DBContract.GoodEntry.COLUMN_NAME_ATTRS, mAttrs == null ? "" :
                    new Gson().toJson(mAttrs));
            values.put(DBContract.GoodEntry.COLUMN_NAME_RATING, mRating == null ? "0" :
                    mRating.toString());
            values.put(DBContract.GoodEntry.COLUMN_NAME_VOTES, mVotes == null ? "0" :
                    mVotes.toString());

            db.replace(
                    DBContract.GoodEntry.TABLE_NAME,
                    null,
                    values);
            return true;
        }

        public void delete(Context context) {
            DatabaseHelper mDbHelper = new DatabaseHelper(context);
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            db.delete(DBContract.GoodEntry.TABLE_NAME, DBContract.GoodEntry.COLUMN_NAME_GOOD_ID + "=" + Integer.toString(mId), null);
        }

        public void loadPhoto() {
            if (mPhotos != null) {
                try {
                    PhotoLoader pl = new PhotoLoader(mPhotos.get(0));
                    pl.execute().get();
                    mImage = pl.img;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public int getId() {
            return mId;
        }

        public String getName() {
            return mName;
        }

        public ArrayList<Attr> getAttrs() {
            return mAttrs;
        }

        public ArrayList<String> getPhotos() {
            return mPhotos;
        }

        public ArrayList<Review> getReviews() {
            return mReviews;
        }

        public Float getRating() {
            return mRating;
        }

        public int getVotes() {
            return mVotes;
        }

        public Drawable getImage() {
            return mImage;
        }

        public String getDTC() { return mDTC; }
    }
