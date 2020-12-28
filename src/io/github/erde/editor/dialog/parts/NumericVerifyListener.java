package io.github.erde.editor.dialog.parts;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

/**
 * NumericVerifyListener.
 *
 * @author modified by parapata
 */
public class NumericVerifyListener implements VerifyListener {

    @Override
    public void verifyText(VerifyEvent event) {
        if (event.keyCode == SWT.ARROW_LEFT
                || event.keyCode == SWT.ARROW_RIGHT
                || event.keyCode == SWT.BS
                || event.keyCode == SWT.DEL
                || event.keyCode == SWT.CTRL
                || event.keyCode == SWT.SHIFT) {
            event.doit = true;
        } else if (StringUtils.isEmpty(event.text)) {
            event.doit = true;
        } else if (StringUtils.isNumeric(event.text)) {
            event.doit = true;
        } else {
            event.doit = false;
        }
    }
}
