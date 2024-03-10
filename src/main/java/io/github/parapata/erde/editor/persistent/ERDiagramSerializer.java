package io.github.parapata.erde.editor.persistent;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.github.parapata.erde.core.exception.AppException;
import io.github.parapata.erde.editor.diagram.model.RootModel;
import io.github.parapata.erde.editor.persistent.diagram.ErdeXmlModel;
import jakarta.xml.bind.JAXB;

/**
 * The diagram persistent.
 *
 * @author modified by parapata
 */
public class ERDiagramSerializer implements IERDiagramWriter, IERDiagramReader {

    /**
     * Constructor.
     */
    public ERDiagramSerializer() {
        super();
    }

    public RootModel read(InputStream is) {
        return toRootModel(is);
    }

    public InputStream write(RootModel rootModel) throws AppException {

        ErdeXmlModel model = toErde(rootModel);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            JAXB.marshal(model, out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new AppException(e);
        }
    }
}
