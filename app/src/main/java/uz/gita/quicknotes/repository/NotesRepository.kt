package uz.gita.quicknotes.repository

import androidx.lifecycle.LiveData
import uz.gita.quicknotes.dao.NotesDao
import uz.gita.quicknotes.model.Notes

class NotesRepository(private val dao: NotesDao) {

    fun getAllNotes(): LiveData<List<Notes>> {
        return dao.getNotes()
    }

    fun insertNotes(notes: Notes) {
        dao.insertNotes(notes)
    }

    fun deleteNotes(id: Int) {
        dao.deleteNotes(id)
    }

    fun updateNotes(notes: Notes) {
        dao.updateNotes(notes)
    }

    //filters

    fun getHighNotes(): LiveData<List<Notes>> {
        return dao.getHighNotes()
    }

    fun getMediumNotes(): LiveData<List<Notes>> {
        return dao.getMediumNotes()
    }

    fun getLowNotes(): LiveData<List<Notes>> {
        return dao.getLowNotes()
    }

    fun getDeletedNotes(): LiveData<List<Notes>> {
        return dao.getDeletedNotes()
    }
}