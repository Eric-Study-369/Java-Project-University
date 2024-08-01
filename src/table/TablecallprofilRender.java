
package table;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import model.ModelEmployee;

public class TablecallprofilRender implements TableCellRenderer {

    private final TableCellRenderer oldCellRenderer;

    public TablecallprofilRender(JTable table) {
        oldCellRenderer = table.getDefaultRenderer(Object.class);
    }

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
        Component com = oldCellRenderer.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
        TableCellProfile cell = new TableCellProfile((ModelEmployee) o, com.getFont());
        cell.setBackground(com.getBackground());
        return cell;
    }
}
