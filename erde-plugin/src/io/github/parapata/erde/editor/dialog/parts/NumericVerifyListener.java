package io.github.parapata.erde.editor.dialog.parts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

import io.github.parapata.erde.core.util.StringUtils;

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
