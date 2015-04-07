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

/**
 * 	// This class represents the contents of a diving gas cylinder.
 *
 */
public class CylinderContents implements Serializable{
	/**
	 * sUID
	 */
	private static final long serialVersionUID = 1377980628200745116L;
	
	/**
	 * FO2 of the mix
	 */
	private Double fO2;  
	
	/**
	 * fHe of the mix
	 */
	private Double fHe; // FHe of the mix
	
	
	/**
	 *  Fill pressure (bar)
	 */
	private Double pressure;
	
	public Double getfHe() {
		return fHe;
	}
	public Double getfO2() {
		return fO2;
	}
	public Double getPressure() {
		return pressure;
	}
	public void setfHe(Double fHe) {
		this.fHe = fHe;
	}
	public void setfO2(Double fO2) {
		this.fO2 = fO2;
	}
	public void setPressure(Double pressure) {
		this.pressure = pressure;
	}
}
