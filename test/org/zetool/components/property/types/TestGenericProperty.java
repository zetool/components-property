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
import java.io.IOException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import org.jmock.Expectations;
import org.jmock.Mockery;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.zetool.common.localization.Localization;
import static org.zetool.components.property.SerializationMatchers.notDeserializable;
import static org.zetool.components.property.SerializationMatchers.notSerializable;

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

    public static <T extends GenericProperty> void testEqual(T sp1, T sp2) {
        GenericProperty gp = new GenericProperty();
        populateEqually(gp, sp1, sp2);
        assertThat(sp1.equals(sp1), is(true));
        assertThat(sp1.equals(null), is(false));
        assertThat(sp1.equals(sp2), is(true));
        if( sp1.getClass() != GenericProperty.class ) {
            assertThat(sp1.equals(gp), is(false));
            assertThat(gp.equals(sp1), is(false));
        }
    }

    public static <T extends GenericProperty> void testUnEqual(T gp, T sp) {
        populateEqually(gp, sp);
        sp.setName("different name");
        assertThat(gp.equals(sp), is(false));
    }

    private static void populateEqually(GenericProperty ... gps) {
        for(GenericProperty gp : gps ) {
            gp.setName("name");
            gp.setDisplayName("displayname");
            gp.setShortDescription("description");
        }
    }
    
    @Test
    public void testLocalization() {
        GenericProperty gp = new GenericProperty();
        gp.setDisplayName("display.name");
        gp.setShortDescription("description.tag");
        
        Mockery context = new Mockery();
        final Localization loc = context.mock(Localization.class);
        gp.setLoc(loc);
        
        gp.useAsLocString(true);
        
        context.checking(new Expectations() {{
            allowing (loc).getString("display.name");
            will(returnValue("Display Name"));
            allowing (loc).getString("description.tag");
            will(returnValue("Description"));
        }});
        
        assertThat(gp.getDisplayName(), is(equalTo("Display Name")));
        assertThat(gp.getDisplayNameTag(), is(equalTo("display.name")));
        
        assertThat(gp.getShortDescription(), is(equalTo("Description")));
        assertThat(gp.getShortDescriptionTag(), is(equalTo("description.tag")));
    }
    
    @Test
    public void testHashCode() {
        GenericProperty gp = new GenericProperty();
        GenericProperty gp2 = new GenericProperty();
        
        assertThat(gp.hashCode(), is(equalTo(gp2.hashCode())));
        
        gp2.setDisplayName("display.name");
        assertThat(gp.hashCode(), is(equalTo(gp2.hashCode())));

        gp2.setName("property.name");
        assertThat(gp.hashCode(), is(not(equalTo(gp2.hashCode()))));
    }
    
    @Test
    public void testLocalizationFallbackToDefault() {
        GenericProperty gp = new GenericProperty();
        gp.setDisplayName("display.name");
        gp.setShortDescription("description.tag");
        
        Mockery context = new Mockery();
        final Localization loc = context.mock(Localization.class);
        gp.setLoc(loc);
        
        gp.useAsLocString(true);
        
        context.checking(new Expectations() {{
            allowing (loc).getString("display.name");
            allowing (loc).getString("description.tag");
        }});
        
        assertThat(gp.getDisplayName(), is(equalTo("")));
        
        assertThat(gp.getDisplayName(), is(equalTo("")));
        assertThat(gp.getShortDescription(), is(equalTo("")));
    }
    @Test
    public void testSerializationFails() throws IOException {
         GenericProperty ex = new GenericProperty();
         assertThat(ex, is(notSerializable()));
    }

    @Test
    public void testDeserializationFails() {
        assertThat(GenericProperty.class, is(notDeserializable()));
    }
}
