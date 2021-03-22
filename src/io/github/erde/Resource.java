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
    /** key:action.importFromDiagram. */
    ACTION_IMPORT_FROM_DIAGRAM("action.importFromDiagram"),
    /** key:action.logical2physical. */
    ACTION_LOGICAL_2_PHYSICAL("action.logical2physical"),
    /** key:action.logical2physical.confirm. */
    ACTION_LOGICAL_2_PHYSICAL_CONFIRM("action.logical2physical.confirm"),
    /** key:action.paste. */
    ACTION_PASTE("action.paste"),
    /** key:action.physical2logical. */
    ACTION_PHYSICAL_2_LOGICAL("action.physical2logical"),
    /** key:action.physical2logical.confirm. */
    ACTION_PHYSICAL_2_LOGICAL_CONFIRM("action.physical2logical.confirm"),
    /** key:action.print. */
    ACTION_PRINT("action.print"),
    /** key:action.quickOutline. */
    ACTION_QUICK_OUTLINE("action.quickOutline"),
    /** key:action.refreshLinkedTables. */
    ACTION_REFRESH_LINKED_TABLES("action.refreshLinkedTables"),
    /** key:action.refreshLinkedTables.noLinkedTable. */
    ACTION_REFRESH_LINKED_TABLES_NO_LINKED_TABLE("action.refreshLinkedTables.noLinkedTable"),
    /** key:action.selectedTablesDDL. */
    ACTION_SELECTED_TABLES_DDL("action.selectedTablesDDL"),
    /** key:action.toggleMode. */
    ACTION_TOGGLE_MODE("action.toggleMode"),
    /** key:action.toggleNotation. */
    ACTION_TOGGLE_NOTATION("action.toggleNotation"),
    /** key:action.toLowercase. */
    ACTION_TO_LOWERCASE("action.toLowercase"),
    /** key:action.toLowercase.confirm. */
    ACTION_TO_LOWERCASE_CONFIRM("action.toLowercase.confirm"),
    /** key:action.toUppercase. */
    ACTION_TO_UPPERCASE("action.toUppercase"),
    /** key:action.validation. */
    ACTION_VALIDATION("action.validation"),
    /** key:action.validation.deleteMarkers. */
    ACTION_VALIDATION_DELETE_MARKERS("action.validation.deleteMarkers"),
    /** key:action.validation.executeValidation. */
    ACTION_VALIDATION_EXECUTE_VALIDATION("action.validation.executeValidation"),
    /** key:button.add. */
    BUTTON_ADD("button.add"),
    /** key:button.browse. */
    BUTTON_BROWSE("button.browse"),
    /** key:button.browseFileSystem. */
    BUTTON_BROWSE_FILE_SYSTEM("button.browseFileSystem"),
    /** key:button.browseWorkspace. */
    BUTTON_BROWSE_WORKSPACE("button.browseWorkspace"),
    /** key:button.delete. */
    BUTTON_DELETE("button.delete"),
    /** key:button.edit. */
    BUTTON_EDIT("button.edit"),
    /** key:ddl.description. */
    DDL_DESCRIPTION("ddl.description"),
    /** key:ddl.tableName. */
    DDL_TABLE_NAME("ddl.tableName"),
    /** key:dialog.alert.domain.delete.error. */
    DIALOG_ALERT_DOMAIN_DELETE_ERROR("dialog.alert.domain.delete.error"),
    /** key:dialog.alert.title. */
    DIALOG_ALERT_TITLE("dialog.alert.title"),
    /** key:dialog.columnSelect.title. */
    DIALOG_COLUMN_SELECT_TITLE("dialog.columnSelect.title"),
    /** key:dialog.confirm.title. */
    DIALOG_CONFIRM_TITLE("dialog.confirm.title"),
    /** key:dialog.dictionary.title. */
    DIALOG_DICTIONARY_TITLE("dialog.dictionary.title"),
    /** key:dialog.domain.addDomain. */
    DIALOG_DOMAIN_ADD_DOMAIN("dialog.domain.addDomain"),
    /** key:dialog.domain.editDomain.decimal. */
    DIALOG_DOMAIN_EDIT_DOMAIN_DECIMAL("dialog.domain.editDomain.decimal"),
    /** key:dialog.domain.editDomain.name. */
    DIALOG_DOMAIN_EDIT_DOMAIN_NAME("dialog.domain.editDomain.name"),
    /** key:dialog.domain.editDomain.size. */
    DIALOG_DOMAIN_EDIT_DOMAIN_SIZE("dialog.domain.editDomain.size"),
    /** key:dialog.domain.editDomain.type. */
    DIALOG_DOMAIN_EDIT_DOMAIN_TYPE("dialog.domain.editDomain.type"),
    /** key:dialog.domain.editDomain.unsigned. */
    DIALOG_DOMAIN_EDIT_DOMAIN_UNSIGNED("dialog.domain.editDomain.unsigned"),
    /** key:dialog.domain.name. */
    DIALOG_DOMAIN_NAME("dialog.domain.name"),
    /** key:dialog.domain.removeDomain. */
    DIALOG_DOMAIN_REMOVE_DOMAIN("dialog.domain.removeDomain"),
    /** key:dialog.domain.title. */
    DIALOG_DOMAIN_TITLE("dialog.domain.title"),
    /** key:dialog.domain.type. */
    DIALOG_DOMAIN_TYPE("dialog.domain.type"),
    /** key:dialog.domain.unsigned. */
    DIALOG_DOMAIN_UNSIGNED("dialog.domain.unsigned"),
    /** key:dialog.error.system.title. */
    DIALOG_ERROR_SYSTEM_TITLE("dialog.error.system.title"),
    /** key:dialog.info.title. */
    DIALOG_INFO_TITLE("dialog.info.title"),
    /** key:dialog.mapping.mapping. */
    DIALOG_MAPPING_MAPPING("dialog.mapping.mapping"),
    /** key:dialog.mapping.multiple. */
    DIALOG_MAPPING_MULTIPLE("dialog.mapping.multiple"),
    /** key:dialog.mapping.name. */
    DIALOG_MAPPING_NAME("dialog.mapping.name"),
    /** key:dialog.mapping.noColumns. */
    DIALOG_MAPPING_NO_COLUMNS("dialog.mapping.noColumns"),
    /** key:dialog.mapping.onDelete. */
    DIALOG_MAPPING_ON_DELETE("dialog.mapping.onDelete"),
    /** key:dialog.mapping.onUpdate. */
    DIALOG_MAPPING_ON_UPDATE("dialog.mapping.onUpdate"),
    /** key:dialog.mapping.option. */
    DIALOG_MAPPING_OPTION("dialog.mapping.option"),
    /** key:dialog.mapping.primariyKey. */
    DIALOG_MAPPING_PRIMARIY_KEY("dialog.mapping.primariyKey"),
    /** key:dialog.mapping.referred. */
    DIALOG_MAPPING_REFERRED("dialog.mapping.referred"),
    /** key:dialog.mapping.title. */
    DIALOG_MAPPING_TITLE("dialog.mapping.title"),
    /** key:dialog.table.addColumn. */
    DIALOG_TABLE_ADD_COLUMN("dialog.table.addColumn"),
    /** key:dialog.table.addIndex. */
    DIALOG_TABLE_ADD_INDEX("dialog.table.addIndex"),
    /** key:dialog.table.columnFK. */
    DIALOG_TABLE_COLUMN_FK("dialog.table.columnFK"),
    /** key:dialog.table.columnLogicalName. */
    DIALOG_TABLE_COLUMN_LOGICAL_NAME("dialog.table.columnLogicalName"),
    /** key:dialog.table.columnNotNull. */
    DIALOG_TABLE_COLUMN_NOT_NULL("dialog.table.columnNotNull"),
    /** key:dialog.table.columnPK. */
    DIALOG_TABLE_COLUMN_PK("dialog.table.columnPK"),
    /** key:dialog.table.columnPyhgicalName. */
    DIALOG_TABLE_COLUMN_PYHGICAL_NAME("dialog.table.columnPyhgicalName"),
    /** key:dialog.table.columnType. */
    DIALOG_TABLE_COLUMN_TYPE("dialog.table.columnType"),
    /** key:dialog.table.columnUnique. */
    DIALOG_TABLE_COLUMN_UNIQUE("dialog.table.columnUnique"),
    /** key:dialog.table.description. */
    DIALOG_TABLE_DESCRIPTION("dialog.table.description"),
    /** key:dialog.table.downColumn. */
    DIALOG_TABLE_DOWN_COLUMN("dialog.table.downColumn"),
    /** key:dialog.table.editColumn. */
    DIALOG_TABLE_EDIT_COLUMN("dialog.table.editColumn"),
    /** key:dialog.table.editColumn.autoIncrement. */
    DIALOG_TABLE_EDIT_COLUMN_AUTO_INCREMENT("dialog.table.editColumn.autoIncrement"),
    /** key:dialog.table.editColumn.decimal. */
    DIALOG_TABLE_EDIT_COLUMN_DECIMAL("dialog.table.editColumn.decimal"),
    /** key:dialog.table.editColumn.defaultValue. */
    DIALOG_TABLE_EDIT_COLUMN_DEFAULT_VALUE("dialog.table.editColumn.defaultValue"),
    /** key:dialog.table.editColumn.enum. */
    DIALOG_TABLE_EDIT_COLUMN_ENUM("dialog.table.editColumn.enum"),
    /** key:dialog.table.editColumn.logicalName. */
    DIALOG_TABLE_EDIT_COLUMN_LOGICAL_NAME("dialog.table.editColumn.logicalName"),
    /** key:dialog.table.editColumn.name. */
    DIALOG_TABLE_EDIT_COLUMN_NAME("dialog.table.editColumn.name"),
    /** key:dialog.table.editColumn.notNull. */
    DIALOG_TABLE_EDIT_COLUMN_NOT_NULL("dialog.table.editColumn.notNull"),
    /** key:dialog.table.editColumn.PK. */
    DIALOG_TABLE_EDIT_COLUMN_PK("dialog.table.editColumn.PK"),
    /** key:dialog.table.editColumn.size. */
    DIALOG_TABLE_EDIT_COLUMN_SIZE("dialog.table.editColumn.size"),
    /** key:dialog.table.editColumn.type. */
    DIALOG_TABLE_EDIT_COLUMN_TYPE("dialog.table.editColumn.type"),
    /** key:dialog.table.editColumn.UniqueKey. */
    DIALOG_TABLE_EDIT_COLUMN_UNIQUE_KEY("dialog.table.editColumn.UniqueKey"),
    /** key:dialog.table.editColumn.unsigned. */
    DIALOG_TABLE_EDIT_COLUMN_UNSIGNED("dialog.table.editColumn.unsigned"),
    /** key:dialog.table.editIndex.indexColumns. */
    DIALOG_TABLE_EDIT_INDEX_INDEX_COLUMNS("dialog.table.editIndex.indexColumns"),
    /** key:dialog.table.editIndex.indexName. */
    DIALOG_TABLE_EDIT_INDEX_INDEX_NAME("dialog.table.editIndex.indexName"),
    /** key:dialog.table.editIndex.indexType. */
    DIALOG_TABLE_EDIT_INDEX_INDEX_TYPE("dialog.table.editIndex.indexType"),
    /** key:dialog.table.insertNotNullSql. */
    DIALOG_TABLE_INSERT_NOT_NULL_SQL("dialog.table.insertNotNullSql"),
    /** key:dialog.table.insertSql. */
    DIALOG_TABLE_INSERT_SQL("dialog.table.insertSql"),
    /** key:dialog.table.removeColumn. */
    DIALOG_TABLE_REMOVE_COLUMN("dialog.table.removeColumn"),
    /** key:dialog.table.removeIndex. */
    DIALOG_TABLE_REMOVE_INDEX("dialog.table.removeIndex"),
    /** key:dialog.table.tableLogicalName. */
    DIALOG_TABLE_TABLE_LOGICAL_NAME("dialog.table.tableLogicalName"),
    /** key:dialog.table.tablePyhgicalName. */
    DIALOG_TABLE_TABLE_PYHGICAL_NAME("dialog.table.tablePyhgicalName"),
    /** key:dialog.table.title. */
    DIALOG_TABLE_TITLE("dialog.table.title"),
    /** key:dialog.table.upColumn. */
    DIALOG_TABLE_UP_COLUMN("dialog.table.upColumn"),
    /** key:error.db.import. */
    ERROR_DB_IMPORT("error.db.import"),
    /** key:error.edit.linkedTable. */
    ERROR_EDIT_LINKED_TABLE("error.edit.linkedTable"),
    /** key:error.erd.extension. */
    ERROR_ERD_EXTENSION("error.erd.extension"),
    /** key:error.key.duplicate. */
    ERROR_KEY_DUPLICATE("error.key.duplicate"),
    /** key:error.required. */
    ERROR_REQUIRED("error.required"),
    /** key:error.select.referenceKey. */
    ERROR_SELECT_REFERENCE_KEY("error.select.referenceKey"),
    /** key:error.validation. */
    ERROR_VALIDATION("error.validation"),
    /** key:html.column.autoIncrement. */
    HTML_COLUMN_AUTO_INCREMENT("html.column.autoIncrement"),
    /** key:html.column.defaultValue. */
    HTML_COLUMN_DEFAULT_VALUE("html.column.defaultValue"),
    /** key:html.column.description. */
    HTML_COLUMN_DESCRIPTION("html.column.description"),
    /** key:html.column.description.domain. */
    HTML_COLUMN_DESCRIPTION_DOMAIN("html.column.description.domain"),
    /** key:html.column.logicalName. */
    HTML_COLUMN_LOGICAL_NAME("html.column.logicalName"),
    /** key:html.column.notNull. */
    HTML_COLUMN_NOT_NULL("html.column.notNull"),
    /** key:html.column.physicalName. */
    HTML_COLUMN_PHYSICAL_NAME("html.column.physicalName"),
    /** key:html.column.primaryKey. */
    HTML_COLUMN_PRIMARY_KEY("html.column.primaryKey"),
    /** key:html.column.rownum. */
    HTML_COLUMN_ROWNUM("html.column.rownum"),
    /** key:html.column.type. */
    HTML_COLUMN_TYPE("html.column.type"),
    /** key:html.column.unique. */
    HTML_COLUMN_UNIQUE("html.column.unique"),
    /** key:html.column.unsigned. */
    HTML_COLUMN_UNSIGNED("html.column.unsigned"),
    /** key:html.domain.name. */
    HTML_DOMAIN_NAME("html.domain.name"),
    /** key:html.domain.size. */
    HTML_DOMAIN_SIZE("html.domain.size"),
    /** key:html.domain.title. */
    HTML_DOMAIN_TITLE("html.domain.title"),
    /** key:html.domain.type. */
    HTML_DOMAIN_TYPE("html.domain.type"),
    /** key:html.foreignKeys. */
    HTML_FOREIGN_KEYS("html.foreignKeys"),
    /** key:html.indices. */
    HTML_INDICES("html.indices"),
    /** key:html.page.section.title.attribute. */
    HTML_PAGE_SECTION_TITLE_ATTRIBUTE("html.page.section.title.attribute"),
    /** key:html.page.section.title.compositeUniqueKeys. */
    HTML_PAGE_SECTION_TITLE_COMPOSITE_UNIQUE_KEYS("html.page.section.title.compositeUniqueKeys"),
    /** key:html.page.section.title.foreignKeys. */
    HTML_PAGE_SECTION_TITLE_FOREIGN_KEYS("html.page.section.title.foreignKeys"),
    /** key:html.page.section.title.index. */
    HTML_PAGE_SECTION_TITLE_INDEX("html.page.section.title.index"),
    /** key:html.page.title.table. */
    HTML_PAGE_TITLE_TABLE("html.page.title.table"),
    /** key:html.referencedTables. */
    HTML_REFERENCED_TABLES("html.referencedTables"),
    /** key:html.table.description. */
    HTML_TABLE_DESCRIPTION("html.table.description"),
    /** key:html.table.logicalName. */
    HTML_TABLE_LOGICAL_NAME("html.table.logicalName"),
    /** key:html.table.physicalName. */
    HTML_TABLE_PHYSICAL_NAME("html.table.physicalName"),
    /** key:html.title. */
    HTML_TITLE("html.title"),
    /** key:info.cancel. */
    INFO_CANCEL("info.cancel"),
    /** key:label.attribute. */
    LABEL_ATTRIBUTE("label.attribute"),
    /** key:label.autoIncrement. */
    LABEL_AUTO_INCREMENT("label.autoIncrement"),
    /** key:label.column. */
    LABEL_COLUMN("label.column"),
    /** key:label.description. */
    LABEL_DESCRIPTION("label.description"),
    /** key:label.domain. */
    LABEL_DOMAIN("label.domain"),
    /** key:label.index. */
    LABEL_INDEX("label.index"),
    /** key:label.logicalName. */
    LABEL_LOGICAL_NAME("label.logicalName"),
    /** key:label.notNull. */
    LABEL_NOT_NULL("label.notNull"),
    /** key:label.o. */
    LABEL_O("label.o"),
    /** key:label.partialMatch. */
    LABEL_PARTIAL_MATCH("label.partialMatch"),
    /** key:label.physicalName. */
    LABEL_PHYSICAL_NAME("label.physicalName"),
    /** key:label.sql. */
    LABEL_SQL("label.sql"),
    /** key:label.table. */
    LABEL_TABLE("label.table"),
    /** key:label.undef. */
    LABEL_UNDEF("label.undef"),
    /** key:label.x. */
    LABEL_X("label.x"),
    /** key:message.saveBeforeExecute. */
    MESSAGE_SAVE_BEFORE_EXECUTE("message.saveBeforeExecute"),
    /** key:none. */
    NONE("none"),
    /** key:note.importedTable. */
    NOTE_IMPORTED_TABLE("note.importedTable"),
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
    /** key:preference.validation. */
    PREFERENCE_VALIDATION("preference.validation"),
    /** key:preference.validation.columnName.duplicated. */
    PREFERENCE_VALIDATION_COLUMN_NAME_DUPLICATED("preference.validation.columnName.duplicated"),
    /** key:preference.validation.columnName.required. */
    PREFERENCE_VALIDATION_COLUMN_NAME_REQUIRED("preference.validation.columnName.required"),
    /** key:preference.validation.foreignKey.columnSize. */
    PREFERENCE_VALIDATION_FOREIGN_KEY_COLUMN_SIZE("preference.validation.foreignKey.columnSize"),
    /** key:preference.validation.foreignKey.columnType. */
    PREFERENCE_VALIDATION_FOREIGN_KEY_COLUMN_TYPE("preference.validation.foreignKey.columnType"),
    /** key:preference.validation.level.error. */
    PREFERENCE_VALIDATION_LEVEL_ERROR("preference.validation.level.error"),
    /** key:preference.validation.level.ignore. */
    PREFERENCE_VALIDATION_LEVEL_IGNORE("preference.validation.level.ignore"),
    /** key:preference.validation.level.warning. */
    PREFERENCE_VALIDATION_LEVEL_WARNING("preference.validation.level.warning"),
    /** key:preference.validation.logicalColumnName.duplicated. */
    PREFERENCE_VALIDATION_LOGICAL_COLUMN_NAME_DUPLICATED("preference.validation.logicalColumnName.duplicated"),
    /** key:preference.validation.logicalColumnName.required. */
    PREFERENCE_VALIDATION_LOGICAL_COLUMN_NAME_REQUIRED("preference.validation.logicalColumnName.required"),
    /** key:preference.validation.logicalTableName.duplicated. */
    PREFERENCE_VALIDATION_LOGICAL_TABLE_NAME_DUPLICATED("preference.validation.logicalTableName.duplicated"),
    /** key:preference.validation.logicalTableName.required. */
    PREFERENCE_VALIDATION_LOGICAL_TABLE_NAME_REQUIRED("preference.validation.logicalTableName.required"),
    /** key:preference.validation.noColumns. */
    PREFERENCE_VALIDATION_NO_COLUMNS("preference.validation.noColumns"),
    /** key:preference.validation.primaryKey. */
    PREFERENCE_VALIDATION_PRIMARY_KEY("preference.validation.primaryKey"),
    /** key:preference.validation.tableName.duplicated. */
    PREFERENCE_VALIDATION_TABLE_NAME_DUPLICATED("preference.validation.tableName.duplicated"),
    /** key:preference.validation.tableName.required. */
    PREFERENCE_VALIDATION_TABLE_NAME_REQUIRED("preference.validation.tableName.required"),
    /** key:property.backgroundColor. */
    PROPERTY_BACKGROUND_COLOR("property.backgroundColor"),
    /** key:property.font. */
    PROPERTY_FONT("property.font"),
    /** key:property.foreignKeyName. */
    PROPERTY_FOREIGN_KEY_NAME("property.foreignKeyName"),
    /** key:property.linkedPath. */
    PROPERTY_LINKED_PATH("property.linkedPath"),
    /** key:property.logicalTableName. */
    PROPERTY_LOGICAL_TABLE_NAME("property.logicalTableName"),
    /** key:property.physicalTableName. */
    PROPERTY_PHYSICAL_TABLE_NAME("property.physicalTableName"),
    /** key:property.schema. */
    PROPERTY_SCHEMA("property.schema"),
    /** key:property.text. */
    PROPERTY_TEXT("property.text"),
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
    /** key:wizard.changedb.databaseType. */
    WIZARD_CHANGEDB_DATABASE_TYPE("wizard.changedb.databaseType"),
    /** key:wizard.changedb.description. */
    WIZARD_CHANGEDB_DESCRIPTION("wizard.changedb.description"),
    /** key:wizard.changedb.title. */
    WIZARD_CHANGEDB_TITLE("wizard.changedb.title"),
    /** key:wizard.generate.browse.message. */
    WIZARD_GENERATE_BROWSE_MESSAGE("wizard.generate.browse.message"),
    /** key:wizard.generate.browse.title. */
    WIZARD_GENERATE_BROWSE_TITLE("wizard.generate.browse.title"),
    /** key:wizard.generate.ddl.alterTable. */
    WIZARD_GENERATE_DDL_ALTER_TABLE("wizard.generate.ddl.alterTable"),
    /** key:wizard.generate.ddl.comment. */
    WIZARD_GENERATE_DDL_COMMENT("wizard.generate.ddl.comment"),
    /** key:wizard.generate.ddl.confirm.message. */
    WIZARD_GENERATE_DDL_CONFIRM_MESSAGE("wizard.generate.ddl.confirm.message"),
    /** key:wizard.generate.ddl.description. */
    WIZARD_GENERATE_DDL_DESCRIPTION("wizard.generate.ddl.description"),
    /** key:wizard.generate.ddl.drop. */
    WIZARD_GENERATE_DDL_DROP("wizard.generate.ddl.drop"),
    /** key:wizard.generate.ddl.encoding. */
    WIZARD_GENERATE_DDL_ENCODING("wizard.generate.ddl.encoding"),
    /** key:wizard.generate.ddl.error.encoding. */
    WIZARD_GENERATE_DDL_ERROR_ENCODING("wizard.generate.ddl.error.encoding"),
    /** key:wizard.generate.ddl.error.filename. */
    WIZARD_GENERATE_DDL_ERROR_FILENAME("wizard.generate.ddl.error.filename"),
    /** key:wizard.generate.ddl.error.output. */
    WIZARD_GENERATE_DDL_ERROR_OUTPUT("wizard.generate.ddl.error.output"),
    /** key:wizard.generate.ddl.error.path. */
    WIZARD_GENERATE_DDL_ERROR_PATH("wizard.generate.ddl.error.path"),
    /** key:wizard.generate.ddl.filename. */
    WIZARD_GENERATE_DDL_FILENAME("wizard.generate.ddl.filename"),
    /** key:wizard.generate.ddl.lineSeparator. */
    WIZARD_GENERATE_DDL_LINE_SEPARATOR("wizard.generate.ddl.lineSeparator"),
    /** key:wizard.generate.ddl.schema. */
    WIZARD_GENERATE_DDL_SCHEMA("wizard.generate.ddl.schema"),
    /** key:wizard.generate.ddl.title. */
    WIZARD_GENERATE_DDL_TITLE("wizard.generate.ddl.title"),
    /** key:wizard.generate.folder. */
    WIZARD_GENERATE_FOLDER("wizard.generate.folder"),
    /** key:wizard.importFromDiagram.erdFile. */
    WIZARD_IMPORT_FROM_DIAGRAM_ERD_FILE("wizard.importFromDiagram.erdFile"),
    /** key:wizard.importFromDiagram.error.existTable. */
    WIZARD_IMPORT_FROM_DIAGRAM_ERROR_EXIST_TABLE("wizard.importFromDiagram.error.existTable"),
    /** key:wizard.importFromDiagram.message. */
    WIZARD_IMPORT_FROM_DIAGRAM_MESSAGE("wizard.importFromDiagram.message"),
    /** key:wizard.importFromDiagram.title. */
    WIZARD_IMPORT_FROM_DIAGRAM_TITLE("wizard.importFromDiagram.title"),
    /** key:wizard.new.erd.message. */
    WIZARD_NEW_ERD_MESSAGE("wizard.new.erd.message"),
    /** key:wizard.new.erd.product. */
    WIZARD_NEW_ERD_PRODUCT("wizard.new.erd.product"),
    /** key:wizard.new.erd.schema. */
    WIZARD_NEW_ERD_SCHEMA("wizard.new.erd.schema"),
    /** key:wizard.new.erd.title. */
    WIZARD_NEW_ERD_TITLE("wizard.new.erd.title"),
    /** key:wizard.new.import.autoConvert. */
    WIZARD_NEW_IMPORT_AUTO_CONVERT("wizard.new.import.autoConvert"),
    /** key:wizard.new.import.catalog. */
    WIZARD_NEW_IMPORT_CATALOG("wizard.new.import.catalog"),
    /** key:wizard.new.import.driver. */
    WIZARD_NEW_IMPORT_DRIVER("wizard.new.import.driver"),
    /** key:wizard.new.import.filter. */
    WIZARD_NEW_IMPORT_FILTER("wizard.new.import.filter"),
    /** key:wizard.new.import.jarFile. */
    WIZARD_NEW_IMPORT_JAR_FILE("wizard.new.import.jarFile"),
    /** key:wizard.new.import.loadTables. */
    WIZARD_NEW_IMPORT_LOAD_TABLES("wizard.new.import.loadTables"),
    /** key:wizard.new.import.message. */
    WIZARD_NEW_IMPORT_MESSAGE("wizard.new.import.message"),
    /** key:wizard.new.import.pass. */
    WIZARD_NEW_IMPORT_PASS("wizard.new.import.pass"),
    /** key:wizard.new.import.schema. */
    WIZARD_NEW_IMPORT_SCHEMA("wizard.new.import.schema"),
    /** key:wizard.new.import.tables. */
    WIZARD_NEW_IMPORT_TABLES("wizard.new.import.tables"),
    /** key:wizard.new.import.title. */
    WIZARD_NEW_IMPORT_TITLE("wizard.new.import.title"),
    /** key:wizard.new.import.uri. */
    WIZARD_NEW_IMPORT_URI("wizard.new.import.uri"),
    /** key:wizard.new.import.user. */
    WIZARD_NEW_IMPORT_USER("wizard.new.import.user"),
    /** key:wizard.new.import.view. */
    WIZARD_NEW_IMPORT_VIEW("wizard.new.import.view"),
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
