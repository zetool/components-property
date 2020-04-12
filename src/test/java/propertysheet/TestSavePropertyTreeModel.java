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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package propertysheet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import gui.propertysheet.GenericProperty;
import gui.propertysheet.JOptionsDialog;
import gui.propertysheet.PropertyTreeModel;
import gui.propertysheet.PropertyTreeNode;
import gui.propertysheet.types.BooleanProperty;
import gui.propertysheet.types.ColorProperty;
import gui.propertysheet.types.EnumProperty;
import gui.propertysheet.types.IntegerRangeProperty;
import gui.propertysheet.types.StringListProperty;
import gui.propertysheet.types.StringProperty;
import org.zetool.components.property.PropertyTreeModelLoader;
import org.zetool.components.property.PropertyTreeModelWriter;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestSavePropertyTreeModel {

    public enum PropertyEnum {

        EnumA, EnumB;
    }

    public enum PropertyEnumAlt {

        EnumA, EnumB;
    }

    @Test
    public void testRead() throws IOException {
        PropertyTreeModelLoader loader = new PropertyTreeModelLoader();
        InputStream is = TestSavePropertyTreeModel.class.getResourceAsStream("/ptm.txt");
        PropertyTreeModel ptm = loader.loadConfigFile(new InputStreamReader(is, "UTF-8"));

        List<PropertyTreeNode> nodes = new ArrayList<>();
        List<List<String>> properties = new ArrayList<>();

        iterateNode(ptm.getRoot(), nodes, properties);

        // Assert
        assertThat(nodes, hasSize(4));
        assertThat(properties, hasSize(4));

        // Root
        assertNode(nodes.get(0), "my_displayname", false);
        assertThat(properties.get(0), hasSize(0));

        // Node 1        
        assertNode(nodes.get(1), "child1", false);
        String[] expectedPropertiesChild1 = new String[]{
            "false cbp1-short cbp1-short cbp1",
            "EnumA enum-short enum-short enum",
            "20 irp-short irp-short irp-dn",
            "java.awt.Color[r=255,g=0,b=0] color-desc color-desc mycolor"};
        assertThat(properties.get(1), contains(expectedPropertiesChild1));

        // Node 2
        assertNode(nodes.get(2), "child2", false);
        String[] expectedPropertiesChild2 = new String[]{
            "true cbp2-short cbp2-short cbp2",
            "[newly created string] stringshort stringshort stringlist"};
        assertThat(properties.get(2), contains(expectedPropertiesChild2));

        // Node 3
        assertNode(nodes.get(3), "child3", false);
        String[] expectedPropertiesChild3 = new String[]{"false cbp3-short cbp3-short cbp3"};
        assertThat(properties.get(3), contains(expectedPropertiesChild3));
    }

    private void assertNode(PropertyTreeNode node, String expectedDisplayName, boolean expectedLocString) {
        assertThat(node.getDisplayName(), is(equalTo(expectedDisplayName)));
        assertThat(node.isUsedAsLocString(), is(equalTo(expectedLocString)));
    }

    public void iterateNode(PropertyTreeNode node, List<PropertyTreeNode> nodes, List<List<String>> properties) {
        nodes.add(node);
        List<String> nodeProperties = new ArrayList<>();
        properties.add(nodeProperties);
        for (int i = 0; i < node.getChildCount(); ++i) {
            PropertyTreeNode childNode = node.getChildAt(i);
            iterateNode(childNode, nodes, properties);
        }
        for (GenericProperty bp : node.getProperties()) {
            String out = String.format("%s %s %s %s", bp.getValue(), bp.getShortDescription(), bp.getShortDescriptionTag(), bp.getDisplayName(), bp.getDisplayNameTag());
            nodeProperties.add(out);
        }

    }

    @Test
    public void writeModel() throws IOException {
        PropertyTreeModelWriter writer = new PropertyTreeModelWriter();
        FileWriter fileWriter = new FileWriter(new File("./ptm.txt"));
        writer.saveConfigFile(createFixture(), fileWriter);
    }
    
    //TODO: write read test

    @Test
    public void displayModelDialog() throws IOException {
        JOptionsDialog d = new JOptionsDialog(createFixture());
        d.setVisible(true);
    }

    public PropertyTreeModel createFixture() throws IOException {
        PropertyTreeNode root = new PropertyTreeNode("my_root_name");
        PropertyTreeModel ptm = new PropertyTreeModel(root);
        root.setDisplayName("my_displayname");

        StringProperty sp = new StringProperty();
        sp.setValue("string_property_name");
        root.addProperty(sp);

        BooleanProperty bp1 = new BooleanProperty();
        root.addProperty(bp1);
        BooleanProperty bp2 = new BooleanProperty();
        bp2.setValue(true);
        root.addProperty(bp2);

        PropertyTreeNode child1 = new PropertyTreeNode("child1");
        root.add(child1);
        BooleanProperty cbp1 = new BooleanProperty();
        cbp1.setDisplayName("cbp1");
        cbp1.setShortDescription("cbp1-short");
        cbp1.setName("cbp1-name");
        root.addProperty(cbp1);
        child1.addProperty(cbp1);

        PropertyTreeNode child2 = new PropertyTreeNode("child2");
        child1.add(child2);

        BooleanProperty cbp2 = new BooleanProperty();
        cbp2.setDisplayName("cbp2");
        cbp2.setShortDescription("cbp2-short");
        cbp2.setName("cbp2-name");
        cbp2.setValue(true);
        child2.addProperty(cbp2);

        StringListProperty slp = new StringListProperty();
        slp.add("string1");
        slp.add("kpd");
        ArrayList<String> newStrings = new ArrayList<>();
        newStrings.add("newly created string");
        newStrings.add("a second string");
        slp.setValue(newStrings);
        slp.setDisplayName("stringlist");
        slp.setShortDescription("stringshort");
        slp.setName("mystringlist");
        child2.addProperty(slp);

        EnumProperty ep = new EnumProperty();
        ep.setDisplayName("enum");
        ep.setShortDescription("enum-short");
        ep.setName("enum");
        ep.setValue(PropertyEnum.EnumA);
        child1.addProperty(ep);

        IntegerRangeProperty irp = new IntegerRangeProperty();
        irp.setMinValue(5);
        irp.setMaxValue(45);
        irp.setMinorTick(1);
        irp.setMajorTick(5);
        irp.setPropertyValue(20);
        irp.setName("irp");
        irp.setDisplayName("irp-dn");
        irp.setShortDescription("irp-short");
        child1.addProperty(irp);

        ColorProperty cp = new ColorProperty();
        cp.setDisplayName("mycolor");
        cp.setShortDescription("color-desc");
        cp.setName("mycolor-name");
        cp.setValue(Color.RED);
        child1.addProperty(cp);

        PropertyTreeNode child3 = new PropertyTreeNode("child3");
        root.add(child3);
        BooleanProperty cbp3 = new BooleanProperty();
        cbp3.setDisplayName("cbp3");
        cbp3.setShortDescription("cbp3-short");
        cbp3.setName("cbp3-name");
        root.addProperty(cbp3);
        child3.addProperty(cbp3);

        return ptm;
    }
}
