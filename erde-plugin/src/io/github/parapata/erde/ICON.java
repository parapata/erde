package io.github.parapata.erde;

/**
 * ICON.
 *
 * @author parapata
 * @since 1.0.8
 */
public enum ICON {
    /** . */
    ALIGN_BOTTOM("icons/alignbottom.gif"),
    /** . */
    ALIGN_CENTER("icons/aligncenter.gif"),
    /** . */
    ALIGN_LEFT("icons/alignleft.gif"),
    /** . */
    ALIGN_MID("icons/alignmid.gif"),
    /** . */
    ALIGN_RIGHT("icons/alignright.gif"),
    /** . */
    ALIGN_TOP("icons/aligntop.gif"),
    /** . */
    ANCHOR("icons/anchor.gif"),
    /** . */
    APPLICATION_RESIZE_ACTUAL("icons/application-resize-actual.png"),
    /** . */
    ARROW_TURN_000_LEFT("icons/arrow-turn-000-left.png"),
    /** . */
    ARROW16("icons/arrow16.gif"),
    /** . */
    ASSOCIATION("icons/association.gif"),
    /** . */
    BINOCULAR("icons/binocular.png"),
    /** . */
    BLANK_WHITE("icons/blank_white.gif"),
    /** . */
    CATEGORY("icons/category.gif"),
    /** . */
    COLOR("icons/color.gif"),
    /** . */
    COLUMN("icons/column.gif"),
    /** . */
    COMMENT_CONNECTION("icons/comment_connection.gif"),
    /** . */
    COMMENT_CONNECTION_DISABLED("icons/comment_connection_disabled.gif"),
    /** . */
    DATABASE("icons/database.png"),
    /** . */
    DATABASE2("icons/database2.png"),
    /** . */
    DATABASE_CONNECT("icons/database_connect.png"),
    /** . */
    DIAGRAM("icons/diagram.png"),
    /** . */
    DICTIONARY("icons/dictionary.gif"),
    /** . */
    DICTIONARY_2("icons/dictionary_2.png"),
    /** . */
    DICTIONARY_OPEN("icons/dictionary_open.gif"),
    /** . */
    DOCUMENT_ATTRIBUTE_D("icons/document-attribute-d.png"),
    /** . */
    DOCUMENT_EXCEL_CSV("icons/document-excel-csv.png"),
    /** . */
    DOCUMENT_EXCEL("icons/document-excel.png"),
    /** . */
    DOCUMENT_GLOBE("icons/document-globe.png"),
    /** . */
    DOCUMENT_IMAGE("icons/document-image.png"),
    /** . */
    DOMAIN("icons/domain.gif"),
    /** . */
    EDIT_EXCEL("icons/edit_excel.png"),
    /** . */
    ERROR("icons/error.gif"),
    /** . */
    FOLDER("icons/folder.gif"),
    /** . */
    FOREIGN_KEY("icons/foreign_key.gif"),
    /** . */
    GRID_SNAP("icons/grid-snap.png"),
    /** . */
    GRID("icons/grid.png"),
    /** . */
    GROUP("icons/group.gif"),
    /** . */
    HORIZONTAL_LINE("icons/horizontal_line.gif"),
    /** . */
    HORIZONTAL_LINE_DISABLED("icons/horizontal_line_disabled.gif"),
    /** . */
    IMAGE_PLUS("icons/image--plus.png"),
    /** . */
    INDEX("icons/index.gif"),
    /** . */
    LIST("icons/list.txt"),
    /** . */
    LOCK_PENCIL("icons/lock--pencil.png"),
    /** . */
    MAGNIFIER_ZOOM_ACTUAL("icons/magnifier-zoom-actual.png"),
    /** . */
    MAGNIFIER_ZOOM_OUT("icons/magnifier-zoom-out.png"),
    /** . */
    MAGNIFIER_ZOOM("icons/magnifier-zoom.png"),
    /** . */
    /** . */
    MATCHHEIGHT("icons/matchheight.gif"),
    /** . */
    MATCHWIDTH("icons/matchwidth.gif"),
    /** . */
    NON_NULL("icons/non_null.gif"),
    /** . */
    NOTE("icons/note.gif"),
    /** . */
    NOTE_DISABLED("icons/note_disabled.gif"),
    /** . */
    NOTE_TABLE_DISABLED("icons/note_table_disabled.gif"),
    /** . */
    OVERLAY_ERROR("icons/ovr_error.gif"),
    /** . */
    OVERLAY_WARNING("icons/ovr_warning.gif"),
    /** . */
    PAGE_WHITE_CUP("icons/page_white_cup.png"),
    /** . */
    PALETTE("icons/palette.png"),
    /** . */
    PENCIL("icons/pencil.png"),
    /** . */
    PKEY("icons/pkey.gif"),
    /** . */
    PK_COLUMN("icons/pk_column.gif"),
    /** . */
    PRIMARYKEY("icons/primarykey.gif"),
    /** . */
    PRINT("icons/print.gif"),
    /** . */
    REFERENCE("icons/reference.gif"),
    /** . */
    REFRESH("icons/refresh.gif"),
    /** . */
    RELATION_1_N("icons/relation_1_n.gif"),
    /** . */
    RELATION_1_N_DISABLED("icons/relation_1_n_disabled.gif"),
    /** . */
    RELATION_N_N("icons/relation_n_n.gif"),
    /** . */
    RELATION_N_N_DISABLED("icons/relation_n_n_disabled.gif"),
    /** . */
    RELATION_SELF("icons/relation_self.gif"),
    /** . */
    RELATION_SELF__DISABLED("icons/relation_self__disabled.gif"),
    /** . */
    SCRIPT_ATTRIBUTE_J("icons/script-attribute-j.png"),
    /** . */
    SELF("icons/self.gif"),
    /** . */
    SEQUENCE("icons/sequence.gif"),
    /** . */
    SMALL_CIRCLE("icons/small_circle.png"),
    /** . */
    SQUARE("icons/square.gif"),
    /** . */
    STAR_RED("icons/star_red.png"),
    /** . */
    TABLE("icons/table.gif"),
    /** . */
    TABLES_ARROW("icons/tables--arrow.png"),
    /** . */
    TABLES_PENCIL("icons/tables--pencil.png"),
    /** . */
    TABLE_NEW("icons/table_new.gif"),
    /** . */
    TABLE_NEW_DISABLED("icons/table_new_disabled.gif"),
    /** . */
    TABLE_WAKU("icons/table_waku.gif"),
    /** . */
    TICK("icons/tick.png"),
    /** . */
    TICK_GREY("icons/tick_grey.png"),
    /** . */
    UI_MAIN_COLUMN("icons/ui-main-column.png"),
    /** . */
    UI_TOOLTIP("icons/ui-tooltip.png"),
    /** . */
    UNIQUE_KEY("icons/unique_key.gif"),
    /** . */
    VERTICAL_LINE("icons/vertical_line.gif"),
    /** . */
    VERTICAL_LINE_DISABLED("icons/vertical_line_disabled.gif"),
    /** . */
    VIEW("icons/view.gif"),
    /** . */
    WARNING("icons/warning.gif"),
    /** . */
    WORD_3("icons/word_3.gif"),
    /** . */
    WRENCH("icons/wrench.png");

    private String path;

    private ICON(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
