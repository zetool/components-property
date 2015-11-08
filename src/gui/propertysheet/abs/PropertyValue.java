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
package gui.propertysheet.abs;

/**
 *
 * @param <T>
 * @author Jan-Philipp Kappmeier
 */
public interface PropertyValue<T> {

    public String getDisplayName();

    public String getDisplayNameTag();

    public void setDisplayName(String text);

    public T getValue();


    /**
     * Returns a description for the property. If the description text is used as a tag, the method automatically
     * generates the actual description out of the tag.
     * @return the description text (possibly transformed by a tag)
     */
    public String getShortDescription();

    /**
     * Returns the value for the short description text. In case the description text is used as a tag, the method
     * returns the tag and not the transformed description (unlike {@link #getShortDescription() }).
     * @return the description tag if used as a tag, the description otherwise
     */
    public String getShortDescriptionTag();

    public void setShortDescription(String text);

    public String getName();

    public void setName(String name);
}
