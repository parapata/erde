package io.github.erde.editor.dialog;
import static io.github.erde.Resource.*;

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
 * SqlViewerDialog.
 *
 * @author modified by parapata
 */
public class SqlViewerDialog extends Dialog {

    private String ddl;

    public SqlViewerDialog(Shell parentShell, String ddl) {
        super(parentShell);
        this.ddl = ddl;
        setShellStyle(getShellStyle() | SWT.TITLE | SWT.RESIZE | SWT.MAX | SWT.MIN);
    }

    @Override
    protected Point getInitialSize() {
        return new Point(800, 600);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        getShell().setText(DIALOG_SQL_VIEWER_TITLE.getValue());

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
