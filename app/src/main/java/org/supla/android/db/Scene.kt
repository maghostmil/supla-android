package org.supla.android.db

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

import android.content.ContentValues
import android.database.Cursor
import org.supla.android.R
import org.supla.android.images.ImageCache
import org.supla.android.images.ImageId
import org.supla.android.lib.SuplaScene
import org.supla.android.lib.SuplaSceneState
import java.text.DateFormat
import java.util.Date

data class Scene(
  var profileId: Long = 0,
  var sceneId: Int = 0,
  var locationId: Int = 0,
  var altIcon: Int = 0,
  var userIcon: Int = 0,
  var caption: String = "",
  var sortOrder: Int = 0,
  var startedAt: Date? = null,
  var estimatedEndDate: Date? = null,
  var initiatorId: Int? = null,
  var initiatorName: String? = null,
  var visible: Int = 0
) : DbItem() {

  override fun AssignCursorData(cur: Cursor) {
    id = cur.getLong(
      cur.getColumnIndexOrThrow(SuplaContract.SceneEntry._ID)
    )
    sceneId = cur.getInt(cur.getColumnIndexOrThrow(SuplaContract.SceneEntry.COLUMN_NAME_SCENEID))
    locationId =
      cur.getInt(cur.getColumnIndexOrThrow(SuplaContract.SceneEntry.COLUMN_NAME_LOCATIONID))
    altIcon = cur.getInt(cur.getColumnIndexOrThrow(SuplaContract.SceneEntry.COLUMN_NAME_ALTICON))
    userIcon = cur.getInt(cur.getColumnIndexOrThrow(SuplaContract.SceneEntry.COLUMN_NAME_USERICON))
    caption = cur.getString(cur.getColumnIndexOrThrow(SuplaContract.SceneEntry.COLUMN_NAME_CAPTION))

    var idx = cur.getColumnIndexOrThrow(SuplaContract.SceneEntry.COLUMN_NAME_STARTED_AT)
    if (!cur.isNull(idx)) {
      startedAt = dateFromString(cur.getString(idx))
    }
    idx = cur.getColumnIndexOrThrow(SuplaContract.SceneEntry.COLUMN_NAME_EST_END_DATE)
    if (!cur.isNull(idx)) {
      estimatedEndDate = dateFromString(cur.getString(idx))
    }
    idx = cur.getColumnIndexOrThrow(SuplaContract.SceneEntry.COLUMN_NAME_INITIATOR_ID)
    if (!cur.isNull(idx)) {
      initiatorId = cur.getInt(idx)
    }
    idx = cur.getColumnIndexOrThrow(SuplaContract.SceneEntry.COLUMN_NAME_INITIATOR_NAME)
    if (!cur.isNull(idx)) {
      initiatorName = cur.getString(idx)
    }
    sortOrder =
      cur.getInt(cur.getColumnIndexOrThrow(SuplaContract.SceneEntry.COLUMN_NAME_SORT_ORDER))
    profileId =
      cur.getLong(cur.getColumnIndexOrThrow(SuplaContract.SceneEntry.COLUMN_NAME_PROFILEID))
    visible = cur.getInt(cur.getColumnIndexOrThrow(SuplaContract.SceneEntry.COLUMN_NAME_VISIBLE))
  }

  override fun getContentValues(): ContentValues {
    val values = ContentValues()
    values.put(SuplaContract.SceneEntry.COLUMN_NAME_SCENEID, sceneId)
    values.put(SuplaContract.SceneEntry.COLUMN_NAME_LOCATIONID, locationId)
    values.put(SuplaContract.SceneEntry.COLUMN_NAME_ALTICON, altIcon)
    values.put(SuplaContract.SceneEntry.COLUMN_NAME_USERICON, userIcon)
    values.put(SuplaContract.SceneEntry.COLUMN_NAME_CAPTION, caption)
    values.put(SuplaContract.SceneEntry.COLUMN_NAME_SORT_ORDER, sortOrder)
    if (startedAt != null) {
      values.put(SuplaContract.SceneEntry.COLUMN_NAME_STARTED_AT, dateToString(startedAt!!))
    } else {
      values.putNull(SuplaContract.SceneEntry.COLUMN_NAME_STARTED_AT)
    }
    if (estimatedEndDate != null) {
      values.put(
        SuplaContract.SceneEntry.COLUMN_NAME_EST_END_DATE,
        dateToString(estimatedEndDate!!)
      )
    } else {
      values.putNull(SuplaContract.SceneEntry.COLUMN_NAME_STARTED_AT)
    }
    values.put(SuplaContract.SceneEntry.COLUMN_NAME_INITIATOR_ID, initiatorId)
    values.put(SuplaContract.SceneEntry.COLUMN_NAME_INITIATOR_NAME, initiatorName)
    values.put(SuplaContract.SceneEntry.COLUMN_NAME_PROFILEID, profileId)
    values.put(SuplaContract.SceneEntry.COLUMN_NAME_VISIBLE, visible)

    return values
  }

  fun assign(scene: SuplaScene) {
    sceneId = scene.id
    locationId = scene.locationId
    altIcon = scene.altIcon
    userIcon = scene.userIcon
    caption = scene.caption
  }

  fun assign(state: SuplaSceneState) {
    assert(sceneId == state.sceneId)
    startedAt = state.startedAt
    estimatedEndDate = state.estimatedEndDate
    initiatorId = state.initiatorId
    initiatorName = state.initiatorName
  }

  fun clone(): Scene {
    val rv = copy()
    rv.id = id
    return rv
  }

  fun isExecuting(): Boolean {
    val sst = startedAt
    val now = Date()
    if (sst != null && sst < now) {
      val eet = estimatedEndDate
      if (eet == null || eet > now) {
        return true
      }
    }
    return false
  }

  fun getImageId(nightMode: Boolean): ImageId {
    val standardIcons: IntArray

    if (nightMode) {
      // We intentionally specify icons with the _nighthtmode
      // suffix for night mode instead of using the default icons
      // from the drawable-night directory because not every
      // part of the application is night mode enabled yet.
      standardIcons = intArrayOf(
        R.drawable.scene0_nightmode, R.drawable.scene1_nightmode,
        R.drawable.scene2_nightmode, R.drawable.scene3_nightmode,
        R.drawable.scene4_nightmode, R.drawable.scene5_nightmode,
        R.drawable.scene6_nightmode, R.drawable.scene7_nightmode,
        R.drawable.scene8_nightmode, R.drawable.scene9_nightmode,
        R.drawable.scene10_nightmode, R.drawable.scene11_nightmode,
        R.drawable.scene12_nightmode, R.drawable.scene13_nightmode,
        R.drawable.scene14_nightmode, R.drawable.scene15_nightmode,
        R.drawable.scene16_nightmode, R.drawable.scene17_nightmode,
        R.drawable.scene18_nightmode, R.drawable.scene19_nightmode
      )
    } else {
      standardIcons = intArrayOf(
        R.drawable.scene0, R.drawable.scene1,
        R.drawable.scene2, R.drawable.scene3,
        R.drawable.scene4, R.drawable.scene5,
        R.drawable.scene6, R.drawable.scene7,
        R.drawable.scene8, R.drawable.scene9,
        R.drawable.scene10, R.drawable.scene11,
        R.drawable.scene12, R.drawable.scene13,
        R.drawable.scene14, R.drawable.scene15,
        R.drawable.scene16, R.drawable.scene17,
        R.drawable.scene18, R.drawable.scene19
      )
    }

    if (userIcon != 0) {
      val id = ImageId(userIcon, 1, profileId)
      if (ImageCache.bitmapExists(id)) {
        return id
      }
    }

    return if (altIcon >= standardIcons.size) {
      ImageId(standardIcons[0])
    } else {
      ImageId(standardIcons[altIcon])
    }
  }

  fun getImageId(): ImageId {
    return getImageId(false)
  }

  private fun dateFromString(str: String): Date {
    val fmt = DateFormat.getDateTimeInstance()
    return fmt.parse(str)!!
  }

  private fun dateToString(date: Date): String {
    val fmt = DateFormat.getDateTimeInstance()
    return fmt.format(date)
  }
}
