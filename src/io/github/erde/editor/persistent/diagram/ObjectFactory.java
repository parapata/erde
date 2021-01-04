
package io.github.erde.editor.persistent.diagram;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import io.github.erde.editor.persistent.adapter.BooleanAdapter;
import io.github.erde.editor.persistent.adapter.IntegerAdapter;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the io.github.erde.editor.persistent.diagram package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DialectName_QNAME = new QName("", "dialectName");
    private final static QName _SchemaName_QNAME = new QName("", "schemaName");
    private final static QName _LowerCase_QNAME = new QName("", "lowerCase");
    private final static QName _LogicalMode_QNAME = new QName("", "logicalMode");
    private final static QName _IncludeView_QNAME = new QName("", "includeView");
    private final static QName _Notation_QNAME = new QName("", "notation");
    private final static QName _JarFile_QNAME = new QName("", "jarFile");
    private final static QName _JdbcDriver_QNAME = new QName("", "jdbcDriver");
    private final static QName _JdbcUrl_QNAME = new QName("", "jdbcUrl");
    private final static QName _JdbcCatalog_QNAME = new QName("", "jdbcCatalog");
    private final static QName _JdbcSchema_QNAME = new QName("", "jdbcSchema");
    private final static QName _JdbcUser_QNAME = new QName("", "jdbcUser");
    private final static QName _JdbcPassword_QNAME = new QName("", "jdbcPassword");
    private final static QName _PhysicalName_QNAME = new QName("", "physicalName");
    private final static QName _LogicalName_QNAME = new QName("", "logicalName");
    private final static QName _Description_QNAME = new QName("", "description");
    private final static QName _DomainId_QNAME = new QName("", "domainId");
    private final static QName _Type_QNAME = new QName("", "type");
    private final static QName _EnumName_QNAME = new QName("", "enumName");
    private final static QName _ColumnSize_QNAME = new QName("", "columnSize");
    private final static QName _Decimal_QNAME = new QName("", "decimal");
    private final static QName _Unsigned_QNAME = new QName("", "unsigned");
    private final static QName _AutoIncrement_QNAME = new QName("", "autoIncrement");
    private final static QName _NotNull_QNAME = new QName("", "notNull");
    private final static QName _PrimaryKey_QNAME = new QName("", "primaryKey");
    private final static QName _UniqueKey_QNAME = new QName("", "uniqueKey");
    private final static QName _DefaultValue_QNAME = new QName("", "defaultValue");
    private final static QName _ColumnName_QNAME = new QName("", "columnName");
    private final static QName _OnUpdateOption_QNAME = new QName("", "onUpdateOption");
    private final static QName _OnDeleteOption_QNAME = new QName("", "onDeleteOption");
    private final static QName _SourceCardinality_QNAME = new QName("", "sourceCardinality");
    private final static QName _TargetCardinality_QNAME = new QName("", "targetCardinality");
    private final static QName _ReferenceName_QNAME = new QName("", "referenceName");
    private final static QName _TargetName_QNAME = new QName("", "targetName");
    private final static QName _Schema_QNAME = new QName("", "schema");
    private final static QName _WithoutOIDs_QNAME = new QName("", "withoutOIDs");
    private final static QName _CharacterSet_QNAME = new QName("", "characterSet");
    private final static QName _Collation_QNAME = new QName("", "collation");
    private final static QName _StorageEngine_QNAME = new QName("", "storageEngine");
    private final static QName _PrimaryKeyLengthOfText_QNAME = new QName("", "primaryKeyLengthOfText");
    private final static QName _Text_QNAME = new QName("", "text");
    private final static QName _DomainName_QNAME = new QName("", "domainName");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: io.github.erde.editor.persistent.diagram
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ErdeXmlModel }
     * 
     */
    public ErdeXmlModel createErdeXmlModel() {
        return new ErdeXmlModel();
    }

    /**
     * Create an instance of {@link ColorXmlModel }
     * 
     */
    public ColorXmlModel createColorXmlModel() {
        return new ColorXmlModel();
    }

    /**
     * Create an instance of {@link FontXmlModel }
     * 
     */
    public FontXmlModel createFontXmlModel() {
        return new FontXmlModel();
    }

    /**
     * Create an instance of {@link DbSettingsXmlModel }
     * 
     */
    public DbSettingsXmlModel createDbSettingsXmlModel() {
        return new DbSettingsXmlModel();
    }

    /**
     * Create an instance of {@link DiagramXmlModel }
     * 
     */
    public DiagramXmlModel createDiagramXmlModel() {
        return new DiagramXmlModel();
    }

    /**
     * Create an instance of {@link TableXmlModel }
     * 
     */
    public TableXmlModel createTableXmlModel() {
        return new TableXmlModel();
    }

    /**
     * Create an instance of {@link LocationXmlModel }
     * 
     */
    public LocationXmlModel createLocationXmlModel() {
        return new LocationXmlModel();
    }

    /**
     * Create an instance of {@link ColumnXmlModel }
     * 
     */
    public ColumnXmlModel createColumnXmlModel() {
        return new ColumnXmlModel();
    }

    /**
     * Create an instance of {@link IndexXmlModel }
     * 
     */
    public IndexXmlModel createIndexXmlModel() {
        return new IndexXmlModel();
    }

    /**
     * Create an instance of {@link ForeignKeyXmlModel }
     * 
     */
    public ForeignKeyXmlModel createForeignKeyXmlModel() {
        return new ForeignKeyXmlModel();
    }

    /**
     * Create an instance of {@link ForeignKeyMappingXmlModel }
     * 
     */
    public ForeignKeyMappingXmlModel createForeignKeyMappingXmlModel() {
        return new ForeignKeyMappingXmlModel();
    }

    /**
     * Create an instance of {@link TablePropertiesXmlModel }
     * 
     */
    public TablePropertiesXmlModel createTablePropertiesXmlModel() {
        return new TablePropertiesXmlModel();
    }

    /**
     * Create an instance of {@link NoteXmlModel }
     * 
     */
    public NoteXmlModel createNoteXmlModel() {
        return new NoteXmlModel();
    }

    /**
     * Create an instance of {@link NoteConnectionXmlModel }
     * 
     */
    public NoteConnectionXmlModel createNoteConnectionXmlModel() {
        return new NoteConnectionXmlModel();
    }

    /**
     * Create an instance of {@link DomainXmlModel }
     * 
     */
    public DomainXmlModel createDomainXmlModel() {
        return new DomainXmlModel();
    }

    /**
     * Create an instance of {@link PropertyXmlModel }
     * 
     */
    public PropertyXmlModel createPropertyXmlModel() {
        return new PropertyXmlModel();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "dialectName")
    public JAXBElement<String> createDialectName(String value) {
        return new JAXBElement<String>(_DialectName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "schemaName")
    public JAXBElement<String> createSchemaName(String value) {
        return new JAXBElement<String>(_SchemaName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "lowerCase")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    public JAXBElement<Boolean> createLowerCase(Boolean value) {
        return new JAXBElement<Boolean>(_LowerCase_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "logicalMode")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    public JAXBElement<Boolean> createLogicalMode(Boolean value) {
        return new JAXBElement<Boolean>(_LogicalMode_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "includeView")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    public JAXBElement<Boolean> createIncludeView(Boolean value) {
        return new JAXBElement<Boolean>(_IncludeView_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "notation")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createNotation(String value) {
        return new JAXBElement<String>(_Notation_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "jarFile")
    public JAXBElement<String> createJarFile(String value) {
        return new JAXBElement<String>(_JarFile_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "jdbcDriver")
    public JAXBElement<String> createJdbcDriver(String value) {
        return new JAXBElement<String>(_JdbcDriver_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "jdbcUrl")
    public JAXBElement<String> createJdbcUrl(String value) {
        return new JAXBElement<String>(_JdbcUrl_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "jdbcCatalog")
    public JAXBElement<String> createJdbcCatalog(String value) {
        return new JAXBElement<String>(_JdbcCatalog_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "jdbcSchema")
    public JAXBElement<String> createJdbcSchema(String value) {
        return new JAXBElement<String>(_JdbcSchema_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "jdbcUser")
    public JAXBElement<String> createJdbcUser(String value) {
        return new JAXBElement<String>(_JdbcUser_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "jdbcPassword")
    public JAXBElement<String> createJdbcPassword(String value) {
        return new JAXBElement<String>(_JdbcPassword_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "physicalName")
    public JAXBElement<String> createPhysicalName(String value) {
        return new JAXBElement<String>(_PhysicalName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "logicalName")
    public JAXBElement<String> createLogicalName(String value) {
        return new JAXBElement<String>(_LogicalName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "description")
    public JAXBElement<String> createDescription(String value) {
        return new JAXBElement<String>(_Description_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "domainId")
    public JAXBElement<String> createDomainId(String value) {
        return new JAXBElement<String>(_DomainId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "type")
    public JAXBElement<String> createType(String value) {
        return new JAXBElement<String>(_Type_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "enumName")
    public JAXBElement<String> createEnumName(String value) {
        return new JAXBElement<String>(_EnumName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "columnSize")
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    public JAXBElement<Integer> createColumnSize(Integer value) {
        return new JAXBElement<Integer>(_ColumnSize_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "decimal")
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    public JAXBElement<Integer> createDecimal(Integer value) {
        return new JAXBElement<Integer>(_Decimal_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "unsigned")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    public JAXBElement<Boolean> createUnsigned(Boolean value) {
        return new JAXBElement<Boolean>(_Unsigned_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "autoIncrement")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    public JAXBElement<Boolean> createAutoIncrement(Boolean value) {
        return new JAXBElement<Boolean>(_AutoIncrement_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "notNull")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    public JAXBElement<Boolean> createNotNull(Boolean value) {
        return new JAXBElement<Boolean>(_NotNull_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "primaryKey")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    public JAXBElement<Boolean> createPrimaryKey(Boolean value) {
        return new JAXBElement<Boolean>(_PrimaryKey_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "uniqueKey")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    public JAXBElement<Boolean> createUniqueKey(Boolean value) {
        return new JAXBElement<Boolean>(_UniqueKey_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "defaultValue")
    public JAXBElement<String> createDefaultValue(String value) {
        return new JAXBElement<String>(_DefaultValue_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "columnName")
    public JAXBElement<String> createColumnName(String value) {
        return new JAXBElement<String>(_ColumnName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReferenceOption }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ReferenceOption }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "onUpdateOption")
    public JAXBElement<ReferenceOption> createOnUpdateOption(ReferenceOption value) {
        return new JAXBElement<ReferenceOption>(_OnUpdateOption_QNAME, ReferenceOption.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReferenceOption }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ReferenceOption }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "onDeleteOption")
    public JAXBElement<ReferenceOption> createOnDeleteOption(ReferenceOption value) {
        return new JAXBElement<ReferenceOption>(_OnDeleteOption_QNAME, ReferenceOption.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "sourceCardinality")
    public JAXBElement<String> createSourceCardinality(String value) {
        return new JAXBElement<String>(_SourceCardinality_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "targetCardinality")
    public JAXBElement<String> createTargetCardinality(String value) {
        return new JAXBElement<String>(_TargetCardinality_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "referenceName")
    public JAXBElement<String> createReferenceName(String value) {
        return new JAXBElement<String>(_ReferenceName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "targetName")
    public JAXBElement<String> createTargetName(String value) {
        return new JAXBElement<String>(_TargetName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "schema")
    public JAXBElement<String> createSchema(String value) {
        return new JAXBElement<String>(_Schema_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "withoutOIDs")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    public JAXBElement<Boolean> createWithoutOIDs(Boolean value) {
        return new JAXBElement<Boolean>(_WithoutOIDs_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "characterSet")
    public JAXBElement<String> createCharacterSet(String value) {
        return new JAXBElement<String>(_CharacterSet_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "collation")
    public JAXBElement<String> createCollation(String value) {
        return new JAXBElement<String>(_Collation_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "storageEngine")
    public JAXBElement<String> createStorageEngine(String value) {
        return new JAXBElement<String>(_StorageEngine_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "primaryKeyLengthOfText")
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    public JAXBElement<Integer> createPrimaryKeyLengthOfText(Integer value) {
        return new JAXBElement<Integer>(_PrimaryKeyLengthOfText_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "text")
    public JAXBElement<String> createText(String value) {
        return new JAXBElement<String>(_Text_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "domainName")
    public JAXBElement<String> createDomainName(String value) {
        return new JAXBElement<String>(_DomainName_QNAME, String.class, null, value);
    }

}
