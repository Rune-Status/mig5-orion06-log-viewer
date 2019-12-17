

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

class ImageRenderer extends DefaultTableCellRenderer
{

    @Override
    public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected,boolean hasFocus, int row, int column)
    {
        JLabel label = new JLabel();

        if (value!=null) {
        label.setHorizontalAlignment(JLabel.CENTER);
        //value is parameter which filled by byteOfImage
        label.setIcon(new ImageIcon((Image)value));
        }

        return label;
    }
}