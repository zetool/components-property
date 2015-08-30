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
import com.l2fprod.common.swing.plaf.ButtonBarUI;
import com.l2fprod.common.swing.plaf.blue.BlueishButtonBarUI;
import info.clearthought.layout.TableLayout;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
    private Localization loc = CommonLocalization.LOC;
    boolean useButtonBar = true;
    PropertySheetTableModel pstm = new PropertySheetTableModel();
    PropertySheetTable pst = new PropertySheetTable( pstm );
    PropertySheetPanel ps = new PropertySheetPanel( pst );

    JOptionsDialog parent;
    JButtonBar jbb;

    private final static String path = "./icons/";
    private final static String name = "open.png";
    Icon icon = new ImageIcon( path + name );
    
    public JOptionsDialog( PropertyTreeModel ptm ) {
        super( (Frame)null, "test" );

        parent = this;

        setSize( 650, 450 );
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation( (d.width - getSize().width) / 2, (d.height - getSize().height) / 2 );

        this.setLayout( new BorderLayout() );

        if( useButtonBar ) {
            jbb = new JButtonBar( 1 );
            add( jbb, BorderLayout.WEST );
            ButtonBarUI b = new BlueishButtonBarUI();
            jbb.setUI( b );
        }

        add( ps, BorderLayout.CENTER );
        init( ptm );

        ps.setDescriptionVisible( true );
        ps.setMode( 1 );

        ps.addPropertySheetChangeListener( new PropertyChangeListener() {

            @Override
            public void propertyChange( PropertyChangeEvent evt ) {
                BasicProperty p = (BasicProperty)evt.getSource();
                System.out.println( p.getName() );
            }
        });

        int space = 10;
        JPanel buttonPanel = new JPanel( );
        JButton btnOK = Button.newButton( loc.getString( "gui.OK" ), aclButton, "ok"  );
        JButton btnCancel = Button.newButton( loc.getString( "gui.Cancel" ), aclButton, "cancel" );
        double size2[][] = { {TableLayout.FILL, TableLayout.PREFERRED, space, TableLayout.PREFERRED, space }, {space, TableLayout.PREFERRED, space } };
        buttonPanel.setLayout( new TableLayout( size2 ) );
        buttonPanel.add( btnOK, "1,1" );
        buttonPanel.add( btnCancel, "3,1" );
        add( buttonPanel, BorderLayout.SOUTH );
    }

    final protected void init( PropertyTreeModel ptm ) {
        System.out.println( "Loading property " + ptm.getPropertyName() );
        PropertyTreeNode node = ptm.getRoot();

        jbb.removeAll();
        for( Property p : pstm.getProperties() ) {
            pstm.removeProperty( p );
        }

        // we are at root level
        if( useButtonBar ) {
            for( int i = 0; i < node.getChildCount(); i++ ) {
                PropertyTreeNode n = node.getChildAt( i );
                JButton newButton = new JPropertyButton( n );
                newButton.setIcon( icon ); // ZETIconSet.Open.icon() );
                jbb.add( newButton );
            }
        } else {
            for( BasicProperty<?> p : node.getProperties() )
                p.setCategory( "General" );
            for( int i = 0; i < node.getChildCount(); i++ ) {
                PropertyTreeNode n = node.getChildAt( i );
                addD( n, pstm, n.getDisplayName() );
            }
        }

        if( jbb != null && jbb.getComponents().length > 0 )
            ((JButton)jbb.getComponent( 0 )).doClick();
    }

    private ActionListener aclButton = new ActionListener() {
        @Override
        public void actionPerformed( ActionEvent e ) {
            if( e.getActionCommand().equals( "ok" ) ) {
                // Store results in the Property container
                for( Component c : jbb.getComponents() ) {
                    JPropertyButton pb = (JPropertyButton)c;
                    for( Property p : pb.pstm.getProperties() ) {
                        BasicProperty p2 = (BasicProperty)p;
                        System.out.println( p2.getName() + ": " + p2.getValue() );
                        p2.store();
                    }
                }
            }
            parent.setVisible( false );
        }
    };

    protected ActionListener getDefaultButtonsListener() {
        return aclButton;
    }


    private BasicProperty<?> newProperty( PropertyTreeNode n, String category ) {
        BasicProperty<?> def = new BasicProperty<>( n.getDisplayNameTag(), n.getDisplayName() );

        if( !category.isEmpty() )
            def.setCategory( category );
        return def;
    }

    private void addD( PropertyTreeNode node, PropertySheetTableModel pstm, String category ) {
        for( int i = 0; i < node.getChildCount(); i++ ) {
            PropertyTreeNode n = node.getChildAt( i );
            BasicProperty<?> def = newProperty( n, category );
            pstm.addProperty( def );
            addD( n, def, pstm );
        }

        for( BasicProperty<?> p : node.getProperties() ) {
            p.setCategory( category );
            pstm.addProperty( p );
        }
    }

    private void addD( PropertyTreeNode node, BasicProperty<?> property, PropertySheetTableModel pstm ) {
        for( int i = 0; i < node.getChildCount(); i++ ) {
            PropertyTreeNode n = node.getChildAt( i );
            BasicProperty<?> def = newProperty( n, "" );
            def.setParentProperty( property );
            property.addSubProperty( def );
            addD( n, def, pstm );
        }

        for( BasicProperty<?> p : node.getProperties() ) {
            property.addSubProperty( p );
            p.setParentProperty( property );
        }
    }

    private class JPropertyButton extends JButton {
        private static final long serialVersionUID = 1L;
        final PropertyTreeNode n;

        private PropertySheetTableModel pstm;

        JPropertyButton( final PropertyTreeNode n ) {
            super( n.getDisplayName() );
            this.n = n;

            pstm = new PropertySheetTableModel();
            for( BasicProperty<?> p : n.getProperties() ) {
                p.setCategory( "General" );
                pstm.addProperty( p );
            }

            for( int i = 0; i < n.getChildCount(); i++ ) {
                PropertyTreeNode node = n.getChildAt( i );
                addD( node, pstm, node.getDisplayName() );
            }

            this.addActionListener( new ActionListener() {
                @Override
                public void actionPerformed( ActionEvent e ) {
                    pst.setModel( pstm );
                    ps.setTable( pst );
                    ps.setMode( 1 );
                }
            });
        }
    }
}
