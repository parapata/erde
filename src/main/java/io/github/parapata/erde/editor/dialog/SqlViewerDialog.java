package io.github.parapata.erde.editor.dialog;

import static io.github.parapata.erde.Resource.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.VerticalRuler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import io.github.parapata.erde.ERDPlugin;
import io.github.parapata.erde.ICON;
import io.github.parapata.erde.core.util.swt.UIUtils;
import io.github.parapata.erde.sqleditor.SQLConfiguration;
import io.github.parapata.erde.sqleditor.SQLPartitionScanner;

/**
 * SqlViewerDialog.
 *
 * @author modified by parapata
 */
public class SqlViewerDialog extends Dialog {

    private String ddl;
    private StyledText text;

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
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText(DIALOG_SQL_VIEWER_TITLE.getValue());
        newShell.setImage(ERDPlugin.getImage(ICON.TABLE.getPath()));
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        createMenuber();
        return createEditer(parent);
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        // createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
    }

    private void createMenuber() {
        Shell shell = getShell();

        Menu menubar = new Menu(shell, SWT.BAR);
        shell.setMenuBar(menubar);

        MenuItem fileMenuItem = new MenuItem(menubar, SWT.CASCADE);
        fileMenuItem.setText(MENUBER_FILE.getValue());

        Menu fileMenu = new Menu(fileMenuItem);
        fileMenuItem.setMenu(fileMenu);

        MenuItem fileMenuSave = new MenuItem(fileMenu, SWT.PUSH);
        fileMenuSave.setText(MENUBER_SAVE.getValue());
        fileMenuSave.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                FileDialog saveDialog = new FileDialog(shell, SWT.SAVE);
                String saveFile = saveDialog.open();
                output(saveFile);
            }
        });

        new MenuItem(fileMenu, SWT.SEPARATOR);

        MenuItem fileMenuQuit = new MenuItem(fileMenu, SWT.PUSH);
        fileMenuQuit.setText(MENUBER_QUIT.getValue());
        fileMenuQuit.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                shell.dispose();
            }
        });
    }

    private StyledText createEditer(Composite parent) {
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

        text = sqlEditor.getTextWidget();
        text.setText(ddl);
        text.setEditable(true);
        return text;
    }

    private void output(String saveFile) {
        try (FileOutputStream fos = new FileOutputStream(saveFile);
                OutputStreamWriter osw = new OutputStreamWriter(fos, ERDPlugin.getCharset())) {
            osw.write(text.getText());
        } catch (IOException e) {
            UIUtils.openAlertDialog(ACTION_AUTO_LAYOUT);
        }
    }
}
