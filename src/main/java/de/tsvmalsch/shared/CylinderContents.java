package de.tsvmalsch.shared;

import java.io.Serializable;

/*  DiveGas for Google Android - Nitrox and Trimix gas blending using VdW
 Copyright (C) 2009 David Pye    <davidmpye@gmail.com  - UI + porting 
 Nigel Hewitt <nigelh@combro.co.uk> - Algorithms 

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

public class CylinderContents implements Serializable{
	// This class represents the contents of a diving gas cylinder.
	public Double FO2; // FO2 of the mix
	public Double FHe; // FHe of the mix
	public Double Pressure; // Fill pressure (bar)
}
