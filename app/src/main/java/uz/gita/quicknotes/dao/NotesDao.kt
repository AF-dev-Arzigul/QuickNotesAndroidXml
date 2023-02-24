package uz.gita.quicknotes.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import uz.gita.quicknotes.model.Notes

@Dao
interface NotesDao {
    @Query("SELECT * FROM Notes WHERE deleted = 0")
    fun getNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM Notes WHERE deleted = 1")
    fun getDeletedNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM Notes WHERE priority = 3 AND deleted = 0")
    fun getHighNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM Notes WHERE priority = 2 AND deleted = 0")
    fun getMediumNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM Notes WHERE priority = 1 AND deleted = 0")
    fun getLowNotes(): LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(notes: Notes)

    @Query("DELETE FROM Notes WHERE id = :id")
    fun deleteNotes(id: Int)

    @Update
    fun updateNotes(notes: Notes)
}
