package io.github.erde.editor.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.VerticalRuler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import io.github.erde.sqleditor.SQLConfiguration;
import io.github.erde.sqleditor.SQLPartitionScanner;

/**
 * DDLDisplayDialog.
 *
 * @author modified by parapata
 */
public class DDLDisplayDialog extends Dialog {

    private String ddl;

    public DDLDisplayDialog(Shell parentShell, String ddl) {
        super(parentShell);
        this.ddl = ddl;
        setShellStyle(getShellStyle() | SWT.RESIZE);
    }

    @Override
    protected Point getInitialSize() {
        return new Point(600, 450);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        getShell().setText("DDL");

        SourceViewer sqlEditor = new SourceViewer(parent, new VerticalRuler(0), SWT.V_SCROLL | SWT.H_SCROLL);
        sqlEditor.configure(new SQLConfiguration());
        sqlEditor.getTextWidget().setFont(JFaceResources.getTextFont());

        Document document = new Document();
        IDocumentPartitioner partitioner = new FastPartitioner(new SQLPartitionScanner(),
                new String[] { SQLPartitionScanner.SQL_COMMENT, SQLPartitionScanner.SQL_STRING });
        partitioner.connect(document);
        document.setDocumentPartitioner(partitioner);
        sqlEditor.setDocument(document);
        sqlEditor.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));

        StyledText text = sqlEditor.getTextWidget();
        text.setText(ddl);
        text.setEditable(false);

        return text;
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
    }
}
