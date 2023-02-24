package uz.gita.quicknotes.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import uz.gita.quicknotes.viewModel.NotesViewModel
import uz.gita.quicknotes.databinding.FragmentDeletedNotesBinding
import uz.gita.quicknotes.model.Notes
import uz.gita.quicknotes.ui.adapter.NotesAdapter

class DeletedNotesFragment : Fragment() {
    lateinit var binding: FragmentDeletedNotesBinding
    private val viewModel: NotesViewModel by viewModels()
    var oldMyNotes = arrayListOf<Notes>()
    lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Deleted notes"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeletedNotesBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = NotesAdapter(requireContext(), oldMyNotes)
        binding.rvDeletedNotes.adapter = adapter

        viewModel.getDeletedNotes().observe(viewLifecycleOwner) { notesList ->

            if (notesList.isEmpty()) binding.ivPlaceholder.visibility = View.VISIBLE
            else binding.ivPlaceholder.visibility = View.GONE

            oldMyNotes = notesList as ArrayList<Notes>
            adapter = NotesAdapter(requireContext(), notesList)
            binding.rvDeletedNotes.layoutManager =
                StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            binding.rvDeletedNotes.adapter = adapter

            adapter.setItemRestoreClickOnListener {
                viewModel.restoreNotes(it)
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigateUp()
        return super.onOptionsItemSelected(item)
    }

}