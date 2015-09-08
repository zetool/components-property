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

import com.l2fprod.common.propertysheet.DefaultProperty;
import gui.propertysheet.abs.PropertyElement;
import java.util.Objects;
import org.zetool.common.localization.CommonLocalization;
import org.zetool.common.localization.Localization;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class GenericProperty extends DefaultProperty implements PropertyElement {

    public GenericProperty() {
        super();
        setDisplayName("");
        super.setShortDescription("");
    }

    boolean useAsLocString = false;
    private Localization loc = CommonLocalization.LOC;

    @Override
    public boolean isUsedAsLocString() {
        return useAsLocString;
    }

    @Override
    public void useAsLocString(boolean useAsLocString) {
        this.useAsLocString = useAsLocString;
    }

    @Override
    public String getDisplayNameTag() {
        return super.getDisplayName();
    }

    /**
     * Returns the detailed description for the property.
     *
     * @return the detailed description for the property
     */
    @Override
    public String getDisplayName() {
        return isUsedAsLocString() ? loc.getString(super.getDisplayName()) : super.getDisplayName();
    }

    @Override
    public String getShortDescription() {
        return isUsedAsLocString() ? loc.getString(super.getShortDescription()) : super.getShortDescription();
    }

    public String getShortDescriptionTag() {
        return super.getShortDescription();
    }

    /**
     * Sets the description for the property. Note that you cannot change the description if it
     * shall be used as a tag for localized string. In that case, you can only change the tag.
     *
     * @param text the description
     */
    @Override
    public void setShortDescription(String text) {
        super.setShortDescription(text);
        
    }
}
