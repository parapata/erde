package io.github.erde;

import java.util.Arrays;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import io.github.erde.core.util.StringUtils;

/**
 * Resource Enum.
 *
 * @author parapata
 * @since 1.0.8
 */
public enum Resource {

    /** key:action.autoLayout. */
    ACTION_AUTO_LAYOUT("action.autoLayout"),
    /** key:action.changeDatabaseType. */
    ACTION_CHANGE_DATABASE_TYPE("action.changeDatabaseType"),
    /** key:action.copy. */
    ACTION_COPY("action.copy"),
    /** key:action.copyAsImage. */
    ACTION_COPY_AS_IMAGE("action.copyAsImage"),
    /** key:action.editDomain. */
    ACTION_EDIT_DOMAIN("action.editDomain"),
    /** key:action.export. */
    ACTION_EXPORT("action.export"),
    /** key:action.import. */
    ACTION_IMPORT("action.import"),
    /** key:action.importFromDB. */
    ACTION_IMPORT_FROM_DB("action.importFromDB"),
    /** key:action.paste. */
    ACTION_PASTE("action.paste"),
    /** key:action.print. */
    ACTION_PRINT("action.print"),
    /** key:action.quickOutline. */
    ACTION_QUICK_OUTLINE("action.quickOutline"),
    /** key:action.selectedTablesDDL. */
    ACTION_SELECTED_TABLES_DDL("action.selectedTablesDDL"),
    /** key:action.toggleMode. */
    ACTION_TOGGLE_MODE("action.toggleMode"),
    /** key:action.toggleNotation. */
    ACTION_TOGGLE_NOTATION("action.toggleNotation"),
    /** key:action.validation. */
    ACTION_VALIDATION("action.validation"),
    /** key:action.validation.deleteMarkers. */
    ACTION_VALIDATION_DELETE_MARKERS("action.validation.deleteMarkers"),
    /** key:action.validation.executeValidation. */
    ACTION_VALIDATION_EXECUTE_VALIDATION("action.validation.executeValidation"),
    /** key:columninfo.logicalName. */
    COLUMNINFO_LOGICAL_NAME("columninfo.logicalName"),
    /** key:columninfo.partialMatch. */
    COLUMNINFO_PARTIAL_MATCH("columninfo.partialMatch"),
    /** key:columninfo.physicalName. */
    COLUMNINFO_PHYSICAL_NAME("columninfo.physicalName"),
    /** key:dialog.alert.title. */
    DIALOG_ALERT_TITLE("dialog.alert.title"),
    /** key:dialog.column.title. */
    DIALOG_COLUMN_TITLE("dialog.column.title"),
    /** key:dialog.confirm.title. */
    DIALOG_CONFIRM_TITLE("dialog.confirm.title"),
    /** key:dialog.dictionary.title. */
    DIALOG_DICTIONARY_TITLE("dialog.dictionary.title"),
    /** key:dialog.domain.title. */
    DIALOG_DOMAIN_TITLE("dialog.domain.title"),
    /** key:dialog.enum.title. */
    DIALOG_ENUM_TITLE("dialog.enum.title"),
    /** key:dialog.indexColumnSelect.title. */
    DIALOG_INDEX_COLUMN_SELECT_TITLE("dialog.indexColumnSelect.title"),
    /** key:dialog.info.title. */
    DIALOG_INFO_TITLE("dialog.info.title"),
    /** key:dialog.mapping.primariyKey. */
    DIALOG_MAPPING_PRIMARIY_KEY("dialog.mapping.primariyKey"),
    /** key:dialog.mapping.title. */
    DIALOG_MAPPING_TITLE("dialog.mapping.title"),
    /** key:dialog.sqlViewer.title. */
    DIALOG_SQL_VIEWER_TITLE("dialog.sqlViewer.title"),
    /** key:dialog.table.title. */
    DIALOG_TABLE_TITLE("dialog.table.title"),
    /** key:label.add. */
    LABEL_ADD("label.add"),
    /** key:label.attribute. */
    LABEL_ATTRIBUTE("label.attribute"),
    /** key:label.autoIncrement. */
    LABEL_AUTO_INCREMENT("label.autoIncrement"),
    /** key:label.browse. */
    LABEL_BROWSE("label.browse"),
    /** key:label.catalog. */
    LABEL_CATALOG("label.catalog"),
    /** key:label.characterEncoding. */
    LABEL_CHARACTER_ENCODING("label.characterEncoding"),
    /** key:label.column. */
    LABEL_COLUMN("label.column"),
    /** key:label.columnFK. */
    LABEL_COLUMN_FK("label.columnFK"),
    /** key:label.columnPK. */
    LABEL_COLUMN_PK("label.columnPK"),
    /** key:label.database. */
    LABEL_DATABASE("label.database"),
    /** key:label.decimal. */
    LABEL_DECIMAL("label.decimal"),
    /** key:label.defaultValue. */
    LABEL_DEFAULT_VALUE("label.defaultValue"),
    /** key:label.delete. */
    LABEL_DELETE("label.delete"),
    /** key:label.description. */
    LABEL_DESCRIPTION("label.description"),
    /** key:label.domain. */
    LABEL_DOMAIN("label.domain"),
    /** key:label.domainName. */
    LABEL_DOMAIN_NAME("label.domainName"),
    /** key:label.downColumn. */
    LABEL_DOWN_COLUMN("label.downColumn"),
    /** key:label.edit. */
    LABEL_EDIT("label.edit"),
    /** key:label.editColumn. */
    LABEL_EDIT_COLUMN("label.editColumn"),
    /** key:label.enumSet. */
    LABEL_ENUM_SET("label.enumSet"),
    /** key:label.error. */
    LABEL_ERROR("label.error"),
    /** key:label.filename. */
    LABEL_FILENAME("label.filename"),
    /** key:label.foreignKeyName. */
    LABEL_FOREIGN_KEY_NAME("label.foreignKeyName"),
    /** key:label.id. */
    LABEL_ID("label.id"),
    /** key:label.ignore. */
    LABEL_IGNORE("label.ignore"),
    /** key:label.index. */
    LABEL_INDEX("label.index"),
    /** key:label.indexColumns. */
    LABEL_INDEX_COLUMNS("label.indexColumns"),
    /** key:label.indexName. */
    LABEL_INDEX_NAME("label.indexName"),
    /** key:label.indexType. */
    LABEL_INDEX_TYPE("label.indexType"),
    /** key:label.jarFile. */
    LABEL_JAR_FILE("label.jarFile"),
    /** key:label.jdbcDriver. */
    LABEL_JDBC_DRIVER("label.jdbcDriver"),
    /** key:label.jdbcUri. */
    LABEL_JDBC_URI("label.jdbcUri"),
    /** key:label.lineSeparator. */
    LABEL_LINE_SEPARATOR("label.lineSeparator"),
    /** key:label.logicalColumnName. */
    LABEL_LOGICAL_COLUMN_NAME("label.logicalColumnName"),
    /** key:label.logicalTableName. */
    LABEL_LOGICAL_TABLE_NAME("label.logicalTableName"),
    /** key:label.mapping. */
    LABEL_MAPPING("label.mapping"),
    /** key:label.multiple. */
    LABEL_MULTIPLE("label.multiple"),
    /** key:label.notNull. */
    LABEL_NOT_NULL("label.notNull"),
    /** key:label.o. */
    LABEL_O("label.o"),
    /** key:label.onDelete. */
    LABEL_ON_DELETE("label.onDelete"),
    /** key:label.onUpdate. */
    LABEL_ON_UPDATE("label.onUpdate"),
    /** key:label.option. */
    LABEL_OPTION("label.option"),
    /** key:label.partialMatch. */
    LABEL_PARTIAL_MATCH("label.partialMatch"),
    /** key:label.password. */
    LABEL_PASSWORD("label.password"),
    /** key:label.physicalName. */
    LABEL_PHYSICAL_NAME("label.physicalName"),
    /** key:label.physicalTableName. */
    LABEL_PHYSICAL_TABLE_NAME("label.physicalTableName"),
    /** key:label.pyhgicalColumnName. */
    LABEL_PYHGICAL_COLUMN_NAME("label.pyhgicalColumnName"),
    /** key:label.referred. */
    LABEL_REFERRED("label.referred"),
    /** key:label.schema. */
    LABEL_SCHEMA("label.schema"),
    /** key:label.size. */
    LABEL_SIZE("label.size"),
    /** key:label.table. */
    LABEL_TABLE("label.table"),
    /** key:label.type. */
    LABEL_TYPE("label.type"),
    /** key:label.undef. */
    LABEL_UNDEF("label.undef"),
    /** key:label.unique. */
    LABEL_UNIQUE("label.unique"),
    /** key:label.uniqueKey. */
    LABEL_UNIQUE_KEY("label.uniqueKey"),
    /** key:label.unsigned. */
    LABEL_UNSIGNED("label.unsigned"),
    /** key:label.upColumn. */
    LABEL_UP_COLUMN("label.upColumn"),
    /** key:label.user. */
    LABEL_USER("label.user"),
    /** key:label.value. */
    LABEL_VALUE("label.value"),
    /** key:label.warning. */
    LABEL_WARNING("label.warning"),
    /** key:label.x. */
    LABEL_X("label.x"),
    /** key:menuber.file. */
    MENUBER_FILE("menuber.file"),
    /** key:menuber.quit. */
    MENUBER_QUIT("menuber.quit"),
    /** key:menuber.save. */
    MENUBER_SAVE("menuber.save"),
    /** key:none. */
    NONE("none"),
    /** key:palette.node.note. */
    PALETTE_NODE_NOTE("palette.node.note"),
    /** key:palette.node.note.connector. */
    PALETTE_NODE_NOTE_CONNECTOR("palette.node.note.connector"),
    /** key:palette.node.relationship. */
    PALETTE_NODE_RELATIONSHIP("palette.node.relationship"),
    /** key:palette.node.table. */
    PALETTE_NODE_TABLE("palette.node.table"),
    /** key:palette.tools. */
    PALETTE_TOOLS("palette.tools"),
    /** key:preference.diagram. */
    PREFERENCE_DIAGRAM("preference.diagram"),
    /** key:preference.diagram.showNotNull. */
    PREFERENCE_DIAGRAM_SHOW_NOT_NULL("preference.diagram.showNotNull"),
    /** key:preference.layout. */
    PREFERENCE_LAYOUT("preference.layout"),
    /** key:preference.layout.enabledGrid. */
    PREFERENCE_LAYOUT_ENABLED_GRID("preference.layout.enabledGrid"),
    /** key:preference.layout.gridSize. */
    PREFERENCE_LAYOUT_GRID_SIZE("preference.layout.gridSize"),
    /** key:preference.layout.showGrid. */
    PREFERENCE_LAYOUT_SHOW_GRID("preference.layout.showGrid"),
    /** key:preference.layout.snapToGeometry. */
    PREFERENCE_LAYOUT_SNAP_TO_GEOMETRY("preference.layout.snapToGeometry"),
    /** key:preference.validation.check.onSave. */
    PREFERENCE_VALIDATION_CHECK_ON_SAVE("preference.validation.check.onSave"),
    /** key:preference.validation.label.foreignKey.columnSize. */
    PREFERENCE_VALIDATION_LABEL_FOREIGN_KEY_COLUMN_SIZE("preference.validation.label.foreignKey.columnSize"),
    /** key:preference.validation.label.foreignKey.columnType. */
    PREFERENCE_VALIDATION_LABEL_FOREIGN_KEY_COLUMN_TYPE("preference.validation.label.foreignKey.columnType"),
    /** key:preference.validation.label.logicalColumnName.duplicated. */
    PREFERENCE_VALIDATION_LABEL_LOGICAL_COLUMN_NAME_DUPLICATED("preference.validation.label.logicalColumnName.duplicated"),
    /** key:preference.validation.label.logicalColumnName.required. */
    PREFERENCE_VALIDATION_LABEL_LOGICAL_COLUMN_NAME_REQUIRED("preference.validation.label.logicalColumnName.required"),
    /** key:preference.validation.label.logicalTableName.duplicated. */
    PREFERENCE_VALIDATION_LABEL_LOGICAL_TABLE_NAME_DUPLICATED("preference.validation.label.logicalTableName.duplicated"),
    /** key:preference.validation.label.logicalTableName.required. */
    PREFERENCE_VALIDATION_LABEL_LOGICAL_TABLE_NAME_REQUIRED("preference.validation.label.logicalTableName.required"),
    /** key:preference.validation.label.noColumns. */
    PREFERENCE_VALIDATION_LABEL_NO_COLUMNS("preference.validation.label.noColumns"),
    /** key:preference.validation.label.primaryKey. */
    PREFERENCE_VALIDATION_LABEL_PRIMARY_KEY("preference.validation.label.primaryKey"),
    /** key:preference.validation.label.tableName.duplicated. */
    PREFERENCE_VALIDATION_LABEL_TABLE_NAME_DUPLICATED("preference.validation.label.tableName.duplicated"),
    /** key:property.backgroundColor. */
    PROPERTY_BACKGROUND_COLOR("property.backgroundColor"),
    /** key:property.font. */
    PROPERTY_FONT("property.font"),
    /** key:property.text. */
    PROPERTY_TEXT("property.text"),
    /** key:wizard.changedb.dialog.title. */
    WIZARD_CHANGEDB_DIALOG_TITLE("wizard.changedb.dialog.title"),
    /** key:wizard.changedb.page.description. */
    WIZARD_CHANGEDB_PAGE_DESCRIPTION("wizard.changedb.page.description"),
    /** key:wizard.changedb.page.title. */
    WIZARD_CHANGEDB_PAGE_TITLE("wizard.changedb.page.title"),
    /** key:wizard.folderSelect.folder. */
    WIZARD_FOLDER_SELECT_FOLDER("wizard.folderSelect.folder"),
    /** key:wizard.generate.ddl.check.alterTable. */
    WIZARD_GENERATE_DDL_CHECK_ALTER_TABLE("wizard.generate.ddl.check.alterTable"),
    /** key:wizard.generate.ddl.check.comment. */
    WIZARD_GENERATE_DDL_CHECK_COMMENT("wizard.generate.ddl.check.comment"),
    /** key:wizard.generate.ddl.check.drop. */
    WIZARD_GENERATE_DDL_CHECK_DROP("wizard.generate.ddl.check.drop"),
    /** key:wizard.generate.ddl.check.schema. */
    WIZARD_GENERATE_DDL_CHECK_SCHEMA("wizard.generate.ddl.check.schema"),
    /** key:wizard.generate.ddl.dialog.title. */
    WIZARD_GENERATE_DDL_DIALOG_TITLE("wizard.generate.ddl.dialog.title"),
    /** key:wizard.generate.ddl.page.description. */
    WIZARD_GENERATE_DDL_PAGE_DESCRIPTION("wizard.generate.ddl.page.description"),
    /** key:wizard.generate.ddl.page.title. */
    WIZARD_GENERATE_DDL_PAGE_TITLE("wizard.generate.ddl.page.title"),
    /** key:wizard.generate.excel.dialog.title. */
    WIZARD_GENERATE_EXCEL_DIALOG_TITLE("wizard.generate.excel.dialog.title"),
    /** key:wizard.generate.excel.page.description. */
    WIZARD_GENERATE_EXCEL_PAGE_DESCRIPTION("wizard.generate.excel.page.description"),
    /** key:wizard.generate.excel.page.title. */
    WIZARD_GENERATE_EXCEL_PAGE_TITLE("wizard.generate.excel.page.title"),
    /** key:wizard.generate.html.dialog.title. */
    WIZARD_GENERATE_HTML_DIALOG_TITLE("wizard.generate.html.dialog.title"),
    /** key:wizard.generate.html.page.description. */
    WIZARD_GENERATE_HTML_PAGE_DESCRIPTION("wizard.generate.html.page.description"),
    /** key:wizard.generate.html.page.title. */
    WIZARD_GENERATE_HTML_PAGE_TITLE("wizard.generate.html.page.title"),
    /** key:wizard.generate.image.dialog.title. */
    WIZARD_GENERATE_IMAGE_DIALOG_TITLE("wizard.generate.image.dialog.title"),
    /** key:wizard.generate.image.page.description. */
    WIZARD_GENERATE_IMAGE_PAGE_DESCRIPTION("wizard.generate.image.page.description"),
    /** key:wizard.generate.image.page.title. */
    WIZARD_GENERATE_IMAGE_PAGE_TITLE("wizard.generate.image.page.title"),
    /** key:wizard.importFromJdbc.chk.autoConvert. */
    WIZARD_IMPORT_FROM_JDBC_CHK_AUTO_CONVERT("wizard.importFromJdbc.chk.autoConvert"),
    /** key:wizard.importFromJdbc.dialog.title. */
    WIZARD_IMPORT_FROM_JDBC_DIALOG_TITLE("wizard.importFromJdbc.dialog.title"),
    /** key:wizard.importFromJdbc.page1.description. */
    WIZARD_IMPORT_FROM_JDBC_PAGE_1_DESCRIPTION("wizard.importFromJdbc.page1.description"),
    /** key:wizard.importFromJdbc.page1.title. */
    WIZARD_IMPORT_FROM_JDBC_PAGE_1_TITLE("wizard.importFromJdbc.page1.title"),
    /** key:wizard.importFromJdbc.page2.description. */
    WIZARD_IMPORT_FROM_JDBC_PAGE_2_DESCRIPTION("wizard.importFromJdbc.page2.description"),
    /** key:wizard.importFromJdbc.page2.title. */
    WIZARD_IMPORT_FROM_JDBC_PAGE_2_TITLE("wizard.importFromJdbc.page2.title"),
    /** key:wizard.new.erd.dialog.title. */
    WIZARD_NEW_ERD_DIALOG_TITLE("wizard.new.erd.dialog.title"),
    /** key:wizard.new.erd.page.description. */
    WIZARD_NEW_ERD_PAGE_DESCRIPTION("wizard.new.erd.page.description"),
    /** key:wizard.new.erd.page.title. */
    WIZARD_NEW_ERD_PAGE_TITLE("wizard.new.erd.page.title"),
    /** key:error.database.connect. */
    ERROR_DATABASE_CONNECT("error.database.connect"),
    /** key:error.db.import. */
    ERROR_DB_IMPORT("error.db.import"),
    /** key:error.dialog.alert.domain.delete.error. */
    ERROR_DIALOG_ALERT_DOMAIN_DELETE_ERROR("error.dialog.alert.domain.delete.error"),
    /** key:error.dialog.mapping.noColumns. */
    ERROR_DIALOG_MAPPING_NO_COLUMNS("error.dialog.mapping.noColumns"),
    /** key:error.erd.extension. */
    ERROR_ERD_EXTENSION("error.erd.extension"),
    /** key:error.getDatabaseMetadata. */
    ERROR_GET_DATABASE_METADATA("error.getDatabaseMetadata"),
    /** key:error.key.duplicate. */
    ERROR_KEY_DUPLICATE("error.key.duplicate"),
    /** key:error.required. */
    ERROR_REQUIRED("error.required"),
    /** key:error.select.referenceKey. */
    ERROR_SELECT_REFERENCE_KEY("error.select.referenceKey"),
    /** key:error.validation. */
    ERROR_VALIDATION("error.validation"),
    /** key:error.wizard.generate.ddl.error.encoding. */
    ERROR_WIZARD_GENERATE_DDL_ERROR_ENCODING("error.wizard.generate.ddl.error.encoding"),
    /** key:error.wizard.generate.ddl.error.filename. */
    ERROR_WIZARD_GENERATE_DDL_ERROR_FILENAME("error.wizard.generate.ddl.error.filename"),
    /** key:error.wizard.generate.ddl.error.output. */
    ERROR_WIZARD_GENERATE_DDL_ERROR_OUTPUT("error.wizard.generate.ddl.error.output"),
    /** key:info.alreadyExists.overWrite. */
    INFO_ALREADY_EXISTS_OVER_WRITE("info.alreadyExists.overWrite"),
    /** key:info.processAborted. */
    INFO_PROCESS_ABORTED("info.processAborted"),
    /** key:info.saveBeforeExecute. */
    INFO_SAVE_BEFORE_EXECUTE("info.saveBeforeExecute"),
    /** key:info.processing.now. */
    INFO_PROCESSING_NOW("info.processing.now"),
    /** key:validation.error.foreignKey.columnSize. */
    VALIDATION_ERROR_FOREIGN_KEY_COLUMN_SIZE("validation.error.foreignKey.columnSize"),
    /** key:validation.error.foreignKey.columnType. */
    VALIDATION_ERROR_FOREIGN_KEY_COLUMN_TYPE("validation.error.foreignKey.columnType"),
    /** key:validation.error.logicalColumnName.duplicated. */
    VALIDATION_ERROR_LOGICAL_COLUMN_NAME_DUPLICATED("validation.error.logicalColumnName.duplicated"),
    /** key:validation.error.logicalColumnName.required. */
    VALIDATION_ERROR_LOGICAL_COLUMN_NAME_REQUIRED("validation.error.logicalColumnName.required"),
    /** key:validation.error.logicalTableName.duplicated. */
    VALIDATION_ERROR_LOGICAL_TABLE_NAME_DUPLICATED("validation.error.logicalTableName.duplicated"),
    /** key:validation.error.logicalTableName.required. */
    VALIDATION_ERROR_LOGICAL_TABLE_NAME_REQUIRED("validation.error.logicalTableName.required"),
    /** key:validation.error.noColumns. */
    VALIDATION_ERROR_NO_COLUMNS("validation.error.noColumns"),
    /** key:validation.error.noPrimaryKey. */
    VALIDATION_ERROR_NO_PRIMARY_KEY("validation.error.noPrimaryKey"),
    /** key:validation.error.oracle.columnNameLength. */
    VALIDATION_ERROR_ORACLE_COLUMN_NAME_LENGTH("validation.error.oracle.columnNameLength"),
    /** key:validation.error.oracle.indexNameLength. */
    VALIDATION_ERROR_ORACLE_INDEX_NAME_LENGTH("validation.error.oracle.indexNameLength"),
    /** key:validation.error.oracle.tableNameLength. */
    VALIDATION_ERROR_ORACLE_TABLE_NAME_LENGTH("validation.error.oracle.tableNameLength"),
    /** key:validation.error.physicalColumnName.duplicated. */
    VALIDATION_ERROR_PHYSICAL_COLUMN_NAME_DUPLICATED("validation.error.physicalColumnName.duplicated"),
    /** key:validation.error.physicalColumnName.required. */
    VALIDATION_ERROR_PHYSICAL_COLUMN_NAME_REQUIRED("validation.error.physicalColumnName.required"),
    /** key:validation.error.physicalTableName.duplicated. */
    VALIDATION_ERROR_PHYSICAL_TABLE_NAME_DUPLICATED("validation.error.physicalTableName.duplicated"),
    /** key:validation.error.physicalTableName.required. */
    VALIDATION_ERROR_PHYSICAL_TABLE_NAME_REQUIRED("validation.error.physicalTableName.required"),
    /** key:type.binary. */
    TYPE_BINARY("type.binary"),
    /** key:type.bit. */
    TYPE_BIT("type.bit"),
    /** key:type.boolean. */
    TYPE_BOOLEAN("type.boolean"),
    /** key:type.char. */
    TYPE_CHAR("type.char"),
    /** key:type.date. */
    TYPE_DATE("type.date"),
    /** key:type.datetime. */
    TYPE_DATETIME("type.datetime"),
    /** key:type.enum. */
    TYPE_ENUM("type.enum"),
    /** key:type.guid. */
    TYPE_GUID("type.guid"),
    /** key:type.integer. */
    TYPE_INTEGER("type.integer"),
    /** key:type.interval. */
    TYPE_INTERVAL("type.interval"),
    /** key:type.macaddress. */
    TYPE_MACADDRESS("type.macaddress"),
    /** key:type.money. */
    TYPE_MONEY("type.money"),
    /** key:type.nchar. */
    TYPE_NCHAR("type.nchar"),
    /** key:type.networkaddress. */
    TYPE_NETWORKADDRESS("type.networkaddress"),
    /** key:type.nstring. */
    TYPE_NSTRING("type.nstring"),
    /** key:type.ntext. */
    TYPE_NTEXT("type.ntext"),
    /** key:type.numeric. */
    TYPE_NUMERIC("type.numeric"),
    /** key:type.object. */
    TYPE_OBJECT("type.object"),
    /** key:type.other. */
    TYPE_OTHER("type.other"),
    /** key:type.real. */
    TYPE_REAL("type.real"),
    /** key:type.serial. */
    TYPE_SERIAL("type.serial"),
    /** key:type.string. */
    TYPE_STRING("type.string"),
    /** key:type.text. */
    TYPE_TEXT("type.text"),
    /** key:type.time. */
    TYPE_TIME("type.time"),
    /** key:type.timestamp. */
    TYPE_TIMESTAMP("type.timestamp"),
    /** key:type.variant. */
    TYPE_VARIANT("type.variant"),
    /** key:type.xml. */
    TYPE_XML("type.xml"),
    /** key:type.year. */
    TYPE_YEAR("type.year"),
    ;

    private static final ResourceBundle resource = ResourceBundle.getBundle("io.github.erde.messages");

    private String propKey;

    private Resource(String propKey) {
        this.propKey = propKey;
    }

    public String getValue() {
        try {
            return resource.getString(propKey);
        } catch (MissingResourceException e) {
            return propKey;
        }
    }

    public String getValue(String defaultValue) {
        try {
            String result = resource.getString(propKey);
            return StringUtils.isEmpty(result) ? defaultValue : result;
        } catch (MissingResourceException e) {
            return propKey;
        }
    }

    public String createMessage(String... args) {
        String message = resource.getString(propKey);
        for (int i = 0; i < args.length; i++) {
            message = message.replaceAll(String.format("\\{%d\\}", i), args[i]);
        }
        return message;
    }

    public String getPropKey() {
        return propKey;
    }

    public static Resource toResource(String propKey) {
        return Arrays.stream(Resource.values())
                .filter(key -> key.getPropKey().equals(propKey))
                .findFirst()
                .orElseThrow();
    }
}
