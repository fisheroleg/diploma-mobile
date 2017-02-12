package listexmobile.listex.info.listexmobile.models;

/**
 * Created by oleg-note on 13.06.2016.
 */
public class SearchResult {
    String mName, mTMName, mGoodId, mGTIN;

    public SearchResult(String name, String TMName, String GoodId, String GTIN) {
        mName = name;
        mTMName = TMName;
        mGoodId = GoodId;
        mGTIN = GTIN;
    }

    public String getName() { return mName; }

    public String getTMName() { return mTMName; }

    public String getGoodId() { return mGoodId; }

    public String getGTIN() { return mGTIN; }
}
