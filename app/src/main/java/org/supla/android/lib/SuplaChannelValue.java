package org.supla.android.lib;

/*
 Copyright (C) AC SOFTWARE SP. Z O.O.

 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public License
 as published by the Free Software Foundation; either version 2
 of the License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */


public class SuplaChannelValue {
    public static final int SUBV_TYPE_NOT_SET_OR_OFFLINE = 0;
    public static final int SUBV_TYPE_SENSOR = 1;
    public static final int SUBV_TYPE_ELECTRICITY_MEASUREMENTS = 2;
    public static final int SUBV_TYPE_IC_MEASUREMENTS = 3;

    public byte[] Value;
    public byte[] SubValue;
    public short SubValueType;

    public SuplaChannelValue() {
        // This constructor is used by native code
    }
}
