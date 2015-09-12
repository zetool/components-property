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
package org.zetool.components.property.types;

import gui.propertysheet.GenericProperty;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestGenericProperty {
    
    @Test
    public void testEqual() {
        TestGenericProperty.testEqual(new GenericProperty(), new GenericProperty());
    }
    
    @Test
    public void testNotEqual() {
        TestGenericProperty.testUnEqual(new GenericProperty(), new GenericProperty());
    }

    public static <T extends GenericProperty> void testEqual(T gp, T sp) {
        populateEqually(gp, sp);
        assertThat(gp.equals(sp), is(true));
    }

    public static <T extends GenericProperty> void testNotEqual(GenericProperty gp, T sp) {
        populateEqually(gp, sp);
        assertThat(gp.equals(sp), is(false));
    }

    public static <T extends GenericProperty> void testUnEqual(T gp, T sp) {
        populateEqually(gp, sp);
        sp.setName("different name");
        assertThat(gp.equals(sp), is(false));
    }

    private static void populateEqually(GenericProperty gp, GenericProperty sp) {
        gp.setName("name");
        gp.setDisplayName("displayname");
        gp.setShortDescription("description");
        sp.setName("name");
        sp.setDisplayName("displayname");
        sp.setShortDescription("description");
    }
    
    
}
