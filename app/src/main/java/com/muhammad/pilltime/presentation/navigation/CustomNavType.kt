package com.muhammad.pilltime.presentation.navigation

import android.net.Uri
import androidx.navigation.NavType
import androidx.savedstate.SavedState
import com.muhammad.pilltime.domain.model.Medicine
import kotlinx.serialization.json.Json

object CustomNavType{
    val Medicine = object : NavType<Medicine>(isNullableAllowed = true){
        override fun put(
            bundle: SavedState,
            key: String,
            value: Medicine,
        ) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun get(
            bundle: SavedState,
            key: String,
        ): Medicine? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): Medicine {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: Medicine): String {
            return Uri.encode(Json.encodeToString(value))
        }
    }
}