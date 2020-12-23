package io.github.erde.dialect.loader;

import java.sql.SQLException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;

import io.github.erde.editor.diagram.model.RootModel;

/**
 * ISchemaLoader.
 * 
 * @author modified by parapata
 */
public interface ISchemaLoader {

    void loadSchema(List<String> tableNames, RootModel root, IProgressMonitor monitor)
            throws SQLException, InterruptedException;
}
