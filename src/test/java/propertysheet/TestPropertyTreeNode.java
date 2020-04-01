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

import gui.propertysheet.PropertyTreeNode;
import gui.propertysheet.types.BooleanProperty;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestPropertyTreeNode {
    private PropertyTreeNode node;
    
    @Before
    public void setUp() {
        node = new PropertyTreeNode("nodename");
        node.addProperty(new BooleanProperty());
    }
  
    @Test
    public void testAddProperties() {
        assertThat(node.getProperties().size(), is(equalTo(1)));
    }
    
    @Test
    public void testClearProperties() {
        node.clearProperties();
        assertThat(node.getProperties().size(), is(equalTo(0)));
    }
}
