
package io.github.parapata.erde.editor.persistent.diagram;

import javax.xml.namespace.QName;
import io.github.parapata.erde.editor.persistent.adapter.BooleanAdapter;
import io.github.parapata.erde.editor.persistent.adapter.IntegerAdapter;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the io.github.parapata.erde.editor.persistent.diagram package.
 * <p>
 * An ObjectFactory allows you to programmatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups. Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _DialectName_QNAME = new QName("", "dialectName");
    private static final QName _SchemaName_QNAME = new QName("", "schemaName");
    private static final QName _LowerCase_QNAME = new QName("", "lowerCase");
    private static final QName _LogicalMode_QNAME = new QName("", "logicalMode");
    private static final QName _IncludeView_QNAME = new QName("", "includeView");
    private static final QName _Notation_QNAME = new QName("", "notation");
    private static final QName _Zoom_QNAME = new QName("", "zoom");
    private static final QName _PhysicalName_QNAME = new QName("", "physicalName");
    private static final QName _LogicalName_QNAME = new QName("", "logicalName");
    private static final QName _Description_QNAME = new QName("", "description");
    private static final QName _DomainId_QNAME = new QName("", "domainId");
    private static final QName _Type_QNAME = new QName("", "type");
    private static final QName _EnumName_QNAME = new QName("", "enumName");
    private static final QName _ColumnSize_QNAME = new QName("", "columnSize");
    private static final QName _Decimal_QNAME = new QName("", "decimal");
    private static final QName _Unsigned_QNAME = new QName("", "unsigned");
    private static final QName _AutoIncrement_QNAME = new QName("", "autoIncrement");
    private static final QName _NotNull_QNAME = new QName("", "notNull");
    private static final QName _PrimaryKey_QNAME = new QName("", "primaryKey");
    private static final QName _UniqueKey_QNAME = new QName("", "uniqueKey");
    private static final QName _DefaultValue_QNAME = new QName("", "defaultValue");
    private static final QName _ColumnName_QNAME = new QName("", "columnName");
    private static final QName _OnUpdateOption_QNAME = new QName("", "onUpdateOption");
    private static final QName _OnDeleteOption_QNAME = new QName("", "onDeleteOption");
    private static final QName _SourceCardinality_QNAME = new QName("", "sourceCardinality");
    private static final QName _TargetCardinality_QNAME = new QName("", "targetCardinality");
    private static final QName _ReferenceName_QNAME = new QName("", "referenceName");
    private static final QName _TargetName_QNAME = new QName("", "targetName");
    private static final QName _Schema_QNAME = new QName("", "schema");
    private static final QName _WithoutOIDs_QNAME = new QName("", "withoutOIDs");
    private static final QName _CharacterSet_QNAME = new QName("", "characterSet");
    private static final QName _Collation_QNAME = new QName("", "collation");
    private static final QName _StorageEngine_QNAME = new QName("", "storageEngine");
    private static final QName _PrimaryKeyLengthOfText_QNAME = new QName("", "primaryKeyLengthOfText");
    private static final QName _Text_QNAME = new QName("", "text");
    private static final QName _DomainName_QNAME = new QName("", "domainName");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package:
     * io.github.parapata.erde.editor.persistent.diagram
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ErdeXmlModel }
     *
     * @return
     *         the new instance of {@link ErdeXmlModel }
     */
    public ErdeXmlModel createErdeXmlModel() {
        return new ErdeXmlModel();
    }

    /**
     * Create an instance of {@link ColorXmlModel }
     *
     * @return
     *         the new instance of {@link ColorXmlModel }
     */
    public ColorXmlModel createColorXmlModel() {
        return new ColorXmlModel();
    }

    /**
     * Create an instance of {@link FontXmlModel }
     *
     * @return
     *         the new instance of {@link FontXmlModel }
     */
    public FontXmlModel createFontXmlModel() {
        return new FontXmlModel();
    }

    /**
     * Create an instance of {@link DiagramXmlModel }
     *
     * @return
     *         the new instance of {@link DiagramXmlModel }
     */
    public DiagramXmlModel createDiagramXmlModel() {
        return new DiagramXmlModel();
    }

    /**
     * Create an instance of {@link TableXmlModel }
     *
     * @return
     *         the new instance of {@link TableXmlModel }
     */
    public TableXmlModel createTableXmlModel() {
        return new TableXmlModel();
    }

    /**
     * Create an instance of {@link LocationXmlModel }
     *
     * @return
     *         the new instance of {@link LocationXmlModel }
     */
    public LocationXmlModel createLocationXmlModel() {
        return new LocationXmlModel();
    }

    /**
     * Create an instance of {@link ColumnXmlModel }
     *
     * @return
     *         the new instance of {@link ColumnXmlModel }
     */
    public ColumnXmlModel createColumnXmlModel() {
        return new ColumnXmlModel();
    }

    /**
     * Create an instance of {@link IndexXmlModel }
     *
     * @return
     *         the new instance of {@link IndexXmlModel }
     */
    public IndexXmlModel createIndexXmlModel() {
        return new IndexXmlModel();
    }

    /**
     * Create an instance of {@link ForeignKeyXmlModel }
     *
     * @return
     *         the new instance of {@link ForeignKeyXmlModel }
     */
    public ForeignKeyXmlModel createForeignKeyXmlModel() {
        return new ForeignKeyXmlModel();
    }

    /**
     * Create an instance of {@link ForeignKeyMappingXmlModel }
     *
     * @return
     *         the new instance of {@link ForeignKeyMappingXmlModel }
     */
    public ForeignKeyMappingXmlModel createForeignKeyMappingXmlModel() {
        return new ForeignKeyMappingXmlModel();
    }

    /**
     * Create an instance of {@link TablePropertiesXmlModel }
     *
     * @return
     *         the new instance of {@link TablePropertiesXmlModel }
     */
    public TablePropertiesXmlModel createTablePropertiesXmlModel() {
        return new TablePropertiesXmlModel();
    }

    /**
     * Create an instance of {@link NoteXmlModel }
     *
     * @return
     *         the new instance of {@link NoteXmlModel }
     */
    public NoteXmlModel createNoteXmlModel() {
        return new NoteXmlModel();
    }

    /**
     * Create an instance of {@link NoteConnectionXmlModel }
     *
     * @return
     *         the new instance of {@link NoteConnectionXmlModel }
     */
    public NoteConnectionXmlModel createNoteConnectionXmlModel() {
        return new NoteConnectionXmlModel();
    }

    /**
     * Create an instance of {@link DomainsXmlModel }
     *
     * @return
     *         the new instance of {@link DomainsXmlModel }
     */
    public DomainsXmlModel createDomainsXmlModel() {
        return new DomainsXmlModel();
    }

    /**
     * Create an instance of {@link DomainXmlModel }
     *
     * @return
     *         the new instance of {@link DomainXmlModel }
     */
    public DomainXmlModel createDomainXmlModel() {
        return new DomainXmlModel();
    }

    /**
     * Create an instance of {@link PropertyXmlModel }
     *
     * @return
     *         the new instance of {@link PropertyXmlModel }
     */
    public PropertyXmlModel createPropertyXmlModel() {
        return new PropertyXmlModel();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "dialectName")
    public JAXBElement<String> createDialectName(String value) {
        return new JAXBElement<>(_DialectName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "schemaName")
    public JAXBElement<String> createSchemaName(String value) {
        return new JAXBElement<>(_SchemaName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "lowerCase")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    public JAXBElement<Boolean> createLowerCase(Boolean value) {
        return new JAXBElement<>(_LowerCase_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "logicalMode")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    public JAXBElement<Boolean> createLogicalMode(Boolean value) {
        return new JAXBElement<>(_LogicalMode_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "includeView")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    public JAXBElement<Boolean> createIncludeView(Boolean value) {
        return new JAXBElement<>(_IncludeView_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "notation")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createNotation(String value) {
        return new JAXBElement<>(_Notation_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link Double }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "zoom")
    public JAXBElement<Double> createZoom(Double value) {
        return new JAXBElement<>(_Zoom_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "physicalName")
    public JAXBElement<String> createPhysicalName(String value) {
        return new JAXBElement<>(_PhysicalName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "logicalName")
    public JAXBElement<String> createLogicalName(String value) {
        return new JAXBElement<>(_LogicalName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "description")
    public JAXBElement<String> createDescription(String value) {
        return new JAXBElement<>(_Description_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "domainId")
    public JAXBElement<String> createDomainId(String value) {
        return new JAXBElement<>(_DomainId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "type")
    public JAXBElement<String> createType(String value) {
        return new JAXBElement<>(_Type_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "enumName")
    public JAXBElement<String> createEnumName(String value) {
        return new JAXBElement<>(_EnumName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "columnSize")
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    public JAXBElement<Integer> createColumnSize(Integer value) {
        return new JAXBElement<>(_ColumnSize_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "decimal")
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    public JAXBElement<Integer> createDecimal(Integer value) {
        return new JAXBElement<>(_Decimal_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "unsigned")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    public JAXBElement<Boolean> createUnsigned(Boolean value) {
        return new JAXBElement<>(_Unsigned_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "autoIncrement")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    public JAXBElement<Boolean> createAutoIncrement(Boolean value) {
        return new JAXBElement<>(_AutoIncrement_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "notNull")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    public JAXBElement<Boolean> createNotNull(Boolean value) {
        return new JAXBElement<>(_NotNull_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "primaryKey")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    public JAXBElement<Boolean> createPrimaryKey(Boolean value) {
        return new JAXBElement<>(_PrimaryKey_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "uniqueKey")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    public JAXBElement<Boolean> createUniqueKey(Boolean value) {
        return new JAXBElement<>(_UniqueKey_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "defaultValue")
    public JAXBElement<String> createDefaultValue(String value) {
        return new JAXBElement<>(_DefaultValue_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "columnName")
    public JAXBElement<String> createColumnName(String value) {
        return new JAXBElement<>(_ColumnName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReferenceOption }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link ReferenceOption }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "onUpdateOption")
    public JAXBElement<ReferenceOption> createOnUpdateOption(ReferenceOption value) {
        return new JAXBElement<>(_OnUpdateOption_QNAME, ReferenceOption.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReferenceOption }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link ReferenceOption }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "onDeleteOption")
    public JAXBElement<ReferenceOption> createOnDeleteOption(ReferenceOption value) {
        return new JAXBElement<>(_OnDeleteOption_QNAME, ReferenceOption.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "sourceCardinality")
    public JAXBElement<String> createSourceCardinality(String value) {
        return new JAXBElement<>(_SourceCardinality_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "targetCardinality")
    public JAXBElement<String> createTargetCardinality(String value) {
        return new JAXBElement<>(_TargetCardinality_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "referenceName")
    public JAXBElement<String> createReferenceName(String value) {
        return new JAXBElement<>(_ReferenceName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "targetName")
    public JAXBElement<String> createTargetName(String value) {
        return new JAXBElement<>(_TargetName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "schema")
    public JAXBElement<String> createSchema(String value) {
        return new JAXBElement<>(_Schema_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "withoutOIDs")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    public JAXBElement<Boolean> createWithoutOIDs(Boolean value) {
        return new JAXBElement<>(_WithoutOIDs_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "characterSet")
    public JAXBElement<String> createCharacterSet(String value) {
        return new JAXBElement<>(_CharacterSet_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "collation")
    public JAXBElement<String> createCollation(String value) {
        return new JAXBElement<>(_Collation_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "storageEngine")
    public JAXBElement<String> createStorageEngine(String value) {
        return new JAXBElement<>(_StorageEngine_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "primaryKeyLengthOfText")
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    public JAXBElement<Integer> createPrimaryKeyLengthOfText(Integer value) {
        return new JAXBElement<>(_PrimaryKeyLengthOfText_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "text")
    public JAXBElement<String> createText(String value) {
        return new JAXBElement<>(_Text_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "domainName")
    public JAXBElement<String> createDomainName(String value) {
        return new JAXBElement<>(_DomainName_QNAME, String.class, null, value);
    }

}
