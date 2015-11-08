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
package org.zetool.components.property;

import gui.propertysheet.PropertyTreeModel;
import gui.propertysheet.PropertyTreeNode;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Checks that the the loader can load a property tree model from an xml file.
 * @author Jan-Philipp Kappmeier
 */
public class TestReader {

    @Test
    public void testReadEmptyModel() throws IOException {
        String emptyModel = "<zp name=\"rootname\" useAsLocString=\"true\" propertyName=\"pnam\"/>";
        StringReader sr = new StringReader(emptyModel);
        PropertyTreeModelLoader loader = new PropertyTreeModelLoader();

        PropertyTreeModel ptm = loader.loadConfigFile(sr);
        assertThat(ptm.getPropertyName(), is(equalTo("pnam")));
        
        PropertyTreeNode root = ptm.getRoot();
        assertThat(root.getChildCount(), is(equalTo(0)));
        assertThat(root.getDisplayName(), is(equalTo("rootname")));
        assertThat(root.isUsedAsLocString(), is(true));
        assertThat(root.getProperties(), is(equalTo(Collections.EMPTY_LIST)));
    }

    @Test(expected=PropertyLoadException.class)
    public void testReadFails() throws IOException {
        String emptyModel = "<z name=\"rootname\" useAsLocString=\"true\" propertyName=\"pnam\"/>";
        StringReader sr = new StringReader(emptyModel);
        PropertyTreeModelLoader loader = new PropertyTreeModelLoader();

        loader.loadConfigFile(sr);
    }

    @Test
    public void testEmptyLoadsTree() throws IOException {
        String[] model = {"<zp name=\"rootname\" useAsLocString=\"true\" propertyName=\"pnam\">",
        "<treeNode name=\"child\" useAsLocString=\"false\">", "</treeNode>", "</zp>"};
        assertEmpty(String.join("\n", model));
    }
    
    @Test
    public void testEmptyIgnoresDataNodes() throws IOException {
        String[] model = {"<zp name=\"rootname\" useAsLocString=\"true\" propertyName=\"pnam\">",
        "<treeNode name=\"child\" useAsLocString=\"false\">",
        "<intNode name=\"\" useAsLocString=\"false\" information=\"\" parameter=\"\">5</intNode>",
        "</treeNode>", "</zp>"};
        assertEmpty(String.join("\n", model));
    }
    
    /**
     * Asserts that a definition is loaded to a model containing exactly one child node.
     * @param model 
     */
    private void assertEmpty(String model) throws PropertyLoadException {
        StringReader sr = new StringReader(String.join("\n", model));
        PropertyTreeModelLoader loader = new PropertyTreeModelLoader(new PropertyConverterLibrary());

        PropertyTreeModel ptm = loader.loadConfigFile(sr);
        
        PropertyTreeNode root = ptm.getRoot();
        assertThat(root.getChildCount(), is(equalTo(1)));
        
        PropertyTreeNode child = root.getChildAt(0);
        assertThat(child.getChildCount(), is(equalTo(0)));
    }
}
