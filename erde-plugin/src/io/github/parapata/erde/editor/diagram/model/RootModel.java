package io.github.parapata.erde.editor.diagram.model;

import static io.github.parapata.erde.Resource.*;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import io.github.parapata.erde.core.util.StringUtils;
import io.github.parapata.erde.core.util.swt.FontDataWrapper;
import io.github.parapata.erde.core.util.swt.FontPropertyDescriptor;
import io.github.parapata.erde.dialect.DialectProvider;

/**
 * The root model of the ER diagram.
 *
 * @author modified by parapata
 */
public class RootModel extends BaseModel implements IPropertySource {

    private static final long serialVersionUID = 1L;

    public static final String P_LOWERCASE = "p_lowercase";
    public static final String P_MODE = "p_mode";
    public static final String P_NOTATION = "p_notation";
    public static final String P_JDBC_INFO = "p_jdbc_info";
    public static final String P_CHILDREN = "p_children";
    public static final String P_DOMAINS = "p_domains";
    public static final String P_FONT = "p_font";
    public static final String P_ZOOM = "p_zoom";

    private DialectProvider dialectProvider;
    private String schemaName;
    private boolean lowercase;
    private boolean logicalMode;
    private boolean includeView;
    private String notation;
    private String fontData;
    private double zoom;

    private String jarFile;
    private String jdbcDriver;
    private String jdbcUrl;
    private String jdbcCatalog;
    private String jdbcSchema;
    private String jdbcUser;
    private String jdbcPassword;

    private List<BaseEntityModel> children = new ArrayList<>();
    private List<DomainModel> domains = new ArrayList<>();

    public void copyFrom(RootModel model) {
        setDialectProvider(model.getDialectProvider());
        setLowercase(model.isLowercase());
        setLogicalMode(model.isLogicalMode());
        setIncludeView(model.isIncludeView());

        setJarFile(model.getJarFile());
        setJdbcDriver(model.getJdbcDriver());
        setJdbcUrl(model.getJdbcUrl());
        setJdbcCatalog(model.getJdbcCatalog());
        setJdbcSchema(model.getJdbcSchema());
        setJdbcUser(model.getJdbcUser());
        setJdbcPassword(model.getJdbcPassword());

        children.clear();
        children.addAll(model.getChildren());

        domains.clear();
        domains.addAll(model.getDomains());

        firePropertyChange(P_CHILDREN, null, null);
    }

    public DialectProvider getDialectProvider() {
        return dialectProvider;
    }

    public void setDialectProvider(DialectProvider dialectProvider) {
        this.dialectProvider = dialectProvider;
    }

    public String getSchemaName() {
        return StringUtils.defaultString(schemaName);
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = StringUtils.defaultString(schemaName);
    }

    public boolean isLowercase() {
        return lowercase;
    }

    public void setLowercase(boolean lowercase) {
        this.lowercase = lowercase;
        firePropertyChange(P_LOWERCASE, null, logicalMode);
    }

    public boolean isLogicalMode() {
        return this.logicalMode;
    }

    public void setLogicalMode(boolean logicalMode) {
        this.logicalMode = logicalMode;
        firePropertyChange(P_MODE, null, logicalMode);
    }

    public boolean isIncludeView() {
        return includeView;
    }

    public void setIncludeView(boolean includeView) {
        this.includeView = includeView;
        firePropertyChange(P_JDBC_INFO, null, jdbcCatalog);
    }

    public String getNotation() {
        return StringUtils.defaultString(notation);
    }

    public void setNotation(String notation) {
        this.notation = StringUtils.defaultString(notation);
        firePropertyChange(P_NOTATION, null, logicalMode);
    }

    public String getFontData() {
        return StringUtils.defaultString(fontData);
    }

    public void setFontData(FontData[] fontData) {
        this.fontData = PreferenceConverter.getStoredRepresentation(fontData);
        firePropertyChange(P_FONT, null, fontData);
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
        firePropertyChange(P_ZOOM, null, this.zoom);
    }

    public String getJarFile() {
        return StringUtils.defaultString(jarFile);
    }

    public void setJarFile(String jarFile) {
        this.jarFile = StringUtils.defaultString(jarFile);
        firePropertyChange(P_JDBC_INFO, null, this.jarFile);
    }

    public String getJdbcDriver() {
        return StringUtils.defaultString(jdbcDriver);
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = StringUtils.defaultString(jdbcDriver);
        firePropertyChange(P_JDBC_INFO, null, this.jdbcDriver);
    }

    public String getJdbcUrl() {
        return StringUtils.defaultString(jdbcUrl);
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = StringUtils.defaultString(jdbcUrl);
        firePropertyChange(P_JDBC_INFO, null, this.jdbcUrl);
    }

    public String getJdbcCatalog() {
        return StringUtils.defaultString(jdbcCatalog);
    }

    public void setJdbcCatalog(String jdbcCatalog) {
        this.jdbcCatalog = StringUtils.defaultString(jdbcCatalog);
        firePropertyChange(P_JDBC_INFO, null, this.jdbcCatalog);
    }

    public String getJdbcSchema() {
        return StringUtils.defaultString(jdbcSchema);
    }

    public void setJdbcSchema(String jdbcSchema) {
        this.jdbcSchema = StringUtils.defaultString(jdbcSchema);
        firePropertyChange(P_JDBC_INFO, null, this.jdbcSchema);
    }

    public String getJdbcUser() {
        return StringUtils.defaultString(jdbcUser);
    }

    public void setJdbcUser(String jdbcUser) {
        this.jdbcUser = StringUtils.defaultString(jdbcUser);
        firePropertyChange(P_JDBC_INFO, null, this.jdbcUser);
    }

    public String getJdbcPassword() {
        return StringUtils.defaultString(jdbcPassword);
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = StringUtils.defaultString(jdbcPassword);
        firePropertyChange(P_JDBC_INFO, null, this.jdbcPassword);
    }

    public void addChild(BaseEntityModel model) {
        children.add(model);
        firePropertyChange(P_CHILDREN, null, model);
    }

    public void removeChild(BaseEntityModel model) {
        children.remove(model);
        firePropertyChange(P_CHILDREN, model, null);
    }

    public List<BaseEntityModel> getChildren() {
        return this.children;
    }

    public List<TableModel> getTables() {
        List<TableModel> result = new ArrayList<>();
        for (BaseEntityModel model : getChildren()) {
            if (model instanceof TableModel) {
                result.add((TableModel) model);
            }
        }
        return result;
    }

    public TableModel getTable(String tableName) {
        for (BaseEntityModel child : children) {
            if (child instanceof TableModel) {
                TableModel table = (TableModel) child;
                if (table.getPhysicalName().equals(tableName)) {
                    return table;
                }
            }
        }
        return null;
    }

    public List<DomainModel> getDomains() {
        if (this.domains == null) {
            this.domains = new ArrayList<>();
        }
        return this.domains;
    }

    public void setDomains(List<DomainModel> domains) {
        this.domains = domains;
        for (BaseEntityModel entity : getChildren()) {
            if (entity instanceof TableModel) {
                TableModel table = (TableModel) entity;
                for (ColumnModel column : table.getColumns()) {
                    if (column.getColumnType().isDomain()) {
                        for (DomainModel domain : domains) {
                            if (domain.getId().equals(((DomainModel) column.getColumnType()).getId())) {
                                column.setColumnType(domain);
                                break;
                            }
                        }
                    }
                }
                table.firePropertyChange(TableModel.P_COLUMNS, null, null);
            }
        }
        firePropertyChange(P_DOMAINS, null, domains);
    }

    @Override
    public Object getEditableValue() {
        return this;
    }

    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        return new IPropertyDescriptor[] {
                new TextPropertyDescriptor(P_JDBC_INFO, LABEL_SCHEMA.getValue()),
                new FontPropertyDescriptor(P_FONT, PROPERTY_FONT.getValue())
        };
    }

    @Override
    public Object getPropertyValue(Object id) {
        if (id == P_JDBC_INFO) {
            return getJdbcSchema();
        } else if (id == P_FONT) {
            return new FontDataWrapper(getFontData());
        }
        return null;
    }

    @Override
    public boolean isPropertySet(Object id) {
        if (id == P_JDBC_INFO || id == P_FONT) {
            return true;
        }
        return false;
    }

    @Override
    public void resetPropertyValue(Object id) {
    }

    @Override
    public void setPropertyValue(Object id, Object value) {
        if (id == P_JDBC_INFO) {
            setJdbcSchema((String) value);
        } else if (id == P_FONT) {
            setFontData(((FontDataWrapper) value).getFontData());
        }
    }
}
