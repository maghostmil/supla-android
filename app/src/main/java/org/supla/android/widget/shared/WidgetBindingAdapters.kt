package org.supla.android.widget.shared
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

import android.view.View
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import org.supla.android.Trace
import org.supla.android.db.AuthProfileItem
import org.supla.android.db.DbItem
import org.supla.android.widget.shared.configuration.*

@BindingAdapter("visibility")
fun setViewVisibility(view: View, isVisible: Boolean) {
  if (isVisible) {
    view.visibility = View.VISIBLE
  } else {
    view.visibility = View.GONE
  }
}

@BindingAdapter("channels")
fun setSpinnerChannels(spinner: Spinner, items: List<SpinnerItem<DbItem>>?) {
  items?.let {
    // Reassigning the same adapter sets mOldSelectedPosition to INVALID_POSITION.
    // Without it, ItemSelectedListener was not called when moving from channels to scenes.
    spinner.adapter = spinner.adapter;
    (spinner.adapter as WidgetConfigurationChannelsSpinnerAdapter).postItems(it)
    if (items.size > 1) {
      spinner.setSelection(1)
    }
  }
}

@BindingAdapter("profiles")
fun setSpinnerProfiles(spinner: Spinner, items: List<AuthProfileItem>?) {
  items?.let {
    (spinner.adapter as WidgetConfigurationProfilesSpinnerAdapter).postItems(it)
  }
}

@BindingAdapter("actions")
fun setSpinnerActions(spinner: Spinner, items: List<WidgetAction>?) {
  items?.let {
    (spinner.adapter as WidgetConfigurationActionsSpinnerAdapter).postItems(it)
  }
}
