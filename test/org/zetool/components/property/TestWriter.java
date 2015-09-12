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
package org.zetool.components.property;

import gui.propertysheet.PropertyTreeModel;
import gui.propertysheet.PropertyTreeNode;
import gui.propertysheet.types.BooleanProperty;
import gui.propertysheet.types.IntegerProperty;
import gui.propertysheet.types.StringProperty;
import java.io.IOException;
import java.io.StringWriter;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Checks that the the writer can write a property tree model to an xml file.
 * @author Jan-Philipp Kappmeier
 */
public class TestWriter {

    static final String ALIAS = "zp";

    @Test
    public void testWrite() throws IOException {
        PropertyTreeNode root = new PropertyTreeNode("root_name");
        PropertyTreeModel ptm = new PropertyTreeModel(root);
        PropertyTreeModelWriter writer = new PropertyTreeModelWriter();

        StringWriter sw = new StringWriter();
        writer.saveConfigFile(ptm, sw);

        // Expected output <zp name="root_name" useAsLocString="false" propertyName=""/>
        String output = sw.toString();
        assertThat(output.startsWith("<" + ALIAS), is(true));
        assertThat(output.contains("name=\"root_name\""), is(true));
        assertThat(output.contains("useAsLocString=\"false\""), is(true));
        assertThat(output.contains("propertyName=\"\""), is(true));
        assertThat(output.contains("\n"), is(false));
    }
    
    @Test
    public void testWriteWithName() throws IOException {
        final String propertyTreeModelName = "ptm_name_property";
        
        PropertyTreeNode root = new PropertyTreeNode("");
        StringProperty sp = new StringProperty();
        sp.setValue(propertyTreeModelName);
        root.addProperty(sp);
        PropertyTreeModel ptm = new PropertyTreeModel(root);

        PropertyTreeModelWriter writer = new PropertyTreeModelWriter();
        StringWriter sw = new StringWriter();
        writer.saveConfigFile(ptm, sw);

        // Expected output <zp name="root_name" useAsLocString="false" propertyName="ptm_name_property"/>
        String output = sw.toString();
        assertThat(output.contains("propertyName=\"" + propertyTreeModelName + "\""), is(true));
    }
    
    @Test
    public void testEmptyẂritesDataNodes() throws IOException {
        PropertyTreeNode root = new PropertyTreeNode("root_name");
        PropertyTreeModel ptm = new PropertyTreeModel(root);
        PropertyTreeNode child = new PropertyTreeNode("child");
        root.add(child);
        BooleanProperty bp = new BooleanProperty();
        bp.setName("b");
        child.addProperty(bp);
        IntegerProperty ip = new IntegerProperty();
        ip.setName("i");
        child.addProperty(ip);

        StringWriter sw = new StringWriter();
        PropertyTreeModelWriter writer = new PropertyTreeModelWriter(new PropertyConverterLibrary());
        writer.saveConfigFile(ptm, sw);

        String[] expected = {"<zp name=\"root_name\" useAsLocString=\"false\" propertyName=\"\">",
            "  <treeNode name=\"child\" useAsLocString=\"false\"/>",
            "</zp>"};
        assertThat(String.join("\n", expected), is(equalTo(sw.toString())));
    }
}
