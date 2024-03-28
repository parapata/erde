
package io.github.parapata.erde.editor.persistent.adapter;

import jakarta.xml.bind.DatatypeConverter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * BooleanAdapter.
 *
 * @author modified by parapata
 */
public class BooleanAdapter extends XmlAdapter<String, Boolean> {

    @Override
    public Boolean unmarshal(String value) {
        return ((boolean) DatatypeConverter.parseBoolean(value));
    }

    @Override
    public String marshal(Boolean value) {
        if (value == null) {
            return null;
        }
        return (DatatypeConverter.printBoolean(value));
    }
}
