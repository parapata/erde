
package io.github.erde.editor.persistent.adapter;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * IntegerAdapter.
 *
 * @author modified by parapata
 */
public class IntegerAdapter extends XmlAdapter<String, Integer> {

    @Override
    public Integer unmarshal(String value) {
        return Integer.parseInt(value);
    }

    @Override
    public String marshal(Integer value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }
}
