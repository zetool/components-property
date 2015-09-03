/* zet evacuation tool copyright (c) 2007-15 zet evacuation team
 *
 * This program is free software; you can redistribute it and/or
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package gui.propertysheet;

import com.l2fprod.common.propertysheet.Property;
import com.l2fprod.common.propertysheet.PropertySheetPanel;
import com.l2fprod.common.propertysheet.PropertySheetTable;
import com.l2fprod.common.propertysheet.PropertySheetTableModel;
import com.l2fprod.common.swing.JButtonBar;
import com.l2fprod.common.swing.plaf.blue.BlueishButtonBarUI;
import ds.PropertyContainer;
import info.clearthought.layout.TableLayout;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import org.zetool.common.localization.CommonLocalization;
import org.zetool.common.localization.Localization;
import org.zetool.components.framework.Button;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class JOptionsDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private final Localization loc = CommonLocalization.LOC;
    private PropertySheetTableModel propertyTableModel = new PropertySheetTableModel();
    private final PropertySheetPanel propertyPanel = new PropertySheetPanel(new PropertySheetTable(propertyTableModel));

    private JButtonBar buttonBar;

    private final static String path = "./icons/";
    private final static String name = "open.png";
    Icon icon = new ImageIcon(path + name);

    public JOptionsDialog(PropertyTreeModel ptm) {
        this(ptm, true);
    }
    
    public JOptionsDialog(PropertyTreeModel ptm, boolean useButtonBar) {
        super((Frame) null, "test");

        setSize(650, 450);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((d.width - getSize().width) / 2, (d.height - getSize().height) / 2);

        this.setLayout(new BorderLayout());

        if(useButtonBar) {
            initButtonBar();        
        }
        initProperties(ptm);
        initNavigation();
    }

    private void initButtonBar() {
        buttonBar = new JButtonBar(1);
        add(buttonBar, BorderLayout.WEST);
        buttonBar.setUI(new BlueishButtonBarUI());
    }

    private void initProperties(PropertyTreeModel ptm) {
        loadProperties(ptm);
        propertyPanel.setDescriptionVisible(true);
        propertyPanel.setMode(1);
        add(propertyPanel, BorderLayout.CENTER);
    }

    private void initNavigation() {
        int space = 10;
        JPanel buttonPanel = new JPanel();
        JButton okButton = Button.newButton(loc.getString("gui.OK"), buttonListener, "ok");
        JButton cancelButton = Button.newButton(loc.getString("gui.Cancel"), buttonListener, "cancel");
        double size2[][] = {{TableLayout.FILL, TableLayout.PREFERRED, space, TableLayout.PREFERRED, space}, {space, TableLayout.PREFERRED, space}};
        buttonPanel.setLayout(new TableLayout(size2));
        buttonPanel.add(okButton, "1,1");
        buttonPanel.add(cancelButton, "3,1");
        add(buttonPanel, BorderLayout.SOUTH);
   }

    protected final void loadProperties(PropertyTreeModel ptm) {
        System.out.println("Loading property " + ptm.getPropertyName());
        PropertyTreeNode root = ptm.getRoot();

        buttonBar.removeAll();
        for (Property p : propertyTableModel.getProperties()) {
            propertyTableModel.removeProperty(p);
        }

        // we are at root level
        if (useButtonBar()) {
            addRootLevelChildrenToButtonBar(root);
            if (root.getChildCount() > 0) {
                ((JButton) buttonBar.getComponent(0)).doClick();
            }
        } else {
            for (GenericProperty p : root.getProperties()) {
                p.setCategory("General");
            }
            for (int i = 0; i < root.getChildCount(); i++) {
                PropertyTreeNode n = root.getChildAt(i);
                addPropertyRootLevel(n, n.getDisplayName());
            }
        }
    }
    
    private void addRootLevelChildrenToButtonBar(PropertyTreeNode root) {
        for (int i = 0; i < root.getChildCount(); i++) {
            PropertyTreeNode child = root.getChildAt(i);
            JButton newButton = new JPropertyButton(child);
            newButton.setIcon(icon); // ZETIconSet.Open.icon() );
            buttonBar.add(newButton);
        }
    }

    private final ActionListener buttonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("ok")) {
                // Store results in the Property container
                for (Component c : buttonBar.getComponents()) {
                    JPropertyButton pb = (JPropertyButton) c;
                    for (Property p : pb.pstm.getProperties()) {
                        BasicProperty p2 = (BasicProperty) p;
                        System.out.println(p2.getName() + ": " + p2.getValue());
                        PropertyContainer.getGlobal().store(p2);
                    }
                }
            }
            JOptionsDialog.this.setVisible(false);
        }
    };

    protected ActionListener getDefaultButtonsListener() {
        return buttonListener;
    }

    private boolean useButtonBar() {
        return buttonBar != null;
    }
    
    private BasicProperty<?> newProperty(PropertyTreeNode n, String category) {
        BasicProperty<?> def = new BasicProperty<>(n.getDisplayNameTag(), n.getDisplayName());

        if (!category.isEmpty()) {
            def.setCategory(category);
        }
        return def;
    }

    /**
     * Displays a (sub-)tree of properties in the {@link #propertyPanel} starting at a given node.
     * @param node the root node that is displayed.
     * @param category the heading for the group belonging to the {@code node}
     */
    private void addPropertyRootLevel(PropertyTreeNode node, String category) {        
        for( ChildPropertyTuple tuple : new ChildPropertyIterator(node, category)) {
            propertyTableModel.addProperty(tuple.property);
            addProperty(tuple.child, tuple.property);
        }

        for (GenericProperty p : node.getProperties()) {
            p.setCategory(category);
            propertyTableModel.addProperty(p);
        }
    }

    /**
     * Displays a (sub-)tree of properties in the {@link #propertyPanel} under an existing property.
     * @param node the node from which the properties are displayed
     * @param property the parent property belonging to the {@code node}
     */
    private void addProperty(PropertyTreeNode node, BasicProperty<?> property) {
        for( ChildPropertyTuple tuple : new ChildPropertyIterator(node, "")) {
            tuple.property.setParentProperty(property);
            property.addSubProperty(tuple.property);
            addProperty(tuple.child, tuple.property);
        }

        for (GenericProperty p : node.getProperties()) {
            property.addSubProperty(p);
            p.setParentProperty(property);
        }
    }


    private static class ChildPropertyTuple {
        private final PropertyTreeNode child;
        private final BasicProperty<?> property;

        public ChildPropertyTuple(PropertyTreeNode child, BasicProperty<?> def) {
            this.child = child;
            this.property = def;
        }
        
    }
    private class ChildPropertyIterator implements Iterable<ChildPropertyTuple> {
        private final PropertyTreeNode node;
        private final String category;

        public ChildPropertyIterator(PropertyTreeNode node, String category) {
            this.node = node;
            this.category = category;
        }

        @Override
        public Iterator<ChildPropertyTuple> iterator() {
            return new Iterator<ChildPropertyTuple>() {
                private int index = 0;

                @Override
                public boolean hasNext() {
                    return index++ < node.getChildCount();
                }

                @Override
                public ChildPropertyTuple next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    final PropertyTreeNode child = node.getChildAt(index);
                    return new ChildPropertyTuple(child, newProperty(child, category));
                }
            };
        }
    }
    
    private class JPropertyButton extends JButton {

        private static final long serialVersionUID = 1L;
        final PropertyTreeNode n;

        private PropertySheetTableModel pstm;

        JPropertyButton(final PropertyTreeNode n) {
            super(n.getDisplayName());
            this.n = n;

            pstm = new PropertySheetTableModel();

            for (int i = 0; i < n.getChildCount(); i++) {
                PropertyTreeNode node = n.getChildAt(i);
                addPropertyRootLevel(node, node.getDisplayName());
            }
            for (GenericProperty p : n.getProperties()) {
                p.setCategory("General");
                pstm.addProperty(p);
            }

            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    propertyTableModel = pstm;
                    propertyPanel.setTable(new PropertySheetTable(pstm));
                    propertyPanel.setMode(1);
                }
            });
        }
    }
}
