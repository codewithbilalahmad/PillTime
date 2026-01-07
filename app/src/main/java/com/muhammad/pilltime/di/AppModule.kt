package com.muhammad.pilltime.di

import android.content.Context
import androidx.room.Room
import com.muhammad.pilltime.PillTimeApplication
import com.muhammad.pilltime.data.local.PillTimeDatabase
import com.muhammad.pilltime.data.repository.MedicationRepositoryImp
import com.muhammad.pilltime.domain.repository.MedicationRepository
import com.muhammad.pilltime.presentation.screens.add_medication.AddMedicationViewModel
import com.muhammad.pilltime.utils.Constants.DATABASE_NAME
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { PillTimeApplication.INSTANCE }
    single {
        Room.databaseBuilder(get<Context>(), PillTimeDatabase::class.java,DATABASE_NAME)
            .fallbackToDestructiveMigration(true).setQueryCoroutineContext(Dispatchers.IO).build()
    }
    single {
        get<PillTimeDatabase>().medicineDao
    }
    singleOf(::MedicationRepositoryImp).bind<MedicationRepository>()
    viewModelOf(::AddMedicationViewModel)
}