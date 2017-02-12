package listexmobile.listex.info.listexmobile.models;

/**
 * Created by oleg-note on 26.04.2016.
 */
public class Attr {
    private String _value, _attr;

    public Attr(String attr, String value) {
        this._value = value;
        this._attr = attr;
    }

    public String getName() {
        return this._attr;
    }

    public String getValue() {
        return this._value;
    }
}
