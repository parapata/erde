package io.github.erde.wizard.page;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;

import io.github.erde.Activator;
import io.github.erde.Resource;
import io.github.erde.core.util.JDBCConnection;
import io.github.erde.core.util.JarClassLoader;
import io.github.erde.core.util.StringUtils;
import io.github.erde.core.util.swt.UIUtils;
import io.github.erde.dialect.DialectProvider;
import io.github.erde.editor.diagram.model.RootModel;

/**
 * ImportFromJDBCWizardPage1.
 *
 * @author modified by parapata
 */
public class ImportFromJDBCWizardPage1 extends WizardPage {

    private JarClassLoader classLoader;
    private ResourceBundle url = ResourceBundle.getBundle("io.github.erde.wizard.databaseURI");
    private RootModel model;

    private Combo cmbDialectProvider;
    private Text txtJarFile;
    private Combo cmbJdbcDriver;
    private Text txtJdbcURI;
    private Text txtJdbcCatalog;
    private Text txtJdbcSchema;
    private Text txtJdbcUser;
    private Text txtJdbcPassword;
    private Button btnAutoConvert;

    public ImportFromJDBCWizardPage1() {
        this(null);
    }

    public ImportFromJDBCWizardPage1(RootModel model) {
        super(Resource.WIZARD_NEW_IMPORT_TITLE.getValue());
        setTitle(Resource.WIZARD_NEW_IMPORT_TITLE.getValue());
        setMessage(Resource.WIZARD_NEW_IMPORT_MESSAGE.getValue());
        this.model = model;
    }

    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NULL);
        container.setLayout(new GridLayout(4, false));
        container.setLayoutData(new GridData(GridData.FILL_BOTH));

        // -------------
        UIUtils.createLabel(container, Resource.WIZARD_NEW_IMPORT_DATABASE);
        cmbDialectProvider = new Combo(container, SWT.READ_ONLY);
        DialectProvider.getDialectNames().forEach(item -> {
            cmbDialectProvider.add(item);
        });
        if (StringUtils.isNotEmpty(model.getDialectProvider().name())) {
            cmbDialectProvider.setText(model.getDialectProvider().name());
        }
        cmbDialectProvider.setLayoutData(UIUtils.createGridData(3));
        cmbDialectProvider.addModifyListener(event -> {
            doValidate();
        });

        // -------------
        UIUtils.createLabel(container, Resource.WIZARD_NEW_IMPORT_JAR_FILE);
        txtJarFile = new Text(container, SWT.BORDER | SWT.SINGLE);
        txtJarFile.setEditable(false);
        txtJarFile.setLayoutData(UIUtils.createGridData(2));
        txtJarFile.addModifyListener(event -> {
            doValidate();
        });

        Button button = new Button(container, SWT.PUSH);
        button.setText(Resource.BUTTON_BROWSE.getValue());
        button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                handleFileSystemBrowse();
            }
        });

        // -------------
        UIUtils.createLabel(container, Resource.WIZARD_NEW_IMPORT_DRIVER);
        cmbJdbcDriver = new Combo(container, SWT.READ_ONLY);
        cmbJdbcDriver.setLayoutData(UIUtils.createGridData(3));

        // -------------
        UIUtils.createLabel(container, Resource.WIZARD_NEW_IMPORT_URI);
        txtJdbcURI = new Text(container, SWT.BORDER | SWT.SINGLE);
        txtJdbcURI.setLayoutData(UIUtils.createGridData(3));
        txtJdbcURI.addModifyListener(event -> {
            doValidate();
        });

        cmbJdbcDriver.addModifyListener(event -> {
            if (Collections.list(url.getKeys()).contains(cmbJdbcDriver.getText())) {
                String template = url.getString(cmbJdbcDriver.getText());
                txtJdbcURI.setText(template);
            }
            doValidate();
        });

        // -------------
        UIUtils.createLabel(container, Resource.WIZARD_NEW_IMPORT_USER);
        txtJdbcUser = new Text(container, SWT.BORDER | SWT.SINGLE);
        txtJdbcUser.setLayoutData(UIUtils.createGridData(3));
        txtJdbcUser.addModifyListener(event -> {
            doValidate();
        });

        // -------------
        UIUtils.createLabel(container, Resource.WIZARD_NEW_IMPORT_PASS);
        txtJdbcPassword = new Text(container, SWT.BORDER | SWT.PASSWORD);
        txtJdbcPassword.setLayoutData(UIUtils.createGridData(3));

        // -------------
        UIUtils.createLabel(container, Resource.WIZARD_NEW_IMPORT_SCHEMA);
        txtJdbcSchema = new Text(container, SWT.BORDER | SWT.SINGLE);
        txtJdbcSchema.setLayoutData(UIUtils.createGridData(3));

        // -------------
        UIUtils.createLabel(container, Resource.WIZARD_NEW_IMPORT_CATALOG);
        txtJdbcCatalog = new Text(container, SWT.BORDER | SWT.SINGLE);
        txtJdbcCatalog.setLayoutData(UIUtils.createGridData(3));

        // ----------------
        btnAutoConvert = new Button(container, SWT.CHECK);
        btnAutoConvert.setText(Resource.WIZARD_NEW_IMPORT_AUTO_CONVERT.getValue());
        btnAutoConvert.setLayoutData(UIUtils.createGridData(4));

        if (model != null) {
            txtJarFile.setText(model.getJarFile());
            loadJdbcDriver();
            cmbJdbcDriver.setText(StringUtils.defaultString(model.getJdbcDriver()));
            txtJdbcURI.setText(StringUtils.defaultString(model.getJdbcUrl()));
            txtJdbcUser.setText(StringUtils.defaultString(model.getJdbcUser()));
            txtJdbcPassword.setText(StringUtils.defaultString(model.getJdbcPassword()));
            txtJdbcCatalog.setText(StringUtils.defaultString(model.getJdbcCatalog()));
            txtJdbcSchema.setText(StringUtils.defaultString(model.getJdbcSchema()));
        }
        doValidate();
        setControl(container);
    }

    public JDBCConnection getJDBCConnection() throws Exception {
        Class<?> driverClass = classLoader.loadClass(cmbJdbcDriver.getText());
        JDBCConnection jdbcConn = new JDBCConnection(driverClass);
        jdbcConn.setDialectProvider(cmbDialectProvider.getText());
        jdbcConn.setURI(txtJdbcURI.getText());
        jdbcConn.setUser(txtJdbcUser.getText());
        jdbcConn.setPassword(txtJdbcPassword.getText());
        jdbcConn.setCatalog(txtJdbcCatalog.getText());
        jdbcConn.setSchema(txtJdbcSchema.getText());
        jdbcConn.setEnableView(true);
        jdbcConn.setAutoConvert(btnAutoConvert.getSelection());
        return jdbcConn;
    }

    public void setJDBCSetting() {
        model.setJarFile(txtJarFile.getText());
        model.setJdbcDriver(cmbJdbcDriver.getText());
        model.setJdbcUrl(txtJdbcURI.getText());
        model.setJdbcUser(txtJdbcUser.getText());
        model.setJdbcPassword(txtJdbcPassword.getText());
        model.setJdbcCatalog(txtJdbcCatalog.getText());
        model.setJdbcSchema(txtJdbcSchema.getText());
        model.setIncludeView(true);
    }

    private void loadJdbcDriver() {
        try {
            URL jarURL = null;
            String jarFilePath = txtJarFile.getText();

            if (jarFilePath.startsWith("workspace:")) {
                IWorkspaceRoot wsroot = ResourcesPlugin.getWorkspace().getRoot();
                jarFilePath = jarFilePath.replaceFirst("^workspace:", "");

                IFile file = wsroot.getFile(new Path(jarFilePath));
                jarFilePath = file.getLocation().makeAbsolute().toString();

                jarURL = new URL("file:///" + jarFilePath);

            } else {
                jarURL = new URL("file:///" + jarFilePath);
            }

            URL[] classpathes = getClassPathUrls(jarURL);
            classLoader = new JarClassLoader(classpathes);
            java.util.List<Class<?>> list = classLoader.getJDBCDriverClass(jarFilePath);
            cmbJdbcDriver.removeAll();
            for (Class<?> item : list) {
                if (Arrays.binarySearch(cmbJdbcDriver.getItems(), item.getName()) < 0) {
                    cmbJdbcDriver.add(item.getName());
                }
            }
            cmbJdbcDriver.select(0);
        } catch (Exception e) {
            Activator.logException(e);
        }
    }

    private URL[] getClassPathUrls(URL jarURL) {
        // TODO 修正
        URL[] classpathes = new URL[0];
        URL[] clspath = new URL[classpathes.length + 1];
        clspath[0] = jarURL;
        for (int i = 0; i < classpathes.length; i++) {
            clspath[i + 1] = classpathes[i];
        }
        return clspath;
    }

    /**
     * Choose a jar file which contains the JDBC driver from local file system.
     */
    private void handleFileSystemBrowse() {
        FileDialog dialog = new FileDialog(getShell());
        if (dialog.open() == null) {
            return;
        }
        txtJarFile.setText(dialog.getFilterPath() + System.getProperty("file.separator") + dialog.getFileName());
        loadJdbcDriver();
    }

    private void doValidate() {
        if (cmbDialectProvider.getSelectionIndex() < 0) {
            setPageComplete(false);
            return;
        }
        if (StringUtils.isNoneEmpty(txtJarFile.getText())) {
            setPageComplete(false);
            return;
        }
        if (cmbJdbcDriver.getSelectionIndex() < 0) {
            setPageComplete(false);
            return;
        }
        if (StringUtils.isNoneEmpty(txtJdbcURI.getText())) {
            setPageComplete(false);
            return;
        }
        if (StringUtils.isNoneEmpty(txtJdbcUser.getText())) {
            setPageComplete(false);
            return;
        }
        setPageComplete(true);
    }
}
