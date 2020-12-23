package io.github.erde.dialect;

import java.util.Arrays;
import java.util.List;

import io.github.erde.IMessages;
import io.github.erde.dialect.type.IColumnType;
import io.github.erde.dialect.type.IIndexType;
import io.github.erde.dialect.type.IndexType;

/**
 * AbstractDialect.
 *
 * @author modified by parapata
 */
public abstract class AbstractDialect implements IDialect, IMessages {

    private List<IColumnType> types;

    private boolean autoIncrement;
    private boolean schema;
    private boolean drop;
    private boolean comment;

    protected List<IIndexType> indexTypes = Arrays.asList(IndexType.values());
    protected String separator = ";";

    public AbstractDialect(List<IColumnType> types) {
        this.types = types;
    }

    @Override
    public List<IColumnType> getColumnTypes() {
        return types;
    }

    @Override
    public List<IIndexType> getIndexTypes() {
        return indexTypes;
    }

    @Override
    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    @Override
    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    @Override
    public String getSeparator() {
        return separator;
    }

    @Override
    public void setSeparator(String separator) {
        this.separator = separator;
    }

    @Override
    public boolean isSchema() {
        return schema;
    }

    @Override
    public void setSchema(boolean schema) {
        this.schema = schema;
    }

    @Override
    public boolean isDrop() {
        return drop;
    }

    @Override
    public void setDrop(boolean drop) {
        this.drop = drop;
    }

    @Override
    public boolean isComment() {
        return comment;
    }

    @Override
    public void setComment(boolean comment) {
        this.comment = comment;
    }
}
