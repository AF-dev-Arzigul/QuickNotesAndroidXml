package uz.gita.quicknotes.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import uz.gita.quicknotes.R
import uz.gita.quicknotes.model.Notes
import uz.gita.quicknotes.databinding.ItemNotesBinding
import uz.gita.quicknotes.ui.fragments.HomeFragmentDirections

class NotesAdapter(val requireContext: Context, var notesList: List<Notes>) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private var itemRestoreClickOnListener: ((Notes) -> Unit)? = null

    fun setItemRestoreClickOnListener(block: (Notes) -> Unit) {
        itemRestoreClickOnListener = block
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filtering(newFilteredList: ArrayList<Notes>) {
        notesList = newFilteredList
        notifyDataSetChanged()
    }

    inner class NotesViewHolder(private val binding: ItemNotesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {

            binding.notesTitle.text = notesList[adapterPosition].title
            binding.notesSubTitle.text = Html.fromHtml(notesList[adapterPosition].subTitle)
            binding.notesDate.text = notesList[adapterPosition].date

            if (notesList[adapterPosition].deleted) {
                binding.btnNoteRestore.visibility = View.VISIBLE
            }

            when (notesList[adapterPosition].priority) {
                "1" -> {
                    binding.viewPriority.setBackgroundResource(R.drawable.green_dot)
                }
                "2" -> {
                    binding.viewPriority.setBackgroundResource(R.drawable.yellow_dot)
                }
                else -> {
                    binding.viewPriority.setBackgroundResource(R.drawable.red_dot)
                }
            }

        }

        init {

            binding.btnNoteRestore.setOnClickListener {
                itemRestoreClickOnListener?.invoke(notesList[adapterPosition])
            }

            binding.root.setOnClickListener {
                if (!notesList[adapterPosition].deleted) {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToEditNotesFragment(notesList[adapterPosition])
                    Navigation.findNavController(it).navigate(action)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            ItemNotesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) = holder.bind()

    override fun getItemCount(): Int {
        return notesList.size
    }
}