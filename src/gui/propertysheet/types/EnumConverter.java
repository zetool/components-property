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
package gui.propertysheet.types;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import gui.propertysheet.abs.AbstractPropertyConverter;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A converter that reads and writes {@link QualitySettingProperty} to
 * XML-files.
 * @author Jan-Philipp Kappmeier
 */
public class EnumConverter extends AbstractPropertyConverter<EnumProperty, Enum> {

	@Override
	public String getNodeName() {
		return "enumNode";
	}

	@Override
	public void createNewProp() {
		prop = new EnumProperty();
	}

	@Override
	public void writeValue( MarshallingContext context ) {
		context.convertAnother( prop.getType() );
                context.convertAnother( "#");
		context.convertAnother( prop.getValue() );
	}

	@Override
	public void readValue( UnmarshallingContext context ) {
            String sa = new String();
            String s = (String)context.convertAnother( sa, String.class );
            StringTokenizer st = new StringTokenizer(s, "#");
            String classname = st.nextToken();
            String enumtype = st.nextToken();
            
            Class a;
            try {
                a = Class.forName(classname);
                Enum finalEnum = Enum.valueOf(a, enumtype);
                prop.setValue(finalEnum);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(EnumConverter.class.getName()).log(Level.SEVERE, null, ex);
                prop.setValue( null );
            }
	}

        @Override
	public boolean canConvert( Class type ) {
		return type.equals(EnumProperty.class );
	}

}
