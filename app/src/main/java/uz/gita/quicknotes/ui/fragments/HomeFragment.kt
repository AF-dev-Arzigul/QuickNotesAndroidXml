package uz.gita.quicknotes.ui.fragments

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import uz.gita.quicknotes.model.Notes
import uz.gita.quicknotes.R
import uz.gita.quicknotes.viewModel.NotesViewModel
import uz.gita.quicknotes.databinding.FragmentHomeBinding
import uz.gita.quicknotes.ui.adapter.NotesAdapter

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private val viewModel: NotesViewModel by viewModels()
    var oldMyNotes = arrayListOf<Notes>()
    lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)

        viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->

            if (notesList.isEmpty()) binding.ivPlaceholder.visibility = View.VISIBLE
            else binding.ivPlaceholder.visibility = View.GONE

            oldMyNotes = notesList as ArrayList<Notes>
            adapter = NotesAdapter(requireContext(), notesList)
            binding.rvAllNotes.layoutManager =
                StaggeredGridLayoutManager(2, VERTICAL)
            binding.rvAllNotes.adapter = adapter
        }

        binding.filterHigh.setOnClickListener {

            binding.filterHigh.setBackgroundResource(R.drawable.btn_filter_shape_foreground)
            binding.filterLow.setBackgroundResource(R.drawable.btn_filter_shape)
            binding.filterMedium.setBackgroundResource(R.drawable.btn_filter_shape)
            binding.filterNone.setBackgroundResource(R.drawable.btn_filter_shape)

            viewModel.getHighNotes().observe(viewLifecycleOwner) { notesList ->

                if (notesList.isEmpty()) binding.ivPlaceholder.visibility = View.VISIBLE
                else binding.ivPlaceholder.visibility = View.GONE

                oldMyNotes = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(), notesList)
                binding.rvAllNotes.layoutManager =
                    StaggeredGridLayoutManager(2, VERTICAL)
                binding.rvAllNotes.adapter = adapter
            }
        }

        binding.filterMedium.setOnClickListener {

            binding.filterHigh.setBackgroundResource(R.drawable.btn_filter_shape)
            binding.filterLow.setBackgroundResource(R.drawable.btn_filter_shape)
            binding.filterMedium.setBackgroundResource(R.drawable.btn_filter_shape_foreground)
            binding.filterNone.setBackgroundResource(R.drawable.btn_filter_shape)

            viewModel.getMediumNotes().observe(viewLifecycleOwner) { notesList ->

                if (notesList.isEmpty()) binding.ivPlaceholder.visibility = View.VISIBLE
                else binding.ivPlaceholder.visibility = View.GONE

                oldMyNotes = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(), notesList)
                binding.rvAllNotes.layoutManager =
                    StaggeredGridLayoutManager(2, VERTICAL)
                binding.rvAllNotes.adapter = adapter
            }
        }

        binding.filterLow.setOnClickListener {

            binding.filterHigh.setBackgroundResource(R.drawable.btn_filter_shape)
            binding.filterLow.setBackgroundResource(R.drawable.btn_filter_shape_foreground)
            binding.filterMedium.setBackgroundResource(R.drawable.btn_filter_shape)
            binding.filterNone.setBackgroundResource(R.drawable.btn_filter_shape)

            viewModel.getLowNotes().observe(viewLifecycleOwner) { notesList ->

                if (notesList.isEmpty()) binding.ivPlaceholder.visibility = View.VISIBLE
                else binding.ivPlaceholder.visibility = View.GONE

                oldMyNotes = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(), notesList)
                binding.rvAllNotes.layoutManager =
                    StaggeredGridLayoutManager(2, VERTICAL)
                binding.rvAllNotes.adapter = adapter
            }
        }

        binding.filterNone.setOnClickListener {

            binding.filterHigh.setBackgroundResource(R.drawable.btn_filter_shape)
            binding.filterLow.setBackgroundResource(R.drawable.btn_filter_shape)
            binding.filterMedium.setBackgroundResource(R.drawable.btn_filter_shape)
            binding.filterNone.setBackgroundResource(R.drawable.btn_filter_shape_foreground)

            viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->

                if (notesList.isEmpty()) binding.ivPlaceholder.visibility = View.VISIBLE
                else binding.ivPlaceholder.visibility = View.GONE

                oldMyNotes = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(), notesList)
                binding.rvAllNotes.layoutManager =
                    StaggeredGridLayoutManager(2, VERTICAL)
                binding.rvAllNotes.adapter = adapter
            }
        }

        binding.btnAddNotes.setOnClickListener {
            closeKeyBoard()
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_createNotesFragment)
        }

        binding.btnDeletedItems.setOnClickListener {
            closeKeyBoard()
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_deletedNotesFragment)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.app_bar_search -> {
                val searchView = item.actionView as SearchView
                searchView.queryHint = "Write note title..."
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        notesFiltering(p0)
                        return true
                    }
                })
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun notesFiltering(p0: String?) {
        val newFilteredList = arrayListOf<Notes>()
        for (i in oldMyNotes) {
            if (i.title.contains(p0!!) || i.subTitle.contains(p0)) {
                newFilteredList.add(i)
            }
        }

        adapter.filtering(newFilteredList)
    }


    private fun closeKeyBoard() {
        val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}