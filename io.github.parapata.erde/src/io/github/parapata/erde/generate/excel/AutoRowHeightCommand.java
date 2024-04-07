package io.github.parapata.erde.generate.excel;

import org.apache.poi.ss.usermodel.Row;
import org.jxls.area.Area;
import org.jxls.command.AbstractCommand;
import org.jxls.command.Command;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.common.Size;
import org.jxls.transform.poi.PoiTransformer;

/**
 * AutoRowHeightCommand.
 *
 * @author parapata
 * @since 1.0.16
 */
public class AutoRowHeightCommand extends AbstractCommand implements Command {

    public static final String COMMAND_NAME = "autoRowHeight";

    private Area area;

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public Command addArea(Area area) {
        String message = String.format("You can only add a single cell area to '%s' command!", COMMAND_NAME);
        if (getAreaList().size() >= 1) {
            throw new IllegalArgumentException(message);
        }
        if (area != null && area.getSize().getHeight() != 1 && area.getSize().getWidth() != 1) {
            throw new IllegalArgumentException(message);
        }
        this.area = area;
        return super.addArea(area);
    }

    @Override
    public Size applyAt(CellRef cellRef, Context context) {
        Size size = this.area.applyAt(cellRef, context);

        PoiTransformer transformer = (PoiTransformer) area.getTransformer();
        Row row = transformer.getWorkbook().getSheet(cellRef.getSheetName()).getRow(cellRef.getRow());
        row.setHeight((short) -1);

        return size;
    }
}
