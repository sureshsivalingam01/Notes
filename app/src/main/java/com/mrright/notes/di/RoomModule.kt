package com.mrright.notes.di

import android.content.Context
import androidx.room.Room
import com.mrright.notes.data.cache.NoteDataBase
import com.mrright.notes.utils.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {


    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDataBase {
        return Room.databaseBuilder(context, NoteDataBase::class.java, DB_NAME)
            .fallbackToDestructiveMigration().build()
    }


    @Provides
    @Singleton
    fun provideNoteDao(noteDataBase: NoteDataBase) = noteDataBase.notesDao()


}