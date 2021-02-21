package io.github.erde;

import java.util.Arrays;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

/**
 * Message enum.
 *
 * @author parapata
 * @since 1.0.8
 */
public enum Resource {

    /** . */
    WIZARD_NEW_ERD_TITLE("wizard.new.erd.title"),
    /** . */
    WIZARD_NEW_ERD_PRODUCT("wizard.new.erd.product"),
    /** . */
    WIZARD_NEW_ERD_SCHEMA("wizard.new.erd.schema"),
    /** . */
    WIZARD_NEW_ERD_MESSAGE("wizard.new.erd.message"),
    /** . */
    WIZARD_NEW_IMPORT_TITLE("wizard.new.import.title"),
    /** . */
    WIZARD_NEW_IMPORT_MESSAGE("wizard.new.import.message"),
    /** . */
    WIZARD_NEW_IMPORT_DRIVER("wizard.new.import.driver"),
    /** . */
    WIZARD_NEW_IMPORT_URI("wizard.new.import.uri"),
    /** . */
    WIZARD_NEW_IMPORT_USER("wizard.new.import.user"),
    /** . */
    WIZARD_NEW_IMPORT_PASS("wizard.new.import.pass"),
    /** . */
    WIZARD_NEW_IMPORT_SCHEMA("wizard.new.import.schema"),
    /** . */
    WIZARD_NEW_IMPORT_CATALOG("wizard.new.import.catalog"),
    /** . */
    WIZARD_NEW_IMPORT_VIEW("wizard.new.import.view"),
    /** . */
    WIZARD_NEW_IMPORT_LOAD_TABLES("wizard.new.import.loadTables"),
    /** . */
    WIZARD_NEW_IMPORT_TABLES("wizard.new.import.tables"),
    /** . */
    WIZARD_NEW_IMPORT_FILTER("wizard.new.import.filter"),
    /** . */
    WIZARD_NEW_IMPORT_JARFILE("wizard.new.import.jarFile"),
    /** . */
    WIZARD_NEW_IMPORT_AUTO_CONVERT("wizard.new.import.autoConvert"),
    /** . */
    WIZARD_GENERATE_FOLDER("wizard.generate.folder"),
    /** . */
    WIZARD_GENERATE_BROWSE_TITLE("wizard.generate.browse.title"),
    /** . */
    WIZARD_GENERATE_BROWSE_MESSAGE("wizard.generate.browse.message"),
    /** . */
    WIZARD_GENERATE_DDL_TITLE("wizard.generate.ddl.title"),
    /** . */
    WIZARD_GENERATE_DDL_DESCRIPTION("wizard.generate.ddl.description"),
    /** . */
    WIZARD_GENERATE_DDL_FILE_NAME("wizard.generate.ddl.filename"),
    /** . */
    WIZARD_GENERATE_DDL_ENCODING("wizard.generate.ddl.encoding"),
    /** . */
    WIZARD_GENERATE_DDL_SCHEMA("wizard.generate.ddl.schema"),
    /** . */
    WIZARD_GENERATE_DDL_DROP_TABLE("wizard.generate.ddl.dropTable"),
    /** . */
    WIZARD_GENERATE_DDL_ALTER_TABLE("wizard.generate.ddl.alterTable"),
    /** . */
    WIZARD_GENERATE_DDL_COMMENT("wizard.generate.ddl.comment"),
    /** . */
    WIZARD_GENERATE_DDL_CONFIRM_MESSAGE("wizard.generate.ddl.confirm.message"),
    /** . */
    WIZARD_GENERATE_DDL_ERR_PATH("wizard.generate.ddl.error.path"),
    /** . */
    WIZARD_GENERATE_DDL_ERR_FILE_NAME("wizard.generate.ddl.error.filename"),
    /** . */
    WIZARD_GENERATE_DDL_ERR_ENCODING("wizard.generate.ddl.error.encoding"),
    /** . */
    WIZARD_GENERATE_DDL_ERR_OUTPUT("wizard.generate.ddl.error.output"),
    /** . */
    WIZARD_IMPORT_FROM_DIAGRAM_TITLE("wizard.importFromDiagram.title"),
    /** . */
    WIZARD_IMPORT_FROM_DIAGRAM_MESSAGE("wizard.importFromDiagram.message"),
    /** . */
    WIZARD_IMPORT_FROM_DIAGRAM_ERD_FILE("wizard.importFromDiagram.erdFile"),
    /** . */
    WIZARD_IMPORT_FROM_DIAGRAM_ERR_EXIST_TABLE("wizard.importFromDiagram.error.existTable"),
    /** . */
    WIZARD_CHANGE_DB_TITLE("wizard.changedb.title"),
    /** . */
    WIZARD_CHANGE_DB_DESCRIPTION("wizard.changedb.description"),
    /** . */
    WIZARD_CHANGE_DB_DATABASE_TYPE("wizard.changedb.databaseType"),
    /** . */
    PALETTE_TOOLS("palette.tools"),
    /** . */
    PALETTE_NODE_TABLE("palette.node.table"),
    /** . */
    PALETTE_NODE_RELATIONSHIP("palette.node.relationship"),
    /** . */
    PALETTE_NODE_NOTE("palette.node.note"),
    /** . */
    PALETTE_NODE_NOTE_CONNECTOR("palette.node.note.connector"),
    /** . */
    TYPE_INTEGER("type.integer"),
    /** . */
    TYPE_REAL("type.real"),
    /** . */
    TYPE_NUMERIC("type.numeric"),
    /** . */
    TYPE_BINARY("type.binary"),
    /** . */
    TYPE_STRING("type.string"),
    /** . */
    TYPE_CHAR("type.char"),
    /** . */
    TYPE_DATE("type.date"),
    /** . */
    TYPE_DATETIME("type.datetime"),
    /** . */
    TYPE_TIME("type.time"),
    /** . */
    TYPE_BOOLEAN("type.boolean"),
    /** . */
    TYPE_OBJECT("type.object"),
    /** . */
    TYPE_OTHER("type.other"),
    /** . */
    TYPE_BIT("type.bit"),
    /** . */
    TYPE_YEAR("type.year"),
    /** . */
    TYPE_SERIAL("type.serial"),
    /** . */
    TYPE_XML("type.xml"),
    /** . */
    TYPE_INTERVAL("type.interval"),
    /** . */
    TYPE_TIMESTAMP("type.timestamp"),
    /** . */
    TYPE_VARIANT("type.variant"),
    /** . */
    TYPE_TEXT("type.text"),
    /** . */
    TYPE_GUID("type.guid"),
    /** . */
    TYPE_MONEY("type.money"),
    /** . */
    TYPE_NETWORK_ADDRESS("type.networkaddress"),
    /** . */
    TYPE_MAC_ADDRESS("type.macaddress"),
    /** . */
    TYPE_NCHAR("type.nchar"),
    /** . */
    TYPE_NTEXT("type.ntext"),
    /** . */
    TYPE_NSTRING("type.nstring"),
    /** . */
    ACTION_TOGGLE_MODE("action.toggleMode"),
    /** . */
    ACTION_COPY_AS_IMAGE("action.copyAsImage"),
    /** . */
    ACTION_EXPORT("action.export"),
    /** . */
    ACTION_PRINT("action.print"),
    /** . */
    ACTION_AUTO_LAYOUT("action.autoLayout"),
    /** . */
    ACTION_EDIT_DOMAIN("action.editDomain"),
    /** . */
    ACTION_VALI_DATION("action.validation"),
    /** . */
    ACTION_VALI_DATION_DELETE_MARKERS("action.validation.deleteMarkers"),
    /** . */
    ACTION_VALI_DATION_EXECUTE_VALIDATION("action.validation.executeValidation"),
    /** . */
    ACTION_IMPORT("action.import"),
    /** . */
    ACTION_IMPORT_FROM_DB("action.importFromDB"),
    /** . */
    ACTION_IMPORT_FROM_DIAGRAM("action.importFromDiagram"),
    /** . */
    ACTION_REFRESH_LINKED_TABLES("action.refreshLinkedTables"),
    /** . */
    ACTION_REFRESH_LINKED_TABLES_NO_LINKED_TABLE("action.refreshLinkedTables.noLinkedTable"),
    /** . */
    ACTION_COPY("action.copy"),
    /** . */
    ACTION_PASTE("action.paste"),
    /** . */
    ACTION_SELECTED_TABLES_DDL("action.selectedTablesDDL"),
    /** . */
    ACTION_CHANGE_DB_TYPE("action.changeDatabaseType"),
    /** . */
    ACTION_TO_UPPERCASE("action.toUppercase"),
    /** . */
    ACTION_TO_LOWERCASE("action.toLowercase"),
    /** . */
    ACTION_TO_LOWERCASE_CONFIRM("action.toLowercase.confirm"),
    /** . */
    ACTION_LOGICAL_TO_PHYSICAL("action.logical2physical"),
    /** . */
    ACTION_LOGICAL_TO_PHYSICAL_CONFIRM("action.logical2physical.confirm"),
    /** . */
    ACTION_PHYSICAL_TO_LOGICAL("action.physical2logical"),
    /** . */
    ACTION_PHYSICAL_TO_LOGICAL_CONFIRM("action.physical2logical.confirm"),
    /** . */
    ACTION_TOGGLE_NOTATION("action.toggleNotation"),
    /** . */
    ACTION_QUICK_OUTLINE("action.quickOutline"),
    /** . */
    PROPERTY_PHYSICAL_TABLE_NAME("property.physicalTableName"),
    /** . */
    PROPERTY_LOGICAL_TABLE_NAME("property.logicalTableName"),
    /** . */
    PROPERTY_FOREIGNKEY_NAME("property.foreignKeyName"),
    /** . */
    PROPERTY_SCHEMA("property.schema"),
    /** . */
    PROPERTY_LINKED_PATH("property.linkedPath"),
    /** . */
    PROPERTY_BACKGROUND_COLOR("property.backgroundColor"),
    /** . */
    PROPERTY_TEXT("property.text"),
    /** . */
    PROPERTY_FONT("property.font"),
    /** . */
    DIALOG_CONFIRM_TITLE("dialog.confirm.title"),
    /** . */
    DIALOG_ERR_SYSTEM_TITLE("dialog.error.system.title"),
    /** . */
    DIALOG_INFO_TITLE("dialog.info.title"),
    /** . */
    DIALOG_ALERT_TITLE("dialog.alert.title"),
    /** . */
    DIALOG_ALERT_DOMAIN_DELETE_ERROR("dialog.alert.domain.delete.error"),
    /** . */
    DIALOG_MAPPING_TITLE("dialog.mapping.title"),
    /** . */
    DIALOG_MAPPING_NAME("dialog.mapping.name"),
    /** . */
    DIALOG_MAPPING_REFERRED("dialog.mapping.referred"),
    /** . */
    DIALOG_MAPPING_ON_UPDATE("dialog.mapping.onUpdate"),
    /** . */
    DIALOG_MAPPING_ON_DELETE("dialog.mapping.onDelete"),
    /** . */
    DIALOG_MAPPING_MAPPING("dialog.mapping.mapping"),
    /** . */
    DIALOG_MAPPING_NO_COLUMNS("dialog.mapping.noColumns"),
    /** . */
    DIALOG_MAPPING_PRIMARIY_KEY("dialog.mapping.primariyKey"),
    /** . */
    DIALOG_MAPPING_OPTION("dialog.mapping.option"),
    /** . */
    DIALOG_MAPPING_MULTIPLE("dialog.mapping.multiple"),
    /** . */
    DIALOG_TABLE_TITLE("dialog.table.title"),
    /** . */
    DIALOG_TABLE_TABLE_PYHGICAL_NAME("dialog.table.tablePyhgicalName"),
    /** . */
    DIALOG_TABLE_TABLE_LOGICAL_NAME("dialog.table.tableLogicalName"),
    /** . */
    DIALOG_TABLE_COLUMN_PYHGICAL_NAME("dialog.table.columnPyhgicalName"),
    /** . */
    DIALOG_TABLE_COLUMN_LOGICAL_NAME("dialog.table.columnLogicalName"),
    /** . */
    DIALOG_TABLE_COLUMN_TYPE("dialog.table.columnType"),
    /** . */
    DIALOG_TABLE_COLUMN_PK("dialog.table.columnPK"),
    /** . */
    DIALOG_TABLE_COLUMN_FK("dialog.table.columnFK"),
    /** . */
    DIALOG_TABLE_COLUMN_NOT_NULL("dialog.table.columnNotNull"),
    /** . */
    DIALOG_TABLE_COLUMN_UNIQUE("dialog.table.columnUnique"),
    /** . */
    DIALOG_TABLE_ADD_COLUMN("dialog.table.addColumn"),
    /** . */
    DIALOG_TABLE_REMOVE_COLUMN("dialog.table.removeColumn"),
    /** . */
    DIALOG_TABLE_UP_COLUMN("dialog.table.upColumn"),
    /** . */
    DIALOG_TABLE_DOWN_COLUMN("dialog.table.downColumn"),
    /** . */
    DIALOG_TABLE_EDIT_COLUMN("dialog.table.editColumn"),
    /** . */
    DIALOG_TABLE_EDIT_COLUMN_NAME("dialog.table.editColumn.name"),
    /** . */
    DIALOG_TABLE_EDIT_COLUMN_LOGICAL_NAME("dialog.table.editColumn.logicalName"),
    /** . */
    DIALOG_TABLE_EDIT_COLUMN_TYPE("dialog.table.editColumn.type"),
    /** . */
    DIALOG_TABLE_EDIT_COLUMN_SIZE("dialog.table.editColumn.size"),
    /** . */
    DIALOG_TABLE_EDIT_COLUMN_DECIMAL("dialog.table.editColumn.decimal"),
    /** . */
    DIALOG_TABLE_EDIT_COLUMN_UNSIGNED("dialog.table.editColumn.unsigned"),
    /** . */
    DIALOG_TABLE_EDIT_COLUMN_ENUM("dialog.table.editColumn.enum"),
    /** . */
    DIALOG_TABLE_EDIT_COLUMN_PK("dialog.table.editColumn.PK"),
    /** . */
    DIALOG_TABLE_EDIT_COLUMN_UNIQUE_KEY("dialog.table.editColumn.UniqueKey"),
    /** . */
    DIALOG_TABLE_EDIT_COLUMN_NOT_NULL("dialog.table.editColumn.notNull"),
    /** . */
    DIALOG_TABLE_EDIT_COLUMN_AUTO_INCREMENT("dialog.table.editColumn.autoIncrement"),
    /** . */
    DIALOG_TABLE_EDIT_COLUMN_DEFAULT_VALUE("dialog.table.editColumn.defaultValue"),
    /** . */
    DIALOG_TABLE_DESCRIPTION("dialog.table.description"),
    /** . */
    DIALOG_TABLE_ADD_INDEX("dialog.table.addIndex"),
    /** . */
    DIALOG_TABLE_REMOVE_INDEX("dialog.table.removeIndex"),
    /** . */
    DIALOG_TABLE_EDIT_INDEX_INDEX_NAME("dialog.table.editIndex.indexName"),
    /** . */
    DIALOG_TABLE_EDIT_INDEX_INDEX_TYPE("dialog.table.editIndex.indexType"),
    /** . */
    DIALOG_TABLE_EDIT_INDEX_INDEX_COLUMNS("dialog.table.editIndex.indexColumns"),
    /** . */
    DIALOG_TABLE_INSERT_SQL("dialog.table.insertSql"),
    /** . */
    DIALOG_TABLE_INSERT_NOT_NULL_SQL("dialog.table.insertNotNullSql"),
    /** . */
    DIALOG_COLUMN_SELECT_TITLE("dialog.columnSelect.title"),
    /** . */
    DIALOG_DOMAIN_TITLE("dialog.domain.title"),
    /** . */
    DIALOG_DOMAIN_ADD_DOMAIN("dialog.domain.addDomain"),
    /** . */
    DIALOG_DOMAIN_REMOVE_DOMAIN("dialog.domain.removeDomain"),
    /** . */
    DIALOG_DOMAIN_EDIT_DOMAIN_NAME("dialog.domain.editDomain.name"),
    /** . */
    DIALOG_DOMAIN_EDIT_DOMAIN_TYPE("dialog.domain.editDomain.type"),
    /** . */
    DIALOG_DOMAIN_EDIT_DOMAIN_SIZE("dialog.domain.editDomain.size"),
    /** . */
    DIALOG_DOMAIN_EDIT_DOMAIN_DECIMAL("dialog.domain.editDomain.decimal"),
    /** . */
    DIALOG_DOMAIN_EDIT_DOMAIN_UNSIGNED("dialog.domain.editDomain.unsigned"),
    /** . */
    DIALOG_DOMAIN_NAME("dialog.domain.name"),
    /** . */
    DIALOG_DOMAIN_TYPE("dialog.domain.type"),
    /** . */
    DIALOG_DOMAIN_UNSIGNED("dialog.domain.unsigned"),
    /** . */
    DIALOG_DICTIONARY_TITLE("dialog.dictionary.title"),
    /** . */
    PREF_VALIDATION("preference.validation"),
    /** . */
    PREF_VALIDATION_TABLE_NAME_REQUIRED("preference.validation.tableName.required"),
    /** . */
    PREF_VALIDATION_TABLE_NAME_DUPLICATED("preference.validation.tableName.duplicated"),
    /** . */
    PREF_VALIDATION_LOGICAL_TABLE_NAME_REQUIRED("preference.validation.logicalTableName.required"),
    /** . */
    PREF_VALIDATION_LOGICAL_TABLE_NAME_DUPLICATED("preference.validation.logicalTableName.duplicated"),
    /** . */
    PREF_VALIDATION_COLUM_NNAME_REQUIRED("preference.validation.columnName.required"),
    /** . */
    PREF_VALIDATION_COLUM_NNAME_DUPLICATED("preference.validation.columnName.duplicated"),
    /** . */
    PREF_VALIDATION_LOGICAL_COLUMN_NAME_REQUIRED("preference.validation.logicalColumnName.required"),
    /** . */
    PREF_VALIDATION_LOGICAL_COLUMN_NAME_DUPLICATED("preference.validation.logicalColumnName.duplicated"),
    /** . */
    PREF_VALIDATION_NO_COLUMNS("preference.validation.noColumns"),
    /** . */
    PREF_VALIDATION_PRIMARY_KEY("preference.validation.primaryKey"),
    /** . */
    PREF_VALIDATION_FOREIGN_KEY_COLUMN_TYPE("preference.validation.foreignKey.columnType"),
    /** . */
    PREF_VALIDATION_FOREIGN_KEY_COLUMN_SIZE("preference.validation.foreignKey.columnSize"),
    /** . */
    PREF_VALIDATION_LEVEL_ERR("preference.validation.level.error"),
    /** . */
    PREF_VALIDATION_LEVEL_WARN("preference.validation.level.warning"),
    /** . */
    PREF_VALIDATION_LEVEL_IGNORE("preference.validation.level.ignore"),
    /** . */
    PREF_LAYOUT("preference.layout"),
    /** . */
    PREF_LAYOUT_SHOW_GRID("preference.layout.showGrid"),
    /** . */
    PREF_LAYOUT_ENABLED_GRID("preference.layout.enabledGrid"),
    /** . */
    PREF_LAYOUT_GRID_SIZE("preference.layout.gridSize"),
    /** . */
    PREF_LAYOUT_SNAP_TO_GEOMETRY("preference.layout.snapToGeometry"),
    /** . */
    PREF_DIAGRAM("preference.diagram"),
    /** . */
    PREF_DIAGRAM_SHOW_NOT_NULL("preference.diagram.showNotNull"),
    /** . */
    ERR_ERD_EXTENSION("error.erd.extension"),
    /** . */
    ERR_EDIT_LINKED_TABLE("error.edit.linkedTable"),
    /** . */
    ERR_REQUIRED("error.required"),
    /** . */
    ERR_DB_IMPORT("error.db.import"),
    /** . */
    ERR_VALIDATION("error.validation"),
    /** . */
    ERR_KEY_DUPLICATE("error.key.duplicate"),
    /** . */
    INFO_CANCEL("info.cancel"),
    /** . */
    LABEL_UNDEF("label.undef"),
    /** . */
    LABEL_ATTRIBUTE("label.attribute"),
    /** . */
    LABEL_TABLE("label.table"),
    /** . */
    LABEL_DESCRIPTION("label.description"),
    /** . */
    LABEL_COLUMN("label.column"),
    /** . */
    LABEL_DOMAIN("label.domain"),
    /** . */
    LABEL_INDEX("label.index"),
    /** . */
    LABEL_SQL("label.sql"),
    /** . */
    LABEL_NOT_NULL("label.notNull"),
    /** . */
    LABEL_O("label.o"),
    /** . */
    LABEL_X("label.x"),
    /** . */
    LABEL_LOGICAL_NAME("label.logicalName"),
    /** . */
    LABEL_PHYSICAL_NAME("label.physicalName"),
    /** . */
    LABEL_PARTIAL_MATCH("label.partialMatch"),
    /** . */
    LABEL_AUTO_INCREMENT("label.autoIncrement"),
    /** . */
    BUTTON_BROWSE("button.browse"),
    /** . */
    BUTTON_BROWSE_FILE_SYSTEM("button.browseFileSystem"),
    /** . */
    BUTTON_BROWSE_WORKSPACE("button.browseWorkspace"),
    /** . */
    BUTTON_ADD("button.add"),
    /** . */
    BUTTON_EDIT("button.edit"),
    /** . */
    BUTTON_DELETE("button.delete"),
    /** . */
    DDL_TABLE_NAME("ddl.tableName"),
    /** . */
    DDL_DESCRIPTION("ddl.description"),
    /** . */
    MESSAGE_SAVE_BEFORE_EXECUTE("message.saveBeforeExecute"),
    /** . */
    VALIDATION_ERR_PHYSICAL_TABLE_NAME_REQUIRED("validation.error.physicalTableName.required"),
    /** . */
    VALIDATION_ERR_PHYSICAL_TABLE_NAME_DUPLICATED("validation.error.physicalTableName.duplicated"),
    /** . */
    VALIDATION_ERR_LOGICAL_TABLE_NAME_REQUIRED("validation.error.logicalTableName.required"),
    /** . */
    VALIDATION_ERR_LOGICAL_TABLE_NAME_DUPLICATED("validation.error.logicalTableName.duplicated"),
    /** . */
    VALIDATION_ERR_NO_COLUMNS("validation.error.noColumns"),
    /** . */
    VALIDATION_ERR_PHYSICAL_COLUMN_NAME_REQUIRED("validation.error.physicalColumnName.required"),
    /** . */
    VALIDATION_ERR_PHYSICAL_COLUMN_NAME_DUPLICATED("validation.error.physicalColumnName.duplicated"),
    /** . */
    VALIDATION_ERR_LOGICAL_COLUMN_NAME_REQUIRED("validation.error.logicalColumnName.required"),
    /** . */
    VALIDATION_ERR_LOGICAL_COLUMN_NAME_DUPLICATED("validation.error.logicalColumnName.duplicated"),
    /** . */
    VALIDATION_ERR_NO_PRIMARY_KEY("validation.error.noPrimaryKey"),
    /** . */
    VALIDATION_ERR_FOREIGN_KEY_COLUMN_TYPE("validation.error.foreignKey.columnType"),
    /** . */
    VALIDATION_ERR_FOREIGN_KEY_COLUMN_SIZE("validation.error.foreignKey.columnSize"),
    /** . */
    VALIDATION_ERR_ORACLE_TABLE_NAME_LENGTH("validation.error.oracle.tableNameLength"),
    /** . */
    VALIDATION_ERR_ORACLE_COLUMN_NAME_LENGTH("validation.error.oracle.columnNameLength"),
    /** . */
    VALIDATION_ERR_ORACLE_INDEX_NAME_LENGTH("validation.error.oracle.indexNameLength"),
    /** . */
    MESSAGE_NONE("");

    private static final ResourceBundle resource = ResourceBundle.getBundle("io.github.erde.messages");

    private String key;

    private Resource(String key) {
        this.key = key;
    }

    public String getValue() {
        try {
            return resource.getString(key);
        } catch (MissingResourceException e) {
            return key;
        }
    }

    public String getValue(String defaultValue) {
        try {
            String result = resource.getString(key);
            return StringUtils.isEmpty(result) ? defaultValue : result;
        } catch (MissingResourceException e) {
            return key;
        }
    }

    public String createMessage(String... args) {
        String message = resource.getString(key);
        for (int i = 0; i < args.length; i++) {
            message = message.replaceAll(String.format("\\{%d\\}", i), args[i]);
        }
        return message;
    }

    private String getPropKey() {
        return key;
    }

    public static Resource toResource(String propKey) {
        return Arrays.stream(Resource.values())
                .filter(key -> key.getPropKey().equals(propKey))
                .findFirst()
                .orElseThrow();
    }
}
