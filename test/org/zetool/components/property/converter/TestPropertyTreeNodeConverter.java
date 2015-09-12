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
package org.zetool.components.property.converter;

import com.thoughtworks.xstream.XStream;
import gui.propertysheet.PropertyTreeNode;
import gui.propertysheet.abs.PropertyTreeNodeConverter;
import gui.propertysheet.types.StringProperty;
import java.io.StringWriter;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.zetool.components.property.PropertyConverterLibrary;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestPropertyTreeNodeConverter {

    private static final int COUNT = 3;
    private XStream xstream;

    @Before
    public void initConverter() {
        xstream = new XStream();
    }

    @Test
    public void testChildConversion() {
        xstream.registerConverter(new PropertyTreeNodeConverter(new PropertyConverterLibrary()));
        PropertyTreeNode ptm = new PropertyTreeNode("testnode");
        for (int i = 1; i <= COUNT; ++i) {
            ptm.add(new PropertyTreeNode("childnode" + i));
        }

        StringWriter sw = new StringWriter();
        xstream.toXML(ptm, sw);
        
        String[] lines = sw.toString().split("\n");
        for( int i = 1; i <= COUNT; ++i ) {
            assertThat(assertContains(lines, "<treeNode name=\"childnode" + i + "\" useAsLocString=\"false\"/>"), is(true));
        }
    }
    
    @Test
    public void testPropertyConversionEmpty() {
        assertContainsStringProperty(new PropertyConverterLibrary(), false);
    }
    
    @Test
    public void testPropertyConversion() {
        assertContainsStringProperty(PropertyConverterLibrary.createDefaultConverters(), true);
    }
    
    /**
     * Creates a node with some properties and asserts that the xml output contains or does not contain the 
     * parameters.
     * @param library the set of converter to be used
     * @param shouldContain decides if the output should or should not contain the property.
     */
    private void assertContainsStringProperty(PropertyConverterLibrary library, boolean shouldContain) {
        PropertyTreeNode ptm = new PropertyTreeNode("testnode");
        for( int i = 1; i <= COUNT; ++i ) {
            StringProperty sp = new StringProperty();
            sp.setValue("string-property" + i);
            sp.setName("string-property-name" + i);            
            ptm.addProperty(sp);
        }
        
        StringWriter sw = new StringWriter();
        xstream.registerConverter(new PropertyTreeNodeConverter(library));
        xstream.toXML(ptm, sw);
        
        String[] lines = sw.toString().split("\n");
        for( int i = 1; i <= COUNT; ++i ) {
            assertThat(assertContains(lines, "<stringNode name=\"\" useAsLocString=\"false\" information=\"\" parameter=\"string-property-name" + i + "\">string-property" + i + "</stringNode>"), is(shouldContain));
        }
    }

    /**
     * Asserts that one of the lines contains the given string.
     * @param lines
     * @param val 
     */
    private boolean assertContains(String[] lines, String val) {
        for(String line : lines ) {
            if( line.contains(val)) {
                return true;
            }
        }
        return false;
    }
}
