package uz.gita.quicknotes.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import uz.gita.quicknotes.database.NotesDatabase
import uz.gita.quicknotes.model.Notes
import uz.gita.quicknotes.repository.NotesRepository

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    var repository: NotesRepository

    init {
        val dao = NotesDatabase.getDatabaseInstance(application).myNotesDao()
        repository = NotesRepository(dao)
    }

    fun addNotes(notes: Notes) {
        repository.insertNotes(notes)
    }

    fun getNotes(): LiveData<List<Notes>> {
        return repository.getAllNotes()
    }

    fun deleteNotes(notes: Notes) {
        repository.updateNotes(
            Notes(
                notes.id,
                notes.title,
                notes.subTitle,
                notes.notes,
                notes.date,
                notes.priority,
                notes.status,
                deleted = true
            )
        )
    }

    fun restoreNotes(notes: Notes) {
        repository.updateNotes(
            Notes(
                notes.id,
                notes.title,
                notes.subTitle,
                notes.notes,
                notes.date,
                notes.priority,
                notes.status,
                deleted = false
            )
        )
    }

    fun updateNotes(notes: Notes) {
        repository.updateNotes(notes)
    }

    //filters
    fun getHighNotes(): LiveData<List<Notes>> {
        return repository.getHighNotes()
    }

    fun getMediumNotes(): LiveData<List<Notes>> {
        return repository.getMediumNotes()
    }

    fun getLowNotes(): LiveData<List<Notes>> {
        return repository.getLowNotes()
    }

    fun getDeletedNotes(): LiveData<List<Notes>> {
        return repository.getDeletedNotes()
    }

}