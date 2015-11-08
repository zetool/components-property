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

import java.io.IOException;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.zetool.components.property.SerializationMatchers.notDeserializable;
import static org.zetool.components.property.SerializationMatchers.notSerializable;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestPropertyLoadException {
    
    @Test
    public void testSerializationFails() throws IOException {
         PropertyLoadException ex = new PropertyLoadException(null, null);
         assertThat(ex, is(notSerializable()));
    }
    
    @Test
    public void testDeserializationFails() {
        assertThat(PropertyLoadException.class, is(notDeserializable()));
    }

}
